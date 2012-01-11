package be.fomp.carcassonne.exceptions;

@SuppressWarnings("serial")
public class ActionNotAllowedException extends GameException {

	public ActionNotAllowedException(){
		super();
	}
	
	public ActionNotAllowedException(String message){
		super(message);
	}
	
	public ActionNotAllowedException(Throwable cause){
		super(cause);
	}
	
	public ActionNotAllowedException(String message, Throwable cause){
		super(message, cause);
	}
}
