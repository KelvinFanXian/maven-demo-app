/**
 * 
 */
package com.juvenxu.mvnbook.account.email;

/**
 * @author yangyao
 *
 */
public interface AccountEmailService {

	void sendEmail( String to,String subject,String htmlText) throws AccountEmailException;
}
