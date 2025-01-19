package org.example.cucumber.hooks;

import static org.example.helpers.Constants.LOGS_FOLDER;
import static org.example.helpers.Constants.SCREENSHOTS_FOLDER;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.driver.DriverFactory;
import org.example.helpers.FileHelper;

public class Hooks {
    private static final Logger logger = LogManager.getLogger(Hooks.class);

    @Before
    public void cleanUpFolders() {
        logger.info("Cleaning up screenshots and logs folders before test execution.");
        FileHelper.deleteFolder(SCREENSHOTS_FOLDER);
        FileHelper.deleteFolder(LOGS_FOLDER);
    }

    @After
    public void closeBrowser() {
        logger.info("Closing browser");
        DriverFactory.quitDriver();
    }
}