
package	com.satvik.args;

/**
 * ArgHandler is a class which parses command line arguments, and enables simple retrieval
 * of flags and arguments. Any class which needs to use this library must create an ArgHandler
 * object, and pass to it an array of arguments, a matrix/table of synonimous
 * 'short' and 'long' flags, as well as the minimum number of arguments (excluding flags).
 * <p>
 * Flags are separated from arguments : any String starting with '-' is a 'short' flag, while 
 * those starting with '--' are long flags. Short flags can be grouped together : '-abc' is 
 * recognized as the group of flags 'a', 'b' and 'c', and processed separately. Each short flag 
 * is associated with it's long version, and thus, they can be used interchangeably. Flags can
 * also carry values attached to them, separated from the flag by an '=' character. These values
 * can also be retrieved separately for each flag.
 * <p>
 * Arguments are the Strings other than flags, and are stored in a queue. They can be popped off
 * the beginning, or pushed to the end. Using this library, the next Integer or Double value can
 * also be retrieved and popped.
 * <p>
 * In addition, simple errors such as the lack of sufficient arguments or the use of unknown flags
 * are detected and can be queried.
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
 * 		String[][] flagTable = {{"-h", "--help"},	// Here, Strings in each row
 * 					{"-M", "-max-value"},	// will be trigger the same
 * 					{"-m", "-min-value"}};	// flags if present in 'args[]'
 * 
 * 		int minArgs = 2;				// Specify that 2 arguments are required
 * 
 * 		ArgHandler a = new ArgHandler(args, flagTable, minArgs);
 * 								// Create the ArgHandler
 * 		String firstArg = a.next();			// Get the first argument
 * 		int firstInt = a.nextInt();			// Get the first Integer
 * 		boolean help = a.checkFlag('h');		// '-h' or '--help' in 'args[]' will
 * 								// result in 'help' being true
 * 		String min = a.valueOfFlag("--min-value");	// Query the value of -m 
 * 		String max = a.valueOfFlag("-M");		// Query the value of -M 
 * 
 * 		boolean tooFewArgs = a.hasTooFewArgs();		// Check for common errors 
 * 		boolean unknownFlagsUsed = a.hasUnknownFlag();
 * 		String unknownFlags = a.getUnknownFlags();
 *		
 *		.
 *		.
 *		.
 *
 * 	} 
 * }
 *
 * }</pre>
 * 	Executing ... 	:	
 * 		<pre>{@code
 * 			java Example --help -m=1 -M=20 FirstArg SecondArg 100 --unknown-flag
 * 		}</pre>
 * 	
 * 	... will assign :	
 * 		<pre>{@code
 * 			
 * 			"FirstArg"		to	firstArg,
 * 			 100			to	firstInt,
 * 			 rue			to 	help,
 * 			"1"			to	min,
 * 			"20"			to	max,
 * 			false			to	tooFewArgs,
 * 			true			to	unknownFlagsUsed, and
 *			"--unknown-flag"	to	unknownFlags 
 *
 *		}</pre>
 *
 *
 * 	@author		Satvik Saha
 * 	@version	1.2, 04/02/2016
 * 	@since		1.0
 */

public class ArgHandler {
	
	/**
	 * Boolean array storing the state of flags, indexed by the flag character.
	 */
	private boolean[] flags;
	
	/**
	 * Matrix of Strings, representing synonimous forms of the same flag.
	 */
	private String[][] flagTable;
	
	/**
	 * String array, storing the elements of the queue of arguments. 
	 */
	private String[] arguments;
	
	/**
	 * String array, storing the elements of the queue of unprocessed arguments and flags.
	 */
	private String[] rawArgs;
	private int minArgs;
	
	private boolean tooFewArgs;
	private boolean flagNotFound;
	private String unknownFlags;
	
	/**
	 * Constructor of ArgHandler. Here, the array of arguments, table of flags and the number 
	 * of required arguments are passed to ArgHandler(String[], String[][], int). The arguments will
	 * be processed and flags separated and stored in a boolean array.
	 *
	 * 	@param	args		the array of arguments to be processed
	 * 	@param	flagTable	the array/matrix specifying which short and long flags are equivalent
	 * 	@param	minArgs		the minimum number of arguments (not flags) required to exist in the queue.
	 * 	@see	#processFlags(String[])
	 * 	@since	1.0
	 */

	public ArgHandler (String[] args, String[][] flagTable, int minArgs) {
		
		flags = new boolean[256];
		rawArgs = new String[args.length];

		this.flagTable = new String[flagTable.length][2];
		for (int i = 0; i < flagTable.length; i++) {
			for (int j = 0; j < 2; j++) {
				this.flagTable[i][j] = flagTable[i][j];
			}
		}
		for (int i = 0; i < args.length; i++) {
			rawArgs[i] = args[i];
		}
		for (int i = 0; i < 256; i++) {
			flags[i] = false;
		}
		arguments = new String[0];
		flagNotFound = false;
		unknownFlags = "";

		processFlags(args);
		
		tooFewArgs = (argCount() < minArgs);
	}
	


	/**
	 * Processes an array of arguments by sending each String to processFlags(String).
	 * This method is automatically called in the constructor.
	 * If a String isnot a flag, ie. is an argument, it is pushed to the argument queue.
	 *
	 * 	@param	args		the array of argumentsto be proxessed
	 * 	@see	#processFlags(String)
	 * 	@see	#pushArg(String)
	 * 	@since	1.0
	 */

	private void processFlags (String[] args) {
		for (String arg : args) {
			if (!processFlags(arg)) {
				pushArg(arg);
			}
		}
	}



	/**
	 * Processes and interprets a String. This method separates the flags from the String
	 * (both short and long), ignores values assigned to flags and stores the short version 
	 * of the flag in a boolean array, with the flag's character being the array index.
	 * For example, flags['c'] will return true or false, based on whether the '-c' flag 
	 * has been triggered.
	 *
	 * 	@param	arg		the String to be provessed
	 * 	@return			true, only if 'arg' was a flag 
	 * 	@since	1.0
	 */

	private boolean processFlags (String arg) {
		if (arg.charAt(0) == '-') {
			arg = arg.substring(0, (arg.indexOf("=") == -1)? arg.length() : arg.indexOf("="));
			if (arg.charAt(1) == '-') {
				if (isInTable(arg)) {
					char opt = getShortOpt(arg);
					flags[opt] = true;
				} else {
					unknownFlags += " " + arg;
					flagNotFound = true;
				}
			} else {
				for (int i = 1; i < arg.length(); i++) {
					if (isInTable(("-" + arg.charAt(i)))) {
						flags[(int)arg.charAt(i)] = true;
					} else {
						unknownFlags += " -" + arg.charAt(i);
						flagNotFound = true;
					}
				}
			}
			return true;
		}
		return false;
	}



	/**
	 * Checks whether the flag represented by character 'c' has been triggered.
	 *
	 * 	@param	c		character representation of flag ("-h" = 'h')
	 * 	@return			true, if the flag has been triggered
	 * 	@since	1.0
	 */

	public boolean checkFlag (char c) {
		return flags[(int)c];
	}



	/**
	 * Checks whether the flag represented in it's long form in String 's' has been triggered.
	 *
	 * 	@param	s		String of long representaion of the flag (eg. "--help")
	 * 	@return			true, if the flag has triggered
	 * 	@see	#getShortOpt(String)
	 * 	@since	1.0
	 */

	public boolean checkFlag (String s) {
		return checkFlag(getShortOpt(s));
	}



	/**
	 * Returns true if the minArgs exceeds the actual number of arguments.
	 * 	
	 * 	@return 		true, if minArgs exceeds arguments.length
	 * 	@since	1.1
	 */

	public boolean hasTooFewArgs () {
		return tooFewArgs;
	}



	/**
	 * Returns true if a flag not present in the flagTable has been triggered.
	 *
	 * 	@return 		true, if an unknown flag has been detected
	 * 	@since	1.1
	 */

	public boolean hasUnknownFlag () {
		return flagNotFound;
	}



	/**
	 * Returns a String containing all of the unknown flags detected.
	 *
	 * 	@return			list of unknown flags
	 * 	@since	1.1
	 */

	public String getUnknownFlags () {
		return unknownFlags;
	}



	/**
	 * Returns the number of arguments currently in the queue.
	 *
	 * 	@return			number of arguments in the queue
	 * 	@since	1.2
	 */

	public int argCount () {
		return arguments.length;
	}



	/**
	 * Returns true if the arguments queue still contains arguments.
	 *
	 * 	@return			false, if all arguments have been popped out of the queue
	 * 	@since	1.2
	 */

	public boolean hasMoreArgs () {
		return (arguments.length > 0);
	}



	/**
	 * Returns whether the queried String is present in the argument queue.
	 *
	 * 	@param	arg		String which os to be matched with arguments
	 * 	@return			true, if 'arg' is present
	 * 	@since	1.1
	 */

	public boolean hasArg (String arg) {
		for (String a : arguments) {
			if (a.equals(arg)) {
				return true;
			}
		}
		return false;
	}



	/**
	 * Returns the first argument in the queue, then pops it out.
	 *
	 * 	@return			first argument in the queue
	 * 	@see	#popArg(int)
	 * 	@see	#nextInt()
	 * 	@see	#nextDouble()
	 * 	@since	1.2
	 */

	public String next () {
		return popArg (0);
	}



	/**
	 * Returns the next Integer in the queue, then pops it out.
	 *
	 * 	@return			next Integer in the argument queue
	 * 	@see	#nextInt(int)
	 * 	@see	#next()
	 * 	@since	1.2
	 */

	public int nextInt () {
		return nextInt(0);
	}



	/**
	 * Returns the next Double in the queue, then pops it out.
	 *
	 * 	@return			next Double in the argument queue
	 * 	@see	#nextDouble(int)
	 * 	@see	#next()
	 * 	@since	1.2
	 */

	public double nextDouble () {
		return nextDouble(0);
	}



	/**
	 * Returns the first Integer after the given index. If none exist, returns Integer.MIN_VALUE
	 *
	 *	@param	index		index above which an Integer is to be searched for
	 *	@return			an Integer with a position in the queue above or equal to 'index'
	 *	@since	1.2
	 */

	private int nextInt (int index) {
		int x = 0;
		while (index < arguments.length) {
			try {
				x = Integer.parseInt(arguments[index]);
				popArg(index);
				return x;
			} catch (NumberFormatException e) {
				index++;
			}
		}
		return Integer.MIN_VALUE;
	}



	/**
	 * Returns the first Double after the given index. If none exist, returns Double.MIN_VALUE
	 *
	 *	@param	index		index above which an Double is to be searched for
	 *	@return			an Double with a position in the queue above or equal to 'index'
	 *	@since	1.2
	 */

	private double nextDouble (int i) {
		double x = 0;
		while (i < arguments.length) {
			try {
				x = Double.parseDouble(arguments[i]);
				popArg(i);
				return x;
			} catch (NumberFormatException e) {
				i++;
			}
		}
		return Double.MIN_VALUE;
	}



	/**
	 * Returns the value assigned to a flag with "=". The flag is represented in it's long form.
	 *
	 * 	@param	s		the long form of the flag whose value is to be extracted
	 * 	@return			the value assigned to the flag 's'
	 * 	@see	#checkFlag(char)
	 * 	@since	1.2
	 */

	public String valueOfFlag (String s) {
		return valueOfFlag(getShortOpt(s));
	}



	/**
	 * Returns the value assigned to a flag with "=". The flag is represented in it's short form.
	 *
	 * 	@param	c		the short form of the flag whose value is to be extracted
	 * 	@return			the value assigned to the flag 'c'
	 * 	@see	#checkFlag(char)
	 * 	@since	1.2
	 */

	public String valueOfFlag (char c) {
		if (checkFlag(c)) {
			int rawIndex = rawIndexOfFlag(c);
			String s = rawArgs[rawIndex];
			s = s.substring(((s.indexOf("=") == -1)? s.length() : (s.indexOf("=") + 1)), s.length());
			return s;
		}
		return "";
	}



	/**
	 * Returns the index of String 's' in the argument queue if present. If not, -1 is returned.
	 *
	 * 	@param	s		the String to be searched for
	 * 	@return			the index of 's' in the queue
	 * 	@since	1.2
	 */

	public int indexOfArg (String s) {
		int i = -1;
		if (hasArg(s)) {
			while (!arguments[++i].equals(s));
		}
		return i;
	}



	/**
	 * Returns the index of a flag (in it's short form) in the unprocessed argument array if present. 
	 * If not, -1 is returned.
	 *
	 * 	@param	c		the flag to be searched for
	 * 	@return			the index of 'c' in the raw arguments array
	 * 	@since	1.2
	 */

	private int rawIndexOfFlag (char c) {
		for (int i = 0; i < rawArgs.length; i++) {
			String s = rawArgs[i];
			s = s.substring(0, (s.indexOf("=") == -1)? s.length() : s.indexOf("="));
			if (s.charAt(0) == '-') {
				if ((s.charAt(1) == '-') && (s.equals(getLongOpt(c)))) {
					return i;
				} else {
					for (int j = 1; j < s.length(); j++) {
						if (s.charAt(j) == c) {
							return i;
						}
					}
				}
			}
		}
		return -1;
	}



	/**
	 * Pushes 'arg' into the end of the argument queue.
	 *
	 * 	@param	arg		the argument to be pushed into the queue
	 *	@see	#popArg(int)
	 *	@since	1.0
	 */

	public void pushArg (String arg) {
		String[] t = new String[arguments.length];
		for (int i = 0; i < arguments.length; i++) {
			t[i] = arguments[i];
		}
		arguments = new String[t.length + 1];
		for (int i = 0; i < t.length; i++) {
			arguments[i] = t[i];
		}
		arguments[t.length] = arg;
	}



	/**
	 * Returns the argument at the index 'n' in the queue, then pops it out.
	 * Returns "" if no such index exists.
	 *
	 * 	@param	n		index of argument to be popped off the queue
	 * 	@return			the argument at index n
	 * 	@see	#pushArg(String)
	 * 	@since	1.0
	 */

	public String popArg (int n) {
		if ((arguments.length > 0) && (n >= 0) && (n < arguments.length)) {
			String arg = arguments[n];
			String[] t = new String[arguments.length];
			for (int i = 0; i < arguments.length; i++) {
				t[i] = arguments[i];
			}
			arguments = new String[t.length - 1];
			for (int i = 0; i < n; i++) {
				arguments[i] = t[i];
			}
			for (int i = n; i < arguments.length; i++) {
				arguments[i] = t[i+1];
			}
			return arg;
		} 
		return "";
	}



	/**
	 * Returns true if String 's'is present in the flagTable.
	 *
	 * 	@param	s		the String representation of a flag (eg. '-h' or '--help')
	 * 	@return			true, if 's' is present in the flagTable
	 * 	@see	#checkFlag(char)
	 * 	@since	1.0
	 */

	public boolean isInTable (String s) {
		for (String line[] : flagTable) {
			for (String option : line) {
				if (s.equals(option)) {
					return true;
				}
			}
		}
		return false;
	}



	/**
	 * Returns the short form of flag 's', if present in the flagTable.
	 * If not, returns '-'.
	 *
	 * 	@param	s		the long form of a flag
	 * 	@return			the short form of the flag
	 * 	@see	#getLongOpt(char)
	 * 	@since	1.0
	 */

	public char getShortOpt (String s) {
		for (String line[] : flagTable) {
			if (line[1].equals(s)) {
				return line[0].charAt(1);
			}
		}
		return '-';
	}



	/**
	 * Returns the long form of flag 's', if present in the flagTable.
	 * If not, returns "-".
	 *
	 * 	@param	c		the short form of a flag
	 * 	@return			the long form of the flag
	 * 	@see	#getShortOpt(String)
	 * 	@since	1.0
	 */

	public String getLongOpt (char c) {
		for (String line[] : flagTable) {
			if (line[0].charAt(1) == c) {
				return line[1];
			}
		}
		return "";
	}
} 
