/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author HP VICTUS
 */
import config.Koneksi;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PelangganDAO {

    public ArrayList<Pelanggan> getSemua() {
        ArrayList<Pelanggan> daftarPelanggan = new ArrayList<>();
        String sql = "SELECT id_pelanggan, nama, no_telepon FROM pelanggan ORDER BY id_pelanggan";
        Connection conn = Koneksi.getConnection();

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                daftarPelanggan.add(new Pelanggan(rs.getInt("id_pelanggan"), rs.getString("nama"), rs.getString("no_telepon")));
            }
        } catch (SQLException e) {
            System.out.println("Gagal mengambil data pelanggan: " + e.getMessage());
        }

        return daftarPelanggan;
    }

    public Pelanggan cariById(int idPelanggan) {
        Pelanggan pelanggan = null;
        String sql = "SELECT id_pelanggan, nama, no_telepon FROM pelanggan WHERE id_pelanggan = ?";
        Connection conn = Koneksi.getConnection();

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, idPelanggan);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                pelanggan = new Pelanggan(rs.getInt("id_pelanggan"), rs.getString("nama"), rs.getString("no_telepon"));
            }
        } catch (SQLException e) {
            System.out.println("Gagal mencari data pelanggan: " + e.getMessage());
        }

        return pelanggan;
    }

    public void tambah(Pelanggan pelanggan) {
        String sql = "INSERT INTO pelanggan (nama, no_telepon) VALUES (?, ?)";
        Connection conn = Koneksi.getConnection();

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, pelanggan.getNama());
            pstmt.setString(2, pelanggan.getNoTelepon());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Gagal menambah data pelanggan: " + e.getMessage());
        }
    }

    public void ubah(Pelanggan pelanggan) {
        String sql = "UPDATE pelanggan SET nama = ?, no_telepon = ? WHERE id_pelanggan = ?";
        Connection conn = Koneksi.getConnection();

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, pelanggan.getNama());
            pstmt.setString(2, pelanggan.getNoTelepon());
            pstmt.setInt(3, pelanggan.getIdPelanggan());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Gagal mengubah data pelanggan: " + e.getMessage());
        }
    }

    public int hapus(int idPelanggan) {
        int jumlahReservasiTerhapus = hitungReservasiMilikPelanggan(idPelanggan);
        String sql = "DELETE FROM pelanggan WHERE id_pelanggan = ?";
        Connection conn = Koneksi.getConnection();

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, idPelanggan);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Gagal menghapus data pelanggan: " + e.getMessage());
        }

        return jumlahReservasiTerhapus;
    }

    private int hitungReservasiMilikPelanggan(int idPelanggan) {
        int jumlah = 0;
        String sql = "SELECT COUNT(*) AS jumlah FROM reservasi WHERE id_pelanggan = ?";
        Connection conn = Koneksi.getConnection();

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, idPelanggan);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                jumlah = rs.getInt("jumlah");
            }
        } catch (SQLException e) {
            System.out.println("Gagal menghitung data reservasi: " + e.getMessage());
        }

        return jumlah;
    }
}