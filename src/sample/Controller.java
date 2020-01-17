package sample;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;

import java.sql.SQLException;
import java.util.ArrayList;


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


    myDBworker dBWorker;

    public void initialize() {
        myDBworker dBWorker = new myDBworker();


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
            ArrayList<ArrayList<String>> data = dBWorker.sendSQLselectAllRequest("goodstype");
            addGrid(data);


        });
        goodsButton.setOnAction(actionEvent -> {
            ArrayList<ArrayList<String>> data = dBWorker.sendSQLselectAllRequest("goods");
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
    public void addGrid(ArrayList<ArrayList<String>> data){

        tablePane.getChildren().clear();

        int size = data.get(0).size();
        System.out.println("размер таблицы = "+size);
        GridPane grid = new GridPane();
        grid.setMaxWidth(600);
        for(int i = 0; i < data.size() + 2; i++){
            grid.getColumnConstraints().add(new ColumnConstraints());
        }

        int i = 0;
        for(String o : data.get(0)){
            if(data.get(0).indexOf(o) != 0) {
                String n = o;


                if(n.contains("id_type")){
                    n = "тип";
                }

                if(n.contains("id_producer")){
                    n = "производитель";
                }

                TextField t = new TextField(n);
                t.setEditable(false);

                grid.setColumnIndex(t, i);
                grid.getChildren().addAll(t);
                i++;
            }

        }

        data.remove(data.get(0));
        int j = 1;//строка
        for(ArrayList<String> o : data){
            grid.getRowConstraints().add(new RowConstraints());
            int k = 0;//столбец
            for(int p = 1; p < o.size(); p++){
                TextField t = new TextField(o.get(p));
                grid.add(t, k, j);
                k++;
            }
            j++;
            //System.out.println();
        }


        grid.setVisible(true);
        tablePane.getChildren().addAll(grid);


    }

}

