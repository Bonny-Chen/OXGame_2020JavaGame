import java.rmi.*;
public class Main{
    
    public static void main(String[] args) {
        Page p = new Page();
        if(args.length ==0){
            System.out.println("Usage : java Main serverIP");
        }
        p.main(args);
    }
}