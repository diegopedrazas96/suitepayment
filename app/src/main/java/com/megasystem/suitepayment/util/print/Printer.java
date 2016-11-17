package com.megasystem.suitepayment.util.print;

import android.content.SharedPreferences;

public interface Printer {
	public boolean getStatus();
	public void setCharactersPerLine(int charactersPerLine);

	public boolean connectPrinter();

	public boolean disconnectPrinter();

	public boolean print(String textToPrint, Line.Alignment alignment, int fontSize, int linefeed, int type);
	
	public void setSharedPreferences(SharedPreferences preference);

	public boolean printQR(String datos);
	
	public boolean printSeparator();
	
}