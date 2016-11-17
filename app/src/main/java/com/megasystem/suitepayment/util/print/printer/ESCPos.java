/**
 * ESC/POS library for communicating with thermal printers
 *
 * ##copyright##
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General
 * Public License along with this library; if not, write to the
 * Free Software Foundation, Inc., 59 Temple Place, Suite 330,
 * Boston, MA  02111-1307  USA
 * 
 * @author		##author##
 * @modified	##date##
 * @version		##version##
 */


package com.megasystem.suitepayment.util.print.printer;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.SharedPreferences;
import android.util.Log;
import com.megasystem.suitepayment.util.print.BluetoothConnector;
import com.megasystem.suitepayment.util.print.Line;
import com.megasystem.suitepayment.util.print.Printer;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;

public class ESCPos implements Printer, Serializable {




	private static final long serialVersionUID = 5632422199896367954L;

	 Activity activity;
	private int charactersPerLine;
	private SharedPreferences preferences = null;
	private String separator = "-";

	// android built in classes for bluetooth operations
	BluetoothAdapter mBluetoothAdapter;
//	BluetoothSocket mmSocket;
	BluetoothConnector.BluetoothSocketWrapper mmSocket;
	BluetoothDevice mmDevice;

	OutputStream mmOutputStream;
	Thread workerThread;

	byte[] readBuffer;
	int readBufferPosition;
	int counter;
	volatile boolean stopWorker;
	

	public ESCPos(Activity activity) {
		super();
		this.activity = activity;
	}
	public ESCPos() {
		super();
	}



/*
	 * 
	 * reusable init esc code
	 * 
	 * @throws IOException
	 */


	public void escInit() throws IOException {
		mmOutputStream.write(0x1B);
		mmOutputStream.write("@".getBytes());
	}


/*
	 * resets all printer settings to default
	 * 
	 * @throws IOException
	 */


	public void resetToDefault() throws IOException {
		setInverse(false);
		setBold(false);
		setUnderline(0);
		setJustification(0);
	}


/*
	 * 
	 * @param str
	 *            String to print
	 * @throws IOException*/


	public void printString(String str) throws IOException {
		// escInit();
		mmOutputStream.write(str.getBytes());
		mmOutputStream.write(0xA);
	}

	public void storeString(String str) throws IOException {
		mmOutputStream.write(str.getBytes());
	}

	public void storeChar(int hex) throws IOException {
		mmOutputStream.write(hex);
	}

	public void printStorage() throws IOException {
		mmOutputStream.write(0xA);
	}


/*
	 * Prints n lines of blank paper.
	 * 
	 * @throws IOException
	 */

	public void feed(int feed) throws IOException {
		// escInit();
		mmOutputStream.write(0x1B);
		mmOutputStream.write("d".getBytes());
		mmOutputStream.write(feed);
	}


/*
	 * Prints a string and outputs n lines of blank paper.
	 * 
	 * @throws IOException
	 */


	public void printAndFeed(String str, int feed) throws IOException {
		// escInit();
		mmOutputStream.write(str.getBytes());
		// output extra paper
		mmOutputStream.write(0x1B);
		mmOutputStream.write("d".getBytes());
		mmOutputStream.write(feed);
	}


/*
	 * Sets bold
	 * 
	 * @throws IOException
	 */

	public void setBold(Boolean bool) throws IOException {
		mmOutputStream.write(0x1B);
		mmOutputStream.write("E".getBytes());
		mmOutputStream.write((int) (bool ? 1 : 0));
	}

/*
	 * Sets Size
	 * 
	 * @throws IOException
	 */

	public void setSize(int size) throws IOException {
		mmOutputStream.write(0x1B);
		mmOutputStream.write("!".getBytes());
		mmOutputStream.write(size);
	}


/*
	 * Sets white on black printing
	 * 
	 * @throws IOException
	 */

	public void setInverse(Boolean bool) throws IOException {
		mmOutputStream.write(0x1D);
		mmOutputStream.write("B".getBytes());
		mmOutputStream.write((int) (bool ? 1 : 0));
	}


/*
	 * Sets underline and weight
	 * 
	 * @param val
	 *            0 = no underline. 1 = single weight underline. 2 = double
	 *            weight underline.
	 * @throws IOException
	 */


	public void setUnderline(int val) throws IOException {
		mmOutputStream.write(0x1B);
		mmOutputStream.write("-".getBytes());
		mmOutputStream.write(val);
	}


/*
	 * Sets left, center, right justification
	 * 
	 * @param val
	 *            0 = left justify. 1 = center justify. 2 = right justify.
	 * @throws IOException
	 */


	private void setJustification(int val) throws IOException {
		mmOutputStream.write(0x1B);
		mmOutputStream.write("a".getBytes());
		mmOutputStream.write(val);
	}


/*
	 * Encode and print QR code
	 * 
	 * @param str
	 *            String to be encoded in QR.
	 *
	 *            The size of the QR module (pixel) in dots. The QR code will
	 *            not print if it is too big. Try setting this low and
	 *            experiment in making it larger.
	 * @throws IOException
*/

	private void printQREscPos(String str)
			throws IOException {
		// save data function 80
		mmOutputStream.write(0x1D);// init
		mmOutputStream.write("(k".getBytes());// adjust height of barcode
		mmOutputStream.write(str.length() + 3); // pl
		mmOutputStream.write(0); // ph
		mmOutputStream.write(49); // cn
		mmOutputStream.write(80); // fn
		mmOutputStream.write(48); //
		mmOutputStream.write(str.getBytes());

		// error correction function 69
		mmOutputStream.write(0x1D);
		mmOutputStream.write("(k".getBytes());
		mmOutputStream.write(3); // pl
		mmOutputStream.write(0); // ph
		mmOutputStream.write(49); // cn
		mmOutputStream.write(69); // fn
		mmOutputStream.write(49); // 48<= n <= 51

		// size function 67
		mmOutputStream.write(0x1D);
		mmOutputStream.write("(k".getBytes());
		mmOutputStream.write(3);
		mmOutputStream.write(0);
		mmOutputStream.write(49);
		mmOutputStream.write(67);
		mmOutputStream.write(5);// 1<= n <= 16

		// print function 81
		mmOutputStream.write(0x1D);
		mmOutputStream.write("(k".getBytes());
		mmOutputStream.write(3); // pl
		mmOutputStream.write(0); // ph
		mmOutputStream.write(49); // cn
		mmOutputStream.write(81); // fn
		mmOutputStream.write(48); // m
	}


/*
	 * Encode and print barcode
	 * 
	 * @param code
	 *            String to be encoded in the barcode. Different barcodes have
	 *            different requirements on the length of data that can be
	 *            encoded.
	 * @param type
	 *            Specify the type of barcode 65 = UPC-A. 66 = UPC-E. 67 =
	 *            JAN13(EAN). 68 = JAN8(EAN). 69 = CODE39. 70 = ITF. 71 =
	 *            CODABAR. 72 = CODE93. 73 = CODE128.
	 * 
	 * @param h
	 *            height of the barcode in points (1 <= n <= 255)
	 * @param w
	 *            width of module (2 <= n <=6). Barcode will not print if this
	 *            value is too large.
	 * @param font
	 *            Set font of HRI characters 0 = font A 1 = font B
	 * @param pos
	 *            set position of HRI characters 0 = not printed. 1 = Above
	 *            barcode. 2 = Below barcode. 3 = Both above and below barcode.
	 * @throws IOException*/


	public void printBarcode(String code, int type, int h, int w, int font,
			int pos) throws IOException {

		// need to test for errors in length of code
		// also control for input type=0-6

		// GS H = HRI position
		mmOutputStream.write(0x1D);
		mmOutputStream.write("H".getBytes());
		mmOutputStream.write(pos); // 0=no print, 1=above, 2=below, 3=above &
									// below

		// GS f = set barcode characters
		mmOutputStream.write(0x1D);
		mmOutputStream.write("f".getBytes());
		mmOutputStream.write(font);

		// GS h = sets barcode height
		mmOutputStream.write(0x1D);
		mmOutputStream.write("h".getBytes());
		mmOutputStream.write(h);

		// GS w = sets barcode width
		mmOutputStream.write(0x1D);
		mmOutputStream.write("w".getBytes());
		mmOutputStream.write(w);// module = 1-6

		// GS k
		mmOutputStream.write(0x1D); // GS
		mmOutputStream.write("k".getBytes()); // k
		mmOutputStream.write(type);// m = barcode type 0-6
		mmOutputStream.write(code.length()); // length of encoded string
		mmOutputStream.write(code.getBytes());// d1-dk
		mmOutputStream.write(0);// print barcode
	}


/*
	 * Encode and print PDF 417 barcode
	 * 
	 * @param code
	 *            String to be encoded in the barcode. Different barcodes have
	 *            different requirements on the length of data that can be
	 *            encoded.
	 * @param type
	 *            Specify the type of barcode 0 - Standard PDF417 1 - Standard
	 *            PDF417
	 * 
	 * @param h
	 *            Height of the vertical module in dots 2 <= n <= 8.
	 * @param w
	 *            Height of the horizontal module in dots 1 <= n <= 4.
	 * @param cols
	 *            Number of columns 0 <= n <= 30.
	 * @param rows
	 *            Number of rows 0 (automatic), 3 <= n <= 90.
	 * @param error
	 *            set error correction level 48 <= n <= 56 (0 - 8).
	 * @throws IOException
	 */


	public void printPSDCode(String code, int type, int h, int w, int cols,
			int rows, int error) throws IOException {

		// print function 82
		mmOutputStream.write(0x1D);
		mmOutputStream.write("(k".getBytes());
		mmOutputStream.write(code.length()); // pl Code length
		mmOutputStream.write(0); // ph
		mmOutputStream.write(48); // cn
		mmOutputStream.write(80); // fn
		mmOutputStream.write(48); // m
		mmOutputStream.write(code.getBytes()); // data to be encoded

		// function 65 specifies the number of columns
		mmOutputStream.write(0x1D);// init
		mmOutputStream.write("(k".getBytes());// adjust height of barcode
		mmOutputStream.write(3); // pl
		mmOutputStream.write(0); // pH
		mmOutputStream.write(48); // cn
		mmOutputStream.write(65); // fn
		mmOutputStream.write(cols);

		// function 66 number of rows
		mmOutputStream.write(0x1D);// init
		mmOutputStream.write("(k".getBytes());// adjust height of barcode
		mmOutputStream.write(3); // pl
		mmOutputStream.write(0); // pH
		mmOutputStream.write(48); // cn
		mmOutputStream.write(66); // fn
		mmOutputStream.write(rows); // num rows

		// module width function 67
		mmOutputStream.write(0x1D);
		mmOutputStream.write("(k".getBytes());
		mmOutputStream.write(3);// pL
		mmOutputStream.write(0);// pH
		mmOutputStream.write(48);// cn
		mmOutputStream.write(67);// fn
		mmOutputStream.write(w);// size of module 1<= n <= 4

		// module height fx 68
		mmOutputStream.write(0x1D);
		mmOutputStream.write("(k".getBytes());
		mmOutputStream.write(3);// pL
		mmOutputStream.write(0);// pH
		mmOutputStream.write(48);// cn
		mmOutputStream.write(68);// fn
		mmOutputStream.write(h);// size of module 2 <= n <= 8

		// error correction function 69
		mmOutputStream.write(0x1D);
		mmOutputStream.write("(k".getBytes());
		mmOutputStream.write(4);// pL
		mmOutputStream.write(0);// pH
		mmOutputStream.write(48);// cn
		mmOutputStream.write(69);// fn
		mmOutputStream.write(48);// m
		mmOutputStream.write(error);// error correction

		// choose pdf417 type function 70
		mmOutputStream.write(0x1D);
		mmOutputStream.write("(k".getBytes());
		mmOutputStream.write(3);// pL
		mmOutputStream.write(0);// pH
		mmOutputStream.write(48);// cn
		mmOutputStream.write(70);// fn
		mmOutputStream.write(type);// set mode of pdf 0 or 1

		// print function 81
		mmOutputStream.write(0x1D);
		mmOutputStream.write("(k".getBytes());
		mmOutputStream.write(3); // pl
		mmOutputStream.write(0); // ph
		mmOutputStream.write(48); // cn
		mmOutputStream.write(81); // fn
		mmOutputStream.write(48); // m

	}


/*
	 * Store custom character input array of column bytes
	 * 
	 * @param columnArray
	 *            Array of bytes (0-255). Ideally not longer than 24 bytes.
	 * 
	 * @param mode
	 *            0 - 8-dot single-density. 1 - 8-dot double-density. 32 -
	 *            24-dot single density. 33 - 24-dot double density.
	 * @throws IOException*/


	public void storeCustomChar(int[] columnArray, int mode) throws IOException {

		// function GS*
		mmOutputStream.write(0x1B);
		mmOutputStream.write("*".getBytes());
		mmOutputStream.write(mode);
		mmOutputStream.write((mode == 0 || mode == 1) ? columnArray.length
				: columnArray.length / 3);// number of cols
		mmOutputStream.write(0);
		for (int i = 0; i < columnArray.length; i++) {
			mmOutputStream.write(columnArray[i]);
		}

	}


/*
	 * Store custom character input array of column bytes. NOT WORKING
	 * 
	 * @param spacing
	 *            Integer representing Vertical motion of unit in inches. 0-255
	 * @throws IOException
	 */


	public void setLineSpacing(int spacing) throws IOException {

		// function ESC 3
		mmOutputStream.write(0x1B);
		mmOutputStream.write("3".getBytes());
		mmOutputStream.write(spacing);

	}

	private void cut() throws IOException {
		mmOutputStream.write(0x1D);
		mmOutputStream.write("V".getBytes());
		mmOutputStream.write(48);
		mmOutputStream.write(0);
	}

	public void feedAndCut(int feed) throws IOException {

		feed(feed);
		cut();
	}

	public void beep() throws IOException {
		mmOutputStream.write(0x1B);
		mmOutputStream.write("(A".getBytes());
		mmOutputStream.write(4);
		mmOutputStream.write(0);
		mmOutputStream.write(48);
		mmOutputStream.write(55);
		mmOutputStream.write(3);
		mmOutputStream.write(15);
	}

	// This will find a bluetooth printer device
	private void findBT(String macAddress) {

			mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

			if (mBluetoothAdapter == null) {
				Log.i("ESCPos","No bluetooth adapter available");
			}

			if (!mBluetoothAdapter.isEnabled()) {
				// Intent enableBluetooth = new
				// Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
				// context.constartActivityForResult(enableBluetooth, 0);
			}

			mmDevice = mBluetoothAdapter.getRemoteDevice(macAddress);
			
		
	}

	// Tries to open a connection to the bluetooth printer device
	private void openBT() throws IOException {
		try {
			BluetoothConnector bc =new BluetoothConnector(mmDevice, false, mBluetoothAdapter, null);
			mmSocket=bc.connect();
			// Standard SerialPortService ID
//			UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");
//			mmSocket = mmDevice.createRfcommSocketToServiceRecord(uuid);
//			mmSocket.connect();
//			Thread.sleep(1000);
			System.out.println("Print connect: "+ mmSocket);
			if(mmSocket==null||!mmSocket.isConnected()){
				return;
			}
			mmOutputStream = mmSocket.getOutputStream();
//			mmInputStream = mmSocket.getInputStream();
			
			
			Log.i("ESCPos","Bluetooth Opened");
		} catch (NullPointerException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Close the connection to bluetooth printer.
	private void closeBT() throws IOException {
		try {
			stopWorker = true;
			mmOutputStream.close();
//			mmInputStream.close();
			mmSocket.close();
			Log.i("ESCPos","Bluetooth Closed");
		} catch (NullPointerException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private void createSeparator() {
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < this.charactersPerLine; i++) {
			buffer.append(this.separator);
		}
		this.separator = buffer.toString();
	}

	@Override
	public boolean getStatus() {
		return (mmSocket==null)?false:mmSocket.isConnected();
	}

	@Override
	public void setCharactersPerLine(int charactersPerLine) {
		this.charactersPerLine=charactersPerLine;
		createSeparator();
	}

	@Override
	public boolean connectPrinter() {
			try {
				findBT(preferences.getString("printerMAC", "00:19:01:27:DD:13"));
				openBT();
				return true;
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
			
			
	}

	@Override
	public boolean disconnectPrinter() {
		try {
			escInit();
			closeBT();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean print(String textToPrint, Line.Alignment alignment, int fontSize,
			int linefeed, int type) {
		try {
			setSize(1);
			int justification=0;
			if(alignment== Line.Alignment.Center){
				justification=1;
			}
			if(alignment==Line.Alignment.Right){
				justification=2;
			}
			if(type==1){
				printQR(textToPrint);
				return true;
			}
			if(type==2){
				printSeparator();
				return true;
			}
//			int charactersPerLine = this.charactersPerLine;
			
//

//			 * Saltos de linea para texto con mayor largo
//

//			if (textToPrint.length() > charactersPerLine) {
//				int lines = textToPrint.length() / charactersPerLine;
//				lines = (textToPrint.length() % charactersPerLine > 0 ? lines + 1 : lines);
//				charactersPerLine = lines * charactersPerLine;
//			}
			setJustification(justification);
			printString(textToPrint);
			if (linefeed > 0) {
				while (linefeed > 0) {
					feed(1);
					linefeed--;
				}
			}
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public void setSharedPreferences(SharedPreferences preference) {
		this.preferences=preference;

	}

	@Override
	public boolean printQR(String datos) {
		try {
			printQREscPos(datos);
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return false;
	}

	@Override
	public boolean printSeparator() {
		try {
			printString(this.separator);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

}

