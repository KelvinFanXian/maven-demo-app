/**
 * 
 */
package com.juvenxu.mvnbook.account.captcha;


import static org.junit.Assert.assertFalse;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

/**
 * @author yangyao
 *
 */
public class RandomGeneratorTest {

	/**
	 * Test method for {@link com.juvenxu.mvnbook.account.captcha.RandomGenerator#getRandomString()}.
	 */
	@Test
	public void testGetRandomString() {
		Set<String> randoms = new HashSet<String>(100);
		
		for (int i = 0; i < 100; i++) {
			String random = RandomGenerator.getRandomString();
			
			assertFalse( randoms.contains( random ) );
			
			randoms.add( random );
			
		
		}
		
		
	}

}