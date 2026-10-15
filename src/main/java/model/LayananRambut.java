/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author HP VICTUS
 */
public class LayananRambut extends Layanan {

    private String panjangRambut;

    public LayananRambut(int idLayanan, String namaLayanan, int harga, String panjangRambut) {
        super(idLayanan, namaLayanan, harga);
        this.panjangRambut = panjangRambut;
    }

    public String getPanjangRambut() {
        return panjangRambut;
    }

    @Override
    public String getKategori() {
        return "Rambut";
    }

    @Override
    public String getInfo() {
        return super.getInfo()
                + "\nPanjang Rambut : " + getPanjangRambut();
    }
}
