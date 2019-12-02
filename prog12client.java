/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dissertation;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.time.LocalDate;


/**
 *
 * @author Pawan
 */
public class prog12client {
    public static void main(String[] args) throws Exception {
        SSLSocketFactory factory=(SSLSocketFactory)SSLSocketFactory.getDefault();
        SSLSocket socket=(SSLSocket)factory.createSocket("localhost",5555);
        DataOutputStream dos=new DataOutputStream(socket.getOutputStream());
            dos.writeUTF("Current Date & Time Is : " + LocalDate.now());
       
    }
}
