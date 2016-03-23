
//play the audio
import java.net.* ;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.Line;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.TargetDataLine;

public class receiveAndPlay extends Thread{
	
	static int lostPackets=0;
	private final static int packetsize = 1000 ;
	int receiveport;
	boolean stopCapture = false;
	ByteArrayOutputStream byteArrayOutputStream;
	AudioFormat audioFormat;
	TargetDataLine targetDataLine;
	AudioInputStream audioInputStream;
	SourceDataLine sourceDataLine;
	byte [] tempBuffer=new byte [packetsize];
	String sendIP;
	
	
   public receiveAndPlay( int receiveport,String sendIP ){
	    this.sendIP=sendIP ;
		this.receiveport=receiveport;
		(new PrintLost()).start();
      
  }
  
  public void run(){
	try(MulticastSocket receivesocket = new MulticastSocket( receiveport );){
	    InetAddress host = InetAddress.getByName( sendIP ) ;
		receivesocket.joinGroup(host);
		int packetNum=0;
		 captureAudio(tempBuffer);
         for( ;;){
            try{
				
			 DatagramPacket receivepacket = new DatagramPacket( tempBuffer, packetsize) ;//recoverable
             receivesocket.receive( receivepacket ) ;
			 //take the packet number
			 packetNum=((Record.p).outputNumber(receivepacket.getData()));
			 //check it should be drpped or not else update last packet number
			 //out of order
			 if((Record.p).shouldDrop(packetNum)==true){
				 System.out.println("Packet Dropped");
			 }
			 else{
				
			//check lost packets
			lostPackets+=(packetNum-(Record.p).inNum-1);
			//if packet loss for out of order will be printed
			if((packetNum-(Record.p).inNum-1)>0){ 
			 System.out.println(" lost Packets:"+ lostPackets);
			}
			 
			 //System.out.println( "received:"+receivepacket.getAddress() + " " + receivepacket.getPort()
								//+" "+((Record.p).inNum)) ;
			 (Record.p).inNum=packetNum;
			 captureAndPlay(tempBuffer);//play packet
			 }
			} catch( Exception e1 ){System.out.println( "E1"+e1 ) ;}
			
	    }	
	 }
     catch( Exception e ){System.out.println( "E2"+e ) ; } 
  }
  
  private void captureAndPlay(byte []tempBuffer) {
      
        stopCapture = false;
        try {
                    sourceDataLine.write(tempBuffer, 0, packetsize);   //playing audio available in tempBuffer
            
        } catch (Exception e) {
            System.out.println("E3"+e);
        }
    }
	
	private void captureAudio(byte []tempBuffer) {
    
    try {

        audioFormat = getAudioFormat();     //get the audio format

        DataLine.Info dataLineInfo1 = new DataLine.Info(SourceDataLine.class, audioFormat);
        sourceDataLine = (SourceDataLine) AudioSystem.getLine(dataLineInfo1);
        sourceDataLine.open(audioFormat);
        sourceDataLine.start();
        
        //Setting the maximum volume
        FloatControl control = (FloatControl)sourceDataLine.getControl(FloatControl.Type.MASTER_GAIN);
        control.setValue(control.getMaximum());

        //captureAndPlay(tempBuffer); //playing the audio

    } catch (LineUnavailableException e) {
        System.out.println(e);
        System.exit(0);
    }
  
}

private AudioFormat getAudioFormat() {
    float sampleRate = 16000.0F;
    int sampleSizeInBits = 16;
    int channels = 2;
    boolean signed = true;
    boolean bigEndian = true;
    return new AudioFormat(sampleRate, sampleSizeInBits, channels, signed, bigEndian);
}
}