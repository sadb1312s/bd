/*
package sample.Data;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class goodsInOrderHelp extends Data{
    String id_goods,name,count,price;

    public goodsInOrderHelp(){
        super("");

    }
    public goodsInOrderHelp(String id_goods,String  name,String count,String price){
        super("");

    }


    @Override
    public ObservableList getNullDataset(RawData data) {
        ArrayList<String> d = new ArrayList<>(data.size);
        for (int i = 0; i < data.size; i++)
            d.add("");

        ObservableList<goodsInOrderHelp> tableData = FXCollections.observableArrayList();
        tableData.add(new goodsInOrderHelp());


        return tableData;
    }

    @Override
    public String getAllData() {
        return getId_goods()+" "+getName()+" "+getPrice();
    }

    @Override
    public String toString() {
        return name;
    }

    public String getId_goods() {
        return id_goods;
    }

    public void setId_goods(String id_goods) {
        this.id_goods = id_goods;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
*/
