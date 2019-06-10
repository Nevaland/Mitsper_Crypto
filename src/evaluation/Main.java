package evaluation;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;

public class Main {

	public static void main(String[] args) throws NoSuchAlgorithmException, UnsupportedEncodingException, GeneralSecurityException {
		AES aes = new AES();
		aes.test();

	}

}
