package eney.exception;

public class UserCertifyException extends Exception {

	private static final long serialVersionUID = 5623422314129636384L;
	
	public UserCertifyException(String message){
		super(message);
	}
	
	public UserCertifyException(){
		super();
	}

}
