package com.dog.controller;

import java.io.File;
import java.lang.reflect.Method;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.dog.context.ApplicationContext;

public class InitApplicationContextServlet extends HttpServlet {

	@Override
	public void init(ServletConfig config) throws ServletException {

		String beanConfigXml = config.getInitParameter("contextConfigLocation");

		if (beanConfigXml == null)
			return;

		ServletContext ctx = config.getServletContext();

		beanConfigXml = ctx.getRealPath("/")
				+ beanConfigXml.replace("classpath:", "WEB-INF/classes/").replace("/", File.separator);
//		System.out.println("확인1");

		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder documentBuilder = factory.newDocumentBuilder();
			Document document = documentBuilder.parse(beanConfigXml);

			Element root = document.getDocumentElement();

			if(root == null || !root.getTagName().equals("beans")) return;
			
			NodeList beans = root.getElementsByTagName("bean"); 
			
//			System.out.println("확인2");

			Map<String, Object> applicationContext = 
					ApplicationContext.getApplicationContext(); 
			for (int i = 0; i < beans.getLength(); i++) {
				Node bean = beans.item(i);
				if (bean.getNodeType() == Node.ELEMENT_NODE) {
					Element ele = (Element) bean;
					String id = ele.getAttribute("id");
					String classType = ele.getAttribute("class");
					
					Class<?> cls = Class.forName(classType);
					Object targetObj = cls.newInstance(); 
					applicationContext.put(id, targetObj);
					
				}
			}
			
			for (int i = 0; i < beans.getLength(); i++) {
				Node bean = beans.item(i);
				if (bean.getNodeType() == Node.ELEMENT_NODE) {
					Element eleBean = (Element)bean;
//					System.out.println("확인3");

					NodeList properties = bean.getChildNodes();
					for (int j = 0; j < properties.getLength(); j++) {
						Node property = properties.item(j);
						if(!property.getNodeName().equals("property")) continue;
						
						if (property.getNodeType() == Node.ELEMENT_NODE) {
							Element ele = (Element) property;
							String name = ele.getAttribute("name");
							String ref = ele.getAttribute("ref-value");
							
							
							String setMethodName = "set" + name.substring(0, 1).toUpperCase() 
									+ name.substring(1);
							
							String className = eleBean.getAttribute("class");
							Class<?> classType = Class.forName(className);

							Method[] methods = classType.getMethods();
							if(methods!=null) for (Method method : methods) {
								if (method.getName().equals(setMethodName)) {
									
									method.invoke(applicationContext.get(eleBean.getAttribute("id")),
											applicationContext.get(ref));
									
									System.out.println("[invoke]"
											+applicationContext.get(eleBean.getAttribute("id"))
											+":"+applicationContext.get(ref));
								}
							}
						}						
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}