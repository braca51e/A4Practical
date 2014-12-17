package de.uni_stuttgart.ipvs.ids.globalstate;

import java.io.Serializable;

@SuppressWarnings("serial")
public class MarkerMessage implements Serializable{
	int sender;
	
	public MarkerMessage(int sender){
		this.sender = sender;
	}
	
	public int getSender(){
		return sender;
	}
}
