package stalemate;

import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		// Basic Variables
		int selection;
		String content="default";
		String encrypted=" ";
        Scanner scan = new Scanner(System.in);

        // Mitsper Variables
        String key = "Apple";
        Mitsper mc = new Mitsper(key,content);
        
        while(true) {
        	// Banner and Select Menu
			System.out.println("--------------------------------\n"
					+ "MITSPER Private Key Encryption\n"
					+ "Key: "+key+"\n"
					+ "Encrypted: ["+encrypted+"]\n\n"
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
	        if(selection != 3 && selection != 2) {
		        System.out.print("Please Enter a String: ");
		        content = scan.nextLine();
	        }
	        // Main Action
	        switch(selection) {
	        case 1:
	        	mc.setContents(content);
	        	encrypted = mc.encrypt();
	            System.out.println("Encrypted String: ["+encrypted+"]\n");
	        	break;
	        case 2:
	        	if(encrypted.equals(" ")) {
	        		System.out.println("Encrypt First");
	        		break;
	        	}
	        	mc.setContents(encrypted);
	            System.out.println("Decrypted String: ["+mc.decrypt()+"]\n");
	            encrypted = "";
	            break;
	        case 3:
	            System.out.print("Current Key: "+key+"\nPlease enter the key within 16 characters.\nYour Key: ");
	            while(true)
	            {
	            	key = scan.nextLine();
	            	
	            	if(key.length()<=16)
	            	{
	            		mc.setKey(key);
	            		break;
	            	}
	            	else
	            	{
	            		System.out.print("Please enter the key within 16 characters.\nYour Key: ");
	            	}
	            }
	            
	        	break;
	        default:
	        	System.out.println("[ERROR] "+selection+" is Invalid Number");
	        }
        }
        scan.close();
	}
}
