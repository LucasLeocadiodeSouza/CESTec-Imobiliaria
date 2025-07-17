package com.cestec.cestec.util;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class utilForm {
    
    private static List<List<List<String>>> grid = new ArrayList<>();
    private static List<List<String>> row = null;
    private static List<String> column    = null;

    public static void initGrid() {
        grid = new ArrayList<>();
    }

    public static List<List<List<String>>> criarGrid(){
        List<List<List<String>>> result = new ArrayList<>(grid);
        initGrid();

        return result;
    }

    public static void criarRow(){
        row = new ArrayList<>();
        grid.add(row);
    }
 
    public static void criarColuna(String dado){
        if (column == null) {
            column = new ArrayList<>();
        }
        column.add(dado);
        row.add(column);
        column = null;
    }

    public static String formatarDataBrasil(Date dataSql) {
        if (dataSql == null) {
            return "";
        }
        
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(dataSql);
    }

}