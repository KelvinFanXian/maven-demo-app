/**
 * 
 */
package com.juvenxu.mvnbook.account.email;

/**
 * @author yangyao
 *
 */
public class AccountEmailException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public AccountEmailException(String msg,Throwable e){
		super(msg, e);
	}

}
