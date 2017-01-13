package com.xml2j.xml.parser;
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
import java.io.IOException;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;

import com.xml2j.xml.core.ParserConfiguration;
import com.xml2j.xml.core.ParserConfigurationException;

public abstract class ParserRunnable extends ParserTask implements Runnable {
	
	/**
	 * Constructor of the parser task.
	 * Use the call to super to pass a parameter to a custom ErrorHandler implementation
	 * simply implement the interface: org.xml.sax.ErrorHandler, instantiate the custom ErrorHandler
	 * and pass it to the super constructor.
	 * @param configuration the runtime configuration 
	 * @throws org.xml.sax.SAXException
	 * @throws ParserConfigurationException 
	 * @throws IOException 
	 */
	public ParserRunnable(ParserConfiguration configuration) throws SAXException, ParserConfigurationException, IOException {
		super(configuration);
	}
	/**
	 * Constructor of the parser task.
	 * Use the call to super to pass a parameter to a custom ErrorHandler implementation
	 * simply implement the interface: org.xml.sax.ErrorHandler, instantiate the custom ErrorHandler
	 * and pass it to the super constructor.
	 * @param configuration the runtime configuration 
	 * @param errorHandler the SAX error handler
	 * @throws org.xml.sax.SAXException
	 * @throws ParserConfigurationException 
	 * @throws IOException 
	 */
	public ParserRunnable(ParserConfiguration configuration, ErrorHandler errorHandler) throws SAXException, ParserConfigurationException, IOException {
		super(configuration, errorHandler);
	}

	@Override
	public void run() {
		try {
			if( logger.isTraceEnabled())
				logger.trace("Calling processXML() from {}", this.getClass());

			processXML();
		} catch (IOException | SAXException e) {
			logger.error("ParserWorker aborted: {}", e.getMessage());
		}
	}	 
}