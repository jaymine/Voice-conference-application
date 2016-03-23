//client 1

public class Client extends Thread{
	
	public static void main(String[] args){
		
		if(args.length < 1){
			System.out.println("usage <IP>");
			
		}
		
		new Record(2000,2001,args[0]).start();//start recording and sending
		new receiveAndPlay(2001).start();//waiting for packets and play
	}
	
	
}