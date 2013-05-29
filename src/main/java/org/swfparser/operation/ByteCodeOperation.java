/*
 *   ByteCodeOperation.java
 * 	 @Author Oleg Gorobets
 *   Created: 11.09.2007
 *   CVS-ID: $Id: 
 *************************************************************************/

package org.swfparser.operation;

import java.util.Collections;
import java.util.List;

import org.swfparser.Operation;
import org.swfparser.Priority;
import org.swfparser.util.PrintfFormat;

public class ByteCodeOperation implements Operation {

	private static PrintfFormat byteFormat = new PrintfFormat("%02x");
	
	private byte[] data;
	
	public ByteCodeOperation(byte[] data) {
		super();
		this.data = data;
	}

	public int getArgsNumber() {
		return 0;
	}

	public List<Operation> getOperations() {
		return Collections.EMPTY_LIST;
	}

	public int getPriority() {
		return Priority.CALL_FUNCTION;
	}

	public String getStringValue(int level) {
		StringBuffer buf = new StringBuffer()
		.append("__bytecode__(\"");
		
		for (byte b : data) {
			String hexValue = byteFormat.sprintf(b);
			if (hexValue.length()>2) {
				hexValue = hexValue.substring(hexValue.length()-2);
			}
			buf.append(hexValue);
		}
		
		buf
		.append("\")");
		
		return buf.toString();
	}
	
	@Override
	public String toString() {
		return "ByteCodeOperation("+data.length+")";
	}
	
//	public static void main(String[] args) {
//		byte[] data = new byte[] {0x21, 1,-1,-2,-128, 0x5f};
//		ByteCodeOperation byteCodeOperation = new ByteCodeOperation(data);
//		System.out.println(byteCodeOperation.getStringValue(0));
//	}

}
