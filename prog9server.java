/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dissertation;

import java.io.DataInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.MessageDigest;
import java.util.Arrays;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import sun.misc.BASE64Decoder;

/**
 *
 * @author Pawan
 */
public class prog9server {
    public static void main(String[] args) throws Exception{
         ServerSocket ss=new ServerSocket(5555);
        System.out.println("Server Started");
        Socket s=ss.accept();
        DataInputStream dis=new DataInputStream(s.getInputStream());
        String msg="";
        String key="";
String answer="";
        while(!msg.equalsIgnoreCase("exit")){
            int length = dis.readInt();
            //System.out.println("length" +length);
            byte[] data;
            data=new byte[length];
            dis.readFully(data);
             int length1 = dis.readInt();
            //System.out.println("length" +length);
            byte[] data1;
            data1=new byte[length1];
            dis.readFully(data1);
            key=dis.readUTF();
            int diglength=dis.readInt();
          //  System.out.println("data :: " +data);
            //System.out.println("key :: " +key);
              BASE64Decoder decoder = new BASE64Decoder();
            byte[] arr = decoder.decodeBuffer(key);
            SecretKey seckey=new SecretKeySpec(arr,"DES");
             byte[] byt = decrypt(seckey, data1);
             byte digest[]=Arrays.copyOfRange(byt,0,diglength);
             byte message[]=Arrays.copyOfRange(byt,diglength,byt.length);
            msg = new String(message);
            System.out.println(msg);
              MessageDigest md=MessageDigest.getInstance("MD5");
             byte check[]=md.digest(msg.getBytes());
             boolean ans=Arrays.equals(check,digest);
             if(ans)
                 answer="Verified Succesfully";
             else
                 answer="Invalid Digest";
             System.out.println(answer);
              if (msg.equals("exit")) {
                break;
            }
        }
        
        dis.close();
        s.close();
    }
    public static byte[] decrypt(SecretKey seckey, byte [] encrypted)throws Exception {
		 Cipher aesCipher=Cipher.getInstance("DES/CBC/PKCS5Padding");
                 byte arr[]={0,0,0,0,0,0,0,0};
                 IvParameterSpec ip=new IvParameterSpec(arr);
		aesCipher.init(Cipher.DECRYPT_MODE,seckey,ip);
                return aesCipher.doFinal(encrypted);
	}
    }

