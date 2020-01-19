package sample;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.util.Callback;
import sample.Data.*;

import java.sql.SQLException;


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
    myDBworker dBWorker;

    public void initialize() {
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

                }else {
                    System.out.println("пытаемся отключиться");
                    dBWorker.disConnect();
                    connectButton.setText("подключиться с бд");
                    connectButton.setStyle("-fx-background-color: #d9d9d9");
                    System.out.println("отключились");
                    dBWorker.isConnected = false;
                    buttonActivate(true);
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

    }

    public void tableRequest(){
        RawData  data = dBWorker.sendSQLselectAllRequest(Data.request);
        addGrid(data);
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
            TableColumn col = null;
            String nameOfRussian = dataWorker.perevod(o);
            if(!o.contains("id_")) {
                //for data

                col = new TableColumn(nameOfRussian);
                col.setCellValueFactory(new PropertyValueFactory<Data, String>(o));
                col.setCellFactory(TextFieldTableCell.forTableColumn());
                col.setId(o);
            }
            else {
                ObservableList<Gender> genderList = FXCollections.observableArrayList(//
                        Gender.values());

                col = new TableColumn(nameOfRussian);
                col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Data, Gender>, ObservableValue<Gender>>() {

                    @Override
                    public ObservableValue<Gender> call(TableColumn.CellDataFeatures<Data, Gender> param) {
                        Data person = param.getValue();
                        // F,M
                        String genderCode = person.getId();
                        Gender gender = Gender.getByCode(genderCode);
                        return new SimpleObjectProperty<Gender>(gender);
                    }
                });

                col.setCellFactory(ComboBoxTableCell.forTableColumn(genderList));

                col.setOnEditCommit(cellEditEvent ->  {

                });





            }

            //for add panel
            TableColumn col2 = null;

            col2 = new TableColumn(nameOfRussian);
            col2.setCellValueFactory(new PropertyValueFactory<Data, String>(o));
            if(!o.equals("id")) {
                col2.setCellFactory(TextFieldTableCell.forTableColumn());
            }
            col2.setId(o);

            //обработчик изменения данных в ячейке
            col.setOnEditCommit((EventHandler<TableColumn.CellEditEvent<Data, String>>) t -> {

                String id = ((Data) t.getTableView().getItems().get(t.getTablePosition().getRow())).getId();
                System.out.println("id = "+id);

                int row = t.getTablePosition().getRow();
                int column = t.getTablePosition().getColumn();
                System.out.println("изменяем");
                System.out.println(row +" "+column);

                String columnName = t.getTableColumn().getId();
                System.out.println(columnName);

                dBWorker.SQLRequest("UPDATE "+currentTable+" set "+columnName+"='"+t.getNewValue()+"' where id = "+id+";");

                currentUpdate();
            }
            );


            col2.setOnEditCommit((EventHandler<TableColumn.CellEditEvent<Data, String>>) t -> {
                System.out.println("добавить запись");
                System.out.println("->>> " + t.getTableColumn().getId());
                ((Data) t.getTableView().getItems().get(t.getTablePosition().getRow())).addCustomData(t.getTableColumn().getId(),t.getNewValue());

                if(!t.equals("id")){
                    System.out.println("нужно добавить id");
                    int id = dBWorker.getMaxId();
                    ((Data) t.getTableView().getItems().get(t.getTablePosition().getRow())).setId(id+"");
                }


                }
            );


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

enum Gender {

    FEMALE("F", "Famale"), MALE("M", "Male");

    private String code;
    private String text;

    private Gender(String code, String text) {
        this.code = code;
        this.text = text;
    }

    public String getCode() {
        return code;
    }

    public String getText() {
        return text;
    }

    public static Gender getByCode(String genderCode) {
        for (Gender g : Gender.values()) {
            if (g.code.equals(genderCode)) {
                return g;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return this.text;
    }

}



