package gamingplatformserver;
import java.io.*;
public class GameID 
{
    private static GameID flag = null;
    
    RandomAccessFile raf;
    private GameID() throws IOException
    {
        raf = new RandomAccessFile("d:/temp/GameID.txt", "rwd");
    }

    public void close()
    {
        try
        {
            raf.close();
            GameID.flag = null;
        }
        catch(Exception ex)
        {}
    }
    //factory method to create one object of the class and return the same everytime
    public static GameID getInstance()
    {
        if(flag == null)
        {
            try
            {
                flag = new GameID();
            }
            catch(Exception ex)
            {}
        }
        return flag;
    }
    
    public synchronized long getGameId()
    {
        long gid = 1001;
        try
        {
            raf.seek(0);//file pointer is positioned at BOF
            if (raf.length() == 0)
            {
                //no data in file
                raf.writeLong(gid+1);
            }
            else
            {
                //file has a gameid, fetch it
                gid = raf.readLong();
                raf.seek(0);
                raf.writeLong(gid+1);
            }
        }
        catch(Exception ex)
        {
            return 0;
        }
        return gid;
    }
    
}
