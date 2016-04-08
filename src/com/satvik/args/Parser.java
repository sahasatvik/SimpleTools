
package com.satvik.args;

/**
 * This interface provides methods for parsing objects into one another. As this is
 * a functional interface, one can create their own functions for parsing Strings 
 * into other objects via lambda expressions. For example :
 * <pre>{@code
 *		.
 *		.
 *		.
 *
 *		Parser<Integer> toInt = (s) -> (Integer.parseInt(s));
 *		
 *		.
 *		.
 *		.
 *
 *		try {
 *			Integer i = toInt.parse("1");
 *		} catch (NumberFormatException e) {
 *			.
 *			.
 *			.
 *		}
 * }</pre>
 *
 * 	@author		Satvik Saha
 * 	@version	1.1, 04/07/2016
 * 	@param	<T>	the target type into which strings are to be parsed 
 * 	@since		1.0
 */

@FunctionalInterface
public interface Parser<T> {
	
	/**
	 * This method must be overriden in the implementing class, or can be used via
	 * lambda expressions. The implementation of {@code parse(String)} must return 
	 * an object belonging to the type parameter used in the inherited class or the
	 * lambda expression declaration.
	 *
	 * 	@param	s		the string to be parsed 
	 * 	@return			an object of the same type as the type parameter
	 * 	@throws	Exception	thrown if the implementation of this method throws an Exception
	 * 	@since	1.1
	 */

	public T parse (String s) throws Exception;
	
	
	
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
