package hackday.mouse.listener;

/**
 * Created with IntelliJ IDEA.
 * User: aniruddha.gangopadhyay
 * Date: 12/09/13
 * Time: 6:23 PM
 */
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseInputListener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class FilePathCatcher implements NativeMouseInputListener {

        private static String currentFilePath = null;
    private final FTPClient client;

    public FilePathCatcher(FTPClient client) {
        this.client = client;
    }

    public void nativeMouseClicked(NativeMouseEvent e) {
            String script = "tell application \"Finder\"\n" +
                "    select window of desktop\n" +
                "    selection\n" +
                "end tell";
            String line;
            String output = "";
//            try {
//               // Thread.sleep(2000);
//            } catch (InterruptedException te) {
//
//            }
            try {
                String[] cmd = { "/usr/bin/osascript", "-e", script };
                Process p = Runtime.getRuntime().exec(cmd);
                BufferedReader input = new BufferedReader
                    (new InputStreamReader(p.getInputStream()));
                while ((line = input.readLine()) != null) {
                    output += line;
                }
                input.close();
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
            String [] filePathElements = output.split(" of folder ");
            String filePath = "/";
            String [] basePath = filePathElements[filePathElements.length-1].split(" of ");
            filePath= filePath+basePath[0]+"/";
            for(int i = filePathElements.length-2;i>0;i--){
                filePath=filePath+filePathElements[i]+"/";
            }
            String filename = filePathElements[0];
            String[] fileNameComponents = filename.split(" ",3);
            filename = fileNameComponents[fileNameComponents.length-1];
            filename.replaceAll(" ","\\ ");
            filePath=filePath+filename;
            if(filePath.equalsIgnoreCase("//")){
                ///receive file
                try {
                    client.dout.writeUTF("GET");
                    client.t.ReceiveFile();
                } catch (IOException e1) {
                    e1.printStackTrace();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
                return;
            }

                currentFilePath=filePath;
                if(currentFilePath.equalsIgnoreCase("//")){
                    currentFilePath=null; //TODO
                }
                System.out.println(currentFilePath);
                System.out.println("-----");

        }

        public void nativeMousePressed(NativeMouseEvent e) {

        }

        public void nativeMouseReleased(NativeMouseEvent e) {

        }

        public void nativeMouseMoved(NativeMouseEvent e) {

        }

        public void nativeMouseDragged(NativeMouseEvent e) {

        }

        public static void main(String[] args) {

        }

        public void init(){
            try {

                GlobalScreen.registerNativeHook();
            }
            catch (NativeHookException ex) {
                System.err.println("There was a problem registering the native hook.");
                System.err.println(ex.getMessage());

                System.exit(1);
            }
            GlobalScreen.getInstance().addNativeMouseListener(this);
            GlobalScreen.getInstance().addNativeMouseMotionListener(this);
        }

    public static String getCurrentFilePath() {
        return currentFilePath;
    }

    public static void setCurrentFilePath(String currentFilePath) {
        FilePathCatcher.currentFilePath = currentFilePath;
    }
}

