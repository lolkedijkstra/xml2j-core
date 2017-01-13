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
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xml2j.util.Printer;


/**
 * ComplexDataType class is the base class for all complex data types.
 * @author Lolke B. Dijkstra
 */
public abstract class ComplexDataType implements DataType, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final Logger logger = LoggerFactory.getLogger(ComplexDataType.class);
	
	/** name of the element. */
	private String xmlElementName = null;
	/** list of attributes of element. */
	private final Map<String, String> atts = new HashMap<>();
	/** content of element. */
	private String content = null;
	/** parent data. */
	private ComplexDataType parent = null;
	
	/**
	 * {@inheritDoc}
	 */
	public boolean equals(Object that) {
		if (that == null) {
			if (logger.isTraceEnabled())
				logger.trace("Passing null to equals");
			return false;
		}
		
		if (!getClass().equals(that.getClass())) {
			if (logger.isTraceEnabled())
				logger.trace("this.type {} not equal that.type {}", this.getClass(), that.getClass());
			return false;
		}

		if (xmlElementName != null
				&& !xmlElementName
						.equals(((ComplexDataType) that).xmlElementName)) {
			if (logger.isTraceEnabled())
				logger.trace("this.xmlElementName {} is not equal that.xmlElementName {}", this.xmlElementName, ((ComplexDataType) that).xmlElementName);
			return false;
		}

		if (content != null
				&& !content.equals(((ComplexDataType) that).content)) {
			if (logger.isTraceEnabled())
				logger.trace("this.content {} is not equal that.content {}", this.content, ((ComplexDataType) that).content);
			return false;
		}

		if (!atts.equals(((ComplexDataType) that).atts)) {
			if (logger.isTraceEnabled())
				logger.trace("this.atts {} is not equal that.atts {}", this.atts, ((ComplexDataType) that).atts);
			return false;
		}

		return true;	
	}
	
	/**
	 * Constructor.
	 * @param tagName the element name used for the element
	 * @param parent the parent of the element (or null)
	 */
	public ComplexDataType(String tagName, ComplexDataType parent) {
		assert (tagName != null);

		if( logger.isTraceEnabled())
			logger.trace("ComplexDataType({},{})", tagName, parent);
		
		this.xmlElementName = tagName;
		this.parent = parent;
	}

	/**
	 * Get the name of the element.
	 * @return name of the element
	 */
	public String getXMLElementName() {
		return xmlElementName;
	}
	
	/**
	 * Get list of attributes.
	 * @return map containing attributes
	 */
	public final Map<String, String> getAttributes() {
		return atts;
	}
	
	/**
	 * Add an attribute value for this type.
	 * 
	 * @param attrName name of the attribute
	 * @param data content of the attribute
	 */
	protected void setAttr(String attrName, String data) {
		if( logger.isTraceEnabled())
			logger.trace("setAttr({},{})", attrName, data);

		atts.put(attrName, data);
	}
	
	/**
	 * Retrieve the attribute value corresponding to attrName.
	 * 
	 * @param attrName the name of the attribute
	 * @return it's value
	 */
	protected String getAttr(String attrName) {
		return atts.get(attrName);
	}
	

	/**
	 * Print this element to output.
	 * @param out where to print
	 */
	public void print(Printer out) {
		printStart(out);
		
		printContents(out);

		printElements(out);
		
		printStop(out);
	}
	
	
	
	private static final String open_b = "<";
	private static final String open_e = ">";
	private static final String att_b = " ";
	private static final String att_e = "\"";
	private static final String att_s = "=\"";
	private static final String close_b = "</";
	private static final String close_e = ">\n";

	/**
	 * Print opening tag and attributes of element.
	 * @param out where to print
	 */
	protected void printStart(Printer out) {
		out.print(open_b + getXMLElementName());
		for( Map.Entry<String, String> entry : atts.entrySet() ) {
			out.print(att_b + entry.getKey() + att_s + entry.getValue() + att_e);	
		}
		out.print(open_e);
	}
	
	/**
	 * Print closing tag of element.
	 * @param out where to print
	 */
	protected void printStop(Printer out) {
		out.print(close_b + getXMLElementName() + close_e);
	}

	/**
	 * Print content of this item.
	 * @param out where to print
	 */
	protected void printContents(Printer out) {
		if (content != null && !content.equals("")) {
			out.print(content);
		}
	}	
	
	/**
	 * Print the elements contained in this element.
	 * @param out where to print
	 */
	protected void printElements(Printer out) {
		// To enable printing printElements must be overloaded for derived classes
		// This is done by generating code with option -p
	}
	
	
	/**
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content.trim();
	}

	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}
		
	/**
	 * @return the parent of this element
	 */
	public ComplexDataType getParent() {
		return parent;
	}
}
