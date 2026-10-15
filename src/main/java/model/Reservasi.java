/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author HP VICTUS
 */
public class Reservasi {

    private int idReservasi;
    private Pelanggan pelanggan;
    private Layanan layanan;
    private String tanggal;

    public Reservasi(int idReservasi, Pelanggan pelanggan, Layanan layanan, String tanggal) {
        this.idReservasi = idReservasi;
        this.pelanggan = pelanggan;
        this.layanan = layanan;
        this.tanggal = tanggal;
    }

    public int getIdReservasi() {
        return idReservasi;
    }

    public Pelanggan getPelanggan() {
        return pelanggan;
    }

    public Layanan getLayanan() {
        return layanan;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getInfo() {
        return "ID Reservasi   : " + idReservasi
                + "\nNama Pelanggan : " + pelanggan.getNama()
                + "\nLayanan        : " + layanan.getNamaLayanan() + " (" + layanan.getKategori() + ")"
                + "\nHarga          : Rp " + layanan.getHarga()
                + "\nTanggal        : " + tanggal;
    }
}