package sample.Data;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;
import sample.Controller;

import java.util.ArrayList;

public class Data {
    public static String request = "";
    ArrayList<String> columnName;
    String tableName;



    String id;
    Data(String tableName){
        this.tableName = tableName;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
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



            default:
                return d;


        }
    }





    //
    public ObservableList<Data> getDataset(RawData data){
        return FXCollections.observableArrayList();
    }
    public ObservableList<Data> getNullDataset(RawData data){
        return FXCollections.observableArrayList();
    }
    public String getAllData(){
        return "";
    }
    public void addCustomData(String columnType,String data){

    }
}


