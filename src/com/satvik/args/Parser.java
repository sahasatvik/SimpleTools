
package com.satvik.args;

/**
 * This class provides static methods for parsing objects into one another.
 *
 * 	@author		Satvik Saha
 * 	@version	1.0, 04/06/2016
 * 	@since		1.0
 */

public class Parser {
	
	/**
	 * This method accepts a String to parse and the class of the target type. It returns
	 * the parsed String in the form of T.
	 *
	 * 	@param	<T>		the target type
	 * 	@param	rawValue	the string to be parsed
	 * 	@param	clazz		the class of the target type - use T.class
	 * 	@return			the converted string in the form of T
	 * 	@throws	NumberFormatException	thrown if the target class is not recognized
	 * 	@since	1.0
	 */

	public static <T> T parse (String rawValue, Class<T> clazz) throws NumberFormatException {
		Object t;
		switch (clazz.getName()) {
			case "java.lang.Character":	if (rawValue.length() == 1) {
								t = Character.valueOf(rawValue.charAt(0));
							} else {
								throw new NumberFormatException();
							}
							break;
			case "java.lang.String"	:	t = String.valueOf(rawValue);	break;
			case "java.lang.Byte"	:	t = Byte.valueOf(rawValue);	break;
			case "java.lang.Short"	:	t = Short.valueOf(rawValue);	break;
			case "java.lang.Integer":	t = Integer.valueOf(rawValue);	break;
			case "java.lang.Long"	:	t = Long.valueOf(rawValue);	break;
			case "java.lang.Float"	:	t = Float.valueOf(rawValue);	break;
			case "java.lang.Double" :	t = Double.valueOf(rawValue);	break;
			default 		:	throw new NumberFormatException();		
		}
		@SuppressWarnings("unchecked")
		T temp = (T) t;
		return temp;
	}
} 
