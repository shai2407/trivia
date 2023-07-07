
public class TriviaGame {

	private int score;

	public TriviaGame(){
		this.score = 0;
	}

	public void right(){
		this.score += 10;
	}
	public void wrong(){
		this.score -= 5;
	}
	public void reset(){
		this.score = 0;
	}
	public int getScore(){
		return Math.max(this.score,0);
	}
}