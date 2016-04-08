/**
 * 
 */
package com.juvenxu.mvnbook.account.persist;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentFactory;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.dom4j.tree.BaseElement;

/**
 * @author yangyao
 *
 */
public class AccountPersistServiceImpl implements AccountPersistService {
	
	private final String ELEMENT_ROOT = "account-persist";
	
	private final String ELEMENT_ACCOUNTS = "accounts";
	
	private final String ELEMENT_ACCOUNT = "account";
	
	private final String ELEMENT_ACCOUNT_ID = "id";
	
	private final String ELEMENT_ACCOUNT_NAME = "name";
	
	private final String ELEMENT_ACCOUNT_EMAIL = "email";
	
	private final String ELEMENT_ACCOUNT_PASSWORD = "password";
	
	private final String ELEMENT_ACCOUNT_ACTIVATED = "activated";
	
	private String file;
	
	/**
	 * @return the file
	 */
	public String getFile() {
		return file;
	}

	/**
	 * @param file the file to set
	 */
	public void setFile(String file) {
		this.file = file;
	}

	private SAXReader reader = new SAXReader();
	
	private Document readDocument() throws AccountPersistException{
		File dataFile = new File( file );
		
		if( !dataFile.exists() ){
			dataFile.getParentFile().mkdirs();
			
			Document doc = DocumentFactory.getInstance().createDocument();
			
			Element rootEle = doc.addElement( ELEMENT_ROOT );
			
			rootEle.addElement( ELEMENT_ACCOUNTS );
			
			writeDocument( doc );
			
		}
		try{
			return reader.read( new File(file) );
		}catch( DocumentException e ){
			throw new AccountPersistException( "Unable to read persist data xml", e );
		}
		
	}
	
	private void writeDocument( Document doc ) throws AccountPersistException {
		Writer out = null;
		
		try{
			out = new OutputStreamWriter( new FileOutputStream(  file ), "utf-8" );
			
			XMLWriter writer = new XMLWriter(out, OutputFormat.createPrettyPrint() );
			
			writer.write( doc );
			
		}catch(IOException e){
			throw new AccountPersistException( "Unable to write persist data xml", e );
		}finally{
			try{
				if( out != null ){
					out.close();
				}
			}catch( IOException e ){
				throw new AccountPersistException( "Unable to close persist data xml writer", e );
			}
		}
		
	}

	private Account buildAcount( Element element ){
		Account account = new Account();
		
		account.setId( element.elementText( ELEMENT_ACCOUNT_ID ) );
		account.setName( element.elementText( ELEMENT_ACCOUNT_NAME ) );
		account.setEmail( element.elementText( ELEMENT_ACCOUNT_EMAIL ) );
		account.setPassword( element.elementText( ELEMENT_ACCOUNT_PASSWORD ) );
		account.setActivated( "true".equals( element.elementText( ELEMENT_ACCOUNT_ACTIVATED ) ) ? true :false );
		
		return account;
	}
	
	/* (non-Javadoc)
	 * @see com.juvenxu.mvnbook.account.persist.AccountPersistService#createAccount(com.juvenxu.mvnbook.account.persist.Account)
	 */
	public Account createAccount(Account account) throws AccountPersistException {
		
		Document document = readDocument();
		Element rootEle = document.getRootElement();
		Element accountsEle = rootEle.element( ELEMENT_ACCOUNTS );
		BaseElement id = new BaseElement("id");
		id.setText( account.getId() );
		BaseElement name = new BaseElement("name");
		name.setText( account.getName() );
		BaseElement email = new BaseElement("email");
		email.setText( account.getEmail() );
		BaseElement password = new BaseElement("password");
		password.setText( account.getPassword() );
		BaseElement activated = new BaseElement("activated");
		activated.setText( String.valueOf( account.isActivated() ) );
		
		Element accountEle = accountsEle.addElement( ELEMENT_ACCOUNT );
		accountEle.add( id );
		accountEle.add( name );
		accountEle.add( email );
		accountEle.add( password );
		accountEle.add( activated );
		
		writeDocument( document );
		
		return account;
	}

	/* (non-Javadoc)
	 * @see com.juvenxu.mvnbook.account.persist.AccountPersistService#readAccount(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public Account readAccount(String id) throws AccountPersistException {
		Document doc = readDocument();
		
		Element accountsEle = doc.getRootElement().element( ELEMENT_ACCOUNTS );
		
		for( Element accountEle : (List<Element>) accountsEle.elements() ){
			if( accountEle.elementText( ELEMENT_ACCOUNT_ID ).equals( id ) ){
				return buildAcount( accountEle );
			}
		}
		
		return null;
	}

	/* (non-Javadoc)
	 * @see com.juvenxu.mvnbook.account.persist.AccountPersistService#updateAccount(com.juvenxu.mvnbook.account.persist.Account)
	 */
	public Account updateAccount(Account account) throws AccountPersistException {
		
		return null;
	}

	/* (non-Javadoc)
	 * @see com.juvenxu.mvnbook.account.persist.AccountPersistService#deleteAccount(java.lang.String)
	 */
	public void deleteAccount(String id) throws AccountPersistException {
		

	}

}
