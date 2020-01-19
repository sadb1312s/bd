package sample.Data;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class Producer extends Data {
    String id;
    String producer;
    String country;


    public Producer(String tableName, ArrayList<String> data) {
        super(tableName);
        if(data!=null) {
            id = data.get(0);
            producer = data.get(1);
            country = data.get(2);
        }
    }

    public Producer() {
        super("");
    }

    @Override
    public ObservableList getDataset(RawData data) {
        ObservableList<Producer> tableData = FXCollections.observableArrayList();
        for(int i = 0; i < data.rows.size(); i++){
            tableData.add(new Producer(data.tableName, data.getRow(i)));
        }

        return tableData;
    }

    @Override
    public ObservableList getNullDataset(RawData data) {
        ArrayList<String> d = new ArrayList<>(data.size);
        for(int i = 0; i < data.size;i++)
            d.add("");

        ObservableList<Producer> tableData = FXCollections.observableArrayList();
        tableData.add(new Producer(data.tableName,d));

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

                id = data;
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




    public String getId() {
        return id;
    }


    public void setId(String id) {
        this.id = id;
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
