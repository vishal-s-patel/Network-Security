/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dissertation;
import java.io.DataOutputStream;
import java.io.DataInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
/**
 *
 * @author Pawan
 */
public class prog14server {
     static String senderid;
	static SecretKeySpec senderkey;
	static byte[] encryptedreceiverid,encryptedsenderid,encryptedsessionkeyclient;
    public static void main(String[] args) throws Exception{
           System.out.println("Server");
        senderid="sender123";
        senderkey=new SecretKeySpec("87654321".getBytes(),"DES");
        getSessionInfoServer();
        ServerSocket ss=new ServerSocket(9090);
	Socket s=ss.accept();
	DataOutputStream dos=new DataOutputStream(s.getOutputStream());
	dos.writeInt(encryptedsenderid.length);
	dos.write(encryptedsenderid,0,encryptedsenderid.length);
        
	dos.writeInt(encryptedreceiverid.length);
	dos.write(encryptedreceiverid,0,encryptedreceiverid.length);
        
	dos.writeInt(encryptedsessionkeyclient.length);
	dos.write(encryptedsessionkeyclient,0,encryptedsessionkeyclient.length);
        
    }
    public static void getSessionInfoServer() throws Exception
	{
		Socket s=new Socket("localhost",8080);
		DataInputStream dis=new DataInputStream(s.getInputStream());
                
		byte[] encryptedsessionkey=new byte[dis.readInt()];
                dis.readFully(encryptedsessionkey);
                
		encryptedsenderid=new byte[dis.readInt()];
                dis.readFully(encryptedsenderid);
	
		encryptedreceiverid=new byte[dis.readInt()];
		dis.readFully(encryptedreceiverid);
		
		
		encryptedsessionkeyclient=new byte[dis.readInt()];
                dis.readFully(encryptedsessionkeyclient);
		
		Cipher cipher=Cipher.getInstance("DES");
		cipher.init(Cipher.DECRYPT_MODE,senderkey);
		byte[] sessionkey=cipher.doFinal(encryptedsessionkey);
		System.out.println("serversessionkey" +new String(sessionkey));
	}
}
