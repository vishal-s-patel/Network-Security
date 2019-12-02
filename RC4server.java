/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dissertation;

import java.io.DataInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import sun.misc.BASE64Decoder;

/**
 *
 * @author Pawan
 */
public class RC4server {
    public static void main(String[] args) throws Exception {
         ServerSocket ss=new ServerSocket(5555);
        System.out.println("Server Started");
        Socket s=ss.accept();
        DataInputStream dis=new DataInputStream(s.getInputStream());
        String msg="";
        String key="";
        while(!msg.equalsIgnoreCase("exit")){
            int length = dis.readInt();
         //   System.out.println("length" +length);
            byte[] data;
            data=new byte[length];
            dis.readFully(data);
            key=dis.readUTF();
           // System.out.println("data :: " +data);
            //System.out.println("key :: " +key);
              BASE64Decoder decoder = new BASE64Decoder();
            byte[] arr = decoder.decodeBuffer(key);
            SecretKey seckey=new SecretKeySpec(arr,"RC4");
             byte[] byt = decrypt(seckey, data);
            msg = new String(byt);
            System.out.println(msg);
              if (msg.equals("exit")) {
                break;
            }
        }
        
        dis.close();
        s.close();
    }
    public static byte[] decrypt(SecretKey seckey, byte [] encrypted)throws Exception {
		Cipher aesCipher = Cipher.getInstance("RC4");
		aesCipher.init(Cipher.DECRYPT_MODE,seckey);
                return aesCipher.doFinal(encrypted);
	}
    }

