package com.pskwiercz;

import java.sql.Connection;
import java.sql.DriverManager;

public class TestJDBC {

    public static void main(String[] args) {
        String jdbcUrl = "jdbc:mysql://localhost:3306/hb_student_tracker?useSSL=false";
        String user = "springstudent";
        String password = "springstudent";

        try {
            System.out.println("Connecting to DB");

            Connection myConn = DriverManager.getConnection(jdbcUrl, user, password);

            System.out.println("Connection successful");


        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
