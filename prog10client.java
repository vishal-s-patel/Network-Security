/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dissertation;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import javax.crypto.Cipher;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import sun.misc.BASE64Encoder;

/**
 *
 * @author Pawan
 */
public class prog10client {
    public static void main(String[] args) throws Exception{
        Socket s=new Socket("127.0.0.1",5555);
        DataInputStream dis=new DataInputStream(System.in);
        DataOutputStream dos=new DataOutputStream(s.getOutputStream());
        String msg="";
        while(!msg.equalsIgnoreCase("exit")){
            System.out.println("Enter message");
            msg=dis.readLine();
            SecretKey skey=getkey();
            byte msgarr[]=msg.getBytes();
            byte digarr[]=genDigest(msg);
            byte newarr[]=new byte[digarr.length+msgarr.length];
            System.arraycopy(digarr,0,newarr,0,digarr.length);
            System.arraycopy(msgarr,0,newarr,digarr.length,msgarr.length);
            byte encrypted[]=encrypt(skey,newarr);
            BASE64Encoder encoder=new BASE64Encoder();
             String key = encoder.encode(skey.getEncoded());
              dos.writeInt(digarr.length);
             dos.write(digarr,0,digarr.length); 
              dos.writeInt(encrypted.length);
            dos.write(encrypted,0,encrypted.length);
             dos.writeUTF(key);
            dos.writeInt(digarr.length);
           // System.out.println("dig" +digarr.length);
        }
    }
    public static SecretKey getkey() throws Exception{
        SecretKey skey= new SecretKeySpec("abcdefghabcdefgh".getBytes(), "AES");
       return skey;
    }
     public static byte[] genDigest(String msg) throws Exception{
         byte [] byteKey = "welcome".getBytes("UTF-8");
          SecretKeySpec keySpec = new SecretKeySpec(byteKey,"HmacMD5");
           Mac  m=Mac.getInstance("HmacMD5"); 
           m.init(keySpec);
           return m.doFinal(msg.getBytes());
    }
      public static byte[] encrypt(SecretKey seckey,byte [] msg) throws Exception{
        Cipher cipher=Cipher.getInstance("AES/CBC/PKCS5Padding");
        byte arr[]={0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
         IvParameterSpec ip=new IvParameterSpec(arr);
        cipher.init(Cipher.ENCRYPT_MODE, seckey,ip);
        return cipher.doFinal(msg);
    }
}
