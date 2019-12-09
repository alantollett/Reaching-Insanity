import java.io.File;
import java.util.HashMap;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/**
 * Represents the player at any given time in the current game state
 * 
 * @author Gursimran Randhawa, Millie Quinn, and Yassine Abdalass
 * @version 1.0 added attributes and methods (w/out implementation) <br>
 *          1.1 Implemented the constructor <br>
 */

public class Player extends Character {
	private String name;
	private Integer highestLevel;
	private HashMap<Collectable, Integer> inventory;
	private int imageID;
	private boolean isDead;
	private boolean hasWon;

	/**
	 * Constructs a player object
	 * @param name
	 *            name of the current player
	 * @param inventory
	 *            a hashmap of the players current collectables
	 * @param highestLevel
	 *            is the players highest reached level
	 * @param imageID
	 * 			  The image id to display.           
	 */
	public Player(String name, HashMap<Collectable, Integer> inventory,
			int highestLevel, int imageID) {
		super(-1, -1, "player" + imageID + "_down.png");

		this.highestLevel = highestLevel;
		this.name = name;
		this.imageID = imageID;

		if (inventory == null) {
			this.inventory = new HashMap<Collectable, Integer>();
		} else {
			this.inventory = inventory;
		}
	}

	/** Adds a collectable to the players inventory
	 * @param item - The item to add to inventory.*/
	public void addToInventory(Collectable item) {
		if (inventory.containsKey(item)) {
			inventory.replace(item, inventory.get(item) + 1);
		} else {
			inventory.put(item, 1);
		}
	}

	/** Adds a collectable to the players inventory
	 * @param item - The item to add to the inventory.
	 * @param amount - The quantity to add of an item.*/
	public void addToInventory(Collectable item, int amount) {
		if (inventory.containsKey(item)) {
			inventory.replace(item, inventory.get(item) + amount);
		} else {
			inventory.put(item, amount);
		}
	}

	/** Checks to see if the player has the item in their inventory.
	 * @param item - The item to check.
	 * @param amount - The amount to check.
	 * @return boolean - If item is present.*/
	public boolean hasItem(Collectable item, Integer amount) {
		if (inventory.containsKey(item) && inventory.get(item) >= amount) {
			return true;
		}
		return false;
	}

	/** Uses an item from the players inventory.
	 * @param item - The item to use.
	 * @param amount - The amount to use.*/
	public void useItem(Collectable item, Integer amount) {
		inventory.replace(item, inventory.get(item) - amount);
	}

	/**
	 * Method get the name.
	 * @return name - The name to return.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Method set the name.
	 * @param name - The name to set.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Method that get the highest level completed.
	 * @return highest level - The level to return.
	 */
	public Integer getHighestLevel() {
		return highestLevel;
	}

	/**
	 * Method that get the string inventory.
	 * @return inventory - The contents of the players inventory.
	 */
	public String getInventoryString() {
		HashMap<Collectable, String> itemAbbreviations = 
				new HashMap<Collectable, String>();
		itemAbbreviations.put(Collectable.TOKEN, "TK");
		itemAbbreviations.put(Collectable.LIFE, "L");
		itemAbbreviations.put(Collectable.RED_KEY, "RK");
		itemAbbreviations.put(Collectable.GREEN_KEY, "GK");
		itemAbbreviations.put(Collectable.BLUE_KEY, "BK");
		itemAbbreviations.put(Collectable.ICE_SKATES, "IS");
		itemAbbreviations.put(Collectable.FLIPPERS, "FL");
		itemAbbreviations.put(Collectable.FIRE_BOOTS, "FB");

		String output = "";
		if (inventory.size() > 0) {
			for (Collectable c : Collectable.values()) {
				if (inventory.get(c) == null) {
					inventory.put(c, 0);
				}
				output += itemAbbreviations.get(c) 
						+ ":" + inventory.get(c) + ", ";
			}
			return output.substring(0, output.length() - 2);
		}
		return output;
	}

	/** Method that move the player inherited from character.
	 * @param grid - The map.*/
	public void move(Cell[][] grid) {}



	/**
	 * Method that moves the player.
	 * Gone over the limit since its all cases for movement.
	 * @param grid - The map.
	 * @param event - The key input.
	 * @return move - The updated movement of the player.
	 */
	public Cell[][] move(Cell[][] grid, KeyEvent event) {
		int nextX = x;
		int nextY = y;

		switch (event.getCode()) {
		case RIGHT:
			nextX += 1;
			break;
		case LEFT:
			nextX -= 1;
			break;
		case UP:
			nextY -= 1;
			break;
		case DOWN:
			nextY += 1;
			break;
		default:
			return grid;
		}

		Cell nextCell = grid[nextX][nextY];

		switch (nextCell.getType()) {
		case WALL:
			nextX = x;
			nextY = y;
			playSound("src/media/sound/bump.wav");
			break;
		case RED_DOOR:
			if (hasItem(Collectable.RED_KEY, 1)) {
				useItem(Collectable.RED_KEY, 1);
				grid[nextX][nextY] = new Cell(CellType.EMPTY, null);
				playSound("src/media/sound/unlock.wav");
			} else {
				nextX = x;
				nextY = y;
				playSound("src/media/sound/bump.wav");
			}
			break;
		case GREEN_DOOR:
			if (hasItem(Collectable.GREEN_KEY, 1)) {
				useItem(Collectable.GREEN_KEY, 1);
				grid[nextX][nextY] = new Cell(CellType.EMPTY, null);
				playSound("src/media/sound/unlock.wav");
			} else {
				nextX = x;
				nextY = y;
				playSound("src/media/sound/bump.wav");
			}
			break;
		case BLUE_DOOR:
			if (hasItem(Collectable.BLUE_KEY, 1)) {
				useItem(Collectable.BLUE_KEY, 1);
				grid[nextX][nextY] = new Cell(CellType.EMPTY, null);
				playSound("src/media/sound/unlock.wav");
			} else {
				nextX = x;
				nextY = y;
				playSound("src/media/sound/bump.wav");
			}
			break;
		case TOKEN_DOOR:
			if (nextCell.getTokens() == 5) {
				if (hasItem(Collectable.TOKEN, 5)) {
					grid[nextX][nextY] = new Cell(CellType.EMPTY, null);
					playSound("src/media/sound/unlock.wav");
				} else {
					nextX = x;
					nextY = y;
					playSound("src/media/sound/bump.wav");
				}
			} else {
				if (hasItem(Collectable.TOKEN, 2)) {
					grid[nextX][nextY] = new Cell(CellType.EMPTY, null);
					playSound("src/media/sound/unlock.wav");
				} else {
					nextX = x;
					nextY = y;
					playSound("src/media/sound/bump.wav");
				}
			}

			break;
		case FIRE:
			if ((!hasItem(Collectable.FIRE_BOOTS, 1)) &&
					((!hasItem(Collectable.LIFE, 1)))) {
				// set player to dead
				isDead = true;
				playSound("src/media/sound/life_lost.mp3");
				// update location
				nextX = x;
				nextY = y;
				// now check in gamestate after move if player is dead
			} else {
				playSound("src/media/sound/fire.wav");
			}
			break;
		case WATER:
			if ((!hasItem(Collectable.FLIPPERS, 1)) && 
					((!hasItem(Collectable.LIFE, 1)))) {
				// set player to dead
				isDead = true;
				playSound("src/media/sound/life_lost.mp3");
				// update location
				nextX = x;
				nextY = y;
				// now check in gamestate after move if player is dead
			} else {
				playSound("src/media/sound/water.wav");
			}
			break;
		case ICE:
			if ((!hasItem(Collectable.ICE_SKATES, 1)) && 
					((!hasItem(Collectable.LIFE, 1)))) {
				// set player to dead
				isDead = true;
				playSound("src/media/sound/life_lost.mp3");
				// update location
				nextX = x;
				nextY = y;
				// now check in gamestate after move if player is dead
			} else {
				playSound("src/media/sound/ice.wav");

				int x1 = nextX;
				int y1 = nextY;

				if (event.getCode().equals(KeyCode.RIGHT)) {
					while (grid[x1][nextY].getType().equals(CellType.ICE)) {
						x1 += 1;
					}
					nextX = x1;
				} else if (event.getCode().equals(KeyCode.LEFT)) {
					while (grid[x1][nextY].getType().equals(CellType.ICE)) {
						x1 -= 1;
					}
					nextX = x1;
				} else if (event.getCode().equals(KeyCode.UP)) {
					while (grid[nextX][y1].getType().equals(CellType.ICE)) {
						y1 -= 1;
					}
					nextY = y1;
				} else if (event.getCode().equals(KeyCode.DOWN)) {
					while (grid[nextX][y1].getType().equals(CellType.ICE)) {
						y1 += 1;
					}
					nextY = y1;
				}
			}
			break;
		case TELEPORTER:
			if (event.getCode().equals(KeyCode.RIGHT)) {
				nextX = nextCell.getLinkX() + 1;
				nextY = nextCell.getLinkY();
			} else if (event.getCode().equals(KeyCode.LEFT)) {
				nextX = nextCell.getLinkX() - 1;
				nextY = nextCell.getLinkY();
			} else if (event.getCode().equals(KeyCode.UP)) {
				nextX = nextCell.getLinkX();
				nextY = nextCell.getLinkY() - 1;
			} else {
				nextX = nextCell.getLinkX();
				nextY = nextCell.getLinkY() + 1;
			}
			playSound("src/media/sound/teleport.wav");
			break;
		case GOAL:
			// multiplayer is level 100, so don't update highest level for
			// players on completion.
			nextX = x;
			nextY = y;
			hasWon = true;
			break;
		case EMPTY:
			for (Character e : GameWindow.getGameState().getEnemies()) {
				if ((e.getX() == nextX && e.getY() == nextY) && 
						(hasItem(Collectable.LIFE, 1))) {
					useItem(Collectable.LIFE, 1);
				} else {
					if ((e.getX() == nextX && e.getY() == nextY) && 
							(!hasItem(Collectable.LIFE, 1))) {
						isDead = true;
					}	
				}
			}

			if (nextCell.getItem() != null) {
				addToInventory(nextCell.getItem());
				nextCell.setItem(null);
				playSound("src/media/sound/pickup.wav");
			}
			break;
		default:
			break;
		}
		moveTo(nextX, nextY);
		return grid;
	}

	/**
	 * Method that gets the image location. 
	 * @return imageID -  The image ID.
	 */
	public int getImageID() {
		return imageID;
	}

	/**
	 * Method that get the amount of collectable.
	 * @param c - The amount of the collectable.
	 * @return inventory amount.
	 */
	public int getAmount(Collectable c) {
		if (inventory.size() > 0 && inventory.get(c) != null) {
			return inventory.get(c);
		} else {
			return 0;
		}
	}
	/**
	 * Method that gets the image ID.
	 * @param imageID - The image ID.
	 */
	public void setImageID(int imageID) {
		this.imageID = imageID;
	}

	/**
	 * Method that detects did the player win or not.
	 * @return boolean - The result of the game.
	 */
	public boolean hasWon() {
		return hasWon;
	}

	/**
	 * Method that set did the player win or not.
	 * @param hasWon - The result to set.
	 */
	public void setHasWon(boolean hasWon) {
		this.hasWon = hasWon;
	}

	/**
	 * Method that look is the player dead or not.
	 * @return boolean - The current state of player.
	 */
	public boolean isDead() {
		return isDead;
	}

	/** 
	 * Method that set the player dead.
	 * @param isDead - The value to set.
	 */
	public void setDead(boolean isDead) {
		this.isDead = isDead;
	}

	/**
	 * Method that clears the inventory.
	 */
	public void clearInventory() {
		inventory.clear();
	}

	/**
	 * Method that get the sound path
	 * @param path - The file path to go to.
	 */
	private void playSound(String path) {
		Media media = new Media(new File(path).toURI().toString());
		MediaPlayer mediaPlayer = new MediaPlayer(media);
		mediaPlayer.play();
	}

}