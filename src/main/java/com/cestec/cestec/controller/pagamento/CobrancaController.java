package com.cestec.cestec.controller.pagamento;

import java.net.URI;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import com.cestec.cestec.infra.pagamento.CobrancaInput;
import com.cestec.cestec.model.contasAPagar.BoletoRegistrado;

@Component
public class CobrancaController {

    private RestTemplate restTemp = new RestTemplate();

    public BoletoRegistrado register(CobrancaInput cobranca, String token, String key){
        URI uri = URI.create("https://api.hm.bb.com.br/cobrancas/v2/boletos");
        var uriBuider = UriComponentsBuilder.fromUri(uri);
        uriBuider.queryParam("gw-dev-app-key", key);

        var header = new HttpHeaders();
        header.set("Authorization", "Bearer " + token);

        var request = new HttpEntity<>(cobranca, header);
        var response = restTemp.postForObject(uriBuider.build().toUri(), request, BoletoRegistrado.class); 
        
        return response;
    }
}
