package tictactoe;
import org.json.*;

public class Board 
{
    private char board [][];
    public static final char EMPTY = ' ';

    public Board() 
    {
        board = new char[3][3];
        initialize();
    }
    
    void initialize()
    {
        int i, j;
        for(i =0 ; i < board.length; i++)
        {
            for(j =0 ; j < board[i].length; j++)
            {
                board[i][j] = EMPTY;
            }
        }
    }
    
    JSONArray toJSONArray()
    {
        JSONArray arr = new JSONArray();
        int i, j;
        for(i =0 ; i < board.length; i++)
        {
            JSONArray row = new JSONArray();
            for(j =0 ; j < board[i].length; j++)
            {
                row.put(board[i][j]);
            }
            arr.put(row);
        }
        return arr;
    }
    
    void updateBoard(int x, int y, char symbol)
    {
        board[x][y] = symbol;
    }
    
    boolean isFull()
    {
        int i, j;
        for(i =0; i < 3; i++)
        {
            for(j =0; j < 3; j++)
            {
                if(board[i][j] == EMPTY)
                    return false;
            }
        }
        return true;
    }
    boolean play(int x, int y, char symbol)
    {
        try
        {
            if(board[x][y] == EMPTY)
            {
                board[x][y] = symbol;
                return true;
            }
            return false;
        }
        catch(Exception ex)
        {
            return false;
        }
        
    }
    
    boolean checkWin(char sym)
    {
        if (board[0][0] == sym && board[1][1] == sym && board[2][2] == sym)
            return true;
        if (board[0][2] == sym && board[1][1] == sym && board[2][0] == sym)
            return true;
        
        int i;
        for(i =0 ; i< board.length; i++)
        {
            if(board[i][0] == sym && board[i][1]== sym && board[i][2] == sym)
                return true;
        }
        
        for(i =0 ; i< board.length; i++)
        {
            if(board[0][i] == sym && board[1][i]== sym && board[2][i] == sym)
                return true;
        }
        
        return false;
    }
    
}
