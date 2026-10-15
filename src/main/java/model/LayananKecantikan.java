/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author HP VICTUS
 */
public class LayananKecantikan extends Layanan {

    private int durasiMenit;

    public LayananKecantikan(int idLayanan, String namaLayanan, int harga, int durasiMenit) {
        super(idLayanan, namaLayanan, harga);
        this.durasiMenit = durasiMenit;
    }

    public int getDurasiMenit() {
        return durasiMenit;
    }

    @Override
    public String getKategori() {
        return "Kecantikan";
    }

    @Override
    public String getInfo() {
        return super.getInfo()
                + "\nDurasi         : " + getDurasiMenit() + " menit";
    }
}