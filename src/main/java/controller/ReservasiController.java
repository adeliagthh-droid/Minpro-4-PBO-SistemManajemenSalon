/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

/**
 *
 * @author HP VICTUS
 */
import java.util.ArrayList;
import model.Layanan;
import model.LayananDAO;
import model.Pelanggan;
import model.PelangganDAO;
import model.Reservasi;
import model.ReservasiDAO;
import view.InputView;
import view.MenuView;

public class ReservasiController implements KelolaData {

    private ReservasiDAO reservasiDAO;
    private PelangganDAO pelangganDAO;
    private LayananDAO layananDAO;
    private MenuView menuView;
    private InputView inputView;
    private KelolaData pelangganController;
    private KelolaData layananController;

    public ReservasiController(MenuView menuView, InputView inputView, KelolaData pelangganController, KelolaData layananController) {
        this.reservasiDAO = new ReservasiDAO();
        this.pelangganDAO = new PelangganDAO();
        this.layananDAO = new LayananDAO();
        this.menuView = menuView;
        this.inputView = inputView;
        this.pelangganController = pelangganController;
        this.layananController = layananController;
    }

    @Override
    public void jalankanMenu() {
        int pilihan;

        do {
            menuView.tampilkanSubMenu("Reservasi");
            pilihan = inputView.inputAngka("Pilih: ", 1, 5);

            switch (pilihan) {
                case 1:
                    tambah();
                    break;
                case 2:
                    tampilkan();
                    break;
                case 3:
                    ubah();
                    break;
                case 4:
                    hapus();
                    break;
            }
        } while (pilihan != 5);
    }

    @Override
    public void tampilkan() {
        menuView.tampilkanJudul("Daftar Reservasi");
        ArrayList<Reservasi> daftarReservasi = reservasiDAO.getSemua();

        if (daftarReservasi.isEmpty()) {
            menuView.tampilkanPesan("Belum ada data reservasi.");
            return;
        }

        for (int i = 0; i < daftarReservasi.size(); i++) {
            menuView.tampilkanData(daftarReservasi.get(i).getInfo());
        }
    }

    private void tambah() {
        if (pelangganDAO.getSemua().isEmpty() || layananDAO.getSemua().isEmpty()) {
            menuView.tampilkanPesan("Data pelanggan/layanan masih kosong, tambahin dulu!");
            return;
        }

        menuView.tampilkanJudul("Tambah Reservasi");
        pelangganController.tampilkan();
        Pelanggan pelanggan = pelangganDAO.cariById(inputView.inputAngka("Masukkan ID Pelanggan: "));

        if (pelanggan == null) {
            menuView.tampilkanPesan("ID Pelanggan tidak ditemukan. Reservasi dibatalkan.");
            return;
        }

        layananController.tampilkan();
        Layanan layanan = layananDAO.cariById(inputView.inputAngka("Masukkan ID Layanan: "));

        if (layanan == null) {
            menuView.tampilkanPesan("ID Layanan tidak ditemukan. Reservasi dibatalkan.");
            return;
        }

        String tanggal = inputView.inputTanggal("Tanggal (dd-mm-yyyy): ");

        reservasiDAO.tambah(new Reservasi(0, pelanggan, layanan, tanggal));
        menuView.tampilkanPesan("yeayy Data Reservasi sudah ditambahkan!");
    }

    private void ubah() {
        tampilkan();

        if (reservasiDAO.getSemua().isEmpty()) {
            return;
        }

        Reservasi reservasi = reservasiDAO.cariById(inputView.inputAngka("Masukkan ID Reservasi yang ingin diubah: "));

        if (reservasi == null) {
            menuView.tampilkanPesan("ID Reservasi tidak ditemukan.");
            return;
        }

        menuView.tampilkanPesan("Tanggal lama: " + reservasi.getTanggal());
        reservasi.setTanggal(inputView.inputTanggal("Tanggal baru (dd-mm-yyyy): "));
        reservasiDAO.ubah(reservasi);
        menuView.tampilkanPesan("yeayy Data Reservasi sudah diubah!");
    }

    private void hapus() {
        tampilkan();

        if (reservasiDAO.getSemua().isEmpty()) {
            return;
        }

        Reservasi reservasi = reservasiDAO.cariById(inputView.inputAngka("Masukkan ID Reservasi yang ingin dihapus: "));

        if (reservasi == null) {
            menuView.tampilkanPesan("ID Reservasi tidak ditemukan.");
            return;
        }

        if (!inputView.inputKonfirmasi("Yakin ingin menghapus reservasi ini?")) {
            menuView.tampilkanPesan("Penghapusan dibatalkan.");
            return;
        }

        reservasiDAO.hapus(reservasi.getIdReservasi());
        menuView.tampilkanPesan("yeayy Data Reservasi sudah dihapus!");
    }
}