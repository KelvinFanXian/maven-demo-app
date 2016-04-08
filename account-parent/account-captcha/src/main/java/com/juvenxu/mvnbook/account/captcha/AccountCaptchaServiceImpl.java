/**
 * 
 */
package com.juvenxu.mvnbook.account.captcha;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.InitializingBean;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;

/**
 * @author yangyao
 *
 */
public class AccountCaptchaServiceImpl implements AccountCaptchaService, InitializingBean {

	private DefaultKaptcha producer;
	
	/* (non-Javadoc)
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	public void afterPropertiesSet() throws Exception {
		producer = new DefaultKaptcha();
		producer.setConfig( new Config( new Properties() ) );
		
	}
	
	private Map<String,String> captchaMap = new HashMap<String,String>();
	
	private List<String> preDefinedTexts;
	
	private int textCount = 0;

	/* (non-Javadoc)
	 * @see com.juvenxu.mvnbook.account.captcha.AccountCaptchaService#generateCaptchaKey()
	 */
	public String generateCaptchaKey() throws AccountCaptchaException {
		String key = RandomGenerator.getRandomString();
		
		String value = getCaptchaText();
		
		captchaMap.put(key, value);
		
		return key;
	}

	/* (non-Javadoc)
	 * @see com.juvenxu.mvnbook.account.captcha.AccountCaptchaService#generateCaptchaImage(java.lang.String)
	 */
	public byte[] generateCaptchaImage(String captchaKey) throws AccountCaptchaException {
		String text = captchaMap.get( captchaKey );
		if( text == null ){
			throw new AccountCaptchaException( "Captcha key'" + captchaKey + "'not found!" );
		}
		BufferedImage image = producer.createImage( text );
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		
		try{
			ImageIO.write(image, "jpg", out);
		}catch( IOException e ){
			throw new AccountCaptchaException("Faild to write captcha stream!",e);
		}
		
		return out.toByteArray();
	}

	/* (non-Javadoc)
	 * @see com.juvenxu.mvnbook.account.captcha.AccountCaptchaService#validateCaptcha(java.lang.String, java.lang.String)
	 */
	public boolean validateCaptcha(String captchaKey, String captchaValue) throws AccountCaptchaException {
		String text = captchaMap.get( captchaKey );
		
		if( text == null ){
			throw new AccountCaptchaException( "Captcha key'" + captchaKey + "'not found!" );
		}
		
		if( text.equals( captchaValue ) ){
			captchaMap.remove( captchaKey );
			return true;
		}else{
			return false;
		}
	}

	/* (non-Javadoc)
	 * @see com.juvenxu.mvnbook.account.captcha.AccountCaptchaService#getPreDefinedTexts()
	 */
	public List<String> getPreDefinedTexts() {
		return preDefinedTexts;
	}

	/* (non-Javadoc)
	 * @see com.juvenxu.mvnbook.account.captcha.AccountCaptchaService#setPreDefinedTexts(java.util.List)
	 */
	public void setPreDefinedTexts(List<String> preDefinedTexts) {
		this.preDefinedTexts = preDefinedTexts;
	}
	
	private String getCaptchaText(){
		if( preDefinedTexts != null && !preDefinedTexts.isEmpty() ){
			String text = preDefinedTexts.get( textCount );
			
			textCount = ( textCount + 1 ) % preDefinedTexts.size();
			
			return text;
		}else{
			return producer.createText();
		}
	}

}
