package com.pskwiercz.demo;

import com.pskwiercz.entity.Student;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class UpdateDemo {

    public static void main(String[] args) {

        SessionFactory factory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Student.class)
                .buildSessionFactory();

        try {

            Session session = factory.getCurrentSession();

            session.beginTransaction();

            Student student =  session.get(Student.class, 15);

            student.setFirstName("Bobby");

            session.createQuery("update Student s set email='foo@gmail.com' where s.firstName = 'Bobby'").executeUpdate();

            session.getTransaction().commit();

            session = factory.getCurrentSession();
            session.beginTransaction();

            List<Student> students =
                    session.createQuery("from Student s where s.id = 15").getResultList();

            for(Student st: students) {
                System.out.println(st.toString());
            }

            session.getTransaction().commit();

        } finally {
            factory.close();
        }
    }
}
