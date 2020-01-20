package breakout;

public class PenaltyBrick extends PowerUpBrick {

    private Penalty penalty;
    private int xPosition;
    private int yPosition;
    private String penaltyType;

    public PenaltyBrick(String type, int xPos, int yPos){
        super(type,xPos,yPos);
        penaltyType = type;
        xPosition = xPos;
        yPosition = yPos;
    }
    @Override
    public void dropPackage(){
        penalty = new Penalty(powerUpType, xPosition, yPosition);
    }
    @Override
    public Penalty getPackage(){
        return penalty;
    }
    @Override
    public void movePackage(double elapsedTime){
        penalty.updateCenter(elapsedTime);
    }
}
