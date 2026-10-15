/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package orm;

/**
 *
 * @author HP VICTUS
 */
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {

    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            System.setProperty("net.bytebuddy.experimental", "true");
            sessionFactory = new Configuration()
                    .configure()
                    .addAnnotatedClass(PelangganORM.class)
                    .buildSessionFactory();
        }

        return sessionFactory;
    }
}