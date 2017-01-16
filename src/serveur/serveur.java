package serveur;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;
import java.sql.Connection;

import BDD.jdbc;

public class serveur {
	public Thread auth;
//public void test(){
	public static void main(String[] zero){
		ServerSocket socket;
		try {
		socket = new ServerSocket(2009);
		Thread t = new Thread(new Accepter_clients(socket));
		t.start();
		System.out.println("Ouvert a la connexion");
		
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}
}

class Accepter_clients implements Runnable {
	public Thread auth;
	public Thread mdp;
	public Thread inscription;
	private BufferedReader in = null;

	   private ServerSocket socketserver;
	   private Socket socket;
	   private int nbrclient = 1;
		public Accepter_clients(ServerSocket s){
			socketserver = s;
		}
		
		public void run() {

	        try {
	        	boolean continu =true;
	        	while(continu){
	    			socket = socketserver.accept();
	    			System.out.println("Un client veut se connecter  ");
	    			//in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	    			//for (int i=0;i<100;i++)
	    			//System.out.println(in.readLine());
	     			//socket.close();
	    			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	    			String valeurlue=in.readLine();
	    			if(valeurlue.equals("connexion")){
	    				
	    				System.out.println("classique");
	    			auth = new Thread(new Authentificateur(socket));
	    			auth.start();
	    			auth.setPriority(Thread.MAX_PRIORITY);
	    			System.out.println("nbThread : "+Thread.activeCount());
	    			
	    			}else if(valeurlue.equals("mdp")){
	    				
	    				System.out.println("mdp");
	    				mdp = new Thread(new MdpForget(socket));
		    			mdp.start();
	    			}else if(valeurlue.equals("inscription")){
	    				
	    				System.out.println("inscription");
	    				inscription = new Thread(new Inscription(socket));
	    				inscription.start();
	    			}
	        	}
	        
	        } catch (IOException e) {
				e.printStackTrace();
				Thread.currentThread().interrupt();
			}
		}

	}

