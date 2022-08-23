package com.souqalmal;

import com.souqalmal.tracker.Output;
import com.souqalmal.tracker.TrackerServices;

public class Test {
	
	public static void main(String[] args) {
		String input = "M2";
		Output result = TrackerServices.inputData(input);
		
		System.out.println(result.getOutput());
		
		;
		for(String value : TrackerServices.trackInputHistory()) {
			System.out.print(value + " ");
		}
	}
}
