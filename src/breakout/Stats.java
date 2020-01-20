package breakout;

/**
 * Unfortunately this class is entirely getters and setters
 * A Stats object exists to keep track of the stats of all of the game play in a breakout game
 * Assumptions:
 *      We're not going to go down a level for any reason
 *      There can only ever be one penalty in the game at one time
 * Stats doesn't depend on any other Classes, but does depend on the package breakout
 * Use a Stats object by changing the values of the instance variables using the setter methods,
 *      and getters to get their values
 */
public class Stats {

    public static final int STARTING_LIVES = 3;

    private int myScore;
    private int myLives;
    private int myLevelNumber;
    private int powerUpsCount;
    private boolean shieldExists;
    private boolean laserIsOnBoard;
    private boolean ballIsSticky;
    private boolean penaltyExists;
    private boolean paddleCanMove;
    private boolean startingAllowed;
    private boolean paddleIsNormal;

    public Stats(){
        myLives = STARTING_LIVES;
        myScore = 0;
        myLevelNumber = 1;
        powerUpsCount = 0;
        shieldExists = false;
        laserIsOnBoard = false;
        ballIsSticky = false;
        penaltyExists = false;
        paddleCanMove = false;
        startingAllowed = true;
        paddleIsNormal = true;
    }

    /**
     * add change to the total score
     * @param change can be positive or negative
     */
    public void updateScore(int change){
        myScore+=change;
    }

    /**
     * add change to the total lives
     * @param change can be positive or negative
     */
    public void updateLives(int change){
        myLives+=change;
    }

    /**
     * increment myLevelNumber
     */
    public void nextLevelNumber(){
        myLevelNumber++;
    }

    /**
     * set myLevelNumber to a new number, newNum
     * @param newNum is the new level number
     */
    public void changeLevelNumber(int newNum){
        myLevelNumber = newNum;
    }

    /**
     * change powerUpsCount by num
     * @param num
     */
    public void changePowerUpCount(int num){
        powerUpsCount+=num;
    }

    /**
     * a shield is on the screen
     */
    public void shieldAdded(){
        shieldExists = true;
    }

    /**
     * a pair of lasers is on/not on (condition) the screen
     * @param condition
     */
    public void setLaserStatus(boolean condition){
        laserIsOnBoard = condition;
    }

    /**
     * reset myLives to the staring value
     */
    public void resetLives(){
        myLives = STARTING_LIVES;
    }

    /**
     * set myLives to 0
     */
    public void dead(){
        myLives = 0;
    }

    /**
     * set ballIsSticky to condition
     * @param condition
     */
    public void setBallIsSticky(boolean condition){
        ballIsSticky = condition;
    }

    /**
     * set penaltyExists to condition
     * @param condition
     */
    public void setPenaltyStatus(boolean condition) {
        penaltyExists = condition;
    }

    /**
     * set paddleCanMove to condition
     * @param condition
     */
    public void setPaddleCanMove(boolean condition) {
        paddleCanMove = condition;
    }

    /**
     * set startingAllowed to condition
     * @param condition
     */
    public void setStartingAllowed(boolean condition) {
        startingAllowed = condition;
    }

    /**
     * set paddleIsNormal to condition
     * @param condition
     */
    public void setPaddleIsNormal(boolean condition) {
        paddleIsNormal = condition;
    }

    /**
     * @return myScore, the current game score
     */
    public int getMyScore(){
        return myScore;
    }

    /**
     * return the current number of lives
     * @return myLives
     */
    public int getMyLives(){
        return myLives;
    }

    /**
     * return the current level number
     * @return myLevelNumber
     */
    public int getMyLevelNumber(){
        return myLevelNumber;
    }

    /**
     * return the number of power ups on the screen
     * @return powerUpsCount
     */
    public int getPowerUpsCount(){
        return powerUpsCount;
    }

    /**
     * returns whether or not a shield is on the screen
     * @return sheildExists
     */
    public boolean doesShieldExist() {
        return shieldExists;
    }

    /**
     * returns if whether or not there is a laser on the board
     * @return laserIsOnBoard
     */
    public boolean isThereALaserOnBoard() {
        return laserIsOnBoard;
    }

    /**
     * returns if the ball is sticky or not
     * @return ballIsSticky
     */
    public boolean isBallSticky() {
        return ballIsSticky;
    }

    /**
     * returns if there is a penalty on the screen or not
     * @return penaltyExists
     */
    public boolean doesPenaltyExist() {
        return penaltyExists;
    }
    /**
     * returns if the paddle can move or not
     * @return paddleCanMove
     */
    public boolean canPaddleMove() {
        return paddleCanMove;
    }

    /**
     * returns if the game is allowed to be started or not
     * @return startingAllowed
     */
    public boolean isStartingAllowed() {
        return startingAllowed;
    }

    /**
     * returns if the paddle is a normal size or not
     * @return paddleIsNormal
     */
    public boolean isPaddleNormal() {
        return paddleIsNormal;
    }
}
