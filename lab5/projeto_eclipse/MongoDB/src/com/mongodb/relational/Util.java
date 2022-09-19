package com.mongodb.relational;

public class Util {
	static String retornarValorStringBD(String valor) {
		valor = valor.replaceAll("'", "''");
	    if (valor != null && !"".equals(valor)) {
	        valor = "'" + valor + "'";
	    } else {
	        valor = "'"+"'";
	    }
	    return valor;
	}

}
