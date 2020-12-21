package olahbarang;

import java.util.Date;
import java.util.InputMismatchException;
import java.util.Scanner;

public class LogIn {

	static Date date = new Date();
	static Scanner sc = new Scanner(System.in);
	
	public static int landingPage() {
		
		Integer pil = 0;
		System.out.println("============================");
		System.out.println(date+"\n");
		System.out.println("1. Login");
		System.out.println("2. Register");
		System.out.print("Tentukan pilihanmu : ");
		
		try {
			System.out.println("============================");
			System.out.println(date+"\n");
			System.out.println("1. Login");
			System.out.println("2. Register");
			System.out.print("Tentukan pilihanmu : ");
			pil = sc.nextInt();
		} 
		catch (InputMismatchException e) {
			System.out.println("Inputan yang anda masukkan salah");
		}
		
		return pil;
	}
}
