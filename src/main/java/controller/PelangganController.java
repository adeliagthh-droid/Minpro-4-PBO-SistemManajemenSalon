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
import model.Pelanggan;
import model.PelangganDAO;
import view.InputView;
import view.MenuView;

public class PelangganController implements KelolaData {

    private PelangganDAO pelangganDAO;
    private MenuView menuView;
    private InputView inputView;

    public PelangganController(MenuView menuView, InputView inputView) {
        this.pelangganDAO = new PelangganDAO();
        this.menuView = menuView;
        this.inputView = inputView;
    }

    @Override
    public void jalankanMenu() {
        int pilihan;

        do {
            menuView.tampilkanSubMenu("Pelanggan");
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
        menuView.tampilkanJudul("Daftar Pelanggan");
        ArrayList<Pelanggan> daftarPelanggan = pelangganDAO.getSemua();

        if (daftarPelanggan.isEmpty()) {
            menuView.tampilkanPesan("Belum ada data pelanggan.");
            return;
        }

        for (int i = 0; i < daftarPelanggan.size(); i++) {
            menuView.tampilkanData(daftarPelanggan.get(i).getInfo());
        }
    }

    private void tambah() {
        menuView.tampilkanJudul("Tambah Pelanggan");
        String nama = inputView.inputNama("Nama Pelanggan : ");
        String noTelepon = inputView.inputNoTelepon("No Telepon     : ");

        pelangganDAO.tambah(new Pelanggan(0, nama, noTelepon));
        menuView.tampilkanPesan("yeayy Data Pelanggan sudah ditambahkan!");
    }

    private void ubah() {
        tampilkan();

        if (pelangganDAO.getSemua().isEmpty()) {
            return;
        }

        Pelanggan pelanggan = pelangganDAO.cariById(inputView.inputAngka("Masukkan ID Pelanggan yang ingin diubah: "));

        if (pelanggan == null) {
            menuView.tampilkanPesan("ID Pelanggan tidak ditemukan.");
            return;
        }

        pelanggan.setNama(inputView.inputNama("Nama baru       : "));
        pelanggan.setNoTelepon(inputView.inputNoTelepon("No Telepon baru : "));
        pelangganDAO.ubah(pelanggan);
        menuView.tampilkanPesan("yeayy Data Pelanggan sudah diubah!");
    }

    private void hapus() {
        tampilkan();

        if (pelangganDAO.getSemua().isEmpty()) {
            return;
        }

        Pelanggan pelanggan = pelangganDAO.cariById(inputView.inputAngka("Masukkan ID Pelanggan yang ingin dihapus: "));

        if (pelanggan == null) {
            menuView.tampilkanPesan("ID Pelanggan tidak ditemukan.");
            return;
        }

        if (!inputView.inputKonfirmasi("Yakin ingin menghapus " + pelanggan.getNama() + "?")) {
            menuView.tampilkanPesan("Penghapusan dibatalkan.");
            return;
        }

        int jumlahReservasiTerhapus = pelangganDAO.hapus(pelanggan.getIdPelanggan());
        menuView.tampilkanPesan("yeayy Data Pelanggan sudah dihapus!");

        if (jumlahReservasiTerhapus > 0) {
            menuView.tampilkanPesan(jumlahReservasiTerhapus + " data reservasi milik pelanggan ini ikut dihapus.");
        }
    }
}