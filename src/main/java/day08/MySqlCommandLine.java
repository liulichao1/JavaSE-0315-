package day08;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.Scanner;
import java.util.logging.Logger;

public class MySqlCommandLine {

    private static final String URL = "jdbc:mysql:///?user=root&password=chao0309@&useSSL=false";
    private static Connection connection;
    private PreparedStatement preparedStatement;
    private static Scanner scanner;
    private static SimpleDateFormat simpleDateFormat;

    public MySqlCommandLine() {
        scanner = new Scanner(System.in);
        getConnection();
        simpleDateFormat = new SimpleDateFormat("[YYYY-MM-DD HH:mm:ss] ");
    }

    public Connection getConnection() {//单例模式
        if (connection == null) {
            try {
                new Driver() {
                    @Override
                    public Connection connect(String url, Properties info) throws SQLException {
                        return null;
                    }

                    @Override
                    public boolean acceptsURL(String url) throws SQLException {
                        return false;
                    }

                    @Override
                    public DriverPropertyInfo[] getPropertyInfo(String url, Properties info) throws SQLException {
                        return new DriverPropertyInfo[0];
                    }

                    @Override
                    public int getMajorVersion() {
                        return 0;
                    }

                    @Override
                    public int getMinorVersion() {
                        return 0;
                    }

                    @Override
                    public boolean jdbcCompliant() {
                        return false;
                    }

                    @Override
                    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
                        return null;
                    }
                };
                connection = (Connection) DriverManager.getConnection(URL);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }

    public void update(String sql) {
        try {
            long start = System.currentTimeMillis();
            preparedStatement = connection.prepareStatement(sql);
            int rowAffected = preparedStatement.executeUpdate();
            long end = System.currentTimeMillis();

            System.out.print(simpleDateFormat.format(new Date(end)));
            System.out.println(rowAffected + " row affected in " + (end - start) + " ms");
        } catch (SQLException e) {
            System.out.println("SQL ERROR: "+e.getMessage());
        }
    }

    public void query(String sql) {
        try {
            preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();//元数据
            int columnCount = resultSetMetaData.getColumnCount();
            for (int i = 0; i < columnCount; i++) {
                System.out.print(resultSetMetaData.getColumnLabel(i + 1) + " ");
            }
            System.out.println("\n--------------------");
//            System.out.println(resultSetMetaData.getColumnCount());
//            System.out.println(resultSetMetaData.getColumnClassName(1));
//            System.out.println(resultSetMetaData.getColumnDisplaySize(1));
//            System.out.println(resultSetMetaData.getColumnLabel(1));
//            System.out.println(resultSetMetaData.getColumnName(1));
//            System.out.println(resultSetMetaData.getColumnTypeName(1));
//            System.out.println(resultSetMetaData.getColumnType(1));
            while (resultSet.next()) {
                for (int i = 0; i < columnCount; i++) {
                    System.out.println(resultSet.getString(i + 1) + " ");
                }
                System.out.println();
            }
        } catch (SQLException e) {
            System.out.println("SQL ERROR: "+e.getMessage());
            e.printStackTrace();
        }
    }

    public void dispatch(String sql) {
        if (sql.toLowerCase().trim().startsWith("select")) {
            query(sql);
        } else {
            update(sql);
        }
    }

    public String getSQL() {
        System.out.print("mysql> ");
        String line = scanner.nextLine();
        StringBuilder sql =new StringBuilder(line);
        while (!line.endsWith(";")) {
            System.out.print("    ->");
            line = scanner.nextLine();
            sql.append(line);
        }
        return sql.toString();
    }

    public static void main(String[] args) {
        MySqlCommandLine mySqlCommandLine = new MySqlCommandLine();
        String sql = mySqlCommandLine.getSQL();
        System.out.println(sql);
        while (!sql.equalsIgnoreCase("quit")) {
            mySqlCommandLine.dispatch(sql);
            sql = mySqlCommandLine.getSQL();
        }
         /*

        mySqlCommandLine.getConnection();

        // 用户在登录表单填写的值

        String username = "' or 'a'='a";   // SQL Injection 注入

        String password = "' or 'a'='a";

        String sql1 = "select * from db_test.user where username = ? and password = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(sql1);

        preparedStatement.setString(1, username);

        preparedStatement.setString(2, password);

        ResultSet resultSet1 = preparedStatement.executeQuery();

        System.out.println(resultSet1.next()); // true 用户可以登录

        String sql2 = "select * from db_test.user where username = '" + username + "' and password = '" + password + "'";

        System.out.println(sql2);

        Statement statement = connection.createStatement();

        ResultSet resultSet2 = statement.executeQuery(sql2);

        System.out.println(resultSet2.next());

        */
    }
}
