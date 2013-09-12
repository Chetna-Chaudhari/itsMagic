package hackday.mouse.listener;

import java.net.*;
import java.io.*;
import java.util.*;

public class FTPServer
{
    public void init() throws IOException {
        ServerSocket soc=new ServerSocket(5217);
        System.out.println("FTP Server Started on Port Number 5217");
        {
            transferfile t=new transferfile(soc);
            t.start();
        }
    }
}

class transferfile extends Thread
{
    private final ServerSocket serverSocket;
    Socket ClientSoc;

    DataInputStream din;
    DataOutputStream dout;

    transferfile(ServerSocket soc)
    {
               this.serverSocket = soc;
    }
    void SendFile(String filename) throws Exception
    {

        dout.writeUTF(filename);
        if(filename !=null)
        {
            File f = new File(filename);
            FileInputStream fin = new FileInputStream(f);
            int ch;
            do {
                ch = fin.read();
                dout.writeUTF(String.valueOf(ch));
            }
            while (ch != -1);
            fin.close();
        }
    }

    public void run()
    {
        try {
            ClientSoc=serverSocket.accept();
        } catch (IOException e) {
            e.printStackTrace();
        }
        while(true)
        {
            try
            {
                try
                {

                    din=new DataInputStream(ClientSoc.getInputStream());
                    dout=new DataOutputStream(ClientSoc.getOutputStream());
                }
                catch(Exception ex)
                {
                }

                System.out.println("Waiting for Command ...");
                String Command=din.readUTF();
                if(Command.compareTo("GET")==0)
                {
                    System.out.println("\tGET Command Received ...");
                    if(FilePathCatcher.getCurrentFilePath()!=null)
                        SendFile(FilePathCatcher.getCurrentFilePath());
                    else
                        dout.writeUTF("NOFILE");

                }

            }
            catch(Exception ex)
            {
                ex.printStackTrace();
            }
        }
    }
}