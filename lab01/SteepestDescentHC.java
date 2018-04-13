package com.g52aim.lab01;


import java.util.Random;

import g52aim.domains.chesc2014_SAT.SAT;
import g52aim.satheuristics.SATHeuristic;

public class SteepestDescentHC extends SATHeuristic {

	public SteepestDescentHC(Random random) {
		
		super(random);
	}

	/**
	  * STEEPEST DESCENT HILL CLIMBING LECTURE SLIDE PSEUDO-CODE
	  *
	  * bestEval = evaluate(currentSolution);
	  * improved = false;
	  * 
	  * for(j = 0; j < length[currentSolution]; j++) {
	  * 
	  *     bitFlip(currentSolution, j); 				//flips j^th bit of current solution
	  *     tmpEval = evaluate(currentSolution);
	  *
	  *     if(tmpEval < bestEval) { 					// remember the bit which yields the best value after evaluation
	  *
	  *         bestIndex = j;
	  *         bestEval = tmpEval; 					//record best achievable solution objective value
	  *         improved = true;
	  *     }
	  *
	  *     bitFlip(currentSolution, j); 				//go back to the initial current solution
	  * }
	  *
	  * if(improved) { bitFlip(currentSolution, bestIndex); }
	  *
	  * @param problem The problem to be solved.
	  */
	public void applyHeuristic(SAT problem) {
		//SETUP
		int bestIndex = 0;
		double tmpEval;
		double bestEval = problem.getObjectiveFunctionValue(CURRENT_SOLUTION_INDEX);	//bestEval = evaluate(currentSolution);
		boolean improved = false;														//improved = false;
		
		//DO HEURISTIC
		for (int j = 0; j < problem.getNumberOfVariables(); j++){						//for(j = 0; j < length[currentSolution]; j++) {
			problem.bitFlip(j);															//bitFlip(currentSolution, j);
			tmpEval = problem.getObjectiveFunctionValue(CURRENT_SOLUTION_INDEX);		//tmpEval = evaluate(currentSolution);
			
			if(tmpEval < bestEval){														//if(tmpEval < bestEval) {
				bestIndex = j;															//bestIndex = j;
				bestEval = tmpEval;														//bestEval = tmpEval; 			//record best achievable solution objective value
				improved = true;														//improved = true;
			}
			problem.bitFlip(j);															// bitFlip(currentSolution, j); //go back to the initial current solution
		}
		
		if(improved) problem.bitFlip(bestIndex);										//if(improved) { bitFlip(currentSolution, bestIndex);
	}

	public String getHeuristicName() {
		
		return "Steepest Descent HC";
	}

}
