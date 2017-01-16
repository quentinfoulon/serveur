package serveur;

import java.net.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.Scanner;
import javax.mail.*;
import javax.mail.PasswordAuthentication;
import javax.mail.internet.*;

import BDD.jdbc;
import chat.Chat_ClientServeur;

import java.io.*;

public class MdpForget implements Runnable {

	private static Socket socket;
	private PrintWriter out = null;
	private BufferedReader in = null;
	private String login = "user", mail =  null;
	public boolean authentifier = false;
	public static Thread t2;
	
	public MdpForget(Socket s){
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
			
			mail = in.readLine();
			System.out.println(login +" "+mail);

			if(isValid(login, mail)){
				
				out.println("mdprecup");
				System.out.println(login +" demande sont mdp ");
				out.flush();
				authentifier = true;
				envoiemail();
				out.close();
				in.close();
				socket.close();
			}
			else {out.println("erreur"); out.flush();
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
    private void envoiemail(){
    	
/////////////////////////////envoie du mail de mdp recup//////////////////////////////////
    	
final String username = "projetjava2@gmail.com";
final String password = "projetjava";

Properties props = new Properties();
props.put("mail.smtp.auth", "true");
props.put("mail.smtp.starttls.enable", "true");
props.put("mail.smtp.host", "smtp.gmail.com");
props.put("mail.smtp.port", "587");

Session session = Session.getInstance(props,
new javax.mail.Authenticator() {
protected PasswordAuthentication getPasswordAuthentication() {
return new PasswordAuthentication(username, password);
}
});

try {

Message message = new MimeMessage(session);
message.setFrom(new InternetAddress("quentinfoulon59551@gmail.com"));
message.setRecipients(Message.RecipientType.TO,
InternetAddress.parse(mail));
message.setSubject("Recuperation mot de passe");
message.setText("vous venez de faire une demande de votre mdp,"
+ "\n\n votre mdp et le suivant : "+id);

Transport.send(message);

System.out.println("mail envoyé");

} catch (MessagingException e) {
throw new RuntimeException(e);
}

/////////////////////////////////////////////////////////////////////////////////////////

    	
    }
	private static boolean isValid(String login, String mail) {
		
		jdbc db = new jdbc();
		Connection c=db.connection();
		statement = null;
        try {
            statement = c.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            resultats = statement.executeQuery("select password from skype.\"userTab\" where username like '" +login+"' and mail like '"+mail+ "' ;");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        id=null;
        	try {

                while ( resultats.next() ) {

                    try {
                        id = resultats.getString( "password" );
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

