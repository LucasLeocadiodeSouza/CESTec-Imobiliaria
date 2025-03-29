package com.cestec.cestec.util;

import java.text.Normalizer;

public class Normalizador {

	public static String norm(String str) {
		return Normalizer.normalize(str.trim(), Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "").toUpperCase();
	}

	public static String abreviar(String str) {

		str = str.replaceAll("AVENIDA", "AV");
		str = str.replaceAll("RUA", "R");
		str = str.replaceAll("DOUTOR", "DR");
		str = str.replaceAll("JARDIM", "JD");
		str = str.replaceAll("VILA", "VL");
		str = str.replaceAll("CAPITAO", "CAP");
		str = str.replaceAll("ENGENHEIRO", "ENG");
		str = str.replaceAll("LOTEAMENTO", "LTO");

		return str;
	}

}