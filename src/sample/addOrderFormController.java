package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.converter.IntegerStringConverter;
import sample.Data.*;
import sample.Data.addOrderForm.Order;
import sample.Data.addOrderForm.goodsInOrder;
import sample.Data.tableForm.Goods;

import java.sql.ResultSet;
import java.sql.SQLException;


public class addOrderFormController {


    static final String employeesHelpSQL = "select id,fio from employees;";
    static final String buyerHelpSQL = "select id,fioBuyer from buyer;";
    static final String goodsHelpSQL = "select id,name,price from goods;";

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
    private AnchorPane goodSelect;
    @FXML
    private AnchorPane userSelect;

    @FXML
    private AnchorPane allGoods;
    @FXML
    private Text TEXTallGOODS;
    @FXML
    private Button saveOrder;



    Order currentOrder;
    TableView tableView;
    Goods currentGood;
    TableView addGoodView;
    TableView allGoodsInOrder;
    public static int allDataSize;

    @FXML
    void initialize(){

        goodSelect.setVisible(false);
        goodSelect.setVisible(false);
        Text.setVisible(false);
        finalPrice.setVisible(false);
        saveOrder.setVisible(false);
        TEXTallGOODS.setVisible(false);
        allGoods.setVisible(false);
        addToOrder.setVisible(false);
        userSelect.setVisible(false);

        newOrderB.setOnAction(actionEvent -> {
            userSelect.setVisible(true);
            newOrderPane();
        });
        writeOrder.setOnAction(actionEvent -> {
            System.out.println("записываем заказ");
            String result = dbWorker.SQLRequest("insert into orders values("+currentOrder.getAllData()+");");
            System.out.println(currentOrder);
            if(result.equals("выполнено успешно")){
                writeOrder.setStyle("-fx-background-color: #00ff00");


                goodSelect.setVisible(true);
                goodSelect.setVisible(true);
                Text.setVisible(true);
                finalPrice.setVisible(true);
                saveOrder.setVisible(true);
                TEXTallGOODS.setVisible(true);
                allGoods.setVisible(true);
                addToOrder.setVisible(true);
                userSelect.setVisible(true);

                newGoodsPane();
                newOrderB.setDisable(true);


            }else {
                writeOrder.setStyle("-fx-background-color: #d9d9d9");
            }

            //newGoodsPane();


        });

        addToOrder.setOnAction(actionEvent -> {


            if(allGoodsInOrder != null) {
                /*System.out.println("!#@#@!#!@#!@#!");
                Goods g = new Goods(currentGood.getId_goods(),currentGood.getName(),currentGood.getCount(),currentGood.getPrice());
                g.idInTable = allDataSize;
                System.out.println("добавить в спико "+currentGood.getAllData());
                ObservableList<goodsInOrder> data = FXCollections.observableArrayList();
                data.add(g);
                allGoodsInOrder.setItems(data);
                allGoodsInOrder.getItems().add(g);
                allDataSize++;

                System.out.println();
                System.out.println("!#@#@!#!@#!@#!");*/

                System.out.println("!#@#@!#!@#!@#!");
                Goods g = new Goods();
                g.setId(currentGood.getId());
                g.setName(currentGood.getName());
                g.setPrice(currentGood.getPrice());
                g.Count = currentGood.Count;
                //g.idInTable = allDataSize;
                System.out.println("добавить в спико "+currentGood.getAllData());
                ObservableList<Goods> data = FXCollections.observableArrayList();
                data.add(g);
                //allGoodsInOrder.setItems(data);
                allGoodsInOrder.getItems().add(g);
                allDataSize++;

                System.out.println();
                System.out.println("!#@#@!#!@#!@#!");

            }

            finalPrice.setText(String.valueOf(currentGood.Accumsum));
            dbWorker.SQLRequest("insert into ordered values('"+currentOrder.getId()+"','"+currentGood.getId()+"','"+currentGood.getCount()+"');");

            finalPrice.setText(String.valueOf(currentGood.Accumsum));




            ResultSet rs = dbWorker.SQLRequestSelect("select balance from goods where id = "+currentGood.getId());
            try {
                rs.next();
                int balance = rs.getInt(1);
                dbWorker.SQLRequest("update goods set balance = "+(balance-currentGood.Count)+" where id = "+currentGood.getId());
            } catch (SQLException e) {
                e.printStackTrace();
            }


            currentGood.Count = 0;
            currentGood.setPrice(0);
            currentGood.setId(0);
            currentGood.setName("");

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




        mainTable.setMaxWidth(380);

        AnchorPane.setTopAnchor(mainTable,5.0);
        AnchorPane.setLeftAnchor(mainTable,5.0);
        AnchorPane.setRightAnchor(mainTable,5.0);
        AnchorPane.setBottomAnchor(mainTable,5.0);
        tableView = mainTable;

        userSelect.getChildren().add(mainTable);

    }

    public void newGoodsPane(){
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

        //таблица со всеми товарами
        TableView addGoodsTable = new TableView();
        addGoodsTable.setEditable(true);
        //столбцы
        //столбец товар
        TableColumn col1 = new TableColumn("  товар  ");
        col1.setCellValueFactory(new PropertyValueFactory<Goods, String>("name"));
        //редактирование
        col1.setCellFactory(ComboBoxTableCell.forTableColumn(currentOrder.goodsHelp));

        col1.setOnEditCommit((EventHandler<TableColumn.CellEditEvent<Goods, Goods>>) t -> {
                    Goods inCell =  t.getTableView().getItems().get(t.getTablePosition().getRow());

                    inCell.setName(t.getNewValue().getName());
                    inCell.setId(t.getNewValue().getId());
                    inCell.setPrice(t.getNewValue().getPrice());

                    addGoodsTable.refresh();

                }
        );


        //столбец цена
        TableColumn col2 = new TableColumn("    цена   ");
        col2.setCellValueFactory(new PropertyValueFactory<Goods, Integer>("price"));

        //количество товаров
        TableColumn col3 = new TableColumn("     Количество     ");
        col3.setCellValueFactory(new PropertyValueFactory<Goods, Integer>("Count"));
        //редактирование
        col3.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        col3.setOnEditCommit((EventHandler<TableColumn.CellEditEvent<Goods, Integer>>) t -> {

                    Goods inCell =  t.getTableView().getItems().get(t.getTablePosition().getRow());
                    inCell.Count = (t.getNewValue());
                    System.out.println("новые данные = "+inCell.getAllData());

                    ResultSet rs = dbWorker.SQLRequestSelect("select id_type from goods where id = "+inCell.getId()+";");
                    try {
                        rs.next();
                        String id = rs.getString(1);
                        System.out.println("---> id скидки"+id);

                        rs = dbWorker.SQLRequestSelect("select discount from goodstype where id = "+id+";");
                        rs.next();
                        String dis = rs.getString(1);
                        System.out.println("---> скидка "+dis);
                        inCell.setPrice(inCell.getPrice() - inCell.getPrice()/Integer.parseInt(dis));

                        int all = inCell.getPrice()  * inCell.getCount();
                        System.out.println("ВСЕГО ЦЕНА = "+all);
                        currentOrder.finalPrice = String.valueOf(all);
                        int accumPrice = 0;
                        try {
                            accumPrice = Integer.parseInt(finalPrice.getText());
                        }catch (Exception e){}

                        all +=accumPrice;


                        currentGood = inCell;
                        currentGood.Accumsum = all;




                    } catch (SQLException e) {
                        e.printStackTrace();
                    }


                }
        );




        AnchorPane.setTopAnchor(addGoodsTable,1.0);
        AnchorPane.setBottomAnchor(addGoodsTable,1.0);
        AnchorPane.setLeftAnchor(addGoodsTable,1.0);
        AnchorPane.setRightAnchor(addGoodsTable,1.0);
        addGoodsTable.getColumns().addAll(col1,col2,col3);
        goodSelect.getChildren().add(addGoodsTable);


        addGoodView = addGoodsTable;
        Goods g = new Goods();
        ObservableList<Goods> NullData = g.getNullDataset(new RawData("",3));
        addGoodsTable.setItems(NullData);


    }

    public int getX(){
        return x;
    }

    //засунуть кнопку в ячейку таблицы, не пытаться понять как это работает
    public Callback<TableColumn<Goods, String>, TableCell<Goods, String>> getButtonCell(String name){

        //

        Callback<TableColumn<Goods,String>, TableCell<Goods, String>> cellFactory
                = //
                new Callback<TableColumn<Goods, String>, TableCell<Goods, String>>() {
                    @Override
                    public TableCell call(final TableColumn<Goods, String> param) {
                        final TableCell<Goods, String> cell = new TableCell<Goods, String>() {

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
                                        Goods g =  getTableView().getItems().get(getIndex());

                                        ResultSet rs = dbWorker.SQLRequestSelect("select balance from goods where id = "+g.getId());
                                        try {
                                            rs.next();
                                            int balance = rs.getInt(1);
                                            dbWorker.SQLRequest("update goods set balance = "+(balance+g.Count)+" where id = "+g.getId());
                                        } catch (SQLException e) {
                                            e.printStackTrace();
                                        }

                                        System.out.println(g.getAllData());
                                        //System.out.println("удаляем id "+id);

                                        int DelSum = g.getPrice()*g.getCount();
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


