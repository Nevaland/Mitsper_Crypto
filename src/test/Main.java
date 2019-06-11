package test;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Scanner;

import checkmate.Checkmate;

public class Main {	
	public static void listen(String pub_key, Checkmate cm) {
		try (ServerSocket serverSocket = new ServerSocket(4321);
            Socket socket = serverSocket.accept();
            PrintWriter out = new PrintWriter(socket.getOutputStream());
            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
			Scanner sc = new Scanner(System.in);
			String sym_key = "";
			
            System.out.println("Listening....");
            while (true) {
                String msg = br.readLine();
                if (msg.equalsIgnoreCase("Quit"))
                    break;
//                System.out.println("Received Data:[ " + msg + " ]");
                if (msg.equalsIgnoreCase("Request"))
                	msg = pub_key;
                else {
                	if(sym_key == "")
                		sym_key = msg;
                	else {
                		msg = cm.symDecrypt(sym_key, msg);
                		System.out.println("Received Data:["+msg+"]");
                	}
//                	System.out.print("Input: ");
//                	msg = sc.nextLine();
//                	msg = cm.encrypt(sym_key)
                }
                out.println(msg);
                out.flush();
            }
            System.out.println("Stop..");
        } catch (IOException e) {
//            e.printStackTrace();
        }
    }
	
	public static void client(String ip, Checkmate cm, String sym_key) throws UnknownHostException, IOException {
        Socket socket = new Socket(ip, 4321);
        PrintWriter out = new PrintWriter(socket.getOutputStream());
        BufferedReader networkIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        BufferedReader userIn = new BufferedReader(new InputStreamReader(System.in));
        Scanner sc = new Scanner(System.in);
        String pub_key = "";
        String data = "";
        
        System.out.println("Connected....");

//        out.println("request");
//        out.flush();
//        pub_key = networkIn.readLine();

        out.println(sym_key);
        out.flush();
        networkIn.readLine();
        
        while (true) {
			System.out.print("Input(or quit): ");
        	data = sc.nextLine();
        	
            out.println(cm.symEncrypt(sym_key, data));
            out.flush();
            if (data.equalsIgnoreCase("Quit"))
                break;
            networkIn.readLine();
//            System.out.println("Received Data:[ " + networkIn.readLine() + " ]");
        }
        System.out.println("Stop..");
        networkIn.close();
        out.close();
        socket.close();
    }
		
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
		String ip_name = "input";
		String k_name = "key";
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
    			+ "-                       [Key File: "+k_name+"]                        -\n"
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
    	String key = "";
		Checkmate cm = new Checkmate();
    	
        Scanner sc = new Scanner(System.in);
        int menu = 0;

    	// Banner and Select Menu
		System.out.print(main_banner);
		while(menu != 3) {
			System.out.print(menu_banner);
			menu = sc.nextInt();
			sc.nextLine();
			
			switch(menu) {
			case 1:
				System.out.print("-----------------\nIP: "+ip+"\nPORT: "+port+"\n-----------------\n");
				
		    	// Sender
				if(input_type.equals("1")) {	// by Interface
					System.out.print("Key: ");
					key = sc.nextLine();
					
					try {
						client(ip, cm, key);
					} catch (UnknownHostException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
//				else {	// by File
//					while(true) {
//						data = file_read("input");
//						System.out.println("Input(q:quit): "+data);
//						if(data=="q") break;
//						
//					}
//				}

				break;
			case 2:
				cm.genSafebox();
				
				// Listener
				System.out.println("-----------------\nPORT: "+port);
		    	listen("", cm);
				break;
			}
		}

	}
	
}
