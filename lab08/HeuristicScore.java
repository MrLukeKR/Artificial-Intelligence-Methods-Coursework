package com.g52aim.lab08;

public class HeuristicScore {
	
	private int heuristic_id;
	
	private int score;
	
	public HeuristicScore(int heuristic_id, int default_score) {
		
		this.heuristic_id = heuristic_id;
		this.score = default_score;
	}
	
	public int getHeuristicId() {
		
		return this.heuristic_id;
	}
	
	public int getScore() {
		
		return this.score;
	}
	
	public void setScore(int score) {
		
		this.score = score;
	}

}
