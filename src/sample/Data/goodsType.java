package sample.Data;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.IntegerStringConverter;
import sample.myDBworker;

import java.awt.image.AreaAveragingScaleFilter;
import java.util.ArrayList;

public class goodsType extends Data {
    public Goods parent;
    public String goods_type;//описание
    public Integer discount;//скидка
    public goodsType(){
        super();
        tableName = "goodstype";
    }

    public goodsType(ArrayList<String> data) {
        super();
        tableName = "goodstype";

        id = Integer.valueOf(data.get(0));
        goods_type = data.get(1);
        discount = Integer.valueOf(data.get(2));

    }

    //Override methods
    @Override
    public ObservableList getDataset(RawData data) {
        ObservableList<goodsType> tableData = FXCollections.observableArrayList();
        for(int i = 0; i < data.rows.size(); i++){
            tableData.add(new goodsType(data.getRow(i)));
        }

        return tableData;
    }

    @Override
    public ObservableList getNullDataset(RawData data) {

        ArrayList<String> d = new ArrayList<>(data.size);
        for(int i = 0; i < data.size;i++)
            d.add("");

        ObservableList<goodsType> tableData = FXCollections.observableArrayList();
        tableData.add(new goodsType());


        return tableData;
    }

    @Override
    public void addCustomData(String columnType, String data) {
        System.out.println("type = "+columnType);

        switch (columnType){
            case "id": {

                id = Integer.valueOf(data);
                break;
            }
            case "goods_type": {
                goods_type = data;
                break;
            }
            case "discount": {
                discount = Integer.valueOf(data);
                break;
            }
        }
    }

    @Override
    public String getAllData() {
        System.out.println("!Dsad");
        return "'"+id+"','"+goods_type+"','"+discount+"'";
    }

    @Override
    public void setNull() {
        super.setNull();
        goods_type = "";
        discount = null;
    }

    @Override
    public goodsType clone() throws CloneNotSupportedException {
        goodsType g = new goodsType();
        g.setId(id);
        g.setGoods_type(goods_type);
        g.setDiscount(discount);

        return g;
    }

    @Override
    public String toString() {
        return id+" "+goods_type;
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
        col1.setCellValueFactory(new PropertyValueFactory<goodsType, Integer>("id"));
        col1.setId("id");
        col1.setVisible(false);

        //столбец категория(название)
        TableColumn col2 = new TableColumn("категория");
        col2.setCellValueFactory(new PropertyValueFactory<goodsType, String>("goods_type"));
        col2.setId("goods_type");
        //обработчик изменения
        col2.setCellFactory(TextFieldTableCell.forTableColumn());
        col2.setOnEditCommit((EventHandler<TableColumn.CellEditEvent<goodsType, String>>) t -> {

            goodsType inCell =  t.getTableView().getItems().get(t.getTablePosition().getRow());
            String columnName = t.getTableColumn().getId();

            System.out.println("изменяем стобец "+columnName+" ,новые данные "+t.getNewValue());
            System.out.println("текущие данные");
            System.out.println(inCell.getAllData());
            inCell.goods_type = t.getNewValue();
            System.out.println("после изменения");
            System.out.println(inCell.getAllData());

            String res = dbworker.SQLRequest("UPDATE goodstype set " + columnName + "='" + t.getNewValue() + "' where id = " + inCell.getId() + ";");

            log.setText("");
            log.setText(res);
        }
        );

        //столбец скидка
        TableColumn col3 = new TableColumn("скидка");
        col3.setCellValueFactory(new PropertyValueFactory<goodsType, Integer>("discount"));
        col3.setId("discount");
        //обработчик изменения
        col3.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        col3.setOnEditCommit((EventHandler<TableColumn.CellEditEvent<goodsType, Integer>>) t -> {

            goodsType inCell =  t.getTableView().getItems().get(t.getTablePosition().getRow());
            String columnName = t.getTableColumn().getId();

            System.out.println("изменяем стобец "+columnName+" ,новые данные "+t.getNewValue());
            System.out.println("текущие данные");
            System.out.println(inCell.getAllData());
            inCell.discount = t.getNewValue();
            System.out.println("после изменения");
            System.out.println(inCell.getAllData());

            String res = dbworker.SQLRequest("UPDATE goodstype set " + columnName + "='" + t.getNewValue() + "' where id = " + inCell.getId() + ";");

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
        ObservableList<goodsType> tableData = getNullDataset(data);

        TableView<goodsType> table = new TableView<goodsType>();
        table.setEditable(true);


        addTable = table;

        //Добавляем столбцы
        //столбец ID
        TableColumn col1 = new TableColumn("id");
        col1.setCellValueFactory(new PropertyValueFactory<goodsType, Integer>("id"));
        col1.setId("id");
        col1.setVisible(false);

        //столбец категория(название)
        TableColumn col2 = new TableColumn("категория");
        col2.setCellValueFactory(new PropertyValueFactory<goodsType, String>("goods_type"));
        col2.setId("goods_type");
        //обработчик изменения
        col2.setCellFactory(TextFieldTableCell.forTableColumn());
        col2.setOnEditCommit((EventHandler<TableColumn.CellEditEvent<goodsType, String>>) t -> {

                    goodsType inCell =  t.getTableView().getItems().get(t.getTablePosition().getRow());
                    String columnName = t.getTableColumn().getId();

                    System.out.println("изменяем стобец "+columnName+" ,новые данные "+t.getNewValue());
                    System.out.println("текущие данные");
                    System.out.println(inCell.getAllData());
                    inCell.goods_type = t.getNewValue();
                    System.out.println("после изменения");
                    System.out.println(inCell.getAllData());
                }
        );

        //столбец скидка
        TableColumn col3 = new TableColumn("скидка");
        col3.setCellValueFactory(new PropertyValueFactory<goodsType, Integer>("discount"));
        col3.setId("discount");
        //обработчик изменения
        col3.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        col3.setOnEditCommit((EventHandler<TableColumn.CellEditEvent<goodsType, Integer>>) t -> {

                    goodsType inCell =  t.getTableView().getItems().get(t.getTablePosition().getRow());
                    String columnName = t.getTableColumn().getId();

                    System.out.println("изменяем стобец "+columnName+" ,новые данные "+t.getNewValue());
                    System.out.println("текущие данные");
                    System.out.println(inCell.getAllData());
                    inCell.discount = t.getNewValue();
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
    public Integer getID_Type(){return super.id;}

    public String getGoods_type() {
        return goods_type;
    }

    public void setGoods_type(String goods_type) {
        this.goods_type = goods_type;
    }

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }
}
