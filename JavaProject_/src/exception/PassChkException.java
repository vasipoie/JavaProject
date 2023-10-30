package exception;

public class PassChkException extends RuntimeException{
	
	public PassChkException() {
		super("패스워드에 문제가 있습니다.");
	}
}
