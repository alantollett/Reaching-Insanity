
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;
/**
 * Leader board class which showed the leader board out
 * @author Allan
 *
 */
public class LeaderBoard {
	
	/** the image for the back ground*/
	private final String IMG_FOLDER = "src/media/img/";
	
	/** the scene of the leader board*/
	private Scene scene;
	
	/** the database was used*/
	private Database db;
	
	/** the level that is*/
	private int level;

	/**
	 * 
	 * Creates an instance of LeaderBoard.
	 * @throws IOException - File exception, such as can't find image.
	 */
	public LeaderBoard() throws IOException {
		try {
			db = new Database("jdbc:mysql://localhost:3306/reaching_insanity"
					, "root", "");

			// start with a stack pane, add the background and add the main
			// contents
			StackPane layout = new StackPane();
			ImageView background = new ImageView(new Image(
					new FileInputStream(IMG_FOLDER + "background.png")));

			// main contents
			VBox contents = new VBox(75);

			ImageView title = new ImageView(new Image(
					new FileInputStream(IMG_FOLDER+ "leaderboard_title.png")));

			HBox levelButtons = new HBox(50);
			for (int i = 1; i < 6; i++) {
				Button b = new Button("Level " + i);
				b.setOnAction(e -> {
					level = Integer.parseInt(b.getText()
							.replace("Level ", ""));
					try {
						contents.getChildren().set(2, getPlayers(level));
					} catch (FileNotFoundException | SQLException e1) {
						System.out.println("ERROR: bad SQL or file not found");
						e1.printStackTrace();
					}
				});
				levelButtons.getChildren().add(b);
			}

			level = 1; // as we can't display level zero.

			Button mainMenu = new Button("Back to Main Menu");
			mainMenu.setOnAction(e -> {
				Stage newWindow = 
						(Stage) ((Node) e.getSource()).getScene().getWindow();
				newWindow.setScene(Main.getScene());
				newWindow.show();
			});

			contents.getChildren().addAll(title, levelButtons
					, getPlayers(level), mainMenu);
			layout.getChildren().addAll(background, contents);
			scene = new Scene(layout, 1200, 700);
			scene.getStylesheets().add("style.css");

		} catch (SQLException e) {
			System.out.println("ERROR: failed to connect to database.");
			e.printStackTrace();
			System.exit(-1);
		}
	}
	
	/**
	 * Methods that get the current scene
	 * @return scene
	 */
	public Scene getScene() {
		return scene;
	}
	
	/**
	 *  Method that gets the players level
	 * @param level
	 * @return player objects
	 * @throws FileNotFoundException
	 * @throws SQLException
	 */
	private HBox getPlayers(int level) 
			throws FileNotFoundException, SQLException {
		HBox players = new HBox(50);
		players.setPrefHeight(225);

		ResultSet rs = db.query("SELECT player.name, image_id, seconds " 
		+ "FROM player, leaderboard " + "WHERE "
				+ "player.name = leaderboard.name " + "AND " 
		+ "level = " + level + " ORDER BY seconds");

		if (rs.next()) {
			rs.previous();
			Timeline timeline = new Timeline(
					new KeyFrame(Duration.seconds(1.5), e -> {
				try {
					// check, because if null then we dont want to display
					// anything!
					if (rs.next()) {
						VBox player = new VBox(25);
						Label name = new Label(rs.getString("name"));
						ImageView img = new ImageView(
								new Image(new FileInputStream(
								"src/media/img/grid/player" 
								+ rs.getInt("image_id") + "_down.png")));

						Label time = new Label(getTime(rs.getInt("seconds")));
						player.getChildren().addAll(name, img, time);
						player.setOpacity(0);

						players.getChildren().add(player);

						FadeTransition ft = new FadeTransition(
								Duration.seconds(1), player);
						ft.setToValue(1.0);
						ft.play();
						playSound("src/media/sound/leaderboard.wav");
					}
				} catch (SQLException | FileNotFoundException er) {
					er.printStackTrace();
					System.exit(-1);
				}
			}));
			timeline.setCycleCount(3);
			timeline.play();
		} else {
			playSound("src/media/sound/error.wav");
		}

		return players;
	}

	/**
	 * Method that gets the time
	 * @param seconds
	 * @return time display
	 */
	private String getTime(int seconds) {
		int hours = seconds / 3600;
		int minutes = (seconds % 3600) / 60;
		seconds = ((seconds % 3600) % 60) % 60;
		String h = (hours >= 10 ? "" : "0") + hours;
		String m = (minutes >= 10 ? "" : "0") + minutes;
		String s = (seconds >= 10 ? "" : "0") + seconds;
		return h + ":" + m + ":" + s;
	}
	
	/**
	 * Method that plays the sound
	 * @param path
	 */
	private void playSound(String path) {
		Media media = new Media(new File(path).toURI().toString());
		MediaPlayer mediaPlayer = new MediaPlayer(media);
		mediaPlayer.play();
	}

}
