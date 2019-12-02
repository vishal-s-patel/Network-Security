/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dissertation;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import javax.crypto.Cipher;
import sun.misc.BASE64Encoder;

/**
 *
 * @author Pawan
 */
public class examrsaclient {
    public static void main(String[] args) throws Exception{
        Socket s=new Socket("127.0.0.1",5555);
        DataInputStream dis=new DataInputStream(System.in);
        DataOutputStream dos=new DataOutputStream(s.getOutputStream());
        String msg="";
        while(!(msg.equalsIgnoreCase("exit"))){
            msg=dis.readLine();
            KeyPair kp=getKey();
            PublicKey pubkey=kp.getPublic();
            PrivateKey prikey=kp.getPrivate();
            byte [] encrypted=encrypt(pubkey,msg);
            dos.writeInt(encrypted.length);
            dos.write(encrypted,0,encrypted.length);
            byte [] pri=prikey.getEncoded();
            BASE64Encoder encoder=new BASE64Encoder();
           String key= encoder.encode(pri);
            dos.writeUTF(key);
            
        }
        dis.close();
        dos.close();       
        s.close();
    }
    public static KeyPair getKey() throws Exception{
        KeyPairGenerator kg=KeyPairGenerator.getInstance("RSA");
        kg.initialize(1024);
        return kg.genKeyPair();
    } 
    public static byte [] encrypt(PublicKey pubkey,String msg)throws Exception{
        Cipher cipher=Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE,pubkey);
        return cipher.doFinal(msg.getBytes());
    }
}
