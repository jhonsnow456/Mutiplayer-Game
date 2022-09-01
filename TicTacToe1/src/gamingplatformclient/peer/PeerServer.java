package gamingplatformclient.peer;

import gamingplatformclient.PoolClient;
import gamingplatformclient.ThreadPool;
import java.net.*;
import java.io.*;
import java.util.LinkedList;

public class PeerServer 
{
    ServerSocket peerPort;
    PeerConnectionListener listener;
    LinkedList<String> messages;
    private static PeerServer flag = null;
    
    private PeerServer(int peer_port)
    {
        System.out.println("PeerServer("+ peer_port + ")");
        
        int i;
        while(true)
        {
            try
            {
                //Open a random (any available) port for the peers to connect to.
                peerPort = new ServerSocket(peer_port);
                listener = new PeerConnectionListener();
                messages = new LinkedList<>();
                System.out.println("PeerPort opened : " + peer_port);
                break;
            }
            catch(Exception ex)
            {
                System.out.println("Port not available : " + peer_port);
                peer_port++;
                System.out.println("Trying to open : " + peer_port);
            }
        }
    }
    
    public static PeerServer getInstance(int peer_port)
    {
        System.out.println("PeerServer.getInstance("+ peer_port + ")");
        if(flag == null)
            flag = new PeerServer(peer_port);
        return flag;
    }
    public int getPeerPort()
    {
        return peerPort.getLocalPort();
    }

    public void close()
    {
        try
        {
            listener.close();
            peerPort.close();
        }
        catch(Exception ex)
        {}
    }
    
    class PeerConnectionListener implements PoolClient
    {
        boolean alive;
        public PeerConnectionListener() 
        {
            alive = true;
            ThreadPool.getInstance().execute(this);
        }
        
        
        public void perform()
        {
            //accept peer connection requests
            while(alive)
            {
                try
                {
                    Socket s = peerPort.accept();
                    new PeerConnectionHandler(s);
                }
                catch(Exception ex)
                {}
            }
        }
    
        public void close()
        {
            alive = false;
        }
    }
    
    class PeerConnectionHandler implements PoolClient
    {

        Socket peer;
        public PeerConnectionHandler(Socket s) 
        {
            peer = s;
            ThreadPool.getInstance().execute(this);
        }
        
        public void perform()
        {
            try
            {
                InputStream is = peer.getInputStream();
                DataInputStream dis = new DataInputStream(is);
                boolean flag = true;
                
                while(flag)
                {
                    System.out.println("Waiting for data from peers");
                    String message = dis.readUTF();
                    System.out.println("Peers sent: " + message);
                    Peers.getInstance().getMessageRecipient().response(message);
                    flag = dis.readBoolean();
                }
                dis.close();
                peer.close();
            }
            catch(Exception ex)
            {
                System.out.println("Err in PeerConnectionHandler " + ex);
            }
            System.out.println("=====================");
            for(String s: messages)
                System.out.println(s);
            System.out.println("=====================");
        }
    }
}
