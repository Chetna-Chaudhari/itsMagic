package hackday.mouse.listener;

import java.net.*;
import java.io.*;
import java.util.*;

public class FTPServer
{
    public static void main(String args[]) throws Exception
    {

    }
    public void init() throws IOException {
        ServerSocket soc=new ServerSocket(5217);
        System.out.println("FTP Server Started on Port Number 5217");
        while(true)
        {
            transferfile t=new transferfile(soc.accept());

        }
    }
}

class transferfile extends Thread
{
    Socket ClientSoc;

    DataInputStream din;
    DataOutputStream dout;

    transferfile(Socket soc)
    {
        try
        {
            ClientSoc=soc;
            din=new DataInputStream(ClientSoc.getInputStream());
            dout=new DataOutputStream(ClientSoc.getOutputStream());
            start();

        }
        catch(Exception ex)
        {
        }
    }
    void SendFile(String filename) throws Exception
    {

        dout.writeUTF(filename);
        File f=new File(filename);
        FileInputStream fin=new FileInputStream(f);
        int ch;
        do
        {
            ch=fin.read();
            dout.writeUTF(String.valueOf(ch));
        }
        while(ch!=-1);
        fin.close();
    }

    void ReceiveFile() throws Exception
    {
        String filename=din.readUTF();
        File f=new File(filename);
        String option;

        dout.writeUTF("SendFile");
        option="Y";

        if(option.compareTo("Y")==0)
        {
            FileOutputStream fout=new FileOutputStream(f);
            int ch;
            String temp;
            do
            {
                temp=din.readUTF();
                ch=Integer.parseInt(temp);
                if(ch!=-1)
                {
                    fout.write(ch);
                }
            }while(ch!=-1);
            fout.close();
            dout.writeUTF("File Send Successfully");
        }
        else
        {
            return;
        }

    }


    public void run()
    {
        while(true)
        {
            try
            {
                System.out.println("Waiting for Command ...");
                String Command=din.readUTF();
                if(Command.compareTo("GET")==0)
                {
                    System.out.println("\tGET Command Received ...");
                    SendFile(FilePathCatcher.getCurrentFilePath());
                    continue;
                }
                else if(Command.compareTo("SEND")==0)
                {
                    System.out.println("\tSEND Command Receiced ...");
                    ReceiveFile();
                    continue;
                }
            }
            catch(Exception ex)
            {
                ex.printStackTrace();
            }
        }
    }
}