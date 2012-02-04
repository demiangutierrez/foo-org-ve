package org.cyrano.swing.java2D.scroll;

import javax.swing.JFrame;

public class ScrollMain extends JFrame {

	public ScrollMain() {
		add(new ScrollPanel());

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(800, 600);
		setVisible(true);
	}

	public static void main(String[] args) {
		new ScrollMain();
	}
}
