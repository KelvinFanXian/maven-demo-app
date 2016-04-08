/**
 * 
 */
package com.juvenxu.mvnbook.account.persist;

/**
 * @author yangyao
 *
 */
public class AccountPersistException extends RuntimeException {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AccountPersistException(String msg,Throwable e){
		super(msg,e);
	}
}
