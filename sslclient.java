/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dissertation;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

/**
 *
 * @author Pawan
 */
public class sslclient {
  public static void main(String[] args) throws Exception {
    SSLSocketFactory sslsocketfactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
    SSLSocket sslsocket = (SSLSocket) sslsocketfactory.createSocket("localhost", 9999);
    DataInputStream dis=new DataInputStream(System.in);
    DataOutputStream dos=new DataOutputStream(sslsocket.getOutputStream());
   String msg="";
        while(!msg.equalsIgnoreCase("exit")){
          msg=dis.readLine();
          dos.writeUTF(msg);
  }
}
}
