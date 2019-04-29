package Crypto;

import java.util.Scanner;

public class Main {
	public static void test() {
		Util util = new Util();
		String str = "TEST12^@";
		System.out.println(str);
		System.out.println(util.str2hex(str));
		
		Mitsper ms = new Mitsper("key",str);
		
		System.exit(0);
	}
	public static void main(String[] args) {
//        test();
		// Basic Variables
		int selection;
		String content="default";
        Scanner scan = new Scanner(System.in);

        // Mitsper Variables
        String key = "Apple";
        Mitsper mc = new Mitsper(key,content);
        
        while(true) {
        	// Banner and Select Menu
			System.out.println("--------------------------------\n"
					+ "MITSPER Private Key Encryption\n"
					+ "Key: "+key+"\n\n"
					+ "1. Encryption\n"
					+ "2. Decryption\n"
					+ "3. Key Setting\n"
					+ "4. Exit\n");
	        System.out.print("Please Select Menu: ");
	        selection = scan.nextInt();
	        scan.nextLine();
	        if(selection == 4) {
	        	System.out.println("Exiting ...");
	        	break;
	        }
	        
	        // Enter a String
	        if(selection != 3) {
		        System.out.print("Please Enter a String: ");
		        content = scan.nextLine();
	        }
	        // Main Action
	        switch(selection) {
	        case 1:
	        	mc.setContents(content);
	            System.out.println("Encrypted String: "+mc.encrypt()+"\n");
	        	break;
	        case 2:
	        	mc.setContents(content);
	            System.out.println("Decrypted String: "+mc.decrypt()+"\n");
	        	break;
	        case 3:
	            System.out.print("Current Key: "+key+"\nYour Key: ");
	            key = scan.nextLine();
	            mc.setKey(key);
	        	break;
	        default:
	        	System.out.println("[ERROR] "+selection+" is Invalid Number");
	        }
        }
        scan.close();
	}
}
