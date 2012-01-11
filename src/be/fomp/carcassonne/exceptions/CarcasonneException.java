package be.fomp.carcassonne.exceptions;

@SuppressWarnings("serial")
public class CarcasonneException extends GameException {

	public CarcasonneException(){
		super();
	}
	
	public CarcasonneException(String message){
		super(message);
	}
	
	public CarcasonneException(Throwable cause){
		super(cause);
	}
	
	public CarcasonneException(String message, Throwable cause){
		super(message, cause);
	}
}
