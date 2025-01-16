package org.example.test;

import org.example.test.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
public class SmokeTest extends BaseTest {
    @Test
    public void testLogin() {
        Assert.assertTrue(mailPage.isLoginSuccessful(), "Login was unsuccessful.");
        logger.info("Login test passed.");
    }

    @Test
    public void testLogout() {
        mailPage.logout();
        Assert.assertTrue(loginPage.isLogoutSuccessful(), "Logout was unsuccessful.");
        logger.info("Logout test passed.");
    }
}


