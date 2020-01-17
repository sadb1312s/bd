package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.Pane;
import javafx.util.Callback;
import sample.Data.Data3;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


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
    private Pane tablePane;


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
            TableView data = dBWorker.sendSQLselectAllRequest(currentTable);
            addGrid(data);


        });
        goodsButton.setOnAction(actionEvent -> {
            currentTable = "goods";
            TableView  data = dBWorker.sendSQLselectAllRequest("goods");
            addGrid(data);
        });

    }
    public void buttonActivate(boolean isActive){
        typeButtun.setDisable(isActive);
        //producerButton.setDisable(isActive);
        goodsButton.setDisable(isActive);
        /*orderedButton.setDisable(isActive);
        ordersButton.setDisable(isActive);
        employeesButton.setDisable(isActive);
        buyerButton.setDisable(isActive);
        deliveryButton.setDisable(isActive);
        supplierinvoicesButton.setDisable(isActive);
        goodsreceiptButton.setDisable(isActive);
        suppliersButton.setDisable(isActive);*/
    }
    public void addGrid(TableView data){
        tablePane.getChildren().clear();
        tablePane.getChildren().addAll(data);
    }



}



