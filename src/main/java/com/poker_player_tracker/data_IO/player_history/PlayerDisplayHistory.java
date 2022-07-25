package com.poker_player_tracker.data_IO.player_history;

import com.poker_player_tracker.data_IO.RequiredFileAccessDeniedException;
import com.poker_player_tracker.data_IO.RequiredFileNotFoundException;
import com.poker_player_tracker.data_IO.game_file_history.FolderPath;
import com.poker_player_tracker.data_IO.player_data.PlayerDisplayData;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.LinkedList;

public class PlayerDisplayHistory extends PlayerHistoryTemplate {

    /**
     * Loads location history file from PlayerDataDisplayHistory.dat <p></p>
     * <p>
     * If file doesn't exist, it will be created.
     * see {@link PlayerHistoryTemplate#loadFileData(FolderPath) Abstract parent class}
     *
     * @throws RequiredFileNotFoundException SecurityException if access to read or write is denied by a security manager.
     */
    public PlayerDisplayHistory() throws RequiredFileNotFoundException {
        loadFileData(FolderPath.DISPLAY_PLAYER_DATA);
    }

    /**
     * Writes {@link PlayerDisplayData} to {@link FolderPath#DISPLAY_PLAYER_DATA} .dat file.
     * <p>
     * Increments and updates total entries saved in file at position 0.
     *
     * @param player         Player data to be written.
     * @param playerLocation Previously written location for player, values will be overwritten by new values.
     * @throws RequiredFileNotFoundException Throws if file cannot be read / written too.
     */
    public void writePlayerData(PlayerDisplayData player, PlayerLocationData playerLocation) throws RequiredFileNotFoundException {
        try (RandomAccessFile raf = new RandomAccessFile(path.getFolderPath(), "rw")) {
            raf.seek(playerLocation.getDisplayPositionStart());
            raf.writeUTF(player.getUserName());
            for (Double stat : player.getVariables()) {
                raf.writeDouble(stat);
            }
            totalEntries++;
            raf.seek(0);
            raf.writeInt(totalEntries);
        }
        catch(SecurityException e){
            throw new RequiredFileAccessDeniedException(path.getFolderPath(), e);
        } catch (IOException e ){
            throw new RequiredFileNotFoundException(path.getFolderPath(), e);
        }
    }

    /**
     * Writes new {@link PlayerDisplayData} to {@link FolderPath#DISPLAY_PLAYER_DATA} .dat file.
     *
     * @param player New player to be added to .dat file.
     * @return Returns {@link PlayerLocationData} containing start and end point of players data in .dat file.
     *
     * @throws RequiredFileNotFoundException Throws if file cannot be read / written too.
     */
    public PlayerLocationData writePlayerData(PlayerDisplayData player) throws RequiredFileNotFoundException {
        PlayerLocationData playerLocation = new PlayerLocationData(player.getUserName());

        try (RandomAccessFile raf = new RandomAccessFile(path.getFolderPath(), "rw")) {
            raf.seek(raf.length());
            previousPointer = raf.getFilePointer();
            playerLocation.setDisplayPositionStart(previousPointer);
            raf.writeUTF(player.getUserName());
            for (Double stat : player.getVariables()) {
                raf.writeDouble(stat);
            }
            playerLocation.setDisplayPositionEnd(raf.getFilePointer());
            return playerLocation;
        }catch(SecurityException e){
            throw new RequiredFileAccessDeniedException(path.getFolderPath(), e);
        } catch (IOException e ){
            throw new RequiredFileNotFoundException(path.getFolderPath(), e);
        }
    }

    /**
     * Reads {@link PlayerDisplayData} from {@link FolderPath#DISPLAY_PLAYER_DATA} .dat file.
     *
     * @param playerLocation To be read from .dat file.
     * @return Returns {@code PlayerDisplayData} if located. Returns {@code Null} if player cannot be located.
     *
     * @throws RequiredFileNotFoundException Throws if file cannot be read / written too.
     */
    public PlayerDisplayData readPlayerData(PlayerLocationData playerLocation) throws RequiredFileNotFoundException {
        PlayerDisplayData player;
        try (RandomAccessFile raf = new RandomAccessFile(path.getFolderPath(), "rw")) {
            raf.seek(playerLocation.getDisplayPositionStart());
            String userName = raf.readUTF();
            if (!playerLocation.getUserName().equals(userName)) {
                return null;
            }
            player = new PlayerDisplayData(userName);
            LinkedList<Double> playerVariables = player.getVariableList();
            while (raf.getFilePointer() < playerLocation.getDisplayPositionEnd()) {
                // TODO: add 'better' logic for adding object variables.
                // player.set1, player.set2....
                playerVariables.offer(raf.readDouble());
            }
            player.applyVariableList(playerVariables);
        }catch(SecurityException e){
            throw new RequiredFileAccessDeniedException(path.getFolderPath(), e);
        } catch (IOException e ){
            throw new RequiredFileNotFoundException(path.getFolderPath(), e);
        }
        return player;
    }

}

