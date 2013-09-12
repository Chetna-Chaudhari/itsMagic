package hackday.mouse.listener;

import java.net.*;
import java.io.*;
import java.util.*;


class FTPClient
{
    private final String serverIp;
    public DataOutputStream dout;
    public transferfileClient t;

//    public static void main(String args[]) throws Exception
//    {
//    }
    public  void init() throws Exception {
        Socket soc=new Socket(serverIp,5217);
        transferfileClient t=new transferfileClient(soc);
        this.dout = t.dout;
        this.t = t;
    }

    FTPClient(String ServerIP) {
        this.serverIp=ServerIP;
    }
}
class transferfileClient
{
    Socket ClientSoc;

    DataInputStream din;
    public DataOutputStream dout;
    BufferedReader br;
    transferfileClient(Socket soc)
    {
        try
        {
            ClientSoc=soc;
            din=new DataInputStream(ClientSoc.getInputStream());
            dout=new DataOutputStream(ClientSoc.getOutputStream());
            br=new BufferedReader(new InputStreamReader(System.in));
        }
        catch(Exception ex)
        {
        }
    }

    void ReceiveFile() throws Exception
    {
        String fileName;

        fileName = din.readUTF();

        if (fileName!=null) {
            System.out.println("Receiving File");
            File f=new File(fileName.substring(fileName.lastIndexOf("/")+1));
            System.out.println(f.toString());
            f.createNewFile();
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
            System.out.println("Received File : " + fileName);
            fout.close();
        } else {
        }
    }
}
