package Crypto;

import java.util.Random;
import java.lang.Integer;

public class Key
{
	private String[] key = new String[10];			//12������ key �迭
	private String CBox = new String();			//chessbox
	private String constantBox = new String();		//ù key ������ ���� ������ �����
    private ConstantNode head = null;

    public class ConstantNode
    {
        public String hexNum;
        public ConstantNode next = null;

        public ConstantNode() {}
        public ConstantNode(String Num)
        {
            hexNum = Num;
        }
        public void deleteNode(ConstantNode prevNode, ConstantNode nextNode)
        {
            if(prevNode!=null)
                prevNode.next = nextNode;
            this.next = null;
        }
    }
	
	public Key(String key)
    {
		generateConstantBox();
		generateCBox(key);
		generateKey(key);
	}
	private void generateConstantBox()
	{
		int i;
		
		for(i = 0; i < 40; i++) {
            if (i % 4 == 0) {
            	String input = Integer.toHexString((i / 4) + 1);
                if(input.length() == 1)
                    input = "0" + input;
                constantBox = constantBox + input;
            }
            else
                constantBox = constantBox + "00";
        }
	}

    private void generateNode()
    {
        int i;
        String inputText;
        ConstantNode target = head;
        ConstantNode prevNode = null;
        for(i = 0; i < 256; i++)
        {
            inputText = Integer.toHexString(i);
            if(inputText.length() == 1)
                inputText = "0" + inputText;
            target = new ConstantNode(inputText);
            if(prevNode != null)
                prevNode.next = target;
            else
            {
                head = target;
            }
            prevNode = target;
            target = target.next;
        }
    }

	private void generateCBox(String key)
    {
		/*this.CBox="000102030405060708090a0b0c0d0e0f101112131415161718191a1b1c1d1e1f"
				+ "202122232425262728292a2b2c2d2e2f303132333435363738393a3b3c3d3e3f"
				+ "404142434445464748494a4b4c4d4e4f505152535455565758595a5b5c5d5e5f"
				+ "606162636465666768696a6b6c6d6e6f707172737475767778797a7b7c7d7e7f"
				+ "808182838485868788898a8b8c8d8e8f909192939495969798999a9b9c9d9e9f"
				+ "a0a1a2a3a4a5a6a7a8a9aaabacadaeafb0b1b2b3b4b5b6b7b8b9babbbcbdbebf"
				+ "c0c1c2c3c4c5c6c7c8c9cacbcccdcecfd0d1d2d3d4d5d6d7d8d9dadbdcdddedf"
				+ "e0e1e2e3e4e5e6e7e8e9eaebecedeeeff0f1f2f3f4f5f6f7f8f9fafbfcfdfeff";*/
		Util util = new Util();
		long num_key = 0;
		String hexKey = util.str2hex(key);
		for(int i = 0; i<hexKey.length(); i+=2)
		{
			num_key += Long.parseLong(hexKey.substring(i, i+2), 16);
		}
		
        generateNode();
        Random random = new Random(num_key);
        ConstantNode target = head;
        ConstantNode prevNode = null;

        for(int i = 256; i > 0; i--)
        {
            int randomInt = random.nextInt(i);
            target = head;
            prevNode = null;
            for(int j = 0; j < randomInt; j++)
            {
                prevNode = target;
                target = target.next;
            }
            CBox = CBox + target.hexNum;
            if(target == head)
                head = target.next;
            target.deleteNode(prevNode, target.next);
        }
	}

	private void generateKey(String key)
    {
		// Sample
		//for(int i=0;i<10;i++)
		//	this.key[i] = "00010203040506070809101112131415";
        int i;
        Util util = new Util();
        String keyChanged = util.str2hex(key);
        String[] hexKey = new String[4];
        String[] roundKey = new String[4];
        String[] Rcon = new String[12];
        for(i=0;i<10;i++)
        {
        	Rcon[i] = constantBox.substring(i*8, (i+1)*8);
        }
        
        if(keyChanged.length()<32)
        {
            int length = keyChanged.length();
            for(i = 0; i < 32-length; i+=2)
            {
                keyChanged = keyChanged + "00";
            }
        }

        for(i = 0; i < 4; i++)
        {
            hexKey[i] = keyChanged.substring(i*8,(i+1)*8);
        }
        
        
        for(i = 0; i < 10; i++)
        {
        	if(i == 0)
        	{
        		for(int j = 0; j < 4; j++)
        		{
        			if(j == 0)
        			{
		        		roundKey[j] = hexKey[3];
		                roundKey[j] = roundKey[j].substring(6,8) + roundKey[j].substring(0,6);
		                String maped_key = "";
		                for(int k = 0; k < 8; k+=2)
		                {
		                	int x, y;
		                	x = Integer.parseInt((roundKey[j].substring(k,k+1)),16);
		                	y = Integer.parseInt((roundKey[j].substring(k+1,k+2)),16);
		                	maped_key += CBox.substring(2*16*x+2*y,2*16*x+2*y+2);
		                }
		                roundKey[j] = maped_key;
		                maped_key = "";
		                for(int k = 0; k < 8; k+=2)
		                {
		                	maped_key += String.format("%02x",
		                			Integer.parseInt(roundKey[j].substring(k, k+2), 16)^Integer.parseInt(hexKey[j].substring(k, k+2), 16)^Integer.parseInt(Rcon[i].substring(k, k+2), 16));
		                }
		                roundKey[j] = maped_key;
        			}
        			else
        			{
        				roundKey[j] = roundKey[j-1];
        				String xor_key = "";
        				
        				for(int k = 0; k < 8; k+=2)
        				{
        					xor_key +=String.format("%02x",
		                			Integer.parseInt(roundKey[j].substring(k, k+2), 16)^Integer.parseInt(hexKey[j].substring(k, k+2), 16));
        				}
        				roundKey[j] = xor_key;
        			}
        		}
        	}
        	else
        	{
        		for(int j = 0; j < 4; j++)
        		{
        			hexKey[j] = this.key[i-1].substring(j*8,(j+1)*8);
        		}
        		for(int j = 0; j < 4; j++)
        		{
        			if(j == 0)
        			{
		        		roundKey[j] = hexKey[3];
		                roundKey[j] = roundKey[j].substring(6,8) + roundKey[j].substring(0,6);
		                String maped_key = "";
		                for(int k = 0; k < 8; k+=2)
		                {
		                	int x, y;
		                	x = Integer.parseInt((roundKey[j].substring(k,k+1)),16);
		                	y = Integer.parseInt((roundKey[j].substring(k+1,k+2)),16);
		                	maped_key += CBox.substring(2*16*x+2*y,2*16*x+2*y+2);
		                }
		                roundKey[j] = maped_key;
		                maped_key = "";
		                for(int k = 0; k < 8; k+=2)
		                {
		                	maped_key += String.format("%02x",
		                			Integer.parseInt(roundKey[j].substring(k, k+2), 16)^Integer.parseInt(hexKey[j].substring(k, k+2), 16)^Integer.parseInt(Rcon[i].substring(k, k+2), 16));
		                }
		                roundKey[j] = maped_key;
        			}
        			else
        			{
        				roundKey[j] = roundKey[j-1];
        				String xor_key = "";
        				
        				for(int k = 0; k < 8; k+=2)
        				{
        					xor_key +=String.format("%02x",
		                			Integer.parseInt(roundKey[j].substring(k, k+2), 16)^Integer.parseInt(hexKey[j].substring(k, k+2), 16));
        				}
        				roundKey[j] = xor_key;
//        				System.out.println(roundKey[j]);
        			}
        		}
        	}
        	this.key[i] = "";
        	for(int j = 0; j < 4; j++)
        	{
        		this.key[i] += roundKey[j];
        	}
        }
	}
	
	public String getKey(int round)
    {
		return this.key[round];
	}
	
	public String getCBox()
    {
		return this.CBox;
	}
}