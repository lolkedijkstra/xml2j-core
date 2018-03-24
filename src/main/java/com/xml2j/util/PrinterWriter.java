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
import java.io.PrintWriter;

/**
 * PrinterWriter writes to a PrintWriter.
 * This class is used by the framework.
 * 
 * @author Lolke B. Dijkstra
 */
public class PrinterWriter implements Printer {
	private PrintWriter writer;

	/**
	 * Wraps the PrintWriter designated by writer.
	 * @param writer the wrapped PrintWriter
	 */
	public PrinterWriter(PrintWriter writer) {
		this.writer = writer;
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

