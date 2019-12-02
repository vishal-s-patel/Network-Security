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

/**
 *
 * @author Pawan
 */
public class Prog3client {
    public static void main(String[] args) throws Exception {
         Socket s=new Socket("127.0.0.1",5555);
        Scanner sc=new Scanner(System.in);
        DataInputStream dis=new DataInputStream(System.in);
        DataOutputStream dos=new DataOutputStream(s.getOutputStream());
        String msg="";
        String type="";
        byte [] msgarr;
         while(!msg.equals("D:\\exitfile.txt")){
             System.out.println("Enter file name with its path");
             msg=dis.readLine();
             msgarr=Files.readAllBytes(Paths.get(msg));
             System.out.println("----------MENU----------");
             System.out.println("Choose Algorithm");
             System.out.println("1 MD5");
             System.out.println("2 SHA-1");
              System.out.println("3 SHA-512");
             System.out.println("Enter choice");
             int choice=sc.nextInt();
            if(choice==1){
               type="MD5";
            }
            else if(choice==2){
                type="SHA-1";
            }
            else if(choice==3){
                type="SHA-512";
            }
             MessageDigest md=MessageDigest.getInstance(type);
             byte arr[]=md.digest(msgarr);
             dos.writeUTF(new String(msgarr));
             dos.writeUTF(type);
             dos.writeInt(arr.length);
             dos.write(arr,0,arr.length);           
        }
    }
}
