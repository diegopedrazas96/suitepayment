package com.megasystem.suitepayment.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.DatePicker;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by fcr on 09/12/2015.
 */
public class Util {
    public static boolean isConnectedToWifi(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = null;
        if (connectivityManager != null) {
            networkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        }
        return networkInfo == null ? false : networkInfo.isConnected();
    }

    public static boolean isConnectedToMobile(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = null;
        if (connectivityManager != null) {
            networkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        }
        return networkInfo == null ? false : networkInfo.isConnected();
    }

    public static boolean isConnected(Context context) {
        if (isConnectedToWifi(context)) {
            return true;
        } else {
            return isConnectedToMobile(context);
        }
    }

    public static String padRight(String s, int n) {
        return String.format("%1$-" + n + "s", s);
    }

    public static String padLeft(String s, int n) {
        return String.format("%1$" + n + "s", s);
    }

    public static String padCenter(String s, int n) {
        return padRight(padLeft(s, ((n / 2) + (s.length() / 2))), n);
    }

    /**
     *
     * Convierte Numeros en literales
     *
     * @param  fltNumber
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

    public static double Round(double Rval, int Rpl) {
        double p = (double) Math.pow(10, Rpl);
        Rval = Rval * p;
        double tmp = Math.round(Rval);
        return (double) tmp / p;
    }

    public static int getDecimal(String str) {
        int value = 0;
        if (str != null && !str.isEmpty()) {
            str = str.replace(",", ".");
            if (str.indexOf(".") > 0) {
                str = str.substring(str.indexOf(".") + 1);
            } else {
                str = "0";
            }
            value = Integer.parseInt(str);
        }

        return value;
    }

    public static String formatDouble(double dbl) {
        String doubleString = "";
        DecimalFormatSymbols dfs=DecimalFormatSymbols.getInstance();
        dfs.setDecimalSeparator('.');
        DecimalFormat format = new DecimalFormat("#.00",dfs);
        doubleString = format.format(Math.round(dbl * 100) / 100d);
        if (dbl < 1) {
            doubleString = "0" + doubleString;
        }
        return doubleString;
    }
    public static String formatDoubleWithOuTDecimal(double dbl) {
        String doubleString = "";
        DecimalFormatSymbols dfs=DecimalFormatSymbols.getInstance();
        dfs.setDecimalSeparator('.');
        DecimalFormat format = new DecimalFormat("#",dfs);
        doubleString = format.format(Math.round(dbl * 100) / 100d);
        if (dbl < 1) {
            doubleString = "0" + doubleString;
        }
        return doubleString;
    }
    public static String formatDoubleHundred(double dbl) {
        String doubleString = "";
        DecimalFormat format = new DecimalFormat("###,###.00");
        doubleString = format.format(Math.round(dbl * 100) / 100d);
        if (dbl < 1) {
            doubleString = "0" + doubleString;
        }
        return doubleString;
    }

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
                    strMontoLiteral += " con " + Long.toString((long) fltMontoFraccion) + "/100 ";
                } else {
                    if (!(strMontoLiteral.substring(0, 1).toUpperCase(Locale.getDefault()).equals("0"))) {
                        strMontoLiteral = strMontoLiteral.substring(0, 1).toUpperCase(Locale.getDefault()) + strMontoLiteral.substring(1);
                        strMontoLiteral += " con 00/100 ";
                    } else {
                        strMontoLiteral = "0";
                    }
                }
            } else {
                strMontoLiteral = Integer.toString((int) (fltNumber * 100)) + "/100 Centavos";
            }
        }
        return strMontoLiteral;
    }
    public static Date getDateFromDatePicket(DatePicker datePicker) {
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth();
        int year = datePicker.getYear();

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);

        return calendar.getTime();
    }
    public static double getValue(String str) {
        double value = 0;
        if (str != null && !str.isEmpty()) {
            str = str.replace(",", ".");
            value = Double.parseDouble(str);
        }
        return value;
    }
}
