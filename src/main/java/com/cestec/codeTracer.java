/*
 * Dev: Lucas Leocadio de Souza
 * Data: 19/07/2025
 * 
 *  Pesquisar por: *parametro obrigatório, é a palavra que o codeTracer vai buscar entre os aquivos;
 *  Tipo Arquivo:  *parametro obrigatório, é o tipo de arquivo que o codeTracer vai procurar, Exemplo: html, js ou java,
 *                                         cada extensao fica em uma pasta especifica entao coloquei para ser obrigatório,
 *                                         mais para frente irei atualizar para colocar pesquisa por todos, mas por enquanto sera
 *                                         implantado dessa forma ;
 *  Local: parametro nao obrigatório, se deixado vazio ele procura por todos os arquivos em todos os diretorio pelas pastas
 *                                    especificas do tipo de arquivo informado anteriormente;
 *  Saída: *parametro obrigatório, nome do arquivo que sera criado apos a execucao da busca do codeTracer;
 * 
 * 
 */

package com.cestec;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
import org.jline.utils.InfoCmp.Capability;
import java.util.List;

public class codeTracer {
    public static void main(String[] args) throws Exception {
        Terminal terminal  = TerminalBuilder.terminal();
        LineReader reader = LineReaderBuilder.builder().terminal(terminal).build();
        
        terminal.puts(Capability.clear_screen);
        terminal.flush();

        terminal.writer().println("+_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_+");
        terminal.writer().println("|  Pesquisar por:                                                         |");
        terminal.writer().println("|  Tipo arquivo:                                                          |");
        terminal.writer().println("|  Local:                                                                 |");
        terminal.writer().println("|  Saída:                                                                 |");
        terminal.writer().println("+_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_+");
        terminal.flush();

        int[][] campos = {
            {2, 19},  // Pesquisar por
            {3, 18},  // Tipo arquivo
            {4, 11},  // Local
            {5, 11}   // Saída
        };

        String[] respostas = new String[4];

        for (int i = 0; i < campos.length; i++) {
            terminal.writer().print(String.format("\033[%d;%dH", campos[i][0], campos[i][1]));
            terminal.flush();

            String input = reader.readLine();

            // if(campos[i][0] == 2 && campos[i][1] == 19 ||
            //    campos[i][0] == 3 && campos[i][1] == 18 ||
            //    campos[i][0] == 5 && campos[i][1] == 11){
                
            //    if(input.isEmpty()){
            //       terminal.writer().println("É preciso preencher o campo para continuar");
            //       terminal.flush();
            //       i--;
            //       break;
            //     }
            // }

            respostas[i] = input;
        }

        String chave    = respostas[0].trim();
        String type     = respostas[1].toLowerCase();
        String path     = respostas[2];
        String filename = respostas[3];

        List<String> resultTracer = new ArrayList<>();

        String caminhofixo = "";
        if(type.equals("html")){
            caminhofixo = "C:/CESTec/CESTEC Imobiliaria SB/cestec/src/main/resources/templates";
        }else if(type.equals("js")){
             caminhofixo = "C:/CESTec/CESTEC Imobiliaria SB/cestec/src/main/resources/static/js";
        }else if(type.equals("java")){
             caminhofixo = "C:/CESTec/CESTEC Imobiliaria SB/cestec/src/main/java/com/cestec/cestec";
        }

        File pasta;
        if(path == ""){
            pasta = Paths.get(caminhofixo).toFile();
        }else{
            pasta = Paths.get(caminhofixo, path.split("/")).toFile();
        }

        if(pasta.isDirectory()){
            File[] arquivos = pasta.listFiles();

            for(File arquivo : arquivos){

                boolean validatype = type.equals(fileExtension(arquivo.getName()));

                if(arquivo.isFile() && validatype){
                    resultTracer = lerArquivo(arquivo, chave, resultTracer);
                }

                if(arquivo.isDirectory()){
                    File pastadir = Paths.get(arquivo.getAbsolutePath()).toFile();


                }
            }
        }
        criarArquivoCodeTracer(resultTracer, filename);

        terminal.writer().println("Arquivo Criado e mandado para o repositorio de saída: C:/CESTec/CESTEC Imobiliaria SB/" + filename);
        terminal.flush();
    }

    public static File[] getArquivos(File pasta){
        if(pasta.isDirectory()){
            return pasta.listFiles();
        }

        return null;
    }


    public static List<String> lerArquivo(File arquivo, String chave, List<String> resultTracer){
        try (RandomAccessFile acessfile = new RandomAccessFile(arquivo, "rw")){                        
            String sline;                       
            int linha = 1;
            while((sline = acessfile.readLine()) != null) {
                if(sline.contains(chave)){
                    String resultbusca = arquivo.getAbsolutePath() + " --> linha: "+ String.valueOf(linha) + "//: " + sline;
                    resultTracer.add(resultbusca);
                }
                linha ++;
            }

            return resultTracer;
        } catch (Exception e) {
            System.err.println("Erro ao abrir o arquivo: " + e.getMessage());
            return null;
        }
    }

    public static void criarArquivoCodeTracer(List<String> list, String filename){
        Path arquivo = Paths.get("C:/CESTec/CESTEC Imobiliaria SB/" + filename);

        try {
            Files.write(arquivo, list);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String fileExtension(String path){
        int lastIndexPoint = path.lastIndexOf(".");
        return path.substring(lastIndexPoint + 1).toLowerCase();
    }

    public static String executeCommand(String command) throws IOException {
        ProcessBuilder pb;
        String os = System.getProperty("os.name").toLowerCase();
        
        if (os.contains("win")) {
            // Windows - usa cmd.exe
            pb = new ProcessBuilder("cmd.exe", "/c", command);
        } else {
            // Unix/Linux/Mac
            pb = new ProcessBuilder("bash", "-c", command);
        }
        
        pb.redirectErrorStream(true);
        Process process = pb.start();
        
        StringBuilder output = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(
            new InputStreamReader(process.getInputStream()))) {
            
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }
        }
        
        return output.toString();
    }
}
