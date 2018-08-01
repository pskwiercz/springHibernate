package com.pskwiercz.demo;

import com.pskwiercz.entity.Student;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class DeleteDemo {

    public static void main(String[] args) {

        SessionFactory factory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Student.class)
                .buildSessionFactory();

        try {

            Session session = factory.getCurrentSession();
            session.beginTransaction();

            Student student = session.get(Student.class, 18);

            session.delete(student);

            session.createQuery("delete from Student where id = 3").executeUpdate();

            session.getTransaction().commit();

            session = factory.getCurrentSession();
            session.beginTransaction();

            List<Student> students =
                    session.createQuery("from Student").getResultList();

            for (Student st : students) {
                System.out.println(st.toString());
            }

            session.getTransaction().commit();

        } finally {
            factory.close();
        }
    }
}