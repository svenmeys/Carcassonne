package be.fomp.carcassonne.view;

import java.awt.event.ActionListener;
import java.util.Observer;

public interface View extends Observer, ActionListener{
	void createView();
	void setVisible(boolean visible);
	void refresh();
}
