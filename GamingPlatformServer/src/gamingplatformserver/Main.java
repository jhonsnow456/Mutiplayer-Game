package gamingplatformserver;

import java.io.*;

public class Main
{
    static
    {
        //init the Thread pool
        ThreadPool.getInstance();
    }
    
    public static void main(String[] args) 
    {
        try
        {
            new ConnectionManager(8900);
        }
        catch(Exception ex)
        {
            System.out.println("Err: " + ex);
        }
        
    }   
}