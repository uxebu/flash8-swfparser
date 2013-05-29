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
package com.jswiff.swfrecords.tags;

import com.jswiff.io.InputBitStream;
import com.jswiff.io.OutputBitStream;
import com.jswiff.swfrecords.FillStyleArray;
import com.jswiff.swfrecords.LineStyleArray;
import com.jswiff.swfrecords.Rect;
import com.jswiff.swfrecords.ShapeWithStyle;

import java.io.IOException;


/**
 * This tag is used to define shapes, just like <code>DefineShape</code>.
 * Unlike <code>DefineShape</code>, <code>DefineShape2</code> supports more
 * than 255 styles in the line and fill style arrays and supports the
 * definition of new style arrays within contained
 * <code>StyleChangeRecord</code> instances.
 *
 * @see DefineShape
 * @see ShapeWithStyle
 * @see com.jswiff.swfrecords.StyleChangeRecord#setNewStyles(LineStyleArray,
 * 		FillStyleArray)
 * @since SWF 2
 */
public final class DefineShape2 extends DefinitionTag {
	private Rect shapeBounds;
	private ShapeWithStyle shapes;

	/**
	 * Creates a new DefineShape2 tag. Supply the character ID of the shape,
	 * its bounding box and its primitives and styles.
	 *
	 * @param characterId character ID of shape
	 * @param shapeBounds bounding box of shape
	 * @param shapes shape's primitives and styles
	 */
	public DefineShape2(
		int characterId, Rect shapeBounds, ShapeWithStyle shapes) {
		code				 = TagConstants.DEFINE_SHAPE_2;
		this.characterId     = characterId;
		this.shapeBounds     = shapeBounds;
		this.shapes			 = shapes;
	}

	DefineShape2() {
		// empty
	}

	/**
	 * Sets the bounding box of the shape, i.e. the rectangle that completely
	 * encloses it.
	 *
	 * @param shapeBounds shape's bounds
	 */
	public void setShapeBounds(Rect shapeBounds) {
		this.shapeBounds = shapeBounds;
	}

	/**
	 * Returns the bounding box of the shape, i.e. the rectangle that
	 * completely encloses it.
	 *
	 * @return shape's bounds
	 */
	public Rect getShapeBounds() {
		return shapeBounds;
	}

	/**
	 * Sets the shape's primitives and styles (i.e. lines and curves) in a
	 * <code>ShapeWithStyle</code> instance.
	 *
	 * @param shapes shape's primitives and styles
	 */
	public void setShapes(ShapeWithStyle shapes) {
		this.shapes = shapes;
	}

	/**
	 * Returns the shape's primitives and styles (i.e. lines and curves) in a
	 * <code>ShapeWithStyle</code> instance.
	 *
	 * @return shape's primitives and styles
	 */
	public ShapeWithStyle getShapes() {
		return shapes;
	}

	protected void writeData(OutputBitStream outStream)
		throws IOException {
		outStream.writeUI16(characterId);
		shapeBounds.write(outStream);
		shapes.write(outStream);
	}

	void setData(byte[] data) throws IOException {
		InputBitStream inStream = new InputBitStream(data);
		characterId     = inStream.readUI16();
		shapeBounds     = new Rect(inStream);
		shapes		    = new ShapeWithStyle(inStream, false); // no alpha here
	}
}
