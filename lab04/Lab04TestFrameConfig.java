package com.g52aim.lab04;

import com.g52aim.TestFrameConfig;

public class Lab04TestFrameConfig extends TestFrameConfig {

	/*
	 * permitted values = { Mode.GA, Mode.MA }
	 * 		Mode.GA = genetic algorithm ( local search <- NOOP )
	 * 		Mode.MA = memetic algorithm ( local search <- DBHC_IE )
	 */
	protected final Mode MODE = Mode.GA;
	
	/*
	 * permitted total runs = 30
	 */
	protected final int TOTAL_RUNS  = 30;
	
	/*
	 * permitted instance ID's = 1
	 */
	protected final int INSTANCE_ID = 1;
	
	/*
	 * population size = 8
	 */
	 public final int POP_SIZE = 8;
	
	@Override
	public int getTotalRuns() {
		return this.TOTAL_RUNS;
	}

	@Override
	public int getInstanceId() {
		return this.INSTANCE_ID;
	}

	@Override
	public int getRunTime() {
		return Integer.MAX_VALUE;
	}

	@Override
	public String getMethodName() {
		return "Genetic Algorithm";
	}
	
	public int getPopulationSize() {
		return this.POP_SIZE;
	}
	
	@Override
	public String getBoxPlotTitle() {
		
		String config = getConfigurationAsString();
		return "Results for " + getMethodName() + " ( " + MODE.toString() + "_Mode ) given " + MODE.getGenerations() +
				" generations for solving instance ID " + getInstanceId() + " over " +
				getTotalRuns() + " runs" + (config == null ? "." : (" with " + config + "."));
	}

	@Override
	public String getConfigurationAsString() {
		return "Population size = " + getPopulationSize();
	}
	
	public enum Mode {
		
		GA (2500),
		MA (75);
		
		private final int generations;
		
		Mode(int generations) {
			this.generations = generations;
		}
		
		public int getGenerations() {
			return generations;
		}
		
	}
}
