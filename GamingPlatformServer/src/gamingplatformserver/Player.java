package gamingplatformserver;

public class Player 
{
    String name;
    String ip;
    int peerPort;
    int teamSize;
    int status ;
    Player(String nm, String ip, int pp, int ts)
    {
        name = nm;
        this.ip = ip;
        peerPort = pp;
        teamSize = ts;
        status = 0;
    }
    
    public void updateStatusAsPlaying()
    {
        status = 1;
    }
    
    public boolean isPlayerPlaying()
    {
        return status == 1;
    }
    public boolean match(Player temp)
    {
        return name.equals(temp.name) && ip.equals(temp.ip) && peerPort == temp.peerPort && teamSize == temp.teamSize;
    }
}
