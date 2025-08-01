/*
 *  Dev:  Lucas Leocadio de Souza
 *  Data: 12/06/2025
 *  
 *  utilForm -> classe para criacao da grid;
 * 
 *  utilForm.init(); -> chamada para criacao do ArrayList grid
 * 
 *  Formato padrão de uso ->
 *                           Envolver em um for ou while ate o tamanho de uma Query;
 *                           Chamar utilForm.criarRow() **[Tipo da row -> List<List<String>>]**, metodo nao recebe parametros, para iniciar uma Lista de Lista do tipo String que contem a lista de colunas da linha
 *                           e adicionar na list grid;
 * 
 *                           Após criado uma row, é necessario preenche-la com as colunas, usando utilForm.criarColuna("Conteudo da Coluna")  **[Tipo da coluna -> String, porem sempre que \
 *                                                                                                                                               criado em sequencia as colunas é adicionado a uma List<String> column 
 *                                                                                                                                               onde é adicionado a mesma na row]**.
 *                           O metodo criarColuna() deve receber um parametro como String;
 *                      
 *                           Apos ter inicializado a grid, criado a Row e a preenchido com as colunas é preciso retornar o objeto utilForm.criarGrid no final do metodo;
 * 
 *                           Exemplo completo ->
 *                                               utilForm.init();
 *                                               for (int i = 0; i < query.size(); i++) {
 *                                                   utilForm.criarRow();
 *                                                   utilForm.criarColuna(aplicacao.get(i).getIdeusu());
 *                                                   utilForm.criarColuna(aplicacao.get(i).getModulo().getId().toString());
 *                                                   utilForm.criarColuna(aplicacao.get(i).getModulo().getDescricao());
 *                                               }
 *                                               return utilForm.criarGrid();
 *  
 */

package com.cestec.cestec.util;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
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