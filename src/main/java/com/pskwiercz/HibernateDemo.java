package com.pskwiercz;

import com.pskwiercz.entity.Student;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;


public class HibernateDemo {

    public static void main(String[] args) {

        SessionFactory factory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Student.class)
                .buildSessionFactory();

        Session session = factory.getCurrentSession();

        try {
            Student student = new Student("Paul", "Fick", "pf@gmail.com");

            session.beginTransaction();

            session.save(student);

            session.getTransaction().commit();

        } finally {
            factory.close();
        }
    }
}
