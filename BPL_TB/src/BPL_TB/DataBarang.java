package BPL_TB;

import java.util.Scanner;

public class DataBarang {
	String sku;
	String nama;
	int stock;
	int harga_beli;
	int harga_jual;
	
	
	Scanner input = new Scanner(System.in);
	Scanner input1 = new Scanner(System.in);
	
	public String sku() {
		System.out.print("SKU : ");
        		sku = input.nextLine();
		return sku;
	}
	
	public String nama() {
        		System.out.print("Nama Barang : ");
        		nama = input.nextLine();
        		return nama;
	}
	
	public int stock() {
        		System.out.print("Stok Barang : ");
        		stock = input1.nextInt();
        		return stock;
	}
	
	public int harga_beli() {
        		System.out.print("Harga Beli : ");
        		harga_beli = input1.nextInt();
        		return harga_beli;
	}
	
	public int harga_jual() {
        		System.out.print("Harga Jual : ");
        		harga_jual = input1.nextInt();
        		return harga_jual;
	}
	
	public void setSku(String sku) {
		this.sku = sku;
	}
	
	public void setNama(String nama) {
		this.nama = nama;
	}
	
	public void setStock(Integer stock) {
		this.stock = stock;
	}
	
	public void setHarga_beli(Integer harga_beli) {
		this.harga_beli = harga_beli;
	}
	
	public void setHarga_jual(Integer harga_jual) {
		this.harga_jual = harga_jual;
	}
}
