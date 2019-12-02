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
import java.util.Base64;
import java.util.Base64.Decoder;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import sun.misc.BASE64Decoder;

/**
 *
 * @author Pawan
 */
public class prog1server {
    public static void main(String[] args) throws Exception{
        ServerSocket ss=new ServerSocket(5555);
        System.out.println("Server Started");
        Socket s=ss.accept();
        DataInputStream dis=new DataInputStream(s.getInputStream());
        String msg="";
        String key="";
        String type="";
        byte [] byt;
        while(!msg.equalsIgnoreCase("D:\\exitfile.txt")){
            int num=0;
            int length = dis.readInt();
         //   System.out.println("length" +length);
            byte[] data;
            data=new byte[length];
            byt=new byte[65535];
            dis.readFully(data);
            key=dis.readUTF();
            type=dis.readUTF();
            int choice1=dis.readInt();
            int choice=dis.readInt();
           // System.out.println("data :: " +data);
            //System.out.println("key :: " +key);
              BASE64Decoder decoder = new BASE64Decoder();
            byte[] arr = decoder.decodeBuffer(key);
            if(choice1==1){
                 SecretKey seckey=new SecretKeySpec(arr,"DES");
                 if(choice==1){
                       byt = decrypt1(seckey, data);
                     // msg = new String(byt);
                       //  System.out.println(msg);
                 }
                 else if(choice==2){
                       byt = decrypt3(seckey, data);
                    // msg = new String(byt);
                     //System.out.println(msg);
                 }
                 else if(choice==3){
                      byt = decrypt6(seckey, data);
                 }
            
            }
            else if(choice1==2){
                    SecretKey seckey=new SecretKeySpec(arr,"AES");
                      if(choice==1){
                      byt = decrypt(seckey, data);
                     //  msg = new String(byt);
                     //System.out.println(msg);
                     }
                 else if(choice==2){
                       byt = decrypt2(seckey, data);
                    //msg = new String(byt);
                       //  System.out.println(msg);
                 }
                       else if(choice==3){
                       byt = decrypt7(seckey, data);
                    //msg = new String(byt);
                       //  System.out.println(msg);
                 }
            }
             else if(choice1==3){
                    SecretKey seckey=new SecretKeySpec(arr,"TripleDES");
                      if(choice==1){
                       byt = decrypt4(seckey, data);
                       //msg = new String(byt);
                     //System.out.println(msg);
                     }
                 else if(choice==2){
                       byt = decrypt5(seckey, data);
                    //msg = new String(byt);
                       //  System.out.println(msg);
                 }
                       else if(choice==3){
                       byt = decrypt8(seckey, data);
                    //msg = new String(byt);
                       //  System.out.println(msg);
                 }
            }
            //only is java 8 is there write the following code otherwise avoid it
            String path = System.getProperty("user.dir") + "\\src\\dissertation\\serverfile." + type;
    //to remove ambiguous filename
                while(Files.exists(Paths.get(path))){
                    path=System.getProperty("user.dir") + "\\src\\dissertation\\serverfile"+(++num)+"." + type;
                }
                Files.write(Paths.get(path),byt);
              if (msg.equals("D:\\exitfile.txt")) {
                break;
            }
        }
        
        dis.close();
        s.close();
    }
    public static byte[] decrypt(SecretKey seckey, byte [] encrypted)throws Exception {
		Cipher aesCipher = Cipher.getInstance("AES");
		aesCipher.init(Cipher.DECRYPT_MODE,seckey);
                return aesCipher.doFinal(encrypted);
	}
     public static byte[] decrypt1(SecretKey seckey, byte [] encrypted)throws Exception {
		Cipher aesCipher = Cipher.getInstance("DES");
		aesCipher.init(Cipher.DECRYPT_MODE,seckey);
                return aesCipher.doFinal(encrypted);
	}
       public static byte[] decrypt4(SecretKey seckey, byte [] encrypted)throws Exception {
		Cipher aesCipher = Cipher.getInstance("TripleDES");
		aesCipher.init(Cipher.DECRYPT_MODE,seckey);
                return aesCipher.doFinal(encrypted);
	}
       public static byte[] decrypt2(SecretKey seckey, byte [] encrypted)throws Exception {
		 Cipher aesCipher=Cipher.getInstance("AES/CBC/PKCS5Padding");
                 byte arr[]={0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
                 IvParameterSpec ip=new IvParameterSpec(arr);
		aesCipher.init(Cipher.DECRYPT_MODE,seckey,ip);
                return aesCipher.doFinal(encrypted);
	}
     public static byte[] decrypt3(SecretKey seckey, byte [] encrypted)throws Exception {
		 Cipher aesCipher=Cipher.getInstance("DES/CBC/PKCS5Padding");
                 byte arr[]={0,0,0,0,0,0,0,0};
                 IvParameterSpec ip=new IvParameterSpec(arr);
		aesCipher.init(Cipher.DECRYPT_MODE,seckey,ip);
                return aesCipher.doFinal(encrypted);
	}
      public static byte[] decrypt5(SecretKey seckey, byte [] encrypted)throws Exception {
		 Cipher aesCipher=Cipher.getInstance("TripleDES/CBC/PKCS5Padding");
                 byte arr[]={0,0,0,0,0,0,0,0};
                 IvParameterSpec ip=new IvParameterSpec(arr);
		aesCipher.init(Cipher.DECRYPT_MODE,seckey,ip);
                return aesCipher.doFinal(encrypted);
	}
       public static byte[] decrypt6(SecretKey seckey, byte [] encrypted)throws Exception {
		 Cipher aesCipher=Cipher.getInstance("DES/CFB8/NoPadding");
                 byte arr[]={0,0,0,0,0,0,0,0};
                 IvParameterSpec ip=new IvParameterSpec(arr);
		aesCipher.init(Cipher.DECRYPT_MODE,seckey,ip);
                return aesCipher.doFinal(encrypted);
	}
         public static byte[] decrypt7(SecretKey seckey, byte [] encrypted)throws Exception {
		 Cipher aesCipher=Cipher.getInstance("AES/CFB8/NoPadding");
                 byte arr[]={0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
                 IvParameterSpec ip=new IvParameterSpec(arr);
		aesCipher.init(Cipher.DECRYPT_MODE,seckey,ip);
                return aesCipher.doFinal(encrypted);
	}
          public static byte[] decrypt8(SecretKey seckey, byte [] encrypted)throws Exception {
		 Cipher aesCipher=Cipher.getInstance("TripleDES/CFB8/NoPadding");
                 byte arr[]={0,0,0,0,0,0,0,0};
                 IvParameterSpec ip=new IvParameterSpec(arr);
		aesCipher.init(Cipher.DECRYPT_MODE,seckey,ip);
                return aesCipher.doFinal(encrypted);
	}
}
