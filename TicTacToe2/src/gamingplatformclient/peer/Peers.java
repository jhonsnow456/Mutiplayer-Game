package gamingplatformclient.peer;

import gamingplatformclient.*;
import java.net.*;
import java.io.*;
import java.util.*;
import org.json.*;
        
public class Peers implements PoolClient
{
    LinkedList<TeamPlayer> team;
    LinkedList<String> updates;
    boolean keep_running;
    private static Peers flag = null;
    private MessageRecipient recipient;
    
    public void setMessageRecipient(MessageRecipient mr)
    {
        recipient = mr;
    }
    
    public MessageRecipient getMessageRecipient()
    {
        return recipient;
    }
    
    public static Peers getInstance(String allpeers) throws Exception
    {
        if(flag == null)
            flag = new Peers(allpeers);
        
        return flag;
    }
    public static Peers getInstance()
    {
        return flag;
    }
    
    private Peers(String allpears) throws Exception
    {
        team = new LinkedList<>();
        JSONArray jarr = new JSONArray(allpears);
        JSONObject jobj;
        int i ;
        for(i =0 ; i < jarr.length(); i++)
        {
            jobj = jarr.getJSONObject(i);
            team.add(new TeamPlayer(jobj.getString("PLAYER_NAME"), jobj.getString("PEER_IP"), jobj.getInt("PEER_PORT"),  jobj.getInt("PLAYER_NUMBER")));
        }
        updates = new LinkedList<>();
        keep_running = true;
        ThreadPool.getInstance().execute(this);
    }
    
    public void addUpdate(String update)
    {
        System.out.println("addUpdate(" + update+")");
        updates.add(update);
    }
    
    public LinkedList<TeamPlayer> getTeam()
    {
        return team;
    }
    
    public void disconnect()
    {
        try
        {
            keep_running = false;
            flag = null;
            for(TeamPlayer p : team)
            {
                p.getOutputStream().writeUTF("{DISCONNECTING}");
                p.getOutputStream().writeBoolean(false);
                p.close();
            }
        }
        catch(Exception ex)
        {}
        
    }
    public void perform()
    {
        String update;
        
        while(keep_running)
        {
            try
            {
                //System.out.println("updates size: " + updates.size());
                Thread.sleep(100);
                if(updates.size() > 0)
                {
                    System.out.println("GOT AN UPDATE");
                        
                    update = updates.remove();
                    for(TeamPlayer p : team)
                    {
                        System.out.println("Peers sharing data: " + update);
                        p.getOutputStream().writeUTF(update);
                        p.getOutputStream().writeBoolean(true);
                    }
                }
            }
            catch(Exception ex)
            {
                System.out.println("Exception in PeerClient" + ex);
            }
        }
        
    }
}
