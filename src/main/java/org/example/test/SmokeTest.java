package org.example.test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.helpers.TestListener;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
@Listeners(TestListener.class)
public class SmokeTest extends BaseTest {
    protected static final Logger logger = LogManager.getLogger(SmokeTest.class);
    @Test
    public void testLogin() {
        logger.info("Login test: checking if login was successful");
        Assert.assertTrue(loginPage.isLoginSuccessful(), "Login was unsuccessful.");
        logger.info("Login test passed.");
    }

    @Test
    public void testLogout() {
        logger.info("Logging out.");
        mailPage.logout();
        Assert.assertTrue(loginPage.isLogoutSuccessful(), "Logout was unsuccessful.");
        logger.info("Logout test passed.");
    }
}


