import java.util.concurrent.TimeUnit;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class TriviaController{

	final int OPTIONS = 4;
	final int BUTTON_WIDTH = 900;
	final int BUTTON_HEIGHT = 75;
	private Question q1;
	private TriviaGame g;
	private boolean answered;
	private Button[] btns;

	@FXML
	private GridPane grid;
	@FXML
	private Label qu;

	@FXML
	private Button gameon;

	@FXML
	// finish
	void fin(ActionEvent event) {
		qu.setText("Your score is:\t" + g.getScore());
		gameon.setVisible(false);
		for(int i = 0 ; i < OPTIONS; i++) {
			btns[i].setVisible(false);
		}
	}

	@FXML
	// new game
	void newGa(ActionEvent event) {
		gameon.setVisible(true);
		initialize();
	}

	@FXML
	// next question
	void next(ActionEvent event) {
		// didn't answer
		if(answered == false)
			g.wrong();
		// no more questions
		if(q1.isFull()) {
			qu.setText(q1.getQuestion());
			end();
		}
		// answered
		else {
			q1.setQuestion();
			qu.setText(q1.getQuestion());
			for (int i = 0 ; i < OPTIONS; i++) {
				btns[i].setStyle("");
				btns[i].setStyle("-fx-font-size:21");
				btns[i].setText(q1.getAns(i));
			}
			answered = false;
		}
	}

	public void initialize() {
		// initialize variables
		q1 = new Question();
		g = new TriviaGame();
		answered = false;

		// set question
		q1.setQuestion();
		qu.setText(q1.getQuestion());
		// create button for each option
		btns = new Button[OPTIONS];
		for(int i = 0 ; i<OPTIONS;i++) {
			btns[i] = new Button(q1.getAns(i));
			btns[i].setStyle("-fx-font-size:21");
			btns[i].setMinSize(BUTTON_WIDTH, BUTTON_HEIGHT);
			grid.add(btns[i], i/OPTIONS, i);
			btns[i].setOnAction(new EventHandler<ActionEvent>(){

				@Override
				public void handle(ActionEvent event) {
					handleButton(event);
				}
			});
		}

	}


	private void handleButton(ActionEvent event){

		answered = true;
		Button b = (Button)event.getSource();
		String guess = b.getText();
		// correct
		if(q1.isCorrect(guess)) {
			b.setStyle("-fx-background-color: #0ff000");
			g.right();
		}
		//incorrect
		else {
			qu.setText("The answer was: " + q1.getCorAns());
			b.setStyle("-fx-background-color: #ff0000;");
			g.wrong();
			for(int i = 0 ; i<OPTIONS;i++) {
				if(q1.isCorrect(btns[i].getText())) {
					btns[i].setStyle("-fx-background-color: #0ff000");
				}
			}
		}
		sleep();
	}

	// delay the result for more excitement
	private void sleep(){
		try {
			TimeUnit.SECONDS.sleep(1);
		} 
		catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	// no more questions
	private void end() {
		qu.setText("No More Questions.\n"+"Your score is:\t" + g.getScore());
		gameon.setVisible(false);
		for(int i = 0 ; i< OPTIONS;i++) {
			btns[i].setVisible(false);
		}
	}
}