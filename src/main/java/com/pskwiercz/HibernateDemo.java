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
            Student student = new Student("Adam", "Badamski", "pf@gmail.com");

            session.beginTransaction();

            session.save(student);

            Student studentDB =  session.get(Student.class, student.getId());

            session.getTransaction().commit();

            System.out.println(studentDB.getLastName());

        } finally {
            factory.close();
        }
    }
}
