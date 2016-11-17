package com.megasystem.suitepayment.util.print;

import android.content.SharedPreferences;
import android.util.Log;

import java.io.Serializable;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;

public class Report implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1207470620967753652L;

	private List<Line> lstLines = new ArrayList<Line>();
	private String dataCodeQr="";
	private String separator = "-";
	private int charactersPerLine = 42;
	private SharedPreferences preferences = null;
	private Printer printer;

	public Report(Printer printer) {
		this.createSeparator();
		this.printer=printer;
	}

	public Report(int charactersPerLine,Printer printer) {
		this.charactersPerLine = charactersPerLine;
		this.createSeparator();
		this.printer=printer;
	}

	public Report(SharedPreferences preferences,Printer printer) {
		this.preferences = preferences;
		this.createSeparator();
		this.printer=printer;
	}

	public Report(int charactersPerLine, SharedPreferences preferences) {
		this.charactersPerLine = charactersPerLine;
		this.preferences = preferences;
		this.createSeparator();
	}
	public Report(int charactersPerLine, SharedPreferences preferences,Printer printer) {
		this.charactersPerLine = charactersPerLine;
		this.preferences = preferences;
		this.printer=printer;
		this.printer.setCharactersPerLine(charactersPerLine);
	}

	private void createSeparator() {
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < this.charactersPerLine; i++) {
			buffer.append(this.separator);
		}
		this.separator = buffer.toString();
	}

	public void addSeparator() {
		lstLines.add(new Line("", Line.Alignment.Center,0,1,2));
	}
	public void addBeginParagraph(){
		lstLines.add(new Line("! U1 SETLP 0 2.9 20\r\n! U1 SETSP 3.5\r\n", Line.Alignment.None,0));
	}
	
	public void addSeparator(String separator) {
		this.separator = this.separator.replace(this.separator.substring(0, 1), separator);
		lstLines.add(new Line(this.separator, Line.Alignment.Center));
	}

	public void addLine(String line) {
		lstLines.add(new Line(normalizerText(line), Line.Alignment.Left));
	}

	public void addLine(String line, Line.Alignment to) {
		lstLines.add(new Line(normalizerText(line), to));
	}

	public void addLine(String line, Line.Alignment to, int lineFeed) {

		lstLines.add(new Line(normalizerText(line), to, lineFeed));
	}
	

	public String getDataCodeQr() {
		return dataCodeQr;
	}

	public void addcodeQr(String dataCodeQr) {
		lstLines.add(new Line(dataCodeQr, Line.Alignment.Center,1,0,1));
	}

	public boolean print() {
		System.gc();
		printer.setSharedPreferences(this.preferences);

		if (printer.getStatus()) {
			String imp="";
			for (Line line : lstLines) {
				printer.print(" "+line.getText(), line.getTo(), line.getFontSize(), line.getLineFeed(),line.getType());
				imp+=line.getText();
			}
			Log.i("Impreso", imp);
			lstLines.clear();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			printer.disconnectPrinter();
			
			System.gc();
			return true;
		}
		System.gc();
		return false;
	}
	private String normalizerText(String value){
		return Normalizer.normalize(value, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
	}
	
	
}
