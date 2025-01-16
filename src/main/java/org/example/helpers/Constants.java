package org.example.helpers;

import java.util.Random;

public class Constants {
    public static final String EMAIL = "test_account713713@yahoo.com";
    public static final String PASSWORD = "test_password";
    public static final String RECEIVER_EMAIL = "bogdanprostov@gmail.com";
    public static final String EMAIL_SUBJECT = "Test Subject " + new Random().nextInt(100);
    public static final String EMAIL_BODY = "This is a test email body.";

    public static final String YAHOO_LOGOUT_URL = "https://www.yahoo.com/";
    public static final String YAHOO_SUCCESS_URL = "mail.yahoo.com/d/";
    public static final String YAHOO_URL = "https://login.yahoo.com/";
    public static final String SCREENSHOTS_FOLDER = "screenshots";
}
