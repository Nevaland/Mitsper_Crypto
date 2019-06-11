package safebox;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Random;

//RSA working class

public class RSA 
{
	private final static BigInteger one = new BigInteger("1");
	
	private SecureRandom random = new SecureRandom();
	
	private BigInteger publicKey;
	
	private BigInteger N;
	
	public BigInteger generateRSA(int bitLength)	//make keys
	{
		BigInteger p = BigInteger.probablePrime(bitLength / 2, random);
		BigInteger q = BigInteger.probablePrime(bitLength / 2, random);
		BigInteger phi = (p.subtract(one)).multiply(q.subtract(one));
		BigInteger privateKey;
		while(true)
		{
			this.publicKey = BigInteger.probablePrime(bitLength / 2, random);
			if(phi.mod(publicKey) != new BigInteger("0"))
				break;
		}
		privateKey = publicKey.modInverse(phi);
		this.N = p.multiply(q);
		
		return privateKey;
	}
	
	public BigInteger encrypt(BigInteger data)		//encrypt
	{
		return data.modPow(publicKey, N);
	}
	
	public BigInteger decrypt(BigInteger encryptedData, BigInteger privateKey)	//decrypt
	{
		return encryptedData.modPow(privateKey, N);
	}
	public String encrypt(String message)		//String encrypt
	{
		return this.encrypt(new BigInteger(message, 16)).toString(16);
	}
	public String decrypt(String message, BigInteger privateKey)		//String decrypt
	{
		return this.decrypt(new BigInteger(message, 16), privateKey).toString(16);
	}
}
