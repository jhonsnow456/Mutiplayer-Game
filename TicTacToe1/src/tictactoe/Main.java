package tictactoe;
import gamingplatformclient.server.*;
import java.util.*;
import java.io.*;


public class Main //extends Connector //implements CommunicationClient
{
    static File resource_location ;
    
    public static void main(String[] args) 
    {
        try
        {
            int team_size , peer_port;
            long game_id;
            String player_name;
            
            Properties p = new Properties();
            p.load(Main.class.getResourceAsStream("game.properties"));
            String resource_game = p.getProperty("RESOURCE_GAME");
            String resource_player = p.getProperty("RESOURCE_PLAYER");
            
            File f1 = new File(resource_game);
            File f2 = new File(resource_player);
            System.out.println("*****"+ f1.getAbsolutePath());
            System.out.println("*****"+ f2.getAbsolutePath());
            if(f1.exists())
            {
                BufferedReader br = new BufferedReader(new FileReader(f1));
                game_id = Long.parseLong(br.readLine());
                team_size = Integer.parseInt(br.readLine());
                peer_port = Integer.parseInt(br.readLine());
                System.out.println("****" + game_id + " "+ team_size  + " " + peer_port);
            }
            else
            {
                team_size = 2;
                peer_port = 9900;
                game_id = GameIDGenerator.getGameId();
                
                FileWriter fw = new FileWriter(f1);
                fw.write(game_id+"\n");
                fw.write(team_size+"\n");
                fw.write(peer_port+"\n");
                fw.close();
            }

            if(f2.exists())
            {
                BufferedReader br = new BufferedReader(new FileReader(f2));
                player_name = br.readLine();
                System.out.println("****" + player_name);
            }
            else
            {
                byte arr[] = new byte[100];
                int n;
                System.out.println("Enter Player Name");
                System.in.skip(System.in.available());
                n = System.in.read(arr);
                player_name = new String(arr, 0, n);
                
                FileWriter fw = new FileWriter(f2);
                fw.write(player_name);
                fw.close();
            }

            System.out.println("About to call Connector(" + player_name + "," + game_id + "," + team_size + "," + peer_port + ")");
            InitializeGame ig = new InitializeGame(player_name, game_id, team_size, peer_port);
            
        }
        catch(Exception ex)
        {
            System.out.println("Err in main() " + ex);
            ex.printStackTrace();
        }
    }
    
}
