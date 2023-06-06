package dungeonmania;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.NotSerializableException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;

import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.ResponseBuilder;
import dungeonmania.util.Direction;
import dungeonmania.util.FileLoader;

public class DungeonManiaController {
    private Game game = null;

    public String getSkin() {
        return "default";
    }

    public String getLocalisation() {
        return "en_US";
    }

    /**
     * /dungeons
     */
    public static List<String> dungeons() {
        return FileLoader.listFileNamesInResourceDirectory("dungeons");
    }

    /**
     * /configs
     */
    public static List<String> configs() {
        return FileLoader.listFileNamesInResourceDirectory("configs");
    }

    /**
     * /game/new
     */
    public DungeonResponse newGame(String dungeonName, String configName) throws IllegalArgumentException {
        if (!dungeons().contains(dungeonName)) {
            throw new IllegalArgumentException(dungeonName + " is not a dungeon that exists");
        }

        if (!configs().contains(configName)) {
            throw new IllegalArgumentException(configName + " is not a configuration that exists");
        }

        try {
            GameBuilder builder = new GameBuilder();
            game = builder.setConfigName(configName).setDungeonName(dungeonName).buildGame();
            return ResponseBuilder.getDungeonResponse(game);
        } catch (JSONException e) {
            return null;
        }
    }

    /**
     * /game/dungeonResponseModel
     */
    public DungeonResponse getDungeonResponseModel() {
        return null;
    }

    /**
     * /game/tick/item
     */
    public DungeonResponse tick(String itemUsedId) throws IllegalArgumentException, InvalidActionException {
        return ResponseBuilder.getDungeonResponse(game.tick(itemUsedId));
    }

    /**
     * /game/tick/movement
     */
    public DungeonResponse tick(Direction movementDirection) {
        return ResponseBuilder.getDungeonResponse(game.tick(movementDirection));
    }

    /**
     * /game/build
     */
    public DungeonResponse build(String buildable) throws IllegalArgumentException, InvalidActionException {
        List<String> validBuildables = List.of("bow", "shield", "midnight_armour", "sceptre");
        if (!validBuildables.contains(buildable)) {
            throw new IllegalArgumentException("Only bow, shield, midnight_armour and sceptre can be built");
        }

        return ResponseBuilder.getDungeonResponse(game.build(buildable));
    }

    /**
     * /game/interact
     */
    public DungeonResponse interact(String entityId) throws IllegalArgumentException, InvalidActionException {
        return ResponseBuilder.getDungeonResponse(game.interact(entityId));
    }

    /**
     * /game/save
     */
    public DungeonResponse saveGame(String name) throws IllegalArgumentException {
        // Serialise the entire Game

        // Make absolute directory
        String absoluteDirectoryPath = System.getProperty("user.dir") + "/saves";

        // Check if directory exists, make directory if doesnt exist
        File saveDir = new File(absoluteDirectoryPath);
        if (!saveDir.exists()) {
            saveDir.mkdir();
        }

        try {
            // Make new file
            File newFile = new File(absoluteDirectoryPath + "/" + name);
            newFile.createNewFile();
            FileOutputStream fileOut = new FileOutputStream(newFile);

            ObjectOutputStream objSave = new ObjectOutputStream(fileOut);
            objSave.writeObject(game);
            objSave.close();
            fileOut.close();
        } catch (NullPointerException nex) {
            throw new NullPointerException("Null was returned somewhere...");
        } catch (NotSerializableException ioex) {
            ioex.printStackTrace();
            System.out.println(ioex);
            throw new IllegalArgumentException("Not Serialisable! SOMETHING WENT WRONG");
        } catch (FileNotFoundException fnfex) {
            fnfex.printStackTrace();
            throw new IllegalArgumentException("FILE NOT THERE SOMETHING WENT WRONG");
        } catch (IOException ioex) {
            ioex.printStackTrace();
            throw new IllegalArgumentException("IO PROBLEM SOMETHING WENT WRONG");
        } catch (Exception ex) {
            throw new IllegalArgumentException("BRUH UNKNOWN EXCEPTION");
        }
        return ResponseBuilder.getDungeonResponse(game);
    }

    /**
     * /game/load
     */
    public DungeonResponse loadGame(String name) throws IllegalArgumentException {
        if (!allGames().contains(name)) {
            throw new IllegalArgumentException("Game save does not exist!");
        }

        // Save path for game saves
        String absoluteGameSavePath = System.getProperty("user.dir") + "/saves/" + name;

        try {
            FileInputStream fileIn = new FileInputStream(absoluteGameSavePath);
            ObjectInputStream objLoad = new ObjectInputStream(fileIn);

            game = (Game) objLoad.readObject();
            objLoad.close();
            fileIn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseBuilder.getDungeonResponse(game);
    }

    /**
     * Returns the path of a new file to be created that is relative to resources/.
     * Will add a `/` prefix to path if it's not specified.
     *
     * General purpose code, sourced from forum post
     *
     * @precondition the `resources/directory` MUST exist before, otherwise throws
     *               NullPointerException
     * @param directory Relative to resources/ will add an implicit `/` prefix if
     *                  not given.
     * @param newFile   file name
     * @return the full path as a string
     * @throws FileNotFoundException directory does not exist
     */
    public static String getPathForNewFile(String directory, String newFile) throws FileNotFoundException {
        if (!directory.startsWith("/"))
            directory = "/" + directory;
        try {
            Path root = Paths.get(FileLoader.class.getResource(directory).toURI());
            return root.toString() + "/" + newFile;
        } catch (URISyntaxException e) {
            throw new FileNotFoundException(directory);
        }
    }

    /**
     * /games/all
     */
    public List<String> allGames() {
        String dirPath = System.getProperty("user.dir") + "/saves";

        // Return list
        ArrayList<String> games = new ArrayList<String>();

        // Adds file names to the list
        File saves = new File(dirPath);
        File[] savedGames = saves.listFiles();
        for (int i = 0; i < savedGames.length; i++) {
            games.add(savedGames[i].getName());
        }
        return games;
    }

    /**
     * /game/new/generate
     */
    public DungeonResponse generateDungeon(
            int xStart, int yStart, int xEnd, int yEnd, String configName) throws IllegalArgumentException {
        return null;
    }

    /**
     * /game/rewind
     */
    public DungeonResponse rewind(int ticks) throws IllegalArgumentException {
        return null;
    }

}
