package controller;

import view.ETRGUI;

public class ETRController {
	
	private ETRGUI gui;

	public void startDesktop() {
		gui = new ETRGUI(this);
		gui.startGUI();
	}

}
