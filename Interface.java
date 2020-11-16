import java.rmi.Remote;

public interface Interface extends Remote
{
    public int GetPlayerNum() throws java.rmi.RemoteException;
    public int PlayerSelection(int Player , int Role) throws java.rmi.RemoteException;
    public void SetInform(int player , int role)throws java.rmi.RemoteException;
    public int GetInform(int player)throws java.rmi.RemoteException;
    public void move(int player , int x) throws java.rmi.RemoteException;
    public int Getmove(int player)throws java.rmi.RemoteException;
    public int srverMove(int s)throws java.rmi.RemoteException;
}

