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

import javax.xml.transform.Source;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public interface MessageHandler extends ConfigurationContants {
	
	/**
	 * Sets the XML document.
	 * 
	 * @param input
	 *            The InputSource that embeds the XML document
	 */
	public void setInputSource(InputSource input);

	/**
	 * Sets the XML document.
	 * 
	 * @param input
	 *            The FileInputStream that embeds the XML document
	 */
	public void setInputSource(FileInputStream input);

	/**
	 * Reads the XML document and creates an internal object representation.
	 * 
	 * @throws IOException
	 *             InputSource is not valid
	 * @throws SAXException
	 *             Error reported by the SAX engine
	 */
	public void process() throws IOException, SAXException;

	/**
	 * Set the WXS Schema (XSD) file for validation.
	 * 
	 * @param schemaFile
	 *            the WXS Schema file (XSD)
	 * @throws SAXException
	 *             Could not create schema
	 */
	public void setSchema(Source schemaFile) throws SAXException;

	/**
	 * Set the WXS Schema (XSD) files for validation.
	 * 
	 * @param schemaFiles
	 *            the WXS Schema file (XSD)
	 * @throws SAXException
	 *             Could not create schema
	 */
	public void setSchema(Source[] schemaFiles) throws SAXException;

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
	public void validate(Source xml) throws SAXException, IOException;

}