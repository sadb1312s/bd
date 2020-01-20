package sample.Data;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.IntegerStringConverter;

import java.util.ArrayList;

public class Buyer extends Data {
    String fioBuyer,phoneBuyer,adressBuyer;
    int orderCount,orderSum;


    public Buyer(ArrayList<String> data) {
        super();


        setId(Integer.valueOf(data.get(0)));
        fioBuyer = data.get(1);
        phoneBuyer = data.get(2);
        adressBuyer = data.get(3);
        orderCount = Integer.parseInt(data.get(4));
        orderSum = Integer.parseInt(data.get(5));
    }
    public Buyer(){
        super();
        tableName = "buyer";
    }


    @Override
    public ObservableList getDataset(RawData data) {
        ObservableList<Buyer> tableData = FXCollections.observableArrayList();
        for(int i = 0; i < data.rows.size(); i++){
            tableData.add(new Buyer(data.getRow(i)));
        }

        return tableData;
    }

    @Override
    public ObservableList getNullDataset(RawData data) {
        ArrayList<String> d = new ArrayList<>(data.size);
        for(int i = 0; i < data.size;i++)
            d.add("");

        ObservableList<Buyer> tableData = FXCollections.observableArrayList();
        tableData.add(new Buyer());

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

                id = Integer.valueOf(data);
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
                orderCount = Integer.parseInt(data);
                break;
            }
            case "orderSum": {
                orderSum = Integer.parseInt(data);
                break;
            }
        }
    }

    @Override
    public void setNull() {
        super.setNull();
        fioBuyer = phoneBuyer = adressBuyer = "";
        orderCount = orderSum = 0;
    }

    @Override
    public Data clone() throws CloneNotSupportedException {
        Buyer b = new Buyer();
        b.setId(id);
        b.setFioBuyer(fioBuyer);
        b.setPhoneBuyer(phoneBuyer);
        b.setAdressBuyer(adressBuyer);
        b.setOrderCount(orderCount);
        b.setOrderSum(orderSum);

        return b;
    }

    //javaFX
    @Override
    public TableView formTable(RawData data) {
        ObservableList<Buyer> tableData = getDataset(data);

        TableView<Buyer> table = new TableView<Buyer>();
        table.setEditable(true);


        mainTable = table;

        //Добавляем столбцы
        /*//столбец ID
        TableColumn col1 = new TableColumn("id");
        col1.setCellValueFactory(new PropertyValueFactory<goodsType, Integer>("id"));
        col1.setId("id");
        col1.setVisible(false);*/

        //столбец Фио
        TableColumn col2 = new TableColumn("Фио");
        col2.setCellValueFactory(new PropertyValueFactory<Buyer, String>("fioBuyer"));
        col2.setId("fioBuyer");
        //обработчик изменения
        col2.setCellFactory(TextFieldTableCell.forTableColumn());
        col2.setOnEditCommit((EventHandler<TableColumn.CellEditEvent<Buyer, String>>) t -> {

                    Buyer inCell =  t.getTableView().getItems().get(t.getTablePosition().getRow());
                    String columnName = t.getTableColumn().getId();

                    System.out.println("изменяем стобец "+columnName+" ,новые данные "+t.getNewValue());
                    System.out.println("текущие данные");
                    System.out.println(inCell.getAllData());
                    inCell.fioBuyer = t.getNewValue();
                    System.out.println("после изменения");
                    System.out.println(inCell.getAllData());

                    String res = dbworker.SQLRequest("UPDATE "+tableName+" set " + columnName + "='" + t.getNewValue() + "' where id = " + inCell.getId() + ";");

                    log.setText("");
                    log.setText(res);
                }
        );

        //столбец телефон
        TableColumn col3 = new TableColumn("телефон");
        col3.setCellValueFactory(new PropertyValueFactory<Buyer, String>("phoneBuyer"));
        col3.setId("phoneBuyer");
        //обработчик изменения
        col3.setCellFactory(TextFieldTableCell.forTableColumn());
        col3.setOnEditCommit((EventHandler<TableColumn.CellEditEvent<Buyer, String>>) t -> {

                    Buyer inCell =  t.getTableView().getItems().get(t.getTablePosition().getRow());
                    String columnName = t.getTableColumn().getId();

                    System.out.println("изменяем стобец "+columnName+" ,новые данные "+t.getNewValue()+", имя таблицы "+tableName);
                    System.out.println("текущие данные");
                    System.out.println(inCell.getAllData());
                    inCell.phoneBuyer = t.getNewValue();
                    System.out.println("после изменения");
                    System.out.println(inCell.getAllData());

                    String res = dbworker.SQLRequest("UPDATE "+tableName+" set " + columnName + "='" + t.getNewValue() + "' where id = " + inCell.getId() + ";");

                    log.setText("");
                    log.setText(res);
                }
        );
        //столбец адресс adress
        TableColumn col4 = new TableColumn("адресс");
        col4.setCellValueFactory(new PropertyValueFactory<Buyer, String>("adressBuyer"));
        col4.setId("adressBuyer");
        //обработчик изменения
        col4.setCellFactory(TextFieldTableCell.forTableColumn());
        col4.setOnEditCommit((EventHandler<TableColumn.CellEditEvent<Buyer, String>>) t -> {

                    Buyer inCell =  t.getTableView().getItems().get(t.getTablePosition().getRow());
                    String columnName = t.getTableColumn().getId();

                    System.out.println("изменяем стобец "+columnName+" ,новые данные "+t.getNewValue()+" table "+tableName);
                    System.out.println("текущие данные");
                    System.out.println(inCell.getAllData());
                    inCell.adressBuyer = t.getNewValue();
                    System.out.println("после изменения");
                    System.out.println(inCell.getAllData());

                    String res = dbworker.SQLRequest("UPDATE "+tableName+" set " + columnName + "='" + t.getNewValue() + "' where id = " + inCell.getId() + ";");

                    log.setText("");
                    log.setText(res);
                }
        );
        //столбец количество заказов
        TableColumn col5 = new TableColumn("заказы");
        col5.setCellValueFactory(new PropertyValueFactory<Buyer, Integer>("orderCount"));
        col5.setId("orderCount");
        //обработчик изменения
        col5.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        col5.setOnEditCommit((EventHandler<TableColumn.CellEditEvent<Buyer, Integer>>) t -> {

                    Buyer inCell =  t.getTableView().getItems().get(t.getTablePosition().getRow());
                    String columnName = t.getTableColumn().getId();

                    System.out.println("изменяем стобец "+columnName+" ,новые данные "+t.getNewValue());
                    System.out.println("текущие данные");
                    System.out.println(inCell.getAllData());
                    inCell.orderCount = t.getNewValue();
                    System.out.println("после изменения");
                    System.out.println(inCell.getAllData());

                    String res = dbworker.SQLRequest("UPDATE "+tableName+" set " + columnName + "='" + t.getNewValue() + "' where id = " + inCell.getId() + ";");

                    log.setText("");
                    log.setText(res);
                }
        );

        //Столбец сумма заказов
        TableColumn col6 = new TableColumn("сумма Заказов");
        col6.setCellValueFactory(new PropertyValueFactory<Buyer, String>("orderSum"));
        col6.setId("orderSum");
        //обработчик изменения
        col6.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        col6.setOnEditCommit((EventHandler<TableColumn.CellEditEvent<Buyer, Integer>>) t -> {

                    Buyer inCell =  t.getTableView().getItems().get(t.getTablePosition().getRow());
                    String columnName = t.getTableColumn().getId();

                    System.out.println("изменяем стобец "+columnName+" ,новые данные "+t.getNewValue());
                    System.out.println("текущие данные");
                    System.out.println(inCell.getAllData());
                    inCell.orderSum = t.getNewValue();
                    System.out.println("после изменения");
                    System.out.println(inCell.getAllData());

                    String res = dbworker.SQLRequest("UPDATE "+tableName+" set " + columnName + "='" + t.getNewValue() + "' where id = " + inCell.getId() + ";");

                    log.setText("");
                    log.setText(res);
                }
        );

        //столбец с кнопкой
        TableColumn buttonCol = new TableColumn();
        buttonCol.setCellFactory(getButtonCell("удалить"));

        table.getColumns().addAll(col2,col3,col4,col5,col6,buttonCol);
        table.setItems(tableData);

        return table;
    }

    @Override
    public TableView formAddTable(RawData data) {
        ObservableList<Buyer> tableData = getNullDataset(data);

        TableView<Buyer> table = new TableView<Buyer>();
        table.setEditable(true);


        addTable = table;

        //Добавляем столбцы
        //столбец Фио
        TableColumn col2 = new TableColumn("Фио");
        col2.setCellValueFactory(new PropertyValueFactory<Buyer, String>("fioBuyer"));
        col2.setId("fioBuyer");
        //обработчик изменения
        col2.setCellFactory(TextFieldTableCell.forTableColumn());
        col2.setOnEditCommit((EventHandler<TableColumn.CellEditEvent<Buyer, String>>) t -> {

                    Buyer inCell =  t.getTableView().getItems().get(t.getTablePosition().getRow());
                    String columnName = t.getTableColumn().getId();

                    System.out.println("изменяем стобец "+columnName+" ,новые данные "+t.getNewValue());
                    System.out.println("текущие данные");
                    System.out.println(inCell.getAllData());
                    inCell.fioBuyer = t.getNewValue();
                    System.out.println("после изменения");
                    System.out.println(inCell.getAllData());
                }
        );

        //столбец телефон
        TableColumn col3 = new TableColumn("телефон");
        col3.setCellValueFactory(new PropertyValueFactory<Buyer, String>("phoneBuyer"));
        col3.setId("phoneBuyer");
        //обработчик изменения
        col3.setCellFactory(TextFieldTableCell.forTableColumn());
        col3.setOnEditCommit((EventHandler<TableColumn.CellEditEvent<Buyer, String>>) t -> {

                    Buyer inCell =  t.getTableView().getItems().get(t.getTablePosition().getRow());
                    String columnName = t.getTableColumn().getId();

                    System.out.println("изменяем стобец "+columnName+" ,новые данные "+t.getNewValue()+", имя таблицы "+tableName);
                    System.out.println("текущие данные");
                    System.out.println(inCell.getAllData());
                    inCell.phoneBuyer = t.getNewValue();
                    System.out.println("после изменения");
                    System.out.println(inCell.getAllData());

                }
        );
        //столбец адресс adress
        TableColumn col4 = new TableColumn("адресс");
        col4.setCellValueFactory(new PropertyValueFactory<Buyer, String>("adressBuyer"));
        col4.setId("adressBuyer");
        //обработчик изменения
        col4.setCellFactory(TextFieldTableCell.forTableColumn());
        col4.setOnEditCommit((EventHandler<TableColumn.CellEditEvent<Buyer, String>>) t -> {

                    Buyer inCell =  t.getTableView().getItems().get(t.getTablePosition().getRow());
                    String columnName = t.getTableColumn().getId();

                    System.out.println("изменяем стобец "+columnName+" ,новые данные "+t.getNewValue()+" table "+tableName);
                    System.out.println("текущие данные");
                    System.out.println(inCell.getAllData());
                    inCell.adressBuyer = t.getNewValue();
                    System.out.println("после изменения");
                    System.out.println(inCell.getAllData());
                }
        );
        //столбец количество заказов
        TableColumn col5 = new TableColumn("заказы");
        col5.setCellValueFactory(new PropertyValueFactory<Buyer, Integer>("orderCount"));
        col5.setId("orderCount");
        //обработчик изменения
        col5.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        col5.setOnEditCommit((EventHandler<TableColumn.CellEditEvent<Buyer, Integer>>) t -> {

                    Buyer inCell =  t.getTableView().getItems().get(t.getTablePosition().getRow());
                    String columnName = t.getTableColumn().getId();

                    System.out.println("изменяем стобец "+columnName+" ,новые данные "+t.getNewValue());
                    System.out.println("текущие данные");
                    System.out.println(inCell.getAllData());
                    inCell.orderCount = t.getNewValue();
                    System.out.println("после изменения");
                    System.out.println(inCell.getAllData());

                }
        );

        //Столбец сумма заказов
        TableColumn col6 = new TableColumn("сумма Заказов");
        col6.setCellValueFactory(new PropertyValueFactory<Buyer, String>("orderSum"));
        col6.setId("orderSum");
        //обработчик изменения
        col6.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        col6.setOnEditCommit((EventHandler<TableColumn.CellEditEvent<Buyer, Integer>>) t -> {

                    Buyer inCell =  t.getTableView().getItems().get(t.getTablePosition().getRow());
                    String columnName = t.getTableColumn().getId();

                    System.out.println("изменяем стобец "+columnName+" ,новые данные "+t.getNewValue());
                    System.out.println("текущие данные");
                    System.out.println(inCell.getAllData());
                    inCell.orderSum = t.getNewValue();
                    System.out.println("после изменения");
                    System.out.println(inCell.getAllData());
                }
        );


        //столбец с кнопкой
        TableColumn buttonCol = new TableColumn();
        buttonCol.setCellFactory(getButtonCell("добавить"));

        table.getColumns().addAll(col2,col3,col4,col5,col6,buttonCol);
        table.setItems(tableData);

        return table;
    }

    //getters and setters
    public Integer getId() {
        return id;
    }

    public void setId(int id) {
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

    public int getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(int orderCount) {
        this.orderCount = orderCount;
    }

    public int getOrderSum() {
        return orderSum;
    }

    public void setOrderSum(int orderSum) {
        this.orderSum = orderSum;
    }
}
