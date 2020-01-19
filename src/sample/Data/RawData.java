package sample.Data;

import java.util.ArrayList;

public class RawData {
    public String tableName;
    public int size;//column count

    public ArrayList<String> columnsName;
    public ArrayList<Row> rows;
    public Row currentRow;

    public RawData(String tableName, int size){
        this.tableName = tableName;
        this.size = size;

        columnsName = new ArrayList<>();
        rows = new ArrayList<>();
        currentRow = new Row();
        rows.add(currentRow);
    }

    public void addColumnName(String name){
        columnsName.add(name);
    }

    public void addData(String d){
        if(currentRow.getSize() == size){
            currentRow = new Row();
            rows.add(currentRow);
        }
        currentRow.add(d);


    }

    public void print(){
        for(String o : columnsName)
            System.out.print(o+" ");
        System.out.println();

        for(Row o : rows)
            o.print();
    }

    public ArrayList<String> getRow(int index){
        return rows.get(index).rowData;
    }

    private class Row{
        ArrayList<String> rowData;

        public Row(){
            rowData = new ArrayList<>();
        }

        public void add(String d){
            rowData.add(d);
        }

        public void print(){
            for(String o : rowData)
                System.out.print(o+" ");
            System.out.println();
        }

        public int getSize(){
            return rowData.size();
        }
    }
}


