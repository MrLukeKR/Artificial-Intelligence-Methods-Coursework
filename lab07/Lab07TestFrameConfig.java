	package com.g52aim.lab07;

import com.g52aim.HyFlexTestFrame;

public class Lab07TestFrameConfig extends HyFlexTestFrame {
	
	/*
	 * permitted run time(s) = { 60s }
	 */
	protected final long RUN_TIME_IN_SECONDS = 60;
	
	/*
	 * permitted problem domain(s) = { SAT, BP }
	 */
	protected final String[] PROBLEM_DOMAINS = { "SAT", "BP" };
	
	/*
	 * permitted instance ID's:
	 * 
	 * 		SAT = { 5,11 }
	 * 		BP  = { 1,11 }
	 */
	protected final int[][] INSTANCE_IDs = { { 5, 11 }, { 1, 11 } };
	
	/*
	 * permitted default score = { 5 }.
	 */
	protected final int DEFAULT_SCORE = 5;
	
	/*
	 * permitted lower bounds = { 0, 1 }.
	 */
	protected final int LOWER_BOUND = 1;
	
	/*
	 * permitted upper bounds = { 10 }.
	 */
	protected final int UPPER_BOUND = 10;
	
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
