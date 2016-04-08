/**
 * 
 */
package com.juvenxu.mvnbook.account.persist;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author yangyao
 *
 */
public class AccountPersistServiceTest {

	private AccountPersistService service;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void prepare() throws Exception {
		File persistDataFile = new File("target/test-classes/persist-data.xml");
		
		if( persistDataFile.exists() ){
			persistDataFile.delete();
		}
		
		ApplicationContext ctx = new ClassPathXmlApplicationContext( "account-persist.xml" );
		
		service = (AccountPersistService)ctx.getBean( "accountPersistService" );
		
		Account account = new Account();
		account.setId( "juven" );
		account.setName( "Juven Xu" );
		account.setEmail( "juven@changeme.com" );
		account.setPassword( "this_should_be_encrypted" );
		account.setActivated( true );
		
		service.createAccount( account );
	}

	/**
	 * Test method for {@link com.juvenxu.mvnbook.account.persist.AccountPersistService#createAccount(com.juvenxu.mvnbook.account.persist.Account)}.
	 */
	@Test
	public void testCreateAccount() {
		
	}

	/**
	 * Test method for {@link com.juvenxu.mvnbook.account.persist.AccountPersistService#readAccount(java.lang.String)}.
	 */
	@Test
	public void testReadAccount() {
		Account account = service.readAccount( "juven" );
		
		assertNotNull( account );
		assertEquals( "juven",account.getId() );
		assertEquals( "Juven Xu",account.getName() );
		assertEquals( "juven@changeme.com",account.getEmail() );
		assertEquals( "this_should_be_encrypted",account.getPassword() );
		assertTrue( account.isActivated() );
	}

	/**
	 * Test method for {@link com.juvenxu.mvnbook.account.persist.AccountPersistService#updateAccount(com.juvenxu.mvnbook.account.persist.Account)}.
	 */
	@Test
	public void testUpdateAccount() {
		
	}

	/**
	 * Test method for {@link com.juvenxu.mvnbook.account.persist.AccountPersistService#deleteAccount(java.lang.String)}.
	 */
	@Test
	public void testDeleteAccount() {
		
	}

}
