package com.g52aim.lab00;

import java.util.Random;

import g52aim.domains.chesc2014_SAT.SAT;
import g52aim.satheuristics.SATHeuristic;

/**
 *
 * @author Warren G. Jackson
 */
public class RandomBitFlipHeuristic extends SATHeuristic {

	public RandomBitFlipHeuristic(Random random) {
		super(random);
	}

	@Override
	public void applyHeuristic(SAT problem) {
		
		int bitIndex = random.nextInt(problem.getNumberOfVariables());
		problem.bitFlip(bitIndex);
	}

	@Override
	public String getHeuristicName() {
		
		return "Random Bit Flip";
	}

}
