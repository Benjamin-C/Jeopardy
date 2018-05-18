package benjaminc.jeopardy;

import java.util.List;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import gnu.io.NRSerialPort;

public class Serial {

	private String portName;
	private SerialEvent onDataAvaliable;
	private int baudRate;
	private NRSerialPort serial;
	private DataInputStream ins;
	private DataOutputStream outs;
	private Thread listener;
	private volatile boolean isListening;
	
	public Serial(String portName, int baudRate, SerialEvent onDataAvaliable) {
		this.portName = portName;
		this.onDataAvaliable = onDataAvaliable;
		this.baudRate = baudRate;
		serial = new NRSerialPort(portName, baudRate);
		//serial.connect();
		//ins = new DataInputStream(serial.getInputStream());
		//outs = new DataOutputStream(serial.getOutputStream());
		listener = null;
		isListening = false;
	}
	
	public static List<String> getPorts() {
		List<String> ports = new ArrayList<String>();
		for(String s:NRSerialPort.getAvailableSerialPorts()){
			ports.add(s);
		}
		return ports;
	}
	
	public void print(String data) {
		
	}
	public void println(String data) {
		print(data + "\n");
	}
	
	public void startListening() {
		if(listener != null && !listener.isAlive()) {
			System.out.println("Starting listenign");
			isListening = true;
			listener = new Thread() {
				@Override
				public void run() { // Do thread stuff
					while(isListening) { // If listening
						try {
							if(ins.available() > 0) { // If there is data to read
								try {
									onDataAvaliable.onDataAvaliable(ins.readByte()); // Send the data to the reciver
								} catch (IOException e) {
									onDataAvaliable.IOException(e); // send an IOException to the reciever if nessary
								}
							}
						} catch (IOException e) {
							onDataAvaliable.IOException(e); // send an IOException to the reciever if nessary
						}
					} // Will close if not listening
				}
			};
		}
	}
	
	public void stopListening() {
		isListening = false;
		//listener = null;
	}
	
	public boolean checkListening() {
		return listener.isAlive();
	}
	public void close() {
		serial.disconnect();
	}
}
