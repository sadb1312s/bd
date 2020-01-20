package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import sample.Data.*;
import sample.Data.tableForm.*;

import java.io.IOException;
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
    private Button newOrder;
    @FXML
    private AnchorPane mainPane;
    @FXML
    private Pane allGoods;

    @FXML
    private Pane tablePane;

    //SQL Table request
    public static final String goodsTypeReuest = "select * from goodstype;";
    public static final String producerRequest = "select * from producer;";

    public static final String goodsRequest = "select goods.id,id_type,name,price,description,id_producer,balance, goodstype.goods_type, producer.producer from goods join goodstype on goodstype.id = goods.id_type\n" +
            "join producer on producer.id = goods.id_producer;";

    public static final String employeesRequest = "select * from employees;";
    public static final String buyerRequest = "select * from buyer;";
    public static final String goodsTypesHelp = "select id, goods_type from goodstype;";
    public static final String goodsProducerHelp = "select id, producer from producer;";



    public Data currentTableObject;
    public static String currentTable;
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
            currentTableObject = new goodsType();
            currentTableObject.dbworker = dBWorker;
            currentTableObject.log = Log;
            currentTable = "goodstype";
            Data.request = goodsTypeReuest;
            tableRequest();

        });

        goodsButton.setOnAction(actionEvent -> {
            currentTableObject = new Goods();
            currentTableObject.dbworker = dBWorker;
            currentTableObject.log = Log;
            currentTable = "goods";
            Data.request = goodsRequest;
            ((Goods)currentTableObject).formHelpData();
            tableRequest();
        });

        producerButton.setOnAction(actionEvent -> {
            currentTableObject = new Producer();
            currentTableObject.dbworker = dBWorker;
            currentTableObject.log = Log;
            currentTable = "producer";
            Data.request = producerRequest;
            tableRequest();
        });

        employeesButton.setOnAction(actionEvent -> {
            currentTableObject = new Employees();
            currentTableObject.dbworker = dBWorker;
            currentTableObject.log = Log;
            currentTable = "employees";
            Data.request = employeesRequest;
            tableRequest();
        });

        buyerButton.setOnAction(actionEvent -> {
            currentTableObject = new Buyer();
            currentTableObject.dbworker = dBWorker;
            currentTableObject.log = Log;
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
        Log.setVisible(true);
        Log.setText("");
        AnchorPane anchorPane = new AnchorPane();

        TableView table = currentTableObject.formTable(data);
        table.setEditable(true);
        table.setMaxWidth(600);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.setMaxHeight(330);

        TableView addTable = currentTableObject.formAddTable(data);
        addTable.setEditable(true);
        addTable.setMaxWidth(600);
        addTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        addTable.setMaxHeight(60);


        AnchorPane.setTopAnchor(table,5.0);
        AnchorPane.setTopAnchor(addTable,330.0);
        anchorPane.getChildren().addAll(table,addTable);
        tablePane.getChildren().addAll(anchorPane,Log);
    }

}










