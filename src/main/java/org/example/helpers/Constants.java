package org.example.helpers;

import java.util.Random;

public class Constants {
    public static final String EMAIL = "test_account713713@yahoo.com";
    public static final String PASSWORD = "test_password";
    public static final String TO = "bogdanprostov@gmail.com";
    public static final String SUBJECT = "Test Subject " + new Random().nextInt(100);
    public static final String BODY = "This is a test email body.";
    public static final String YAHOO_URL =
        "https://login.yahoo.com/?.src=ym&pspid=1197806870&activity=header-signin&.lang=en-US&.intl=us&.done=https%3A%2F%2Fmail.yahoo.com%2Fd";
}
