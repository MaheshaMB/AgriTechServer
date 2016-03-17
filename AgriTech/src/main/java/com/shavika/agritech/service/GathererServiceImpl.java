package com.shavika.agritech.service;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.util.PDFTextStripper;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.shavika.agritech.api.exception.AgriTechAppException;
import com.shavika.agritech.db.dto.ReservoirLevel;
import com.shavika.agritech.utils.PropertyReader;

public class GathererServiceImpl implements GathererService {

	private WebDriver driver = null;
	private String URL = null;
	private FileWriter fstream = null;
	private static BufferedWriter out = null;
	private static String PAGE_PRINT_SCREEN_FILE = PropertyReader.getPropertyValue("Application", "pageprint.screen");

	private static final String tableHeader = "<table cellpadding=\"10\" border=\"1\" width=\"100%\"><tr><td width=\"20%\"></td><td width=\"80%\">Welcome to Scraping tool</td></tr>";
	private static final String tablefooter = "<\table>";

	public GathererServiceImpl() throws AgriTechAppException, IOException {
		driver = new FirefoxDriver();
		fstream = new FileWriter((System.getProperty("user.dir") + PAGE_PRINT_SCREEN_FILE), true);
		out = new BufferedWriter(fstream);
		out.write(tableHeader);
	}

	public void openURL(String url) throws AgriTechAppException, IOException {
		scraptext("Started scraping...");
		this.URL = url;
		driver.navigate().to(URL);
		waitTime(5000);
		scrapScreen();
	}

	public List<ReservoirLevel> getreservoirlevel() throws AgriTechAppException, IOException {
		PDFParser parser = null;
		List<ReservoirLevel> rlData = null;
		try {
			scraptext("Started scraping...");
			rlData = new ArrayList<ReservoirLevel>();
			URL url = new URL(driver.getCurrentUrl());
			BufferedInputStream fileToParse = new BufferedInputStream(url.openStream());

			parser = new PDFParser(fileToParse);
			parser.parse();

			String output = new PDFTextStripper().getText(parser.getPDDocument());
			String[] strs = output.split("\n");
			boolean scrapvalue = false;
			String present_date = null;
			for (String st : strs) {
				st = st.trim();
				if (st.contains("MAJOR RESERVOIR LEVEL INFORMATION")) {
					System.out.println("==>" + st);
					present_date = st;
					present_date = present_date.substring((present_date.indexOf("–") + 1), present_date.length()).trim();
				} else if (st.contains("1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16")) {
					scrapvalue = true;
					continue;
				} else if (!scrapvalue)
					continue;

				if (scrapvalue) {
					String[] strvalues = st.split(" ");
					// System.out.println("==>[" + strvalues.length + "]" + st);
					ReservoirLevel rl = new ReservoirLevel(strvalues, present_date).dataParse();
					rlData.add(rl);
				}
			} // for loop.

		} catch (Exception e) {
			System.err.println("Page load issue......");
			e.printStackTrace();
		} finally {
			if (parser != null)
				parser.getPDDocument().close();
			closeBrowser();
		}
		return rlData;
	}

	private void saveScreenshot() throws AgriTechAppException, IOException {
		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(scrFile, new File("screenshot.png"));
	}

	private void closeBrowser() throws AgriTechAppException, IOException {
		scrapScreenClose();
		driver.close();
	}

	private void scraptext(String str) throws AgriTechAppException, IOException {
		out.write("<tr><td width=\"20%\"></td><td width=\"80%\"><font size=\"6\" color=\"red\"><h1>" + str + "</h1></font></td></tr>");
	}

	private void scrapScreen() throws AgriTechAppException, IOException {
		out.write("<tr><td width=\"20%\"></td><td width=\"80%\">" + driver.getPageSource() + "</td></tr>");
	}

	private void scrapScreenClose() throws AgriTechAppException, IOException {
		out.write(tablefooter);
		if (out != null) {
			out.close();
			out = null;
		}
	}

	private void waitTime(long milliSecond) {
		try {
			Thread.sleep(milliSecond);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
