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
/**
 * The MessageProcessor is responsible for processing the data that is received
 * from the Reader component.
 */
public interface MessageProcessor {
	/**
	 * Process the data. The implementation class implements the process
	 * customized to the domain. Note that it is up to the implementor to select
	 * the appropriate processing model (e.g synchronous, asynchronous,
	 * threading model etc.).
	 * 
	 * @param evt
	 *            The XML event that causes the invocation (START | END) or
	 *            processing the data.
	 * @param data
	 *            The data that is sent by the reader
	 * @throws ProcessorException
	 *             If the processing cannot recover from error it throws this
	 *             exception. Typically the processor catches all unrecoverable
	 *             errors in the outer most scope of the process method and
	 *             throws a ProcessingException
	 * 
	 */
	public void process(XMLEvent evt, ComplexDataType data)
			throws ProcessorException;
}
