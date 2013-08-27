package com.jswiff.swfrecords.actions;

import java.io.IOException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.List;

import org.springframework.util.Assert;

import org.apache.commons.lang.builder.HashCodeBuilder;

import org.swfparser.BooleanOperation;
import org.swfparser.CodeUtil;
import org.swfparser.DualUse;
import org.swfparser.Operation;
import org.swfparser.Priority;
import org.swfparser.operation.NotOperation;
import com.jswiff.io.InputBitStream;
import com.jswiff.io.OutputBitStream;

/**
 * This class contains a value which can be pushed to the stack. The default
 * value is <code>undefined</code>, the type is <code>TYPE_UNDEFINED</code>.
 * Use setters to change.
 */
public class StackValue implements Serializable, Operation, BooleanOperation/*, DualUse*/ {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3688111214860987011L;

	/** Indicates that the value to be pushed is a string. */
	public static final short TYPE_STRING = 0;

	/** Indicates that the value to be pushed is a floating point number. */
	public static final short TYPE_FLOAT = 1;

	/** Indicates that the value to be pushed is <code>null</code>. */
	public static final short TYPE_NULL = 2;

	/** Indicates that the value to be pushed is <code>undefined</code>. */
	public static final short TYPE_UNDEFINED = 3;

	/** Indicates that the value to be pushed is a register number. */
	public static final short TYPE_REGISTER = 4;

	/** Indicates that the value to be pushed is a boolean. */
	public static final short TYPE_BOOLEAN = 5;

	/**
	 * Indicates that the value to be pushed is double-precision floating point
	 * number.
	 */
	public static final short TYPE_DOUBLE = 6;

	/** Indicates that the value to be pushed is an integer. */
	public static final short TYPE_INTEGER = 7;

	/**
	 * Indicates that the value to be pushed is an 8-bit constant pool index.
	 */
	public static final short TYPE_CONSTANT_8 = 8;

	/**
	 * Indicates that the value to be pushed is a 16-bit constant pool index.
	 */
	public static final short TYPE_CONSTANT_16 = 9;

	protected short type = TYPE_UNDEFINED;

	private String string;

	private float floatValue;

	private short registerNumber;

	private boolean booleanValue;

	private double doubleValue;

	private long integerValue;

	private short constant8;

	private int constant16;
	
	private boolean isStatement = false;

	/**
	 * Creates a new StackValue instance. Initial type is
	 * <code>TYPE_UNDEFINED</code>.
	 */
	public StackValue() {
		// nothing to do
	}

	public StackValue(String string) {
		this.string = string;
		this.type = TYPE_STRING;
	}
	
	public StackValue(int intValue) {
		this.integerValue = intValue;
		this.type = TYPE_INTEGER;
	}
	
	public StackValue(boolean bValue) {
		this.booleanValue = bValue;
		this.type = TYPE_BOOLEAN;
	}
	
	/*
	 * Reads a PushEntry instance from a bit stream.
	 */
	StackValue(InputBitStream stream) throws IOException {
		type = stream.readUI8();
		switch (type) {
		case TYPE_STRING:
			string = stream.readString();
			break;
		case TYPE_FLOAT:
			floatValue = stream.readFloat();
			break;
		case TYPE_REGISTER:
			registerNumber = stream.readUI8();
			break;
		case TYPE_BOOLEAN:
			booleanValue = (stream.readUI8() != 0);
			break;
		case TYPE_DOUBLE:
			doubleValue = stream.readDouble();
			break;
		case TYPE_INTEGER:
			integerValue = stream.readUI32();
			break;
		case TYPE_CONSTANT_8:
			constant8 = stream.readUI8();
			break;
		case TYPE_CONSTANT_16:
			constant16 = stream.readUI16();
			break;
		}
	}

	/**
	 * Sets the push value to a boolean, and the type to TYPE_BOOLEAN.
	 * 
	 * @param value
	 *            a boolean value
	 */
	public void setBoolean(boolean value) {
		this.booleanValue = value;
		type = TYPE_BOOLEAN;
	}

	/**
	 * Returns the boolean the push value is set to. If the value type is not
	 * TYPE_BOOLEAN, an IllegalStateException is thrown.
	 * 
	 * @return push value as boolean
	 */
	public boolean getBoolean() {
		return booleanValue;
	}

	/**
	 * Sets the push value to a 16-bit constant pool index, and the type to
	 * TYPE_BOOLEAN. Use 16-bit indexes when the constant pool contains more
	 * than 256 constants.
	 * 
	 * @param value
	 *            an 8-bit constant pool index
	 */
	public void setConstant16(int value) {
		this.constant16 = value;
		type = TYPE_CONSTANT_16;
	}

	/**
	 * Returns the 16-bit constant pool index the push value is set to. If the
	 * value type is not TYPE_CONSTANT_16, an IllegalStateException is thrown.
	 * 
	 * @return push value as 16-bit constant pool index
	 * 
	 * @throws IllegalStateException
	 *             if type is not TYPE_CONSTANT_16
	 */
	public int getConstant16() {
		if (type != TYPE_CONSTANT_16) {
			throw new IllegalStateException("Value type is not TYPE_CONSTANT_16!");
		}
		return constant16;
	}

	/**
	 * Sets the push value to an 8-bit constant pool index, and the type to
	 * TYPE_BOOLEAN. Use 8-bit indexes when the constant pool contains less than
	 * 256 constants.
	 * 
	 * @param value
	 *            an 8-bit constant pool index
	 */
	public void setConstant8(short value) {
		this.constant8 = value;
		type = TYPE_CONSTANT_8;
	}

	/**
	 * Returns the 8-bit constant pool index the push value is set to. If the
	 * value type is not TYPE_CONSTANT_8, an IllegalStateException is thrown.
	 * 
	 * @return push value as 8-bit constant pool index
	 * 
	 * @throws IllegalStateException
	 *             if type is not TYPE_CONSTANT_8
	 */
	public short getConstant8() {
		if (type != TYPE_CONSTANT_8) {
			throw new IllegalStateException("Value type is not TYPE_CONSTANT_8!");
		}
		return constant8;
	}

	/**
	 * Sets the push value to a double-precision number, and the type to
	 * TYPE_DOUBLE.
	 * 
	 * @param value
	 *            a double value
	 */
	public void setDouble(double value) {
		this.doubleValue = value;
		type = TYPE_DOUBLE;
	}

	/**
	 * Returns the double the push value is set to. If the value type is not
	 * TYPE_DOUBLE, an IllegalStateException is thrown.
	 * 
	 * @return push value as double
	 * 
	 * @throws IllegalStateException
	 *             if type is not TYPE_DOUBLE
	 */
	public double getDouble() {
		if (type != TYPE_DOUBLE) {
			throw new IllegalStateException("Value type is not TYPE_DOUBLE!");
		}
		return doubleValue;
	}

	/**
	 * Sets the push value to a (single-precision) float, and the type to
	 * TYPE_FLOAT.
	 * 
	 * @param value
	 *            a float value
	 */
	public void setFloat(float value) {
		this.floatValue = value;
		type = TYPE_FLOAT;
	}

	/**
	 * Returns the float the push value is set to. If the value type is not
	 * TYPE_FLOAT, an IllegalStateException is thrown.
	 * 
	 * @return push value as float
	 * 
	 * @throws IllegalStateException
	 *             if type is not TYPE_FLOAT
	 */
	public float getFloat() {
		if (type != TYPE_FLOAT) {
			throw new IllegalStateException("Value type is not TYPE_FLOAT!");
		}
		return floatValue;
	}

	/**
	 * Sets the push value to an integer, and the type to TYPE_INTEGER.
	 * 
	 * @param value
	 *            an integer value (of type <code>long</code>)
	 */
	public void setInteger(long value) {
		this.integerValue = value;
		type = TYPE_INTEGER;
	}

	/**
	 * Returns the integer the push value is set to. If the value type is not
	 * TYPE_INTEGER, an IllegalStateException is thrown.
	 * 
	 * @return push value as integer
	 * 
	 * @throws IllegalStateException
	 *             if type is not TYPE_INTEGER
	 */
	public long getInteger() {
		if (type != TYPE_INTEGER) {
			throw new IllegalStateException("Value type is not TYPE_INTEGER!");
		}
		return integerValue;
	}

	/**
	 * Sets the type to <code>TYPE_NULL</code> (i.e. the push value is
	 * <code>null</code>).
	 */
	public void setNull() {
		type = TYPE_NULL;
	}

	/**
	 * Checks if the push value is <code>null</code>.
	 * 
	 * @return true if <code>null</code>, else false.
	 */
	public boolean isNull() {
		return (type == TYPE_NULL);
	}

	/**
	 * Sets the push value to a register number, and the type to TYPE_REGISTER.
	 * 
	 * @param value
	 *            a register number
	 */
	public void setRegisterNumber(short value) {
		this.registerNumber = value;
		type = TYPE_REGISTER;
	}

	/**
	 * Returns the register number the push value is set to. If the value type
	 * is not TYPE_REGISTER, an IllegalStateException is thrown.
	 * 
	 * @return push value as register number
	 * 
	 * @throws IllegalStateException
	 *             if type is not TYPE_REGISTER
	 */
	public short getRegisterNumber() {
		if (type != TYPE_REGISTER) {
			throw new IllegalStateException("Value type is not TYPE_REGISTER!");
		}
		return registerNumber;
	}

	/**
	 * Sets the push value to a string, and the type to TYPE_STRING
	 * 
	 * @param value
	 *            a string value
	 */
	public void setString(String value) {
		this.string = value;
		type = TYPE_STRING;
	}

	/**
	 * Returns the string the push value is set to. If the value type is not
	 * TYPE_STRING, an IllegalStateException is thrown
	 * 
	 * @return push value as string
	 * 
	 * @throws IllegalStateException
	 *             if type is not TYPE_STRING
	 */
	public String getString() {
		if (type != TYPE_STRING) {
			throw new IllegalStateException("Value type is not TYPE_STRING!");
		}
		return string;
	}

	/**
	 * Returns the type of the push value. The type is one of the constants
	 * <code>TYPE_BOOLEAN, TYPE_CONSTANT_8, TYPE_CONSTANT_16, TYPE_DOUBLE,
	 * TYPE_FLOAT, TYPE_INTEGER, TYPE_NULL, TYPE_REGISTER, TYPE_STRING,
	 * TYPE_UNDEFINED</code>.
	 * 
	 * @return type of push value
	 */
	public short getType() {
		return type;
	}

	/**
	 * Sets the type to <code>TYPE_UNDEFINED</code> (i.e. the push value is
	 * <code>undefined</code>).
	 */
	public void setUndefined() {
		type = TYPE_UNDEFINED;
	}

	/**
	 * Checks if the push value is <code>undefined</code>.
	 * 
	 * @return true if <code>undefined</code>, else false.
	 */
	public boolean isUndefined() {
		return (type == TYPE_UNDEFINED);
	}

	/**
	 * Returs a short description of the push value (type and value)
	 * 
	 * @return type and value
	 */
	public String toString() {
		String result = "StackValue(";
		switch (type) {
		case TYPE_STRING:
			result += ("string: '" + string + "'");
			break;
		case TYPE_FLOAT:
			result += ("float: " + floatValue);
			break;
		case TYPE_REGISTER:
			result += ("register: " + registerNumber);
			break;
		case TYPE_BOOLEAN:
			result += ("boolean: " + booleanValue);
			break;
		case TYPE_DOUBLE:
			result += ("double: " + doubleValue);
			break;
		case TYPE_INTEGER:
			result += ("integer: " + integerValue);
			break;
		case TYPE_CONSTANT_8:
			result += ("c8[" + constant8 + "]");
			break;
		case TYPE_CONSTANT_16:
			result += ("c16[" + constant16 + "]");
			break;
		case TYPE_UNDEFINED:
			result += "undefined";
			break;
		case TYPE_NULL:
			result += "null";
			break;
		}
		result += ")";
		return result;
	}

	int getSize() {
		int size = 1; // type
		switch (type) {
		case TYPE_STRING:
			try {
				size += (string.getBytes("UTF-8").length + 1);
			} catch (UnsupportedEncodingException e) {
				// UTF-8 should be available. If not, we have a big problem
				// anyway
			}
			break;
		case TYPE_FLOAT:
			size += 4;
			break;
		case TYPE_REGISTER:
			size++;
			break;
		case TYPE_BOOLEAN:
			size++;
			break;
		case TYPE_DOUBLE:
			size += 8;
			break;
		case TYPE_INTEGER:
			size += 4;
			break;
		case TYPE_CONSTANT_8:
			size++;
			break;
		case TYPE_CONSTANT_16:
			size += 2;
			break;
		}
		return size;
	}

	void write(OutputBitStream outStream) throws IOException {
		outStream.writeUI8(type);
		switch (type) {
		case TYPE_STRING:
			outStream.writeString(string);
			break;
		case TYPE_FLOAT:
			outStream.writeFloat(floatValue);
			break;
		case TYPE_REGISTER:
			outStream.writeUI8(registerNumber);
			break;
		case TYPE_BOOLEAN:
			outStream.writeUI8((short) (booleanValue ? 1 : 0));
			break;
		case TYPE_DOUBLE:
			outStream.writeDouble(doubleValue);
			break;
		case TYPE_INTEGER:
			outStream.writeUI32(integerValue);
			break;
		case TYPE_CONSTANT_8:
			outStream.writeUI8(constant8);
			break;
		case TYPE_CONSTANT_16:
			outStream.writeUI16(constant16);
			break;
		}
	}

	public int getArgsNumber() {
		return 0;
	}

	public int getIntValue() {
		int value = 0;
		if (StackValue.TYPE_FLOAT == getType()) {
			value = Float.valueOf(getFloat()).intValue();
		}
		if (StackValue.TYPE_DOUBLE == getType()) {
			value = Double.valueOf(getDouble()).intValue();
		}
		if (StackValue.TYPE_INTEGER == getType()) {
			value = (int) getInteger();
		}
		return value;
	}

	public double getDoubleValue() {
		double value = 0;
		if (StackValue.TYPE_FLOAT == getType()) {
			value = Float.valueOf(getFloat()).doubleValue();
		}
		if (StackValue.TYPE_DOUBLE == getType()) {
			value = Double.valueOf(getDouble()).doubleValue();
		}
		if (StackValue.TYPE_INTEGER == getType()) {
			value = getInteger();
		}
		return value;
	}

	public String getStringValue(int level) {

		String prefix = isStatement ? CodeUtil.getIndent(level) : "";
		
		switch (type) {
		case TYPE_NULL:
			return "null";
		case TYPE_STRING:
			return prefix+formatString(string);
		case TYPE_FLOAT:
			return prefix+Float.valueOf(getFloat()).toString();
		case TYPE_REGISTER:
			return prefix+"r:" + getRegisterNumber();
		case TYPE_BOOLEAN:
			return prefix+(getBoolean() ? "true" : "false");
		case TYPE_DOUBLE:
			return prefix + ((getDouble() == 0) ? "0" : Double.valueOf(getDouble()).toString());
		case TYPE_INTEGER:
			return prefix+Long.valueOf(getInteger()).toString();
		case TYPE_UNDEFINED:
			return prefix+"undefined";
		case TYPE_CONSTANT_8:
		case TYPE_CONSTANT_16:
		default:
			return prefix+toString();
		}

	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(type).append(toString()).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
//		if (!(obj instanceof StackValue)) {
//			return false;
//		}
		if (this == obj) {
			return true;
		}
		
		if (obj instanceof StackValue) {
			StackValue stackValue = (StackValue) obj;
	
			boolean match = stackValue.getType() == type;
			if (!match) {
				return false;
			}
	
			switch (type) {
				case TYPE_STRING:
					match = string.equals(stackValue.getString());
					break;
				case TYPE_FLOAT:
					match = (floatValue == stackValue.getFloat());
					break;
				case TYPE_REGISTER:
					match = (registerNumber == stackValue.getRegisterNumber());
					break;
				case TYPE_BOOLEAN:
					match = (booleanValue == stackValue.getBoolean());
					break;
				case TYPE_DOUBLE:
					match = (doubleValue == stackValue.getDouble());
					break;
				case TYPE_INTEGER:
					match = (integerValue == stackValue.getInteger());
					break;
				case TYPE_CONSTANT_8:
					match = (constant8 == stackValue.getConstant8());
					break;
				case TYPE_CONSTANT_16:
					match = (constant16 == stackValue.getConstant16());
					break;
	
			}
			return match;
		} else if (obj instanceof NotOperation) {
			return obj.equals(this);
		} else {
			return false;
		}

		
	}

//	public String formatString() {
//		Assert.isTrue(type == TYPE_STRING);
//		return formatString(string);
//	}
	
	public static String formatString(String v) {
		return "\""+escapeString(v)+"\"";
	}
	
	private static String escapeString(String v) {
		v = v.replaceAll("\\\\", "\\\\\\\\");
        v = v.replaceAll("\n", "\\\\n");
        v = v.replaceAll("\"", "\\\\\"");
		return v;
	}
	
	public int getPriority() {
		return Priority.HIGHEST;
	}
	
	public static void main(String[] args) {
		System.out.println(formatString("\\ - \n"));
	}

//	public String getInvertedStringValue(int level) {
//		if (TYPE_BOOLEAN == type) {
//			return booleanValue ? "false" : "true";
//		} else {
//			return "!"+getStringValue(level);
//		}
//	}
	
	public Operation getInvertedOperation() {
		return new InvertedStackValue(this);
	}
	
	/**
	 * Inverted stack value operation class.
	 *
	 */
	private class InvertedStackValue implements BooleanOperation, DualUse {
		private StackValue stackValue;
		private boolean isStatement = false;

		public InvertedStackValue(StackValue stackValue) {
			super();
			this.stackValue = stackValue;
		}

		public Operation getInvertedOperation() {
			return stackValue;
		}

		public int getArgsNumber() {
			return 0;
		}

		public int getPriority() {
			return Priority.HIGHEST;
		}

		public String getStringValue(int level) {
			String prefix = isStatement ? CodeUtil.getIndent(level) : ""; 
			if (TYPE_BOOLEAN == stackValue.getType()) {
				return prefix+ (stackValue.getBoolean() ? "false" : "true");
			} else {
				return prefix+"!"+getStringValue(level);
			}
		}
		
		@Override
		public boolean equals(Object obj) {
			if (obj instanceof NotOperation) {
				return obj.equals(this); // invert comparison
			} else {
				return super.equals(obj);
			}
		}

		public List<Operation> getOperations() {
			return Collections.EMPTY_LIST;
		}

		public void markAsStatement() {
			isStatement = true;
			
		}
		
		
	}

	public void markAsStatement() {
		isStatement = true;
	}

	public List<Operation> getOperations() {
		return Collections.EMPTY_LIST;
	}

	
}
