package tictactoe;

import java.io.StringWriter;
import org.json.*;

public class Game {

    Player players[];
    Board board;
    int currentPlayer;
    int myself;

    public Game(String player1, int playerNumber1, String player2, int playerNumber2) {
        players = new Player[2];
        myself = playerNumber1;
        if(playerNumber1 == 0)
        {
            players[0] = new Player(player1, 'X');
            players[1] = new Player(player2, 'O');
        }
        else
        {
            players[0] = new Player(player2, 'X');
            players[1] = new Player(player1, 'O');
        }
        
        currentPlayer = 0;
        board = new Board();
    }

    public boolean isMyTurn()
    {
        return myself == currentPlayer;
    }

    public void changePlayer() {
        currentPlayer = (currentPlayer + 1) % 2;
    }

    public int getCurrentPlayer() {
        return currentPlayer;
    }

    public String getCurrentPlayerName()
    {
        return players[currentPlayer].getName();
    }
    
    public String getOtherPlayerName()
    {
        int q;
        if(currentPlayer == 0)
            q = 1;
        else
            q = 0;
        return players[q].getName();
    }
    
    public char getCurrentPlayerSymbol() {
        return players[currentPlayer].symbol;
    }

    public boolean move(int x, int y) {
        if(isMyTurn())
        {
            if(board.play(x, y, players[currentPlayer].symbol))
            {
                return true;
            }
        }
        return false;
    }

    public boolean checkWin() {
        return board.checkWin(players[currentPlayer].symbol);

    }

    public boolean checkDraw()
    {
        return board.isFull();
    }
    
    public String toJSONString() {
        JSONObject jobj = new JSONObject();
        jobj.put("currentplayer", currentPlayer);
        jobj.put("board", board.toJSONArray());
        JSONArray jarr = new JSONArray();
        int i;
        for (i = 0; i < players.length; i++) {
            jarr.put(players[i].toJSONObject());
        }
        jobj.put("players", jarr);

        StringWriter sw = new StringWriter();
        jobj.write(sw);
        return sw.toString();

    }

    public void fromJSONString(String json) {
        JSONObject jobj = new JSONObject(json);
        
        //update current player
        currentPlayer = jobj.getInt("currentplayer");

        //update board
        JSONArray boardArr = jobj.getJSONArray("board");
        int i;
        for (i = 0; i < boardArr.length(); i++) 
        {
            JSONArray row = boardArr.getJSONArray(i);
            int j;
            
            for (j = 0; j < row.length(); j++) 
            {
                board.updateBoard(i, j, (char) row.getInt(j));
            }
        }

        //update players
        JSONArray playerArr = jobj.getJSONArray("players");
        for(i =0 ; i < playerArr.length(); i++)
        {
            JSONObject playerObj = playerArr.getJSONObject(i);
            players[i].setName(playerObj.getString("name"));
            players[i].setSymbol((char)playerObj.getInt("symbol"));
        }

    }

}

class Player {

    String name;
    char symbol;

    Player(String nm, char sym) {
        name = nm;
        symbol = sym;
    }

    String getName() {
        return name;
    }

    char getSymbol() {
        return symbol;
    }
    
    
    void setSymbol(char sym)
    {
        symbol = sym;
    }
    
    void setName(String nm)
    {
        name = nm;
    }

    JSONObject toJSONObject() {
        JSONObject jobj = new JSONObject();
        jobj.put("name", name);
        jobj.put("symbol", symbol);
        return jobj;
    }
}
