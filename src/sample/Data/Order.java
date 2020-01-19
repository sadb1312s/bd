package sample.Data;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;

public class Order extends Data{
    public goodsInOrder ordered;
    public String id,id_buyer,id_employee,dataStart,dataEnd,finalPrice,id_delivery;
    public ArrayList<String> fieldName;



    public Order(){
        super("");

        id_delivery = "1";
        finalPrice = "0";
        id_delivery = "1";
        dataEnd = "12.12.2100";

        ordered = new goodsInOrder();
        fieldName = new ArrayList<>();

        fieldName.add("id");
        fieldName.add("id_buyer");
        fieldName.add("id_employee");
        fieldName.add("dataStart");
        fieldName.add("dataEnd");
        fieldName.add("finalPrice");
        fieldName.add("id_delivery");

    }


    @Override
    public ObservableList getNullDataset(RawData data) {
        ArrayList<String> d = new ArrayList<>(data.size);
        for (int i = 0; i < data.size; i++)
            d.add("");

        ObservableList<Order> tableData = FXCollections.observableArrayList();
        tableData.add(new Order());


        return tableData;
    }

    @Override
    public String getAllData() {
        return "'"+id+"','"+id_buyer+"','"+id_employee+"','"+dataStart+"','"+dataEnd+"','"+finalPrice+"','"+id_delivery+"'";
    }

    @Override
    public void addCustomData(String columnType, String data) {
        switch (columnType){
            case "id_buyer": {
                setId_buyer(data);
                break;
            }
            case "id_employee": {
                setId_employee(data);
                break;
            }
            case "dataStart": {
                setDataStart(data);
                break;
            }
            case "dataEnd": {
                setDataEnd(data);
                break;
            }
            case "finalPrice": {
                setFinalPrice(data);
                break;
            }
            case "id_delivery": {
                setId_delivery(data);
                break;
            }
        }

    }

    public goodsInOrder getOrdered() {
        return ordered;
    }

    public void setOrdered(goodsInOrder ordered) {
        this.ordered = ordered;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId_buyer() {
        return id_buyer;
    }

    public void setId_buyer(String id_buyer) {
        this.id_buyer = id_buyer;
    }

    public String getId_employee() {
        return id_employee;
    }

    public void setId_employee(String id_employee) {
        this.id_employee = id_employee;
    }

    public String getDataStart() {
        return dataStart;
    }

    public void setDataStart(String dataStart) {
        this.dataStart = dataStart;
    }

    public String getDataEnd() {
        return dataEnd;
    }

    public void setDataEnd(String dataEnd) {
        this.dataEnd = dataEnd;
    }

    public String getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(String finalPrice) {
        this.finalPrice = finalPrice;
    }

    public String getId_delivery() {
        return id_delivery;
    }

    public void setId_delivery(String id_delivery) {
        this.id_delivery = id_delivery;
    }
}
