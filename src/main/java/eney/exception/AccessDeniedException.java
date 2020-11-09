package eney.exception;

public class AccessDeniedException extends Exception{
	
	private static final long serialVersionUID = 469811471683115217L;
	
	public AccessDeniedException(String message){
		super(message);
	}
}
