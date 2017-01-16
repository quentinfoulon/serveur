package serveur;

import java.net.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.NoSuchElementException;
import java.util.Scanner;

import BDD.jdbc;
import chat.Chat_ClientServeur;

import java.io.*;

public class Authentificateur implements Runnable {

	private static Socket socket;
	private PrintWriter out = null;
	private BufferedReader in = null;
	private String login = "user", pass =  null;
	public boolean authentifier = false;
	private Thread[] tarray;
	public static Thread t2;
	
	public Authentificateur(Socket s){
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

			System.out.println(login +" "+pass);

			if(isValid(login, pass)){
				out.println("connecter");
				
				out.flush();
				
				System.out.println(login +" vient de se connecter ");
				
				authentifier = true;
		        if(id!=null){
		        	
		        try{
		        	in.close();
					out.close();
					t2 = new Thread(new Chat_ClientServeur(socket,login));
					t2.start();
					
		        } catch (Exception e) {
		        	Thread.currentThread().interrupt();
		        	socket.close();
					System.err.println(login +"s'est déconnecté ");
				}
		        }
				 
				System.out.println(id);

			}
			else {
				out.println("erreur"); out.flush();
				System.err.println(login+" ne peux pas ce connexté !");
			socket.close();
			}
			//}
		 }
			
		} catch (IOException e) {
			
        	
			System.err.println(login+" ne répond pas !");
			try {
				Thread.currentThread().interrupt();
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
	private static boolean isValid(String login, String pass) {
		
		jdbc db = new jdbc();
		Connection c=db.connection();
		statement = null;
        try {
            statement = c.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            resultats = statement.executeQuery("select id from skype.\"userTab\" where username like '" +login+"' and password like '"+pass+ "' ;");
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

