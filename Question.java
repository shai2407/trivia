import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class Question{

	private String question;
	private String ans1;
	private String ans2;
	private String ans3;
	private String ans4;
	private String corrAns;
	private int [] asked;
	final int OPTIONS = 4;

	public Question() {
		question = "";
		ans1 = "";
		ans2 = "";
		ans3 = "";
		ans4 = "";
		corrAns = "";
		int numOfQuestions = calcNumOfQuestion();
		asked = new int[numOfQuestions];

	}

	// set random question
	public void setQuestion() {		
		if (isFull()) 
			return;
		int numOfQuestions = calcNumOfQuestion();
		// choose random question
		int randomLine = ThreadLocalRandom.current().nextInt(0, numOfQuestions);
		// avoid same question twice
		while(inArr(randomLine)) {
			randomLine = ThreadLocalRandom.current().nextInt(0, numOfQuestions);
		}
		try{
			Scanner f = new Scanner (new File("trivia.txt"));
			for(int i = 0; i < (OPTIONS+1)*randomLine; i++)
			{
				f.nextLine();
			}
			// set question and answers
			question = f.nextLine();
			String a = f.nextLine();
			String b = f.nextLine();
			String c = f.nextLine();
			String d = f.nextLine();
			corrAns = a; 
			// shuffle the answers order
			randomOrder(a,b,c,d);
			f.close();
		}
		catch(IOException e){
			System.out.println("Error");
		}
		// update which question chosen		
		int j = 0;
		while(asked[j] != 0 ){
			j++;
		}
		asked[j] = randomLine;
	}

	public boolean isCorrect(String guess) {
		return corrAns.compareTo(guess) == 0;
	}

	public String getQuestion() {
		return question;
	}

	public String getAns(int num){
		if (num == 1)
			return this.ans1;
		if (num == 2)
			return this.ans2;
		if (num == 3)
			return this.ans3;
		return this.ans4;
	}

	public String getCorAns(){
		return corrAns;
	}

	public boolean isFull(){
		int numOfQuestions = calcNumOfQuestion();
		for (int i =0 ; i<numOfQuestions-1; i++) {
			if(asked[i] == 0)
				return false;
		}
		return true;
	}	

	private boolean inArr(int num){
		int numOfQuestions = calcNumOfQuestion();
		for (int i =0 ; i<numOfQuestions; i++) {
			if(num == asked[i])
				return true;
		}
		return false;
	}

	// choose random order of answers
	private void randomOrder(String a,String b,String c,String d) {
		String [] arr = {a,b,c,d};
		shuffleArray(arr);
		ans1 = arr[0];
		ans2 = arr[1];
		ans3 = arr[2];
		ans4 = arr[3];
	}

	// calc the number of questions in a file
	private int calcNumOfQuestion() {
		int numOfQuestions = 0;
		try{
			Scanner s = new Scanner (new File("trivia.txt"));
			while(s.hasNext())
			{
				numOfQuestions++;
				s.nextLine();
			}
			s.close();
		}
		catch(IOException e) {
			System.out.println("Error");
		}
		numOfQuestions = (numOfQuestions / (OPTIONS+1)) - 1;
		return numOfQuestions;
	}
	
	private void shuffleArray(String[] ar)
	{
		Random rnd = ThreadLocalRandom.current();
		for (int i = ar.length - 1; i > 0; i--)
		{
			int index = rnd.nextInt(i + 1);
			// swap
			String a = ar[index];
			ar[index] = ar[i];
			ar[i] = a;
		}
	}
}