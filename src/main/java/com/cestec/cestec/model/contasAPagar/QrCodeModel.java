package com.cestec.cestec.model.contasAPagar;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QrCodeModel {

	private String url;
	private String txId;
	private String emv;
	
}