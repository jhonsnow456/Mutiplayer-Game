package gamingplatformserver;

public final class CommunicationFlags
{
    public final static int CONNECTED= 1;
    public final static int GENERATE_GAME_ID = 2;
    public final static int PLAY_REQUEST=3;
    public final static int TEAM_STATUS_REQUEST=4;
    
    public final static int PLAY_REQUEST_RECORDED=5;
    public final static int WAIT_FOR_TEAM_FORMATION=6;
    public final static int TEAM_FORMED_START_PLAYING=7;
    
    public final static int STATUS_OK = 8;
    public final static int STATUS_ERROR = 9;
    public final static int STATUS_UNSET = 10;
    public final static int STATUS_CONTINUE_LOOP = 11;
    public final static int STATUS_STOP_LOOP = 12;
    public final static int DISCONNECT= 13;
}
        
