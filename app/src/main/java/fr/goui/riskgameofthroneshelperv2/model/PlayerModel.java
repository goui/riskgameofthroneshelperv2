package fr.goui.riskgameofthroneshelperv2.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Observable;
import java.util.Set;

/**
 * Singleton class managing players.
 */
public class PlayerModel extends Observable {

    private static PlayerModel instance;

    private List<Player> players;

    private Set<Integer> pickedColors;

    private PlayerModel() {
        players = new ArrayList<>();
        pickedColors = new HashSet<>();
        // min 2 players
        addPlayer();
        addPlayer();
    }

    public static PlayerModel getInstance() {
        if (instance == null) {
            instance = new PlayerModel();
        }
        return instance;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void addPlayer() {
        // picking the first available color
        int colorIndex = 0;
        for (int i = 0; i < 7; i++) {
            if (!pickedColors.contains(i)) {
                colorIndex = i;
                break; // sorry Cyril
            }
        }
        // creating the new player
        Player player = new Player();
        player.setColorIndex(colorIndex);
        players.add(player);
        pickedColors.add(colorIndex);
        // notifying observers
        setChanged();
        notifyObservers();
    }

    public void removePlayer() {
        // removing the last player
        Player player = players.get(players.size() - 1);
        players.remove(player);
        pickedColors.remove(player.getColorIndex());
        // notifying observers
        setChanged();
        notifyObservers();
    }

    /**
     * Picks next available color for a specific player.
     *
     * @param player the selected player
     * @return the new color index
     */
    public int getNextAvailableColorIndex(Player player) {
        // picking next available color
        int colorIndex = player.getColorIndex();
        do {
            colorIndex++;
            if (colorIndex > 6) {
                colorIndex = 0;
            }
        } while (pickedColors.contains(colorIndex));
        // updating picked colors
        pickedColors.remove(player.getColorIndex());
        pickedColors.add(colorIndex);
        // updating player color index
        player.setColorIndex(colorIndex);
        return colorIndex;
    }

    public Set<Integer> getPickedColors() {
        return pickedColors;
    }

    /**
     * Finds a player by his color index.
     *
     * @param colorIndex color index
     * @return the player with provided color index
     */
    public Player findPlayerByColorIndex(int colorIndex) {
        Player player = null;
        for (Player currentPlayer : players) {
            if (currentPlayer.getColorIndex() == colorIndex) {
                player = currentPlayer;
            }
        }
        return player;
    }
}
