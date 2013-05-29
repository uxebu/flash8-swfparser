/*
 *   ActionBlockReader.java
 * 	 @Author Oleg Gorobets
 *   Created: 24.07.2007
 *   CVS-ID: $Id: 
 *************************************************************************/

package com.jswiff.swfrecords.actions;

import java.io.IOException;
import java.util.List;

import com.jswiff.io.InputBitStream;
import com.jswiff.io.OutputBitStream;

public interface ActionBlockReader {

	public void read(InputBitStream stream) throws IOException;

	/**
	 * Returns a list of the contained action records. Warning: use this list in
	 * a read-only manner!
	 *
	 * @return contained actions in a list
	 */
	public List getActions();

	/**
	 * Returns the size of the action block in bytes, i.e. the sum of the size of
	 * the contained action records.
	 *
	 * @return size of block in bytes
	 */
	public int getSize();

	/**
	 * Adds an action record to this action block.
	 *
	 * @param action an action record
	 */
	public void addAction(Action action);

	/**
	 * Removes the specified action record from the action block.
	 *
	 * @param action action record to be removed
	 *
	 * @return <code>true</code> if action block contained the specified action
	 *         record
	 */
	public boolean removeAction(Action action);

	/**
	 * Removes the action record at the specified position within the block.
	 *
	 * @param index index of the action record to be removed
	 *
	 * @return the action record previously contained at specified position
	 */
	public Action removeAction(int index);

	/**
	 * Writes the action block to a bit stream.
	 *
	 * @param stream the target bit stream
	 * @param writeEndAction if <code>true</code>, an END action is written at
	 *        the end of the block
	 *
	 * @throws IOException if an I/O error has occured
	 */
	public void write(OutputBitStream stream, boolean writeEndAction) throws IOException;
	
	/**
	 * @param skipGarbage
	 */
	public void setSkipGarbage(boolean skipGarbage);
	
	/**
	 * @return
	 */
	public byte[] getData();

}