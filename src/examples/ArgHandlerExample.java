
package examples;

import com.github.sahasatvik.cli.*;
import com.github.sahasatvik.struct.*;

public class ArgHandlerExample {
	public static void help () {
		System.out.println("ArgHandlerExample is a small program which displays multiplication tables.");
		System.out.println("This program makes use of the com.github.sahasatvik.cli library, which simplifies the retrieval of options and arguments.\n");
		System.out.println("Usage :	java ArgHandlerExample [-ht] [-s=<first multiplicand>] [-e=<last multiplicand>] multiplier \n");
		System.out.println("	Arguments :");
		System.out.println("		multiplier		: The number whose multiplication table is to be displayed");
		System.out.println("	Options :");
		System.out.println("		-h or --help 		: Displays this help-text");
		System.out.println("		-t or --show-table	: Display the products, along with the multiplier and multiplicands");
		System.out.println("		-s or --start-at 	: Specify the first multiplicand");
		System.out.println("		-e or --end-at 		: Specify the last multiplicand");
		System.exit(0);
	}
	public static void main (String[] args) {
		
		int number = 0, start = 0, end = 0;
		boolean show = false;
		
		try {
			Option help = new Option("-h", "--help");
			Option showTables = new Option("-t", "--show-table");
			Option<Integer> startAt = new Option<Integer>("-s", "--start-at").canHaveValue(true)
								 		     .setValueType(Integer.class)
										     .setDefaultValue(1);
			Option<Integer> endAt = new Option<Integer>("-e", "--end-at").canHaveValue(true)
										 .setValueType(Integer.class)
										 .setDefaultValue(10);
			
			ArgHandler argHandler = new ArgHandler(args).useOptions(help, showTables, startAt, endAt);
		
			if (help.getState()) {
				help();
			}

			number = argHandler.next(Integer.class);
			show = showTables.getState();
			start = startAt.getValue();
			end = endAt.getValue();

		} catch (ArgumentException e) {
			System.out.println("You must enter the number whose multiplication table is to be printed !\n");
			help();
		} catch (ArgHandlerException e) {
			System.out.println(e.getMessage() + "\n");
			help();
		}

		for (int i = start; i <= end; i++) {
			System.out.print((show)? (number + " X " + i + " = ") : (""));
			System.out.println(number * i);
		}
	}
}
