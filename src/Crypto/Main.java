package Crypto;

import java.util.Scanner;

public class Main {
//	static public void eHandle_selection(int selection) {
//		while(true) {
//			try {
//				// code
//				break;
//			}
//			catch( Exception e) {
//				continue;
//			}
//		}
//	}
	public static void main(String[] args) {
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
//			eHandle_selection(selection);
	        System.out.print("Please Select Menu: ");
	        selection = scan.nextInt();
	        scan.nextLine();
	        if(selection == 4) {
	        	System.out.println("Exiting ...");
	        	break;
	        }
	        
	        // Enter a String
	        System.out.print("Please Enter a String: ");
	        content = scan.nextLine();
	        
	        // Main Action
	        switch(selection) {
	        case 1:
	        	mc.contentSet(content);
	            System.out.println("Encrypted String: "+mc.encrypt()+"\n");
	        	break;
	        case 2:
	        	mc.contentSet(content);
	            System.out.println("Decrypted String: "+mc.decrypt()+"\n");
	        	break;
	        case 3:
	            System.out.print("Current Key: "+key+"\nYour Key: ");
	            key = scan.nextLine();
	            mc.keySet(key);
	        	break;
	        default:
	        	System.out.println("[ERROR] "+selection+" is Invalid Number");
	        }
        }
        scan.close();
	}
}
