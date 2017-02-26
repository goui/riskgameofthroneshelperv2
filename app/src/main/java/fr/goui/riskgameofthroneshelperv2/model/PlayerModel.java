package fr.goui.riskgameofthroneshelperv2.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Observable;
import java.util.Set;

import fr.goui.riskgameofthroneshelperv2.Utils;

import static fr.goui.riskgameofthroneshelperv2.Utils.INVALID;
import static fr.goui.riskgameofthroneshelperv2.Utils.MIN_TROOPS;

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
        int color = 0;
        int[] colors = Utils.getInstance().getColorsArray();
        for (int currentColor : colors) {
            if (!pickedColors.contains(currentColor)) {
                color = currentColor;
                break; // sorry Cyril
            }
        }
        // creating the new player
        Player player = new Player();
        player.setColor(color);
        players.add(player);
        pickedColors.add(color);
        // notifying observers
        setChanged();
        notifyObservers();
    }

    public void removePlayer() {
        // removing the last player
        Player player = players.get(players.size() - 1);
        players.remove(player);
        pickedColors.remove(player.getColor());
        // notifying observers
        setChanged();
        notifyObservers();
    }

    /**
     * Picks the next available color for a specific player.
     *
     * @param player the selected player
     * @return the new color resource id
     */
    public int getNextAvailableColor(Player player) {
        // getting current color index
        int color = player.getColor();
        int[] colors = Utils.getInstance().getColorsArray();
        int colorIndex = 0;
        for (int i = 0; i < colors.length; i++) {
            int currentColor = colors[i];
            if (currentColor == color) {
                colorIndex = i;
            }
        }
        // picking next available color
        do {
            colorIndex++;
            if (colorIndex > 6) {
                colorIndex = 0;
            }
            color = colors[colorIndex];
        } while (pickedColors.contains(color));
        // updating picked colors
        pickedColors.remove(player.getColor());
        pickedColors.add(color);
        // updating player color index
        player.setColor(color);
        return color;
    }

    public Set<Integer> getPickedColors() {
        return pickedColors;
    }

    /**
     * Finds a player by his color index.
     *
     * @param colorId the color resource id
     * @return the player with provided color
     */
    public Player findPlayerByColor(int colorId) {
        Player player = null;
        for (Player currentPlayer : players) {
            if (currentPlayer.getColor() == colorId) {
                player = currentPlayer;
            }
        }
        return player;
    }

    /**
     * Calculates the number of troops for all the players.
     */
    public void computeNumberOfTroopsForPlayers() {
        for (Player player : players) {
            int troops = player.getTerritories().size();
            for (Territory territory : player.getTerritories()) {
                // a territory with a castle is worth the double
                troops += territory.getCastle();
            }
            // region bonus
            player.setRegionBonus(computeRegionBonus(player));
            // game algorithm for troops depending on territory control
            troops = Math.max(troops / MIN_TROOPS, MIN_TROOPS);
            troops += player.getRegionBonus();
            player.setNumberOfTroops(troops);
        }
    }

    private int computeRegionBonus(Player player) {
        int regionBonus = 0;
        for (Map currentMap : MapModel.getInstance().getMaps()) {
            for (Region region : currentMap.getRegions()) {
                int color = isRegionControlled(region);
                // if region is controlled by this player, giving him the bonus
                if (player.getColor() == color) {
                    regionBonus += region.getBonus();
                }
            }
        }
        return regionBonus;
    }

    /**
     * Checks if region is controlled.
     *
     * @param region the region
     * @return the color of the region if controlled, -1 otherwise
     */
    private int isRegionControlled(Region region) {
        // getting the color of the first territory
        int color = region.getTerritories().get(0).getColor();
        boolean controlled = true;
        for (int i = 1; i < region.getTerritories().size(); i++) {
            Territory territory = region.getTerritories().get(i);
            // if just one of the territories has a different color, the region is not controlled
            if (territory.getColor() != color) {
                controlled = false;
                break;
            }
        }
        return controlled ? color : INVALID;
    }

    /**
     * Calculates the end game points for each player.
     */
    public void computeEndGamePoints() {
        for (Player player : players) {
            int troops = player.getTerritories().size();
            for (Territory territory : player.getTerritories()) {
                troops += territory.getCastle();
                troops += territory.getPort();
            }
            player.setNumberOfTroops(troops);
        }
    }
}
