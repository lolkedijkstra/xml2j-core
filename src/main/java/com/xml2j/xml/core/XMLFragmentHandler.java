package com.xml2j.xml.core;

/********************************************************************************
Copyright 2016, 2018 Lolke B. Dijkstra

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
import java.io.CharArrayWriter;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/* SAX dependencies */
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import com.xml2j.xml.parser.ParserTask;


/**
 * Base XML handler class. Handles XML fragment of type T.
 * 
 * @param <T>
 *            the type of element handled by this class
 * @author Lolke B. Dijkstra
 */
public abstract class XMLFragmentHandler<T extends ComplexDataType> extends
		DefaultHandler implements XMLContentHandler {
	
	Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * reference to the application context this XMLFragmentHandler belongs to.
	 */
	private ParserTask application = null;
	
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
	protected boolean doProcess(String childName) {
		String value = application.getConfiguration().getProperty(
				getPath() + "/" + childName + PROCESS);
		return value == null ? false : !value.equalsIgnoreCase(FALSE);
	}

	/**
	 * Finds out whether or not a child element is linked to its parent. The
	 * value (linking) defaults to true.
	 * 
	 * In the configuration file the property '@detach' is used to detach
	 * (unlink) the item. If the property is present and "true" the framework
	 * detaches (unlinks) the item from its parent.
	 * 
	 * Linking the item to its parent makes it 'long-lived' in memory, whereas
	 * not linking the item makes it 'short-lived'. Short-lived items are either
	 * ignored or processed (by setting the processing property for the item).
	 * 
	 * If the property '@detach' is set to "true" (ignored case) then (and
	 * only then) this method returns false. So, if the property is not present
	 * this method returns true and the item is linked.
	 * 
	 * @param childName
	 *            The name of the XML Element
	 * @return true if needs processing
	 */
	protected boolean doLink(String childName) { 
		String value = application.getConfiguration().getProperty(
				getPath() + "/" + childName + DETACH);
		return value == null ? true : value.equalsIgnoreCase(FALSE);
	}
	
	/**
	 * Get the XML path this handler is allocated to.
	 * @return the XPath path relative to document root
	 */
	public String getPath() {
		if (this.getParent() != null) {
			return this.getParent().getPath() + "/" + this.getXMLElementName();
		}
		return this.getXMLElementName();
	}

	/**
	 * Proxy for handler XMLFragmentHandler&lt;T&gt;.
	 * 
	 * @param T
	 *            the data type that is associated to the handler
	 */
	public abstract static class HandlerProxy<T extends ComplexDataType> {
		/**
		 * Allocator for XMLFragmentHandler&lt;T&gt;.
		 * 
		 * @param T
		 *            the data type that is associated to the handler
		 */
		public abstract static class Allocator<T extends ComplexDataType> {
			/**
			 * Creator method for derived type of XMLFragmentHandler&lt;T&gt;.
			 * 
			 * @param reader
			 *            (SAX) XML reader
			 * @param parentHandler
			 *            the parent handler (if any otherwise null)
			 * @param elementName
			 *            the name of the element it handles
			 * @param dataSetter
			 *            setter for data with parent (if any otherwise null)
			 * @param doProcess
			 *            indicates whether processing is active for handler
			 *            instance
			 * @return new instance of XMLFragmentHandler&lt;T&gt; derived class
			 */
			public abstract XMLFragmentHandler<T> create(ParserTask application, XMLReader reader,
					XMLFragmentHandler<?> parentHandler, String elementName,
					DataSetter dataSetter, boolean doProcess);
		}

		protected ParserTask application = null;
		/** the (SAX) XML Reader. */
		protected XMLReader reader = null;
		/** parent handler (null of handler is root). */
		protected XMLFragmentHandler<?> parent = null;
		/** name of element that is handled. */
		protected String elementName = null;
		/** the setter for attaching data to parent. */
		protected DataSetter dataSetter = null;
		/** the handler. */
		protected XMLFragmentHandler<T> handler = null;
		/** the allocator for the handler. */
		protected Allocator<T> handlerAllocator = null;
		/** indicates whether processing is active for this handler instance. */
		private boolean doProcess = false;
		
		void setDoProcess(boolean doProcess) {
			this.doProcess = doProcess;
		}

		/**
		 * Constructor for Allocator for XMLFragmentHandler.
		 * 
		 * @param reader
		 *            (SAX) XML reader
		 * @param parentHandler
		 *            the parent handler (if any otherwise null)
		 * @param elementName
		 *            the name of the element it handles
		 * @param dataSetter
		 *            setter for data with parent (if any otherwise null)
		 * @param allocator
		 *            allocator object for handler
		 * @param doProcess
		 *            indicates whether processing is active for handler
		 *            instance
		 */
		public HandlerProxy(ParserTask application, XMLReader reader,
				XMLFragmentHandler<?> parentHandler, String elementName,
				DataSetter dataSetter, Allocator<T> allocator, boolean doProcess) {
			assert (application != null);
			assert (reader != null);
			assert (elementName != null);
			assert (allocator != null);
			
			this.application = application;
			this.reader = reader;
			this.parent = parentHandler;
			this.elementName = elementName;
			this.dataSetter = dataSetter;
			this.handlerAllocator = allocator;
			this.doProcess = doProcess;
		}

		/**
		 * Find out whether handler was already instantiated.
		 * 
		 * @return true if handler exists, false otherwise
		 */
		public boolean hasHandler() {
			return handler != null;
		}

		
		/**
		 * Get handler associated to proxy.
		 * 
		 * @return handler instance
		 */
		public XMLFragmentHandler<T> getHandler() {
			if (handler == null) {
				handler = handlerAllocator.create(application, reader, parent, elementName,
						dataSetter, doProcess);
			}
			return handler;
		}
	}

	/** buffer for storing content. */
	private CharArrayWriter contents = new CharArrayWriter();
	/** XML reader. */
	private XMLReader reader = null;
	/** parent handler (null indicates handler is root). */
	private XMLFragmentHandler<?> parent = null;
	/** name of element that is handled. */
	private String elementName = null;
	/** allocator for type T. */
	private TypeAllocator<T> dataAlloctor = null;
	/** the setter for attaching data to parent. */
	private DataSetter dataSetter = null;
	/** data of type T. */
	private T data = null;
	/** handlers for children. */
	private Map<String, HandlerProxy<?>> handlers = new HashMap<>();
	/** indicates whether processing is active for this handler instance. */
	private boolean doProcess = false;
	/** indicates whether this is the root handler */
	private boolean isRoot = false;
	/** indicates whether this is the first time this handler is entered */
	private boolean isFirst = true;

	/**
	 * Create new data object of type T.
	 * 
	 * Creates new data object and attaches parent data to this object.
	 * 
	 * @return new data object of type T
	 */
	private T newDataObject() {
		ComplexDataType pData = getParent() != null ? (ComplexDataType) getParent()
				.getData()
				: null;
		return dataAlloctor.newInstance(elementName, pData);
	}

	/**
	 * Reset the content of element.
	 * 
	 */
	private void reset() {
		// get new data object..
		data = newDataObject();

		// reset internal buffer..
		getContents().reset();

		// reset children..
		for (HandlerProxy<?> h : handlers.values()) {
			if (h.hasHandler()) {
				h.getHandler().reset();
			}
		}
	}

	/**
	 * @return XMLReader for this XMLFragment
	 */
	public XMLReader getReader() {
		return reader;
	}

	/**
	 * @return Parent of this content handler
	 */
	public XMLFragmentHandler<?> getParent() {
		return parent;
	}

	/**
	 * @return Setter for setting data to parent
	 */
	public DataSetter getParentDataSetter() {
		return dataSetter;
	}

	/**
	 * Constructor.
	 * 
	 * @param reader
	 *            the XMLReader
	 * @param parentHandler
	 *            the handler of the parent of this element
	 * @param elementName
	 *            the element that this handler handles
	 * @param dataAllocator
	 *            the creator of elements of type T
	 * @param dataSetter
	 *            helper for setting data at parent
	 * @param doProcess
	 *            indicates whether processing is activated for this handler
	 *            instance
	 */
	protected XMLFragmentHandler(ParserTask application, XMLReader reader,
			XMLFragmentHandler<?> parentHandler, String elementName,
			TypeAllocator<T> dataAllocator, DataSetter dataSetter,
			boolean doProcess) {
		assert (application != null);
		assert (reader != null);
		assert (elementName != null);
		assert (dataAllocator != null);

		this.application = application;
		this.reader = reader;
		this.parent = parentHandler;
		this.elementName = elementName;
		this.dataAlloctor = dataAllocator;
		this.dataSetter = dataSetter;
		this.data = newDataObject();
		this.doProcess = doProcess;
		this.isRoot = (parent == null);
	}

	/** {@inheritDoc} */
	public void characters(char[] c, int off, int len) throws SAXException {
		contents.write(c, off, len);
	}

	/** activates this handler, so new events are routed here. */
	public void activate() {
		reader.setContentHandler(this);
		reset();
	}

	/** deactivates this handler and passes control back to parent. */
	public void deactivate() {
		if (getParent() != null) {
			reader.setContentHandler(getParent());
		}
	}

	/**
	 * @return the internal CharArrayWriter.
	 */
	protected CharArrayWriter getContents() {
		return contents;
	}

	/**
	 * @return the value (String) of the content.
	 */
	public String getValue() {
		return contents.toString();
	}

	/**
	 * Overwrite the content of the element.
	 * 
	 * @param data
	 *            new content of element
	 */
	public void setValue(String data) {
		contents.reset();
		contents.write(data, 0, data.length());
	}

	/**
	 * @return the data collected by this handler.
	 */
	public T getData() {
		return data;
	}

	/**
	 * Set the data associated to handler. The setter is used to export XML
	 * data.
	 * 
	 * @param data
	 *            the data to set
	 */
	public void setData(T data) {
		this.data = data;
	}

	/**
	 * @return the element name
	 */
	public String getXMLElementName() {
		return elementName;
	}

	/**
	 * Registers a child handler proxy with this handler.
	 * 
	 * @param proxy
	 *            for the child handler
	 */
	protected void registerHandler(HandlerProxy<?> proxy) {
		handlers.put(proxy.elementName, proxy);
	}

	/** {@inheritDoc} */
	public void startElement(String uri, String localName, String name,
			Attributes atts) throws SAXException {

		if (logger.isTraceEnabled())
			logger.trace("uri = {}, localName = {}, name = {}, atts = {}", uri, localName, name, atts);

		/*
		 * if root element contains element with same name, prevent going into that the first time
		 * this is because for root the opening tag is consumed here. for child elements the opening tag
		 * is consumed in the parent.
		 * 
		 * else get the registered child handler proxy
		 */
		HandlerProxy<?> proxy = isRoot && isFirst ? null : handlers.get(localName);
		isFirst = false;

		/*  
		 * pass control to the child handler
		 */
		if (proxy != null) {
			XMLFragmentHandler<?> handler = proxy.getHandler();
			handler.activate();
			handler.handleAttributes(atts);
			handler.process(XMLEvent.START);
		} else {  
		/*
		 * root data handling
		 */
			if (isRoot) {
				this.handleAttributes(atts);
				this.process(XMLEvent.START);
			} else {
				this.contents.reset();
			}
		}
	}

	/**
	 * handle the element.
	 */
	protected void handleElement() {
		// return control to parent handler..
		this.deactivate();

		// get content of this item..
		getData().setContent(this.getValue());

		// attach data to parent (if parent data setter is found)..
		DataSetter setter = getParentDataSetter();
		if (setter != null) {
			setter.set(getData());
		}

		// process data if required..
		if (doProcess()) {
			process(XMLEvent.END);
		}
	}


	/**
	 * Attach attributes to the element data.
	 * 
	 * @param atts
	 *            the attributes to be handled (if any)
	 */
	private void handleAttributes(Attributes atts) {
		T element = getData();
		int sz = atts.getLength();
		for (int i = 0; i != sz; i++) {
			element.setAttr(atts.getLocalName(i), atts.getValue(i));
		}
	}
	
	/**
	 * Checks whether processing is active for this handler.
	 * @return true if active, false otherwise
	 */
	private boolean doProcess() {
		return doProcess;
	}

	/**
	 * Perform some arbitrary processing.
	 * 
	 * This method is intended to connect to an application specific processor.
	 * 
	 * @param evt
	 *            indicates whether the process is called from START
	 *            (startElement) or END (endElement)
	 */
	private void process(XMLEvent evt) throws ProcessorException {
		if (doProcess) {
			MessageProcessor processor = application.getDataProcessor();
			if (processor == null) {
				throw new ProcessorException("No processor set!");
			}
			processor.process(evt, this.data);
		}
	}
}
