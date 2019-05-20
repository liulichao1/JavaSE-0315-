package day09;

import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.logging.Logger;

class Server {

    private static final String URL = "jdbc:mysql:///?user=root&password=chao0309@&useSSL=false";
    private static Connection connection;
    private PreparedStatement preparedStatement;
    private static SimpleDateFormat simpleDateFormat;

    Server() {
        getConnection();
        simpleDateFormat = new SimpleDateFormat("[YYYY-MM-DD HH:mm:ss] ");
    }

    private Connection getConnection() {//单例模式
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

    String update(String sql) {
        String output;
        try {
            long start = System.currentTimeMillis();
            preparedStatement = connection.prepareStatement(sql);
            int rowAffected = preparedStatement.executeUpdate();
            long end = System.currentTimeMillis();

            output = simpleDateFormat.format(new Date(end));
            output += rowAffected + " row affected in " + (end - start) + " ms";

        } catch (SQLException e) {
            output = "SQL ERROR:" + e.getMessage();
        }
        return output;
    }

    String query(String sql) {
        String output;
        try {
            long start = System.currentTimeMillis();
            preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            long end = System.currentTimeMillis();
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();//元数据
            int columnCount = resultSetMetaData.getColumnCount();

            String[] columnNames = new String[columnCount];
            for (int i = 0; i < columnCount; i++) {
                columnNames[i] = resultSetMetaData.getColumnLabel(i + 1);
            }
            int rowCount = 0;//记录的条数
            if (resultSet.last()) {//移动到最后一条数据
                rowCount = resultSet.getRow();//获取记录的条数
                resultSet.beforeFirst();//移动到第一条之前
            }

            String[][] data = new String[rowCount][columnCount];

            for (int row = 0; row < rowCount; row++) {
                resultSet.next();
                for (int col = 0; col < columnCount; col++) {
                    data[row][col] = resultSet.getString(col + 1);
                }
            }
            Client1.defaultTableModel = new DefaultTableModel(data, columnNames);

            output = simpleDateFormat.format(new Date(end));
            output += (rowCount + " rows retrieved in " + (end - start) + " ms");
        } catch (SQLException e) {
            output = "SQL ERROR: " + e.getMessage();
        }
        return output;
    }

    String dispatch(String sql) {
        if (sql == null) {
            return null;
        } else {
            if (sql.toLowerCase().trim().startsWith("select")) {
                return query(sql);
            } else {
                return update(sql);
            }
        }
    }
}


