package com.pskwiercz.demo;

import com.pskwiercz.entity.Student;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;


public class QueryDemo {

    public static void main(String[] args) {

        SessionFactory factory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Student.class)
                .buildSessionFactory();

        Session session = factory.getCurrentSession();

        try {

            session.beginTransaction();

            List<Student> students = session.createQuery("from Student").getResultList();
            List<Student> students1 =
                    session.createQuery("from Student s where s.firstName = 'Adam'").getResultList();
            List<Student> students2 =
                    session.createQuery("from Student s where s.lastName = 'Adamski' OR s.firstName = 'Betty'")
                    .getResultList();

            List<Student> students3 =
                    session.createQuery("from Student s where s.lastName like '%amski'").getResultList();

            session.getTransaction().commit();

            printStudents(students);
            printStudents(students1);
            printStudents(students2);
            printStudents(students3);

        } finally {
            factory.close();
        }
    }

    private static void printStudents(List<Student> students) {
        for(Student st: students) {
            System.out.println(st.toString());
        }
        System.out.println("==================================================================");
    }


}
