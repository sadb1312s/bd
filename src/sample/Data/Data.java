package sample.Data;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.util.Callback;
import sample.Controller;
import sample.myDBworker;

import java.util.ArrayList;

public class Data {
    public TableView mainTable;
    public TableView addTable;
    public String tableName;
    public TextArea log;
    public myDBworker dbworker;
    public static String request = "";
    ArrayList<String> columnName;

    public Integer id;
    public Data(){
        id = 0;
    }

    public String perevod(String d){
        switch (d){
            case "goods_type" :
                return "тип товара";
            case "discount" :
                return "скидка";
            case "name" :
                return "имя товара";
            case "price" :
                return "Цена";
            case "description" :
                return "описание";
            case "balance" :
                return "остаток";
            case "producer" :
                return "производитель";
            case "country" :
                return "страна";

            case "fio" :
                return "ФИО";
            case "phone" :
                return "телефон";
            case "adress" :
                return "адрес";
            case "salary" :
                return "зарплата";

            case "fioBuyer":
                return "фиоКлиента";
            case "phoneBuyer":
                return "телефонКлиента";
            case "adressBuyer":
                return "адресКлиента";
            case "orderCount":
                return "всегаЗаказов";
            case "orderSum":
                return "суммаЗаказов";


            case "id_buyer" :
                return "покупатель";
            case "id_employee" :
                return "сотрудник";
            case "dataStart" :
                return "дата начала";
            case "dataEnd" :
                return "дата завершения";
            case "finalPrice" :
                return "итог руб.";
            case "id_delivery" :
                return "доставка";



            default:
                return d;


        }
    }

    //Methods for override
    public ObservableList<Data> getDataset(RawData data){
        return FXCollections.observableArrayList();
    }
    public ObservableList<Data> getNullDataset(RawData data){
        return FXCollections.observableArrayList();
    }
    public String getAllData(){
        return "";
    }
    public void addCustomData(String columnType,String data){}
    public TableView formTable(RawData data){
        return new TableView();
    }
    public TableView formAddTable(RawData data){
        return new TableView();
    }
    public void setNull(){
        id = 0;
    }

    @Override
    public Data clone() throws CloneNotSupportedException {
        return new Data();
    }

    //засунуть кнопку в ячейку таблицы, не пытаться понять как это работает
    public Callback<TableColumn<Data, String>, TableCell<Data, String>> getButtonCell(String name){//
        Callback<TableColumn<Data,String>, TableCell<Data, String>> cellFactory
                = //
                new Callback<TableColumn<Data, String>, TableCell<Data, String>>() {
                    @Override
                    public TableCell call(final TableColumn<Data, String> param) {
                        final TableCell<Data, String> cell = new TableCell<Data, String>() {

                            final Button btn = new Button(name);
                            @Override
                            public void updateItem(String item, boolean empty) {
                                super.updateItem(item, empty);
                                if (empty) {
                                    setGraphic(null);
                                    setText(null);
                                } else {

                                    if(name.equals("удалить")){
                                        btn.setId("DELETE");
                                    }

                                    if(name.equals("добавить")){
                                        btn.setId("INSERT");
                                    }

                                    btn.setOnAction(event -> {
                                        //System.out.println("нажали кнопку "+btn.getId());
                                        //System.out.println("->"+getTableView().getItems().get(0).getAllData());

                                        Data cellData;
                                        if(btn.getId().equals("INSERT")){

                                            System.out.println("Вставить новую запись в таблицу");
                                            cellData = getTableView().getItems().get(0);

                                            int maxIdId = dbworker.getMaxId();
                                            cellData.setId(maxIdId);


                                            String dataS = getTableView().getItems().get(0).getAllData();
                                            System.out.println(dataS);

                                            try {
                                                mainTable.getItems().addAll(cellData.clone());
                                            } catch (CloneNotSupportedException e) {
                                                e.printStackTrace();
                                            }


                                            String res = dbworker.SQLRequest("insert into "+tableName+" values("+dataS+");");
                                            log.setText(res);


                                            cellData.setNull();
                                            mainTable.refresh();
                                            addTable.refresh();


                                        }

                                        if(btn.getId().equals("DELETE")){
                                            cellData = getTableView().getItems().get(getIndex());
                                            System.out.println("Удаляем данные из таблицы");
                                            int id = getTableView().getItems().get(getIndex()).getId();
                                            System.out.println("удаляем id "+id);
                                            String res = dbworker.SQLRequest("delete from "+tableName+" where id = "+id+";");
                                            getTableView().getItems().remove(cellData);
                                            cellData.setId(0);

                                            //currentUpdate();
                                            log.setText(res);
                                        }

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

    //getter and setter
    public Integer getId() { return id; }
    public void setId(Integer id) {
        this.id = id;
    }
}


