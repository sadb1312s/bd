package sample;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class myDBworker {
    private String dbServerUrl = "jdbc:mysql://localhost:3306/market?serverTimezone=UTC";
    private String userName = "root";
    private String password = "123456";

    boolean isConnected = false;
    Connection connection;
    Statement stmt;
    ResultSet rs;

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
    public ArrayList<ArrayList<String>> sendSQLselectAllRequest(String tableName){
        ArrayList<ArrayList<String>> data = new ArrayList<>();
        try {
            System.out.println("посылаем запрос таблицы :" +tableName);
            rs = stmt.executeQuery("select * from "+tableName+" where id = 1;");

            //определяем размер таблицы
            int size = 0;
            while (rs.next()) {
                int i = 1;
                try {

                    for(;;i++){
                        rs.getString(i);
                        System.out.println(">> "+rs.getString(i));
                    }

                }catch (Exception e){
                    //e.printStackTrace();
                    size = i - 1;

                }
            }

            System.out.println("размер таблицы = "+size);

            //названия столбцов
            data.add(new ArrayList<>());
            rs = stmt.executeQuery("desc "+tableName+";");
            while (rs.next()) {
                //System.out.print(rs.getString(1)+" ");
                data.get(0).add(rs.getString(1));
            }
            //System.out.println();

            //данные
            rs = stmt.executeQuery("select * from "+tableName);
            while (rs.next()) {
                data.add(new ArrayList<>());
                for(int i = 0; i < size; i++){
                    //System.out.print(rs.getString(i+1)+" ");
                    data.get(data.size() - 1).add(rs.getString(i+1));
                }
                //System.out.println();
            }


            /*for(ArrayList<String> e : data) {
                for (String s : e)
                    System.out.print(s + " ");
                System.out.println();
            }
            System.out.println("----");*/

           

        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return data;
    }
    public void SQLRequest(String request){
        System.out.println("Запрос : "+request);
        try {
            stmt.executeUpdate(request);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
