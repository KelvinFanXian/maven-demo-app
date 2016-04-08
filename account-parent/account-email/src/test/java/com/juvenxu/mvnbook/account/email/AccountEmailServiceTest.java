/**
 * 
 */
package com.juvenxu.mvnbook.account.email;


import static org.junit.Assert.assertEquals;

import javax.mail.Message;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.GreenMailUtil;
import com.icegreen.greenmail.util.ServerSetup;

/**
 * @author yangyao
 *
 */
public class AccountEmailServiceTest {

	private GreenMail greenMail;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void startMailServer() throws Exception {
		greenMail = new GreenMail( ServerSetup.SMTP );
		greenMail.setUser("test@juvenxu.com", "123456");
		greenMail.start();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void stopMailServer() throws Exception {
		greenMail.stop();
	}

	/**
	 * Test method for {@link com.juvenxu.mvnbook.account.email.AccountEmailService#sendEmail(java.lang.String, java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testSendEmail() throws Exception{
		ApplicationContext ctx = new ClassPathXmlApplicationContext( "account-email.xml" );
		AccountEmailService accountEmailService = (AccountEmailService)ctx.getBean("accountEmailService");
		
		String subject = "Test subject";
		String htmlText = "<h3>Test</h3>";
		accountEmailService.sendEmail( "test2", subject, htmlText);

		greenMail.waitForIncomingEmail( 2000, 1 );
		
		Message [] msgs = greenMail.getReceivedMessages();
		assertEquals( 1,msgs.length );
		assertEquals( subject,msgs[0].getSubject() );
		assertEquals( htmlText,GreenMailUtil.getBody( msgs[0] ).trim() );
		
		
	}

}
