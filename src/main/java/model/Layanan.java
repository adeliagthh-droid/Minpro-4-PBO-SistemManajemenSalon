/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author HP VICTUS
 */
public abstract class Layanan {

    private int idLayanan;
    private String namaLayanan;
    private int harga;

    public Layanan(int idLayanan, String namaLayanan, int harga) {
        this.idLayanan = idLayanan;
        this.namaLayanan = namaLayanan;
        this.harga = harga;
    }

    public int getIdLayanan() {
        return idLayanan;
    }

    public String getNamaLayanan() {
        return namaLayanan;
    }

    public void setNamaLayanan(String namaLayanan) {
        this.namaLayanan = namaLayanan;
    }

    public int getHarga() {
        return harga;
    }

    public void setHarga(int harga) {
        this.harga = harga;
    }

    public abstract String getKategori();

    public String getInfo() {
        return "ID Layanan     : " + idLayanan
                + "\nNama Layanan   : " + namaLayanan
                + "\nKategori       : " + getKategori()
                + "\nHarga          : Rp " + harga;
    }
}