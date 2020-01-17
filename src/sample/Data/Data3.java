package sample.Data;

import javafx.scene.chart.XYChart;

import java.awt.*;
import java.util.ArrayList;

//данные на 3 столбца
public class Data3{
    public String id;
    private String data1;
    private String data2;
    private String data3;



    //-------------------
    private String dataE = "едит";
    private String dataD = "удалить";

    public Data3(String data1,String data2,String data3){
        this.data1 = data1;
        this.data2 = data2;
        this.data3 = data3;

        id = data1;
    }
    public Data3(ArrayList<String> o){
        data1 = o.get(0);
        data2 = o.get(1);
        data3 = o.get(2);

        id = data1;
        //System.out.println(data1+" "+data2+" "+data3);
    }



    public void setData1(String data1){
        this.data1 = data1;
    }
    public void setData2(String data2){
        this.data2 = data2;
    }
    public void setData3(String data3){
        this.data3 = data3;
    }
    public void setDataE(String dataE) {
        this.dataE = dataE;
    }
    public String getDataD() {
        return dataD;
    }


    public String getData1(){
        return data1;
    }
    public String getData2(){
        return data2;
    }
    public String getData3(){
        return data3;
    }
    public String getDataE() {
        return dataE;
    }
    public void setDataD(String dataD) {
        this.dataD = dataD;
    }


}