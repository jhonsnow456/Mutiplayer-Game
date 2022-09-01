package tictactoe;

import gamingplatformclient.server.Connector;

public class InitializeGame extends Connector
{
    String player_name;
    int team_size;
    public InitializeGame(String playerName, long gameId, int teamSize, int peerPort) throws Exception
    {
        super(playerName, gameId, teamSize, peerPort);
        this.player_name = playerName;
        this.team_size = teamSize;
    }
    
    public void startGame()
    {
        System.out.println("LETS PLAY TICTACTOE");
        new TicTacToe(player_name, team_size);
    }
    
}
