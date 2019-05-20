package day08;

import java.sql.*;

import static sun.plugin.javascript.navig.JSType.URL;

// JDBC - Java DataBase Connectivity

public class JDBC {

    //    private static final String URL = "jdbc:mysql://127.0.0.1:3306";
//
//    private static final String User = "root";
//
//    private static final String PASSWORD = "chao0309@";
//
//    public static void main(String[] args) throws ClassNotFoundException, SQLException {
//
//        // 1. Driver
//
////        new Driver();
//
//        Class.forName("com.mysql.cj.jdbc.Driver");
//
//        // 2. Connection
//
//        Connection connection = DriverManager.getConnection(URL, User, PASSWORD);
//
//        // 3. PreparedStatement
//
//        String sql = "insert into db_test.user value (66, 'root', '123456')";
//
//        PreparedStatement preparedStatement = connection.prepareStatement(sql);
//
////        preparedStatement.setString(1, "Tom");
////
////        preparedStatement.setString(2, "123");
//
//        // 4.1 p.executeUpdate(); // DML
//
//        preparedStatement.executeUpdate();
//
//        // 4.2 p.executeQuery(); // DQL
//
//        // 5. *.close();
//
//        preparedStatement.close();
//
//        connection.close();
//
//        System.out.println("done.");
//
//    }
    static Connection connection = null;
    static java.sql.Statement Statement = null;

    public static void main(String[] args) {
        initDB();

    }

    public static void initDB() {
        final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
        final String USER = "root";
        final String PASSWORD = "chao0309@";
        final String URL = "jdbc:mysql://localhost:3306/?characterEncoding=UTF-8&useSSL=false";

        try {
//            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
//            Statement = connection.createStatement();
            PreparedStatement preparedStatement = connection.prepareStatement("show databases");
            ResultSet resultSet = preparedStatement.executeQuery();
            PreparedStatement preparedStatement1 = connection.prepareStatement("drop database ggggg");
            preparedStatement1.execute();
            while (resultSet.next()) {
                System.out.println(resultSet.getString(1));
            }
            System.out.println("Initial success");
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }
}