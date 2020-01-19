package sample.Data;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class Buyer extends Data {
    String id,fioBuyer,phoneBuyer,adressBuyer,orderCount,orderSum;


    public Buyer(String tableName,ArrayList<String> data) {
        super(tableName);

        id = data.get(0);
        fioBuyer = data.get(1);
        phoneBuyer = data.get(2);
        adressBuyer = data.get(3);
        orderCount = data.get(4);
        orderSum = data.get(5);
    }
    public Buyer(){
        super("");
    }


    @Override
    public ObservableList getDataset(RawData data) {
        ObservableList<Buyer> tableData = FXCollections.observableArrayList();
        for(int i = 0; i < data.rows.size(); i++){
            tableData.add(new Buyer(data.tableName, data.getRow(i)));
        }

        return tableData;
    }

    @Override
    public ObservableList getNullDataset(RawData data) {
        ArrayList<String> d = new ArrayList<>(data.size);
        for(int i = 0; i < data.size;i++)
            d.add("");

        ObservableList<Buyer> tableData = FXCollections.observableArrayList();
        tableData.add(new Buyer(data.tableName,d));

        return tableData;
    }

    @Override
    public String getAllData() {
        System.out.println("!Dsad");
        return "'"+id+"','"+fioBuyer+"','"+phoneBuyer+"','"+adressBuyer+"','"+orderCount+"','"+orderSum+"'";
    }

    @Override
    public void addCustomData(String columnType, String data) {
        switch (columnType){
            case "id": {

                id = data;
                break;
            }
            case "fioBuyer": {
                fioBuyer = data;
                break;
            }
            case "phoneBuyer": {
                phoneBuyer = data;
                break;
            }
            case "adressBuyer": {
                adressBuyer = data;
                break;
            }
            case "orderCount": {
                orderCount = data;
                break;
            }
            case "orderSum": {
                orderSum = data;
                break;
            }
        }
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public String getFioBuyer() {
        return fioBuyer;
    }

    public void setFioBuyer(String fioBuyer) {
        this.fioBuyer = fioBuyer;
    }

    public String getPhoneBuyer() {
        return phoneBuyer;
    }

    public void setPhoneBuyer(String phoneBuyer) {
        this.phoneBuyer = phoneBuyer;
    }

    public String getAdressBuyer() {
        return adressBuyer;
    }

    public void setAdressBuyer(String adressBuyer) {
        this.adressBuyer = adressBuyer;
    }

    public String getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(String orderCount) {
        this.orderCount = orderCount;
    }

    public String getOrderSum() {
        return orderSum;
    }

    public void setOrderSum(String orderSum) {
        this.orderSum = orderSum;
    }
}
