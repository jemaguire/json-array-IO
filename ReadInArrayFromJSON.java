//I/O imports
import java.io.*;
//.json file imports
import org.json.JSONTokener;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
//javaFX imports
import javafx.geometry.Insets;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.text.Text; 

public class ReadInArrayFromJSON extends Application{

  private static String[] parsedQuestions;//holds the questions parsed from the .json file in an array
  private static String[] responsesToParse;//holds the answers the user types in
  private static int numAnswered;//holds the number of questions answered
    
  public void start(Stage primaryStage) {
    parser();//call to method that reads the .json file
    responsesToParse = new String[parsedQuestions.length];
    
    //set up primary stage
    primaryStage.setMinWidth(400);
    primaryStage.setMinHeight(300);
    primaryStage.setResizable(false);
    
    // Create panes and set properties
    BorderPane root = new BorderPane();//main pane
    
    VBox questionPane = new VBox(10);//where question & answer are displayed
    questionPane.setAlignment(Pos.CENTER);
    HBox btnPane = new HBox(10);//where next button is displayed
          
    //Question Text
    Text questionNum = new Text("Question "+ (numAnswered+1) +":");
    Text question = new Text(parsedQuestions[0]);
    questionNum.setStyle("-fx-font: normal 20px 'arial'");
    question.setStyle("-fx-font: normal 20px 'arial'");
    //Response
    Text resp = new Text("Response:");
    resp.setStyle("-fx-font: normal 15px 'arial'");
    TextField answer = new TextField();
    
    //Next Question button
    Button btNext = new Button("Next>>");
    btnPane.getChildren().add(btNext);
    btnPane.setAlignment(Pos.CENTER_RIGHT);
    root.setBottom(btnPane);
    //Save Button
    Button btSave = new Button ("Save");
    
    //Action Handler for Next button
    btNext.setOnAction(action ->{
      if (!answer.getText().trim().isEmpty() && numAnswered<parsedQuestions.length-1){
         responsesToParse[numAnswered] = answer.getText();
         numAnswered++;
         //reset question
         questionNum.setText("Question "+ (numAnswered+1) +":");
         question.setText( parsedQuestions[numAnswered]);
         answer.setText("");
      }//end if
      else if(numAnswered == parsedQuestions.length-1){
         //make old buttons & text field invisible
         questionNum.setVisible(false);
         resp.setVisible(false);
         answer.setVisible(false);
         btNext.setVisible(false);
         //save final response
         responsesToParse[numAnswered] = answer.getText();
         //Save Button
         question.setText("Click save to save your responses.");
         btSave.setVisible(true);
      }//end of else if
    });//end next button response
    
    btSave.setOnAction(action ->{
      writeIn();
      Platform.exit();
    });//end save button response
    
    //add everything to a pane
    questionPane.getChildren().addAll(questionNum,question,resp,answer,btSave);
    btSave.setVisible(false);
    root.setCenter(questionPane);
    root.setMargin(questionPane, new Insets(12,12,12,12));
    root.setMargin(btnPane, new Insets(12,12,12,12));
    // Create a scene and place it in the stage
    Scene scene = new Scene(root);
    primaryStage.setTitle("Pison Coding Challenge"); // Set the stage title
    primaryStage.setScene(scene); // Place the scene in the stage
    primaryStage.show(); // Display the stage
  }//end of start

  //Reads in input from .json file
  public static void parser(){
      try {/*For .json files encoded in ANSI or UTF-8*/
         JSONTokener token = new JSONTokener(new FileInputStream("config.json"));
         String actualCode = token.nextValue().toString();
         if(actualCode.length() == 3)
            actualCode = token.nextValue().toString();
         String codeToArray = actualCode.substring(actualCode.indexOf('[')+1,actualCode.lastIndexOf(']'));
         String[] fixedQuestions = codeToArray.split("\",");
         parsedQuestions = new String[fixedQuestions.length];
         for(int i=0; i<parsedQuestions.length;i++)
            parsedQuestions[i] = fixedQuestions[i].substring(1,fixedQuestions[i].length());
         
		}catch (FileNotFoundException noFile) {
			System.out.println("No file found");
         System.exit(0);
		}
	}//end of parser
   
  //method to write responses.json
  public static void writeIn(){
      JSONObject writerObject = new JSONObject();
      JSONArray listOfResponses = new JSONArray();
      writerObject.put("numResponses", responsesToParse.length);
      for(int i=0;i<responsesToParse.length;i++){
         listOfResponses.add(responsesToParse[i]);
      }
      writerObject.put("responses", listOfResponses);

      try (FileWriter file = new FileWriter("responses.json")) {

          file.write(writerObject.toJSONString());
          file.close();

      } catch (IOException output) {
          System.out.println("Issue outputting information.");
          Platform.exit();
          System.exit(0);
      }
      
  }//end of writer method*/
 
  public static void main(String[] args) {
    launch(args);
  }
  
}//end of ReadInArrayFromJSON class

