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
	
	public Serial(String portName, int baudRate, SerialEvent onDataAvaliable) throws NullPointerException {
		this.portName = portName;
		this.onDataAvaliable = onDataAvaliable;
		this.baudRate = baudRate;
		serial = new NRSerialPort(portName, baudRate);
		serial.connect();
		try {
			ins = new DataInputStream(serial.getInputStream());
			outs = new DataOutputStream(serial.getOutputStream());
		} catch(NullPointerException e) {
			System.out.println(e.getMessage());
			throw new NullPointerException("Crashed, the serial port is probibly busy. Try closing all Jeopardy windows then try again.");
		}
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
		System.out.println(listener);
		if(listener == null || !listener.isAlive()) {
			System.out.println("Starting listenign");
			isListening = true;
			listener = new Thread("SerialListener") {
				@Override
				public void run() { // Do thread stuff
					while(isListening) { // If listening
						try {
							if(ins.available() > 0) { // If there is data to read
								try {
									byte b = ins.readByte();
									System.out.println(ins.available());
									System.out.println(b);
									onDataAvaliable.onDataAvaliable(b); // Send the data to the reciver
								} catch (IOException e) {
									onDataAvaliable.IOException(e); // send an IOException to the reciever if nessary
								}
							}
						} catch (IOException e) {
							onDataAvaliable.IOException(e); // send an IOException to the reciever if nessary
						}
						try {
							Thread.sleep(1);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					} // Will close if not listening
				}
			};
			listener.start();
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
