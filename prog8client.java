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
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.Cipher;
import javax.crypto.KeyAgreement;
import javax.crypto.SecretKey;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 *
 * @author Pawan
 */
public class prog8client {
    public static void main(String[] args) throws Exception {
           Socket s=new Socket("127.0.0.1",5555);
        DataInputStream dis1=new DataInputStream(System.in);
        DataInputStream dis=new DataInputStream(s.getInputStream());
        DataOutputStream dos=new DataOutputStream(s.getOutputStream());
        String msg="";
        String key="";
        while(!msg.equalsIgnoreCase("exit")){
            System.out.println("Enter message");
            msg=dis1.readLine();
            KeyPair kp=getKey();
            PublicKey pubkey=kp.getPublic();
            PrivateKey prikey=kp.getPrivate();
               byte [] pub=pubkey.getEncoded();
            BASE64Encoder encoder=new BASE64Encoder();
           String publickey= encoder.encode(pub);
            dos.flush();
            dos.writeUTF(publickey);
            // System.out.println("writing key::" + publickey);
             dos.flush();
             key=dis.readUTF();
           //System.out.println("key::" + key);
             BASE64Decoder decoder=new BASE64Decoder();
             byte arr[]=decoder.decodeBuffer(key);
             KeyFactory keyFactory=KeyFactory.getInstance("DH");
            PublicKey publicKeyrec=keyFactory.generatePublic(new X509EncodedKeySpec(arr));
            Key desKey=generateDESKey(publicKeyrec,prikey); 
             byte[] encrypted = encrypt(desKey, msg);
             dos.flush();
            dos.writeInt(encrypted.length);
            //System.out.println("length :: " + encrypted.length);
            dos.flush();
            dos.write(encrypted,0,encrypted.length);
    }
}
      public static KeyPair getKey() throws Exception{
        KeyPairGenerator kg=KeyPairGenerator.getInstance("DH");
        kg.initialize(1024);
        return kg.genKeyPair();
    } 
      public static Key generateDESKey(PublicKey recpubkey,PrivateKey prikey) throws Exception
	{
		KeyAgreement keyAgreement = KeyAgreement.getInstance("DH");
		keyAgreement.init(prikey);
		keyAgreement.doPhase(recpubkey,true);
		return  keyAgreement.generateSecret("DES");
                
	}
       public static byte[] encrypt(Key seckey,String msg) throws Exception{
        Cipher cipher=Cipher.getInstance("DES");
        cipher.init(Cipher.ENCRYPT_MODE, seckey);
        return cipher.doFinal(msg.getBytes());
    }
}
