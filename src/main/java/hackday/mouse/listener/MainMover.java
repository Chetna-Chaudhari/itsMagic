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
        FilePathCatcher filePathCatcher = new FilePathCatcher();
        filePathCatcher.init();
        new FTPServer().init();
        new FTPClient("172.17.81.122").init();
    }
}
