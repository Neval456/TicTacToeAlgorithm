import java.io.*;
import javax.bluetooth.*;
import lejos.nxt.*;
import lejos.nxt.comm.*;

public class Main {
	
	public static String connected = "Connected";
    public static String waiting = "Waiting...";
    public static NXTConnection connection;
	public static void main(String[] args) {
		
		LCD.drawString(waiting,0,0);
	    connection = Bluetooth.waitForConnection(); 
	    LCD.clear();
	    LCD.drawString(connected,0,0);
		Board board = new Board();
		
	}

}
