package safebox;
import java.math.BigInteger;
import java.util.Random;

//for making RSA block - 3 public and private key, 3 dummy and data

public class RSABlocking {
	private final int DATA_SIZE = 96;
	private final int BLOCK_SIZE = 16;
	private final int NUM_OF_BLOCK = 6;
	
	private RSA RSABlock[] = new RSA[NUM_OF_BLOCK];
	private String data[] = new String[NUM_OF_BLOCK];
	private String dummy[] = new String[NUM_OF_BLOCK];
	private int dataLength;
	private Util util;
	
	public RSABlocking()		//Constructor
	{
		util = new Util();
	}
	public BigInteger[] generateBlock(int bitSize)		//make block - dummy and RSA keys
	{
		BigInteger privateKey[] = new BigInteger[NUM_OF_BLOCK];
		for(int i = 0; i < NUM_OF_BLOCK; i++)
		{
			RSABlock[i] = new RSA();
			privateKey[i] = RSABlock[i].generateRSA(bitSize);
			dummy[i] = new BigInteger(bitSize/2, new Random()).toString(16);
		}
		return privateKey;
	}
	public void setData(String data)			//input data
	{
		data = util.str2hex(data);
		this.dataLength = data.length();
		if(dataLength < DATA_SIZE)
		{
			data = padding(data);
		}
		for(int i = 0 ; i < NUM_OF_BLOCK; i++)
		{
			this.data[i] = data.substring(i*BLOCK_SIZE,(i+1)*BLOCK_SIZE);
		}
	}
	
	private String padding(String target)			//padding
	{
		for(int i = dataLength; i < DATA_SIZE; i+=2) 
		{
			target += "00";
		}
		return target;
	}
	
	public String getDummy(int i)		//get index of dummy
	{
		return dummy[i];
	}
	public void setData(int i, String data)		//set index of data
	{
		this.data[i] = data;
	}
		
	public String getData(int i)		//get index of data
	{
		return data[i];
	}
	public RSA getRSA(int i)		//get index of RSA
	{
		return RSABlock[i];
	}
	public int getDataLength()		//get data length to substring
	{
		return dataLength;
	}
}
