/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

/**
 *
 * @author HP VICTUS
 */
public class MenuView {

    public void tampilkanMenuUtama() {
        System.out.println();
        System.out.println("SISTEM MANAJEMEN SALON");
        System.out.println("1. Kelola Pelanggan");
        System.out.println("2. Kelola Layanan");
        System.out.println("3. Kelola Reservasi");
        System.out.println("4. Keluar");
    }

    public void tampilkanSubMenu(String namaData) {
        System.out.println();
        System.out.println("MENU " + namaData.toUpperCase());
        System.out.println("1. Tambah " + namaData);
        System.out.println("2. Tampilkan " + namaData);
        System.out.println("3. Ubah " + namaData);
        System.out.println("4. Hapus " + namaData);
        System.out.println("5. Kembali");
    }

    public void tampilkanJudul(String judul) {
        System.out.println();
        System.out.println(judul.toUpperCase());
    }

    public void tampilkanData(String info) {
        System.out.println(info);
        System.out.println("--------------------------------");
    }

    public void tampilkanPesan(String pesan) {
        System.out.println(pesan);
    }
}
