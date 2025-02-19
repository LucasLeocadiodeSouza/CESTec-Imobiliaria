package com.cestec.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class prm001 {        

    private static Connection      conn = null;
    private static ResultSet         rs = null;
    private static PreparedStatement st = null; 	

		public static String fmtDataBR(java.util.Date data){
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy"); 		
			return sdf.format(data);
		}

		public static String fmtDataSQL(java.util.Date data){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 		
			return sdf.format(data);	
		}

		public static String getDataNow(){		
			return LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		}

		public static String getDataHoraNow(){
			return LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm"));
		}
}