package gamingplatformclient.server;

import java.io.*;
import java.net.*;
import java.util.Properties;
import org.json.JSONObject;

public class GameIDGenerator 
{
    public static long getGameId() 
    {
        try
        {
            Properties p = new Properties();
            p.load(GameIDGenerator.class.getResourceAsStream("server.properties"));
            String server_ip = p.getProperty("SERVER_IP");
            int server_port = Integer.parseInt(p.getProperty("SERVER_PORT"));

            Socket svr_conn = new Socket(server_ip, server_port);
            DataInputStream din = new DataInputStream(svr_conn.getInputStream());
            DataOutputStream dout = new DataOutputStream(svr_conn.getOutputStream());
            dout.writeInt(CommunicationFlags.GENERATE_GAME_ID);
            JSONObject jobj = new JSONObject();
            StringWriter sw = new StringWriter();
            jobj.write(sw);
            dout.writeUTF(sw.toString());
            int res_status = din.readInt();
            String res_data = din.readUTF();
            long game_id = -1;
            if(res_status == CommunicationFlags.STATUS_OK)
            {
                jobj = new JSONObject(res_data);
                game_id = jobj.getLong("GAME_ID");

            }
            svr_conn.close();
            return game_id;
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            return -11;
            
        }
    }
    
}
