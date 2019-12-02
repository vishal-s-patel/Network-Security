/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dissertation;
import java.io.DataInputStream;
import java.net.Socket;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
/**
 *
 * @author Pawan
 */
public class prog14client {
    static String receiverid;
	static SecretKeySpec receiverkey;
	//static byte[] encryptedreceiverid,encryptedsenderid,encryptedsessionkeyclient;
		
    public static void main(String[] args) throws Exception{
           System.out.println("client");
        receiverid="receiver123";
        receiverkey=new SecretKeySpec("12345678".getBytes(),"DES");
        
        Socket s=new Socket("localhost",9090);
	DataInputStream dis=new DataInputStream(s.getInputStream());
        
	byte[] encryptedsenderid=new byte[dis.readInt()];
	dis.readFully(encryptedsenderid);
        
	byte[] encryptedreceiverid=new byte[dis.readInt()];
	dis.readFully(encryptedreceiverid);
        
	byte[] encryptedsessionkeyclient=new byte[dis.readInt()];
	dis.readFully(encryptedsessionkeyclient);
        
	Cipher cipher=Cipher.getInstance("DES");
	cipher.init(Cipher.DECRYPT_MODE,receiverkey);
	byte[] senderid=cipher.doFinal(encryptedsenderid);
	System.out.println("sender id" +new String(senderid));	
	byte[] receiverid=cipher.doFinal(encryptedreceiverid);
	System.out.println("receiverid" +new String(receiverid));		
	byte[] sessionkey=cipher.doFinal(encryptedsessionkeyclient);
	System.out.println("sessionkey" + new String(sessionkey));
    }
}
