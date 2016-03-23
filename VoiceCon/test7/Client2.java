//client 2

public class Client2 extends Thread{
	
	public static void main(String[] args){
		
		if(args.length != 2){
			System.out.println("usage <IP>");
			
		}
		new Record(2002,2001,args[0]).start();//start recording and sending
		new receiveAndPlay(2003,args[0]).start();//waiting for packets and play
	}
	
	
}