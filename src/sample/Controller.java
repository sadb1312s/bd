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



        ObservableList<Data3> tData = FXCollections.observableArrayList();
        //добавление данных в таблицу
        for(int i = 1; i < data.size(); i++){
            ArrayList<String> o = data.get(i);
            Data3 t = new Data3(o);
            tData.add(t);
        }


        //column add
        TableView<Data3> tableView = new TableView<>(tData);
        tableView.setEditable(true);
        tableView.setPrefWidth(600);
        for(int i = 1; i < size + 2; i++){
            TableColumn<Data3, String> dataColumn;
            //данные
            if(i < size) {
                dataColumn = new TableColumn<Data3, String>(data.get(0).get(i));
                dataColumn.setCellValueFactory(new PropertyValueFactory<Data3, String>("data" + (i + 1)));
                dataColumn.setCellFactory(TextFieldTableCell.forTableColumn());

                dataColumn.setOnEditCommit(dataStringCellEditEvent -> {

                });

                tableView.getColumns().add(dataColumn);

            }
            //служебные столбцы редактировать и удалить
            if(i == size){
                dataColumn = new TableColumn<Data3, String>();
                //dataColumn.setCellValueFactory(new PropertyValueFactory<Data3, String>("dataE"));
                dataColumn.setCellFactory(getButton("изменить",1,1));
                tableView.getColumns().add(dataColumn);
            }
            if(i == size + 1){
                dataColumn = new TableColumn<Data3, String>();
                //dataColumn.setCellValueFactory(new PropertyValueFactory<Data3, String>("dataD"));
                dataColumn.setCellFactory(getButton("удалить",2,2));
                tableView.getColumns().add(dataColumn);
            }
        }


        tablePane.getChildren().addAll(tableView);

    }

    //засунуть кнопку в ячейку таблицы, не пытаться понять как это работает
    public Callback<TableColumn<Data3, String>, TableCell<Data3, String>> getButton(String name,int id,int type){ //type = 1 едит, 2 удалить
        Callback<TableColumn<Data3, String>, TableCell<Data3, String>> cellFactory
                = //
                new Callback<TableColumn<Data3, String>, TableCell<Data3, String>>() {
                    @Override
                    public TableCell call(final TableColumn<Data3, String> param) {
                        final TableCell<Data3, String> cell = new TableCell<Data3, String>() {

                            final Button btn = new Button(name);

                            @Override
                            public void updateItem(String item, boolean empty) {
                                super.updateItem(item, empty);
                                if (empty) {
                                    setGraphic(null);
                                    setText(null);
                                } else {
                                    btn.setOnAction(event -> {
                                        Data3 cellData3 = getTableView().getItems().get(getIndex());
                                        System.out.println("нажали кнопку id : "+cellData3.id);
                                        if(type == 1){
                                            System.out.println("нужно отредактировать");
                                        }
                                        if(type == 2){
                                            System.out.println("нужно удалить "+cellData3.id);
                                            System.out.println(dBWorker);
                                            dBWorker.SQLRequest("delete from goodstype where id = "+cellData3.id+";");
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



