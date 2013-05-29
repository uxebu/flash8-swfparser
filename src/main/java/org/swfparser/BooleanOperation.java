/*
 *   BooleanOperation.java
 * 	 @Author Oleg Gorobets
 *   Created: 26.07.2007
 *   CVS-ID: $Id: 
 *************************************************************************/

package org.swfparser;

public interface BooleanOperation extends Operation {
//	public String getInvertedStringValue(int level);
	public Operation getInvertedOperation();
}
