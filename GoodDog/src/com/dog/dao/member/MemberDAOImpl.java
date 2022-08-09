package com.dog.dao.member;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.dog.vo.member.MemberVO;



public class MemberDAOImpl implements MemberDAO {

	@Override
	public MemberVO selectMemberById(SqlSession session, String memId) throws SQLException {
		MemberVO member = session.selectOne("Member-Mapper.selectMemberById", memId);
		return member;
	}

	@Override
	public List<MemberVO> selectMemberList(SqlSession session) throws SQLException {
		List<MemberVO> memberList = session.selectList("Member-Mapper.selectMemberList");
		return memberList;
	}


	@Override
	public void insertMember(SqlSession session, MemberVO member) throws SQLException {
		session.update("Member-Mapper.insertMember", member);
	}

	@Override
	public void updateMember(SqlSession session, MemberVO member) throws SQLException {
		session.update("Member-Mapper.updateMember", member);
	}

	@Override
	public void deleteMember(SqlSession session, String id) throws SQLException {
		session.update("Member-Mapper.deleteMember", id);
	}

	@Override
	public void disabledMember(SqlSession session, String id) throws SQLException {
		session.update("Member-Mapper.disabledMember", id);
	}

	@Override
	public void enabledMember(SqlSession session, String id) throws SQLException {
		session.update("Member-Mapper.enabledMember", id);
	}

}