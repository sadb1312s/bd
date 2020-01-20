package sample.Data;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.IntegerStringConverter;

import java.util.ArrayList;

public class Producer extends Data {
    String producer;
    String country;


    public Producer(ArrayList<String> data) {
        super();
            id = Integer.valueOf(data.get(0));
            producer = data.get(1);
            country = data.get(2);

    }

    public Producer() {
        super();
        tableName = "producer";
    }

    //Override methods
    @Override
    public ObservableList getDataset(RawData data) {
        ObservableList<Producer> tableData = FXCollections.observableArrayList();
        for(int i = 0; i < data.rows.size(); i++){
            tableData.add(new Producer(data.getRow(i)));
        }

        return tableData;
    }

    @Override
    public ObservableList getNullDataset(RawData data) {
        ArrayList<String> d = new ArrayList<>(data.size);
        for(int i = 0; i < data.size;i++)
            d.add("");

        ObservableList<Producer> tableData = FXCollections.observableArrayList();
        tableData.add(new Producer());

        return tableData;
    }

    @Override
    public String getAllData() {
        System.out.println("!Dsad");
        return "'"+id+"','"+producer+"','"+country+"'";
    }

    @Override
    public void addCustomData(String columnType, String data) {
        System.out.println("type = "+columnType);

        switch (columnType){
            case "id": {

                id = Integer.valueOf(data);
                break;
            }
            case "producer": {
                producer = data;
                break;
            }
            case "country": {
                country = data;
                break;
            }
        }
    }

    @Override
    public void setNull() {
        super.setNull();
        producer = "";
        country = "";
    }

    @Override
    public Producer clone() throws CloneNotSupportedException {
        Producer p = new Producer();
        p.setId(id);
        p.setProducer(producer);
        p.setCountry(country);

        return p;
    }

    @Override
    public String toString() {
        return producer+" "+country;
    }

    //javaFX
    @Override
    public TableView formTable(RawData data) {
        ObservableList<goodsType> tableData = getDataset(data);

        TableView<goodsType> table = new TableView<goodsType>();
        table.setEditable(true);


        mainTable = table;

        //Добавляем столбцы
        //столбец ID
        TableColumn col1 = new TableColumn("id");
        col1.setCellValueFactory(new PropertyValueFactory<Producer, Integer>("id"));
        col1.setId("id");
        col1.setVisible(false);

        //столбец производитель(название)
        TableColumn col2 = new TableColumn("производитель");
        col2.setCellValueFactory(new PropertyValueFactory<Producer, String>("producer"));
        col2.setId("producer");
        //обработчик изменения
        col2.setCellFactory(TextFieldTableCell.forTableColumn());
        col2.setOnEditCommit((EventHandler<TableColumn.CellEditEvent<Producer, String>>) t -> {

                    Producer inCell =  t.getTableView().getItems().get(t.getTablePosition().getRow());
                    String columnName = t.getTableColumn().getId();

                    System.out.println("изменяем стобец "+columnName+" ,новые данные "+t.getNewValue());
                    System.out.println("текущие данные");
                    System.out.println(inCell.getAllData());
                    inCell.producer = t.getNewValue();
                    System.out.println("после изменения");
                    System.out.println(inCell.getAllData());

                    String res = dbworker.SQLRequest("UPDATE "+tableName+" set " + columnName + "='" + t.getNewValue() + "' where id = " + inCell.getId() + ";");

                    log.setText("");
                    log.setText(res);
                }
        );

        //столбец страна
        TableColumn col3 = new TableColumn("страна");
        col3.setCellValueFactory(new PropertyValueFactory<goodsType, Integer>("country"));
        col3.setId("country");
        //обработчик изменения
        col3.setCellFactory(TextFieldTableCell.forTableColumn());
        col3.setOnEditCommit((EventHandler<TableColumn.CellEditEvent<Producer, String>>) t -> {

                    Producer inCell =  t.getTableView().getItems().get(t.getTablePosition().getRow());
                    String columnName = t.getTableColumn().getId();

                    System.out.println("изменяем стобец "+columnName+" ,новые данные "+t.getNewValue());
                    System.out.println("текущие данные");
                    System.out.println(inCell.getAllData());
                    inCell.country = t.getNewValue();
                    System.out.println("после изменения");
                    System.out.println(inCell.getAllData());

                    String res = dbworker.SQLRequest("UPDATE "+tableName+" set " + columnName + "='" + t.getNewValue() + "' where id = " + inCell.getId() + ";");

                    log.setText("");
                    log.setText(res);
                }
        );


        //столбец с кнопкой
        TableColumn buttonCol = new TableColumn();
        buttonCol.setCellFactory(getButtonCell("удалить"));

        table.getColumns().addAll(col1,col2,col3,buttonCol);
        table.setItems(tableData);

        return table;
    }

    @Override
    public TableView formAddTable(RawData data) {
        ObservableList<Producer> tableData = getNullDataset(data);

        TableView<Producer> table = new TableView<Producer>();
        table.setEditable(true);


        addTable = table;

        //Добавляем столбцы
        //столбец ID
        TableColumn col1 = new TableColumn("id");
        col1.setCellValueFactory(new PropertyValueFactory<goodsType, Integer>("id"));
        col1.setId("id");
        col1.setVisible(false);

        //столбец производитель
        TableColumn col2 = new TableColumn("производитель");
        col2.setCellValueFactory(new PropertyValueFactory<Producer, String>("producer"));
        col2.setId("producer");
        //обработчик изменения
        col2.setCellFactory(TextFieldTableCell.forTableColumn());
        col2.setOnEditCommit((EventHandler<TableColumn.CellEditEvent<Producer, String>>) t -> {

                    Producer inCell =  t.getTableView().getItems().get(t.getTablePosition().getRow());
                    String columnName = t.getTableColumn().getId();

                    System.out.println("изменяем стобец "+columnName+" ,новые данные "+t.getNewValue());
                    System.out.println("текущие данные");
                    System.out.println(inCell.getAllData());
                    inCell.producer = t.getNewValue();
                    System.out.println("после изменения");
                    System.out.println(inCell.getAllData());
                }
        );

        //столбец страна
        TableColumn col3 = new TableColumn("страна");
        col3.setCellValueFactory(new PropertyValueFactory<goodsType, Integer>("country"));
        col3.setId("country");
        //обработчик изменения
        col3.setCellFactory(TextFieldTableCell.forTableColumn());
        col3.setOnEditCommit((EventHandler<TableColumn.CellEditEvent<Producer, Integer>>) t -> {

                    Producer inCell =  t.getTableView().getItems().get(t.getTablePosition().getRow());
                    String columnName = t.getTableColumn().getId();

                    System.out.println("изменяем стобец "+columnName+" ,новые данные "+t.getNewValue());
                    System.out.println("текущие данные");
                    System.out.println(inCell.getAllData());
                    inCell.country = String.valueOf(t.getNewValue());
                    System.out.println("после изменения");
                    System.out.println(inCell.getAllData());
                }
        );


        //столбец с кнопкой
        TableColumn buttonCol = new TableColumn();
        buttonCol.setCellFactory(getButtonCell("добавить"));

        table.getColumns().addAll(col1,col2,col3,buttonCol);
        table.setItems(tableData);

        return table;
    }

    //getter and setter
    public Integer getId() {
        return id;
    }

    public void setId(String id2) {
        id = Integer.valueOf(id2);
    }

    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}

