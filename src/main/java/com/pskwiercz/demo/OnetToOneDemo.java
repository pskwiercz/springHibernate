package com.pskwiercz.demo;

import com.pskwiercz.entity.Instructor;
import com.pskwiercz.entity.InstructorDetail;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.Arrays;
import java.util.List;

public class OnetToOneDemo {

    public static void main(String[] args) {

        InstructorDetail instructorDetail1 =
                new InstructorDetail("https://www.youtube.com/channel/UCaA4Zh3F8KRbtB1GW4ZW2lw", "Java");
        InstructorDetail instructorDetail2 =
                new InstructorDetail("https://www.youtube.com/channel/UCuTaETsuCOkJ0H_GAztWt0Q", "Bike");

        Instructor instructor1 = new Instructor("Adam", "Adamski", "adam@gmail.com");
        Instructor instructor2 = new Instructor("Beata", "Babacka", "beata@gmail.com");
        instructor1.setInstructorDetail(instructorDetail1);
        instructor2.setInstructorDetail(instructorDetail2);

        SessionFactory factory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Instructor.class)
                .addAnnotatedClass(InstructorDetail.class)
                .buildSessionFactory();

        Session session = factory.getCurrentSession();

        try {

            session.beginTransaction();
            session.save(instructor1);
            session.save(instructor2);
            session.getTransaction().commit();

            // SQL JOIN
            session = factory.getCurrentSession();
            session.beginTransaction();
            List<Object[]> list =
                    session.createQuery("from Instructor i INNER JOIN i.instructorDetail instructor_detail")
                            .getResultList();
            session.getTransaction().commit();

            for(Object[] arr : list){
                System.out.println(Arrays.toString(arr));
            }

            // Cascade DELETE
            session = factory.getCurrentSession();
            session.beginTransaction();
            int id = 2;
            Instructor inst = session.get(Instructor.class, id);
            if (inst != null) {
                // Also delete associated details
                session.delete(inst);
            }
            session.getTransaction().commit();

        } finally {
            factory.close();
        }
    }
}
