package hackday.mouse.listener;

/**
 * Created with IntelliJ IDEA.
 * User: aniruddha.gangopadhyay
 * Date: 12/09/13
 * Time: 9:15 PM
 * To change this template use File | Settings | File Templates.
 */
public class MainMover {
    public static void main(String[] args) throws Exception {


        FTPServer server = new FTPServer(); server.init();
        Thread.sleep(5000);
        FTPClient client = new FTPClient("172.17.82.33");client.init();
        FilePathCatcher filePathCatcher = new FilePathCatcher(client);
        filePathCatcher.init();
    }
}
