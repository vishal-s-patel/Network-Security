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
import java.security.MessageDigest;
import java.util.Scanner;
import javax.crypto.Cipher;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author Pawan
 */
public class prog4 {
    public static void main(String[] args) throws Exception{
        Socket s=new Socket("127.0.0.1",5555);
        Scanner sc=new Scanner(System.in);
        DataInputStream dis=new DataInputStream(System.in);
        DataOutputStream dos=new DataOutputStream(s.getOutputStream());
        String msg="";
        String type="";
        byte msgarr[];
          while(!msg.equals("exit")){
             System.out.println("Enter message");
             msg=dis.readLine();
              msgarr=Files.readAllBytes(Paths.get(msg));
             System.out.println("----------MENU----------");
             System.out.println("Choose Algorithm");
             System.out.println("1 HMAC MD5");
             System.out.println("2 HMAC SHA-1");
              System.out.println("3 HMAC SHA-512");
             System.out.println("Enter choice");
             int choice=sc.nextInt();
            if(choice==1){
               type="HmacMD5";
            }
            else if(choice==2){
                type="HmacSHA1";
            }
            else if(choice==3){
                type="HmacSHA512";
            }
             byte arr[]=encrypt("welcome", msgarr, type);
             dos.writeUTF(new String(msgarr));
             dos.writeUTF(type);
             dos.writeInt(arr.length);
             dos.write(arr,0,arr.length);
        }
    }
     public static byte[] encrypt(String key,byte [] msg,String type) throws Exception{
         byte [] byteKey = key.getBytes("UTF-8");
          SecretKeySpec keySpec = new SecretKeySpec(byteKey,type);
           Mac  m=Mac.getInstance(type);
           m.init(keySpec);
           return m.doFinal(msg);
    }
}
