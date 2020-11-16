import java.rmi.*;
import java.rmi.server.*;
import java.lang.*;

public class RMIImplement extends UnicastRemoteObject implements Interface
{
    public int Role;
    public static int PlayerNum = 1;
    public static int playerInform[] = new int[2];            // [role]
    public static int Moving[] = new int[2];
    public static int s = 10;
    public RMIImplement() throws java.rmi.RemoteException
	{
		super(); 	// Use constructor of parent class
    }
    
    public synchronized int GetPlayerNum () throws java.rmi.RemoteException{
        PlayerNum = (PlayerNum + 1 )% 2;
        System.out.println(PlayerNum);
        return PlayerNum;
    }
	public synchronized int PlayerSelection(int Player , int Role) throws java.rmi.RemoteException{
        if(Player == 0){
            switch (Role) {
                case 0:
                    this.Role = 1;
                    break;
                case 1:
                    this.Role = 2;
                    break;
                case 2:
                    this.Role = 3;
                    break;
                default:
                    break;
            }
        }else{
            switch (Role) {
                case 0:
                    this.Role = 1;
                    break;
                case 1:
                    this.Role = 2;
                    break;
                case 2:
                    this.Role = 3;
                    break;
                default:
                    break;
            }
        }

        return this.Role;
    }

    public synchronized void SetInform(int player , int role)throws java.rmi.RemoteException{
        playerInform[player-1] = role;
    }

    public synchronized int GetInform(int player)throws java.rmi.RemoteException{
        return playerInform[player-1];
    }

    public synchronized void move(int player , int x)throws java.rmi.RemoteException{
        // System.out.println(player+" moving to "+x);
        Moving[player-1] = x;
    }

    public synchronized int Getmove(int player)throws java.rmi.RemoteException{
        if(Moving[player-1] == 0){
            Moving[player-1] = 500;
        }
        return Moving[player-1];
    }

    public synchronized int srverMove(int s)throws java.rmi.RemoteException{
        System.out.println("ServerMove");
        this.s = s - this.s;
        return this.s;
    }

}