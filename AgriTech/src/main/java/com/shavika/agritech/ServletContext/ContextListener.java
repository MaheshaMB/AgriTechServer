package com.shavika.agritech.ServletContext;

import java.io.File;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

@WebListener("application context listener")
public class ContextListener implements ServletContextListener {

	private final String START_MSG = "Application started with Context Initialized ...";
	private final String STOP_MSG = "Application Stoped with Context Destroyed ...";

	/**
	 * Initialize log4j when the application is being started
	 */
	@Override
	public void contextInitialized(ServletContextEvent event) {
		try {
			ServletContext context = event.getServletContext();
			String log4jConfigFile = context.getInitParameter("log4j-config-location");
			String fullPath = context.getRealPath("") + File.separator + log4jConfigFile;
			PropertyConfigurator.configure(fullPath);
			Logger LOGGER = Logger.getLogger(ContextListener.class);
			LOGGER.info(START_MSG + ".................");
		} catch (Exception e) {
			System.out.println("Failed to load... LOGGER file....");
		}
	}

	/**
	 * Destroying application started functions.
	 */
	@Override
	public void contextDestroyed(ServletContextEvent event) {
		Logger LOGGER = Logger.getLogger(ContextListener.class);
		LOGGER.info(STOP_MSG + ".................");
	}
}