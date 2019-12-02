/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dissertation;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import sun.misc.BASE64Encoder;

/**
 *
 * @author Pawan
 */
public class prog13client {
    public static void main(String[] args) throws Exception{
        Socket s=new Socket("127.0.0.1",5555);
        DataInputStream dis=new DataInputStream(System.in);
        DataOutputStream dos=new DataOutputStream(s.getOutputStream());
        String msg="";
           byte msgarr[];
           byte signarr[];
        while(!(msg.equalsIgnoreCase("D:\\exitfile.txt"))){
            System.out.println("Enter filename");
            msg=dis.readLine();
             KeyPair kp=getKey();
            PublicKey pubkey=kp.getPublic();
            PrivateKey prikey=kp.getPrivate();
            byte p[]=pubkey.getEncoded();
            BASE64Encoder encoder=new BASE64Encoder();
            String pkey=encoder.encode(p);
            dos.writeUTF(msg);
            dos.writeUTF(pkey);
            msgarr=Files.readAllBytes(Paths.get(msg));
            dos.writeInt(msgarr.length);
            dos.write(msgarr,0,msgarr.length);
            signarr=getSignature(prikey,msgarr);
            dos.writeInt(signarr.length);
            dos.write(signarr,0,signarr.length);
        }
        
    }
     public static KeyPair getKey() throws Exception{
        KeyPairGenerator kg=KeyPairGenerator.getInstance("RSA");
        kg.initialize(1024);
        return kg.genKeyPair();
    } 
     public static byte[] getSignature(PrivateKey prikey,byte[] msgarr) throws Exception{
         Signature signature=Signature.getInstance("SHA1withRSA");
         signature.initSign(prikey);
         signature.update(msgarr);
         return signature.sign();
     }
}

