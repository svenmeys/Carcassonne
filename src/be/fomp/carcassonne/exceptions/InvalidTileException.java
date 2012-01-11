package be.fomp.carcassonne.exceptions;

@SuppressWarnings("serial")
public class InvalidTileException extends GameException {

	public InvalidTileException(){
		super();
	}
	
	public InvalidTileException(String message){
		super(message);
	}
	
	public InvalidTileException(Throwable cause){
		super(cause);
	}
	
	public InvalidTileException(String message, Throwable cause){
		super(message, cause);
	}
}
