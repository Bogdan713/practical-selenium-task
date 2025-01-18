package org.example.test;

import static org.example.helpers.Constants.EMAIL_BODY;
import static org.example.helpers.Constants.EMAIL_SUBJECT;
import static org.example.helpers.Constants.RECEIVER_EMAIL;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.helpers.TestListener;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
@Listeners(TestListener.class)
public class RegressionTest extends BaseTest {

    protected static final Logger logger = LogManager.getLogger(RegressionTest.class);
    @Test
    public void testCreateDraft() {
        logger.info("Executing draft creation test.");
        mailPage.createDraft(RECEIVER_EMAIL, EMAIL_SUBJECT, EMAIL_BODY);
        Assert.assertTrue(mailPage.isDraftPresent(EMAIL_SUBJECT), "Draft not found in the 'Drafts' folder.");
        logger.info("Draft creation test passed.");
    }

    @Test
    public void testVerifyDraftContent() {
        logger.info("Verifying draft content.");
        mailPage.createDraft(RECEIVER_EMAIL, EMAIL_SUBJECT, EMAIL_BODY);
        Assert.assertTrue(mailPage.isDraftPresent(EMAIL_SUBJECT), "Draft with the specified subject does not exist.");

        mailPage.openDraft(EMAIL_SUBJECT);
        Assert.assertEquals(mailPage.getDraftTo(1), RECEIVER_EMAIL, "Recipient in draft does not match.");
        Assert.assertEquals(mailPage.getDraftSubject(), EMAIL_SUBJECT, "Subject in draft does not match.");
        Assert.assertEquals(mailPage.getDraftBody(), EMAIL_BODY, "Body in draft does not match.");
        logger.info("Draft content verification test passed.");
    }

    @Test
    public void testSendMail() {
        logger.info("Executing mail sending test.");
        mailPage.createDraft(RECEIVER_EMAIL, EMAIL_SUBJECT, EMAIL_BODY)
            .openDraft(EMAIL_SUBJECT)
            .sendDraft();
        Assert.assertFalse(mailPage.isDraftPresent(EMAIL_SUBJECT), "Draft still present in the 'Drafts' folder.");
        Assert.assertTrue(mailPage.isMailInSentFolder(EMAIL_SUBJECT), "Mail not found in the 'Sent' folder.");
        logger.info("Mail sending test passed.");
    }
}
