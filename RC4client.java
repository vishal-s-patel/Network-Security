/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dissertation;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.security.SecureRandom;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import sun.misc.BASE64Encoder;

/**
 *
 * @author Pawan
 */
public class RC4client {
    public static void main(String[] args) throws Exception{
          Socket s=new Socket("127.0.0.1",5555);
        DataInputStream dis=new DataInputStream(System.in);
        DataOutputStream dos=new DataOutputStream(s.getOutputStream());
        String msg="";
       
        
        while(!msg.equalsIgnoreCase("exit")){
            msg=dis.readLine();
             SecretKey seckey=getkey();
           //  System.out.println("msg" +msg);
            byte[] encrypted = encrypt(seckey, msg);
            dos.writeInt(encrypted.length);
          //  System.out.println("encr" +encrypted);
           // System.out.println("length" +encrypted.length);
            dos.write(encrypted,0,encrypted.length);
              BASE64Encoder encoder = new BASE64Encoder();
            String key = encoder.encode(seckey.getEncoded());
            dos.writeUTF(key);
           // System.out.println("data :: " + encrypted);
            //System.out.println("key :: " + key);
        }
      
        dis.close();
        dos.close();
         s.close();
    }
    public static SecretKey getkey() throws Exception{
      /*  SecureRandom sr = new SecureRandom("password".getBytes());
      KeyGenerator kg = KeyGenerator.getInstance("RC4");
      kg.init(sr);
      SecretKey sk = kg.generateKey();
      return sk;*/
          SecretKey skey= new SecretKeySpec("password".getBytes(), "RC4");
       return skey;
    }
    public static byte[] encrypt(SecretKey seckey,String msg) throws Exception{
        Cipher cipher=Cipher.getInstance("RC4");
        cipher.init(Cipher.ENCRYPT_MODE, seckey);
        return cipher.doFinal(msg.getBytes());
    }
    }
