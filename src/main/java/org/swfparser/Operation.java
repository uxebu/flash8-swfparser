/*
 *   Operation.java
 * 	 @Author Oleg Gorobets
 *   Created: 24.07.2007
 *   CVS-ID: $Id: 
 *************************************************************************/

package org.swfparser;

import java.util.List;

/*
 * Deprecated Function summary
 * ===========================
 * 
	Modifiers	
	Function Name	
	Description
	
	 	
	call(frame:Object)	
	Deprecated since Flash Player 5. This action was deprecated in favor of the function statement.
	
	 	
	chr(number:Number)	
	Deprecated since Flash Player 5. This function was deprecated in favor of String.fromCharCode().
	
	 	
	TextFormat.getTextExtent(text:String, [width:Number])	
	Deprecated since Flash Player 8. There is no replacement.
	
	 	
	ifFrameLoaded([scene:String], frame:Object)	
	Deprecated since Flash Player 5. This function has been deprecated. Macromedia recommends that you use the MovieClip._framesloaded property.
	
	 	
	int(value:Number)	
	Deprecated since Flash Player 5. This function was deprecated in favor of Math.round().
	
	 	
	length(expression:String, variable:Object)	
	Deprecated since Flash Player 5. This function, along with all the string functions, has been deprecated. Macromedia recommends that you use the methods of the String class and the String.length property to perform the same operations.
	
	 	
	mbchr(number:Number)	
	Deprecated since Flash Player 5. This function was deprecated in favor of the String.fromCharCode() method.
	
	 	
	mblength(string:String)	
	Deprecated since Flash Player 5. This function was deprecated in favor of the methods and properties of the String class.
	
	 	
	mbord(character:String)	
	Deprecated since Flash Player 5. This function was deprecated in favor of String.charCodeAt().
	
	 	
	mbsubstring(value:String, index:Number, count:Number)	
	Deprecated since Flash Player 5. This function was deprecated in favor of String.substr().
	
	 	
	ord(character:String)	
	Deprecated since Flash Player 5. This function was deprecated in favor of the methods and properties of the String class.
	
	 	
	random(value:Number)	
	Deprecated since Flash Player 5. This function was deprecated in favor of Math.random().
	
	 	
	substring(string:String, index:Number, count:Number)	
	Deprecated since Flash Player 5. This function was deprecated in favor of String.substr().
	
	 	
	tellTarget(target:String, statement(s))	
	Deprecated since Flash Player 5. Macromedia recommends that you use dot (.) notation and the with statement.
	
	 	
	toggleHighQuality()	
	Deprecated since Flash Player 5. This function was deprecated in favor of _quality.
 * 
 * 
 */


public interface Operation {
	
	/**
	 * @return
	 */
	public int getArgsNumber();
	/**
	 * @param level
	 * @return
	 */
	public String getStringValue(int level);
	/**
	 * @return
	 */
	public int getPriority();
	
	/**
	 * @return list of underlying operations
	 */
	public List<Operation> getOperations();

	
	
}
