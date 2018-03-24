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
import org.slf4j.Logger;

/**
 * This exception is thrown by the processor if it cannot recover from errors and
 * the current run should be aborted.
 * This class may be adapted to hook up logging or alt. this exception can be caught
 * and logged.
 * 
 * The main catches this exception.
 *
 */
public class ProcessorException extends RuntimeException {
	private static final Logger logger = XMLMessageHandler.logger;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * embedded exception
	 */
	private Exception exception = null;
	
	/**
	 * Constructor of ProcessorException
	 * @param msg error msg
	 */
	public ProcessorException(String msg) {
		super("ProcessorException: " + msg);
		logger.error("ProcessorException: {}", msg);		
	}
	/**
	 * Constructor of ProcessorException
	 * @param e embedded error
	 */

	public ProcessorException(Exception e) {
		super("ProcessorException: " + e.getMessage());
		exception = e;
		logger.error("ProcessorException: {}", e);
	}
	/**
	 * Constructor of ProcessorException
	 * @param e embedded exception
	 * @param msg special message
	 */
	public ProcessorException(Exception e, String msg) {
		super("ProcessorException: " + msg);
		logger.error("ProcessorException: {}", msg);
	}
	
	/**
	 * @return the embedded error
	 */
	public Exception getException() {
		return exception;
	}
}
