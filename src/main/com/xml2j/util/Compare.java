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

/**
 * Class for comparing objects (and native types).
 * 
 * @author Lolke B. Dijkstra
 */
public final class Compare {
	private Compare() {}
	/**
	 * Compares 2 objects p and q, these may also be null.
	 * @param p left object
	 * @param q right object
	 * @return true if p.equals(q) or both p and q == null
	 */
	static public boolean equals(Object p, Object q) {	
		if (p==null)
			return (q==null);
		return p.equals(q);	
	}
	
	static public boolean equals(boolean p, boolean q) {
		return p==q;
	}

	static public boolean equals(byte p, byte q) {
		return p==q;
	}
	
	static public boolean equals(char p, char q) {
		return p==q;
	}
	
	static public boolean equals(int p, int q) {
		return p==q;
	}
	
	static public boolean equals(float p, float q) {
		return p==q;
	}
	
	static public boolean equals(long p, long q) {
		return p==q;
	}
	
	static public boolean equals(double p, double q) {
		return p==q;
	}
	
}		
