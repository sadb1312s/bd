package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.Pane;
import sample.Data.Data3;

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


        //column add
        ObservableList<Data3> tData = FXCollections.observableArrayList();
        //data add to column
        for(int i = 1; i < data.size(); i++){
            ArrayList<String> o = data.get(i);

            Data3 t = new Data3(o);
            tData.add(t);
        }



        TableView<Data3> tableView = new TableView<>(tData);
        tableView.setEditable(true);
        tableView.setPrefWidth(600);
        for(int i = 0; i < size + 2; i++){
            if(i < size) {
                TableColumn<Data3, Label> data1Column = new TableColumn<Data3, Label>(data.get(0).get(i));
                data1Column.setCellValueFactory(new PropertyValueFactory<Data3, Label>("data" + (i + 1)));
                tableView.getColumns().add(data1Column);
            }
            //
            if(i == size){
                //System.out.println("1");
                TableColumn<Data3, Label> data1Column = new TableColumn<Data3, Label>();
                data1Column.setCellValueFactory(new PropertyValueFactory<Data3, Label>("dataE"));
                tableView.getColumns().add(data1Column);
            }
            if(i == size + 1){
                //System.out.println("2");
                TableColumn<Data3, Label> data1Column = new TableColumn<Data3, Label>();
                data1Column.setCellValueFactory(new PropertyValueFactory<Data3, Label>("dataD"));
                tableView.getColumns().add(data1Column);
            }
        }


        tablePane.getChildren().addAll(tableView);

        /*int size = data.get(0).size();
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
        tablePane.getChildren().addAll(grid);*/

        //good
        /*ObservableList<Data> tData = FXCollections.observableArrayList();
        tData.add((new Data("1","2","3")));

        TableView<Data> tableView = new TableView<>(tData);
        tableView.setEditable(true);

        *//*TableColumn<Data,Label> data1Column = new TableColumn<Data,Label>("hello world");
        data1Column.setCellValueFactory(new PropertyValueFactory<Data,Label>("data1"));
        tableView.getColumns().add(data1Column);

        TableColumn<Data,String> data2Column = new TableColumn<Data,String>("dsadsa");
        data2Column.setCellValueFactory(new PropertyValueFactory<Data,String>("data2"));
        tableView.getColumns().add(data2Column);*//*

        TableColumn<Data,String> data1Column = new TableColumn<Data,String>("hello world");
        data1Column.setCellValueFactory(new PropertyValueFactory<Data,String>("data1"));
        data1Column.setCellFactory(TextFieldTableCell.forTableColumn());
        *//*data1Column.setOnEditCommit(dataStringCellEditEvent -> {

        });*//*

        tableView.getColumns().add(data1Column);

        tablePane.getChildren().addAll(tableView);*/
    }

}



