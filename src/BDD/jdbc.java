package BDD;

import java.sql.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;


public class jdbc {
   public jdbc( )
     {
       
     }
   public Connection connection(){
	   Connection conn = null;
	   try {
		      Class.forName("org.postgresql.Driver");
		      System.out.println("Driver O.K.");

		      String url = "jdbc:postgresql://flnq.fr:5432/projetjava";
		      String user = "projet";
		      String passwd = "projet";

		       conn = DriverManager.getConnection(url, user, passwd);
		      System.out.println("Connexion effective à la bdd  !");         
		         
		    } catch (Exception e) {
		      e.printStackTrace();
		    }
      // System.out.println("");
	return conn;
	   
   }
}
