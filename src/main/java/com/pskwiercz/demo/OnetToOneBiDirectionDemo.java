package com.pskwiercz.demo;

import com.pskwiercz.entity.Instructor;
import com.pskwiercz.entity.InstructorDetail;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class OnetToOneBiDirectionDemo {

    public static void main(String[] args) {

        SessionFactory factory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Instructor.class)
                .addAnnotatedClass(InstructorDetail.class)
                .buildSessionFactory();

        Session session = factory.getCurrentSession();

        try {

            session.beginTransaction();

            int id = 1;
            InstructorDetail instDetail = session.get(InstructorDetail.class, id);
            System.out.println("Instructor detail: " + instDetail);
            System.out.println("Associated instructor: " + instDetail.getInstructor());

            session.getTransaction().commit();

        } finally {
            factory.close();
        }
    }
}
