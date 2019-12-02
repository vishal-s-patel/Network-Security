/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dissertation;

import java.io.DataInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.MessageDigest;
import java.util.Arrays;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author Pawan
 */
public class prog4server {
    public static void main(String[] args) throws Exception{
         ServerSocket ss=new ServerSocket(5555);
        System.out.println("Server Started");
        Socket s=ss.accept();
        DataInputStream dis=new DataInputStream(s.getInputStream());
        String msg="";
        String type="";
        String answer="";
        while(!msg.equalsIgnoreCase("exit")){
            msg=dis.readUTF();
            type=dis.readUTF();
            int length=dis.readInt();
            byte[] data;
            data=new byte[length];
            dis.readFully(data);
            System.out.print(msg);
             byte arr[]=encrypt("welcome", msg, type);
             boolean ans=Arrays.equals(data,arr);
             if(ans)
                 answer="valid";
             else
                 answer="invald";
             System.out.print("    "+ answer);
             System.out.println("");
    }
    }
     public static byte[] encrypt(String key,String msg,String type) throws Exception{
         byte [] byteKey = key.getBytes("UTF-8");
          SecretKeySpec keySpec = new SecretKeySpec(byteKey,type);
           Mac  m=Mac.getInstance(type);
           m.init(keySpec);
           return m.doFinal(msg.getBytes());
    }
}
