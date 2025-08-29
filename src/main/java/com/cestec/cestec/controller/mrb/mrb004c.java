package com.cestec.cestec.controller.mrb;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.cestec.cestec.service.mrb.mrb004s;

@RestController
@RequestMapping("/mrb004c")
public class mrb004c {
    @Autowired
    private mrb004s mrb004;

    @PostMapping("/cadastrarBloqueio")
    public ResponseEntity<?> cadastrarBloqueio(@RequestParam(value = "codapl") Integer codapl, @RequestParam(value = "codmod") Integer codmod, @RequestParam(value = "ideusu") String ideusu) {
        try {
            mrb004.cadastrarBloqueio(codapl,codmod,ideusu);
            return ResponseEntity.ok("OK");
            
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
            
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Erro interno ao cadastrar bloqueio: " + e.getMessage());
        }
    }

    @PostMapping("/cadastrarBloqueioUsuario")
    public ResponseEntity<?> cadastrarBloqueioUsuario(@RequestParam(value = "ideusuSolic") String ideusuSolic, @RequestParam(value = "codbloqueio") Integer codbloqueio, @RequestParam(value = "ideusu") String ideusu) {
        try {
            mrb004.cadastrarBloqueioUsuario(ideusuSolic,codbloqueio, ideusu);
            return ResponseEntity.ok("OK");
            
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
            
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Erro interno ao cadastrar bloqueio de usuario: " + e.getMessage());
        }
    }

    @PostMapping("/cadastrarBloqueioResponsavel")
    public ResponseEntity<?> cadastrarBloqueioResponsavel(@RequestParam(value = "ideusuSolic") String ideusuSolic, @RequestParam(value = "codbloqueio") Integer codbloqueio, @RequestParam(value = "ideusu") String ideusu) {
        try {
            mrb004.cadastrarBloqueioResponsavel(ideusuSolic,codbloqueio, ideusu);
            return ResponseEntity.ok("OK");
            
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
            
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Erro interno ao cadastrar bloqueio de Responsavel: " + e.getMessage());
        }
    }

    @PostMapping("/alteraEstadoBloqueioResp")
    public ResponseEntity<?> alteraEstadoBloqueioResp(@RequestParam(value = "ideusuSolic") String ideusuSolic, @RequestParam(value = "codbloqueio") Integer codbloqueio, @RequestParam(value = "ideusu") String ideusu) {
        try {
            mrb004.alteraEstadoBloqueioResp(ideusuSolic,codbloqueio, ideusu);
            return ResponseEntity.ok("OK");
            
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
            
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Erro interno ao alterar estado de bloqueio de usuario: " + e.getMessage());
        }
    }

    @PostMapping("/alteraEstadoBloqueioUsuario")
    public ResponseEntity<?> alteraEstadoBloqueioUsuario(@RequestParam(value = "ideusuSolic") String ideusuSolic, @RequestParam(value = "codbloqueio") Integer codbloqueio, @RequestParam(value = "ideusu") String ideusu) {
        try {
            mrb004.alteraEstadoBloqueioUsuario(ideusuSolic,codbloqueio, ideusu);
            return ResponseEntity.ok("OK");
            
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
            
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Erro interno ao alterar estado de bloqueio de usuario: " + e.getMessage());
        }
    }

    /* Grids */
    @GetMapping("/carregarGridBloqueios")
    public List<?> carregarGridBloqueios(@RequestParam(value = "codapl", required = false) Integer codapl,
                                         @RequestParam(value = "codmod", required = false) Integer codmod){
        return mrb004.carregarGridBloqueios(codapl, codmod);
    }

    @GetMapping("/carregarGridUsuariosBloq")
    public List<?> carregarGridUsuariosBloq(@RequestParam(value = "idbloq", required = false) Integer idbloq){
        return mrb004.carregarGridUsuariosBloq(idbloq);
    }

    @GetMapping("/carregarGridUsuariosResp")
    public List<?> carregarGridUsuariosResp(@RequestParam(value = "idbloq", required = false) Integer idbloq){
        return mrb004.carregarGridUsuariosResp(idbloq);
    }
}
