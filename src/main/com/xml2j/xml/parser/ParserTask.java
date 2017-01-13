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
import java.io.InputStream;

import javax.xml.transform.stream.StreamSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import com.xml2j.xml.core.MessageHandler;
import com.xml2j.xml.core.MessageProcessor;
import com.xml2j.xml.core.ParserConfiguration;
import com.xml2j.xml.core.ParserConfigurationException;

/**
 * Base class for creation of application glue.
 * The project's message-handler-application type extends this type.
 * @author Lolke B. Dijkstra
 *
 */
public abstract class ParserTask {
	public final Logger logger = LoggerFactory.getLogger(getClass());
	
	private ErrorHandler errorHandler = null;

	private ParserConfiguration configuration = null;
	private XMLReader reader = null;
	protected MessageHandler handler = null;
	
	private void initialize() throws ParserConfigurationException, IOException {
		if( logger.isTraceEnabled())
			logger.trace("reader: {}, handler: {}", errorHandler, reader);

		this.configuration.load();
		this.reader.setErrorHandler( this.errorHandler );
		this.handler = getMessageHandler( this.reader );
	}

	public ParserConfiguration getConfiguration() {
		return configuration;
	}
	
	/**
	 * ParserApplication default constructor.
	 * The default constructor relies on default implementations of XMLReader and ErrorHandler.
	 * @throws IOException 
	 * @throws ParserConfigurationException 
	 */
	protected ParserTask(ParserConfiguration configuration) throws SAXException, ParserConfigurationException, IOException {
		assert(configuration != null);
		this.configuration = configuration;
		this.errorHandler = new ParserDefaultErrorHandler();
		this.reader  = XMLReaderFactory.createXMLReader();
		initialize();
	}
	
	/**
	 * ParserApplication constructor overriding the default ErrorHandler.
	 * 
	 * @param errorHandler the custom implementation of the org.xml.sax.ErrorHandler interface
	 * @throws SAXException 
	 * @throws IOException 
	 * @throws ParserConfigurationException 
	 */
	protected ParserTask(ParserConfiguration configuration, ErrorHandler errorHandler) throws SAXException, ParserConfigurationException, IOException {
		assert(configuration != null && errorHandler != null);
		this.configuration = configuration;
		this.errorHandler = errorHandler;
		this.reader  = XMLReaderFactory.createXMLReader();  
		initialize();
	}
	

	/**
	 * This method creates and returns the specific MessageHandler.
	 * The purpose of this method is to hide the specific type from the implementation.
	 * 
	 * @param reader XML SAX reader
	 * @return MessageHandler
	 */
	protected abstract MessageHandler getMessageHandler(XMLReader reader);

	
	/**
	 * Returns the ErrorHandler used by the parser.
	 * 
	 * @return ErrorHandler
	 */
	public ErrorHandler getErrorHandler() {
		return errorHandler;
	}


	/**
	 * Validate the XML against XML Schema.
	 * @param input InputStream of XML document
	 * @param schema InputStream of XML Schema
	 * @throws IOException input and/or schema are not valid input streams
	 * @throws SAXException XML validation error
	 */
	public void validateXML(InputStream input, InputStream schema) throws IOException, SAXException {
		assert (this.reader != null);

		if( logger.isTraceEnabled())
			logger.trace("validateXML( input: {}, schema: {} )", input, schema);

		
		// validate input against schema..
		handler.setSchema(new StreamSource(schema));
		handler.validate(new StreamSource(input));
	}

	/**
	 * Validate the XML against XML Schemas.
	 * @param input InputStream of XML document
	 * @param schemas InputStream of XML Schema
	 * @throws IOException input and/or schema are not valid input streams
	 * @throws SAXException XML validation error
	 */
	public void validateXML(InputStream input, InputStream[] schemas) throws IOException, SAXException {
		assert (this.reader != null && schemas != null && schemas.length > 0);
		
		if( logger.isTraceEnabled())
			logger.trace("validateXML( input: {}, schemas: {} )", input, schemas);
		
		// wrap schemas in streamSources
		StreamSource [] schemasAsStreamSource = new StreamSource[schemas.length];
		int i = 0;
		for (InputStream schema : schemas) {
			schemasAsStreamSource[i++] = new StreamSource(schema);
		}
		
		// validate input against schemata..
		handler.setSchema(schemasAsStreamSource);
		handler.validate(new StreamSource(input));
	}	
	
	private MessageProcessor processor = null;
	public MessageProcessor getDataProcessor() { return processor; }
	public MessageHandler getMessageHandler() { return handler; }
	
	/**
	 * Prepare process the XML document.
	 * @param input InputStream to XML document
	 * @param dataProcessor Custom implementation of DataProcessor interface
	 * @throws IOException input is not a valid inputstream
	 * @throws SAXException error reported by SAX Engine during processing of input
	 */
	public void prepareStart(InputStream input, MessageProcessor dataProcessor) {
		assert (reader != null && input != null && dataProcessor != null);

		if( logger.isTraceEnabled())
			logger.trace("ProcessXML( input: {}, dataProcessor: {} )", input, dataProcessor);

		// connect to processor..
		processor = dataProcessor;
		// connect input source..
		handler.setInputSource(new InputSource(input));
	}
	
	/**
	 * Prepare process the XML document.
	 * @param input InputSource to XML document
	 * @param dataProcessor Custom implementation of DataProcessor interface
	 * @throws IOException input is not a valid inputsource
	 * @throws SAXException error reported by SAX Engine during processing of input
	 */
	public void prepareStart(InputSource input, MessageProcessor dataProcessor) {
		assert (reader != null && input != null && dataProcessor != null);

		if( logger.isTraceEnabled())
			logger.trace("prepareStart( input: {}, dataProcessor: {} )", input, dataProcessor);

		// connect to processor..
		processor = dataProcessor;
		// connect input source..
		handler.setInputSource(input);
	}

	/**
	 * Process the XML message.
	 * @throws IOException
	 * @throws SAXException
	 */
	public void processXML() throws IOException, SAXException{
		handler.process();
	}

}
