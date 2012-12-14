/*
 *   Priority.java
 * 	 @Author Oleg Gorobets
 *   Created: Jul 31, 2007
 *   CVS-ID: $Id: 
 *************************************************************************/

package org.swfparser;

public class Priority {
	
	public static final int HIGHEST 		= 0;
	
	public static final int CALL_FUNCTION 	= 1;
	public static final int UNARY 			= 2; // !, -, --, ++
	public static final int BITWISE 		= 3;
	public static final int ARITHMETIC 		= 4; // *, /, %
	public static final int PLUS_MINUS 		= 5; // +, -
	public static final int BOOLEAN 		= 6; // ==, >, <
	public static final int AND 			= 7; // &&
	public static final int OR 				= 8; // ||
	
	
	public static final int LOWEST 			= Integer.MAX_VALUE;
	
	
	
}
