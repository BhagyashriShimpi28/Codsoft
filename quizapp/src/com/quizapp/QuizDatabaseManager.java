package com.quizapp;

import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class QuizDatabaseManager {
	private DatabaseConnector dbConnector;

    public QuizDatabaseManager() {
        dbConnector = new DatabaseConnector();
    }
    
    public Question[] getAllQuestions() {
    	List<Question> questions = new ArrayList<>();
    	
    	try {
            Connection connection = dbConnector.getConnection();
            String query = "SELECT * FROM quiz_questions";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String questionText = resultSet.getString("question_text");
                String option1 = resultSet.getString("option1");
                String option2 = resultSet.getString("option2");
                String option3 = resultSet.getString("option3");
                String option4 = resultSet.getString("option4");
                String correctAnswer = resultSet.getString("correct_answer");

                Question question = new Question(id, questionText, option1, option2, option3, option4, correctAnswer);
                questions.add(question);
            }
            
            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbConnector.closeConnection();
        }

    	return questions.toArray(new Question[0]);

    	
    }

}
