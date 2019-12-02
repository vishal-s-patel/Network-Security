/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dissertation;

import java.io.DataInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.Key;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import sun.misc.BASE64Decoder;

/**
 *
 * @author Pawan
 */
public class prog7server {
    public static void main(String[] args) throws Exception{
           ServerSocket ss=new ServerSocket(5555);
        System.out.println("Server Started");
        Socket s=ss.accept();
        DataInputStream dis=new DataInputStream(s.getInputStream()); 
        String msg="";
        while(msg!="exit"){
            int len=dis.readInt();
            byte [] data;
            data=new byte[len];
            dis.readFully(data);
            int len1=dis.readInt();
            byte [] data1;
            data1=new byte[len1];
            dis.readFully(data1);
            String key=dis.readUTF();
            BASE64Decoder decoder=new BASE64Decoder();
            byte arr[]=decoder.decodeBuffer(key);
            KeyFactory keyFactory=KeyFactory.getInstance("RSA");
            PrivateKey prikey=keyFactory.generatePrivate(new PKCS8EncodedKeySpec(arr));
            byte dec[]=decrypt(data,prikey);
            SecretKey deskey=new SecretKeySpec(dec,"DES");
            byte msgarr[]=decrypt1(deskey,data1);
            msg=new String(msgarr);
            System.out.println("msg " +msg);
            if (msg.equals("exit")) {
                break;
            }
        }
        dis.close();
    }
    public static byte [] decrypt(byte [] data,PrivateKey prikey) throws Exception{
        Cipher cipher=Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE,prikey);
        return cipher.doFinal(data);
    }
     public static byte[] decrypt1(SecretKey seckey, byte [] encrypted)throws Exception {
		Cipher aesCipher = Cipher.getInstance("DES");
		aesCipher.init(Cipher.DECRYPT_MODE,seckey);
                return aesCipher.doFinal(encrypted);
	}
    }

