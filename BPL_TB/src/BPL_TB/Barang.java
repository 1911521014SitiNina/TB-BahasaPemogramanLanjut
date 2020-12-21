package olahbarang;

import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Barang extends Koneksi implements OlahBarang {
	
	static Connection conn;
	static Statement stmt;
	static ResultSet rs;
	
	Scanner input = new Scanner(System.in);
	Scanner input1 = new Scanner(System.in);
	User user = new User();
	
	String sku;
	String nama;
	int stock;
	int harga_beli;
	int harga_jual;
	String jwb;
	
	//Menu Data
    public void Menu() {
        System.out.println("\n--PILIHAN DATA BARANG--");
        System.out.println("1. Tambah Data Barang");
        System.out.println("2. Cari Data Barang");
        System.out.println("3. Ubah Data Barang");
        System.out.println("4. Hapus Data Barang");
        System.out.println("5. Lihat Data Barang");
        System.out.println("0. Kembali ke menu utama");
        System.out.print("Masukkan Pilihan : ");

        try {
            Integer pilih = input1.nextInt();
            switch (pilih) {
                case 0:
                    user.user_pilih();
                    break;
                case 1:
                    TambahBarang();
                    break;
                case 2:
                    CariBarang();
                    break;
                case 3:
                    UbahBarang();
                    break;
                case 4:
                    HapusBarang();
                    break;
                case 5 :
                	LihatData();
                	break;
                default:
                    System.out.println("Tidak menemukan pilihan yang anda masukkan!");
            }
        } 
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    //Tambah Data Barang
	@Override
	public void TambahBarang() throws Exception {
    	
		conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
		
		DataBarang brg = new DataBarang();
		
		System.out.println(" >>>>>>>>>> TAMBAH DATA BARANG <<<<<<<<<<");
		
		sku = brg.sku();
		nama = brg.nama();
		stock = brg.stock();
		harga_beli = brg.harga_beli();
		harga_jual = brg.harga_jual();
       
        try {
            stmt = conn.createStatement();
            String sql = "INSERT INTO barang VALUES ('"+sku+"', '"+nama+"', '"+stock+"', '"+harga_beli+"', '"+harga_jual+"')";   

            stmt.execute(sql);
            System.out.println("\nData barang berhasil disimpan");
    
        } 
        catch (Exception e) {
            e.printStackTrace();
        }            
		LihatData();
        
	}
	
	//Cari Data Barang
	@Override
	public void CariBarang() throws Exception {
		
		conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
		
		ArrayList <DataBarang> data = new ArrayList<>();
		
		System.out.println(" >>>>>>>>>> CARI DATA BARANG <<<<<<<<<<");
        
		System.out.print("Input nama barang yang akan dicari : ");
		String cari = input.nextLine();

		stmt = conn.createStatement();
		String sql = "SELECT * FROM barang WHERE nama='"+cari+"'";
		
		try {
			rs = stmt.executeQuery(sql);
			
        	if(rs.next()) { 
        		DataBarang n = new DataBarang();
        		
        		n.setSku(rs.getString("sku"));
        		n.setNama(rs.getString("nama"));
        		n.setStock(rs.getInt("stock"));
        		n.setHarga_beli(rs.getInt("harga_beli"));
        		n.setHarga_jual(rs.getInt("harga_jual"));
        		
        		data.add(n);
        		
				System.out.println(" ");
         		System.out.print("  SKU");
         		System.out.print("\t\t");
         		System.out.print("  NAMA BARANG");
         		System.out.print("\t\t");
         		System.out.print("  STOCK BARANG");
         		System.out.print("\t\t");
         		System.out.print("  HARGA BELI");
         		System.out.print("\t\t");
         		System.out.println("  HARGA JUAL ");
         		
         		for(DataBarang barang : data) {
    	        	System.out.print("  " +barang.sku);
    	        	System.out.print("\t\t");
    	        	System.out.print("  " +barang.nama);
    	        	System.out.print("\t\t");
    	        	System.out.print("  " +barang.stock);
    	        	System.out.print("\t\t\t");
    	        	System.out.print("  " +barang.harga_beli);
    	        	System.out.print("\t\t\t");
    	        	System.out.println("  " +barang.harga_jual);
            	}
        	}
        	else {
        		System.out.println("\nBarang tidak tersedia");
        	}
        	
            System.out.println("\nIngin mencari barang lagi? Y/T");
            System.out.print(">>>>> : ");
            jwb = input.nextLine();
            
            if(jwb.equalsIgnoreCase("y")) {
            	CariBarang();
            }
            else {
                System.out.println("\nKembali ke menu? Y/T");
                System.out.print(">>>>> : ");
                jwb = input.nextLine();
                
                if(jwb.equalsIgnoreCase("y")) {
                	Menu();
                }
                else {
                	user.logout();
                }
            }
        		
		}
		
		catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	//Ubah Barang
	@Override
	public void UbahBarang() throws Exception {
    	
		conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
		
		String sql;
		DataBarang brg = new DataBarang();
		
		System.out.println(" >>>>>>>>>> UBAH DATA BARANG <<<<<<<<<<");
        
        System.out.println("1. Nama");
        System.out.println("2. Stock");
		System.out.print("\nPilih data yang ingin diubah : ");
		Integer ubah = input1.nextInt();
		System.out.println(" ");
		
        stmt = conn.createStatement();
        
        
        try {
			switch (ubah) {
				case 1 : 
					System.out.print("Masukkan SKU pada data yang ingin di ubah : ");
					this.sku = input.nextLine();
			        nama = brg.nama();
			        
			        sql = "UPDATE barang SET nama='"+nama+"' WHERE sku='"+sku+"'";   
			        stmt.execute(sql);
					break;
				case 2 :
					System.out.print("Masukkan SKU pada data yang ingin di ubah : ");
					this.sku = input.nextLine();
			        stock = brg.stock();
			        
			        sql = "UPDATE barang SET stock='"+stock+"' WHERE sku='"+sku+"'";   
			        stmt.execute(sql);
					break;
					default :
					System.out.println("Pilihan Tidak Tersedia\n");
					UbahBarang();
			}

		}
        
        catch (Exception e) {
        	e.printStackTrace();
        }			
		LihatData();
		
	}
	
	//Hapus Data Barang
	@Override
	public void HapusBarang() throws Exception {
    	
		conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
		
		System.out.println(" >>>>>>>>>> HAPUS DATA BARANG <<<<<<<<<<");
		System.out.print("Masukkan SKU pada data barang yang akan di hapus : ");
		this.sku = input.nextLine();
		
		try {
			stmt = conn.createStatement();
			String sql = "DELETE FROM barang WHERE sku='"+sku+"'";
		
			stmt.execute(sql);
			System.out.println("\nData Berhasil Terhapus");

		}
		
		catch(Exception e) {
			e.printStackTrace();
		}			
		LihatData();

	}
	
	//Melihat Data
	@Override
	public void LihatData() throws Exception {
    	
		conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
		
    	stmt = conn.createStatement();
    	
    	ArrayList <DataBarang> data = new ArrayList<>();
        
        String sql = "SELECT * FROM barang";
        rs = stmt.executeQuery(sql);

        try {
        	System.out.println(" >>>>>>>>>> LIHAT DATA BARANG <<<<<<<<<<");
            
         		System.out.print("  SKU");
         		System.out.print("\t\t");
         		System.out.print("  NAMA BARANG");
         		System.out.print("\t\t");
         		System.out.print("  STOCK BARANG");
         		System.out.print("\t\t");
         		System.out.print("  HARGA BELI");
         		System.out.print("\t\t");
         		System.out.println("  HARGA JUAL");
        	
        	while(rs.next()) { 
        		DataBarang n = new DataBarang();
        		
        		n.setSku(rs.getString("sku"));
        		n.setNama(rs.getString("nama"));
        		n.setStock(rs.getInt("stock"));
        		n.setHarga_beli(rs.getInt("harga_beli"));
        		n.setHarga_jual(rs.getInt("harga_jual"));
        		
        		data.add(n);
        	}

        	for(DataBarang barang : data) {
	        	System.out.print("  " +barang.sku);
	        	System.out.print("\t\t");
	        	System.out.print("  " +barang.nama);
	        	System.out.print("\t\t");
	        	System.out.print("  " +barang.stock);
	        	System.out.print("\t\t\t");
	        	System.out.print("  " +barang.harga_beli);
	        	System.out.print("\t\t\t");
	        	System.out.println("  " +barang.harga_jual);
        	}
        	
            System.out.println("\nKembali ke Menu? Y/T");
            System.out.print(">>>>> : ");
            jwb = input.nextLine();
            
            if(jwb.equalsIgnoreCase("y")) {
            	Menu();
            }
            else {
            	user.logout();
            }

        } 
        
        catch (Exception e) {
            e.printStackTrace();
        }
		
	}
	
}
