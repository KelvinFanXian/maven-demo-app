/**
 * 
 */
package com.juvenxu.mvnbook.account.captcha;

/**
 * @author yangyao
 *
 */
public class AccountCaptchaException extends RuntimeException {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AccountCaptchaException(String msg,Throwable e){
		super(msg,e);
	}
	
	public AccountCaptchaException(String msg){
		super(msg);
	}
	
	public AccountCaptchaException(){
		super();
	}
}
