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
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 *	This class encapsulates the Configuration, which is needed by the parser at startup.
 *	The configuration options helps to tune the runtime performance deployment time. 
 */
public class ParserConfiguration {
	static final int MSG_INVALID = 0;
	static final int MSG_NO_PROPERTIES = 1;
	static final int MSG_ERR_LOG = 2;
	
	static final String[] msg = {
		"XML2J Configuation: invalid or missing data.",
		"Could not process XML data. Reason: No properties found.",
		"Unable to log parserConfiguration."
	};

	static Logger logger = LoggerFactory.getLogger(ParserConfiguration.class);
	
	/** first line in properties file must contain #XML2J-PROPERTIES. */
	static final String PROPERTIES_FILE = "#XML2J-PROPERTIES";
	
	/** the properties bag. */
	private Properties properties = null;
	
	/** config the name of the configuration file. */
	private String propertiesFile = null;

	/** constructor. */
	public ParserConfiguration(String config) {
		this.propertiesFile = config;
	}


	/**
	 * @return true if configuration was loaded, false otherwise
	 */
	synchronized public boolean isLoad() {
		return properties != null && !properties.isEmpty();
	}

	/**
	 * Load the configuration from the input designated by config.
	 * @throws ParserConfigurationException configuration file is either invalid or empty
	 * @throws FileNotFoundException configuration file is missing
	 * @throws IOException input error reading configuration file
	 */
	synchronized public void load() throws ParserConfigurationException, FileNotFoundException, IOException {
		assert( propertiesFile != null);

		if (isLoad())
			return;

		logger.trace("Loading configuation: {}", propertiesFile);

		BufferedReader reader = new BufferedReader(new FileReader(propertiesFile));
		String line = reader.readLine();
		reader.close();
		if (line == null || !line.startsWith(PROPERTIES_FILE)) {
			throw new ParserConfigurationException( msg[MSG_INVALID] );
		}

		read(new FileInputStream(propertiesFile));
		log();
	}


	synchronized private void log() {
		StringWriter writer = new StringWriter();
		writer.append('\n');
		try {
			this.write(writer, propertiesFile);
			logger.info(writer.getBuffer().toString());
		} catch (IOException e) {
			logger.error(msg[MSG_ERR_LOG]);
		} finally {
			try {
				writer.close();
			} catch (IOException e) {}
		}
	}	
	
	/**
	 * Reads the configuration from the input.
	 * @param input the input stream containing the properties file
	 * @throws IOException if input invalid (i.e. file cannot be opened)
	 */
	private void read(InputStream input) throws IOException {
		assert( input != null);

		if (properties == null) {
			properties = new Properties();
		}

		properties.load(input);
		input.close();
	}

	/**
	 * Writes the configuration to the output.
	 * @param output 	the stream where the properties are written
	 * @param comments 	a description of the property list
	 * @throws IOException output does not represent a valid output stream
	 */
	synchronized public void write(OutputStream output, String comments) throws IOException {
		assert( output != null);

		if (properties != null) {
			properties.store(output, comments);
		}
	}
	
	/**
	 * Writes the configuration to the output.
	 * @param output 	the writer where the properties are written
	 * @param comments 	a description of the property list
	 * @throws IOException IO error writing to output stream
	 */
	synchronized public void write(Writer output, String comments) throws IOException {
		assert( output != null);
		
		if (properties != null) {
			properties.store(output, comments);
		}
	}
	
	/**
	 * Get the specified property from the property bag.
	 * @param key the key under which the property is stored
	 * @return the value associated with the key
	 */
	synchronized public String getProperty(String key) {
		if (!isLoad()) {
			throw new ParserConfigurationException( msg[MSG_NO_PROPERTIES] );
		}
		return properties.getProperty(key);
	}
}
