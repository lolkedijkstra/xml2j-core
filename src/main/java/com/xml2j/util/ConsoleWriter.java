package com.xml2j.util;
/********************************************************************************
Copyright 2016 Lolke B. Dijkstra

Permission is hereby granted, free of charge, to any person obtaining a copy of 
this software and associated documentation files (the "Software"), to deal in the
Software without restriction, including without limitation the rights to use, 
copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the 
Software, and to permit persons to whom the Software is furnished to do so, 
subject to the following conditions:

The above copyright notice and this permission notice shall be included in all 
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A 
PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION 
OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE 
SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.

Project root: https://sourceforge.net/projects/xml2j/
********************************************************************************/
import java.io.PrintStream;
/**
 * PrinterWriter and ConsoleWriter adapt the interfaces of writing.
 * This class is used by the framework.
 * 
 * @author Lolke B. Dijkstra
 */

public class ConsoleWriter implements Printer {
	private PrintStream writer;
	/** wraps the PrintStream System.out. */
	public static final ConsoleWriter out = new ConsoleWriter(System.out); 
	/** wraps the PrintStream System.err. */
	public static final ConsoleWriter err = new ConsoleWriter(System.err); 

	/**
	 * Wraps the PrintStream designated by argument stream.
	 * @param stream the wrapped PrintStream
	 */
	public ConsoleWriter(PrintStream stream) {
		this.writer = stream;
	}
	
	/**
	 * Wraps the standard PrintStream System.out.
	 */
	public ConsoleWriter() {
		this.writer = System.out;
	}
	
	public void print(String s) {
		writer.print(s);			
	}

	public void println(String s) {
		writer.println(s);
	}

	public void close() {
		writer.close();
	}		
}
