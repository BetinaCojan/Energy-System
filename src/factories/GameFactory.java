package factories;

import game.Game;
import input.HomeworkJSONTemplate;

public final class GameFactory {
    private GameFactory() {

    }

    public static Game getGameInstance(final HomeworkJSONTemplate inputData) {
        return new Game(inputData.getNumberOfTurns(), inputData.getInitialData());
    }
}
