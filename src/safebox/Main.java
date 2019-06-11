package safebox;
import java.util.Scanner;
import java.math.BigInteger;

public class Main {

	public static void main(String[] args) {
		RSAManager manager = new RSAManager();
		RSABlocking block = manager.generateBlock();
		Scanner sc = new Scanner(System.in);
		String data = "";
		String Encrypted = "";
		String Decrypted = "";
		int selection;

		while(true)
		{
			//start
			System.out.println("----------------------------------\n"
						+"Safe box program\n"
						+"1. encrypt\n"
						+"2. decrypt\n"
						+"3. exit");
			System.out.print("Please Select Menu: ");
			selection = sc.nextInt();
			sc.nextLine();
			if(selection == 3)	//exit
			{
				System.out.println("Exit.");
				break;
			}
			switch(selection)
			{
				case 1:	//encrypt
					do {
						System.out.print("Please enter data of 32 characters or less. :");
						data = sc.nextLine();
						
						if(data.length()>32)
						{
							System.out.println("Data must be 32 characters or less.");
						}
					} while(data.length()>32);
					Encrypted = manager.encrypt(data, block);
					System.out.println("Encrypted:" + Encrypted);
					break;
				case 2:	//decrypt
					Decrypted = manager.decrypt(block);
					System.out.println("Decrypted:" + Decrypted + "");
					Encrypted = "";
					break;
				default:	
					System.out.println("[ERROR] "+selection+" is Invalid Number");
			}
		}
		
		sc.close();
	}

}
