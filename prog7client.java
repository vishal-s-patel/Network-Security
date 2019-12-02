/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dissertation;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import sun.misc.BASE64Encoder;

/**
 *
 * @author Pawan
 */
public class prog7client {
    public static void main(String[] args) throws Exception{
           Socket s=new Socket("127.0.0.1",5555);
        DataInputStream dis=new DataInputStream(System.in);
        DataOutputStream dos=new DataOutputStream(s.getOutputStream());
        String msg="";
        while(!msg.equalsIgnoreCase("exit")){
            msg=dis.readLine();
            KeyPair kp=getKey();
            PublicKey pubkey=kp.getPublic();
            PrivateKey prikey=kp.getPrivate();
            KeyGenerator kg=KeyGenerator.getInstance("DES");
            SecretKey skey= new SecretKeySpec("abcdefgh".getBytes(), "DES");
            byte [] keyencrypt=encrypt(pubkey, skey);
             dos.writeInt(keyencrypt.length);
            dos.write(keyencrypt,0,keyencrypt.length);
            byte [] msgencrypt=encrypt1(skey,msg);
             dos.writeInt(msgencrypt.length);
            dos.write(msgencrypt,0,msgencrypt.length);
             byte [] pri=prikey.getEncoded();
            BASE64Encoder encoder=new BASE64Encoder();
           String privatekey= encoder.encode(pri);
            dos.writeUTF(privatekey);
	}
        
    }
      public static KeyPair getKey() throws Exception{
        KeyPairGenerator kg=KeyPairGenerator.getInstance("RSA");
        kg.initialize(1024);
        return kg.genKeyPair();
    } 
       public static byte [] encrypt(PublicKey pubkey,SecretKey key)throws Exception{
        Cipher cipher=Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE,pubkey);
        return cipher.doFinal(key.getEncoded());
    }
        public static byte[] encrypt1(SecretKey seckey,String msg) throws Exception{
        Cipher cipher=Cipher.getInstance("DES");
        cipher.init(Cipher.ENCRYPT_MODE, seckey);
        return cipher.doFinal(msg.getBytes());
    }
}
