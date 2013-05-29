/*
 *   UnsignedByte.java
 * 	 @Author Oleg Gorobets
 *   Created: 19.07.2007
 *   CVS-ID: $Id: 
 *************************************************************************/

package org.swfparser.util;
public class UnsignedByte {
	public static void main(String args[]) {
		byte b1 = 127;
		byte b2 = -128;
		byte b3 = -1;

		System.out.println(b1);
		System.out.println(b2);
		System.out.println(b3);
		System.out.println(unsignedByteToInt(b1));
		System.out.println(unsignedByteToInt(b2));
		System.out.println(unsignedByteToInt(b3));
		/*
		 * 127 -128 -1 127 128 255
		 */
		
		printBinary(0x10);
		printBinary(0x11);
		printBinary(0xA);
		printBinary(0xB);
	}

	public static void printBinary(int i) {
		System.out.println(binary(i));
		
	}

	public static int unsignedByteToInt(byte b) {
		return (int) b & 0xFF;
	}
	
	public static String binary(int byteValue) {
		String byteValueString="";
		for (int j=0;j<=7;j++) {
			int mask = 1<<j;
			if ((mask & byteValue) > 0) {
				byteValueString = "1"+byteValueString;
			} else {
				byteValueString = "0"+byteValueString;
			}
		}
//		System.out.println(byteValueString);
		return byteValueString;
	}
	
	private static PrintfFormat hexFormat = new PrintfFormat("0x%08x");
	
	public static String hex(long byteValue) {
//		return Integer.toHexString(byteValue);
		return hexFormat.sprintf(byteValue);
	}
	
	private static PrintfFormat byteF = new PrintfFormat("%02X");
	
	public static String dump(byte[] bytes) {
		return dump(bytes,0);
	}
	
	public static String dump(byte[] bytes, int offset) {
		
	    StringBuffer buf = new StringBuffer();
	    for (int j=offset;j<bytes.length;j++) {
	    	buf.append(byteF.sprintf(UnsignedByte.unsignedByteToInt(bytes[j])));
	    }
	    return buf.toString();
	}
	
	
}
