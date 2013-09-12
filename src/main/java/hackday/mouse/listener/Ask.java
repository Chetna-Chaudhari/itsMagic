package hackday.mouse.listener;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created with IntelliJ IDEA.
 * User: aniruddha.gangopadhyay
 * Date: 13/09/13
 * Time: 2:28 AM
 */
public interface Ask extends Remote{

    public boolean askServer() throws RemoteException;
}
