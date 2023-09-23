package Grade_Calculator;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class StudentGradeCalculator extends Application{
	private List<Subject> subjects = new ArrayList<>();
	private TableView<Subject> table = new TableView<>();
	private Label totalMarksLabel = new Label("Total Marks: ");
	private Label averagePercentageLabel = new Label("Average Percentage: ");
    private Label gradeLabel = new Label("Grade: ");
    private VBox subjectFieldsContainer = new VBox(10);  
    private int rowCount = 3; 
    
    private Button addRowButton;
    private Button calculateButton;
    private Button resetButton; 
    
    public static void main(String[] args) {
        launch(args);
    }

	
	@Override
	public void start(Stage primaryStage) throws Exception {
		 primaryStage.setTitle("Student Grade Calculator");
		
		 TableColumn<Subject, String> nameColumn = new TableColumn<>("Subject Name");
	        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

	        TableColumn<Subject, Integer> marksColumn = new TableColumn<>("Marks");
	        marksColumn.setCellValueFactory(new PropertyValueFactory<>("marks"));

	        
	      buttons
	        addRowButton = new Button("Add Row");
	        addRowButton.setOnAction(e -> addRow());
	        addRowButton.setStyle( 
	            "-fx-background-color: #17a2b8; " +              
	            "-fx-text-fill: #ffffff; " +                    
	            "-fx-font-weight: bold; " +                    
	            "-fx-background-radius: 6px; "   +           
	            "-fx-padding: 8px 16px; "                    
	        );
	       
	        	
	        calculateButton = new Button("Calculate");
	        calculateButton.setOnAction(e -> calculateGrades());
	        calculateButton.setStyle(
	            "-fx-background-color:  #28a745; " +
	            "-fx-text-fill: #ffffff; " +
	            "-fx-font-weight: bold; " +
	            "-fx-background-radius: 6px; " +
	            "-fx-padding: 8px 16px; " 
	            
	        );
	        
	        resetButton = new Button("Reset"); 
	        resetButton.setOnAction(e -> resetCalculator()); // Set action handler
	        resetButton.setStyle(
	        	    "-fx-background-color: #dc3545; " +    
	        	    "-fx-text-fill: #ffffff; " +          
	        	    "-fx-font-weight: bold; " +         
	        	    "-fx-background-radius: 6px; " +     
	        	    "-fx-padding: 8px 16px; "          
	        	);
	        
	     	// layout    
	        BorderPane root = new BorderPane();
	        root.setCenter(createCenterPane());
	        
	        Scene scene = new Scene(root, 400, 400);
	        primaryStage.setScene(scene);
	        primaryStage.show();
	        
	} 

			private GridPane createCenterPane() {
	        	GridPane centerPane = new GridPane();
	            centerPane.setHgap(10);
	            centerPane.setVgap(10);
	            centerPane.setPadding(new Insets(10, 10, 10, 10));
	            centerPane.setAlignment(Pos.CENTER); 
	            
	     	        
	        VBox vbox = new VBox(10);
	        vbox.setPadding(new Insets(10, 10, 10, 10));

	        
	        //  title
	        Label titleLabel = new Label("Grade Calculator");
	        titleLabel.setStyle("-fx-font-size: 40px; -fx-font-weight: bold; -fx-text-fill: skyblue; -fx-font-family: italic;");
	           
	        HBox headerBox = new HBox(90);
	        Label nameLabel = new Label("Subject Name");
	        Label marksLabel = new Label("Marks (out of 100)");
	        nameLabel.setStyle("-fx-font-family: 'Arial'; -fx-font-weight: bold;");
	        marksLabel.setStyle("-fx-font-family: 'Arial'; -fx-font-weight: bold;");
	        headerBox.getChildren().addAll(nameLabel, marksLabel);
	        vbox.getChildren().addAll(
	                titleLabel,
	                headerBox
	        );
	        
	          
	        for (int i = 0; i < rowCount; i++) {
	            subjectFieldsContainer.getChildren().add(createSubjectRow());
	        }
	        
	        HBox buttonsBox = new HBox(10);
	        buttonsBox.getChildren().addAll(addRowButton, calculateButton,resetButton);
  
	        
	        VBox resultBox = new VBox(10);
	        Label resultTextLabel = new Label("Result:");
	        resultTextLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: green;");
	        totalMarksLabel.setStyle("-fx-font-size: 16px; -fx-font-family: 'Arial'; -fx-font-weight: bold;");
	        averagePercentageLabel.setStyle("-fx-font-size: 16px; -fx-font-family: 'Arial'; -fx-font-weight: bold;");
	        gradeLabel.setStyle("-fx-font-size: 16px; -fx-font-family: 'Arial'; -fx-font-weight: bold;");
	        resultBox.getChildren().addAll(
	        		resultTextLabel,
	        		totalMarksLabel,
	                averagePercentageLabel,
	                gradeLabel
	        );
	        vbox.getChildren().addAll(
	                subjectFieldsContainer,
	                buttonsBox,
	                resultBox
	        );
	        
	        centerPane.getChildren().add(vbox);
	        return centerPane;
	    }

	private HBox createSubjectRow() {
        HBox hbox = new HBox(10); 

  
        TextField nameField = new TextField();
        TextField marksField = new TextField();

        hbox.getChildren().addAll( nameField, marksField);
        return hbox;
    }

	private void addRow() {
        if (rowCount < Integer.MAX_VALUE) {
        	subjectFieldsContainer.getChildren().add(createSubjectRow());
            rowCount++;
        }
    }

	
	private void calculateGrades() {
        int totalMarks = 0;
        int totalSubjects = 0;

        for (Node node : subjectFieldsContainer.getChildren()) {
            if (node instanceof HBox) {
                HBox row = (HBox) node;
                TextField marksField = (TextField) row.getChildren().get(1);

                try {
                    int marks = Integer.parseInt(marksField.getText());
                    
                    if (marks < 0 || marks > 100) {
                        showAlert("Marks should be between 0 and 100.");
                        return; // Stop calculation if invalid input is encountered
                    }

                    
                    totalMarks += marks;
                    totalSubjects++;
                } catch (NumberFormatException e) {
                    showAlert("Invalid marks. Please enter a valid number.");
                    return; // Stop calculation if invalid input is encountered
                }
            }
		}
        if (totalSubjects == 0) {
            showAlert("No subjects entered.");
            return;     
        }
			
			double averagePercentage = (double) totalMarks / totalSubjects;
	        String grade = calculateGrade(averagePercentage);
	        
	        totalMarksLabel.setText("Total Marks: " + totalMarks);
	        averagePercentageLabel.setText("Average Percentage: " + String.format("%.2f%%", averagePercentage));
	        gradeLabel.setText("Grade: " + grade);
	  
		}	
	private void resetCalculator() {
		// Clear input fields and labels
        subjectFieldsContainer.getChildren().clear();
        totalMarksLabel.setText("Total Marks: ");
        averagePercentageLabel.setText("Average Percentage: ");
        gradeLabel.setText("Grade: ");
        rowCount = 3;
        for (int i = 0; i < rowCount; i++) {
            subjectFieldsContainer.getChildren().add(createSubjectRow());
        }	
	
	}

	
	private String calculateGrade(double averagePercentage) {
		if (averagePercentage > 90) {
            return "Distinction";
        } else if (averagePercentage >= 85 && averagePercentage <=90 ) {
            return "First Class";
        } else if (averagePercentage >= 75 && averagePercentage <=84.99 ) {
            return "A";
        } else if (averagePercentage >= 60 && averagePercentage <=74.99 ) {
            return "B";
        } else if (averagePercentage >= 50 && averagePercentage <=59.99 ) {
            return "C";
        }else if (averagePercentage >= 40 && averagePercentage <=49.99 ) {
            return "D";
        }else {
            return "F";
        }
		}


	private void showAlert(String message) {
		Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();

		
	}

    public class Subject {
        private String name;
        private int marks;

        public Subject(String name, int marks) {
            this.name = name;
            this.marks = marks;
        }

        public String getName() {
            return name;
        }

        public int getMarks() {
            return marks;
        }
    }	
}
