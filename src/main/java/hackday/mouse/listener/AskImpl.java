package hackday.mouse.listener;

import java.rmi.RemoteException;

/**
 * Created with IntelliJ IDEA.
 * User: aniruddha.gangopadhyay
 * Date: 13/09/13
 * Time: 2:29 AM
 * To change this template use File | Settings | File Templates.
 */
public class AskImpl implements Ask{
    @Override
    public boolean askServer() throws RemoteException {
        if(FilePathCatcher.getCurrentFilePath() != null){
            System.out.println("Ask returns true: for file:"+FilePathCatcher.getCurrentFilePath());
            return true;
        }
        else{
            System.out.println("Ask returns false: for file:"+FilePathCatcher.getCurrentFilePath());
            return false;
        }
    }
}
