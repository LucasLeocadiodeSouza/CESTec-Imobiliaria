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
        
        System.out.println("🎯 === WEBHOOK RECEBIDO ===");
        System.out.println("📦 Evento: " + event);
        
        if ("push".equals(event) && payload.contains("refs/heads/main")) {
            try {
                System.out.println("🚀 === EXECUTANDO SCRIPT GIT PULL ===");
                
                // Execute o script externo
                Process process = Runtime.getRuntime().exec("/home/cestec/webhook.sh");
                
                // Capturar output do script
                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                StringBuilder output = new StringBuilder();
                String line;
                
                while ((line = reader.readLine()) != null) {
                    output.append(line).append("\n");
                    System.out.println("📤 SCRIPT: " + line);
                }
                
                int exitCode = process.waitFor();
                System.out.println("🏁 === SCRIPT FINALIZADO ===");
                System.out.println("🔢 Exit code: " + exitCode);
                System.out.println("📄 Output: " + output.toString());
                
                if (exitCode == 0) {
                    System.out.println("✅ SUCESSO - Script executado");
                    return ResponseEntity.ok("SUCESSO: " + output.toString());
                } else {
                    System.out.println("❌ ERRO - Script falhou");
                    return ResponseEntity.status(500).body("ERRO: " + output.toString());
                }
                
            } catch (Exception e) {
                System.out.println("💥 === ERRO EXCEÇÃO ===");
                e.printStackTrace();
                return ResponseEntity.status(500).body("EXCEÇÃO: " + e.getMessage());
            }
        }
        
        return ResponseEntity.ok("Webhook recebido - Evento: " + event);
    }
}