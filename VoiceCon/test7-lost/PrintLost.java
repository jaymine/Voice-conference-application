//this class print the lost packet 
public class PrintLost extends Thread{
	
	public PrintLost(){
		
	}
	public void run(){
		for(;;){
		try {
             Thread.sleep(60000);
        } catch (InterruptedException e) {}
		System.out.println(" Lost Packets:"+receiveAndPlay.lostPackets);
		}
	}
}