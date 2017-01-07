package com.xml2j.util;
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
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Simple thread manager.
 * This class is included for demonstration purposes and not intended for use in production applications.
 * Monitors worker threads and waits for completion.
 * @author Dijkstra
 */
public class DefaultThreadManager {
	static final int MILLIS = 1000;
	static Logger logger = LoggerFactory.getLogger(DefaultThreadManager.class);
	List<Thread> threads = new ArrayList<>();
	
	public void addTask(String name, Runnable task) {
		Thread t = new Thread(task);
		t.setName(name);
		threads.add(t);
	}

	public void monitorThreads() {
		int running;
		do {
			running = 0;
			for (Thread thread : threads) {
				if (thread.isAlive()) {
					running++;
				}
			}
			logger.info("{} worker {} running.", running, (running == 1 ? "thread" : "threads"));
			try {
				Thread.sleep(MILLIS);
			} catch (InterruptedException e) {}
		} while (running > 0);

		logger.info("{} worker {} running.", running, (running == 1 ? "thread" : "threads"));
	}

	public void startThreads() {
		for (Thread t : threads) {
			logger.info("starting: {}", t.getName());
			t.start();
		}
	}

};