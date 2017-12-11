
import java.util.List;

public class Board {

    private static Board b = null;
    private static int score;

    private Config boardConfig;
    private BoardView boardView;

    private Board() {

    }

    public static Board getInstance() {
        if (b == null) {
            b = new Board();
        }

        return b;
    }

    
    public void init(Config config) {
        score = 0;
        boardConfig = config;
    }


    @Override
    public String toString() {
        return "Board";
    }


    private void resetNeighbors() {
        boardConfig.resetNeighbors();
    }


    
    public void gameOver() {
        boardConfig.gameOver();
    }

    public int getScore() {
        return score;
    }

    public void updateScore() {
        score++;
        boardView.notifyScoreChanged(score);
    }

   
    public void getNewLocation(Element caller) {
        if (boardConfig != null) {
            boardConfig.getFreeLocation(caller);
        }
    }

    public void register(BoardView boardView) {
        this.boardView = boardView;
    }

    public int getNumberOfCats() {

        if (boardConfig.getCats() == null) {
            System.out.println("BOARDCONFIG RETUNS NULL?!");
        }
        return boardConfig.getCats().size();
    }

    public void notifyLivesChanged(int livesRemaining) {
        boardView.notifyLivesChanged(livesRemaining);
    }

    public void notifyDataChanged() {
        boardView.notifyDataChanged();
    }

    public boolean areAllCatsTrapped() {

        List<Cat> allCats = boardConfig.getCats();
        boolean result = true;

        for (Cat c : allCats) {
            if (!c.isTrappedInWalls()) {
                result = false;
                break;
            }
        }

        return result;
    }
}