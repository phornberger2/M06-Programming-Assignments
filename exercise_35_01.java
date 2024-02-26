import java.sql.*;
import java.util.Scanner;
import java.sql.Connection;
import java.sql.Statement;
import java.lang.*;

public class exercise_35_01 {

    // JDBC driver name and database URL
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/num";

    // Database credentials
    static final String USER = "root";
    static final String PASS = "123456";

    public static void main(String[] args) {

        Connection conn = null;
        PreparedStatement ps = null;
        double num1, num2, num3;

        try {
            Class.forName("com.mysql.jdbc.Driver");

            // Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            String sql = "insert into Temp (num1, num2, num3) values (?, ?, ?)";
            ps = conn.prepareStatement(sql);

            final int batchSize = 1000;
            int count = 0;

            System.out.println("Started inserting numbers using Batch update");

            // Calculating the time before executing the statements
            long startTime = System.nanoTime();

            for (int i = 0; i < batchSize; i++) {
                // Generating the random numbers
                num1 = Math.random();
                num2 = Math.random();
                num3 = Math.random();

                // Setting the randomly generated numbers into statement for execution
                ps.setDouble(1, num1);
                ps.setDouble(2, num2);
                ps.setDouble(3, num3);
                ps.addBatch();
            }

            ps.executeBatch();

            // Insert remaining records
            long estimatedTime = System.nanoTime() - startTime;
            System.out.println("Batch update completed");
            System.out.println("The elapsed time is " + estimatedTime);

            System.out.println("Started inserting numbers using Non-Batch update");

            // Calculating the time before executing the statements
            long start = System.nanoTime();

            for (int index = 1; index < batchSize; index++) {
                // Generating the random numbers
                num1 = Math.random();
                num2 = Math.random();
                num3 = Math.random();

                // Setting the randomly generated numbers into statement for execution
                ps.setDouble(1, num1);
                ps.setDouble(2, num2);
                ps.setDouble(3, num3);
                ps.executeUpdate();
            }

            long end = System.nanoTime();
            System.out.println("Non-Batch update completed");
            System.out.println("Time elapsed is " + (end - start));

            ps.close();
            conn.close();

        } catch (Exception e) {
            // Handle errors for Class.forName
            e.printStackTrace();
        }

        System.out.println("All the insertions are done..Goodbye!");
    }
}
