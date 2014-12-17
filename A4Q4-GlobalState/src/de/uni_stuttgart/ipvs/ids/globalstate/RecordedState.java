package de.uni_stuttgart.ipvs.ids.globalstate;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import java.net.Socket;
import java.io.ObjectOutputStream;

@SuppressWarnings("serial")
public class RecordedState implements Serializable{
	
	// put your implementation here
	private double balance;

	private ArrayList<Collector> collectors;
	private ArrayList<MoneyMessage> msgs;
	private Set<Integer> activeChannels;

	public RecordedState(ArrayList<Collector> collectors) {
		msgs = new ArrayList<MoneyMessage>();
		this.collectors = new ArrayList<Collector>();
		this.collectors = collectors;
		activeChannels = new HashSet<Integer>();

	}

	public void stopRecord(int id) {
		activeChannels.remove(id);

		if (activeChannels.size() == 0) {

			for (Collector c : collectors) {
				sendState(c);
			}
		}

	}

	public void saveState(double balance, Set<Integer> neighbors) {
		this.balance = balance;

		activeChannels.addAll(neighbors);

	}

	public boolean isActive() {
		return (activeChannels.size() != 0);
	}

	public boolean isRecording(int id) {
		return activeChannels.contains(id);
	}

	public void addMessage(MoneyMessage msg) {
		msgs.add(msg);
	}

	public void reset() {
		msgs.clear();
		activeChannels.clear();
		balance = 0;
	}

	private void sendState(Collector collector) {
		try {
			Socket socket = new Socket(collector.getAddress(),
					collector.getPort());

			ObjectOutputStream out = new ObjectOutputStream(
					socket.getOutputStream());
			out.writeObject(balance);
			out.writeObject(msgs);
			out.flush();
			out.close();
			socket.close();
		} 
		catch(Exception e)
	    {
	        //System.out.println(e);
			e.printStackTrace();
	    }
	}
		
}
