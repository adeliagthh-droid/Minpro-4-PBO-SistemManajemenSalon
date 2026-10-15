/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package orm;

/**
 *
 * @author HP VICTUS
 */
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "pelanggan")
public class PelangganORM {

    @Id
    @Column(name = "id_pelanggan")
    private int idPelanggan;

    @Column(name = "nama")
    private String nama;

    @Column(name = "no_telepon")
    private String noTelepon;

    public int getIdPelanggan() {
        return idPelanggan;
    }

    public String getNama() {
        return nama;
    }

    public String getNoTelepon() {
        return noTelepon;
    }

    public String getInfo() {
        return "ID Pelanggan   : " + idPelanggan
                + "\nNama           : " + nama
                + "\nNo Telepon     : " + noTelepon;
    }
}