package sample;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.Callback;
import sample.Data.Data3;

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
    public TableView sendSQLselectAllRequest(String tableName){

        TableView tableView = new TableView();
        //tableView.setMaxWidth(650);
        tableView.setEditable(true);
        ObservableList<ObservableList> data = FXCollections.observableArrayList();

        try {
            System.out.println("посылаем запрос таблицы :" +tableName);
            rs = stmt.executeQuery("select * from "+tableName+";");

            //определяем размер таблицы
            int size = rs.getMetaData().getColumnCount();

            System.out.println("размер таблицы = "+size);

            System.out.println("Столбцы");
            /**
             * ********************************
             * TABLE COLUMN ADDED DYNAMICALLY *
             *********************************
             **/
            for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
                //We are using non property style for making dynamic table
                final int j = i;
                TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i + 1));

                col.setCellFactory(TextFieldTableCell.forTableColumn());
                col.setOnEditCommit(dataStringCellEditEvent -> {

                });

                col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> param) {
                        return new SimpleStringProperty(param.getValue().get(j).toString());
                    }
                });

                tableView.getColumns().addAll(col);
                System.out.println("Column [" + i + "] ");

            }
            //для кнопок редактирования и удаления
            TableColumn col = new TableColumn();
            col.setCellFactory(getButtonCell("изменить"));

            TableColumn col2 = new TableColumn();
            col2.setCellFactory(getButtonCell("удалить"));

            col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
                public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> param) {
                    return new SimpleStringProperty(param.getValue().get(size).toString());
                }
            });
            col2.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
                public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> param) {
                    return new SimpleStringProperty(param.getValue().get(size + 1).toString());
                }
            });

            tableView.getColumns().addAll(col,col2);

            System.out.println("Данные");
            /**
             * ******************************
             * Data added to ObservableList *
             *******************************
            */
            while (rs.next()) {
                //Iterate Row
                ObservableList<String> row = FXCollections.observableArrayList();
                for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                    //Iterate Column
                    row.add(rs.getString(i));
                }

                row.add("изменить");
                row.add("удалить");
                System.out.println("Row [1] added " + row);
                data.add(row);


            }
            tableView.setItems(data);





            return tableView;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return new TableView();
    }

    //засунуть кнопку в ячейку таблицы, не пытаться понять как это работает
    public Callback<TableColumn<ObservableList, String>, TableCell<ObservableList, String>> getButtonCell(String name){//
        Callback<TableColumn<ObservableList,String>, TableCell<ObservableList, String>> cellFactory
                = //
                new Callback<TableColumn<ObservableList, String>, TableCell<ObservableList, String>>() {
                    @Override
                    public TableCell call(final TableColumn<ObservableList, String> param) {
                        final TableCell<ObservableList, String> cell = new TableCell<ObservableList, String>() {

                            final Button btn = new Button(name);
                            @Override
                            public void updateItem(String item, boolean empty) {
                                super.updateItem(item, empty);
                                if (empty) {
                                    setGraphic(null);
                                    setText(null);
                                } else {

                                    if(name.equals("изменить")){
                                        btn.setId("UPDATE");
                                    }

                                    if(name.equals("удалить")){
                                        btn.setId("DELETE");
                                    }

                                    btn.setOnAction(event -> {

                                        String id = (String) getTableView().getItems().get(getIndex()).get(0);
                                        String requestType = btn.getId();
                                        String tableName = Controller.currentTable;

                                        String SQLRequest = requestType;
                                        if(requestType.equals("DELETE")){
                                            SQLRequest += " from "+tableName+" where id = "+id+";";
                                        }

                                        System.out.println("SQL request "+SQLRequest);
                                        SQLRequest(SQLRequest);



                                    });
                                    setGraphic(btn);
                                    setText(null);
                                }
                            }
                        };
                        return cell;
                    }
                };
        return cellFactory;
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
