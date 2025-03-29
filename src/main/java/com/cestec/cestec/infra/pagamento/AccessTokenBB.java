package com.cestec.cestec.infra.pagamento;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccessTokenBB {
    private String access_token;
    private String type_token;
    private int expires_in;
}
