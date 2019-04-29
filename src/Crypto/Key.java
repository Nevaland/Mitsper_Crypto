package Crypto;

public class Key {
	private String[] key = new String[12];
	
	public Key(String key){
		generate(key);
	}
	public void generate(String key) {
		// Sample
		for(int i=0;i<10;i++)
			this.key[i] = "00010203040506070809101112131415";
	}
	
	public String getKey(int round) {
		return this.key[round];
	}
}