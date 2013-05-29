/*
 *   NameResolver.java
 * 	 @Author Oleg Gorobets
 *   Created: Jul 31, 2007
 *   CVS-ID: $Id: 
 *************************************************************************/

package org.swfparser;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

public class NameResolver {
	
	private static Logger logger = Logger.getLogger(NameResolver.class);
	
	private static Pattern variablePattern = Pattern.compile("[a-z_][a-z0-9_]*", Pattern.CASE_INSENSITIVE);
	private static Map<String,String> variableMapping = new HashMap<String, String>();
	
	public static String getVariableName(String name) {
		if (variableMapping.containsKey(name)) {
			return variableMapping.get(name);
		} else {
			Matcher matcher = variablePattern.matcher(name);
			if (matcher.matches()) {
				return name;
			} else {
				String newName = getModifiedVarName(name);
				variableMapping.put(name, newName);
				return newName;
			}
		}
	}
	
	private static int varIndex = 1;
	
	protected static String getModifiedVarName(String var) {
		// TODO: build new name on the base of var
		return "__var_"+(varIndex++);
	}
	
	public static void main(String[] args) {
		String[] names = new String[] {"","x25","25x","2x","_25x","$x","a","a b","a_b","a_b$"};
		for (String name : names) {
			logger.debug(name+" => "+getVariableName(name));
		}
	}
}
