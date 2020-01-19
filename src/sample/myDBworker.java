package sample;

import javafx.scene.layout.Pane;
import sample.Data.Data;
import sample.Data.RawData;
import sample.Data.goodsType;

import java.sql.*;

public class myDBworker {
    private String dbServerUrl = "jdbc:mysql://localhost:3306/market?serverTimezone=UTC";
    private String userName = "root";
    private String password = "123456";

    public boolean isConnected = false;
    public Connection connection;
    public Statement stmt;
    public ResultSet rs;

    public void connect() throws SQLException{
        connection = DriverManager.getConnection(dbServerUrl,userName,password);
        stmt = connection.createStatement();
    }

    public void disConnect(){
        if(connection != null) {
            try {
                connection.close();
                stmt.close();
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    public RawData sendSQLselectAllRequest(String sql){
        try {

            System.out.println("запрос : "+sql);
            rs = stmt.executeQuery(sql);
            int columnCount = rs.getMetaData().getColumnCount();
            System.out.println("column count = "+columnCount);


            RawData data = null;
            data = new RawData(Controller.currentTable,columnCount);


            for(int i = 1; i < columnCount + 1; i++) {
                data.addColumnName(rs.getMetaData().getColumnName(i));
                //System.out.print(rs.getMetaData().getColumnName(i)+" ");
            }
            //System.out.println();

            while (rs.next()) {
                for(int i = 1; i < columnCount + 1; i++) {
                    data.addData(rs.getString(i));
                    //System.out.print(rs.getString(i)+" ");
                }
                //System.out.println();
            }

            System.out.println("результат запроса");
            data.print();
            System.out.println("-----------------");


            return data;

        } catch (SQLException e) {
            e.printStackTrace();
            return new RawData(e.toString(),1);
        }

    }

    public String SQLRequest(String request){
        System.out.println("Запрос : "+request);
        try { stmt.executeUpdate(request);
        } catch (SQLException e) {
            System.out.println("ошибка = "+e.toString());
            e.printStackTrace();
            return "выполнено с ошибкой: "+e.toString();
        }
        return "выполнено успешно";
    }
    public ResultSet SQLRequestSelect(String request){
        System.out.println("Запрос : "+request);
        try {
            rs = stmt.executeQuery(request);
            return rs;
        } catch (SQLException e) {
            System.out.println("ошибка = "+e.toString());
            e.printStackTrace();
            return null;
        }
    }

    public int getMaxId(){
        try {
            rs = stmt.executeQuery("SELECT * FROM "+Controller.currentTable+" ORDER BY id DESC LIMIT 0, 1");

            System.out.println(">> "+rs.getMetaData().getColumnCount());
            while (rs.next()){
                int max = rs.getInt(1);
                System.out.println("Max "+max);

                return max + 1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  -1;
    }
}
