package checkmate;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Random;

//RSA working class

public class RSA 
{
	private final static BigInteger one = new BigInteger("1");
	
	private SecureRandom random = new SecureRandom();
	
	private BigInteger publicKey;
	private BigInteger privateKey;
	
	private BigInteger N;
	
	public RSA(int bitLength)
	{
		BigInteger p = BigInteger.probablePrime(bitLength / 2, random);
		BigInteger q = BigInteger.probablePrime(bitLength / 2, random);
		BigInteger phi = (p.subtract(one)).multiply(q.subtract(one));
		
		this.publicKey = new BigInteger("65537");
		this.privateKey = publicKey.modInverse(phi);
		this.N = p.multiply(q);
		System.out.println("p = " + p.toString());
		System.out.println("q = " + q.toString());
		System.out.println("privateKey = " + privateKey.toString());
	}
	
	public BigInteger encrypt(BigInteger data)
	{
		return data.modPow(publicKey, N);
	}
	
	public BigInteger decrypt(BigInteger encryptedData)
	{
		return encryptedData.modPow(privateKey, N);
	}
	public String encrypt(String message)
	{
		return this.encrypt(new BigInteger(message,16)).toString(16);
	}
	public String decrypt(String message)
	{
		return this.decrypt(new BigInteger(message,16)).toString(16);
	}
}
