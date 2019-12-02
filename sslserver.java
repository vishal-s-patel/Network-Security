/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dissertation;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;

/**
 *
 * @author Pawan
 */
public class sslserver {
     public static void main(String[] args) throws Exception {

            SSLServerSocketFactory sslserversocketfactory =
                    (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
            SSLServerSocket sslserversocket =
                    (SSLServerSocket) sslserversocketfactory.createServerSocket(9999);
                 System.out.println("Server started");
            SSLSocket sslsocket = (SSLSocket) sslserversocket.accept();
            DataInputStream dis=new DataInputStream(sslsocket.getInputStream()); 
        String msg="";
        while(msg!="exit"){
            msg=dis.readUTF();
            System.out.println("msg " +msg);
            if (msg.equals("exit")) {
                break;
            }
        }
  }
/*
     TO RUN THIS PROGRAM YOU NEED TO COPY PROGRAMS AND FROM CMD AND WRITE FOLLOWING COMMANNDS
     NOTE THAT YOU WILL NEED ADMIN RIGHTS OTHERWISE PROGRAM WILL NOT RUN
     D:\MCA SEM 5\NS>keytool -genkey -keystore mySrvKeystore -keyalg RSA
Enter keystore password:123456
Re-enter new password:123456
What is your first and last name?
  [Unknown]:  localhost
What is the name of your organizational unit?
  [Unknown]:  localhost
What is the name of your organization?
  [Unknown]:  localhost
What is the name of your City or Locality?
  [Unknown]:  localhost
What is the name of your State or Province?
  [Unknown]:  localhost
What is the two-letter country code for this unit?
  [Unknown]:  lh
Is CN=localhost, OU=localhost, O=localhost, L=localhost, ST=localhost, C=lh corr
ect?
  [no]:  yes

Enter key password for <mykey>
        (RETURN if same as keystore password):123456
Re-enter new password:123456

D:\MCA SEM 5\NS>javac sslserver.java

D:\MCA SEM 5\NS>javac sslclient.java

D:\MCA SEM 5\NS>java -Djavax.net.ssl.keyStore=mySrvKeystore -Djavax.net.ssl.keyStorePassword=123456 sslserver
heyyy
hiii
exit


D:\MCA SEM 5\NS>java -Djavax.net.ssl.trustStore=mySrvKeystore -Djavax.net.ssl.trustStorePassword=123456 sslclient
heyyy
hiii
exit
*/
}
