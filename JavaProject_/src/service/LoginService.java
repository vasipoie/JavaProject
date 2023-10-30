package service;

import java.util.List;

import controller.Controller;
import dao.LoginDAO;
import exception.PassChkException;
import vo.Member;

public class LoginService {
	// 싱글톤 패턴을 만든다.
	private static LoginService instance = null;
	private LoginService() {}
	public static LoginService getInstance() {
		if(instance == null) 
			instance = new LoginService();
		return instance;
	}
	
	public static int loginCount = 0;
	
	// Dao를 부른다
	LoginDAO dao = LoginDAO.getInstance();
	
	public boolean login(String id, String pass){
		/*
		 * Map->VO
		 */
//		Map<String, Object> result = dao.login(id,pass);
		Member result = dao.login(id,pass);
		
		/*
		 * map은 get을 이용해서 MEM_ID를 꺼내왔는데
		 * VO로 바꾸면 게터로 getId()사용하면됨
		 */
//		if(result != null && result.get("MEM_ID").equals(id)){	//map방식
		if(result != null && result.getId().equals(id)){		//VO방식
			loginCount++;
			Controller.sessionStorage.put("login", true);
			Controller.sessionStorage.put("loginInfo", result);
			return true;
		}else{
			return false;
		}
	}
	
	/*
	 * 파라미터 직접 id,pass등 4개 입력하기 귀찮으니까 param으로 보낸다
	 * Dao에도 만들어야한다
	 */
	public boolean signUp(List<Object> param) throws PassChkException{
		int result = dao.signUp(param);
		String pass = (String) param.get(1);	//List에서 pass는 1번째값
		if(pass.contains("admin")) {
			throw new PassChkException();
		}
		if(result ==0) {	//회원가입실패
			return false;
		}else
			return true;
	}
	
	/**
	 * id 10자 이내 입력 하도록 체크할 것
	 * 영문이랑, 숫자만 입력 가능하도록
	 */
	public boolean idChk(String id) {
		boolean pass = true;
		for(char ch : id.toCharArray()) {
			//숫자 체크
			if('0' <= ch && ch <= '9') {
				
			}
			else if('a' <= ch && ch <= 'z') {
				
			}else {
				return false;
			}
		}
		if(id.length()<=10) {
			
		}else {
			return false;
		}
		
		return pass;
	}
	
	/**
	 * name 2~4글자 입력, 체크
	 */
	public boolean nameChk(String name) {
		boolean pass = true;
		if(2<name.length() && name.length()<8) {
			
		}else {
			return false;
		}
		
		return pass;
	}
	
	
	
}
