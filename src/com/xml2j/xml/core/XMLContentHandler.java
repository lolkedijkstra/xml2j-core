package com.xml2j.xml.core;

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
import org.xml.sax.ContentHandler;

/**
 * This specialized ContentHandler interface adds operations for bookkeeping
 * (activate and deactivate). The operations serve two purposes
 * 
 */
public interface XMLContentHandler extends ContentHandler, ConfigurationContants {
	/**
	 * This method is called on activation of the handler. The method is
	 * responsible for: (1) resetting the handler so it can be reused to handle
	 * multiple instances of the associated type (2) connecting the handler to
	 * the XMLReader, so the events are routed there.
	 */
	public void activate();

	/**
	 * This method is called on deactivation of the handler. The method
	 * reconnects the handler's parent to the XMLReader, so the events are
	 * routed to the parent again.
	 */
	public void deactivate();
}
