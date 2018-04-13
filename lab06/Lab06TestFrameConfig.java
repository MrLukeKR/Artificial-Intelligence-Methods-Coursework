package com.g52aim.lab06;

import com.g52aim.HyFlexTestFrame;

public class Lab06TestFrameConfig extends HyFlexTestFrame {
	
	/*
	 * permitted run time(s) = { 60s }
	 */
	protected final long RUN_TIME_IN_SECONDS = 60;
	
	/*
	 * permitted problem domain(s) = { SAT }
	 */
	protected final String[] PROBLEM_DOMAINS = { "SAT" };
	/*
	 * permitted instance ID's:
	 * 		SAT = { 3,4,5,10,11 }
	 */
	protected final int[][] INSTANCE_IDs = { {3, 4, 5, 10, 11} };
	
	@Override
	public String[] getDomains() {
		
		return this.PROBLEM_DOMAINS;
	}

	@Override
	public int[][] getInstanceIDs() {
		
		return this.INSTANCE_IDs;
	}

	@Override
	public long getRunTime() {
		
		return (MILLISECONDS_IN_TEN_MINUTES * RUN_TIME_IN_SECONDS) / 600;
	}

}
