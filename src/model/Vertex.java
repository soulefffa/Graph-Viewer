package model;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.io.Serializable;

/**
 * Represents a vertices in the graph it belongs to. His xPosition and yPosition
 * determine where the vertices is positioned. The 0x0 point is situated on
 * top-left corner of the graph.
 * 
 * @author Axel Schumacher
 */
public class Vertex implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * Vertex position under X axe
	 */
	private int xPosition = 0;

	/**
	 * Vertex position under Y axe
	 */
	private int yPosition = 0;

	/**
	 * Vertex name
	 */
	private String name;

	/**
	 * Name position under X axe
	 */
	private int xName;

	/**
	 * Name position under Y axe
	 */
	private int yName;

	/**
	 * Name angle
	 */
	private int nameAngle = 0;

	/**
	 * Name distance
	 */
	private int nameDistance = 5;

	/**
	 * Vertex's diameter in pixels
	 */
	private int diameter = 10;
	
	/** Vertex is a label */
	private boolean label = false;

	/** In program name font */
	private Font font = new Font("Arial", Font.BOLD, diameter * 2);

	/**
	 * Basic empty constructor with an empty name, a 0x0 position and a 10px
	 * diameter
	 */
	public Vertex() {
		name = new String("");
		changeNamePosition();
	}

	/**
	 * Default constructor
	 * 
	 * @param name
	 *            Vertex name
	 * @param xPosition
	 *            Vertex position under X axe
	 * @param yPosition
	 *            Vertex position under Y axe
	 * @param diameter
	 *            Vertex diameter in pixels
	 * @param isLabel
	 *            Vertex is a label
	 */
	public Vertex(String name, int xPosition, int yPosition, int diameter,
			boolean label) {
		this.name = name;
		this.xPosition = xPosition;
		this.yPosition = yPosition;
		this.diameter = diameter;
		this.label = label;
		changeNamePosition();
	}

	/**
	 * Copy constructor
	 * 
	 * @param other
	 *            vertex to copy
	 */
	public Vertex(Vertex other) {
		xPosition = other.xPosition;
		yPosition = other.yPosition;
		name = other.name;
		xName = other.xName;
		yName = other.yName;
		nameAngle = other.nameAngle;
		nameDistance = other.nameDistance;
		diameter = other.diameter;
		label = other.label;
	}

	/**
	 * Returns Vertex's point
	 * 
	 * @return Vertex's point
	 */
	public Point getPoint() {
		return new Point(xPosition, yPosition);
	}

	/**
	 * Returns Vertex clickable rectangle
	 * 
	 * @return
	 */

	/**
	 * Return Vertex selectable circle
	 * 
	 * @param multiplier
	 *            user friendly multiplier
	 * @return rectangle
	 */
	public Rectangle getCircleRectangle(int multiplier) {
		int realWidth = diameter * multiplier;
		Rectangle circle = new Rectangle(xPosition - realWidth / 2, yPosition
				- realWidth / 2, realWidth, realWidth);
		return circle;
	}

	public Rectangle getNameRectangle(Graphics g) {
		FontMetrics metrics = g.getFontMetrics(font);
		int hgt = metrics.getHeight();
		int adv = metrics.stringWidth(name);
		Dimension size = new Dimension(adv + 2, hgt + 2);
		Rectangle result = new Rectangle(new Point(xName, yName), size);
		return result;
	}

	/**
	 * Draws the vertex
	 * 
	 * @param g
	 *            Graphics passed by GeometricGraph
	 */
	public void draw(Graphics g) {
		if (!isLabel()) {
			g.fillOval(xPosition - diameter / 2, yPosition - diameter / 2,
					diameter, diameter);
		}
		g.setFont(font);
		g.drawString(name, xName, yName);
	}

	/**
	 * Draws the vertex' point in PS language
	 */
	public String drawPointPS(int graphWidth, int graphHeight, int sheetWidth,
			int sheetHeight) {
		if (isLabel()) {
			return "";
		}
		int xPosition = this.xPosition;
		if (graphWidth > sheetWidth)
			xPosition = xPosition * sheetWidth / graphWidth;
		int yPosition = this.yPosition;
		if (graphHeight > sheetHeight)
			yPosition = yPosition * sheetHeight / graphHeight;
		String result = "";
		result += xPosition + " " + Integer.toString(sheetHeight - yPosition)
				+ " " + diameter / 2 + " 0 360 arc\n" + "0 setgray\n"
				+ "fill\n" + "stroke\n" + "\n";
		return result;
	}

	/**
	 * Draws the vertex name in PS language
	 */
	public String drawNamePS(int graphWidth, int graphHeight, int sheetWidth,
			int sheetHeight) {
		int xName = this.xName;
		if (graphWidth > sheetWidth)
			xName = xName * sheetWidth / graphWidth;
		int yName = this.yName;
		if (graphHeight > sheetHeight)
			yName = yName * sheetHeight / graphHeight;
		String result = "";
		result += xName + " " + Integer.toString(sheetHeight - yName)
				+ " moveto\n" + "(" + name + ") show\n" + "\n";
		return result;
	}

	/**
	 * ToString method
	 */
	public String toString() {
		return (isLabel() ? "Label " : "Vertex ") + name + " at " + xPosition
				+ "x" + yPosition;
	}

	/**
	 * Changes name position with angle
	 */
	private void changeNamePosition() {
		int width = 2 * nameDistance * diameter / 10;
		int height = 2 * nameDistance * diameter / 10;
		int x = xPosition
				+ (int) (width * Math.cos(nameAngle * 2 * Math.PI / 360));
		int y = yPosition
				+ (int) (height * Math.sin(nameAngle * 2 * Math.PI / 360));
		xName = x;
		yName = y;
	}

	/**
	 * Returns index associated String
	 * 
	 * @param i
	 *            index to convert
	 * @return Name
	 */
	public static String indexToString(int index) {
		if (index >= 26)
			return Integer.toString(index - 25);
		String alphabet = "abcdefghijklmnopqrstuvwxyz";
		return alphabet.substring(index, index + 1);
	}

	public int getXPosition() {
		return xPosition;
	}

	public void setXPosition(int position) {
		xPosition = position;
		changeNamePosition();
	}

	public int getYPosition() {
		return yPosition;
	}

	public void setYPosition(int position) {
		yPosition = position;
		changeNamePosition();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getDiameter() {
		return diameter;
	}

	public void setDiameter(int diameter) {
		this.diameter = diameter;
		changeNamePosition();
		font = new Font("Arial", Font.BOLD, diameter * 2);
	}

	public int getXName() {
		return xName;
	}

	public void setXName(int name) {
		xName = name;
	}

	public int getYName() {
		return yName;
	}

	public void setYName(int name) {
		yName = name;
	}

	public void setNameAngle(int nameAngle) {
		this.nameAngle = nameAngle;
		this.changeNamePosition();
	}

	public int getNameAngle() {
		return nameAngle;
	}

	public void setNameDistance(int nameDistance) {
		this.nameDistance = nameDistance;
		this.changeNamePosition();
	}

	public int getNameDistance() {
		return nameDistance;
	}

	public boolean isLabel() {
		return label;
	}

	public void setLabel(boolean label) {
		this.label = label;
	}
}
