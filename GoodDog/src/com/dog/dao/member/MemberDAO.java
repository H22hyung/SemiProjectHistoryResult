package com.dog.dao.member;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.dog.command.Criteria;
import com.dog.vo.member.MemberVO;



public interface MemberDAO {
	
	// ȸ������ ��ȸ
	MemberVO selectMemberById(String memId) throws SQLException;
	
	int selectMemberListCount(Criteria cri)throws SQLException;
	
	// ȸ������Ʈ ��ȸ
	List<MemberVO> selectMemberList(Criteria cri) throws SQLException;

	
	// ȸ�� �߰�
	public void insertMember(MemberVO member) throws SQLException;
	
	// ȸ�� ����
	public void updateMember(MemberVO member) throws SQLException;
	
	// ȸ�� ����
	void deleteMember(String memId) throws SQLException;
	
	// ȸ�� ����
	//void disabledMember(String memId) throws SQLException;
	
	// ȸ�� Ȱ��ȭ
	//void enabledMember(String memId) throws SQLException;
	
}