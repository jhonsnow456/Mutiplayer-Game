package gamingplatformclient.peer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class TeamPlayer
{
    private Socket skt;
    private String ip;
    private int port, player_number;
    private String name;
    private DataInputStream dis;
    private DataOutputStream dos;
    
    public TeamPlayer(String name, String ip, int port, int player_number) throws Exception
    {
        this.name = name;
        this.ip = ip;
        this.port = port;
        this.player_number = player_number;
        skt = new Socket(ip, port);
        System.out.println("For me other player is: " + ip + "@" + port);
        dis = new DataInputStream(skt.getInputStream());
        dos = new DataOutputStream(skt.getOutputStream());
    }
    
    public String getName()
    {
        return name;
    }

    public int getPlayerNumber()
    {
        return player_number;
    }
    String getIP()
    {
        return ip;
    }

    int getPort()
    {
        return port;
    }
    
    DataOutputStream getOutputStream()
    {
        return dos;
    }
    
    DataInputStream getInputStream()
    {
        return dis;
    }
    
    void close()
    {
        try
        {
            skt.close();
        }
        catch(Exception ex)
        {}
    }
}
