/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dissertation;

import java.io.DataInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import sun.misc.BASE64Decoder;

/**
 *
 * @author Pawan
 */
public class prog13server {
    public static void main(String[] args) throws Exception{
        ServerSocket ss=new ServerSocket(5555);
         System.out.println("Server Started");
         Socket s=ss.accept();
         DataInputStream dis=new DataInputStream(s.getInputStream()); 
         String msg="";
         
            while(!msg.equalsIgnoreCase("D:\\exitfile.txt")){
                msg=dis.readUTF();
               String key=dis.readUTF();
                int len=dis.readInt();
                 byte [] data;
                 data=new byte[len];
                 dis.readFully(data);
                 int len1=dis.readInt();
                 byte [] sign;
                 sign=new byte[len1];
                 dis.readFully(sign);
                 BASE64Decoder decoder=new BASE64Decoder();
                 byte arr[]=decoder.decodeBuffer(key);
                 KeyFactory keyFactory=KeyFactory.getInstance("RSA");
                 PublicKey publicKey=keyFactory.generatePublic(new X509EncodedKeySpec(arr));
                  Signature signature=Signature.getInstance("SHA1withRSA");
                  signature.initVerify(publicKey);
                  signature.update(data);
                 boolean tmp= signature.verify(sign);
                 if(tmp==true)
                     System.out.println("Verified Successfully");
                 else
                     System.out.println("Data is corrupted");
                 if(msg.equalsIgnoreCase("D:\\exitfile.txt"))
                     break;
           }
           dis.close();
    }
}
