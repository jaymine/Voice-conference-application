public class PacketFormat{
	int inNum=0;
	int outNum=0;
	public PacketFormat(){}
	static int dataSize=996;//change according to the packet size
	
	//include a sequence number to a packet 996-999 bytes
	public byte[] inputNumber(byte [] data){
		outNum++;
		
		byte [] packet=new byte[dataSize+4];
		for(int i=0;i<dataSize;i++){
			packet[i]=data[i];
		}
		byte [] no=new byte[] {(byte)(outNum >>> 24),(byte)(outNum >>> 16),(byte)(outNum >>> 8),(byte)outNum};
		for(int i=dataSize;i<dataSize+4;i++){
			packet[i]=no[i-dataSize];
		}
		return packet;
	}
	// get the sequence number of a packet
	public int outputNumber(byte [] packet){
		byte[] number=new byte[4];
		
		for(int i=dataSize;i<dataSize+4;i++){
			number[i-dataSize]=packet[i];
		}
		int serialnum=number[0] << 24 | (number[1] & 0xFF) << 16 | (number[2] & 0xFF) << 8 | (number[3] & 0xFF);
		return serialnum;
	}
	
	//check whether the packet should drop or not
	//if we have already play a packet which sequnce no is high.then others will drop
	//consider as a lost
	public boolean shouldDrop(int newInNum){
		if(newInNum > inNum) {return false;}
		return true;
	}
	
	public static void main(String [] args){
		PacketFormat p=new PacketFormat();
		byte [] data=new byte[dataSize];
		byte [] packet= p.inputNumber(data);
		System.out.println((p.outputNumber(packet)));
		data=new byte[dataSize];
		packet= p.inputNumber(data);
		System.out.println((p.outputNumber(packet)));
		System.out.println((p.shouldDrop(0)));
		System.out.println((p.shouldDrop(3)));
	}
}