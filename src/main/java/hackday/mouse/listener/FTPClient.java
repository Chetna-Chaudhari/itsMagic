package hackday.mouse.listener;

import java.net.*;
import java.io.*;
import java.util.*;


class FTPClient
{
    private final String serverIp;

    public static void main(String args[]) throws Exception
    {


    }
    public  void init() throws Exception {
        Socket soc=new Socket(serverIp,5217);
        transferfileClient t=new transferfileClient(soc);
        t.displayMenu();
    }

    FTPClient(String ServerIP) {
        this.serverIp=ServerIP;
    }
}
class transferfileClient
{
    Socket ClientSoc;

    DataInputStream din;
    DataOutputStream dout;
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
    void SendFile(String filename) throws Exception
    {

        File f=new File(filename);
        dout.writeUTF(filename);

        String msgFromServer=din.readUTF();

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
        String fileName;
        fileName = din.readUTF();
        File f=new File(fileName);

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
    }

    public void displayMenu() throws Exception
    {
        while(true)
        {
            String filePath = FilePathCatcher.getCurrentFilePath();
            int choice =3;
            if(filePath!=null && !filePath.isEmpty()){
                //do nothign
                choice = 3;
            }else if(filePath.isEmpty()){
                choice = 2;
            }
                                                                            //choice=Integer.parseInt(br.readLine());
            if(choice==1)
            {
                dout.writeUTF("SEND");
                SendFile(filePath);
            }
            else if(choice==2)
            {
                dout.writeUTF("GET");
                ReceiveFile();
                FilePathCatcher.setCurrentFilePath(null);
            }
            else
            {
                //dout.writeUTF("DISCONNECT");
                //System.exit(1);
            }
        }
    }
}
