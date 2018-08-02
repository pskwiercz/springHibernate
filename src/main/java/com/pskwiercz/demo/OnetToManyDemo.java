package com.pskwiercz.demo;

import com.pskwiercz.entity.Course;
import com.pskwiercz.entity.Instructor;
import com.pskwiercz.entity.InstructorDetail;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.Arrays;
import java.util.List;

public class OnetToManyDemo {

    public static void main(String[] args) {

        InstructorDetail instructorDetail1 =
                new InstructorDetail("https://www.youtube.com/channel/UCaA4Zh3F8KRbtB1GW4ZW2lw", "Video Games");

        Instructor instructor1 = new Instructor("Adam", "Adamski", "adam@gmail.com");
        instructor1.setInstructorDetail(instructorDetail1);

        Course course1 = new Course("Java for Dummies");
        Course course2 = new Course("Hibernate for Dummies");


        SessionFactory factory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Instructor.class)
                .addAnnotatedClass(InstructorDetail.class)
                .addAnnotatedClass(Course.class)
                .buildSessionFactory();

        Session session = factory.getCurrentSession();

        try {

            session.beginTransaction();

            // Create data
            session.save(instructor1);
            Instructor instructor = session.get(Instructor.class, 1);
            instructor.add(course1);
            instructor.add(course2);
            session.save(course1);
            session.save(course2);

            System.out.println("Instructor: " + instructor);
            System.out.println("Courses: " + instructor.getCourses());

            // Delete course ONLY - not instructor
            Course cor = session.get(Course.class, 11);
            session.delete(cor);

            session.getTransaction().commit();

            // Check that course was deleted
            session = factory.getCurrentSession();
            session.beginTransaction();

            instructor = session.get(Instructor.class, 1);
            System.out.println("Instructor1: " + instructor);
            System.out.println("Courses1: " + instructor.getCourses());

            session.getTransaction().commit();

         } catch(Exception ex) {
            ex.printStackTrace();
        }finally {
            session.close();
            factory.close();
        }
    }
}
