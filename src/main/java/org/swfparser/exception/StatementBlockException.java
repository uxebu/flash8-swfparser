/*
 *   StatementBlockException.java
 * 	 @Author Oleg Gorobets
 *   Created: 25.07.2007
 *   CVS-ID: $Id: 
 *************************************************************************/

package org.swfparser.exception;

public class StatementBlockException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6981179340134710341L;
	
	public StatementBlockException(String message) {
		super(message);
	}

	public StatementBlockException(String message, Throwable cause) {
		super(message, cause);
	}

	public StatementBlockException(Throwable cause) {
		super(cause);
	}

//	@Override
//	public String getMessage() {
//		return getCause()!=null ? getCause().getMessage() : getMessage();
//	}
//	
//	@Override
//	public String getLocalizedMessage() {
//		return getCause()!=null ? getCause().getLocalizedMessage() : getMessage();
//	}
	
}
