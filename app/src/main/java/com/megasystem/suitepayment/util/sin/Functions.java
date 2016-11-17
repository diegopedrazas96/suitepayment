package com.megasystem.suitepayment.util.sin;

import java.io.Serializable;
import java.util.Locale;

public class Functions implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9213734011072639930L;

	/**
	 * 
	 * Redondeo de numeros
	 * 
	 * @param double Rval
	 * @param int Rpl
	 * @return double
	 */
	public static double Round(double Rval, int Rpl) {
		double p = (double) Math.pow(10, Rpl);
		Rval = Rval * p;
		double tmp = Math.round(Rval);
		return (double) tmp / p;
	}

	/**
	 * 
	 * Retorna el monto en formato literal de un numero con punto flotante
	 * 
	 * @param String
	 *            fltNumber
	 * @return String
	 */
	public static String AmountToLiteral(double fltNumber) {
		String strMontoLiteral = "";
		double fltMontoFraccion = 0d;
		if (fltNumber > 0) {
			if (fltNumber > 1) {
				strMontoLiteral = NumberToLiteral(fltNumber);
				fltMontoFraccion = Round(fltNumber - (long) (fltNumber), 2);
				if (fltMontoFraccion > 0d) {
					fltMontoFraccion = (double) ((long) (fltMontoFraccion * 100));
					strMontoLiteral = strMontoLiteral.substring(0, 1).toUpperCase(Locale.getDefault()) + strMontoLiteral.substring(1);
					strMontoLiteral += " " + Long.toString((long) fltMontoFraccion) + "/100 Bolivianos";
				} else {
					if (!(strMontoLiteral.substring(0, 1).toUpperCase(Locale.getDefault()).equals("0"))) {
						strMontoLiteral = strMontoLiteral.substring(0, 1).toUpperCase(Locale.getDefault()) + strMontoLiteral.substring(1);
						strMontoLiteral += " 00/100 Bolivianos";
					} else {
						strMontoLiteral = "0";
					}
				}
			} else {
				strMontoLiteral = Integer.toString((int) (fltNumber * 100)) + "/100 Bolivianos";
			}
		}
		return strMontoLiteral;
	}

	/**
	 * 
	 * Convierte Numeros en literales
	 * 
	 * @param String
	 *            fltNumber
	 * @return String
	 */
	private static String NumberToLiteral(double fltNumber) {
		StringBuilder strLiteral = new StringBuilder();
		String[] strUnidades = { "un", "dos", "tres", "cuatro", "cinco", "seis", "siete", "ocho", "nueve", "diez", "once", "doce", "trece", "catorce", "quince" };
		String[] strDecenas = { "dieci", "veint", "treinta", "cuarenta", "cincuenta", "sesenta", "setenta", "ochenta", "noventa" };
		String[] strCentenas = { "ciento ", "doscientos ", "trecientos ", "cuatrocientos ", "quinientos ", "seiscientos ", "setecientos ", "ochocientos ", "novecientos " };
		String[] strMiles = { " mil ", " millones " };
		int intMontoentero = (int) fltNumber;
		long lngParcial = 0;
		String strComodin = "";
		while (intMontoentero > 0) {
			if (intMontoentero >= 1000000) {
				lngParcial = (long) (intMontoentero / 1000000);
				intMontoentero = intMontoentero % 1000000;
				if (lngParcial == 1) {
					strLiteral.append("un millon");
				} else {
					strLiteral.append(NumberToLiteral(lngParcial) + strMiles[1]);
				}
			} else if (intMontoentero >= 1000) {
				lngParcial = (long) (intMontoentero / 1000);
				intMontoentero = intMontoentero % 1000;
				if (lngParcial == 1) {
					strLiteral.append("un " + strMiles[0]);
				} else {
					strLiteral.append(NumberToLiteral(lngParcial) + strMiles[0]);
				}
			} else if (intMontoentero >= 100) {
				lngParcial = (long) (intMontoentero / 100);
				intMontoentero = intMontoentero % 100;
				if ((lngParcial == 1) && (intMontoentero == 0)) {
					strLiteral.append("cien");
				} else {
					strLiteral.append(strCentenas[(int) (lngParcial - 1)]);
				}
			} else if (intMontoentero > 15) {
				if ((intMontoentero >= 20) && (intMontoentero < 30)) {
					strComodin = (intMontoentero % 10 == 0 ? "e" : "i");
				} else if ((intMontoentero % 10 != 0) && (intMontoentero > 30)) {
					strComodin = " y ";
				} else {
					strComodin = "";
				}
				lngParcial = (long) (intMontoentero / 10);
				intMontoentero = intMontoentero % 10;
				strLiteral.append(strDecenas[(int) (lngParcial - 1)] + strComodin);
			} else {
				strLiteral.append(strUnidades[(int) (intMontoentero - 1)]);
				intMontoentero = 0;
			}
		}
		return strLiteral.toString();
	}
}
