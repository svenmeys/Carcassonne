package be.fomp.carcassonne.model;

import java.util.Observer;

/**
 * Interface voor de Observable klasse. 
 * Laat toe om op interface niveau te interrageren met models.
 * @author sven
 *
 */
public interface Observable {
	
	void addObserver(Observer o);
	
	int countObservers();
	
	void deleteObserver(Observer o);
	void deleteObservers();
	
	boolean hasChanged();
	
	void notifyObservers(Object arg);
	void notifyObservers();
}
