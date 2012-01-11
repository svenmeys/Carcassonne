package be.fomp.carcassonne.model;

public interface Beanable<T> {
	/**
	 * The method converts an object to its bean instance
	 * @return
	 */
	T toBean();
	
	/**
	 * @param bean The bean instance from this object
	 */
	void setValues(T bean);
}
