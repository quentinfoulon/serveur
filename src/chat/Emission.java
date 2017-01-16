package chat;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;


public class Emission implements Runnable {

	private PrintWriter out;
	private String message = null;
	private Scanner sc = null;
	private boolean connect=true;
	public Emission(PrintWriter out) {
		this.out = out;
	}

	
	public void run() {
		
		  sc = new Scanner(System.in);
		  
		  while(connect){
			  try{
			    System.out.println("Votre message :");
				message = sc.nextLine();
				out.println(message);
			    out.flush();
		  } catch (Exception e) {
				System.err.println(" un client s'est déconnecté ");
				Thread.currentThread().interrupt();
				connect=false;
			}
			  }
	}
}

