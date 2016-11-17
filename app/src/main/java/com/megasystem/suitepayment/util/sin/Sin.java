package com.megasystem.suitepayment.util.sin;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class Sin implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6160066131483529165L;

	private static String numeroAutorizacion;
	private static String numeroFactura;
	private static String nit;
	private static String fecha;
	private static String montoTotal;
	private static String llave;

	public static String getCode(String numeroAutorizacionIn, String numeroFacturaIn, String nitIn, Date fechaIn, double montoTotalIn, String llaveIn) throws Exception {
		if(nit==null||nitIn.trim().equals("")){
			nitIn="0";
		}else{
			if ((nitIn.startsWith("0")) && (nitIn.trim().length() != 1) ) {
				throw new Exception("Nit invalido [" + nitIn + "]");
			}
		}
		
		long sumatoria;
		String strSumatoria = "";
		String digitosVerhoeff = "";

		numeroAutorizacion = numeroAutorizacionIn.trim();
		numeroFactura = numeroFacturaIn.trim();
		nit = nitIn.trim();
		fecha = new SimpleDateFormat("yyyyMMdd",Locale.US).format(fechaIn);
		if (montoTotalIn % Math.round(montoTotalIn) >= 0.5) {
			montoTotal = Double.toString(Math.ceil(montoTotalIn));
		} else {
			montoTotal = Double.toString(Math.floor(montoTotalIn));
		}
		montoTotal = montoTotal.replace(".0", "");
		llave = llaveIn.trim();
		numeroFactura = numeroFactura + getVerhoeffDigits(numeroFactura, 2);
		nit = nit + getVerhoeffDigits(nit, 2);
		fecha = fecha + getVerhoeffDigits(fecha, 2);
		montoTotal = montoTotal + getVerhoeffDigits(montoTotal, 2);
		sumatoria = Long.parseLong(numeroFactura) + Long.parseLong(nit) + Long.parseLong(fecha) + Long.parseLong(montoTotal);
		strSumatoria = Long.toString(sumatoria);
		digitosVerhoeff = getVerhoeffDigits(strSumatoria, 5);
		strSumatoria = strSumatoria + digitosVerhoeff;

		String aux = "";
		String llaveAux = "";
		llaveAux = llaveIn.trim();
		// numeroAutorizacion
		aux = llaveAux.substring(0, Integer.parseInt(Character.toString(digitosVerhoeff.charAt(0))) + 1);
		numeroAutorizacion += aux;
		llaveAux = llaveAux.substring(Integer.parseInt(Character.toString(digitosVerhoeff.charAt(0))) + 1);
		// numeroFactura
		aux = llaveAux.substring(0, Integer.parseInt(Character.toString(digitosVerhoeff.charAt(1))) + 1);
		numeroFactura += aux;
		llaveAux = llaveAux.substring(Integer.parseInt(Character.toString(digitosVerhoeff.charAt(1))) + 1);
		// nit
		aux = llaveAux.substring(0, Integer.parseInt(Character.toString(digitosVerhoeff.charAt(2))) + 1);
		nit += aux;
		llaveAux = llaveAux.substring(Integer.parseInt(Character.toString(digitosVerhoeff.charAt(2))) + 1);
		// fecha
		aux = llaveAux.substring(0, Integer.parseInt(Character.toString(digitosVerhoeff.charAt(3))) + 1);
		fecha += aux;
		llaveAux = llaveAux.substring(Integer.parseInt(Character.toString(digitosVerhoeff.charAt(3))) + 1);
		// montoTotal
		aux = llaveAux.substring(0, Integer.parseInt(Character.toString(digitosVerhoeff.charAt(4))) + 1);
		montoTotal += aux;


		String allegedRC4 = AllegedRc4.cifrarMensajeRC4(numeroAutorizacion + numeroFactura + nit + fecha + montoTotal, llave + digitosVerhoeff).replace("-", "");


		long[] vecTotales = new long[5];
		long sumTotal = 0;
		long sumParcial = 0;
		int j = 0;
		for (int i = 0; i < 5; i++) {
			j = i;
			sumParcial = 0;
			while (j < allegedRC4.length()) {
				sumParcial += allegedRC4.charAt(j);
				j += 5;
			}
			sumTotal += sumParcial;
			vecTotales[i] = sumParcial;
		}


		long sumFinal = 0;
		for (int i = 0; i < 5; i++) {
			sumFinal += Math.floor(Math.abs(((sumTotal * vecTotales[i]) / (Integer.parseInt(Character.toString(digitosVerhoeff.charAt(i))) + 1))));
		}

		String strBase64 = Base64.getBase64(sumFinal);

		return AllegedRc4.cifrarMensajeRC4(strBase64, llave + digitosVerhoeff);
	}

	private static String getVerhoeffDigits(String str, int cantidad) {
		String result = "";
		for (int i = 0; i < cantidad; i++) {
			result = result + Verhoeff.getNumber(str + result);
		}
		return result;
	}

}
