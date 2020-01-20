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

public class Employees extends Data{
    public String fio, phone,adress;/*,salary;*/
    public int salary;


    public Employees(ArrayList<String> data) {
        super();

        id = Integer.valueOf(data.get(0));
        fio = data.get(1);
        phone = data.get(2);
        adress = data.get(3);
        salary = Integer.parseInt(data.get(4));

    }
    public Employees() {
        super();
        tableName = "employees";
    }


    //Override methods;
    @Override
    public ObservableList getDataset(RawData data) {
        ObservableList<Employees> tableData = FXCollections.observableArrayList();
        for(int i = 0; i < data.rows.size(); i++){
            tableData.add(new Employees(data.getRow(i)));
        }

        return tableData;
    }

    @Override
    public ObservableList getNullDataset(RawData data) {
        ArrayList<String> d = new ArrayList<>(data.size);
        for(int i = 0; i < data.size;i++)
            d.add("");

        ObservableList<Employees> tableData = FXCollections.observableArrayList();
        tableData.add(new Employees());

        return tableData;
    }

    @Override
    public String getAllData() {
        System.out.println("!Dsad");
        return "'"+id+"','"+fio+"','"+phone+"','"+adress+"','"+salary+"'";
    }

    @Override
    public void addCustomData(String columnType, String data) {
        switch (columnType){
            case "id": {

                id = Integer.valueOf(data);
                break;
            }
            case "fio": {
                fio = data;
                break;
            }
            case "phone": {
                phone = data;
                break;
            }
            case "adress": {
                adress = data;
                break;
            }
            case "salary": {
                salary = Integer.parseInt(data);
                break;
            }
        }
    }

    @Override
    public void setNull() {
        super.setNull();
        fio = adress = phone = "";
        salary = 0;
    }

    @Override
    public Employees clone() throws CloneNotSupportedException {
        Employees e = new Employees();
        e.setId(id);
        e.setFio(fio);
        e.setAdress(adress);
        e.setPhone(phone);
        e.setSalary(salary);

        return e;
    }

    //javaFX
    @Override
    public TableView formTable(RawData data) {
        ObservableList<goodsType> tableData = getDataset(data);

        TableView<goodsType> table = new TableView<goodsType>();
        table.setEditable(true);


        mainTable = table;

        //Добавляем столбцы
        /*//столбец ID
        TableColumn col1 = new TableColumn("id");
        col1.setCellValueFactory(new PropertyValueFactory<goodsType, Integer>("id"));
        col1.setId("id");
        col1.setVisible(false);*/

        //столбец fio
        TableColumn col2 = new TableColumn("Фио");
        col2.setCellValueFactory(new PropertyValueFactory<Employees, String>("fio"));
        col2.setId("fio");
        //обработчик изменения
        col2.setCellFactory(TextFieldTableCell.forTableColumn());
        col2.setOnEditCommit((EventHandler<TableColumn.CellEditEvent<Employees, String>>) t -> {

                    Employees inCell =  t.getTableView().getItems().get(t.getTablePosition().getRow());
                    String columnName = t.getTableColumn().getId();

                    System.out.println("изменяем стобец "+columnName+" ,новые данные "+t.getNewValue());
                    System.out.println("текущие данные");
                    System.out.println(inCell.getAllData());
                    inCell.fio = t.getNewValue();
                    System.out.println("после изменения");
                    System.out.println(inCell.getAllData());

                    String res = dbworker.SQLRequest("UPDATE "+tableName+" set " + columnName + "='" + t.getNewValue() + "' where id = " + inCell.getId() + ";");

                    log.setText("");
                    log.setText(res);
                }
        );

        //столбец телефон
        TableColumn col3 = new TableColumn("телефон");
        col3.setCellValueFactory(new PropertyValueFactory<Employees, String>("phone"));
        col3.setId("phone");
        //обработчик изменения
        col3.setCellFactory(TextFieldTableCell.forTableColumn());
        col3.setOnEditCommit((EventHandler<TableColumn.CellEditEvent<Employees, String>>) t -> {

                    Employees inCell =  t.getTableView().getItems().get(t.getTablePosition().getRow());
                    String columnName = t.getTableColumn().getId();

                    System.out.println("изменяем стобец "+columnName+" ,новые данные "+t.getNewValue());
                    System.out.println("текущие данные");
                    System.out.println(inCell.getAllData());
                    inCell.phone = t.getNewValue();
                    System.out.println("после изменения");
                    System.out.println(inCell.getAllData());

                    String res = dbworker.SQLRequest("UPDATE "+tableName+" set " + columnName + "='" + t.getNewValue() + "' where id = " + inCell.getId() + ";");

                    log.setText("");
                    log.setText(res);
                }
        );
        //столбец адресс adress
        TableColumn col4 = new TableColumn("адрес");
        col4.setCellValueFactory(new PropertyValueFactory<Employees, String>("adress"));
        col4.setId("adress");
        //обработчик изменения
        col4.setCellFactory(TextFieldTableCell.forTableColumn());
        col4.setOnEditCommit((EventHandler<TableColumn.CellEditEvent<Employees, String>>) t -> {

                    Employees inCell =  t.getTableView().getItems().get(t.getTablePosition().getRow());
                    String columnName = t.getTableColumn().getId();

                    System.out.println("изменяем стобец "+columnName+" ,новые данные "+t.getNewValue());
                    System.out.println("текущие данные");
                    System.out.println(inCell.getAllData());
                    inCell.adress = t.getNewValue();
                    System.out.println("после изменения");
                    System.out.println(inCell.getAllData());

                    String res = dbworker.SQLRequest("UPDATE "+tableName+" set " + columnName + "='" + t.getNewValue() + "' where id = " + inCell.getId() + ";");

                    log.setText("");
                    log.setText(res);
                }
        );
        //столбец зп
        TableColumn col5 = new TableColumn("зарплата");
        col5.setCellValueFactory(new PropertyValueFactory<Employees, String>("salary"));
        col5.setId("salary");
        //обработчик изменения
        col5.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        col5.setOnEditCommit((EventHandler<TableColumn.CellEditEvent<Employees, Integer>>) t -> {

                    Employees inCell =  t.getTableView().getItems().get(t.getTablePosition().getRow());
                    String columnName = t.getTableColumn().getId();

                    System.out.println("изменяем стобец "+columnName+" ,новые данные "+t.getNewValue());
                    System.out.println("текущие данные");
                    System.out.println(inCell.getAllData());
                    inCell.salary = t.getNewValue();
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

        table.getColumns().addAll(col2,col3,col4,col5,buttonCol);
        table.setItems(tableData);

        return table;
    }

    @Override
    public TableView formAddTable(RawData data) {
        ObservableList<Employees> tableData = getNullDataset(data);

        TableView<Employees> table = new TableView<Employees>();
        table.setEditable(true);


        addTable = table;

        //Добавляем столбцы
        //столбец fio
        TableColumn col2 = new TableColumn("Фио");
        col2.setCellValueFactory(new PropertyValueFactory<Employees, String>("fio"));
        col2.setId("fio");
        //обработчик изменения
        col2.setCellFactory(TextFieldTableCell.forTableColumn());
        col2.setOnEditCommit((EventHandler<TableColumn.CellEditEvent<Employees, String>>) t -> {

                    Employees inCell =  t.getTableView().getItems().get(t.getTablePosition().getRow());
                    String columnName = t.getTableColumn().getId();

                    System.out.println("изменяем стобец "+columnName+" ,новые данные "+t.getNewValue());
                    System.out.println("текущие данные");
                    System.out.println(inCell.getAllData());
                    inCell.fio = t.getNewValue();
                    System.out.println("после изменения");
                    System.out.println(inCell.getAllData());
                }
        );

        //столбец телефон
        TableColumn col3 = new TableColumn("телефон");
        col3.setCellValueFactory(new PropertyValueFactory<Employees, String>("phone"));
        col3.setId("phone");
        //обработчик изменения
        col3.setCellFactory(TextFieldTableCell.forTableColumn());
        col3.setOnEditCommit((EventHandler<TableColumn.CellEditEvent<Employees, String>>) t -> {

                    Employees inCell =  t.getTableView().getItems().get(t.getTablePosition().getRow());
                    String columnName = t.getTableColumn().getId();

                    System.out.println("изменяем стобец "+columnName+" ,новые данные "+t.getNewValue());
                    System.out.println("текущие данные");
                    System.out.println(inCell.getAllData());
                    inCell.phone = t.getNewValue();
                    System.out.println("после изменения");
                    System.out.println(inCell.getAllData());
                }
        );
        //столбец адресс adress
        TableColumn col4 = new TableColumn("адрес");
        col4.setCellValueFactory(new PropertyValueFactory<Employees, String>("adress"));
        col4.setId("adress");
        //обработчик изменения
        col4.setCellFactory(TextFieldTableCell.forTableColumn());
        col4.setOnEditCommit((EventHandler<TableColumn.CellEditEvent<Employees, String>>) t -> {

                    Employees inCell =  t.getTableView().getItems().get(t.getTablePosition().getRow());
                    String columnName = t.getTableColumn().getId();

                    System.out.println("изменяем стобец "+columnName+" ,новые данные "+t.getNewValue());
                    System.out.println("текущие данные");
                    System.out.println(inCell.getAllData());
                    inCell.adress = t.getNewValue();
                    System.out.println("после изменения");
                    System.out.println(inCell.getAllData());
                }
        );
        //столбец зп
        TableColumn col5 = new TableColumn("зарплата");
        col5.setCellValueFactory(new PropertyValueFactory<Employees, String>("salary"));
        col5.setId("salary");
        //обработчик изменения
        col5.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        col5.setOnEditCommit((EventHandler<TableColumn.CellEditEvent<Employees, Integer>>) t -> {

                    Employees inCell =  t.getTableView().getItems().get(t.getTablePosition().getRow());
                    String columnName = t.getTableColumn().getId();

                    System.out.println("изменяем стобец "+columnName+" ,новые данные "+t.getNewValue());
                    System.out.println("текущие данные");
                    System.out.println(inCell.getAllData());
                    inCell.salary = t.getNewValue();
                    System.out.println("после изменения");
                    System.out.println(inCell.getAllData());
                }
        );


        //столбец с кнопкой
        TableColumn buttonCol = new TableColumn();
        buttonCol.setCellFactory(getButtonCell("добавить"));

        table.getColumns().addAll(col2,col3,col4,col5,buttonCol);
        table.setItems(tableData);

        return table;
    }

    //setters and getters

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }
}
