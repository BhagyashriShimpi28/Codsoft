package com.quizapp;

import java.util.concurrent.atomic.AtomicInteger;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainApp extends Application {
	private int score = 0;
   // private boolean allQuestionsCompleted;
	private AtomicInteger currentQuestionIndex;
	private Scene introScene; 
	private Question[] questions;
    private VBox quizLayout;
    private Label questionLabel;
    private RadioButton option1;
    private RadioButton option2;
    private RadioButton option3;
    private RadioButton option4;
    private ToggleGroup toggleGroup;
    private Button submitButton;
    private Button nextButton; 

	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		VBox introLayout = new VBox(10);
		introLayout.setAlignment(Pos.CENTER);
		
		Label titleLabel = new Label("Quiz Application");
		titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
		
		Label instructionsLabel = new Label("Welcome to the quiz app. Click 'Start' to begin.");
		
		Button startButton = new Button("Start");
	    startButton.setOnAction(e -> {
	    	 Question[] questions = loadQuestionsFromDatabase(); 
	    	 currentQuestionIndex = new AtomicInteger(0); // Initialize currentQuestionIndex
	            score = 0; // Reset the score
	    	 showQuizScreen(primaryStage,  questions);

	     });
 
	        introLayout.getChildren().addAll(titleLabel, instructionsLabel, startButton);
	        Scene introScene = new Scene(introLayout, 400, 200);
		 
	        primaryStage.setScene(introScene);
	        primaryStage.setTitle("Quiz Application");
	        primaryStage.show();
	}
		private Question[] loadQuestionsFromDatabase() {
			 QuizDatabaseManager databaseManager = new QuizDatabaseManager();
			    return databaseManager.getAllQuestions();	
		}
		
		private void showQuizScreen(Stage primaryStage, Question[] questions) {
			
			VBox quizLayout = new VBox(10);
	        quizLayout.setAlignment(Pos.CENTER);

	        questionLabel = new Label();
	        option1 = new RadioButton();
	        option2 = new RadioButton();
	        option3 = new RadioButton();
	        option4 = new RadioButton();
	      
	        ToggleGroup toggleGroup = new ToggleGroup();
	        option1.setToggleGroup(toggleGroup);
	        option2.setToggleGroup(toggleGroup);
	        option3.setToggleGroup(toggleGroup);
	        option4.setToggleGroup(toggleGroup);
	                  
	       	submitButton = new Button("Submit"); 
	        nextButton = new Button("Next");
	        nextButton.setDisable(true);
	
	        toggleGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
	            if (newValue != null) {
	                submitButton.setDisable(false); // Enable the submit button
	            }
	        });  
	        
	        submitButton.setOnAction(e -> {
	        	RadioButton selectedOption = (RadioButton) toggleGroup.getSelectedToggle();
	        	if (toggleGroup.getSelectedToggle() != null) {
	        		Question currentQuestion = questions[currentQuestionIndex.get()];  // Move to the next question         
	                String selectedAnswer = selectedOption.getText();
	        	 
	                if (selectedAnswer.equals(currentQuestion.getCorrectAnswer())) {
	                    score++;
	                }
	                toggleGroup.selectToggle(null);
	                submitButton.setDisable(true);
	                nextButton.setDisable(false);
	        	}
	        });
	        nextButton.setOnAction(e -> {
	            if (currentQuestionIndex.incrementAndGet() < questions.length) {
	                updateUIWithQuestion(questions[currentQuestionIndex.get()]);
	                nextButton.setDisable(true);  
	                
	            } else if (currentQuestionIndex.get() == questions.length) {
	            	showScoreScreen(primaryStage);
	            	}
	            
	        }); 
	            
	         // Run this on the JavaFX application thread
	            Platform.runLater(() -> updateUIWithQuestion(questions[currentQuestionIndex.get()]));
	            
	            quizLayout.getChildren().addAll(questionLabel,option1, option2, option3, option4, submitButton, nextButton);
	             
	        Scene quizScene = new Scene(quizLayout, 400, 400);
	        primaryStage.setScene(quizScene);
	       
	    }
		
		 private void showScoreScreen(Stage primaryStage) {
			 VBox scoreLayout = new VBox(10);
		        scoreLayout.setAlignment(Pos.CENTER);

		        Label scoreLabel = new Label("Your Score: " + score);
		        Button restartButton = new Button("Restart Quiz");

		        restartButton.setOnAction(e -> {
		            primaryStage.setScene(introScene);
		            this.introScene = primaryStage.getScene();
		        });

		        scoreLayout.getChildren().addAll(scoreLabel, restartButton);
		        Scene scoreScene = new Scene(scoreLayout, 400, 200);
		        primaryStage.setScene(scoreScene);
		}
   		 
		private void updateUIWithQuestion(Question question) {
		        questionLabel.setText(question.getQuestionText());
		        option1.setText(question.getOption1());
		        option2.setText(question.getOption2());
		        option3.setText(question.getOption3());
		        option4.setText(question.getOption4());
		    }			
}
