package com.cestec.cestec.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

@RestController
public class webhookController {
    
    @PostMapping("/webhook")
    public ResponseEntity<String> webhook(
            @RequestBody String payload,
            @RequestHeader(value = "X-GitHub-Event", required = false) String event) {
        
        System.out.println("Webhook recebido: " + event);
        System.out.println("Payload: " + payload);
        
        if ("push".equals(event) && payload.contains("refs/heads/main")) {
            try {
                System.out.println("Executando git pull...");
                
                File projectDir = new File("/home/cestec/CESTec-Imobiliaria");
                
                // Comando git pull
                ProcessBuilder processBuilder = new ProcessBuilder("git", "pull", "origin", "main");
                processBuilder.directory(projectDir);
                processBuilder.redirectErrorStream(true);
                
                Process process = processBuilder.start();
                
                // Captura a saída do comando
                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                StringBuilder output = new StringBuilder();
                String line;
                
                while ((line = reader.readLine()) != null) {
                    output.append(line).append("\n");
                    System.out.println("Git: " + line);
                }
                
                // Espera o processo terminar
                int exitCode = process.waitFor();
                System.out.println("Git pull finalizado com código: " + exitCode);
                
                if (exitCode == 0) {
                    // Se o pull foi bem-sucedido, rebuild e restart
                    rebuildAndRestart();
                    return ResponseEntity.ok("Git pull realizado com sucesso!\n" + output.toString());
                } else {
                    return ResponseEntity.status(500).body("Erro no git pull. Código: " + exitCode + "\n" + output.toString());
                }
                
            } catch (Exception e) {
                System.out.println("Erro ao executar git pull: " + e.getMessage());
                e.printStackTrace();
                return ResponseEntity.status(500).body("Erro: " + e.getMessage());
            }
        }
        
        return ResponseEntity.ok("Webhook recebido - Evento: " + event);
    }
    
    private void rebuildAndRestart() {
        try {
            System.out.println("Recompilando e reiniciando aplicação...");
             
            File projectDir = new File("/home/cestec/CESTec-Imobiliaria");
            
            ProcessBuilder buildProcess = new ProcessBuilder("mvn", "clean", "package", "-DskipTests");
            buildProcess.directory(projectDir);
            buildProcess.redirectErrorStream(true);
            
            Process build = buildProcess.start();
            
            // Ler output da compilação
            BufferedReader buildReader = new BufferedReader(new InputStreamReader(build.getInputStream()));
            String buildLine;
            while ((buildLine = buildReader.readLine()) != null) {
                System.out.println("Maven: " + buildLine);
            }
            
            int buildExitCode = build.waitFor();
            
            if (buildExitCode == 0) {
                System.out.println("Build realizado com sucesso! Reiniciando serviço...");
                
                // Reinicia o serviço systemd
                Process restartProcess = Runtime.getRuntime().exec("systemctl restart cestec");
                restartProcess.waitFor();
                
                System.out.println("Serviço reiniciado com sucesso!");
            } else {
                System.out.println("Erro no build. Código: " + buildExitCode);
            }
            
        } catch (Exception e) {
            System.out.println("Erro no rebuild: " + e.getMessage());
            e.printStackTrace();
        }
    }
}