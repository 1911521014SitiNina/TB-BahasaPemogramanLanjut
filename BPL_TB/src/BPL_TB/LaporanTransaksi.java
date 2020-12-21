package olahbarang;

import java.sql.*;
import java.util.Scanner;

public class LaporanTransaksi extends Koneksi{
	Scanner input = new Scanner(System.in);
	Scanner sc = new Scanner(System.in);
	
	static Connection conn;
	static Statement stmt;
	static ResultSet rs;
	
	Integer id, harga_beli, harga_jual, jumlah, harga, total_beli, total_jual, untung;
	String sku, nama, noresi;
	Date tanggal;
	
	User user = new User();
	
	public void MenuLaporan () throws Exception{
		System.out.println("\n+------------------------------+");
		System.out.println("|          MENU LAPORAN        |");
		System.out.println("+------------------------------+");
		System.out.println("| 1. Laporan Harian            |");
		System.out.println("| 2. Laporan Bulanan           |");
		System.out.println("| 3. Kembali ke Menu Utama     |");
                System.out.println("+------------------------------+");
		System.out.print("Masukkan Pilihan Anda : ");
		int masuk = input.nextInt();
		switch(masuk) {
			case 1:
				LaporanPerHari();
				break;
			case 2:
				LaporanPerBulan();
				break;
			case 3:
				user.user_menu();
				break;
			default:
				System.out.println("\nPilihan Anda Salah");
				System.out.println("\n");
				MenuLaporan();
		}
	}
	
	private void cek() throws Exception {
		System.out.println("\n\nApakah anda ingin melanjutkan program?(y/t)");
		String cek = sc.nextLine();
		cek.toUpperCase();
		if(cek.equalsIgnoreCase("y")) {
			MenuLaporan();
		}
		else if(cek.equalsIgnoreCase("t")) {
			System.out.println("Kembali ke Menu Utama");
			user.user_menu();
		}
		else {
			System.out.println("\nMasukkan Pilihan dengan Benar!\n");
			cek();
		}
	}
	
	private void kembali() throws Exception {
		System.out.println("Apakah anda ingin melanjutkan program?(y/t)");
		String masuk = sc.nextLine();
		masuk.toUpperCase();
		if(masuk.equalsIgnoreCase("y")) {
			System.out.println("\n");
			LaporanPerBulan();
		}
		else if(masuk.equalsIgnoreCase("t")) {
			System.out.println("\nKembali ke menu");
			MenuLaporan();
		}
		else {
			System.out.println("\nPilihan Salah");
			System.out.println("\n");
			kembali();
		}
	}
	
	public void LaporanPerHari() throws Exception{
		try {
			conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
	    	stmt = conn.createStatement();
	        
	        String format = "|%s\t| %s\t| %s\t| %s\t| %s\t| %s\t| %s\t| %s\t| %s\t|";
	        String format1 = "|%d\t| %s\t\t| %s\t|  %s\t| %s\t|   %d\t|   %d\t|    %d\t\t| %d\t|";
	        
	        System.out.print("\nMasukkan Tanggal (YYYY/MM/DD) : ");
	        String tgl = sc.nextLine();
	        
	        String sql = "SELECT * FROM transaksi_detail INNER JOIN barang  ON transaksi_detail.sku = barang.sku INNER JOIN transaksi ON transaksi_detail.noresi = transaksi.noresi WHERE tanggal='"+tanggal+"'";
	        rs = stmt.executeQuery(sql);
	        
	        if(rs.next()==false) {
	        	System.out.println("Data Tidak Ditemukan");
	        	System.out.println("\n");
	        	LaporanPerHari();
	        }
	        else {
	        	System.out.println("\n+-------------------------------------------------------------------------------------------------------------+");
	        	System.out.println("|                                       LAPORAN PENJUALAN HARIAN                                                |");
	        	System.out.println("+---------------------------------------------------------------------------------------------------------------+");
	        	System.out.printf(format, "ID", "No.Resi", "Tanggal", "SKU", "Nama", "Harga Beli", "Harga Jual", "Jumlah", "Total");
	          	do {
			        	id = rs.getInt("id");
			        	noresi = rs.getString("noresi");
			        	tanggal = rs.getDate("tanggal");
			        	sku = rs.getString("sku");
			        	nama = rs.getString("nama");
			        	harga_beli = rs.getInt("harga_beli");
			        	harga_jual = rs.getInt("harga_jual");
			        	jumlah = rs.getInt("jumlah");
			        	harga = rs.getInt("harga");
			        	
			        	total_beli=total_beli+harga_beli;
			        	total_jual=total_jual+harga_jual;
			        	
			        	System.out.println("\n");
			        	System.out.printf(format1, id, noresi, tanggal, sku, nama, harga_beli, harga_jual, jumlah, harga);
	          	} while(rs.next());
		        untung = total_jual-total_beli;
		        System.out.println("\n+---------------------------------------------------------------------------------------------------------------+");
		        System.out.println("| Total penjualan barang per hari      : "+total_jual+"                                                          |");
		        System.out.println("| Total modal barang terpakai per hari : "+total_beli+"                                                          |");
		        System.out.println("| Keuntungan per hari                  : "+untung+"                                                              |");
		        System.out.println("+---------------------------------------------------------------------------------------------------------------+");
		        cek();
	        } 
	        
		}
		catch (SQLException e){
			 e.printStackTrace();
		}

	}
        
        private void TampilanBulan(){
            System.out.println("DAFTAR BULAN");
            System.out.println("1. Januari");
            System.out.println("2. Februari");
            System.out.println("3. Maret");
            System.out.println("4. April");
            System.out.println("5. Mei");
            System.out.println("6. Juni");
            System.out.println("7. Juli");
            System.out.println("8. Agustus");
            System.out.println("9. September");
            System.out.println("10. Oktober");
            System.out.println("11. November");
            System.out.println("12. Desember");
        }
        
	public void LaporanPerBulan() throws Exception {
		try {

			conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
	    	stmt = conn.createStatement();
	        
	        String format = "|%s\t| %s\t| %s\t| %s\t| %s\t| %s\t| %s\t| %s\t| %s\t|";
	        String format1 = "|%d\t| %s\t\t| %s\t|  %s\t| %s\t|   %d\t|   %d\t|    %d\t\t| %d\t|";
	        
	        TampilanBulan();
	        System.out.print("\nPilih Bulan : ");
	        String tgl = sc.nextLine();
	        System.out.print("Masukkan Tahun (YYYY) : ");
	        String tgl1 = sc.nextLine();
	        
	        String sql = "SELECT * FROM transaksi_detail INNER JOIN barang  ON transaksi_detail.sku = barang.sku INNER JOIN transaksi ON transaksi_detail.noresi = transaksi.noresi WHERE MONTH(tanggal)='"+tanggal+"' AND YEAR(tanggal)='"+tgl1+"'";
	        rs = stmt.executeQuery(sql);
	        
	        if(rs.next()==false) {
	        	System.out.println("\nData Tidak Ada atau Format Salah");
	        	System.out.println("\n");
	        	kembali();
	        }
	        else {
	        	System.out.println("\n+---------------------------------------------------------------------------------------------------------------+");
                        System.out.println("|                                           LAPORAN PENJUALAN BULANAN                                           |");
	        	System.out.println("+---------------------------------------------------------------------------------------------------------------+\n");
	        	System.out.printf(format, "ID", "No.Resi", "Tanggal", "SKU", "Nama", "Harga Beli", "Harga Jual", "Jumlah", "Total");
	          	do {
			        	id = rs.getInt("id");
			        	noresi = rs.getString("noresi");
			        	tanggal = rs.getDate("tanggal");
			        	sku = rs.getString("sku");
			        	nama = rs.getString("nama");
			        	harga_beli = rs.getInt("harga_beli");
			        	harga_jual = rs.getInt("harga_jual");
			        	jumlah = rs.getInt("jumlah");
			        	harga = rs.getInt("harga");
			        	
			        	total_beli=total_beli+harga_beli;
			        	total_jual=total_jual+harga_jual;
			        	
			        	System.out.println("\n");
			        	System.out.printf(format1, id, noresi, tanggal, sku, nama, harga_beli, harga_jual, jumlah, harga);
	          	} 
                        
                        while(rs.next());
		        untung = total_jual-total_beli;
	          	System.out.println("\n+---------------------------------------------------------------------------------------------------------------+");
		        System.out.println("| Total Penjualan Barang per Hari      : "+total_jual+"                                                         |");
		        System.out.println("| Total Modal Barang erpakai per hari : "+total_beli+"                                                         |");
		        System.out.println("| Keuntungan per hari                  : "+untung+"                                                              |");
		        System.out.println("+---------------------------------------------------------------------------------------------------------------+");
		        cek();
                    }    
		}
		catch (SQLException e) {
			 e.printStackTrace();
		}
	}}
