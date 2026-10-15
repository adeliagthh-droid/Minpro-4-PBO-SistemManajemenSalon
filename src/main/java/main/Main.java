/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

/**
 *
 * @author HP VICTUS
 */
import controller.KelolaData;
import controller.LayananController;
import controller.PelangganController;
import controller.ReservasiController;
import orm.PelangganORMController;
import view.InputView;
import view.MenuView;

public class Main {

    public static void main(String[] args) {
        MenuView menuView = new MenuView();
        InputView inputView = new InputView();

        KelolaData pelangganController = new PelangganController(menuView, inputView);
        KelolaData layananController = new LayananController(menuView, inputView);
        KelolaData reservasiController = new ReservasiController(menuView, inputView, pelangganController, layananController);
        PelangganORMController pelangganORMController = new PelangganORMController(menuView);

        int pilihan;

        do {
            menuView.tampilkanMenuUtama();
            pilihan = inputView.inputAngka("Pilih menu: ", 1, 5);

            switch (pilihan) {
                case 1:
                    pelangganController.jalankanMenu();
                    break;
                case 2:
                    layananController.jalankanMenu();
                    break;
                case 3:
                    reservasiController.jalankanMenu();
                    break;
                case 4:
                    pelangganORMController.tampilkan();
                    break;
                case 5:
                    menuView.tampilkanPesan("Program selesai. Terima kasih!");
                    break;
            }
        } while (pilihan != 5);
    }
}