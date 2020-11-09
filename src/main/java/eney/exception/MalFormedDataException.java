package eney.exception;

public class MalFormedDataException extends Exception{
	
	private static final long serialVersionUID = 3210534041068920053L;
	
	public MalFormedDataException(String data_name){
		super(data_name);
	}

}
