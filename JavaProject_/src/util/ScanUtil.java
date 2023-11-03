package util;

import java.util.Scanner;

public class ScanUtil {
	// 스캐너를 손쉽게 사용할 수 있는 static 메서드를 가지고있음
	static Scanner sc = new Scanner(System.in);
	
	/*
	 * (String print) : 강제로 안내문구 넣게 함
	 */
	public static String nextLine(String print) {
		System.out.print(print);
		return nextLine();
	}
	
	/*
	 * 니까 사용자가 사용 못하게 막음
	 */
	 static String nextLine() {
		return sc.nextLine();
	}
	public static int nextInt(String print) {
		System.out.print(print);
		return nextInt();
	}
	
	 static int nextInt() {
		while(true) {
			try {
				int result = Integer.parseInt(sc.nextLine());
				return result;
			}catch (NumberFormatException e) {
				System.out.println("잘못 입력!!");
			}
		}
	}
}
