package sample.Data;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class Employees extends Data{
    public String id, fio, phone,adress,salary;


    public Employees(String tableName, ArrayList<String> data) {
        super(tableName);

        if(data!=null) {
            id = data.get(0);
            fio = data.get(1);
            phone = data.get(2);
            adress = data.get(3);
            salary = data.get(4);
        }
    }
    public Employees() {
        super("");
    }

    @Override
    public ObservableList getDataset(RawData data) {
        ObservableList<Employees> tableData = FXCollections.observableArrayList();
        for(int i = 0; i < data.rows.size(); i++){
            tableData.add(new Employees(data.tableName, data.getRow(i)));
        }

        return tableData;
    }

    @Override
    public ObservableList getNullDataset(RawData data) {
        ArrayList<String> d = new ArrayList<>(data.size);
        for(int i = 0; i < data.size;i++)
            d.add("");

        ObservableList<Employees> tableData = FXCollections.observableArrayList();
        tableData.add(new Employees(data.tableName,d));

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

                id = data;
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
                salary = data;
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

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }
}
