package com.tutorial.imagesdyn;

import java.awt.Color;

public class Planet {

	public String name;
	public int x;
	public int y;
	public int r;

	public Color color;

	public Planet(String name, int x, int y, int r, Color color) {
		this.name = name;
		this.x = x;
		this.y = y;
		this.r = r;
		this.color = color;
	}
}
