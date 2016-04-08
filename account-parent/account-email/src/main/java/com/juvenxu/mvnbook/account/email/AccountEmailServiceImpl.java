/**
 * 
 */
package com.juvenxu.mvnbook.account.email;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

/**
 * @author yangyao
 *
 */
public class AccountEmailServiceImpl implements AccountEmailService {

	private JavaMailSender javaMailSender;
	
	private String systemEmail;
	
	/* (non-Javadoc)
	 * @see com.juvenxu.mvnbook.account.email.AccountEmailService#sendEmail(java.lang.String, java.lang.String, java.lang.String)
	 */
	public void sendEmail(String to, String subject, String htmlText) throws AccountEmailException {
		try{
			MimeMessage msg = javaMailSender.createMimeMessage();
			MimeMessageHelper msgHelper = new MimeMessageHelper( msg );
			
			msgHelper.setFrom( systemEmail );
			msgHelper.setTo( to );
			msgHelper.setSubject( subject );
			msgHelper.setText( htmlText,true );
			
			javaMailSender.send( msg ); 
		}catch( MessagingException e ){
			throw new AccountEmailException( "Faild to send mail.", e );
		}
		
		
	}

	/**
	 * @return the javaMailSender
	 */
	public JavaMailSender getJavaMailSender() {
		return javaMailSender;
	}

	/**
	 * @param javaMailSender the javaMailSender to set
	 */
	public void setJavaMailSender(JavaMailSender javaMailSender) {
		this.javaMailSender = javaMailSender;
	}

	/**
	 * @return the systemEmail
	 */
	public String getSystemEmail() {
		return systemEmail;
	}

	/**
	 * @param systemEmail the systemEmail to set
	 */
	public void setSystemEmail(String systemEmail) {
		this.systemEmail = systemEmail;
	}

	
	
}
