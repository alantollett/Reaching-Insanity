import javax.security.auth.login.CredentialException;

/*
 * Author Ben Wickens
 */
public class WallFollowingEnemy extends Character {
	private Direction currtDir;
	private Direction vertiDir;
	private Cell [] [] grid;
	public WallFollowingEnemy(int x, int y, Direction startDir,Direction vertiDir) {
		super(x, y, "WFE.png");
		currtDir = startDir;
		this.vertiDir = vertiDir;
	}


	private boolean directionUP(CellType cellType) {
		CellType nextType = grid[x][y - 1].getType();
		if (nextType.equals(cellType)) {
			return true;
		} else {
			return false;
		}
	}

	private boolean directionUPLeft(CellType cellType) {
		CellType nextType = grid[x - 1][y - 1].getType();
		if (nextType.equals(cellType)) {
			return true;
		} else {
			return false;
		}
	}

	private boolean directionUPRight(CellType cellType) {
		CellType nextType = grid[x + 1][y - 1].getType();
		if (nextType.equals(cellType)) {
			return true;
		} else {
			return false;
		}
	}

	private boolean directionRight(CellType cellType) {
		CellType nextType = grid[x + 1][y].getType();
		if (nextType.equals(cellType)) {
			return true;
		} else {
			return false;
		}
	}

	private boolean directionLeft(CellType cellType) {
		CellType nextType = grid[x - 1][y].getType();
		if (nextType.equals(cellType)) {
			return true;
		} else {
			return false;
		}
	}

	private boolean directionDownLeft(CellType cellType) {
		CellType nextType = grid[x - 1][y + 1].getType();
		if (nextType.equals(cellType)) {
			return true;
		} else {
			return false;
		}
	}

	private boolean directionDown(CellType cellType) {
		CellType nextType = grid[x][y + 1].getType();
		if (nextType.equals(cellType)) {
			return true;
		} else {
			return false;
		}
	}

	private boolean directionDownRight(CellType cellType) {
		CellType nextType = grid[x + 1][y + 1].getType();
		if (nextType.equals(cellType)) {
			return true;
		} else {
			return false;
		}
	}

	private int validWallOnMove() {
        if (directionUP(CellType.WALL)) {
            System.out.println("Up");
            if (directionLeft(CellType.WALL) && directionRight(CellType.WALL) && directionDown(CellType.EMPTY)){
                vertiDir = Direction.DOWN;
                return y+=1;
            } else if(directionLeft(CellType.WALL) && directionDown(CellType.WALL)){
                System.out.println("Up1");
                currtDir = Direction.RIGHT;
                return x +=1;
            }else if(directionRight(CellType.WALL) && directionDown(CellType.WALL)){
                System.out.println("Up2");
                currtDir = Direction.LEFT;
                return x-=1;
            }else if (directionRight(CellType.EMPTY) && directionLeft(CellType.EMPTY)){
                System.out.println("Up3");
                if(currtDir == Direction.RIGHT){
                   vertiDir = Direction.UP;
                   return x +=1;
                }else{
                    vertiDir = Direction.UP;
                    return x-=1;
                }
            }else if(directionUPRight(CellType.EMPTY) && directionLeft(CellType.WALL)){
                System.out.println("Up4");
                if(vertiDir == Direction.UP && currtDir == Direction.RIGHT){
                    currtDir = Direction.RIGHT;
                    return x+=1;
                }else{
                    if(vertiDir == Direction.DOWN && !directionLeft(CellType.WALL)){
                       return x+=1;
                    }else {
                        vertiDir = Direction.DOWN;
                        return y += 1;
                    }
                }
            }else if(directionUPLeft(CellType.EMPTY) && directionRight(CellType.WALL)) {
                System.out.println("Up5");
                if (vertiDir == Direction.UP && currtDir == Direction.LEFT) {
                    return x -= 1;
                } else if (vertiDir == Direction.UP && currtDir == Direction.RIGHT) {
                    return x += 1;
                }else if(vertiDir == Direction.DOWN && currtDir ==Direction.RIGHT){
                    vertiDir= Direction.DOWN;
                    return y+=1;
                }else{
                    vertiDir = Direction.UP;
                    return y+=1;
                }
            }else if(directionLeft(CellType.WALL) && directionRight(CellType.EMPTY) && directionUPRight(CellType.WALL)){
                System.out.println("Up6");
                if(directionDown(CellType.EMPTY) && directionDownRight(CellType.WALL)){
                    if(currtDir == Direction.LEFT ){
                        vertiDir = Direction.DOWN;
                        return y+=1;
                    }else {
                        currtDir = Direction.RIGHT;
                        return x += 1;
                    }
                }else if (currtDir == Direction.LEFT && vertiDir == Direction.UP){
                    currtDir = Direction.RIGHT;
                    vertiDir = Direction.DOWN;
                    return y+=1;
                }else{
                    if (currtDir==Direction.LEFT && vertiDir == Direction.DOWN){
                        currtDir = Direction.RIGHT;
                        return y+=1;
                    }
                    vertiDir = Direction.DOWN;
                    currtDir = Direction.RIGHT;
                    return x+=1;
                }
            }else if (directionRight(CellType.WALL)&&directionLeft(CellType.EMPTY)&& directionUPLeft(CellType.WALL)){
                System.out.println("Up7");
                if(currtDir == Direction.LEFT && vertiDir== Direction.UP){
                    return x -=1;
                }else{
                    if(directionDownLeft(CellType.WALL) && vertiDir == Direction.UP && currtDir == Direction.RIGHT){
                        currtDir = Direction.LEFT;
                        vertiDir = Direction.DOWN;
                        return y+=1;
                    }else {
                        currtDir = Direction.LEFT;
                        vertiDir = Direction.DOWN;
                        return y += 1;
                    }
                }
            }
        } else if (directionLeft(CellType.WALL) && !directionRight(CellType.WALL)) {
            System.out.println("Left");
            if (directionDown(CellType.EMPTY) && directionUP(CellType.EMPTY)){
                System.out.println("Left1");
             if(vertiDir == Direction.UP){
                 currtDir = Direction.RIGHT;
                 return y-=1;
             }else{
                 currtDir = Direction.RIGHT;
                 return y+=1;
             }
            }else if(directionDown(CellType.WALL)&&directionDownRight(CellType.WALL) && directionRight(CellType.EMPTY)){
                System.out.println("Left2");
                if(directionUPRight(CellType.WALL)&&directionUP(CellType.EMPTY)){
                    if(currtDir == Direction.LEFT && vertiDir == Direction.UP){
                        return y-=1;
                    }else {
                        currtDir = Direction.RIGHT;
                        return x += 1;
                    }
                }else if (currtDir == Direction.LEFT && vertiDir == Direction.UP){
                    vertiDir = Direction.UP;
                    currtDir = Direction.RIGHT;
                    return y-=1;
                }else{
                    if(currtDir == Direction.LEFT && vertiDir== Direction.DOWN){
                        vertiDir = Direction.UP;
                        currtDir = Direction.RIGHT;
                        return y-=1;
                    }else {
                        currtDir = Direction.RIGHT;
                        return x += 1;
                    }
                }
            }else if(directionDown(CellType.WALL)&&directionDownRight(CellType.EMPTY)){
                System.out.println("Left3");
                if (vertiDir == Direction.DOWN){
                    return x+=1;
                }else{
                    if (currtDir == Direction.LEFT) {
                        vertiDir = Direction.UP;
                        return y-=1;
                    }else {
                        vertiDir = Direction.UP;
                        return y += 1;
                    }
                }
            }else if(directionRight(CellType.WALL)&&directionDown(CellType.WALL)){
                System.out.println("Left4");
                    vertiDir =Direction.UP;
                    return y-=1;
            }
        } else if (directionRight(CellType.WALL)) {
            System.out.println("Right");
            if(directionLeft(CellType.WALL) && directionDown(CellType.WALL)){
                vertiDir = Direction.UP;
                currtDir = Direction.RIGHT;
                return y-=1;
            }else  if (directionDown(CellType.EMPTY) && directionUP(CellType.EMPTY)){
                System.out.println("Right1");
                if(vertiDir == Direction.UP){
                    if(directionUPRight(CellType.EMPTY)){
                        currtDir = Direction.LEFT;
                    }
                    currtDir = Direction.LEFT;
                    return y-=1;
                }else{
                    currtDir = Direction.LEFT;
                    vertiDir = Direction.DOWN;
                    return y+=1;
                }
            }else if(directionDown(CellType.WALL)&&directionDownLeft(CellType.WALL) && directionLeft(CellType.EMPTY)) {
                System.out.println("Right2");
                if(directionUPLeft(CellType.WALL) && directionUP(CellType.EMPTY)){
                    if(vertiDir == Direction.DOWN) {
                        currtDir = Direction.LEFT;
                        return x -=1;
                    }else {
                        vertiDir = Direction.UP;
                        return y -= 1;
                    }
                }else if (currtDir == Direction.RIGHT && vertiDir == Direction.DOWN) {
                    currtDir = Direction.LEFT;
                    vertiDir = Direction.UP;
                    return y -= 1;
                }else if(currtDir == Direction.RIGHT && directionUPLeft(CellType.WALL) && directionDown(CellType.WALL)){
                    currtDir = Direction.LEFT;
                    return x -=1;
            }else{
                    if(currtDir == Direction.LEFT){
                        return x-=1;
                    }else {
                        vertiDir = Direction.UP;
                        return x += 1;
                    }
                }
            }else if(directionDown(CellType.WALL)&&directionDownLeft(CellType.EMPTY)){
                System.out.println("Right3");
                if (vertiDir == Direction.DOWN){
                    currtDir = Direction.RIGHT;
                    return x-=1;
                }else{
                    System.out.println("RightH3");
                    if (currtDir == Direction.RIGHT) {
                        currtDir = Direction.RIGHT;
                        vertiDir = Direction.UP;
                        return y-=1;
                    }else {
                        System.out.println("Right3111");
                        currtDir = Direction.RIGHT;
                        vertiDir = Direction.UP;
                        return y += 1;
                    }
                }
            }
        } else if (directionDown(CellType.WALL)) {
            System.out.println("Down");
            if(directionLeft(CellType.EMPTY)&&directionRight(CellType.EMPTY)){
                System.out.println("Down1");
                if (currtDir == Direction.RIGHT){
                    vertiDir = Direction.DOWN;
                    return x +=1;
                }else{
                    vertiDir = Direction.DOWN;
                    return x-=1;
                }
            }else if(directionLeft(CellType.EMPTY)&&directionDownLeft(CellType.EMPTY)){
                System.out.println("Down2");
                vertiDir =Direction.DOWN;
                return x-=1;
            }else if(directionRight(CellType.EMPTY)&&directionDownRight(CellType.EMPTY)){
                System.out.println("Down3");
                vertiDir = Direction.DOWN;
                return x+=1;
            }
        }
        //Connor 1
        else if(directionDownRight(CellType.WALL) && directionDown(CellType.EMPTY) && directionRight(CellType.EMPTY)){
            System.out.println("Conc1");
            if(vertiDir == Direction.DOWN) {
                if (currtDir == Direction.RIGHT && directionDownRight(CellType.WALL)) {
                    System.out.println("Conc12");
                    currtDir = Direction.LEFT;
                    return y+=1;
                }else{
                    currtDir = Direction.LEFT;
                    return y+=1;
                }
            }else {
                if (currtDir == Direction.LEFT && directionDownRight(CellType.WALL)) {
                    currtDir = Direction.RIGHT;
                        return x+=1;
                } else if(currtDir == Direction.RIGHT&& directionDownRight(CellType.WALL)) {
                        return x+=1;
                }else{
                    vertiDir = Direction.UP;
                    currtDir = Direction.LEFT;
                    return y += 1;
                }
            }
        }
        //Connor 2 where only side wall attach
        else if(directionDownLeft(CellType.WALL) && directionDown(CellType.EMPTY) && directionLeft(CellType.EMPTY)){
            System.out.println("Conc2");
            if(vertiDir == Direction.UP) {
                if (currtDir == Direction.LEFT && directionDownRight(CellType.WALL)) {
                    currtDir = Direction.RIGHT;
                    vertiDir = Direction.DOWN;
                    return x+=1;
                }else{
                    //vertiDir = Direction.DOWN;
                    currtDir = Direction.LEFT;
                    return x -= 1;
                }
            }else {
                return y+=1;
            }
        }
        //Connor 3 where only side wall attach
        else if(directionUPLeft(CellType.WALL) && directionUP(CellType.EMPTY) && directionLeft(CellType.EMPTY)){
            System.out.println("Conc3");
            if(vertiDir == Direction.UP && currtDir== Direction.RIGHT) {
                return y -= 1;
            }else if(vertiDir == Direction.UP && currtDir == Direction.LEFT) {
                return x+=1;
            }else {
                if(vertiDir == Direction.DOWN){
                    if(directionUPLeft(CellType.WALL)){
                        currtDir = Direction.LEFT;
                        return x-=1;
                    }else {
                        currtDir = Direction.RIGHT;
                        vertiDir = Direction.UP;
                        return y -= 1;
                    }
                }else {
                    vertiDir = Direction.UP;
                    currtDir = Direction.LEFT;
                    return x -= 1;
                }
            }
        }
        //Connor 4
        else if(directionUPRight(CellType.WALL) && directionUP(CellType.EMPTY) && directionRight(CellType.EMPTY)) {
            System.out.println("Conc4");
            if (vertiDir == Direction.UP && currtDir== Direction.LEFT) {
                return y -= 1;
            } else if(vertiDir == Direction.UP && currtDir == Direction.RIGHT) {
                System.out.println("Conc41");
                if(directionUPRight(CellType.WALL)){
                    System.out.println("Conc42");
                    vertiDir = Direction.DOWN;
                }else {
                    System.out.println("Conc43");
                    vertiDir = Direction.UP;
                }
                return x+=1;
            }else{
                System.out.println("Conc44");
                currtDir = Direction.RIGHT;
                if(directionUPRight(CellType.WALL)){
                    vertiDir = Direction.DOWN;
                }else {
                    vertiDir = Direction.UP;
                }
                return x += 1;
            }
        }
        return 0;
    }



	@Override
	public void move(Cell[][] grid) {
		this.grid = grid;
		validWallOnMove();
	}
}
/*
System.out.println("This is Up");
            if (directionLeft(CellType.WALL) && directionRight(CellType.WALL)) {
                System.out.println("This is Up W +W");
                vertiDir = Direction.DOWN;
                return y += 1;
            } else if (directionRight(CellType.EMPTY) && directionLeft(CellType.WALL) && directionDown(CellType.WALL)) {
                System.out.println("This is R:E + L:W + D:W");
                if (currtDir == Direction.LEFT) {
                    currtDir = Direction.RIGHT;
                    System.out.println("Direction Right");
                    return x += 1;
                } else {
                    System.out.println("Direction Left");
                    currtDir = Direction.RIGHT;
                    return x += 1;
                }
            } else if (directionRight(CellType.WALL) && directionLeft(CellType.EMPTY) && directionDown(CellType.WALL)) {
                System.out.println("This is R:W + L:E + D:W");
                if (currtDir == Direction.LEFT) {
                    currtDir = Direction.RIGHT;
                    return x -= 1;
                } else {
                    currtDir = Direction.LEFT;
                    return x -= 1;
                }
            } else if (directionRight(CellType.EMPTY) && directionLeft(CellType.EMPTY) && directionDown(CellType.WALL)) {
                System.out.println("This is R:E + L:E + D:W");
                if (currtDir == Direction.LEFT) {
                    System.out.println("Direction Right");
                    return x -= 1;
                } else {
                    System.out.println("Direction Left");
                    return x += 1;
                }
            } else if (directionRight(CellType.EMPTY) && directionLeft(CellType.WALL) && directionDown(CellType.EMPTY) && directionUPRight(CellType.EMPTY)) {
                System.out.println("should be it ");
                if (vertiDir == Direction.UP) {
                    return x += 1;
                } else {
                    return y += 1;
                }
            } else if (directionRight(CellType.EMPTY) && directionLeft(CellType.WALL) && directionDown(CellType.EMPTY)) {
                System.out.println("Direction Here");
                if (currtDir == Direction.LEFT && vertiDir == Direction.DOWN) {
                    System.out.println("Direction Here1");
                    currtDir = Direction.LEFT;
                    vertiDir = Direction.DOWN;
                    return y += 1;
                } else if (vertiDir == Direction.UP) {
                    currtDir = Direction.RIGHT;
                    vertiDir = Direction.DOWN;
                    return x += 1;
                } else {
                    System.out.println("Direction Her2e");
                    vertiDir = Direction.UP;
                    currtDir = Direction.RIGHT;
                    return y += 1;
                }
            } else if (directionRight(CellType.WALL) && directionLeft(CellType.EMPTY) && directionDown(CellType.EMPTY) && !directionUPLeft(CellType.WALL) && vertiDir == Direction.UP) {
                System.out.println("should be it ");
                vertiDir = Direction.DOWN;
                return y += 1;
            } else if (directionRight(CellType.WALL) && directionLeft(CellType.EMPTY) && directionDown(CellType.EMPTY)) {
                System.out.println("Direction Here3");
                if (vertiDir == Direction.UP) {
                    currtDir = Direction.LEFT;
                    return x -= 1;
                } else {
                    vertiDir = Direction.DOWN;
                    return y += 1;
                }
            } else if (directionRight(CellType.EMPTY) && directionLeft(CellType.EMPTY) && directionDown(CellType.EMPTY) && directionUPLeft(CellType.EMPTY) && directionUPRight(CellType.EMPTY)) {
                if (currtDir == Direction.LEFT) {
                    currtDir = Direction.RIGHT;
                    vertiDir = Direction.UP;
                    return x += 1;
                } else {
                    vertiDir = Direction.UP;
                    x -= 1;
                }
            } else if (directionRight(CellType.EMPTY) && directionLeft(CellType.EMPTY) && directionDown(CellType.EMPTY)) {
                if (currtDir == Direction.LEFT) {
                    return x -= 1;
                } else {
                    return x += 1;
                }
            }
 */

/*
 System.out.println("This is Left");
            if (directionDown(CellType.WALL) && directionDownLeft(CellType.WALL) && directionUP(CellType.EMPTY)) {
                if (vertiDir == Direction.DOWN) {
                    vertiDir = Direction.UP;
                    currtDir = Direction.LEFT;
                    return x - +1;
                } else {
                    vertiDir = Direction.UP;
                    return y -= 1;
                }
            } else if (directionUP(CellType.EMPTY) && directionDown(CellType.EMPTY)) {
                System.out.println("This is Left Dected Wrong");
                if (vertiDir == Direction.DOWN) {
                    return y += 1;
                } else {
                    return y -= 1;
                }
            }else if(directionUP(CellType.EMPTY)&& directionDownLeft(CellType.EMPTY)){
                System.out.println("This is Left Dected Wrong Here");
                vertiDir = Direction.UP;
                currtDir = Direction.RIGHT;
                return y-=1;
            }
        }else if (directionDownLeft(CellType.WALL) || directionDownRight(CellType.WALL)){
            System.out.println("Flagged");
            if(vertiDir == Direction.DOWN){
                return  y+=1;
            }else{
                y-=1;
            }
        }else if(directionUPRight(CellType.WALL) || directionUPLeft(CellType.WALL)){
            System.out.println("Flagged2");
            if(vertiDir == Direction.DOWN && currtDir == Direction.LEFT){
                System.out.println("Flagged22");
                currtDir = Direction.RIGHT;
                return  x-=1;
            }else if(vertiDir == Direction.UP && currtDir == Direction.LEFT) {
                return x +=1;
            }else if(vertiDir == Direction.UP && currtDir ==Direction.RIGHT) {
                return y-=1;
            }else {
                System.out.println("Flagged2 Wong");
                vertiDir =Direction.UP;
                x+=1;
            }
        }
 */
/*
System.out.println("This is Right");
            if (directionUP(CellType.EMPTY) && directionDown(CellType.EMPTY) && directionDownRight(CellType.EMPTY)) {
                System.out.println("This is Right Dected");
                if (vertiDir == Direction.DOWN) {
                    vertiDir = Direction.UP;
                    return y += 1;
                } else {
                    return y -= 1;
                }
            } else if (directionDown(CellType.WALL) && directionDownLeft(CellType.WALL) && directionUP(CellType.EMPTY)) {
                if (vertiDir == Direction.DOWN) {
                    vertiDir = Direction.UP;
                    currtDir = Direction.LEFT;
                    return x - +1;
                } else {
                    vertiDir = Direction.UP;
                    return y -= 1;
                }
            } else if (directionUP(CellType.EMPTY) && directionDown(CellType.EMPTY) && directionDownRight(CellType.WALL)) {
                System.out.println("This is Right Dected Wrong");
                if (vertiDir == Direction.DOWN) {
                    return y += 1;
                } else {
                    vertiDir = Direction.UP;
                    return y -= 1;
                }
            }
 */

/*
System.out.println("This is Down");
            if (directionLeft(CellType.WALL) && directionRight(CellType.WALL)) {
                System.out.println("This is Down W+W");
                vertiDir = Direction.UP;
                return y -= 1;
            } else if (directionRight(CellType.EMPTY) && directionLeft(CellType.WALL) && directionUP(CellType.EMPTY) && directionDownRight(CellType.EMPTY)) {
                System.out.println("should be it ");
                if (vertiDir == Direction.DOWN) {
                    return x += 1;
                }
            } else if (directionRight(CellType.EMPTY) && directionLeft(CellType.WALL) && directionUP(CellType.EMPTY)) {
                System.out.println("This is Down E + W +W");
                if (vertiDir == Direction.DOWN && currtDir == Direction.LEFT) {
                    currtDir = Direction.LEFT;
                    vertiDir = Direction.UP;
                    return x += 1;
                } else if (vertiDir == Direction.DOWN && currtDir == Direction.RIGHT) {
                    currtDir = Direction.LEFT;
                    vertiDir = Direction.UP;
                    return x += 1;
                } else {
                    currtDir = Direction.RIGHT;
                    return y -= 1;
                }
            } else if (directionRight(CellType.EMPTY) && directionLeft(CellType.WALL) && directionUP(CellType.WALL)) {
                System.out.println("This is Down W + E +W last 2");
                if (vertiDir == Direction.DOWN) {
                    currtDir = Direction.RIGHT;
                    return x += 1;
                } else {
                    vertiDir = Direction.UP;
                    return y -= 1;
                }
            } else if (directionRight(CellType.WALL) && directionLeft(CellType.EMPTY) && directionUP(CellType.EMPTY) && directionDownLeft(CellType.EMPTY)) {
                System.out.println("should be it ");
                vertiDir = Direction.UP;
                return y -= 1;
            } else if (directionRight(CellType.WALL) && directionLeft(CellType.EMPTY) && directionUP(CellType.EMPTY)) {
                System.out.println("This is Down W + E +W last ");
                if (vertiDir == Direction.DOWN) {
                    currtDir = Direction.RIGHT;
                    vertiDir = Direction.UP;
                    x -= 1;
                } else {
                    vertiDir = Direction.UP;
                    return y -= 1;
                }
            } else if (directionRight(CellType.EMPTY) && directionLeft(CellType.EMPTY)) {
                if (currtDir == Direction.RIGHT) {
                    return x -= 1;
                } else {
                    return x += 1;
                }
            }
 */