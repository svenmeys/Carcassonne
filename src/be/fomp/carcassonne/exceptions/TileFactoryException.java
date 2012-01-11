package be.fomp.carcassonne.exceptions;

@SuppressWarnings("serial")
public class TileFactoryException extends CarcasonneException {

	public TileFactoryException(){
		super();
	}
	
	public TileFactoryException(String message){
		super(message);
	}
	
	public TileFactoryException(Throwable cause){
		super(cause);
	}
	
	public TileFactoryException(String message, Throwable cause){
		super(message, cause);
	}
}