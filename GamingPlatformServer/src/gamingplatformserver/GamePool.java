package gamingplatformserver;
import java.io.StringWriter;
import java.util.*;
import org.json.JSONArray;
import org.json.JSONObject;

public class GamePool 
{
    HashMap<Long, LinkedList<Team>> pool;

    public GamePool() {
        pool = new HashMap<>();
    }
    
    public boolean addPlayer(long gid, Player pl)
    {
        Long l = Long.valueOf(gid);
        if(pool.containsKey(l))
        {
            LinkedList<Team> list = pool.get(l);
            for(Team t : list)
            {
                if(t.addPlayer(pl))
                {
                    return true;
                }
            }
            Team t = new Team(pl.teamSize);
            t.addPlayer(pl);
            list.add(t);
            return true;
        }
        else
        {
            LinkedList<Team> list = new LinkedList<>();
            Team t = new Team(pl.teamSize);
            t.addPlayer(pl);
            list.add(t);
            pool.put(l, list);
            return true;
        }
    }
    
    public String makeTeam(long gid, Player p)
    {
        Long l = Long.valueOf(gid);
        LinkedList<Team> list;
        if(pool.containsKey(l))
        {
            list = pool.get(l);
            for(Team t : list)
            {
                if(t.isFull())
                {
                    if(t.isPlayerInTeam(p))
                    {
                        int i;
                        JSONArray arr = new JSONArray();
                        for(i =0 ; i < t.team_size; i++)
                        {
                            Player temp = t.team.get(i);
                            if(!temp.match(p))
                            {
                                JSONObject jobj = new JSONObject();
                                jobj.put("PLAYER_NAME", temp.name);
                                jobj.put("PEER_IP", temp.ip);
                                jobj.put("PEER_PORT", temp.peerPort);
                                jobj.put("PLAYER_NUMBER", i);
                                arr.put(jobj);
                            }
                            else
                            {
                                temp.updateStatusAsPlaying();
                            }
                        }
                        
                        StringWriter sw = new StringWriter();
                        arr.write(sw);
                        dropTeam(gid);
                        return sw.toString();
                    }
                }
            }
            return null;
        }   
        return null;
    }
    
    public void dropTeam(long gid)
    {
        Long l = Long.valueOf(gid);
        LinkedList<Team> list;
        if(pool.containsKey(l))
        {
            list = pool.get(l);
            int i;
            for(i =0 ;i < list.size(); i++)
            {
                Team t = list.get(i);
                if(t.isFull())
                {
                    int cnt=0;
                    int j;
                    for(j =0 ; j< t.team_size; j++)
                    {
                        if(t.team.get(j).isPlayerPlaying())
                        {
                            cnt++;
                        }
                    }
                    if(cnt == t.team_size)
                    {
                        t.dropall();
                        list.remove(i);
                        i--;
                    }
                }
            }
        }   
    }
    
}

class Team
{
    LinkedList<Player> team;
    int team_size;
    Team(int ts)
    {
        team_size = ts;
        team = new LinkedList<Player>();
    }
    
    boolean isPlayerInTeam(Player pl)
    {
        for(Player p : team)
            if(p.match(pl))
                return true;
        return false;
    }
    
    boolean isFull()
    {
        return team_size == team.size();
    }
    
    boolean addPlayer(Player pl)
    {
        if(!isFull())
        {
            boolean flag = true;
            for(Player p : team)
            {
                if(p.match(pl))
                {
                    flag = false;
                    break;
                }
            }
            if(flag == true)
            {
                team.add(pl);
            }
            return true;
        }
        return false;
    }
    
    void dropall()
    {
        team.clear();
    }
}