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
import java.io.FileInputStream;
import java.io.IOException;

import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import com.xml2j.xml.parser.ParserTask;

/**
 * This class is the entry point class that is used in the client.
 * @param <T> the specific type of element to be handled
 */
public abstract class XMLMessageHandler<T extends ComplexDataType> implements MessageHandler {
	static final int MSG_NO_INPUT = 0;
	static final int MSG_NO_SCHEMA = 1;
	
	static final String[] msg = {
		"Could not process XML data. Reason: InputSource was not set.",
		"Could not perform validation. Reason: Schema file was not set."
	};
	
	static final Logger logger = LoggerFactory.getLogger(XMLMessageHandler.class);

	
	/** The (SAX) XML reader. */
	private XMLReader reader = null;

	/** The InputSource that embeds the XML data. */
	private InputSource inputSource = null;

	/** SchemaFactory capable of understanding WXS schemas. */
	private final SchemaFactory schemaFactory = SchemaFactory
			.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);

	/** WXS Schema validator. */
	private Validator validator = null;

	/** The XML Handler class. */
	private XMLFragmentHandler<?> handler;

	/**
	 * Base constructor.
	 * 
	 * @param reader
	 *            The XML Reader (SAX 2.0)
	 * @param handler
	 *            The fragment handler of the specific type set by descendant
	 *            constructor
	 */
	public XMLMessageHandler(XMLReader reader, XMLFragmentHandler<?> handler) {
		this.reader = reader;
		this.handler = handler;
		reader.setContentHandler(handler);
	}

	/**
	 * Finds out whether or not processing is activated for an XML element. The
	 * value (processing) defaults to false.
	 * 
	 * In the configuration file the property '@process' is used to activate
	 * processing. If the property is present and "true" the framework invokes
	 * the user provided processor for the item.
	 * 
	 * If the property '@process' is set to "true" (ignored case) then (and only
	 * then) this method returns true. So, if the property is not present it is
	 * false.
	 * 
	 * 
	 * @param childName
	 *            The name of the XML Element
	 * @return true if needs processing
	 */
	static protected boolean doProcess(String childName, ParserTask context) {
		String value = context.getConfiguration().getProperty(
				childName + PROCESS);
		return value == null ? false : !value.equalsIgnoreCase(FALSE);
	}
	
	
	/**
	 * Sets the XML document.
	 * 
	 * @param input
	 *            The InputSource that embeds the XML document
	 */
	@Override
	public void setInputSource(InputSource input) {
		inputSource = input;
	}

	/**
	 * Sets the XML document.
	 * 
	 * @param input
	 *            The FileInputStream that embeds the XML document
	 */
	@Override
	public void setInputSource(FileInputStream input) {
		inputSource = new InputSource(input);
	}

	/**
	 * Reads the XML document and creates an internal object representation.
	 * 
	 * @throws IOException
	 *             InputSource is not valid
	 * @throws SAXException
	 *             Error reported by the SAX engine
	 */
	@Override
	public void process() throws IOException, SAXException {
		if (inputSource == null) {
			logger.error(msg[MSG_NO_INPUT]);
			throw new ParserConfigurationException(msg[MSG_NO_INPUT]);
		}
		reader.parse(inputSource);
	}

	/**
	 * Set the WXS Schema (XSD) file for validation.
	 * 
	 * @param schemaFile
	 *            the WXS Schema file (XSD)
	 * @throws SAXException
	 *             Could not create schema
	 */
	@Override
	public void setSchema(Source schemaFile) throws SAXException {
		Schema schema = schemaFactory.newSchema(schemaFile);
		validator = schema.newValidator();
	}

	/**
	 * Set the WXS Schema (XSD) files for validation.
	 * 
	 * @param schemaFiles
	 *            the WXS Schema files (XSD)
	 * @throws SAXException
	 *             Could not create schema
	 */
	@Override
	public void setSchema(Source[] schemaFiles) throws SAXException {
		Schema schema = schemaFactory.newSchema(schemaFiles);
		validator = schema.newValidator();
	}

	/**
	 * Validate XML file according to XSD.
	 * 
	 * @param xml
	 *            the XML file to be validated
	 * @throws IOException
	 *             The XML document could not be accessed
	 * @throws SAXException
	 *             The XML document failed validation
	 */
	@Override
	public void validate(Source xml) throws SAXException, IOException {
		if (validator == null) {
			logger.error(msg[MSG_NO_SCHEMA]);
			throw new RuntimeException(msg[MSG_NO_SCHEMA]);
		}
		validator.validate(xml);
	}

	/**
	 * @return The data object.
	 */
	public ComplexDataType getData() {
		return handler.getData();
	}
	
}
