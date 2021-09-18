package application.scene;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneManager {
    private Stage stage;
    private Scene scene;
    private Parent root;

    public void switchToMenuScene(ActionEvent event) throws IOException {
        // load fxml file
        root = FXMLLoader.load(getClass().getResource("./fxml/Menu.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);

        // load css file
        String homeSceneCss = this.getClass().getResource("./css/Menu.css").toExternalForm();
        scene.getStylesheets().add(homeSceneCss);

        // set and show stage
        stage.setScene(scene);
        stage.show();

        
    }

    public void switchToTopicScene(ActionEvent event) throws IOException {
        // load fxml file
        root = FXMLLoader.load(getClass().getResource("./fxml/Topic.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root, stage.getWidth(), stage.getHeight());

        // load css file
        String homeSceneCss = this.getClass().getResource("./css/Topic.css").toExternalForm();
        scene.getStylesheets().add(homeSceneCss);

        // set and show stage
        stage.setScene(scene);
        stage.show();
    }
    
    public void switchToQuizScene(ActionEvent event) throws IOException {
        // load fxml file
        root = FXMLLoader.load(getClass().getResource("./fxml/Quiz.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root, stage.getWidth(), stage.getHeight());

        // load css file
        String homeSceneCss = this.getClass().getResource("./css/Quiz.css").toExternalForm();
        scene.getStylesheets().add(homeSceneCss);

        // set and show stage
        stage.setScene(scene);
        stage.show();
    }
    
    public void switchToQuizSecondAttemptScene(ActionEvent event) throws IOException {
        // load fxml file
        root = FXMLLoader.load(getClass().getResource("./fxml/QuizSecondAttempt.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root, stage.getWidth(), stage.getHeight());

        // load css file
        String homeSceneCss = this.getClass().getResource("./css/QuizSecondAttempt.css").toExternalForm();
        scene.getStylesheets().add(homeSceneCss);

        // set and show stage
        stage.setScene(scene);
        stage.show();
    }
    
    public void switchToCorrectScene(ActionEvent event) throws IOException {
        // load fxml file
        root = FXMLLoader.load(getClass().getResource("./fxml/Correct.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root, stage.getWidth(), stage.getHeight());

        // load css file
        String homeSceneCss = this.getClass().getResource("./css/Correct.css").toExternalForm();
        scene.getStylesheets().add(homeSceneCss);

        // set and show stage
        stage.setScene(scene);
        stage.show();
    }
    
    public void switchToIncorrectTryAgainScene(ActionEvent event) throws IOException {
        // load fxml file
        root = FXMLLoader.load(getClass().getResource("./fxml/IncorrectTryAgain.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root, stage.getWidth(), stage.getHeight());

        // load css file
        String homeSceneCss = this.getClass().getResource("./css/IncorrectTryAgain.css").toExternalForm();
        scene.getStylesheets().add(homeSceneCss);

        // set and show stage
        stage.setScene(scene);
        stage.show();
    }
    
    public void switchToIncorrectNextScene(ActionEvent event) throws IOException {
        // load fxml file
        root = FXMLLoader.load(getClass().getResource("./fxml/IncorrectNext.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root, stage.getWidth(), stage.getHeight());

        // load css file
        String homeSceneCss = this.getClass().getResource("./css/IncorrectNext.css").toExternalForm();
        scene.getStylesheets().add(homeSceneCss);

        // set and show stage
        stage.setScene(scene);
        stage.show();
    }
    
    public void switchToFinishScene(ActionEvent event) throws IOException {
        // load fxml file
        root = FXMLLoader.load(getClass().getResource("./fxml/Finish.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root, stage.getWidth(), stage.getHeight());

        // load css file
        String homeSceneCss = this.getClass().getResource("./css/Finish.css").toExternalForm();
        scene.getStylesheets().add(homeSceneCss);

        // set and show stage
        stage.setScene(scene);
        stage.show();
    }
    
    public void closeWindow(ActionEvent event) throws IOException {
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.close();
    }
}
