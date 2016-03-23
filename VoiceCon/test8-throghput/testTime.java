import java.io.*;
import java.io.IOException;
import java.util.Scanner;

public class testTime{
	
	
	public static void main(String[] args){
		if(args.length <2){
			System.out.println("usage<output.txt> <input.txt>");
			System.exit(0);
		}
		
		String [][]OutTime=new String[100][3];
		Scanner sc2 = null;
		//output file read and put vals to an array
		try {
        sc2 = new Scanner(new File(args[0]));
		} catch (FileNotFoundException e) {
        System.out.println(e);  
		}
		int i=0;
		sc2.nextLine();
		while (sc2.hasNextLine() && i<100) {
			Scanner s2 = new Scanner(sc2.nextLine());
			//received time
				String s = s2.next();
				OutTime[i][2]=s;
			//packetno
			    s = s2.next();
				OutTime[i][0]=s;
			i++;
		}
		//input file read and put valus to an array
		try {
        sc2 = new Scanner(new File(args[1]));
		} catch (FileNotFoundException e) {
        System.out.println(e);  
		}
		i=0;
		while (sc2.hasNextLine() && i<100) {
			Scanner s2 = new Scanner(sc2.nextLine());
			//send time
				String t = s2.next();
				String no = s2.next();//System.out.println(no+" "+OutTime[i][0]);
				if(no.equals(OutTime[i][0])){
					OutTime[i][1]=t;
					i++;
				}	
			}
			
	/*for(i=0;i<100;i++){
		for(int j=0;j<3;j++)
			System.out.print(OutTime[i][j]+"\t");
		System.out.println();
	}*/
	int average=0;
	for(i=0;i<100;i++){
		average+=Long.parseLong(OutTime[i][2])-Long.parseLong(OutTime[i][1]);
		//System.out.println(average);
		
	}
	System.out.println("average time to send a packet:"+ average/100);
	}
	
}