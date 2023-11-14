import com.fasterxml.jackson.databind.ObjectMapper;
import factories.GameFactory;
import game.Game;
import game.Gamestate;
import input.HomeworkJSONTemplate;

import java.io.File;

public final class Main {

    private Main() { }

    public static void main(String[] args) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        HomeworkJSONTemplate inputData = objectMapper.readValue(
                new File(args[0]),
                HomeworkJSONTemplate.class
        );

        inputData.getMonthlyUpdates().add(0, null);
        Game game = GameFactory.getGameInstance(inputData);
        game.runGame(inputData.getMonthlyUpdates());

        Gamestate gamestate = game.getGamestate();
        objectMapper.writeValue(new File(args[1]), gamestate);
    }
}
