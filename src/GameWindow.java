import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class GameWindow {

	private final String LOGO_PATH = "src/media/img/logo.png";
	private final String PLAYER_PATH = "src/media/img/grid/player1_down.png";
	private final String IMG_FOLDER = "src/media/img/grid/";
	
	/**Used to update the timer*/
	private Timeline timeline;
	/**the scene to put on the window*/
	private Scene scene;
	/**true if the game is paused*/
	private boolean paused;
	/**the number of hours spent on the current level*/
	private int hours;
	/**the number of minutes spent on the current level*/
	private int minutes;
	/**the number of seconds spent on the current level*/
	private int seconds;
	/**the pane containing the grid*/
	private GridPane gridPane;
	/**holds a reference to the current game state*/
	private static GameState gameState;
	/**the overall layout of the scene*/
	private BorderPane borderPane;
	/**used to put a background underneath borderpane*/
	private StackPane layout;

	public GameWindow(String playerName, File levelFile) {
		layout = new StackPane();
		borderPane = new BorderPane();

		try {
			gameState = new GameState(levelFile, playerName, null, 0);

			ImageView logo = new ImageView(new Image(new FileInputStream(LOGO_PATH)));
			HBox top = new HBox();
			top.getChildren().add(logo);
			top.setAlignment(Pos.CENTER);
			borderPane.setTop(top);
			borderPane.setLeft(getLeft());
			borderPane.setRight(getRight());

			gridPane = getGrid();
			borderPane.setCenter(gridPane);

			
			ImageView background = new ImageView(new Image(
					new FileInputStream("src/media/img/background.png")));
			
			layout.getChildren().addAll(background, borderPane);
			scene = new Scene(layout, 1200, 700);
			scene.setOnKeyPressed(e -> processKeyEvent(e));
			scene.getStylesheets().add("gameWindow.css");
		} catch (FileNotFoundException e) {
			System.out.println("ERROR: Failed to load image(s).");
			e.printStackTrace();
		}
	}

	public Scene getScene() {
		return scene;
	}

	public String getTime() {
		String h = (hours >= 10 ? "" : "0") + hours;
		String m = (minutes >= 10 ? "" : "0") + minutes;
		String s = (seconds >= 10 ? "" : "0") + seconds;
		return h + ":" + m + ":" + s;
	}

	private GridPane getGrid() throws FileNotFoundException {
		GridPane pane = new GridPane();
		pane.setAlignment(Pos.CENTER);

		int playerX = gameState.getPlayer().getX();
		int playerY = gameState.getPlayer().getY();

		for (int gridX = -4; gridX < 4; gridX++) {
			for (int gridY = -4; gridY < 4; gridY++) {
				try {
					int currentGridX = playerX + gridX;
					int currentGridY = playerY + gridY;
					Cell cell = gameState.getGrid()[currentGridX][currentGridY];
					StackPane stack = new StackPane();
					stack.getChildren().add(cell.getCellImage());
					
					if (gridX == 0 && gridY == 0) { // if drawing the center cell, add player
						stack.getChildren().add(gameState.getPlayer().getImage());
					} else {
						boolean enemyFound = false;
						for(Character enemy : gameState.getEnemies()) {
							if(enemy.getX() == currentGridX && enemy.getY() == currentGridY) {
								stack.getChildren().add(enemy.getImage());
								enemyFound = true;
							}
						}
						
						if(!enemyFound) {
							if(cell.getItem() != null) {
								stack.getChildren().add(cell.getItemImage());
							}
						}
						
					}
					pane.add(stack, gridX + 4, gridY + 4);
				} catch (ArrayIndexOutOfBoundsException e) {
					ImageView img = new ImageView(new Image(new FileInputStream(IMG_FOLDER + "black.png")));
					pane.add(img, gridX + 4, gridY + 4);
				}
			}
		}
		return pane;
	}

	private VBox getLeft() throws FileNotFoundException {
		VBox left = new VBox(60);
		left.setMinWidth(300);
		left.setAlignment(Pos.CENTER);
		Label nameLabel = new Label(gameState.getPlayer().getName());
		nameLabel.setId("playerName");

		ImageView playerIcon = new ImageView(new Image(new FileInputStream(PLAYER_PATH)));
		playerIcon.setScaleX(2.25);
		playerIcon.setScaleY(2.25);
		
		HBox inv1 = new HBox(30);
		inv1.setId("inventoryRow");
		inv1.setMinHeight(60);
		
		HBox inv2 = new HBox(30);
		inv2.setId("inventoryRow");
		inv2.setMinHeight(60);
		
		VBox invs = new VBox(10);
		invs.getChildren().addAll(inv1, inv2);
		
		int items = 0;
		for(Collectable c : Collectable.values()) {
			ImageView img = new ImageView(new Image(new FileInputStream(
					IMG_FOLDER + c.toString().toLowerCase() + ".png")));
			
			int count = gameState.getPlayer().getAmount(c);
			
			Label countLabel = new Label(String.valueOf(count));
			countLabel.setId("countLabel");
			
			StackPane pane = new StackPane();
			pane.getChildren().addAll(img, countLabel);
			
			if(items < 4) {
				inv1.getChildren().add(pane);
			}else {
				inv2.getChildren().add(pane);
			}
			items++;
		}

		left.getChildren().addAll(nameLabel, playerIcon, invs);
		return left;
	}

	private VBox getRight() {
		VBox right = new VBox(40);
		right.setMinWidth(300);
		right.setAlignment(Pos.CENTER);

		Label timeLabel = new Label();
		timeLabel.setId("timeLabel");

		Label optionsLabel = new Label("Options");
		optionsLabel.setId("optionsLabel");

		timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
			if (!paused) {
				seconds++;
				if (seconds == 60) {
					seconds = 0;
					minutes++;
					if (minutes >= 60) {
						minutes = 0;
						hours++;
					}
				}
			}
			timeLabel.setText(getTime());
		}));
		timeline.setCycleCount(Animation.INDEFINITE);
		timeline.play();

		Button pause = new Button("Pause");
		pause.setOnAction(e -> {
			timeline.pause();
			paused = true;
		});

		Button resume = new Button("Resume");
		resume.setOnAction(e -> {
			timeline.play();
			paused = false;
		});

		Button exitGame = new Button("Exit and Save");
		exitGame.setOnAction(e -> {
			timeline.pause();
			paused = true;
			gameState.save();
			try {
				Parent loadIn = FXMLLoader.load(getClass().getResource("mainMenu.fxml"));
				Stage newWindow = (Stage) ((Node) e.getSource()).getScene().getWindow();
				newWindow.setScene(new Scene(loadIn, 1200, 700));
				newWindow.show();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		});

		right.getChildren().addAll(timeLabel, optionsLabel, pause, resume, exitGame);
		return right;
	}

	public void processKeyEvent(KeyEvent event) {
		if (!paused) {
			gameState.getPlayer().move(gameState.getGrid(), event);
			
			int i = 0;
			for(Character e : gameState.getEnemies()) {
				e.move(gameState.getGrid());
				Player p = gameState.getPlayer();
				if(e.getX() == p.getX() && e.getY() == p.getY()) {
					if(p.hasItem(Collectable.LIFE, 1)) {
						p.useItem(Collectable.LIFE, 1);
						GameState.killEnemy(i);
					}else {
						// kill player
					}
				}
				i++;
			}
			update();
		}
		event.consume();
	}
	
	public static GameState getGameState() {
		return gameState;
	}

	public void update() {
		try {
			gridPane = getGrid();
			borderPane.setCenter(gridPane);
			borderPane.setLeft(getLeft());
			layout.getChildren().set(1, borderPane);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}