package com.cestec.cestec.controller.cri;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.MediaType;
import com.cestec.cestec.model.modelUtilForm;
import com.cestec.cestec.model.cri.pcp_imovel;
import com.cestec.cestec.service.genService;
import com.cestec.cestec.service.cri.cri001s;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/cri001")
public class cri001c {
    @Autowired
    private cri001s cri001s;

    @Autowired
    private genService gen;

    public static String uploadDirectory = System.getProperty("user.dir") + "/uploadImage/imoveisImages";

    @PostMapping("/salvarImovel")
    public ResponseEntity<?> salvarImovel(@RequestBody pcp_imovel imovel, @RequestParam(value = "codprop", required = false) Integer codprop, HttpServletRequest request) {
        try {
            cri001s.salvarImovel(imovel, codprop, gen.getUserName(request));
            return ResponseEntity.ok("OK");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
            
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erro interno ao cadastrar imovel: " + e.getMessage());
        }
    }

    @PostMapping("/inativarImovel")
    public ResponseEntity<?> inativarImovel(@RequestParam(value = "codimovel", required = false) Integer codimovel, HttpServletRequest request) {
        try {
            cri001s.inativarImovel(codimovel, gen.getUserName(request));
            return ResponseEntity.ok("OK");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
            
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erro interno ao inativar imovel: " + e.getMessage());
        }
    }

    @PostMapping(value = "/adicionarImagemImovel", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> adicionarImagemImovel(@RequestParam(value = "codimovel", required = false) Integer codimovel,
                                                   @RequestParam(value = "image", required = false) MultipartFile image,
                                                   HttpServletRequest request) throws IOException{
        try {
            Path fileNameAndPath = Paths.get(uploadDirectory, image.getOriginalFilename());
            Files.write(fileNameAndPath, image.getBytes());

            cri001s.adicionarImagemImovel(image.getOriginalFilename(), codimovel, gen.getUserName(request));
            return ResponseEntity.ok("OK");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
            
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erro interno ao inativar imovel: " + e.getMessage());
        }
    }

    @GetMapping("/buscarImagensImovel")
    public ResponseEntity<?> buscarImagensImovel(@RequestParam(value = "codimovel", required = false) Integer codimovel){
        try {
            return ResponseEntity.ok(cri001s.buscarImagensImovel(codimovel));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
            
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erro interno ao inativar imovel: " + e.getMessage());
        }
    }

    @GetMapping("/buscarImoveis")
    public List<?> buscarImoveis(@RequestParam(value = "codcontrato", required = false) Integer codcontrato,
                                 @RequestParam(value = "codprop", required = false)     Integer codprop,
                                 @RequestParam(value = "tipimovel", required = false)   Integer tipimovel) {
        return cri001s.buscarImoveis(codcontrato,codprop,tipimovel);
    }

    @GetMapping("/getOptionsTpContrato")
    public List<modelUtilForm> getOptionsTpContrato() {
        return cri001s.getOptionsTpContrato();
    }

    @GetMapping("/getOptionsTpImovel")
    public List<modelUtilForm> getOptionsTpImovel() {
        return cri001s.getOptionsTpImovel();
    }
}
