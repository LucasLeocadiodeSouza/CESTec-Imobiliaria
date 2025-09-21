package com.cestec.cestec.infra.pagamento.controller;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import com.cestec.cestec.infra.pagamento.AccessTokenBB;


@Component
public class AcessTokenController {

    private RestTemplate restTemp = new RestTemplate();

    public String requisitarToken(String clienteId, String clienteSecret){

        URI uri = URI.create("https://oauth.hm.bb.com.br/oauth/token");
        var headers = new HttpHeaders();
        
        var basic = clienteId.concat(":").concat(clienteSecret);

        String encodedAuth = Base64.getEncoder().encodeToString(basic.getBytes(StandardCharsets.UTF_8));

        headers.set("Authorization", "Basic " + new String(encodedAuth));
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
		map.add("grant_type", "client_credentials");
		map.add("scope", "cobrancas.boletos-requisicao");

        var request = new HttpEntity<>(map, headers);
        var acess_token = restTemp.postForObject(uri, request, AccessTokenBB.class); 

        return acess_token.getAccess_token();
    }
}
