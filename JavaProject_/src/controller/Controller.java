package controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import exception.PassChkException;
import print.Print;
import service.LoginService;
import util.ScanUtil;
import util.View;

public class Controller extends Print {
	// 세션
	/*
	 * static -> 데이터가 하나임
	 * 내가 넣고싶은 데이터를 sessionStorage에 넣는다
	 */
	static public Map<String, Object> sessionStorage = new HashMap<>();

	/*
	 * 싱글톤 객체를 getInstance로 가져옴
	 * 로그인 서비스 안에 있는 기능 사용가능
	 */
	LoginService loginService = LoginService.getInstance();

	public static void main(String[] args) {
		new Controller().start();
	}

	private void start() {
		sessionStorage.put("login", false); // false: 로그인 안됨
		sessionStorage.put("loginInfo", null);
		View view = View.HOME;
		while (true) {
			switch (view) {
			case HOME:
				view = home();
				break;
			case MEMBER_LOGIN:
				view = login();
				break;
			case MEMBER_SIGNUP:
				view = signUp();
				break;
			case MAIN:
				view = main();
				break;
			}
		}
	}

	private View signUp() {
		System.out.println("회원가입 정보를 입력해주세요.");
		/*
		 * id 10자 이내 입력 하도록 체크할 것
		 * 영문이랑, 숫자만 입력 가능하도록
		 */
		String id = "";
		while(true) {	//아이디
			id = ScanUtil.nextLine("아이디 >>");
			boolean idPass = loginService.idChk(id);
			if(idPass) break;
			else {
				if(id.length() >10) {
					System.out.println("아이디를 10자 이내로 입력하세요.");
				}
				else 
					System.out.println("영문이랑 숫자만 입력하세요.");
			}
		}
		
		String pass = ScanUtil.nextLine("비밀번호 >>");
		/*
		 * service로 throw로 던지기 힘들면 try~catch구문을 control에 직접 만들자
		 */
		try {
			if(pass.contains("admin")) {
				throw new PassChkException();
			}
		} catch (Exception e) {
			System.out.println(e);
			return View.MEMBER_SIGNUP;
		}
		
		/*
		 * name 2~4글자 입력, 체크
		 */
		String name = "";
		while(true) {
			name = ScanUtil.nextLine("이름 >>");
			boolean namePass = loginService.nameChk(name);
			if(namePass) break;
			else {
				System.out.println("이름은 2~4글자만 입력하세요.");
			}
		}
		
		/*
		 * phone 11자 숫자 입력하도록 체크
		 * long으로 데이터 바꾸고
		 * 숫자말고 다른게 있으면 오류 -> try~catch
		 * try 숫자 정상적으로
		 * catch 숫자로 바꿀 수 없으니까 다시하라고 알려주기
		 */
		String phone = "";
		while(true) {
			phone = ScanUtil.nextLine("전화번호 >>");
			try {
				if(phone.length()!=11) {
					System.out.println("11자리 숫자만 입력하세요");
					continue;
				}
				Long.parseLong(phone);
				break;	//long으로 바뀌면 break해서 while문 빠져나감. 안바뀌면 catch문으로 감
			} catch (Exception e) {
				
			}
			System.out.println("11자리 숫자만 입력하세요");
		}
		
		
		List<Object> param = new ArrayList();
		param.add(id);
		param.add(pass);
		param.add(name);
		param.add(phone);
		
		boolean signPass = loginService.signUp(param);	//service의 signUp으로 보내고, dao의 signUp으로 보내고, sql, param을 JDBCUtill update로 보낸다
		if(signPass) {
			System.out.println("회원가입에 성공했습니다.");
			System.out.println();
			return View.HOME;
		}else {
			System.out.println("회원가입에 실패했습니다. 다시 시도해주세요.");
			return View.MEMBER_SIGNUP;
		}
	}

	private View main() {
//		Map<String, Object> loginInfo = (Map<String, Object>) sessionStorage.get("loginInfo");
//		System.out.println(loginInfo.get("NAME") + "님! 로그인 되었습니다");
		System.out.println("환영합니다.");
		return null;
	}

	private View login() {
		View pageNo;
		/*
		 * Scanner sc = new Scanner(System.in);
		 * sc.next();
		 * ScanUtil메소드 만들었다 -> 안내문구도 강제로 넣을 수 있게 만들었다
		 */
		String id   = ScanUtil.nextLine("아이디 입력>>> ");
		String pass = ScanUtil.nextLine("비밀번호 입력>>> ");
		
		boolean loginChk = loginService.login(id, pass);
		if (loginChk) {
			pageNo = View.MAIN;
		} else {
			System.out.println("다시 로그인해주세요!");
			pageNo = View.MEMBER_LOGIN;
		}
		return pageNo;
	}

	private View home() {
		printHome();
		switch (ScanUtil.nextInt("번호 입력 >> ")) {
		case 1:
			return View.MEMBER_LOGIN;
		case 2:
			return View.MEMBER_SIGNUP;
		default:
			return View.HOME;
		}
	}

}
