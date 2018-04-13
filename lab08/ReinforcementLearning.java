package com.g52aim.lab08;

public interface ReinforcementLearning {

	public void incrementScore(int heuristic_id);
	
	public void decrementScore(int heuristic_id);
	
	public int getScore(int heuristic_id);
	
	public int getTotalScore();
	
	public int selectNextHeuristic();
}
