/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dissertation;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
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
public class prog8server {
    public static void main(String[] args) throws Exception{
            ServerSocket ss=new ServerSocket(5555);
        System.out.println("Server Started");
        Socket s=ss.accept();
        DataInputStream dis=new DataInputStream(s.getInputStream());
        DataOutputStream dos=new DataOutputStream(s.getOutputStream());
        String msg="";
        while(msg!="exit"){
             KeyPair kp=getKey();
            PublicKey pubkey=kp.getPublic();
            PrivateKey prikey=kp.getPrivate();
            byte [] pub=pubkey.getEncoded();
            BASE64Encoder encoder=new BASE64Encoder();
           String publickey= encoder.encode(pub);
           String key=dis.readUTF();
          //  System.out.println("key read::" + key);
            dos.flush();
            dos.writeUTF(publickey);
            //System.out.println("key server::" + publickey);
            dos.flush();
            
             BASE64Decoder decoder=new BASE64Decoder();
              byte arr[]=decoder.decodeBuffer(key);
             KeyFactory keyFactory=KeyFactory.getInstance("DH");
             PublicKey publicKeysender=keyFactory.generatePublic(new X509EncodedKeySpec(arr));
             Key desKey=generateDESKey(publicKeysender,prikey); 
              int length = dis.readInt();
          //  System.out.println("length :: " +length);
            byte[] data;
            data=new byte[length];
            dis.readFully(data);
              byte[] byt = decrypt(desKey, data);
            msg = new String(byt);
            System.out.println("data:: " + msg);
            if(msg.equalsIgnoreCase("exit"))
                break;
        }
    }
     public static KeyPair getKey() throws Exception{
        KeyPairGenerator kg=KeyPairGenerator.getInstance("DH");
        kg.initialize(1024);
        return kg.genKeyPair();
    } 
      public static Key generateDESKey(PublicKey sendpubkey,PrivateKey prikey) throws Exception
	{
		KeyAgreement keyAgreement = KeyAgreement.getInstance("DH");
		keyAgreement.init(prikey);
		keyAgreement.doPhase(sendpubkey,true);
		return  keyAgreement.generateSecret("DES");
                
	}
       public static byte[] decrypt(Key seckey, byte [] encrypted)throws Exception {
		Cipher aesCipher = Cipher.getInstance("DES");
		aesCipher.init(Cipher.DECRYPT_MODE,seckey);
                return aesCipher.doFinal(encrypted);
	}
}
