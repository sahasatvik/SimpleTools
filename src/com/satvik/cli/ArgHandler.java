
package	com.satvik.cli;

import com.satvik.struct.*;

/**
 * This class parses command line arguments, and enables simple retrieval of options and arguments. 
 * Any class which needs to use this library must create an ArgHandler object, and pass to it an 
 * array of arguments, as well as a list of Option objects which can be accepted from the argument array.
 * <p>
 * Options are separated from arguments : any String starting with '-' is a 'short' option, while 
 * those starting with '--' are long options. Short options can be grouped together : '-abc' is 
 * recognized as the group of options 'a', 'b' and 'c', and processed separately. Each short option 
 * is associated with it's long version, and thus, they can be used interchangeably. Options can
 * also carry values attached to them, separated from the option by an '=' character. These values
 * can also be retrieved separately for each option.
 * <p>
 * Arguments are the Strings other than options, and are stored in a queue. They can be popped off
 * the beginning, or pushed to the end. Using this library, the next Integer or Double value can
 * also be retrieved and popped.
 * <p>
 * In addition, errors such as the use of unknown options are detected, and suitable Exceptions are thrown.
 * <p>
 * An example of ArgHandler's implementation is as follows : 
 * <pre>{@code
 *
 * class Example {
 * 	public static void main (String[] args) {
 *
 * 		.
 * 		.
 * 		. 
 *      
 *	Option help = new Option("-h", "--help");		// Normal option without value. Note that 
 *							// no value can be queried from it using 
 *							// option.getValue() : attempting to 
 *							// do so throws a OptionException
 *	Option<Integer> min = new Option<Integer>("-m", "--min").canHaveValue(true)
 *							    .setValueType(Integer.class);
 *							// This Option may be assigned an Integer by 
 *							// ArgHandler while processing
 *	Option<Integer> max = new Option<Integer>("-M", "--max").canHaveValue(true)
 *							    .setValueType(Integer.class)
 *							    .setDefaultValue(10);
 *							// This Option will have a default value of 10, if no
 *							// value is set by ArgHandler
 *	Option<String> capsName = new Option<String>("-n", "--name").canHaveValue(true)
 *								.useParser((s) -> (s.toUpperCase()));
 *							// This option uses a lambda expression for 
 *							// Parser<T>, to parse the name into upperCase.
 *							// This means that while setting the value of the Option,
 *							// the raw String is parsed by the parser specified above,
 *							// which is a lambda expression here for brevity.
 *							
 *	try {
 *		ArgHandler a = new ArgHandler(args).useOptions(help, min, max, cpasName);
 *							// Create an ArgHandler and pass the Options to it
 *	} catch (ArgumentException e) {
 *
 *	} catch ( . . .
 *		.
 *		.					// Continue catching all Exceptions
 *		.
 *	
 *	Boolean needsHelp = help.getState();		// The state of the Option can be retrieved 
 *							// by calling help.getState()
 *	Integer minVal = min.getValue();		// The value of min.getValue() can be stored 
 *							// directly in minVal, due to the use of generics
 *	Integer maxVal = max.getValue();		// If no value has been set, the default value
 *	String name = capsName.hetValue()		// specified will be returned
 *	try {
 *		String firstArg = a.next();		// Get the first argument
 *		Integer firstInteger = a.<Integer>next(Integer.class);
 *							// Get the first Integer	
 *		Parser<String> small = (s) -> (s.toLowerCase());	
 *		String smallName = a.<String>next(small);
 *							// Get the first argument which can be parsed
 *							// by 'small'
 *	} catch ( . . .
 *		.
 *		.
 *		.
 * 	}
 * }
 *
 * }</pre>
 * 	
 * 	@author		Satvik Saha
 * 	@version	2.0, 04/09/2016
 * 	@since		1.0
 */ 	

public class ArgHandler {
	
	private Queue<Option<?>> options;
	private Queue<Argument> arguments;

	private String[] rawArgs;
	
	/**
	 * This is the only constructor of ArgHandler. Here, the array of arguments must be passed to 
	 * ArgHandler. The arguments will be processed, and can be retrieved as if they were in a Queue.
	 *
	 * 	@param	args			the array of arguments to be processed
	 * 	@since	1.0
	 */

	public ArgHandler (String[] args) {
		
		rawArgs = new String[args.length];

		for (int i = 0; i < args.length; i++) {
			rawArgs[i] = args[i];
		}

		options = new Queue<Option<?>>();
		arguments = new Queue<Argument>();
		
		processArgs();
	}



	/**
	 * This method must be called in order to initiate the processing of options in the array
	 * of arguments passed earlier. Option objects created earlier must be passed, which will be midified
	 * dynamically during processing. Only these options will be considered to be valid while processing.
	 * The presence of any option other than those passed here will trigger a OptionException.
	 *
	 * 	@param	options			the array of valid Options to be used
	 * 	@return				this ArgHandler
	 * 	@throws	com.satvik.cli.InvalidOptionException	thrown if an invalid Option is found
	 *	@throws	com.satvik.cli.OptionException		thrown if a value assigned to a Option cannot be parsed properly
	 *	@since	2.0
	 */
	
	public ArgHandler useOptions (Option<?> ... options) throws OptionException {
		for (Option<?> f : options) {
			this.options.push(f);
		}
		for (String s : rawArgs) {
			processOptions(s);
		}
		return this;
	}

	private void processOptions (String s) throws OptionException {
		Option f;
		if (s.charAt(0) == '-') {
			if (s.charAt(1) == '-') {
				f = getOption(s);
				f.setState(true);
				f.parseValue(Option.extractValue(s));
			} else {
				for (int i = 1; i < s.length(); i++) {
					f = getOption(s.charAt(i));
					f.setState(true);
					if ((i + 1) < s.length()) {
						if (s.charAt(i+1) == '=') {
							f.parseValue(f.extractValue(s));
							i = s.length();
							break;
						}
					}
				}
			}
		}
	}



	/**
	 * This method returns the Option whose short or long form matches the String passed to 
	 * it. Note that the Options collected by useOptions(Options[]) are used here.
	 *
	 * 	@param	s			the String to be compared with the Options
	 * 	@return				the Option matching 's', if it exists
	 * 	@throws	com.satvik.cli.InvalidOptionException	thrown if the Option matching 's' does not exist
	 * 	@see	#useOptions(Option ...)
	 * 	@since	2.0
	 */

	public Option<?> getOption (String s) throws InvalidOptionException {
		int optionCount = options.getSize();
		Option<?> f;
		for (int i = 0; i < optionCount; i++) {
			try {
				f = options.getItemAt(i);
				if (f.matches(s)) {
					return f;
				}
			} catch (ListIndexOutOfBoundsException e) {
				System.out.println(e);
			}

		}
		throw new InvalidOptionException("Option " + s + " not valid !");
	}



	/**
	 * This method returns the Option whose short form contains the charcter passed to it.
	 *
	 * 	@param	c			the character to be compared with the Options
	 * 	@return				the Option matching 'c', if it exists
	 * 	@throws	com.satvik.cli.InvalidOptionException	thrown if the Option matching 'c' does not exist
	 * 	@see	#getOption(String)
	 * 	@since	2.0
	 */

	public Option<?> getOption (char c) throws InvalidOptionException {
		return getOption("-" + c);
	}

	private void processArgs () {
		for (String arg : rawArgs) {
			if (isArg(arg)) {
				arguments.push(new Argument(arg));
			}
		}
	}

	private boolean isArg (String arg) {
		if (arg.charAt(0) == '-') {
			return false;
		}
		return true;
	}



	/**
	 * Returns the number of arguments currently in the queue.
	 *
	 * 	@return				number of arguments in the queue
	 * 	@since	1.2
	 */

	public int argCount () {
		return arguments.getSize();
	}



	/**
	 * Returns true if the arguments queue still contains arguments.
	 *
	 * 	@return				false, if all arguments have been popped out of the queue
	 * 	@since	1.2
	 */

	public boolean hasMoreArgs () {
		return (argCount() > 0);
	}



	/**
	 * Returns the first argument in the queue, then pops it out.
	 *
	 * 	@return				first argument in the queue
	 * 	@throws	com.satvik.cli.NoRemainingArgumentsException	thrown if no arguments are left in the queue 
	 * 	@since	1.2
	 */

	public String next () throws NoRemainingArgumentsException {
		try {
			return arguments.pop().getValue();
		} catch (ListException e) {
			throw new NoRemainingArgumentsException();
		}
	}



	/**
	 * Returns the next argument of the class type 'clazz' passed to the method.
	 * If the first argument cannot be cast into the mentioned type, this method will 
	 * loop through the remaining arguments until a suitable argument is found, or 
	 * the end of the argument queue is reached.
	 *
	 * 	@param	<T>			the target type
	 * 	@param	clazz			the class of the target type
	 * 	@return				the first argument which can be parsed a 'clazz'
	 * 	@throws	com.satvik.cli.NoRemainingArgumentsException	thrown if no argument is found
	 * 	@throws	com.satvik.cli.NoArgumentOfRequiredTypeFoundException	thrown if no parsable arguments are left
	 * 	@see	com.satvik.cli.Argument#getValue(Class)
	 * 	@since	2.0
	 */
	
	public <T> T next (Class<T> clazz) throws NoRemainingArgumentsException, NoArgumentOfRequiredTypeFoundException {
		if (!hasMoreArgs()) {
			throw new NoRemainingArgumentsException();
		}
		int argCount = argCount();
		int i = 0;
		while (i < argCount) {	
			try {
				Argument a = arguments.getItemAt(i);
				T value = a.<T>getValue(clazz);
				arguments.popItemAt(i);
				return value;
			} catch (Exception e) {
				i++;
			}
		}
		throw new NoArgumentOfRequiredTypeFoundException(clazz);
	}



	/**
	 * Returns the next argument which can be parsed by the parser passed to the method.
	 * If the first argument cannot be parsed by the mentioned parser this method will 
	 * loop through the remaining arguments until a suitable argument is found, or 
	 * the end of the argument queue is reached.
	 *
	 * 	@param	<T>			the target type
	 * 	@param	parser			the parser to be used
	 * 	@return				the first argument which can be parsed by 'parser'
	 * 	@throws	com.satvik.cli.NoRemainingArgumentsException	thrown if no argument is found
	 * 	@throws	com.satvik.cli.NoArgumentOfRequiredTypeFoundException	thrown if no parsable arguments are left
	 * 	@see	com.satvik.cli.Argument#getValue(Parser)
	 * 	@see	com.satvik.cli.Parser#parse(String)
	 * 	@since	2.0
	 */

	public <T> T next (Parser<T> parser) throws NoRemainingArgumentsException, NoArgumentOfRequiredTypeFoundException {
		if (!hasMoreArgs()) {
			throw new NoRemainingArgumentsException();
		}
		int argCount = argCount();
		int i = 0;
		while (i < argCount) {	
			try {
				Argument a = arguments.getItemAt(i);
				T value = a.<T>getValue(parser);
				arguments.popItemAt(i);
				return value;
			} catch (Exception e) {
				i++;
			}
		}
		throw new NoArgumentOfRequiredTypeFoundException(parser);	
	}
} 
