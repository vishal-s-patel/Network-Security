/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dissertation;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.security.MessageDigest;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import sun.misc.BASE64Encoder;

/**
 *
 * @author Pawan
 */
public class prog9client {
    public static void main(String[] args) throws Exception{
          Socket s=new Socket("127.0.0.1",5555);
        DataInputStream dis=new DataInputStream(System.in);
        DataOutputStream dos=new DataOutputStream(s.getOutputStream());
        String msg="";
        while(!msg.equalsIgnoreCase("exit")){
            System.out.println("Enter message");
            msg=dis.readLine();
             SecretKey seckey=getkey();
              MessageDigest md=MessageDigest.getInstance("MD5");
             byte arr[]=md.digest(msg.getBytes());
             dos.writeInt(arr.length);
             dos.write(arr,0,arr.length);        
             byte msgar[]=msg.getBytes();
             byte newarr[]=new byte[arr.length+msgar.length];
             System.arraycopy(arr, 0,newarr, 0, arr.length);
            System.arraycopy(msgar, 0, newarr, arr.length, msgar.length);
            byte[] encrypted = encrypt(seckey,newarr);
            dos.writeInt(encrypted.length);
            dos.write(encrypted,0,encrypted.length);
              BASE64Encoder encoder = new BASE64Encoder();
            String key = encoder.encode(seckey.getEncoded());
            dos.writeUTF(key);
            dos.writeInt(arr.length);      
         //  System.out.println("diglen :: " + arr.length);          
        }
        dis.close();
        dos.close();
         s.close();
    }
    public static SecretKey getkey() throws Exception{
      SecretKey skey= new SecretKeySpec("abcdefgh".getBytes(), "DES");
       return skey;
    }
    public static byte[] encrypt(SecretKey seckey,byte [] msg) throws Exception{
        Cipher cipher=Cipher.getInstance("DES/CBC/PKCS5Padding");
         byte arr[]={0,0,0,0,0,0,0,0};
         IvParameterSpec ip=new IvParameterSpec(arr);
        cipher.init(Cipher.ENCRYPT_MODE, seckey,ip);
        return cipher.doFinal(msg);
    }
    }

