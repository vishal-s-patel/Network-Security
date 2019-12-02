/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dissertation;

import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.SecureRandom;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author Pawan
 */
public class prog14KDC {
    static SecretKeySpec senderkey,receiverkey;
      static  byte [] sessionkey,encryptedsessionkey;
       static  String senderid,receiverid;
    public static void main(String[] args) throws Exception {
        System.out.println("KDC");
        receiverid="receiver123";
        senderid="sender123";
        receiverkey=new SecretKeySpec("12345678".getBytes(),"DES");
        senderkey=new SecretKeySpec("87654321".getBytes(),"DES");
        ServerSocket ss=new ServerSocket(8080);
	  Socket s=ss.accept();
          sessionkey=generateSessionKey();
          System.out.println("sessionkey" +new String(sessionkey));
          DataOutputStream dos=new DataOutputStream(s.getOutputStream());
          Cipher cipher=Cipher.getInstance("DES");
            cipher.init(Cipher.ENCRYPT_MODE,senderkey);
            encryptedsessionkey=cipher.doFinal(sessionkey);
            cipher.init(Cipher.ENCRYPT_MODE,receiverkey);
            byte[] encryptedreceiverid=cipher.doFinal(receiverid.getBytes());
            byte[] encryptedsenderid=cipher.doFinal(senderid.getBytes());
            byte[] encryptedsessionkeyclient=cipher.doFinal(sessionkey);
		
		dos.writeInt(encryptedsessionkey.length);
		dos.write(encryptedsessionkey,0,encryptedsessionkey.length);
		
		dos.writeInt(encryptedsenderid.length);
		dos.write(encryptedsenderid,0,encryptedsenderid.length);
		
		dos.writeInt(encryptedreceiverid.length);
		dos.write(encryptedreceiverid,0,encryptedreceiverid.length);
		
		dos.writeInt(encryptedsessionkeyclient.length);
		dos.write(encryptedsessionkeyclient,0,encryptedsessionkeyclient.length);
        
        
    }
    public static byte [] generateSessionKey() throws Exception
	{
		sessionkey=new byte[8];
		SecureRandom random = new SecureRandom();
		random.nextBytes(sessionkey);
		return sessionkey;
	}
}
