/*
 * JSwiff is an open source Java API for Macromedia Flash file generation
 * and manipulation
 *
 * Copyright (C) 2004-2005 Ralf Terdic (contact@jswiff.com)
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */

package com.jswiff.swfrecords.actions;

import com.jswiff.io.InputBitStream;
import com.jswiff.io.OutputBitStream;

import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * <p>
 * Pushes one or more values to the stack. Add <code>Push.StackValue</code>
 * instances using <code>addValue()</code>.
 * </p>
 * 
 * <p>
 * Performed stack operations: addition of one or more values to stack.
 * </p>
 * 
 * <p>
 * ActionScript equivalent: none (used internally, e.g. for parameter passing).
 * </p>
 * 
 * @since SWF 4
 */
public final class Push extends Action {
	private List values = new ArrayList();

	/**
	 * Creates a new Push action.
	 */
	public Push() {
		code = ActionConstants.PUSH;
	}

	/*
	 * Reads a Push action from a bit stream.
	 */
	Push(InputBitStream stream) throws IOException {
		code = ActionConstants.PUSH;
		while (stream.available() > 0) {
			StackValue value = new StackValue(stream);
			values.add(value);
		}
	}

	/**
	 * Returns the size of this action record in bytes.
	 * 
	 * @return size of this record
	 * 
	 * @see Action#getSize()
	 */
	public int getSize() {
		int size = 3;
		for (int i = 0; i < values.size(); i++) {
			size += ((StackValue) values.get(i)).getSize();
		}
		return size;
	}

	/**
	 * Returns a list of values this action is supposed to push to the stack.
	 * Use this list in a read-only manner.
	 * 
	 * @return a list of Push.StackValue instances
	 */
	public List getValues() {
		return values;
	}

	/**
	 * Adds a value to be pushed to the stack.
	 * 
	 * @param value
	 *            a <code>StackValue</code> instance
	 */
	public void addValue(StackValue value) {
		values.add(value);
	}

	/**
	 * Returns a short description of the action.
	 * 
	 * @return <code>"Push"</code>
	 */
	public String toString() {
		return "Push";
	}

	protected void writeData(OutputBitStream dataStream, OutputBitStream mainStream) throws IOException {
		for (int i = 0; i < values.size(); i++) {
			((StackValue) values.get(i)).write(dataStream);
		}
	}
}
