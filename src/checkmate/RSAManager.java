package checkmate;
import java.math.BigInteger;

public class RSAManager {
	private final BigInteger MAX = new BigInteger("340282366920938463463374607431768211455");
	private final static BigInteger ZERO = new BigInteger("0");
	
	private final static int BLOCK_SIZE = 16;
	private final static int BIT_SIZE = 128;
	private final static int NUM_OF_BLOCK = 6;
	private final static int DATA_SIZE = 96;
	
	private BigInteger privateKey[];
	private Util util;
	
	public RSAManager()
	{
		util = new Util();
	}
	
	public RSABlocking generateBlock()		//make block
	{
		RSABlocking block = new RSABlocking();
		this.privateKey = block.generateBlock(BIT_SIZE);
		
		return block;
	}
	private String addDummy(int i, RSABlocking block)		//add data with dummy
	{
		BigInteger data = new BigInteger(block.getData(i), 16);
		BigInteger dummy = new BigInteger(block.getDummy(i), 16);
		return data.add(dummy).toString(16);
	}
	private String subDummy(int i, RSABlocking block)		//subtract data with dummy
	{
		BigInteger data = new BigInteger(block.getData(i), 16);
		BigInteger dummy = new BigInteger(block.getDummy(i), 16);
		return data.subtract(dummy).toString(16);
	}
	private String overFlow(String data)			//check data
	{
		BigInteger target = new BigInteger(data, 16);
		if(target.compareTo(MAX) == 1)	//if bigger than max
		{
			target = target.subtract(MAX);
		}
		else if(target.compareTo(ZERO) == -1)		//if negative
		{
			target = MAX.add(target);
		}
		
		data = target.toString(16);
		if(data.length() < BLOCK_SIZE)
		{
			for(int j = data.length(); j < DATA_SIZE; j++) 
			{
				data = "0" + data ;
			}
		}
		
		return data;
	}
	
	public String encrypt(String data, RSABlocking block)
	{
		String encrypted = "";
		block.setData(data);
		for(int i = 0; i < NUM_OF_BLOCK; i++)
		{
			block.setData(i, addDummy(i, block));
			block.setData(i, overFlow(block.getData(i)));
			block.setData(i, block.getRSA(i).encrypt(block.getData(i)));
			encrypted += block.getData(i);
		}
		
		return encrypted;
	}
	public String decrypt(RSABlocking block)
	{
		String decrypted = "";
		
		for(int i = 0; i < NUM_OF_BLOCK; i++)
		{
			block.setData(i, block.getRSA(i).decrypt(block.getData(i), privateKey[i]));
			block.setData(i, subDummy(i, block));
			block.setData(i, overFlow(block.getData(i)));
			decrypted += block.getData(i);
		}
		
		return util.hex2str(decrypted.substring(0, block.getDataLength()));
	}
}
