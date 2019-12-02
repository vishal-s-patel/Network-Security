/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dissertation;

import java.io.DataInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.util.Arrays;

/**
 *
 * @author Pawan
 */
public class Prog3server {
    public static void main(String[] args) throws Exception{
         ServerSocket ss=new ServerSocket(5555);
        System.out.println("Server Started");
        Socket s=ss.accept();
        DataInputStream dis=new DataInputStream(s.getInputStream());
        String msg="";
        String type="";
        String answer="";
        while(!msg.equalsIgnoreCase("D:\\exitfile.txt")){
            msg=dis.readUTF();
            type=dis.readUTF();
            int length=dis.readInt();
            byte[] data;
            data=new byte[length];
            dis.readFully(data);
            System.out.print(msg);
             MessageDigest md=MessageDigest.getInstance(type);
             byte arr[]=md.digest(msg.getBytes());
             boolean ans=Arrays.equals(data,arr);
             if(ans)
                 answer="valid";
             else
                 answer="invald";
             System.out.print("    "+ answer);
             System.out.println("");
    }
    }
}
