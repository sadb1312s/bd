package sample.Data.addOrderForm;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import sample.Controller;
import sample.Data.Data;
import sample.Data.RawData;
import sample.Data.tableForm.Buyer;
import sample.Data.tableForm.Employees;
import sample.Data.tableForm.Goods;
import sample.Data.tableForm.goodsType;

import java.util.ArrayList;

public class Order extends Data {

    ObservableList<Goods> addedGoods;
    ObservableList<Goods> goodsHelp;
    ObservableList<Employees> employeesHelp;
    ObservableList<Buyer> buyersHelp;

    public Order currentOrder;
    public ObservableList currentList;

    public goodsInOrder ordered;
    public int id_buyer,id_employee,id_delivery ;
    public String dataStart,dataEnd,finalPrice, buyerName, empName;



    public Order() {
        super();
        tableName = "orders";

        id_delivery = 1;
        finalPrice = "0";
        dataEnd = "12.12.2100";

        //ordered = new goodsInOrder();
    }

    public void setHelp(){
        System.out.println("FORM HELP DATA");
        RawData emps = dbworker.sendSQLselectAllRequest(Controller.employeesRequest);
        RawData buyer = dbworker.sendSQLselectAllRequest(Controller.buyerRequest);
        RawData goods = dbworker.sendSQLselectAllRequest(Controller.goodsRequest);

        addedGoods = FXCollections.observableArrayList();
        goodsHelp = new Goods().getDataset(goods);
        employeesHelp = new Employees().getDataset(emps);
        buyersHelp = new Buyer().getDataset(buyer);

        currentList = FXCollections.observableArrayList();
        currentList.addAll(currentOrder);
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
                setId_buyer(Integer.parseInt(data));
                break;
            }
            case "id_employee": {
                setId_employee(Integer.parseInt(data));
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
                setId_delivery(Integer.parseInt(data));
                break;
            }
        }

    }

    @Override
    public void setNull() {
        super.setNull();
        id_delivery = 1;
        id_buyer = id_employee = 0;
        dataStart = dataEnd = finalPrice = buyerName = empName = "";
    }

    //JavaFx


    @Override
    public TableView formAddTable(RawData data) {

        ObservableList<Order> tableData = getNullDataset(data);


        TableView<Order> table = new TableView<Order>();
        table.setEditable(true);


        //Столбцы
        //столбец Выбор клиента
        TableColumn col1 = new TableColumn("Покупатель");
        col1.setCellValueFactory(new PropertyValueFactory<Order, String>("buyerName"));
        col1.setId("id_buyer");
        //обработчик изменения
        col1.setCellFactory(ComboBoxTableCell.forTableColumn(buyersHelp));
        col1.setOnEditCommit((EventHandler<TableColumn.CellEditEvent<Order, Buyer>>) t -> {

                    Order inCell =  t.getTableView().getItems().get(t.getTablePosition().getRow());
                    String columnName = t.getTableColumn().getId();

                    System.out.println("изменяем стобец "+columnName+" ,новые данные "+t.getNewValue());
                    System.out.println("текущие данные");
                    System.out.println(inCell.getAllData());
                    currentOrder.id_buyer = t.getNewValue().getId();
                    currentOrder.buyerName = t.getNewValue().getFioBuyer();
                    inCell.id_buyer = t.getNewValue().getId();
                    inCell.buyerName = t.getNewValue().getFioBuyer();
                    System.out.println("после изменения");
                    System.out.println(inCell.getAllData());
                    System.out.println(buyerName);
                    System.out.println(">>>> "+inCell);

                    table.refresh();

                }
        );

        //столбец выбор сотрудника
        TableColumn col2 = new TableColumn("сотрудник");
        col2.setCellValueFactory(new PropertyValueFactory<Order, String>("empName"));
        col2.setId("id_buyer");
        //обработчик изменения
        col2.setCellFactory(ComboBoxTableCell.forTableColumn(employeesHelp));
        col2.setOnEditCommit((EventHandler<TableColumn.CellEditEvent<Order, Employees>>) t -> {

                    Order inCell =  t.getTableView().getItems().get(t.getTablePosition().getRow());
                    String columnName = t.getTableColumn().getId();

                    System.out.println("изменяем стобец "+columnName+" ,новые данные "+t.getNewValue());
                    System.out.println("текущие данные");
                    System.out.println(inCell.getAllData());
                    currentOrder.id_employee = t.getNewValue().getId();
                    currentOrder.empName = t.getNewValue().getFio();

                    inCell.id_employee = t.getNewValue().getId();
                    inCell.empName = t.getNewValue().getFio();
                    System.out.println("после изменения");
                    System.out.println(inCell.getAllData());
                    System.out.println(buyerName);

                    table.refresh();

                }
        );

        //столбец установка даты
        TableColumn col3 = new TableColumn("дата оформления");
        col3.setCellValueFactory(new PropertyValueFactory<Order, String>("dataStart"));
        col3.setId("dataStart");
        //обработчик изменения
        col3.setCellFactory(TextFieldTableCell.forTableColumn());
        col3.setOnEditCommit((EventHandler<TableColumn.CellEditEvent<Order, String>>) t -> {

                    Order inCell =  t.getTableView().getItems().get(t.getTablePosition().getRow());
                    String columnName = t.getTableColumn().getId();

                    System.out.println("изменяем стобец "+columnName+" ,новые данные "+t.getNewValue());
                    System.out.println("текущие данные");
                    System.out.println(inCell.getAllData());
                    inCell.dataStart = t.getNewValue();
                    currentOrder.dataStart = t.getNewValue();
                    System.out.println("после изменения");
                    System.out.println(inCell.getAllData());
                    System.out.println(inCell);

                    table.refresh();

                }
        );

        //дата завершения

        TableColumn col4 = new TableColumn("дата завершения(планируемая)");
        col4.setCellValueFactory(new PropertyValueFactory<Order, String>("dataEnd"));
        col4.setId("dataEnd");
        //обработчик изменения
        col4.setCellFactory(TextFieldTableCell.forTableColumn());
        col4.setOnEditCommit((EventHandler<TableColumn.CellEditEvent<Order, String>>) t -> {

                    Order inCell =  t.getTableView().getItems().get(t.getTablePosition().getRow());
                    String columnName = t.getTableColumn().getId();

                    System.out.println("изменяем стобец "+columnName+" ,новые данные "+t.getNewValue());
                    System.out.println("текущие данные");
                    System.out.println(inCell.getAllData());
                    inCell.dataEnd = t.getNewValue();
                    currentOrder.dataEnd = t.getNewValue();
                    System.out.println("после изменения");
                    System.out.println(inCell.getAllData());

                    table.refresh();

                }
        );

        table.getColumns().addAll(col1,col2,col3,col4);
        table.setItems(currentList);



        return table;
    }

    public goodsInOrder getOrdered() {
        return ordered;
    }

    public void setOrdered(goodsInOrder ordered) {
        this.ordered = ordered;
    }




    //getters and setters;
    public Integer getId() {
        return Integer.valueOf(id);
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_buyer() {
        return id_buyer;
    }

    public void setId_buyer(int id_buyer) {
        this.id_buyer = id_buyer;
    }

    public int getId_employee() {
        return id_employee;
    }

    public void setId_employee(int id_employee) {
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

    public int getId_delivery() {
        return id_delivery;
    }

    public void setId_delivery(int id_delivery) {
        this.id_delivery = id_delivery;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }
}
