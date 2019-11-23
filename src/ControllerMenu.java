
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import motd.CipherSolver;
import motd.HttpRequest;
/**
 * File name: ControllerMenu
 *
 * @version 1.0
 * Creation Date: 14/11/2019
 * Last Modification date: 15/11/2019
 * @author Robbie Ko
 * <br>
 * No copyright.
 * <br>
 * Purpose:
 * The Controller for the Menu
 * <br>
 * Version History
 * 1.0 - loads in a video, making the buttons workings with their purepose (Message of the day implemented)
 *
 */


public class ControllerMenu implements Initializable {
    @FXML
    private MediaView see;
    private Media look;
    private MediaPlayer video;

    @FXML
    private Label messageDisplay;
    @FXML
    private AnchorPane layout;
    @FXML
    private Button playGame;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String path = new File("src/media/video/DummyTest.mp4").getAbsolutePath();
        look = new Media(new File(path).toURI().toString());
        video = new MediaPlayer(look);
        see.setMediaPlayer(video);
        see.setPreserveRatio(false);
        see.fitHeightProperty().bind(layout.heightProperty());
        see.fitWidthProperty().bind(layout.widthProperty());
        video.setAutoPlay(true);
        video.setCycleCount(MediaPlayer.INDEFINITE);

        HttpRequest get = new HttpRequest();
        String result = get.newConnection("http://cswebcat.swan.ac.uk/puzzle");
        CipherSolver solve = new CipherSolver();
        String solvedCipher = solve.solved(result);
        String cipherURL = "http://cswebcat.swan.ac.uk/message?solution=" + solvedCipher;
        result = get.newConnection(cipherURL);
        messageDisplay.setText(result);
        messageDisplay.setAlignment(Pos.CENTER);
        messageDisplay.prefWidth(result.length());
        
    }

    private void setScreen(ActionEvent event, String string)throws IOException {
        video.stop();
        Parent loadIn = FXMLLoader.load(getClass().getResource(string));
        Stage newWindow = (Stage)((Node)event.getSource()).getScene().getWindow();
        newWindow.setMinHeight(600);
        newWindow.setMinWidth(800);
        newWindow.setScene(new Scene(loadIn,1200,700));
        newWindow.show();
    }


    @FXML
    private void playGamePress(ActionEvent event)throws IOException {
//        setScreen(event,"mainGame.fxml");
        video.stop();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Reaching Insanity");
        stage.setScene((new GameWindow("test player", new File("")).getScene()));
    }
    
    @FXML
    private void leaderBoardPress(ActionEvent event)throws IOException {
       video.stop();
       Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
       stage.setTitle("Reaching Insanity - Leader Board");
       stage.setScene((new LeaderBoard().getScene()));
    }
    
    @FXML
    private void playerEditorPress(ActionEvent event)throws IOException {
        video.stop();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Reaching Insanity - Player Editor");
        stage.setScene((new PlayerEditor().getScene()));
    }

    @FXML
    private void exitGamePress(ActionEvent event) {
        Platform.exit();
    }


}