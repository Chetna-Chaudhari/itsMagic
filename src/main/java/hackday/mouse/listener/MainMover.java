package hackday.mouse.listener;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: aniruddha.gangopadhyay
 * Date: 12/09/13
 * Time: 9:15 PM
 * To change this template use File | Settings | File Templates.
 */
public class MainMover {


    public static ArrayList<String> otherHosts = new ArrayList<String>();
    public static Map<String,FTPClient> host2ClientMap  = new HashMap<String, FTPClient>();

    static {
        otherHosts.add("172.17.82.33");
        otherHosts.add("172.17.81.122");  //one is me the rest are not!!!
    }

    public static void main(String[] args) throws Exception {

        FTPServer server = new FTPServer(); server.init();
        Thread.sleep(5000);

        String myaddress = InetAddress.getLocalHost().getHostAddress() ;
        otherHosts.remove(otherHosts.indexOf(myaddress));  //filter myself

        for(String host: otherHosts){
            FTPClient client = new FTPClient(host);
            client.init();
            host2ClientMap.put(host,client);
        }

        //FTPClient client = new FTPClient("172.17.82.33");client.init();
        FilePathCatcher filePathCatcher = new FilePathCatcher(host2ClientMap);
        filePathCatcher.init();
    }
}
