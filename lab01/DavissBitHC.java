package com.g52aim.lab01;

import java.util.ArrayList;
// potential packages which may need importing
import java.util.Collections;
import java.util.Random;

import g52aim.domains.chesc2014_SAT.SAT;
import g52aim.helperfunctions.ArrayMethods;
import g52aim.satheuristics.SATHeuristic;

public class DavissBitHC extends SATHeuristic {

	public DavissBitHC(Random random) {
		
		super(random);
	}

	/**
	  * DAVIS's BIT HILL CLIMBING LECTURE SLIDE PSEUDO-CODE
	  *
	  * bestEval = evaluate(currentSolution);
	  * perm = createRandomPermutation();
	  * for(j = 0; j < length[currentSolution]; j++) {
	  * 
	  *     bitFlip(currentSolution, perm[j]); 		//flips j^th bit from permutation of solution producing s' from s
	  *     tmpEval = evaluate(currentSolution);
	  *
	  *     if(tmpEval < bestEval) { 				// if there is improvement (strict improvement)
	  *
	  *         bestEval = tmpEval; 				// accept the best flip
	  *         
	  *     } else { 								// if there is no improvement, reject the current bit flip
	  *
	  *          bitFlip(solution, perm[j]); 		//go back to s from s'
	  *     }
	  * }
	  *
	  * @param problem The problem to be solved.
	  */
	public void applyHeuristic(SAT problem) {
		//SETUP
		double tmpEval;
		ArrayList<Integer> perm = new ArrayList<Integer>();								//Create new int array
		for(int i = 0; i < problem.getNumberOfVariables(); i++) perm.add(i);			//Add all indices to it
		
		double bestEval = problem.getObjectiveFunctionValue(CURRENT_SOLUTION_INDEX); 	//bestEval = evaluate(currentSolution);
		Collections.shuffle(perm, random);												//perm = createRandomPermutation();
		
		//DO HEURISTIC:
		for(int j = 0; j < problem.getNumberOfVariables(); j++){						//for(j = 0; j < length[currentSolution]; j++) {
			problem.bitFlip(perm.get(j));											//bitFlip(currentSolution, perm[j]);
			tmpEval = problem.getObjectiveFunctionValue(CURRENT_SOLUTION_INDEX);		//tmpEval = evaluate(currentSolution);
			
			if(tmpEval <= bestEval){														//if(tmpEval < bestEval) {
				bestEval = tmpEval;														//bestEval = tmpEval; 		  // accept the best flip
			}else{																		//} else { 					  // if there is no improvement, reject the current bit flip
				problem.bitFlip(perm.get(j));										//bitFlip(solution, perm[j]); //go back to s from s'
			}	
		}
	}

	public String getHeuristicName() {

		return "Davis's Bit HC (Improving)";
	}

}
