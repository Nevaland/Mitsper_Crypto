package test;
import java.util.Scanner;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class Main {	
	public static String file_read(String filename) {
		try {
	        FileInputStream fileStream = null;
	        
	        fileStream = new FileInputStream(filename);

	        byte[ ] readBuffer = new byte[fileStream.available()];
	        while (fileStream.read(readBuffer) != -1){}

	        fileStream.close();
	        return new String(readBuffer);
	    } catch (Exception e) {
	    	e.getStackTrace();
	    }
        return "[ERROR] No File";
	}
	public static void main(String[] args) {
		// Basic Variables
		String op_name = "option"; // ip, port, input_type
		String ip_name = "input"; // ip, port, input_type
    	String main_banner = 
  			  	  "----------------------------------------------------------------\n" +
    			  "                _   _               .-.        _\r\n" + 
    			  "    o   o      | |_| |             .' '.      ( )        |\\.\r\n" + 
    			  "o   /\\ /\\  o   |     |    .-\"-.    (   )   .-. ^ .-.    /   '.\r\n" + 
    			  "\\`.'  `  `'/   '-----'    `. .'    `. .'  :   `.'   :  /_.'-  \\\r\n" + 
    			  " \\        /    |     |    .' '.     | |   `.       .'     /   |  \r\n" + 
    			  "  \\_.--._/    /_.---._\\  .'___'.  ._' '_.  )_.---._(     /____|\r\n" + 
    			  "  '.____.'    '._____.'  `-----'  '--^--'  `._____.'    `.____.'\n"
      			+ "----------------------------------------------------------------\n"
    			+ "-               CheckMate Encryption Test Program              -\n"
    			+ "-                    [Option File: "+op_name+"]                     -\n"
    			+ "-                     [Input File: "+ip_name+"]                      -\n"
    			+ "----------------------------------------------------------------\n";
    	String menu_banner = 
    			  " 1. Sender Mode\n"
    			+ " 2. Listener Mode\n"
    			+ " 3. Exit\n"
				+ " MENU(1~3): ";
    	String op_data = (file_read(op_name));
    	String ip = op_data.substring(0,op_data.indexOf("\n")-1);
    	String port = op_data.substring(op_data.indexOf("\n")+1,op_data.lastIndexOf("\n")-1);
    	String input_type = op_data.substring(op_data.lastIndexOf("\n")+1);
    	String data = "";
    	
//    	System.out.println(ip+"-"+port+"-"+input_type);
    	
        Scanner sc = new Scanner(System.in);
        int menu = 0;

    	// Banner and Select Menu
		System.out.print(main_banner);
		while(menu != 3) {
			System.out.print(menu_banner);
			menu = sc.nextInt();
			switch(menu) {
			case 1:
				System.out.print("-----------------\nIP: "+ip+"\nPORT: "+port+"\n-----------------\\n");
				
		    	// Sender
				if(input_type == "1") {	// by Interface
					System.out.print("Input: ");
					data = sc.nextLine();
					
				}
				else {	// by File
					data = file_read("input");
					System.out.println("Input: "+data);
				}
				
				break;
			case 2:
				System.out.print("-----------------\nPORT: "+port+"\n-----------------\\n");
		    	// Listener
				
				break;
			}
		}

	}
	
}
