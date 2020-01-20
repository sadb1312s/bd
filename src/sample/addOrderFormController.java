package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;
import sample.Data.*;
import sample.Data.addOrderForm.Order;
import sample.Data.addOrderForm.goodsInOrder;

import java.sql.ResultSet;
import java.sql.SQLException;


public class addOrderFormController {


    static final String employeesHelpSQL = "select id,fio from employees;";
    static final String buyerHelpSQL = "select id,fioBuyer from buyer;";
    static final String goodsHelpSQL = "select id,name,price from goods;";

    //public static ArrayList<Help> employeesData1;
    //public static ArrayList<Help> buyerData2;
    //public static ArrayList<goodsInOrderHelp> anyHelpData;

    public AnchorPane primaryMainPain;
    public int x = 5;
    public myDBworker dbWorker;
    public Controller mainController;

    @FXML
    private AnchorPane MainPane;

    @FXML
    private Button newOrderB;

    @FXML
    private Button writeOrder;

    @FXML
    private TextField finalPrice;

    @FXML
    private Text Text;

    @FXML
    private Button addToOrder;

    @FXML
    private AnchorPane allGoods;
    @FXML
    private Text TEXTallGOODS;
    @FXML
    private Button saveOrder;


    Order currentOrder;
    TableView tableView;
    goodsInOrder currentGood;
    TableView addGoodView;
    TableView allGoodsInOrder;
    public static int allDataSize;

    @FXML
    void initialize(){
        newOrderB.setOnAction(actionEvent -> {
            newOrderPane();
        });
        writeOrder.setOnAction(actionEvent -> {
            System.out.println("записываем заказ");
            String result = dbWorker.SQLRequest("insert into orders values("+currentOrder.getAllData()+");");
            System.out.println(currentOrder);
            if(result.equals("выполнено успешно")){
                writeOrder.setStyle("-fx-background-color: #00ff00");
            }else {
                writeOrder.setStyle("-fx-background-color: #d9d9d9");
            }

            //newGoodsPane();


        });

        addToOrder.setOnAction(actionEvent -> {


            if(allGoodsInOrder != null) {
                System.out.println("!#@#@!#!@#!@#!");
                goodsInOrder g = new goodsInOrder(currentGood.getId_goods(),currentGood.getName(),currentGood.getCount(),currentGood.getPrice());
                g.idInTable = allDataSize;
                System.out.println("добавить в спико "+currentGood.getAllData());
                ObservableList<goodsInOrder> data = FXCollections.observableArrayList();
                data.add(g);
                //allGoodsInOrder.setItems(data);
                allGoodsInOrder.getItems().add(g);
                allDataSize++;

                System.out.println();
                System.out.println("!#@#@!#!@#!@#!");
            }

            finalPrice.setText(String.valueOf(currentGood.Accumsum));
            dbWorker.SQLRequest("insert into ordered values('"+currentOrder.getId()+"','"+currentGood.getId_goods()+"','"+currentGood.getCount()+"');");
            currentGood.setCount("");
            currentGood.setPrice("");
            currentGood.setId_goods("");
            currentGood.setName("");
            finalPrice.setText(String.valueOf(currentGood.Accumsum));



            addGoodView.refresh();
            allGoodsInOrder.refresh();

        });

        saveOrder.setOnAction(actionEvent -> {

            dbWorker.SQLRequest("UPDATE orders set finalPrice='"+finalPrice.getText()+"' where id ='"+currentOrder.getId()+"';");

            ResultSet rs = dbWorker.SQLRequestSelect("Select orderCount from buyer where id = '"+currentOrder.getId_buyer()+"';");
            try {
                rs.next();
                int countOrder = rs.getInt(1);
                countOrder+=1;
                dbWorker.SQLRequest("update buyer set orderCount ='+"+countOrder+"' where id = '"+currentOrder.getId_buyer()+"';");


            } catch (SQLException e) {
                e.printStackTrace();
            }


            rs = dbWorker.SQLRequestSelect("Select orderSum from buyer where id = '"+currentOrder.getId_buyer()+"';");
            try {
                rs.next();
                int countSum = rs.getInt(1);
                countSum+=Integer.parseInt(finalPrice.getText());
                dbWorker.SQLRequest("update buyer set orderSum ='+"+countSum+"' where id = '"+currentOrder.getId_buyer()+"';");

            } catch (SQLException e) {
                e.printStackTrace();
            }

            Stage stage = (Stage) saveOrder.getScene().getWindow();
            stage.close();
        });
    }






    public void newOrderPane(){


        System.out.println("формируем таблицу");
        Controller.currentTable = "orders";
        //формируем таблицу
        TableView<Order> mainTable;

        Order order = new Order();
        order.currentOrder = order;
        currentOrder = order;
        System.out.println(currentOrder+" "+order);
        order.dbworker = dbWorker;
        order.setHelp();
        mainTable = order.formAddTable(new RawData("",4));

        mainTable.setEditable(true);
        mainTable.setMaxHeight(65);

        RawData rawData = new RawData("",7);
        for(int i =0 ; i < 7 ;i++)
            rawData.addData("");
        ObservableList<Order> NullData = order.getNullDataset(rawData);
        mainTable.setItems(NullData);





        AnchorPane.setTopAnchor(mainTable,68.0);
        AnchorPane.setLeftAnchor(mainTable,150.0);
        AnchorPane.setRightAnchor(mainTable,150.0);
        tableView = mainTable;

        MainPane.getChildren().addAll(mainTable);
    }

    /*public void newGoodsPane(){
        TEXTallGOODS.setVisible(true);
        //таблица со всеми товарами
        TableView allGoodsTable = new TableView();
        allGoodsTable.setMaxWidth(500);
        allGoodsTable.setMaxHeight(200);
        TableColumn col1A = new TableColumn("товар");
        col1A.setCellValueFactory(new PropertyValueFactory<goodsInOrder, String>("name"));

        TableColumn col2A = new TableColumn("цена");
        col2A.setCellValueFactory(new PropertyValueFactory<goodsInOrder, String>("price"));

        TableColumn col3A = new TableColumn("Количество");
        col3A.setCellValueFactory(new PropertyValueFactory<goodsInOrder, String>("count"));

        TableColumn col34 = new TableColumn();
        col34.setCellFactory(getButtonCell("удалить"));

        allGoodsTable.getColumns().addAll(col1A,col2A,col3A,col34);

        allGoodsInOrder = allGoodsTable;


        AnchorPane.setTopAnchor(allGoodsTable,30.0);
        AnchorPane.setBottomAnchor(allGoodsTable,5.0);
        AnchorPane.setRightAnchor(allGoodsTable,5.0);
        AnchorPane.setLeftAnchor(allGoodsTable,5.0);
        allGoods.getChildren().addAll(allGoodsTable);


        //Выбор текущего товара для добавления

        addToOrder.setVisible(true);
        Text.setVisible(true);
        finalPrice.setVisible(true);
        allGoods.setVisible(true);
        saveOrder.setVisible(true);

        RawData rawData = dbWorker.sendSQLselectAllRequest(goodsHelpSQL);


        TableView goodsTable = new TableView();
        goodsTable.setEditable(true);
        goodsTable.setMaxHeight(80);
        goodsTable.setMaxWidth(300);
        addGoodView = goodsTable;

        //колонка товар(с подстановкой)
        TableColumn col1 = new TableColumn("Товар");
        col1.setId("name");
        col1.setCellValueFactory(new PropertyValueFactory<goodsInOrder, String>("name"));

        ObservableList<goodsInOrderHelp> list = FXCollections.observableArrayList();
        *//*for(goodsInOrderHelp k : anyHelpData){
            list.add(k);
        }*//*

        col1.setCellFactory(ComboBoxTableCell.forTableColumn(list));

        col1.setOnEditCommit((EventHandler<TableColumn.CellEditEvent<goodsInOrder, goodsInOrderHelp>>) t -> {
            TablePosition<goodsInOrder, goodsInOrderHelp> pos = t.getTablePosition();

                    System.out.println("-->>>> "+t.getNewValue().getClass());
                    goodsInOrderHelp newData = t.getNewValue();


                    goodsInOrder person = t.getTableView().getItems().get(0);
                    person.setId_goods(newData.getId_goods());
                    person.setName(newData.getName());
                    person.setPrice(newData.getPrice());

                    goodsTable.refresh();

                    //person.setPrice();
                    //goodsTable.refresh();

            System.out.println("новые данные = "+person.getAllData());


        }
        );
        //Цена, автоподстановка
        TableColumn col2 = new TableColumn("Цена");
        col2.setId("price");
        col2.setCellValueFactory(new PropertyValueFactory<goodsInOrder, String>("price"));

        //Количество
        TableColumn col3 = new TableColumn("количество");
        col3.setId("count");
        col3.setCellValueFactory(new PropertyValueFactory<goodsInOrder, String>("count"));
        col3.setCellFactory(TextFieldTableCell.forTableColumn());

        col3.setOnEditCommit((EventHandler<TableColumn.CellEditEvent<goodsInOrder, String>>) t -> {

                    System.out.println("-->>>> "+t.getNewValue().getClass() +t.getNewValue());
                    String newData = t.getNewValue();


                    goodsInOrder person = t.getTableView().getItems().get(0);
                    person.setCount(newData);
                    System.out.println("новые данные = "+person.getAllData());

                    ResultSet rs = dbWorker.SQLRequestSelect("select id_type from goods where id = "+person.getId_goods()+";");
                    try {
                        rs.next();
                        String id = rs.getString(1);
                        System.out.println("---> id скидки"+id);

                        rs = dbWorker.SQLRequestSelect("select discount from goodstype where id = "+id+";");
                        rs.next();
                        String dis = rs.getString(1);
                        System.out.println("---> скидка "+dis);
                        person.setPrice(String.valueOf((Integer.parseInt(person.getPrice()) - Integer.parseInt(person.getPrice())/Integer.parseInt(dis))));

                        int all = Integer.parseInt(person.getPrice())  * Integer.parseInt(person.getCount());
                        System.out.println("ВСЕГО ЦЕНА = "+all);
                        currentOrder.finalPrice = String.valueOf(all);
                        int accumPrice = 0;
                        try {
                            accumPrice = Integer.parseInt(finalPrice.getText());
                        }catch (Exception e){}

                        all +=accumPrice;



                        currentGood = person;
                        currentGood.Accumsum = all;




                    } catch (SQLException e) {
                        e.printStackTrace();
                    }


                }
        );




        *//*if(t.getTableColumn().getId().equals("count")){
            person.setCount(newData.getCount());
        }*//*



        goodsTable.getColumns().addAll(col1,col2,col3);
        goodsInOrder g = new goodsInOrder();
        ObservableList<goodsInOrder> NullData = g.getNullDataset(rawData);
        goodsTable.setItems(NullData);


        AnchorPane.setTopAnchor(goodsTable,280.0);
        AnchorPane.setLeftAnchor(goodsTable,10.0);
        MainPane.getChildren().addAll(goodsTable);




    }*/


    public int getX(){
        return x;
    }

    //засунуть кнопку в ячейку таблицы, не пытаться понять как это работает
    public Callback<TableColumn<goodsInOrder, String>, TableCell<goodsInOrder, String>> getButtonCell(String name){

        //

        Callback<TableColumn<goodsInOrder,String>, TableCell<goodsInOrder, String>> cellFactory
                = //
                new Callback<TableColumn<goodsInOrder, String>, TableCell<goodsInOrder, String>>() {
                    @Override
                    public TableCell call(final TableColumn<goodsInOrder, String> param) {
                        final TableCell<goodsInOrder, String> cell = new TableCell<goodsInOrder, String>() {

                            final Button btn = new Button(name);
                            @Override
                            public void updateItem(String item, boolean empty) {
                                super.updateItem(item, empty);
                                if (empty) {
                                    setGraphic(null);
                                    setText(null);
                                } else {

                                    btn.setId("");

                                    btn.setOnAction(event -> {
                                        //String id = getTableView().getItems().get(getIndex()).getId();
                                        goodsInOrder g =  getTableView().getItems().get(getIndex());
                                        System.out.println(g.getAllData());
                                        //System.out.println("удаляем id "+id);

                                        int DelSum = Integer.parseInt(g.getPrice())*Integer.parseInt(g.getCount());
                                        g.Accumsum-=DelSum;

                                        finalPrice.setText(String.valueOf(g.Accumsum));


                                        //String res = dbWorker.SQLRequest("delete from ordered where id_goods = "+id+";");


                                        TablePosition<goodsInOrder,String> t = allGoodsInOrder.getFocusModel().getFocusedCell();
                                        int id2 = t.getRow();


                                        allGoodsInOrder.refresh();

                                        System.out.println("---------> !!!! "+t.getRow());

                                        allGoodsInOrder.getItems().remove(getIndex());


                                        //int delSum = Integer.parseInt()
                                        //System.out.println(getTableView().getItems().get(id2).getAllData());


                                        //allGoodsInOrder.getItems().remove(0);


                                    });
                                    setGraphic(btn);
                                    setText(null);
                                }
                            }
                        };
                        cell.getTableRow();
                        return cell;
                    }
                };
        return cellFactory;
    }


}


