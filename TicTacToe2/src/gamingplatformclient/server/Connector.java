package gamingplatformclient.server;

import gamingplatformclient.*;
import java.io.*;
import java.net.*;
import java.util.*;
import org.json.JSONObject;
import gamingplatformclient.peer.*;

public abstract class Connector implements PoolClient
{
    String player_name;
    int peer_port;
    long game_id;
    int team_size;
    Socket svr_conn;
    DataInputStream dis;
    DataOutputStream dos;
    boolean keep_running = true;
    int connect_status = 0;
    
    public Connector(String playerName, long gameId, int teamSize, int peerPort) throws Exception
    {
        
        player_name = playerName;
        game_id = gameId;
        team_size = teamSize;
        peer_port = PeerServer.getInstance(peerPort).getPeerPort();
        
        Properties p = new Properties();
        p.load(Connector.class.getResourceAsStream("server.properties"));
        String server_ip = p.getProperty("SERVER_IP");
        int server_port = Integer.parseInt(p.getProperty("SERVER_PORT"));
        
        svr_conn = new Socket(server_ip, server_port);
        dis = new DataInputStream(svr_conn.getInputStream());
        dos = new DataOutputStream(svr_conn.getOutputStream());
    
        connect_status  = 1;
        ThreadPool.getInstance().execute(this);
        
    }

    public abstract void startGame();
    
    
    protected void finalize()
    {
        try
        {
            close();
        }
        catch(Exception ex)
        {}
    }
    
    public void close()
    {
        try
        {
            keep_running = false;
            dis.close();
            dos.close();
            svr_conn.close();
        }
        catch(Exception ex)
        {
            System.out.println("Err in Connector close() :"+ ex);
        }
    }

    
    public void perform()
    {
        int resp_status;
        String resp_data;
        while(keep_running)
        {
            try
            {
                if(connect_status == 1)
                {
                    //register to play
                    JSONObject jobj = new JSONObject();
                    jobj.put("GAME_ID", game_id);
                    jobj.put("TEAM_SIZE", team_size);
                    jobj.put("PLAYER_NAME", player_name);
                    jobj.put("PEER_PORT", peer_port);

                    StringWriter sw = new StringWriter();
                    jobj.write(sw);
                
                    dos.writeInt(CommunicationFlags.PLAY_REQUEST);
                    dos.writeUTF(sw.toString());
                    resp_status = dis.readInt();
                    resp_data = dis.readUTF();
                    
                    if(resp_status == CommunicationFlags.PLAY_REQUEST_RECORDED)
                    {
                        connect_status =2;
                    }
                }
                else if(connect_status == 2)
                {
                    //know about team status
                    dos.writeInt(CommunicationFlags.TEAM_STATUS_REQUEST);
                    
                    JSONObject jobj = new JSONObject();
                    jobj.put("GAME_ID", game_id);
                    jobj.put("TEAM_SIZE", team_size);
                    jobj.put("PLAYER_NAME", player_name);
                    jobj.put("PEER_PORT", peer_port);

                    StringWriter sw = new StringWriter();
                    jobj.write(sw);
                    dos.writeUTF(sw.toString());
                    
                    resp_status = dis.readInt();
                    resp_data = dis.readUTF();
                    
                    if(resp_status == CommunicationFlags.WAIT_FOR_TEAM_FORMATION)
                    {
                        System.out.println("Waiting for team formation");
                    }
                    else if(resp_status == CommunicationFlags.TEAM_FORMED_START_PLAYING)
                    {
                        try
                        {
                            Peers.getInstance(resp_data);
                            startGame();
                        }
                        catch(Exception ex)
                        {
                            System.out.println("Couldnt form peer to peer network for the team");
                        }
                        connect_status =3;
                    }
                    
                }
                else if(connect_status ==3)
                {
                    dos.writeInt(CommunicationFlags.DISCONNECT);
                    dos.writeUTF("{}");
                    close();
                }
                
                Thread.sleep(1000);
            }
            catch(Exception ex)
            {
                System.out.println("Err in request loop: " + ex);
            }
        }
    }
    
}
