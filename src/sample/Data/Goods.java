package sample.Data;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class Goods extends Data {


    public String id_type;
    public String name;
    public String price;
    public String description;
    public String id_producer;
    public String balance;
    public String goods_type;
    public String producer;

    public Goods(){
        super("");
    }

    public Goods(String tableName, ArrayList<String> data) {
        super(data.get(0));
        id = data.get(0);
        id_type = data.get(1);
        name = data.get(2);
        price = data.get(3);
        description = data.get(4);
        id_producer = data.get(5);
        balance = data.get(6);
        goods_type = data.get(7);
        producer = data.get(8);


        System.out.println("!!!! "+id);
        System.out.println("!!!! "+super.id);
    }


    @Override
    public ObservableList getDataset(RawData data) {
        ObservableList<Goods> tableData = FXCollections.observableArrayList();
        for(int i = 0; i < data.rows.size(); i++){
            tableData.add(new Goods(data.tableName, data.getRow(i)));
        }

        return tableData;
    }
    @Override
    public ObservableList getNullDataset(RawData data) {

        ArrayList<String> d = new ArrayList<>(data.size);
        for (int i = 0; i < data.size; i++)
            d.add("");

        ObservableList<Goods> tableData = FXCollections.observableArrayList();
        tableData.add(new Goods(data.tableName, d));


        return tableData;
    }

    @Override
    public String getAllData() {
        return "";
    }
    @Override
    public void addCustomData(String columnType, String data) {
        System.out.println("type = "+columnType);

        switch (columnType){
            case "id": {
                setId(data);
                id = data;
                break;
            }
            case "id_type": {
                setId_type(data);
                break;
            }
            case "price": {
                setPrice(data);
                break;
            }
            case "description": {
                setDescription(data);
                break;
            }
            case "id_producer": {
                setId_producer(data);
                break;
            }
            case "balance": {
                setBalance(data);
                break;
            }
            case "goods_type": {
                setGoods_type(data);
                break;
            }
            case "producer": {
                setProducer(data);
                break;
            }
        }
    }

    public String getId_type() {
        return id_type;
    }

    public void setId_type(String id_type) {
        this.id_type = id_type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getId_producer() {
        return id_producer;
    }

    public void setId_producer(String id_producer) {
        this.id_producer = id_producer;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getGoods_type() {
        return goods_type;
    }

    public void setGoods_type(String goods_type) {
        this.goods_type = goods_type;
    }

    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }
}
