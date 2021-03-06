package BPL_TB;

import java.util.Scanner;
import java.sql.*;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Random;

public class User extends Koneksi {
	
	static Connection conn;
	static Statement stmt;
	static ResultSet result;
	
//	MulaiProgram Mulai = new MulaiProgram();
	Scanner input = new Scanner(System.in);
	Date date = new Date();

	String uname;
	String pass;

	String username;
	String password;
	String email;
	String login_terakhir;
	String query;
	
    public void login() throws Exception {
    	
    	conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
		int login = 0 ; 
		do {
                        System.out.println("+---------------+");
                        System.out.println("|     LOGIN     |");
                        System.out.println("+---------------+");
                        System.out.print("Username : ");
			this.username = input.next();
			
			System.out.print("Password : ");
			this.password = input.next();
			System.out.println("+---------------+");

			this.login_terakhir = String.format("%tF", date);

			this.query = "SELECT*FROM user WHERE username='"+username+"'"
	    				+ " AND password='"+password+"'";
			
			stmt=conn.createStatement();
			ResultSet result = stmt.executeQuery(query);
			if (result.next()) {
				String sql = "UPDATE user SET login_terakhir='"+login_terakhir+"' WHERE username='"+username+"'";
				stmt.executeUpdate(sql);
				System.out.println("Login Berhasil");
				uname=username;
				pass=password;
				user_menu();
			} else {
				System.out.println("Username dan Password salah");
				login++;
				if (login==3) {
					reset();
				}
			}
		} while (login>=0 && login<=2);
    	
	}
    
    public void reset () throws Exception {
		String resetpass = "abcdefghijklmnopqrstuvwxyz1234567890";
		String randompass = "";
		int length = 5;
		Random random = new Random();
		char [] pass = new char [length];
		
		for (int a=0 ; a<length ; a++  ) {
			pass[a] = resetpass.charAt(random.nextInt(resetpass.length()));
		}
		
		for (int a=0 ; a<pass.length ; a++) {
			randompass += pass[a];
		}
		
		System.out.println("Password Baru Anda : "+ randompass);
		System.out.println("SILAHAKAN LOGIN KEMBALI DENGAN PASSWORD BARU");
		String sql = "UPDATE user SET password='"+randompass+"' WHERE username='"+username+"'";
		stmt.executeUpdate(sql);
		login();
	}
	
    // Register data
 	public void Register() throws Exception{

 		conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);

        System.out.println("+---------------+");
 	    System.out.println("|    SIGN UP    |");
 	    System.out.println("+---------------+");

 		System.out.print("Masukkan Username : ");
 		this.username = input.next();

 		this.login_terakhir = String.format("%tF", date);

 		System.out.print("Masukkan Email : ");
 		this.email = input.next();

 		System.out.print("Masukkan Password : ");
 		this.password = input.next();

 		// Melakukan pengecekan validitas email
 		if (email.contains("@")) {

 			// Melakukan pengecekan username sudah tersedia atau belum
 			String cek = "SELECT username FROM user WHERE username='"+username+"'";
 			try {
 				stmt = conn.createStatement();
 				ResultSet resultCek = stmt.executeQuery(cek);
 				
 				if (resultCek.next()) {
 					System.out.println("Username Sudah Terdaftar");
 					Register();
 				} else{

 					this.query = "INSERT INTO user VALUES ('"+username+"','"+email+"','"+password+"','"+login_terakhir+"')";
 		
 					try {
 						stmt = conn.createStatement();

 						if (stmt.executeUpdate(query) == 1) {
 							System.out.println("Data Berhasil Diinputkan");
 							login();
 						} else{
 							System.out.println("Data Gagal Diinputkan");
 						}
 						
 					} catch (SQLException e) {
 						System.out.println("Terjadi Kesalahan");
 					}

 				}

 			} catch (SQLException e) {
 				System.out.println("Terjadi Kesalahan");
 			}

 		} else{
 			System.out.println("Masukkan Email Dengan Benar");
 			Register();
 		}

 		conn.close();

 	}

  	public void user_menu() throws Exception{
  		boolean tampil=true;
		while (tampil==true) {
	  		Scanner scan = new Scanner(System.in);
	  		Barang brg = new Barang();
	  		LaporanTransaksi lap = new LaporanTransaksi();
	  		Transaksi trs = new Transaksi();
	  		System.out.println("+============================================+");
	  		System.out.println("+''''''''''''''''''''''''''''''''''''''''''''+");
	  		System.out.println("|             SELAMAT DATANG DI              |");
	  		System.out.println("|                 TOKO KITA                  |");
	  		System.out.println("+____________________________________________+");
	  		System.out.println("+============================================+");
	  		System.out.println();
	  		System.out.println("+============================================+");
	  		System.out.println("|            '' DAFTAR MENU ''               |");
	  		System.out.println("+____________________________________________+");
	  		System.out.println("|             [1] Pengelolaan User           |");
	  		System.out.println("+____________________________________________+");
	  		System.out.println("|             [2] Kelola Data Barang         |");
	  		System.out.println("+____________________________________________+");
	  		System.out.println("|             [3] Transaksi                  |");
	  		System.out.println("+____________________________________________+");
	  		System.out.println("|             [4] Laporan Penjualan          |");
	  		System.out.println("+____________________________________________+");
	  		System.out.println("|             [5] Logout                     |");
	  		System.out.println("|____________________________________________|");
	  		System.out.println("+============================================+");
	  		System.out.print("Pilih : ");
	  		Integer pilihan = scan.nextInt();
	  			switch (pilihan) {
	  				case 1:
	  					user_setting();
	  					break;
	  				
	  				case 2:
	  					System.out.print("\n");
	  					brg.Menu();
	  					break;	
	
	  				case 3:
	  					System.out.print("\n");
	  					trs.insertall();
	  					break;
	
	  				case 4:
	  					lap.MenuLaporan();
	  					break;
	  					
	  				case 5: 
	  					logout();
	
	  					break;
	  					
	  				default:
	  					System.out.println("Pilihan Tidak Tersedia");
	  					break;
	  			}
	  			System.out.print("Apakah anda ingin melanjutkan? [y/n]  ");
	  			Scanner sc = new Scanner (System.in);
				String next = sc.nextLine();
				String lanjut = "y";
				tampil = next.equalsIgnoreCase(lanjut);
				if (tampil==false) {
					user_menu();
				}

		}
  	}


  	// Pilihan setting
  	public void user_setting() throws Exception {

  		Scanner scan = new Scanner(System.in);
  		System.out.println();	
  		System.out.println("+============================================+");
  		System.out.println("|''''''''''''''''''''''''''''''''''''''''''''|");
  		System.out.println("|         '  PENGELOLAAN DATA USER  '        |");
  		System.out.println("+____________________________________________+");
  		System.out.println("|             [1] Edit Akun                  |");
  		System.out.println("|             [2] Hapus Akun                 |");
  		System.out.println("|             [3] Cari Data                  |");
  		System.out.println("|             [4] Lihat Data                 |");
  		System.out.println("|             [5] Kembali                    |");
  		System.out.println("|             [6] Logout                     |");
  		System.out.println("+============================================+");
  		System.out.print("Tentukan Pilihanmu : ");
  		
  		Integer pilihan = scan.nextInt();	
  		
  		switch (pilihan) {
  			case 1:
  				EditUser();
  				break;
  		
  			case 2:
  				HapusUser();
  				break;

  			case 3:
  				CariUser();
  				break;

  			case 4:
  				LihatUser();
  				break;
  				
  			case 5:
  				user_menu();
  				break;
  				
  			case 6:
  				logout();

  			default:
  				System.out.println("Pilihan Tidak Tersedia");
  				
  				break;
  		}

  		scan.close();

  	}
  	static Scanner sc = new Scanner(System.in);
  	// Logout
  	public void logout() {
  		boolean cek = true;
  		String jawab;
  		do {
  			System.out.println("Apakah Anda Ingin Keluar ? ");
  			System.out.println("Jawab Y/T");
  			jawab = input.next();
  			if(jawab.equalsIgnoreCase("Y")) {
  				cek = false;
  				System.out.println("Anda Berhasil Keluar");
  			} 

  		} 
  		while(cek);
  		try {
//  			Mulai.mulai();
  		}
  		catch(Exception e){
  			e.printStackTrace();	
  		}

  	}

 	// Mengedit email dan password akun
 	public void EditUser() throws Exception{

  		System.out.println("+========================================+");
  		System.out.println("|         '' MENU EDIT USER ''           |");
  		System.out.println("|________________________________________|");
 		System.out.println("|         [1] Ubah Username              |");
 		System.out.println("|         [2] Ubah Email                 |");
 		System.out.println("|         [3] Ubah Password              |");
 		System.out.println("|         [4] Kembali                    |");
 		System.out.println("+========================================+");
 		System.out.print("Tentukan Pilihanmu : ");

 		try {
 			Integer pilihan = input.nextInt();
 			switch (pilihan) {

 				// Ubah Username
 				case 1:
 			  		System.out.println("+====================================+");
 			  		System.out.println("|        '' Ubah Username ''         |");
 			  		System.out.println("|____________________________________|");
		
					System.out.print("Masukkan Username Baru :");
 					this.username = input.next();
 	
 					System.out.print("Konfirmasi Password : ");
 					this.password = input.next();

 					if (password.equals(pass)) {
 					
 						this.query = "UPDATE user SET username='"+username+"' WHERE username='"+uname+"' ";
 		
 						try {
 							stmt = conn.createStatement();
 								
 							if (stmt.executeUpdate(query) == 1) {
 								System.out.println("Username Berhasil Di Ubah");
 								user_menu();
 							} else{
 								System.out.println("Username Gagal Di Ubah");
 								EditUser();
 							}
 								
 						} catch (SQLException e) {
 							System.out.println("Terjadi Kesalahan");
 						}
 	
 					} else {
						System.out.println("Password Yang Anda Masukkan Salah");
						EditUser();
 					}
 	
 					break;
					
 			
 				// Ubah email
 				case 2:
 			  		System.out.println("+====================================+");
 			  		System.out.println("|          '' Ubah Email ''          |");
 			  		System.out.println("|____________________________________|");
 					System.out.print("Masukkan Email Baru :");
 					this.email = input.next();
 					System.out.print("Konfirmasi Password : ");
 		
 					this.password = input.next();
 	
 					if (email.contains("@")) {
 	
 						if (password.equals(pass)) {
 					
 							this.query = "UPDATE user SET email='"+email+"' WHERE username='"+uname+"'";
 		
 							try {
 								stmt = conn.createStatement();
 								
 								if (stmt.executeUpdate(query) == 1) {
 									System.out.println("Email Berhasil Di Ubah");
 									user_menu();
 								} else{
 									System.out.println("Email Gagal Di Ubah");
 									EditUser();
 								}
 								
 							} catch (SQLException e) {
 								System.out.println("Terjadi Kesalahan");
 							}
 		
 						} else {
 							System.out.println("Password Yang Anda Masukkan Salah");
 							EditUser();
 						}
 	
 					} else {
 						System.out.println("Masukkan Email Dengan Benar");
 						EditUser();
 					}
 	
 					break;
 				
 				// Ubah password
 				case 3:
 			  		System.out.println("+====================================+");
 			  		System.out.println("|        '' Ubah Password '          |");
 			  		System.out.println("|____________________________________|");
 					System.out.print("\nMasukkan Password Lama : ");
 					String passwordLama = input.next();
 	
 					System.out.print("Masukkan Password Baru :");
 					String passwordBaru = input.next();
 	
 					if (passwordLama.equals(pass)) {
 						
 						this.query = "UPDATE user SET password='"+passwordBaru+"' WHERE username='"+uname+"'";
 		
 						try {
 							stmt = conn.createStatement();
 							
 							if (stmt.executeUpdate(query) == 1) {
 								System.out.println("Password Berhasil Di Ubah");
 								user_menu();
 							} else {
 								System.out.println("Password Gagal Di Ubah");
 								EditUser();
 							}
 							
 						} catch (SQLException e) {
 							System.out.println("Terjadi Kesalahan");
 						}
 	
 					} else {
 						System.out.println("Password Yang Anda Masukkan Salah");
 						EditUser();
 					}
 	
 					break;
 					
 				//Kembali ke menu pengelolaan data user
 				case 4:
 					user_setting();
 			
 				default:
 					System.out.println("Pilihan Tidak Tersedia");
 					break;
 			}

 		} catch (InputMismatchException e) {
 			System.out.println("Pilihan Tidak Tersedia");
 		}

 	}


 	// Hapus akun
 	public void HapusUser(){

 		System.out.println("+====================================+");
	  	System.out.println("|          '' HAPUS AKUN ''          |");
	  	System.out.println("|____________________________________|");

 		System.out.println("Apakah Anda Yakin Ingin Menghapus Akun Anda ?");
 		System.out.print("Jawab y/t : ");

 		String lanjut = input.next();

 		if (lanjut.equals("y")) {
 			this.query = "DELETE FROM user WHERE username='"+uname+"'";
 			try {
 				stmt = conn.createStatement();
 				stmt.execute(query);
 				System.out.println("Data Berhasil Di Hapus");
 			} catch (SQLException e) {
 				System.out.println("Data Gagal Di Hapus");				
 			}
 			
 		} 
 		
 		try {
//			Mulai.mulai();
		} catch (Exception e) {
			e.printStackTrace();
		}

 	}

 	// Cari data akun atau data transaksi
 	public void CariUser() throws Exception{
 		System.out.println("+====================================+");
  		System.out.println("|     '' Pencarian Data User ''      |");
  		System.out.println("|____________________________________|");

  		System.out.print("Masukkan Username : ");
  		String cari = input.next();

  		this.query = "SELECT*FROM user WHERE username LIKE '%"+cari+"%'";
  		try {
  			stmt = conn.createStatement();
  			ResultSet result = stmt.executeQuery(query);
		
  			while (result.next()) {
  				System.out.println("+====================================+");	
  				System.out.println("Username   : "+result.getString("username"));				
  				System.out.println("Email	   : "+result.getString("email"));
  				System.out.println("Password   : "+result.getString("password"));
  				System.out.println("Last Login : "+result.getDate("login_terakhir"));
  				System.out.println("+====================================+");
			}
		} catch (SQLException e) {
			System.out.println("Gagal Mengakses Database");
		}
		user_setting();
 	}


 	// Lihat data akun atau data transaksi??
 	public void LihatUser() throws Exception{
  		System.out.println("+====================================+");
  		System.out.println("|       '' Lihat Data User ''        |");
  		System.out.println("|____________________________________|");

 		this.query = "SELECT*FROM user";
 		try {
 			stmt = conn.createStatement();
 			
 			ResultSet result = stmt.executeQuery(query);
				System.out.println("+==============================================================+");
				String format1 = "|%-10s| %-25s| %-10s |%-10s |\n";
				System.out.printf(format1, "Username", " Email", "Password", "Last Login" );	
				System.out.println("+==============================================================+");
 			while (result.next()) {

 				String nama = result.getString("username");
 				String email = result.getString("email");
 				String pwd = result.getString("password");
 				Date tgl = result.getDate("login_terakhir");
 				String format = "|%-10s| %-25s| %-10s |%-10s |\n";
 				System.out.printf(format, nama,email,pwd,tgl);
 				System.out.println("+--------------------------------------------------------------+");
 			}
 		} catch (SQLException e) {
 			System.out.println("Tidak Dapat Mengakses Database");
 		}
 		user_setting();
 	}
 	
 	public void MainBarang() {
		Scanner sc = new Scanner (System.in);
		try {
			conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
	        if (conn!=null){
	        	
	            System.out.println("'''SELAMAT DATANG DI SUPERMARKET SI'''");
	        }
	        else {
	            System.out.println("Koneksi Gagal");
	        }
//	        menu.menu1();
	    }
	    catch (SQLException e){
	        System.out.println("Load Failed");
	    }
 	}
}
