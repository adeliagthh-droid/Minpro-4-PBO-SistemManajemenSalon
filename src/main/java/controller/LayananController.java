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
import model.LayananKecantikan;
import model.LayananRambut;
import view.InputView;
import view.MenuView;

public class LayananController implements KelolaData {

    private LayananDAO layananDAO;
    private MenuView menuView;
    private InputView inputView;

    public LayananController(MenuView menuView, InputView inputView) {
        this.layananDAO = new LayananDAO();
        this.menuView = menuView;
        this.inputView = inputView;
    }

    @Override
    public void jalankanMenu() {
        int pilihan;

        do {
            menuView.tampilkanSubMenu("Layanan");
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
        menuView.tampilkanJudul("Daftar Layanan");
        ArrayList<Layanan> daftarLayanan = layananDAO.getSemua();

        if (daftarLayanan.isEmpty()) {
            menuView.tampilkanPesan("Belum ada data layanan.");
            return;
        }

        for (int i = 0; i < daftarLayanan.size(); i++) {
            menuView.tampilkanData(daftarLayanan.get(i).getInfo());
        }
    }

    private void tambah() {
        menuView.tampilkanJudul("Tambah Layanan");
        menuView.tampilkanPesan("Kategori: 1. Rambut  2. Kecantikan");
        int kategori = inputView.inputAngka("Pilih kategori : ", 1, 2);
        String nama = inputView.inputNama("Nama Layanan   : ");
        int harga = inputView.inputAngka("Harga (Rp)     : ", 10000, 5000000);

        if (kategori == 1) {
            menuView.tampilkanPesan("Panjang rambut: 1. Pendek  2. Sedang  3. Panjang");
            int pilihanPanjang = inputView.inputAngka("Pilih panjang  : ", 1, 3);
            String panjangRambut = "Pendek";

            if (pilihanPanjang == 2) {
                panjangRambut = "Sedang";
            } else if (pilihanPanjang == 3) {
                panjangRambut = "Panjang";
            }

            layananDAO.tambah(new LayananRambut(0, nama, harga, panjangRambut));
        } else {
            int durasi = inputView.inputAngka("Durasi (menit) : ", 15, 240);
            layananDAO.tambah(new LayananKecantikan(0, nama, harga, durasi));
        }

        menuView.tampilkanPesan("yeayy Data Layanan sudah ditambahkan!");
    }

    private void ubah() {
        tampilkan();

        if (layananDAO.getSemua().isEmpty()) {
            return;
        }

        Layanan layanan = layananDAO.cariById(inputView.inputAngka("Masukkan ID Layanan yang ingin diubah: "));

        if (layanan == null) {
            menuView.tampilkanPesan("ID Layanan tidak ditemukan.");
            return;
        }

        layanan.setNamaLayanan(inputView.inputNama("Nama Layanan baru : "));
        layanan.setHarga(inputView.inputAngka("Harga baru (Rp)   : ", 10000, 5000000));
        layananDAO.ubah(layanan);
        menuView.tampilkanPesan("yeayy Data Layanan sudah diubah!");
    }

    private void hapus() {
        tampilkan();

        if (layananDAO.getSemua().isEmpty()) {
            return;
        }

        Layanan layanan = layananDAO.cariById(inputView.inputAngka("Masukkan ID Layanan yang ingin dihapus: "));

        if (layanan == null) {
            menuView.tampilkanPesan("ID Layanan tidak ditemukan.");
            return;
        }

        if (!inputView.inputKonfirmasi("Yakin ingin menghapus " + layanan.getNamaLayanan() + "?")) {
            menuView.tampilkanPesan("Penghapusan dibatalkan.");
            return;
        }

        int jumlahReservasiTerhapus = layananDAO.hapus(layanan.getIdLayanan());
        menuView.tampilkanPesan("yeayy Data Layanan sudah dihapus!");

        if (jumlahReservasiTerhapus > 0) {
            menuView.tampilkanPesan(jumlahReservasiTerhapus + " data reservasi yang memakai layanan ini ikut dihapus.");
        }
    }
}