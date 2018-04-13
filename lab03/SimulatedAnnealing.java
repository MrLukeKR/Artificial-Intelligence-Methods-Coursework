package com.g52aim.lab03;


import java.util.Random;

import com.g52aim.lab02.RandomMutation;

import g52aim.domains.chesc2014_SAT.SAT;
import g52aim.satheuristics.SATHeuristic;
import g52aim.searchmethods.SinglePointSearchMethod;


public class SimulatedAnnealing extends SinglePointSearchMethod {
	
	private CoolingSchedule cs;
	
	public SimulatedAnnealing(CoolingSchedule schedule, SAT problem, Random random) {
		
		super(problem, random);
		
		this.cs = schedule;
	}

	/**
	 *
	 * PSEUDOCODE for Simulated Annealing:
	 *
	 * INPUT : T_0 and any other parameters of the cooling schedule
	 * s_0 = generateInitialSolution();
	 * Temp <- T_0;
	 * s_{best} <- s_0;
	 * s* <- s_0;
	 *
	 * REPEAT
	 *     s' <- bitFlip(s*);
	 *     delta <- f(s') - f(s*);
	 *     r <- random \in [0,1];
	 *     IF delta < 0 OR r < P(delta, Temp) THEN
	 *         s* <- s';
	 *     ENDIF
	 *     s_{best} <- updateBest();
	 *     Temp <- advanceTemperature();
	 * UNTIL termination conditions are satisfied;
	 *
	 * RETURN s_{best};
	 */
	protected void runMainLoop() {
		//SETUP
		int bitFlipIndex;
		double delta;
		double r;
		double boltzmanProbability;
		double s;
		double sPrime;
		
		bitFlipIndex = random.nextInt(problem.getNumberOfVariables());					//Determine bitFlip position
		
		//SIMULATE ANNEALING
		
		s = problem.getObjectiveFunctionValue(CURRENT_SOLUTION_INDEX);					//Evaluate s* [f(s*)]
		problem.bitFlip(bitFlipIndex);														//s' <- bitFlip(s*)
		sPrime = problem.getObjectiveFunctionValue(CURRENT_SOLUTION_INDEX);				//Evaluate s' [f(s')]
		
		delta = sPrime - s;																//delta <- f(s') - f(s*)
		r = random.nextDouble();														//r <- random in [0,1];

		boltzmanProbability = Math.exp((-delta) / cs.getCurrentTemperature());			//P(delta, Temp)
		
		if (delta < 0 || r < boltzmanProbability) 										//IF delta < 0 OR r < P(delta, Temp) THEN
			; 																				//Accept
		else																			//ELSE
			problem.bitFlip(bitFlipIndex); 													//Reject
																						//s_{best} <- updateBest(); [Handled by framework]
		cs.advanceTemperature();														//Temp <- advanceTemperature()
	}
		
	public String toString() {
		return "Simulated Annealing with " + cs.toString();
	}
}
