package checkmate;
import java.math.BigInteger;
import java.util.Random;

//for making RSA block - 3 public and private key, 3 dummy and data

public class RSABlocking {
	private final BigInteger MAX = new BigInteger("340282366920938463463374607431768211456");
	private final int DATA_SIZE = 96;
	private final int BLOCK_SIZE = 32;
	private final static BigInteger ZERO = new BigInteger("0");
	private final static int BIT_SIZE = 128;
	
	private RSA RSABlock[] = new RSA[3];
	private String data[] = new String[3];
	private String dummy[] = new String[3];
	private int dataLength;
	private Util util;
	
	public RSABlocking()
	{
		util = new Util();
		for(int i = 0; i < 3; i++)
		{
			RSABlock[i] = new RSA(BIT_SIZE);
			dummy[i] = new BigInteger(128, new Random()).toString(16);
			System.out.println(dummy[i]);
		}
	}
	private void setData(String data)
	{
		data = util.str2hex(data);
		this.dataLength = data.length();
		if(dataLength < DATA_SIZE)
		{
			data = padding(data);
		}
		for(int i = 0 ; i < 3; i++)
		{
			this.data[i] = data.substring(i*BLOCK_SIZE,(i+1)*BLOCK_SIZE);
		}
	}
	
	private String padding(String target)
	{
		for(int i = dataLength; i < DATA_SIZE; i+=2) 
		{
			target += "00";
		}
		return target;
	}
	private String overFlow(String data)
	{
		BigInteger target = new BigInteger(data, 16);
		if(target.compareTo(MAX)==0 ||target.compareTo(MAX) == 1)	//if bigger than max
		{
			target = target.subtract(MAX);
			System.out.println("넘었다");
		}
		else if(target.compareTo(ZERO) == -1)		//if negative
		{
			target = ZERO.subtract(target);
			System.out.println("음수다");
		}
		else
		{
			System.out.println("문제없음");
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
	private String addDummy(int i)
	{
		BigInteger data = new BigInteger(this.data[i], 16);
		BigInteger dummy = new BigInteger(this.dummy[i], 16);
		return data.add(dummy).toString(16);
	}
	private String subDummy(int i)
	{
		BigInteger data = new BigInteger(this.data[i], 16);
		BigInteger dummy = new BigInteger(this.dummy[i], 16);
		return data.subtract(dummy).toString(16);
	}
		
	
	public String encrypt(String data)
	{
		String encrypted = "";
		
		setData(data);
		for(int i = 0; i < 3; i++)
		{
			System.out.println(i+": 변환된 값:" + this.data[i]);
//			this.data[i] = addDummy(i);
//			System.out.println(i+": 더미한 값:" + this.data[i]);
//			this.data[i] = overFlow(this.data[i]);
			this.data[i] = this.RSABlock[i].encrypt(this.data[i]);
			encrypted += this.data[i];
			System.out.println(i+": 암호된 값:" + this.data[i]);
		}
		
		return encrypted;
	}
	public String decrypt()
	{
		String decrypted = "";
		
		for(int i = 0; i < 3; i++)
		{
			this.data[i] = this.RSABlock[i].decrypt(this.data[i]);
			System.out.println(i+": 복호화된 값:" + this.data[i]);
//			this.data[i] = subDummy(i);
//			System.out.println(i+": 더미뺸 값:" + this.data[i]);
//			this.data[i] = overFlow(this.data[i]);
			decrypted += this.data[i];
		}
		
		return util.hex2str(decrypted.substring(0, dataLength));
	}
}
