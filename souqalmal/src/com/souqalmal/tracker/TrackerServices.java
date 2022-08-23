package com.souqalmal.tracker;

import java.util.ArrayList;


public class TrackerServices {
	static ArrayList<String> inputs = new ArrayList<String>();
	
	public static synchronized Output inputData(String value){
		Output output = new Output();
		if(inputs.size()>0) {
			output.setOutput(inputs.get(inputs.size()-1));
		}
		inputs.add(value);
		return output;
	}
	
	public static synchronized ArrayList<String> trackInputHistory(){
		int removedItems = 0;
		if(inputs.size()>10) {
			removedItems = inputs.size() - 10;
		}
		
		if(removedItems>0) {
			for(int i=0; i<removedItems; i++) {
				inputs.remove(i);
			}
		}
		return inputs;
	}
}
