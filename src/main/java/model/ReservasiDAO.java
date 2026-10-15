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

public class ReservasiDAO {

    public ArrayList<Reservasi> getSemua() {
        ArrayList<Reservasi> daftarReservasi = new ArrayList<>();
        String sql = "SELECT r.id_reservasi, r.tanggal, p.id_pelanggan, p.nama, p.no_telepon, "
                + "l.id_layanan, l.nama_layanan, l.harga, l.kategori, l.panjang_rambut, l.durasi_menit "
                + "FROM reservasi r "
                + "JOIN pelanggan p ON r.id_pelanggan = p.id_pelanggan "
                + "JOIN layanan l ON r.id_layanan = l.id_layanan "
                + "ORDER BY r.id_reservasi";
        Connection conn = Koneksi.getConnection();

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Pelanggan pelanggan = new Pelanggan(rs.getInt("id_pelanggan"), rs.getString("nama"), rs.getString("no_telepon"));
                Layanan layanan;

                if (rs.getString("kategori").equals("Rambut")) {
                    layanan = new LayananRambut(rs.getInt("id_layanan"), rs.getString("nama_layanan"), rs.getInt("harga"), rs.getString("panjang_rambut"));
                } else {
                    layanan = new LayananKecantikan(rs.getInt("id_layanan"), rs.getString("nama_layanan"), rs.getInt("harga"), rs.getInt("durasi_menit"));
                }

                daftarReservasi.add(new Reservasi(rs.getInt("id_reservasi"), pelanggan, layanan, rs.getString("tanggal")));
            }
        } catch (SQLException e) {
            System.out.println("Gagal mengambil data reservasi: " + e.getMessage());
        }

        return daftarReservasi;
    }

    public Reservasi cariById(int idReservasi) {
        Reservasi reservasi = null;
        String sql = "SELECT r.id_reservasi, r.tanggal, p.id_pelanggan, p.nama, p.no_telepon, "
                + "l.id_layanan, l.nama_layanan, l.harga, l.kategori, l.panjang_rambut, l.durasi_menit "
                + "FROM reservasi r "
                + "JOIN pelanggan p ON r.id_pelanggan = p.id_pelanggan "
                + "JOIN layanan l ON r.id_layanan = l.id_layanan "
                + "WHERE r.id_reservasi = ?";
        Connection conn = Koneksi.getConnection();

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, idReservasi);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                Pelanggan pelanggan = new Pelanggan(rs.getInt("id_pelanggan"), rs.getString("nama"), rs.getString("no_telepon"));
                Layanan layanan;

                if (rs.getString("kategori").equals("Rambut")) {
                    layanan = new LayananRambut(rs.getInt("id_layanan"), rs.getString("nama_layanan"), rs.getInt("harga"), rs.getString("panjang_rambut"));
                } else {
                    layanan = new LayananKecantikan(rs.getInt("id_layanan"), rs.getString("nama_layanan"), rs.getInt("harga"), rs.getInt("durasi_menit"));
                }

                reservasi = new Reservasi(rs.getInt("id_reservasi"), pelanggan, layanan, rs.getString("tanggal"));
            }
        } catch (SQLException e) {
            System.out.println("Gagal mencari data reservasi: " + e.getMessage());
        }

        return reservasi;
    }

    public void tambah(Reservasi reservasi) {
        String sql = "INSERT INTO reservasi (id_pelanggan, id_layanan, tanggal) VALUES (?, ?, ?)";
        Connection conn = Koneksi.getConnection();

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, reservasi.getPelanggan().getIdPelanggan());
            pstmt.setInt(2, reservasi.getLayanan().getIdLayanan());
            pstmt.setString(3, reservasi.getTanggal());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Gagal menambah data reservasi: " + e.getMessage());
        }
    }

    public void ubah(Reservasi reservasi) {
        String sql = "UPDATE reservasi SET tanggal = ? WHERE id_reservasi = ?";
        Connection conn = Koneksi.getConnection();

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, reservasi.getTanggal());
            pstmt.setInt(2, reservasi.getIdReservasi());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Gagal mengubah data reservasi: " + e.getMessage());
        }
    }

    public void hapus(int idReservasi) {
        String sql = "DELETE FROM reservasi WHERE id_reservasi = ?";
        Connection conn = Koneksi.getConnection();

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, idReservasi);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Gagal menghapus data reservasi: " + e.getMessage());
        }
    }
}