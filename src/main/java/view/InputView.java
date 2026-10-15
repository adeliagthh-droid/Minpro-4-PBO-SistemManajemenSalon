/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

/**
 *
 * @author HP VICTUS
 */
import java.util.Scanner;

public class InputView {

    private Scanner input;

    public InputView() {
        input = new Scanner(System.in);
    }

    public int inputAngka(String label) {
        while (true) {
            System.out.print(label);
            String teks = input.nextLine().trim();

            try {
                int angka = Integer.parseInt(teks);

                if (angka > 0) {
                    return angka;
                }

                System.out.println("Input harus lebih dari 0.");
            } catch (NumberFormatException e) {
                System.out.println("Input harus berupa angka.");
            }
        }
    }

    public int inputAngka(String label, int min, int max) {
        while (true) {
            int angka = inputAngka(label);

            if (angka >= min && angka <= max) {
                return angka;
            }

            System.out.println("Input harus di antara " + min + " sampai " + max + ".");
        }
    }

    public String inputNama(String label) {
        while (true) {
            System.out.print(label);
            String teks = input.nextLine().trim();

            if (teks.length() < 2) {
                System.out.println("Input minimal 2 huruf.");
            } else if (!hanyaHuruf(teks)) {
                System.out.println("Input hanya boleh berisi huruf dan spasi.");
            } else {
                return rapikanHuruf(teks);
            }
        }
    }

    public String inputNoTelepon(String label) {
        while (true) {
            System.out.print(label);
            String teks = input.nextLine().trim();

            if (!hanyaAngka(teks)) {
                System.out.println("No telepon hanya boleh berisi angka.");
            } else if (!teks.startsWith("08")) {
                System.out.println("No telepon harus diawali 08.");
            } else if (teks.length() < 10 || teks.length() > 13) {
                System.out.println("No telepon harus 10 sampai 13 digit.");
            } else {
                return teks;
            }
        }
    }

    public String inputTanggal(String label) {
        while (true) {
            System.out.print(label);
            String teks = input.nextLine().trim();

            if (tanggalValid(teks)) {
                return teks;
            }

            System.out.println("Format tanggal harus dd-mm-yyyy, contoh 25-10-2026.");
        }
    }

    public boolean inputKonfirmasi(String label) {
        while (true) {
            System.out.print(label + " (y/n): ");
            String teks = input.nextLine().trim().toLowerCase();

            if (teks.equals("y")) {
                return true;
            } else if (teks.equals("n")) {
                return false;
            }

            System.out.println("Ketik y atau n.");
        }
    }

    private boolean hanyaHuruf(String teks) {
        for (int i = 0; i < teks.length(); i++) {
            char karakter = teks.charAt(i);

            if (!Character.isLetter(karakter) && karakter != ' ') {
                return false;
            }
        }

        return true;
    }

    private boolean hanyaAngka(String teks) {
        if (teks.isEmpty()) {
            return false;
        }

        for (int i = 0; i < teks.length(); i++) {
            if (!Character.isDigit(teks.charAt(i))) {
                return false;
            }
        }

        return true;
    }

    private String rapikanHuruf(String teks) {
        String[] kata = teks.toLowerCase().split("\\s+");
        String hasil = "";

        for (int i = 0; i < kata.length; i++) {
            hasil += kata[i].substring(0, 1).toUpperCase() + kata[i].substring(1);

            if (i < kata.length - 1) {
                hasil += " ";
            }
        }

        return hasil;
    }

    private boolean tanggalValid(String teks) {
        String[] bagian = teks.split("-");

        if (bagian.length != 3) {
            return false;
        }

        if (bagian[0].length() != 2 || bagian[1].length() != 2 || bagian[2].length() != 4) {
            return false;
        }

        if (!hanyaAngka(bagian[0]) || !hanyaAngka(bagian[1]) || !hanyaAngka(bagian[2])) {
            return false;
        }

        int hari = Integer.parseInt(bagian[0]);
        int bulan = Integer.parseInt(bagian[1]);
        int tahun = Integer.parseInt(bagian[2]);

        return hari >= 1 && hari <= 31 && bulan >= 1 && bulan <= 12 && tahun >= 2026;
    }
}