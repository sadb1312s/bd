package sample.Data.tableForm;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.IntegerStringConverter;
import sample.Controller;
import sample.Data.Data;
import sample.Data.RawData;

import java.util.ArrayList;

public class Goods extends Data {

    public String name;
    public int price;
    public String description;
    public int balance;

    public int typeFkey;
    public int prodFkey;
    public String producer;
    public String type;
    //for help
    public int Count = 0;
    public static int Accumsum = 0;



    ObservableList<goodsType> typeData;
    ObservableList<Producer> producersData;

    public Goods(){
        super();
        tableName = "goods";
    }

    public void formHelpData(){
        System.out.println("ВСПОМОГАТЕЛЬНЫЕ ДАННЫЕ");
        RawData types = dbworker.sendSQLselectAllRequest(Controller.goodsTypeReuest);
        RawData producers = dbworker.sendSQLselectAllRequest(Controller.producerRequest);

        typeData = new goodsType().getDataset(types);
        producersData = new Producer().getDataset(producers);
    }

    public Goods(ArrayList<String> data) {
        super();

        setId(Integer.valueOf(data.get(0)));
        setTypeFkey(Integer.parseInt(data.get(1)));
        setName(data.get(2));
        setPrice(Integer.parseInt(data.get(3)));
        setDescription(data.get(4));
        setProdFkey(Integer.parseInt(data.get(5)));
        setBalance(Integer.parseInt(data.get(6)));
        setProducer(data.get(8));
        setType(data.get(7));
        System.out.println(">>>>>> "+data);
    }


    @Override
    public ObservableList getDataset(RawData data) {
        ObservableList<Goods> tableData = FXCollections.observableArrayList();
        for(int i = 0; i < data.rows.size(); i++){
            tableData.add(new Goods(data.getRow(i)));
        }

        return tableData;
    }
    @Override
    public ObservableList getNullDataset(RawData data) {

        ArrayList<String> d = new ArrayList<>(data.size);
        for (int i = 0; i < data.size; i++)
            d.add("");

        ObservableList<Goods> tableData = FXCollections.observableArrayList();
        tableData.add(new Goods());


        return tableData;
    }

    @Override
    public String getAllData() {
        //return "'"+id+"','"+id_type+"','"+name+"','"+price+"','"+description+"','"+id_producer+"','"+balance+"','"+goods_type+"','"+producer+"'";
        return "'"+id+"','"+typeFkey+"','"+name+"','"+price+"','"+description+"','"+prodFkey+"','"+balance+"'";
    }

    @Override
    public void addCustomData(String columnType, String data) {
        /*System.out.println("type = "+columnType+" data= "+data);
        System.out.println("!!! "+getAllData());

        switch (columnType){
            case "id": {
                //setId(data);
                //id = data;
                break;
            }
            case "id_type": {
                setId_type(data);
                break;
            }
            case "name": {
                System.out.println("set NAME");
                setName(data);
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
        }*/
    }

    @Override
    public void setNull() {
        super.setNull();
        name = "";
        price = 0;
        description = "";
        balance = 0;
        prodFkey = typeFkey = 0;
        producer = type = "";
    }

    @Override
    public Data clone() throws CloneNotSupportedException {
        Goods g = new Goods();
        g.setId(id);
        g.setName(name);
        g.setPrice(price);
        g.setDescription(description);
        g.setBalance(balance);
        g.setTypeFkey(typeFkey);
        g.setProdFkey(prodFkey);
        g.setType(type);
        g.setProducer(producer);

        return g;
    }

    @Override
    public String toString() {
        return id+" "+name;
    }

    //JavaFx
    @Override
    public TableView formTable(RawData data) {
        ObservableList<Goods> tableData = getDataset(data);

        TableView<Goods> table = new TableView<Goods>();
        table.setEditable(true);


        mainTable = table;


        //Столбцы
        //столбец тип
        TableColumn col2 = new TableColumn("Тип товара");
        col2.setCellValueFactory(new PropertyValueFactory<Goods, String>("type"));
        col2.setId("id_type");
        //обработчик изменения
        col2.setCellFactory(ComboBoxTableCell.forTableColumn(typeData));
        col2.setOnEditCommit((EventHandler<TableColumn.CellEditEvent<Goods, goodsType>>) t -> {

                    Goods inCell =  t.getTableView().getItems().get(t.getTablePosition().getRow());
                    String columnName = t.getTableColumn().getId();

                    System.out.println("изменяем стобец "+columnName+" ,новые данные "+t.getNewValue());
                    System.out.println("текущие данные");
                    System.out.println(inCell.getAllData());
                    inCell.typeFkey = t.getNewValue().getID_Type();
                    inCell.type = t.getNewValue().getGoods_type();
                    System.out.println("после изменения");
                    System.out.println(inCell.getAllData());

                    mainTable.refresh();
                    String res = dbworker.SQLRequest("UPDATE "+tableName+" set " + columnName + "='" + inCell.getTypeFkey() + "' where id = " + inCell.getId() + ";");

                    log.setText("");
                    log.setText(res);
                }
        );


        //столбец имя
        TableColumn col3 = new TableColumn("название");
        col3.setCellValueFactory(new PropertyValueFactory<Goods, String>("name"));
        col3.setId("name");
        //обработчик изменения
        col3.setCellFactory(TextFieldTableCell.forTableColumn());
        col3.setOnEditCommit((EventHandler<TableColumn.CellEditEvent<Goods, String>>) t -> {

                    Goods inCell =  t.getTableView().getItems().get(t.getTablePosition().getRow());
                    String columnName = t.getTableColumn().getId();

                    System.out.println("изменяем стобец "+columnName+" ,новые данные "+t.getNewValue()+", имя таблицы "+tableName);
                    System.out.println("текущие данные");
                    System.out.println(inCell.getAllData());
                    inCell.name = t.getNewValue();
                    System.out.println("после изменения");
                    System.out.println(inCell.getAllData());

                    String res = dbworker.SQLRequest("UPDATE "+tableName+" set " + columnName + "='" + t.getNewValue() + "' where id = " + inCell.getId() + ";");

                    log.setText("");
                    log.setText(res);
                }
        );

        //столбец адресс Цена
        TableColumn col4 = new TableColumn("цена");
        col4.setCellValueFactory(new PropertyValueFactory<Goods, Integer>("price"));
        col4.setId("price");
        //обработчик изменения
        col4.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        col4.setOnEditCommit((EventHandler<TableColumn.CellEditEvent<Goods, Integer>>) t -> {

                    Goods inCell =  t.getTableView().getItems().get(t.getTablePosition().getRow());
                    String columnName = t.getTableColumn().getId();

                    System.out.println("изменяем стобец "+columnName+" ,новые данные "+t.getNewValue()+" table "+tableName);
                    System.out.println("текущие данные");
                    System.out.println(inCell.getAllData());
                    inCell.price = Integer.parseInt(String.valueOf(t.getNewValue()));
                    System.out.println("после изменения");
                    System.out.println(inCell.getAllData());

                    String res = dbworker.SQLRequest("UPDATE "+tableName+" set " + columnName + "='" + t.getNewValue() + "' where id = " + inCell.getId() + ";");

                    log.setText("");
                    log.setText(res);
                }
        );

        //столбец описание
        TableColumn col5 = new TableColumn("описание");
        col5.setCellValueFactory(new PropertyValueFactory<Goods, Integer>("description"));
        col5.setId("description");
        //обработчик изменения
        col5.setCellFactory(TextFieldTableCell.forTableColumn());
        col5.setOnEditCommit((EventHandler<TableColumn.CellEditEvent<Goods, Integer>>) t -> {

                    Goods inCell =  t.getTableView().getItems().get(t.getTablePosition().getRow());
                    String columnName = t.getTableColumn().getId();

                    System.out.println("изменяем стобец "+columnName+" ,новые данные "+t.getNewValue());
                    System.out.println("текущие данные");
                    System.out.println(inCell.getAllData());
                    inCell.description = String.valueOf(t.getNewValue());
                    System.out.println("после изменения");
                    System.out.println(inCell.getAllData());

                    String res = dbworker.SQLRequest("UPDATE "+tableName+" set " + columnName + "='" + t.getNewValue() + "' where id = " + inCell.getId() + ";");

                    log.setText("");
                    log.setText(res);
                }
        );

        //Столбец остаток
        TableColumn col6 = new TableColumn("остаток");
        col6.setCellValueFactory(new PropertyValueFactory<Goods, String>("balance"));
        col6.setId("balance");
        //обработчик изменения
        col6.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        col6.setOnEditCommit((EventHandler<TableColumn.CellEditEvent<Goods, Integer>>) t -> {

                    Goods inCell =  t.getTableView().getItems().get(t.getTablePosition().getRow());
                    String columnName = t.getTableColumn().getId();

                    System.out.println("изменяем стобец "+columnName+" ,новые данные "+t.getNewValue());
                    System.out.println("текущие данные");
                    System.out.println(inCell.getAllData());
                    inCell.balance = t.getNewValue();
                    System.out.println("после изменения");
                    System.out.println(inCell.getAllData());

                    String res = dbworker.SQLRequest("UPDATE "+tableName+" set " + columnName + "='" + t.getNewValue() + "' where id = " + inCell.getId() + ";");

                    log.setText("");
                    log.setText(res);
                }
        );

        //Столбец производитель
        TableColumn col7 = new TableColumn("производитель");
        col7.setCellValueFactory(new PropertyValueFactory<Goods, String>("producer"));
        col7.setId("id_producer");
        //обработчик изменения
        col7.setCellFactory(ComboBoxTableCell.forTableColumn(producersData));
        col7.setOnEditCommit((EventHandler<TableColumn.CellEditEvent<Goods, Producer>>) t -> {

                    Goods inCell =  t.getTableView().getItems().get(t.getTablePosition().getRow());
                    String columnName = t.getTableColumn().getId();

                    System.out.println("изменяем стобец "+columnName+" ,новые данные "+t.getNewValue());
                    System.out.println("текущие данные");
                    System.out.println(inCell.getAllData());
                    inCell.prodFkey = t.getNewValue().getId();
                    inCell.producer = t.getNewValue().getProducer();
                    System.out.println("после изменения");
                    System.out.println(inCell.getAllData());


                    mainTable.refresh();
                    String res = dbworker.SQLRequest("UPDATE "+tableName+" set " + columnName + "='" + t.getNewValue().getId() + "' where id = " + inCell.getId() + ";");

                    log.setText("");
                    log.setText(res);
                }
        );

        //столбец с кнопкой
        TableColumn buttonCol = new TableColumn();
        buttonCol.setCellFactory(getButtonCell("удалить"));

        table.getColumns().addAll(col2,col3,col4,col5,col6,col7,buttonCol);
        table.setItems(tableData);

        return table;
    }

    @Override
    public TableView formAddTable(RawData data) {
        ObservableList<Goods> tableData = getNullDataset(data);

        TableView<Goods> table = new TableView<Goods>();
        table.setEditable(true);


        addTable = table;

        //Столбцы
        //столбец тип
        TableColumn col2 = new TableColumn("Тип товара");
        col2.setCellValueFactory(new PropertyValueFactory<Goods, String>("type"));
        col2.setId("id_type");
        //обработчик изменения
        col2.setCellFactory(ComboBoxTableCell.forTableColumn(typeData));
        col2.setOnEditCommit((EventHandler<TableColumn.CellEditEvent<Goods, goodsType>>) t -> {

                    Goods inCell =  t.getTableView().getItems().get(t.getTablePosition().getRow());
                    String columnName = t.getTableColumn().getId();

                    System.out.println("изменяем стобец "+columnName+" ,новые данные "+t.getNewValue());
                    System.out.println("текущие данные");
                    System.out.println(inCell.getAllData());
                    inCell.typeFkey = t.getNewValue().getID_Type();
                    inCell.type = t.getNewValue().getGoods_type();
                    System.out.println("после изменения");
                    System.out.println(inCell.getAllData());

                    addTable.refresh();

                }
        );


        //столбец имя
        TableColumn col3 = new TableColumn("название");
        col3.setCellValueFactory(new PropertyValueFactory<Goods, String>("name"));
        col3.setId("name");
        //обработчик изменения
        col3.setCellFactory(TextFieldTableCell.forTableColumn());
        col3.setOnEditCommit((EventHandler<TableColumn.CellEditEvent<Goods, String>>) t -> {

                    Goods inCell =  t.getTableView().getItems().get(t.getTablePosition().getRow());
                    String columnName = t.getTableColumn().getId();

                    System.out.println("изменяем стобец "+columnName+" ,новые данные "+t.getNewValue()+", имя таблицы "+tableName);
                    System.out.println("текущие данные");
                    System.out.println(inCell.getAllData());
                    inCell.name = t.getNewValue();
                    System.out.println("после изменения");
                    System.out.println(inCell.getAllData());

                }
        );

        //столбец адресс Цена
        TableColumn col4 = new TableColumn("цена");
        col4.setCellValueFactory(new PropertyValueFactory<Goods, Integer>("price"));
        col4.setId("price");
        //обработчик изменения
        col4.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        col4.setOnEditCommit((EventHandler<TableColumn.CellEditEvent<Goods, Integer>>) t -> {

                    Goods inCell =  t.getTableView().getItems().get(t.getTablePosition().getRow());
                    String columnName = t.getTableColumn().getId();

                    System.out.println("изменяем стобец "+columnName+" ,новые данные "+t.getNewValue()+" table "+tableName);
                    System.out.println("текущие данные");
                    System.out.println(inCell.getAllData());
                    inCell.price = Integer.parseInt(String.valueOf(t.getNewValue()));
                    System.out.println("после изменения");
                    System.out.println(inCell.getAllData());

                }
        );

        //столбец описание
        TableColumn col5 = new TableColumn("описание");
        col5.setCellValueFactory(new PropertyValueFactory<Goods, Integer>("description"));
        col5.setId("description");
        //обработчик изменения
        col5.setCellFactory(TextFieldTableCell.forTableColumn());
        col5.setOnEditCommit((EventHandler<TableColumn.CellEditEvent<Goods, Integer>>) t -> {

                    Goods inCell =  t.getTableView().getItems().get(t.getTablePosition().getRow());
                    String columnName = t.getTableColumn().getId();

                    System.out.println("изменяем стобец "+columnName+" ,новые данные "+t.getNewValue());
                    System.out.println("текущие данные");
                    System.out.println(inCell.getAllData());
                    inCell.description = String.valueOf(t.getNewValue());
                    System.out.println("после изменения");
                    System.out.println(inCell.getAllData());

                }
        );

        //Столбец остаток
        TableColumn col6 = new TableColumn("остаток");
        col6.setCellValueFactory(new PropertyValueFactory<Goods, String>("balance"));
        col6.setId("balance");
        //обработчик изменения
        col6.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        col6.setOnEditCommit((EventHandler<TableColumn.CellEditEvent<Goods, Integer>>) t -> {

                    Goods inCell =  t.getTableView().getItems().get(t.getTablePosition().getRow());
                    String columnName = t.getTableColumn().getId();

                    System.out.println("изменяем стобец "+columnName+" ,новые данные "+t.getNewValue());
                    System.out.println("текущие данные");
                    System.out.println(inCell.getAllData());
                    inCell.balance = t.getNewValue();
                    System.out.println("после изменения");
                    System.out.println(inCell.getAllData());

                }
        );

        //Столбец производитель
        TableColumn col7 = new TableColumn("производитель");
        col7.setCellValueFactory(new PropertyValueFactory<Goods, String>("producer"));
        col7.setId("id_producer");
        //обработчик изменения
        col7.setCellFactory(ComboBoxTableCell.forTableColumn(producersData));
        col7.setOnEditCommit((EventHandler<TableColumn.CellEditEvent<Goods, Producer>>) t -> {

                    Goods inCell =  t.getTableView().getItems().get(t.getTablePosition().getRow());
                    String columnName = t.getTableColumn().getId();

                    System.out.println("изменяем стобец "+columnName+" ,новые данные "+t.getNewValue());
                    System.out.println("текущие данные");
                    System.out.println(inCell.getAllData());
                    inCell.prodFkey = t.getNewValue().getId();
                    inCell.producer = t.getNewValue().getProducer();
                    System.out.println("после изменения");
                    System.out.println(inCell.getAllData());


                    addTable.refresh();
                }
        );


        //столбец с кнопкой
        TableColumn buttonCol = new TableColumn();
        buttonCol.setCellFactory(getButtonCell("добавить"));

        table.getColumns().addAll(col2,col3,col4,col5,col6,col7,buttonCol);
        table.setItems(tableData);

        return table;
    }

    //getters and setters;
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public int getTypeFkey() {
        return typeFkey;
    }

    public void setTypeFkey(int typeFkey) {
        this.typeFkey = typeFkey;
    }

    public int getProdFkey() {
        return prodFkey;
    }

    public void setProdFkey(int prodFkey) {
        this.prodFkey = prodFkey;
    }

    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    //for helpData

    public int getCount() {
        return Count;
    }

    public void setCount(int count) {
        Count = count;
    }
}
