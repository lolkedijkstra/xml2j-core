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
import java.util.ArrayList;

/**
 *	This class is required by the code generator.
 *	The code generator uses StringList to encapsulate a sequence of the same simple element. 
 */
public class StringList extends ArrayList<String> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String elementName = "";

	/**
	 * Constructor.
	 * @param elementName the name of the xml element.
	 */
	public StringList(final String elementName) {
		this.elementName  = elementName;
	}
	
	/**
	 * Equals compares this and that.
	 * @return true if the contents of this and that is identical, otherwise false
	 */
	public boolean equals(Object that) {
		if (!super.equals(that))
			return false;

		if (!getClass().equals(that.getClass()))
			return false;
		
		return this.elementName.equals(((StringList)that).elementName);
	}
	
	/**
	 * Method for printing the list.
	 * @param out where to print
	 */
	public void print(Printer out) {
		for (String item : this) {
			out.print("<" + elementName + ">");
			out.print(item);				
			out.print("</" + elementName + ">");
		}
	}

	
//	public static void main(String args[]) {
//		final String fileId = System.getenv("LDX_HOME") + "/tmp/stringlist.ser";
//		
//		StringList sl1 = new StringList("count");
//		sl1.add(new String("one"));
//		sl1.add(new String("two"));
//		sl1.add(new String("three"));
//		sl1.add(new String("four"));
//		sl1.add(new String("five"));
//		
//		com.ldx.extra.Serialize.write( sl1, fileId );
//		StringList sl2 = (StringList)com.ldx.extra.Serialize.read(fileId);
//
//		if (sl1.equals(sl2))
//			sl2.print(new PrintStreamer(System.out));
//		else 
//			System.err.println("not equal");
//	}
}