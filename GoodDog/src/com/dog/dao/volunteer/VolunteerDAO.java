package com.dog.dao.volunteer;

import java.sql.SQLException;
import java.util.List;

import com.dog.command.Criteria;
import com.dog.vo.member.MemberVO;
import com.dog.vo.volunteer.VolunteerVO;

public interface VolunteerDAO {
	
	List<MemberVO> selectVolWantMemberList(Criteria cri)throws SQLException;
	int selectVolWantMemberListCount(Criteria cri)throws SQLException;
	
	public void insertVol(VolunteerVO volVo) throws SQLException;

	public int insertVolWantMember(VolunteerVO volVo) throws SQLException;
	
	List<VolunteerVO> selectVolList(Criteria cri) throws SQLException;
	
	int selectVolListCount(Criteria cri) throws SQLException;
	
	VolunteerVO selectVolunteerByTitle(String volTilte) throws SQLException;
	
	
}
