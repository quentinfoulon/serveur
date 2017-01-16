package serveur;

import java.net.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.NoSuchElementException;
import java.util.Scanner;

import BDD.jdbc;
import chat.Chat_ClientServeur;

import java.io.*;

public class Inscription implements Runnable {

	private static Socket socket;
	private PrintWriter out = null;
	private BufferedReader in = null;
	private String login = "user", pass =  null , mail=null;
	public boolean authentifier = false;
	public static Thread t2;
	
	public Inscription(Socket s){
		 socket = s;
		}
	public void run() {
	
		try {
			
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new PrintWriter(socket.getOutputStream());
			//for (int i=0;i<100;i++){
		while(!authentifier){
			//if(in.readLine().equals("connexion")){
			login = in.readLine();
			pass = in.readLine();
			mail =in.readLine();
			System.out.println(login +" "+pass);

			if(!isValidLogin(login)){
				
				if(!isValidMail(mail)){
				
				
				jdbc db = new jdbc();
				Connection c=db.connection();
				statement = null;
				out.println("inscrit");
				System.out.println(login +" vient de s'inscrire ");
				out.flush();
				out.close();
				in.close();
				socket.close();
		        try {
		        	statement = c.createStatement();
		        	statement.executeUpdate("INSERT INTO skype.\"userTab\" (username , password , mail) VALUES ('" + login + "' ,'" + pass + "' ,'" + mail + "')  ");
		        	statement.close();
		        	    	c.close();
		        	
		        } catch (SQLException e) {
		            e.printStackTrace();
		            out.println("erreur");
					System.out.println("erreur");
					out.flush();
		        }
				
				
				authentifier = true;
				}else {
				out.println("mail");
				System.out.println(mail +" deja utilisé ");
				out.flush();
				socket.close();
				}
			}
			else {
			out.println("login");
			System.out.println(login +" deja utilisé ");
			out.flush();
			socket.close();
			}
			//}
		 }
			
		} catch (IOException e) {
			
			System.err.println(login+" ne répond pas !");
			try {
				socket.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
    private static  Statement statement ;
    public static ResultSet resultats;
    private static String id;
	private static boolean isValidMail(String mail) {
		
		jdbc db = new jdbc();
		Connection c=db.connection();
		statement = null;
        try {
            statement = c.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            resultats = statement.executeQuery("select id from skype.\"userTab\" where mail like '" +mail+"'  ;");
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        id=null;


        	try {

                while ( resultats.next() ) {

                    try {
                        id = resultats.getString( "id" );
                        //System.out.println(soustheme);
                        //String theme = resultats.getString( "theme" );
                        //System.out.println(theme);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
 

        boolean connexion=false;
        if(id!=null){
        	connexion = true;
        }
		 
		System.out.println(id);

		try {
			statement.close();
			c.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
        	
	return connexion;
		
	}
private static boolean isValidLogin(String login) {
		
		jdbc db = new jdbc();
		Connection c=db.connection();
		statement = null;
        try {
            statement = c.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            resultats = statement.executeQuery("select id from skype.\"userTab\" where username like '" +login+"' ;");

        } catch (SQLException e) {
            e.printStackTrace();
        }
        id=null;


        	try {

                while ( resultats.next() ) {

                    try {
                        id = resultats.getString( "id" );
                        //System.out.println(soustheme);
                        //String theme = resultats.getString( "theme" );
                        //System.out.println(theme);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
 

        boolean connexion=false;
        if(id!=null){
        	connexion = true;
        }
		 
		System.out.println(id);

		try {
			statement.close();
			c.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        	
	return connexion;
		
	}

}