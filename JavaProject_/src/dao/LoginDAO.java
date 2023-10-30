package dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import util.JDBCUtil;
import vo.Member;

// 데이터베이스로 쿼리를 날려서 결과를 얻는다.
public class LoginDAO {
	// 싱글톤 패턴을 만든다.
	private static LoginDAO instance = null;
	private LoginDAO() {}
	public static LoginDAO getInstance() {
		if(instance == null) 
			instance = new LoginDAO();
		return instance;
	}
	
	// JDBC를 부른다.
	JDBCUtil jdbc = JDBCUtil.getInstance();
	
//	public Map<String, Object> login(String id, String pass){
	/*
	 * Map대신 VO인 Member를 리턴
	 */
	public Member login(String id, String pass){
		// 로그인
		// 내가 입력한 아이디, 비밀번호에 해당하는 회원정보를 주세요
		// SELECT * FROM MEMBER WHERE MEM_ID = ? AND MEM_PW = ?
		// SELECT * FROM MEMBER WHERE MEM_ID = 'admin' AND MEM_PW = '1234'
		String sql = "SELECT * FROM MB WHERE ID = ? AND PASS = ?";
		List<Object> param = new ArrayList<Object>();
		System.out.println(id);
		System.out.println(pass);
		param.add(id);	//? 자리에 간다
		param.add(pass);
		
		/*
		 * Map 데이터 -> VO데이터 하는법  --> convertutils.java에 있음
		 * result에는 실제 회원정보가 담김
		 */
		Map result = jdbc.selectOne(sql, param);
		/*
		 * map인 result에서 데이터를 꺼내서 VO인 member에 담는다
		 */
		String name = (String) result.get("NAME");
		String phone = (String) result.get("PHONE");;
		String id1 = (String) result.get("ID");;
		String pass1 = (String) result.get("PASS");;
		
		Member member = new Member();
		/*
		 * 세터에 데이터를 담는다
		 */
		member.setId(id1);
		member.setName(name);
		member.setPass(pass1);
		member.setPhone(phone);
		
//		return jdbc.selectOne(sql, param);
		return member;
	}
	public int signUp(List<Object> param) {
		String sql = "insert INTO MB (id, pass, name, phone) values(?, ?, ?, ?)";
		return jdbc.update(sql, param);
	}
}
