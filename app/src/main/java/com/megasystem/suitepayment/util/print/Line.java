package com.megasystem.suitepayment.util.print;

import java.io.Serializable;

public class Line implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -973658107627071292L;

	private String Text;
	private Alignment To;
	private int FontSize = 1;
	private int LineFeed = 1;
	private int type=0;

	public static enum Alignment {
		Left, Right, Center, None
	}

	public Line(String text, Alignment alignment) {
		Text =text;
		To = alignment;
	}

	public Line(String text, Alignment alignment, int lineFeed) {
		Text = text;
		To = alignment;
		LineFeed = lineFeed;
	}
	
	
	public Line(String text, Alignment alignment, int fontSize, int lineFeed, int type) {
		this.Text = text;
		this.To = alignment;
		this.FontSize = fontSize;
		this.LineFeed = lineFeed;
		this.type = type;
	}


	public Line(String text, Alignment alignment, int fontSize, int lineFeed) {
		Text = text;
		To = alignment;
		FontSize = fontSize;
		LineFeed = lineFeed;
	}

	/**
	 * @return the text
	 */
	public String getText() {
		return Text;
	}

	/**
	 * @param text
	 *            the text to set
	 */
	public void setText(String text) {
		Text = text;
	}

	/**
	 * @return the to
	 */
	public Alignment getTo() {
		return To;
	}

	/**
	 * @param to
	 *            the to to set
	 */
	public void setTo(Alignment to) {
		To = to;
	}

	/**
	 * @return the fontSize
	 */
	public int getFontSize() {
		return FontSize;
	}

	/**
	 * @param fontSize
	 *            the fontSize to set
	 */
	public void setFontSize(int fontSize) {
		FontSize = fontSize;
	}

	/**
	 * @return the lineFeed
	 */
	public int getLineFeed() {
		return LineFeed;
	}

	/**
	 * @param lineFeed
	 *            the lineFeed to set
	 */
	public void setLineFeed(int lineFeed) {
		LineFeed = lineFeed;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
	

}
