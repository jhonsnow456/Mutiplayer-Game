package gamingplatformserver;

import java.net.*;
import java.io.*;
import org.json.*;

public class ConnectionManager implements PoolClient 
{
    ServerSocket port;
    boolean connectionFlag;
    GamePool gamePool;
    
    public ConnectionManager(int portNumber) throws Exception
    {
        //open a port (connection endpoint)
        port = new ServerSocket(portNumber);

        //dedicate a thread for accepting client connections
        connectionFlag = true;
        
        gamePool = new GamePool();
        ThreadPool tpool = ThreadPool.getInstance();
        tpool.execute(this);
    }
    
    //runs in a new thread
    public void perform()
    {
        while(connectionFlag)
        {
            try
            {
                Socket client = port.accept();
                //Dispatch a thread for IO
                ThreadPool tp = ThreadPool.getInstance();
                tp.execute(new ConnectionIO(client, gamePool));
            }
            catch(Exception ex)
            {}
        }
    }//perform
        
}

class ConnectionIO implements PoolClient
{
    Socket client;
    GamePool game_pool;
    public ConnectionIO(Socket s, GamePool gpool) 
    {
        client = s;
        game_pool = gpool;
    }
    
    public void perform()
    {
        try
        {
            //fetch socket i/o streams
            InputStream is = client.getInputStream();
            OutputStream os = client.getOutputStream();

            //For i/o in std Java types
            DataInputStream din = new DataInputStream(is);
            DataOutputStream dout = new DataOutputStream(os);

            while(true)
            {
                //read request id
                int request_id = din.readInt();
                //read request
                String request = din.readUTF();

                //process
                if(request_id == CommunicationFlags.GENERATE_GAME_ID)
                {
                    long gid = GameID.getInstance().getGameId();
                    if(gid != 0)
                    {
                        dout.writeInt(CommunicationFlags.STATUS_OK);
                        JSONObject obj = new JSONObject();
                        obj.put("GAME_ID", gid);
                        StringWriter wr = new StringWriter();
                        obj.write(wr);
                        dout.writeUTF(wr.toString());
                    }
                }
                else if(request_id == CommunicationFlags.PLAY_REQUEST)
                {
                    JSONObject jobj = new JSONObject(request);
                    long game_id =jobj.getLong("GAME_ID");
                    int team_size = jobj.getInt("TEAM_SIZE");
                    String  player_name = jobj.getString("PLAYER_NAME");
                    int peer_port = jobj.getInt("PEER_PORT");
                    InetSocketAddress sockaddr = (InetSocketAddress)client.getRemoteSocketAddress();
                    String ip = sockaddr.getAddress().getHostAddress();
                    game_pool.addPlayer(game_id, new Player(player_name,ip, peer_port, team_size ));
                    dout.writeInt(CommunicationFlags.PLAY_REQUEST_RECORDED);
                    dout.writeUTF("{}");
                }
                else if(request_id == CommunicationFlags.TEAM_STATUS_REQUEST)
                {
                    JSONObject jobj = new JSONObject(request);
                    long game_id =jobj.getLong("GAME_ID");
                    int team_size = jobj.getInt("TEAM_SIZE");
                    String  player_name = jobj.getString("PLAYER_NAME");
                    int peer_port = jobj.getInt("PEER_PORT");
                    InetSocketAddress sockaddr = (InetSocketAddress)client.getRemoteSocketAddress();
                    String ip = sockaddr.getAddress().getHostAddress();
                    Player temp = new Player(player_name, ip, peer_port, team_size);
                    String team = game_pool.makeTeam(game_id, temp);
                    if(team != null)
                    {
                        dout.writeInt(CommunicationFlags.TEAM_FORMED_START_PLAYING);
                        dout.writeUTF(team);
                    }
                    else
                    {
                        dout.writeInt(CommunicationFlags.WAIT_FOR_TEAM_FORMATION);
                        dout.writeUTF("{}");
                    }
                }
                else if(request_id == CommunicationFlags.DISCONNECT)
                {
                    //close the connection
                    client.close();
                    break;
                }
            }
        }
        catch(Exception ex)
        {
            System.out.println("Err: " +ex);
            ex.printStackTrace();
        }
        
    }
}
