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
import java.util.Scanner;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import sun.misc.BASE64Encoder;

/**
 *
 * @author Pawan
 */
public class prog1 {
    public static void main(String[] args) throws Exception{
        Socket s=new Socket("127.0.0.1",5555);
        Scanner sc=new Scanner(System.in);
        DataInputStream dis=new DataInputStream(System.in);
        DataOutputStream dos=new DataOutputStream(s.getOutputStream());
        String msg="";
        String type="";
        byte msgarr[];
         while(!msg.equals("D:\\exitfile.txt")){
             System.out.println("Enter filename with its path");
             //any type of file type like jpg mp3 text is supported
             msg=dis.readLine();
              String[] temp = msg.split("\\.");
            type = temp[temp.length - 1];
            //If java 8 is available write this code
             msgarr=Files.readAllBytes(Paths.get(msg));
             /* Otherwise you need to write
              File f = new File(msg);
                  InputStream is= new FileInputStream(f);
               msgarr = new byte[is.available()];
                is.read(msgarr);
             */
             System.out.println("----------MENU----------");
             System.out.println("Choose Mode");
             System.out.println(" 1 ECB");
             System.out.println("2 CBC");
             System.out.println("3 CFM");
             System.out.println("Enter choice");
             int choice=sc.nextInt();
             System.out.println("Choose Algorithm");
             System.out.println(" 1 DES");
             System.out.println("2 AES");
             System.out.println("3 3DES");
             System.out.println("Enter choice");
             int choice1=sc.nextInt();
             SecretKey skey=null;
             byte encrypted[];
             if(choice1==1){
                  skey=getkey1();
                 if(choice==1){
                     encrypted=encrypt1(skey, msgarr);
                       dos.writeInt(encrypted.length);
                     dos.write(encrypted,0,encrypted.length);
                         BASE64Encoder encoder = new BASE64Encoder();
                 String key = encoder.encode(skey.getEncoded());
                 dos.writeUTF(key);
                 dos.writeUTF(type);
                 dos.writeInt(choice1);
                 dos.writeInt(choice);
                 }
                 else if(choice==2){
                      encrypted=encrypt3(skey, msgarr);
                        dos.writeInt(encrypted.length);
                dos.write(encrypted,0,encrypted.length);
                 BASE64Encoder encoder = new BASE64Encoder();
                String key = encoder.encode(skey.getEncoded());
                dos.writeUTF(key);
                   dos.writeUTF(type);
                dos.writeInt(choice1);
                dos.writeInt(choice);
                 }
                  else if(choice==3){
                      encrypted=encrypt6(skey, msgarr);
                        dos.writeInt(encrypted.length);
                dos.write(encrypted,0,encrypted.length);
                 BASE64Encoder encoder = new BASE64Encoder();
                String key = encoder.encode(skey.getEncoded());
                dos.writeUTF(key);
                   dos.writeUTF(type);
                dos.writeInt(choice1);
                dos.writeInt(choice);
                 }
               
                 
             }
             else if(choice1==2){
                 skey=getkey();
                 if(choice==1){
                      encrypted=encrypt(skey, msgarr);
                   dos.writeInt(encrypted.length);
                 dos.write(encrypted,0,encrypted.length);
                 BASE64Encoder encoder = new BASE64Encoder();
                 String key = encoder.encode(skey.getEncoded());
                 dos.writeUTF(key);
                    dos.writeUTF(type);
                 dos.writeInt(choice1);
                 dos.writeInt(choice);
                 }
                 else if(choice==2){
                      encrypted=encrypt2(skey, msgarr);
                   dos.writeInt(encrypted.length);
                 dos.write(encrypted,0,encrypted.length);
                 BASE64Encoder encoder = new BASE64Encoder();
                 String key = encoder.encode(skey.getEncoded());
                 dos.writeUTF(key);
                    dos.writeUTF(type);
                 dos.writeInt(choice1);
                 dos.writeInt(choice);
                 }
                   else if(choice==3){
                      encrypted=encrypt7(skey, msgarr);
                        dos.writeInt(encrypted.length);
                dos.write(encrypted,0,encrypted.length);
                 BASE64Encoder encoder = new BASE64Encoder();
                String key = encoder.encode(skey.getEncoded());
                dos.writeUTF(key);
                   dos.writeUTF(type);
                dos.writeInt(choice1);
                dos.writeInt(choice);
                 }
             }
             else if(choice1==3){
                       skey=getkey2();
                 if(choice==1){
                     encrypted=encrypt4(skey, msgarr);
                       dos.writeInt(encrypted.length);
                     dos.write(encrypted,0,encrypted.length);
                         BASE64Encoder encoder = new BASE64Encoder();
                 String key = encoder.encode(skey.getEncoded());
                 dos.writeUTF(key);
                    dos.writeUTF(type);
                 dos.writeInt(choice1);
                 dos.writeInt(choice);
                 }
                 else if(choice==2){
                      encrypted=encrypt5(skey, msgarr);
                        dos.writeInt(encrypted.length);
                dos.write(encrypted,0,encrypted.length);
                 BASE64Encoder encoder = new BASE64Encoder();
                String key = encoder.encode(skey.getEncoded());
                dos.writeUTF(key);
                   dos.writeUTF(type);
                dos.writeInt(choice1);
                dos.writeInt(choice);
                 }
                  else if(choice==3){
                      encrypted=encrypt8(skey, msgarr);
                        dos.writeInt(encrypted.length);
                dos.write(encrypted,0,encrypted.length);
                 BASE64Encoder encoder = new BASE64Encoder();
                String key = encoder.encode(skey.getEncoded());
                dos.writeUTF(key);
                   dos.writeUTF(type);
                dos.writeInt(choice1);
                dos.writeInt(choice);
                 }
             }
            
         }
         
    }
      public static SecretKey getkey() throws Exception{
      SecretKey skey= new SecretKeySpec("abcdefghabcdefgh".getBytes(), "AES");
       return skey;
    }
    public static byte[] encrypt(SecretKey seckey,byte [] msg) throws Exception{
        Cipher cipher=Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, seckey);
        return cipher.doFinal(msg);
    }
     public static SecretKey getkey1() throws Exception{
      SecretKey skey= new SecretKeySpec("abcdefgh".getBytes(), "DES");
       return skey;
    }
      public static SecretKey getkey2() throws Exception{
      SecretKey skey= new SecretKeySpec("abcdefghabcdefghabcdefgh".getBytes(), "TripleDES");
       return skey;
    }
    public static byte[] encrypt1(SecretKey seckey,byte [] msg) throws Exception{
        Cipher cipher=Cipher.getInstance("DES");
        cipher.init(Cipher.ENCRYPT_MODE, seckey);
        return cipher.doFinal(msg);
    }
     public static byte[] encrypt4(SecretKey seckey,byte [] msg) throws Exception{
        Cipher cipher=Cipher.getInstance("TripleDES");
        cipher.init(Cipher.ENCRYPT_MODE, seckey);
        return cipher.doFinal(msg);
    }
     public static byte[] encrypt2(SecretKey seckey,byte [] msg) throws Exception{
        Cipher cipher=Cipher.getInstance("AES/CBC/PKCS5Padding");
        byte arr[]={0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
         IvParameterSpec ip=new IvParameterSpec(arr);
        cipher.init(Cipher.ENCRYPT_MODE, seckey,ip);
        return cipher.doFinal(msg);
    }
      public static byte[] encrypt3(SecretKey seckey,byte [] msg) throws Exception{
        Cipher cipher=Cipher.getInstance("DES/CBC/PKCS5Padding");
         byte arr[]={0,0,0,0,0,0,0,0};
         IvParameterSpec ip=new IvParameterSpec(arr);
        cipher.init(Cipher.ENCRYPT_MODE, seckey,ip);
        return cipher.doFinal(msg);
    }
          public static byte[] encrypt5(SecretKey seckey,byte [] msg ) throws Exception{
        Cipher cipher=Cipher.getInstance("TripleDES/CBC/PKCS5Padding");
       byte arr[]={0,0,0,0,0,0,0,0};
         IvParameterSpec ip=new IvParameterSpec(arr);
        cipher.init(Cipher.ENCRYPT_MODE, seckey,ip);
        return cipher.doFinal(msg);
    }
             public static byte[] encrypt6(SecretKey seckey,byte [] msg) throws Exception{
        Cipher cipher=Cipher.getInstance("DES/CFB8/NoPadding");
         byte arr[]={0,0,0,0,0,0,0,0};
         IvParameterSpec ip=new IvParameterSpec(arr);
        cipher.init(Cipher.ENCRYPT_MODE, seckey,ip);
        return cipher.doFinal(msg);
    }
               public static byte[] encrypt7(SecretKey seckey,byte [] msg) throws Exception{
        Cipher cipher=Cipher.getInstance("AES/CFB8/NoPadding");
        byte arr[]={0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
         IvParameterSpec ip=new IvParameterSpec(arr);
        cipher.init(Cipher.ENCRYPT_MODE, seckey,ip);
        return cipher.doFinal(msg);
    }
                  public static byte[] encrypt8(SecretKey seckey,byte [] msg ) throws Exception{
        Cipher cipher=Cipher.getInstance("TripleDES/CFB8/NoPadding");
       byte arr[]={0,0,0,0,0,0,0,0};
         IvParameterSpec ip=new IvParameterSpec(arr);
        cipher.init(Cipher.ENCRYPT_MODE, seckey,ip);
        return cipher.doFinal(msg);
    }
}
