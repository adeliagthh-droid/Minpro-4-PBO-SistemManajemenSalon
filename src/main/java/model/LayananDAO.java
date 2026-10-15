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

public class LayananDAO {

    public ArrayList<Layanan> getSemua() {
        ArrayList<Layanan> daftarLayanan = new ArrayList<>();
        String sql = "SELECT id_layanan, nama_layanan, harga, kategori, panjang_rambut, durasi_menit FROM layanan ORDER BY id_layanan";
        Connection conn = Koneksi.getConnection();

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                if (rs.getString("kategori").equals("Rambut")) {
                    daftarLayanan.add(new LayananRambut(rs.getInt("id_layanan"), rs.getString("nama_layanan"), rs.getInt("harga"), rs.getString("panjang_rambut")));
                } else {
                    daftarLayanan.add(new LayananKecantikan(rs.getInt("id_layanan"), rs.getString("nama_layanan"), rs.getInt("harga"), rs.getInt("durasi_menit")));
                }
            }
        } catch (SQLException e) {
            System.out.println("Gagal mengambil data layanan: " + e.getMessage());
        }

        return daftarLayanan;
    }

    public Layanan cariById(int idLayanan) {
        Layanan layanan = null;
        String sql = "SELECT id_layanan, nama_layanan, harga, kategori, panjang_rambut, durasi_menit FROM layanan WHERE id_layanan = ?";
        Connection conn = Koneksi.getConnection();

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, idLayanan);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                if (rs.getString("kategori").equals("Rambut")) {
                    layanan = new LayananRambut(rs.getInt("id_layanan"), rs.getString("nama_layanan"), rs.getInt("harga"), rs.getString("panjang_rambut"));
                } else {
                    layanan = new LayananKecantikan(rs.getInt("id_layanan"), rs.getString("nama_layanan"), rs.getInt("harga"), rs.getInt("durasi_menit"));
                }
            }
        } catch (SQLException e) {
            System.out.println("Gagal mencari data layanan: " + e.getMessage());
        }

        return layanan;
    }

    public void tambah(LayananRambut layanan) {
        String sql = "INSERT INTO layanan (nama_layanan, harga, kategori, panjang_rambut) VALUES (?, ?, ?, ?)";
        Connection conn = Koneksi.getConnection();

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, layanan.getNamaLayanan());
            pstmt.setInt(2, layanan.getHarga());
            pstmt.setString(3, layanan.getKategori());
            pstmt.setString(4, layanan.getPanjangRambut());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Gagal menambah data layanan: " + e.getMessage());
        }
    }

    public void tambah(LayananKecantikan layanan) {
        String sql = "INSERT INTO layanan (nama_layanan, harga, kategori, durasi_menit) VALUES (?, ?, ?, ?)";
        Connection conn = Koneksi.getConnection();

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, layanan.getNamaLayanan());
            pstmt.setInt(2, layanan.getHarga());
            pstmt.setString(3, layanan.getKategori());
            pstmt.setInt(4, layanan.getDurasiMenit());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Gagal menambah data layanan: " + e.getMessage());
        }
    }

    public void ubah(Layanan layanan) {
        String sql = "UPDATE layanan SET nama_layanan = ?, harga = ? WHERE id_layanan = ?";
        Connection conn = Koneksi.getConnection();

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, layanan.getNamaLayanan());
            pstmt.setInt(2, layanan.getHarga());
            pstmt.setInt(3, layanan.getIdLayanan());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Gagal mengubah data layanan: " + e.getMessage());
        }
    }

    public int hapus(int idLayanan) {
        int jumlahReservasiTerhapus = hitungReservasiMemakaiLayanan(idLayanan);
        String sql = "DELETE FROM layanan WHERE id_layanan = ?";
        Connection conn = Koneksi.getConnection();

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, idLayanan);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Gagal menghapus data layanan: " + e.getMessage());
        }

        return jumlahReservasiTerhapus;
    }

    private int hitungReservasiMemakaiLayanan(int idLayanan) {
        int jumlah = 0;
        String sql = "SELECT COUNT(*) AS jumlah FROM reservasi WHERE id_layanan = ?";
        Connection conn = Koneksi.getConnection();

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, idLayanan);
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