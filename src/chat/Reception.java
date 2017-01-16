package chat;

import java.io.BufferedReader;
import java.io.IOException;


public class Reception implements Runnable {

	private BufferedReader in;
	private String message = null, login = null;
	private boolean connect=true;
	
	public Reception(BufferedReader in, String login){
		
		this.in = in;
		this.login = login;
	}
	
	public void run() {
		
		while(connect){
	        try {
	        	System.out.println("test1");
	        message = in.readLine();
			System.out.println(login+" : "+message);
			System.out.println("test2");
		    } catch (IOException e) {
		    	System.out.println("test3");
				connect=false;
				Thread.currentThread().interrupt();
				e.printStackTrace();
			}
		}
	}

}
