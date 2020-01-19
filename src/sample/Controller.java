package sample;

import com.sun.javafx.tk.TKScenePaintListener;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;
import sample.Data.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;


public class Controller {

    @FXML
    private Button connectButton;
    @FXML
    private Button typeButtun;
    @FXML
    private Button producerButton;
    @FXML
    private Button goodsButton;
    @FXML
    private Button orderedButton;
    @FXML
    private Button ordersButton;
    @FXML
    private Button employeesButton;
    @FXML
    private Button buyerButton;
    @FXML
    private Button deliveryButton;
    @FXML
    private Button supplierinvoicesButton;
    @FXML
    private Button goodsreceiptButton;
    @FXML
    private Button suppliersButton;
    @FXML
    private TextArea Log;
    @FXML
    private Button newOrder;
    @FXML
    private AnchorPane mainPane;
    @FXML
    private Pane allGoods;

    @FXML
    private Pane tablePane;



    static final String goodsTypeReuest = "select * from goodstype;";
    static final String producerRequest = "select * from producer;";

    static final String goodsRequest = "select goods.id,id_type,name,price,description,id_producer,balance, goodstype.goods_type, producer.producer from goods join goodstype on goodstype.id = goods.id_type\n" +
            "join producer on producer.id = goods.id_producer;";

    static final String employeesRequest = "select * from employees;";
    static final String buyerRequest = "select * from buyer;";
    static final String goodsTypesHelp = "select id, goods_type from goodstype;";
    static final String goodsProducerHelp = "select id, producer from producer;";



    public static String currentTable;
    public static ArrayList<Help> helpData1;
    public static ArrayList<Help> helpData2;
    myDBworker dBWorker;

    public void initialize() {
        newOrder.setDisable(true);
        dBWorker = new myDBworker();

        connectButton.setOnAction(actionEvent -> {
            try {
                if(!dBWorker.isConnected) {
                    System.out.println("пытаемся подключиться");
                    dBWorker.connect();
                    connectButton.setText("отключиться от бд");
                    connectButton.setStyle("-fx-background-color: #00ff00");
                    System.out.println("подключились");
                    dBWorker.isConnected = true;
                    buttonActivate(false);
                    newOrder.setDisable(false);

                }else {
                    System.out.println("пытаемся отключиться");
                    dBWorker.disConnect();
                    connectButton.setText("подключиться с бд");
                    connectButton.setStyle("-fx-background-color: #d9d9d9");
                    System.out.println("отключились");
                    dBWorker.isConnected = false;
                    buttonActivate(true);
                    newOrder.setDisable(true);
                }

            } catch (SQLException e) {
                System.out.println("ошибка");
                e.printStackTrace();
            }

        });

        typeButtun.setOnAction(actionEvent -> {
            currentTable = "goodstype";
            Data.request = goodsTypeReuest;
            tableRequest();

        });
        goodsButton.setOnAction(actionEvent -> {
            currentTable = "goods";
            Data.request = goodsRequest;
            tableRequest();
        });
        producerButton.setOnAction(actionEvent -> {
            currentTable = "producer";
            Data.request = producerRequest;
            tableRequest();
        });
        employeesButton.setOnAction(actionEvent -> {
            currentTable = "employees";
            Data.request = employeesRequest;
            tableRequest();
        });
        buyerButton.setOnAction(actionEvent -> {
            currentTable = "buyer";
            Data.request = buyerRequest;
            tableRequest();
        });

        newOrder.setOnAction(actionEvent -> {
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("addOrderForm.fxml"));
                    Parent pane = fxmlLoader.load();

                    addOrderFormController addController = fxmlLoader.getController();
                    addController.primaryMainPain = mainPane;
                    addController.dbWorker = dBWorker;


                    //current controller


                    FXMLLoader loader2 = new FXMLLoader(getClass().getResource("sample.fxml"));
                    Parent root = loader2.load();
                    Controller currentContoller = loader2.getController();


                    addController.mainController = currentContoller;

                    Stage s = new Stage();
                    s.setScene(new Scene(pane));
                    s.show();


                } catch (IOException e) {
                    e.printStackTrace();
                }


        });

    }

    //for table goods
    public RawData[] helpRequest(){
        RawData data1help = dBWorker.sendSQLselectAllRequest(goodsTypesHelp);
        RawData data2Help = dBWorker.sendSQLselectAllRequest(goodsProducerHelp);

        RawData data[] = {data1help,data2Help};

        return data;
    }

    public void tableRequest(){
        if(currentTable.equals("goods")) {
            RawData helpData[] = helpRequest();
            for (RawData o : helpData)
                o.print();

            setHelpData(helpData);
            System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!");
        }
        RawData  data = dBWorker.sendSQLselectAllRequest(Data.request);
        addGrid(data);
    }

    public void setHelpData(RawData data[]){
        helpData1 = new ArrayList<>();
        helpData2 = new ArrayList<>();
        System.out.println("формируем вспомогательные данные");

        for(int i = 0; i < data[0].rows.size(); i++){
            String id = data[0].getRow(i).get(0);
            String name = data[0].getRow(i).get(1);
            System.out.println(id+" "+name);
            helpData1.add(new Help(id,name));
        }

        System.out.println();
        for(int i = 0; i < data[1].rows.size(); i++){
            String id = data[1].getRow(i).get(0);
            String name = data[1].getRow(i).get(1);
            System.out.println(id+" "+name);
            helpData2.add(new Help(id,name));
        }
    }

    public void currentUpdate(){
        RawData  data = dBWorker.sendSQLselectAllRequest(Data.request);
        addGrid(data);
    }

    public void buttonActivate(boolean isActive){
        typeButtun.setDisable(isActive);
        producerButton.setDisable(isActive);
        goodsButton.setDisable(isActive);
        employeesButton.setDisable(isActive);
        buyerButton.setDisable(isActive);
        /*orderedButton.setDisable(isActive);
        ordersButton.setDisable(isActive);
        deliveryButton.setDisable(isActive);
        supplierinvoicesButton.setDisable(isActive);
        goodsreceiptButton.setDisable(isActive);
        suppliersButton.setDisable(isActive);*/
    }

    public void addGrid(RawData data){
        tablePane.getChildren().clear();
        Log.setText("");
        AnchorPane anchorPane = new AnchorPane();

        Data dataWorker = getNeedObject();

        System.out.println("!!"+data);
        ObservableList<Data> tableData = dataWorker.getDataset(data);

        TableView<Data> table = new TableView<Data>();
        table.setEditable(true);
        table.setMaxWidth(600);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.setMaxHeight(300);

        TableView<Data> tableAdd = new TableView<>();
        tableAdd.setEditable(true);
        tableAdd.setMaxWidth(600);
        tableAdd.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tableAdd.setMaxHeight(80);


        for(String o : data.columnsName){
            if(o.equals("finalPrice"))
                continue;
            //данные в таблицу
            //----------------
            TableColumn col = null;
            String nameOfRussian = dataWorker.perevod(o);
            //простые столбцы
            //for data

            if(!currentTable.equals("goods")||(!o.equals("goods_type")&&!o.equals("producer"))){
                col = new TableColumn(nameOfRussian);
                col.setCellValueFactory(new PropertyValueFactory<Data, String>(o));
                col.setId(o);

                //обработчик изменения данных в ячейке для простых данны
                if(!o.contains("id_")) {
                    col.setCellFactory(TextFieldTableCell.forTableColumn());
                    col.setOnEditCommit((EventHandler<TableColumn.CellEditEvent<Data, String>>) t -> {
                                String id = ((Data) t.getTableView().getItems().get(t.getTablePosition().getRow())).getId();
                                System.out.println("id = " + id);

                                int row = t.getTablePosition().getRow();
                                int column = t.getTablePosition().getColumn();
                                System.out.println("изменяем");
                                System.out.println(row + " " + column);

                                String columnName = t.getTableColumn().getId();
                                System.out.println(columnName);

                                dBWorker.SQLRequest("UPDATE " + currentTable + " set " + columnName + "='" + t.getNewValue() + "' where id = " + id + ";");

                                currentUpdate();
                        }
                    );
                }
            }

            //сложный столбцы
            if(o.equals("goods_type")&&currentTable.equals("goods")){

                col = new TableColumn(nameOfRussian);
                col.setCellValueFactory(new PropertyValueFactory<Data, String>(o));
                col.setCellFactory(TextFieldTableCell.forTableColumn());
                col.setId(o);

                ObservableList<Help> list = FXCollections.observableArrayList();
                for(Help k : helpData1){
                    list.add(k);
                }

                col.setCellFactory(ComboBoxTableCell.forTableColumn(list));

                col.setOnEditCommit((EventHandler<TableColumn.CellEditEvent<Data, Help>>) t -> {
                        TablePosition<Data, Help> pos = t.getTablePosition();

                        Help newData = t.getNewValue();

                        int row = pos.getRow();
                        Data person = t.getTableView().getItems().get(row);

                        System.out.println(newData.name);

                        person.addCustomData("goods_type",newData.name);
                        person.addCustomData("id_type",newData.id);
                        System.out.println(":!!--->>> "+person.getAllData());

                        String columnName = "id_type";


                        dBWorker.SQLRequest("UPDATE " + currentTable + " set " + columnName + "='" + newData.id + "' where id = " + person.getId() + ";");
                        currentUpdate();

                    }
                );

            }

            if(o.equals("producer")&&currentTable.equals("goods")){

                col = new TableColumn(nameOfRussian);
                col.setCellValueFactory(new PropertyValueFactory<Data, String>(o));
                col.setCellFactory(TextFieldTableCell.forTableColumn());
                col.setId(o);

                ObservableList<Help> list = FXCollections.observableArrayList();
                for(Help k : helpData2){
                    list.add(k);
                }

                col.setCellFactory(ComboBoxTableCell.forTableColumn(list));

                col.setOnEditCommit((EventHandler<TableColumn.CellEditEvent<Data, Help>>) t -> {
                            TablePosition<Data, Help> pos = t.getTablePosition();

                            Help newData = t.getNewValue();

                            int row = pos.getRow();
                            Data person = t.getTableView().getItems().get(row);

                            System.out.println(newData.name);

                            person.addCustomData("producer",newData.name);
                            person.addCustomData("id_producer",newData.id);
                            System.out.println(":!!--->>> "+person.getAllData());

                            String columnName = "id_producer";


                            dBWorker.SQLRequest("UPDATE " + currentTable + " set " + columnName + "='" + newData.id + "' where id = " + person.getId() + ";");
                            currentUpdate();

                        }
                );

            }

            //панель добавления
            //------------
            TableColumn col2 = null;

            col2 = new TableColumn(nameOfRussian);
            col2.setCellValueFactory(new PropertyValueFactory<Data, String>(o));
            //простые столбцы
            if(!currentTable.equals("goods")||(!o.equals("goods_type")&&!o.equals("producer"))) {


                if (!o.contains("id")) {
                    col2.setCellFactory(TextFieldTableCell.forTableColumn());
                }
                col2.setId(o);

                //панель добавления

                col2.setOnEditCommit((EventHandler<TableColumn.CellEditEvent<Data, String>>) t -> {
                    System.out.println("добавить запись "+t.getNewValue());
                    String column = t.getTableColumn().getId();
                    System.out.println("->>> column =  " + column);
                    int rowNun = 0;


                    System.out.println("строка = "+rowNun);
                    Data d = t.getTableView().getItems().get(rowNun);
                    d.addCustomData(column,t.getNewValue());

                    if (!column.contains("id_")) {
                        System.out.println("нужно добавить id");
                        int id = dBWorker.getMaxId();
                        ((Data) t.getTableView().getItems().get(t.getTablePosition().getRow())).setId(id + "");
                    }

                    System.out.println(d.getAllData());


                });
            }

            //сложные столбцы
            if(o.equals("goods_type")&&currentTable.equals("goods")){

                System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                col2 = new TableColumn(nameOfRussian);
                col2.setCellValueFactory(new PropertyValueFactory<Goods, String>(o));
                col2.setCellFactory(TextFieldTableCell.forTableColumn());
                col2.setId(o);

                ObservableList<Help> list = FXCollections.observableArrayList();
                for(Help k : helpData1){
                    list.add(k);
                }

                col2.setCellFactory(ComboBoxTableCell.forTableColumn(list));

                col2.setOnEditCommit((EventHandler<TableColumn.CellEditEvent<Data, Help>>) t -> {
                            TablePosition<Data, Help> pos = t.getTablePosition();

                            Help newData = t.getNewValue();

                            int row = pos.getRow();
                            Data person = t.getTableView().getItems().get(row);

                            System.out.println(newData.name);

                            person.addCustomData("goods_type",newData.name);
                            person.addCustomData("id_type",newData.id);
                            System.out.println(":!!--->>> "+person.getAllData());

                            String columnName = "id_type";


                        }
                );

            }

            if(o.equals("producer")&&currentTable.equals("goods")){

                col2 = new TableColumn(nameOfRussian);
                col2.setCellValueFactory(new PropertyValueFactory<Data, String>(o));
                col2.setCellFactory(TextFieldTableCell.forTableColumn());
                col2.setId(o);

                ObservableList<Help> list = FXCollections.observableArrayList();
                for(Help k : helpData2){
                    list.add(k);
                }

                col2.setCellFactory(ComboBoxTableCell.forTableColumn(list));

                col2.setOnEditCommit((EventHandler<TableColumn.CellEditEvent<Data, Help>>) t -> {
                            TablePosition<Data, Help> pos = t.getTablePosition();

                            Help newData = t.getNewValue();

                            int row = pos.getRow();
                            Data person = t.getTableView().getItems().get(row);

                            System.out.println(newData.name);

                            person.addCustomData("producer",newData.name);
                            person.addCustomData("id_producer",newData.id);
                            System.out.println(":!!--->>> "+person.getAllData());

                            String columnName = "id_producer";


                        }
                );

            }


            if(nameOfRussian.contains("id")){
                col.setVisible(false);
                col2.setVisible(false);
            }

            table.getColumns().addAll(col);
            tableAdd.getColumns().addAll(col2);
        }
        //добавить кнопку удаления
        TableColumn del = new TableColumn();
        del.setCellFactory(getButtonCell("удалить"));
        table.getColumns().addAll(del);

        //кнопка добавить
        TableColumn del2 = new TableColumn();
        del2.setCellFactory(getButtonCell("добавить"));
        tableAdd.getColumns().addAll(del2);


        //пустые ячейки для поанели добавления
        ObservableList<Data> NullData = dataWorker.getNullDataset(data);
        tableAdd.setItems(NullData);


        table.setItems(tableData);

        //add Pane

        AnchorPane.setTopAnchor(table,5.0);
        AnchorPane.setTopAnchor(tableAdd,310.0);



        if(data.tableName.contains("java.sql.SQLException:")){
            System.out.println("ошибка = "+data.tableName);
            Log.setText(data.tableName);
        }else {
            Log.setText("выполнено успешно");
        }
        Log.setVisible(true);
        anchorPane.getChildren().addAll(table,tableAdd);
        tablePane.getChildren().addAll(anchorPane,Log);


    }

    public Data getNeedObject(){
        switch (currentTable){
            case "goodstype":
                return new goodsType();
            case "goods" :
                return new Goods();
            case "producer" :
                return new Producer();
            case "employees" :
                return new Employees();
            case "buyer" :
                return new Buyer();
        }
        return null;

    }

    //засунуть кнопку в ячейку таблицы, не пытаться понять как это работает
    public Callback<TableColumn<Data, String>, TableCell<Data, String>> getButtonCell(String name){//
        Callback<TableColumn<Data,String>, TableCell<Data, String>> cellFactory
                = //
                new Callback<TableColumn<Data, String>, TableCell<Data, String>>() {
                    @Override
                    public TableCell call(final TableColumn<Data, String> param) {
                        final TableCell<Data, String> cell = new TableCell<Data, String>() {

                            final Button btn = new Button(name);
                            @Override
                            public void updateItem(String item, boolean empty) {
                                super.updateItem(item, empty);
                                if (empty) {
                                    setGraphic(null);
                                    setText(null);
                                } else {

                                    if(name.equals("удалить")){
                                        btn.setId("DELETE");
                                    }

                                    if(name.equals("добавить")){
                                        btn.setId("INSERT");
                                    }

                                    btn.setOnAction(event -> {
                                        System.out.println("нажали кнопку "+btn.getId());
                                        //System.out.println("->"+getTableView().getItems().get(0).getAllData());

                                        if(btn.getId().equals("INSERT")){
                                            System.out.println("Вставить !!!");

                                            String dataS = getTableView().getItems().get(0).getAllData();
                                            System.out.println(""+getTableView().getItems().get(0).getAllData());

                                            System.out.println("добавляем ->> "+dataS);

                                            String res = dBWorker.SQLRequest("insert into "+currentTable+" values("+dataS+");");
                                            currentUpdate();
                                            Log.setText(res);
                                        }

                                        if(btn.getId().equals("DELETE")){

                                            String id = getTableView().getItems().get(getIndex()).getId();
                                            System.out.println("удаляем id "+id);
                                            String res = dBWorker.SQLRequest("delete from "+currentTable+" where id = "+id+";");
                                            currentUpdate();
                                            Log.setText(res);
                                        }

                                    });
                                    setGraphic(btn);
                                    setText(null);
                                }
                            }
                        };
                        return cell;
                    }
                };
        return cellFactory;
    }



}



class Help{
    public String id;
    public String name;

    public Help(String id,String name){
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}






