/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package orm;

/**
 *
 * @author HP VICTUS
 */
import java.util.List;
import org.hibernate.Session;
import view.MenuView;

public class PelangganORMController {

    private MenuView menuView;

    public PelangganORMController(MenuView menuView) {
        this.menuView = menuView;
    }

    public void tampilkan() {
        menuView.tampilkanJudul("Daftar Pelanggan (ORM Hibernate)");

        Session session = HibernateUtil.getSessionFactory().openSession();
        List<PelangganORM> daftarPelanggan = session.createQuery("FROM PelangganORM ORDER BY idPelanggan", PelangganORM.class).list();
        session.close();

        if (daftarPelanggan.isEmpty()) {
            menuView.tampilkanPesan("Belum ada data pelanggan.");
            return;
        }

        for (int i = 0; i < daftarPelanggan.size(); i++) {
            menuView.tampilkanData(daftarPelanggan.get(i).getInfo());
        }
    }
}