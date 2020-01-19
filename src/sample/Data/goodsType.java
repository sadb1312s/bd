package sample.Data;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.awt.image.AreaAveragingScaleFilter;
import java.util.ArrayList;

public class goodsType extends Data {
    public String goods_type;//описание
    public String discount;//скидка
    public goodsType(){
        super("");
    }

    public goodsType(String tableName, ArrayList<String> data) {
        super(tableName);
        if(data!=null) {
            id = data.get(0);
            goods_type = data.get(1);
            discount = data.get(2);
        }
    }


    public goodsType(String id){
        super(id);
    }

    public String getID_Type(){return super.id;}

    public String getGoods_type() {
        return goods_type;
    }

    public void setGoods_type(String goods_type) {
        this.goods_type = goods_type;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    @Override
    public ObservableList getDataset(RawData data) {
        ObservableList<goodsType> tableData = FXCollections.observableArrayList();
        for(int i = 0; i < data.rows.size(); i++){
            tableData.add(new goodsType(data.tableName, data.getRow(i)));
        }

        return tableData;
    }
    @Override
    public ObservableList getNullDataset(RawData data) {

        ArrayList<String> d = new ArrayList<>(data.size);
        for(int i = 0; i < data.size;i++)
            d.add("");

        ObservableList<goodsType> tableData = FXCollections.observableArrayList();
        tableData.add(new goodsType(data.tableName,d));


        return tableData;
    }
    @Override
    public void addCustomData(String columnType, String data) {
        System.out.println("type = "+columnType);

        switch (columnType){
            case "id": {

                id = data;
                break;
            }
            case "goods_type": {
                goods_type = data;
                break;
            }
            case "discount": {
                discount = data;
                break;
            }
        }
    }

    @Override
    public String getAllData() {
        System.out.println("!Dsad");
        return "'"+id+"','"+goods_type+"','"+discount+"'";
    }
}
