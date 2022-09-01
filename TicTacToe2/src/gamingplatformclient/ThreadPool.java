package gamingplatformclient;
import java.util.LinkedList;

//A Singleton class
public class ThreadPool implements Runnable
{
    private static ThreadPool onlyObject = null;
    
    private final int POOL_SIZE = 20;
    private Thread pool [];
    private boolean threadLifeController;
    private LinkedList<PoolClient> queue;
    
    //factory method to create one object of ThreadPool and return the same everytime.
    public static ThreadPool getInstance()
    {
        if (onlyObject == null)
        {
            System.out.println("Creating an object of the Thread Pool");
            onlyObject = new ThreadPool();
            try
            {
                Thread.sleep(2000);
            }
            catch(InterruptedException ex)
            {}
            
        }
        return onlyObject;
    }
    
    //not accessible outside the class
    private ThreadPool()
    {
        threadLifeController = true;
        queue = new LinkedList<PoolClient>();
        
        //an array of POOL_SIZE Thread references
        pool = new Thread[POOL_SIZE];
        
        int i;
        for(i =0 ; i < POOL_SIZE; i++)
        {
            //create the thread and associate with the pool (array)
            pool[i] = new Thread(this);
            //activate the thread
            pool[i].start();
        }
        
    }
      
    //Threads life cycle method
    //Auto invoked on thread activation (start)
    public void run()
    {
        while(threadLifeController)
        {
            try
            {
                System.out.println(">>> Relaxing: " + Thread.currentThread()); 
                if(lounge())
                {
                    System.out.println("### Dispatched : " + Thread.currentThread());
                    while(queue.size() > 0)
                    {
                        //pop out a task
                        PoolClient ref = queue.remove();
                        ref.perform();
                    }
                }
            }
            catch(Exception ex)
            {}
        }//while
    }//run
    
    //synchornized activates the binary semaphore
    //Active binary semaphore allows only one thread
    //to execute the method at a time.
    //Rest of the threads try to execute the method
    //concurrently get suspended
    synchronized boolean lounge()
    {
        try
        {
            wait(); //a thread suspends itself until notified
        }
        catch(InterruptedException ex)
        {
            return false;
        }
        return true;
    }
    
    public synchronized void execute(PoolClient ref)
    {
        try
        {
            System.out.println("IN EXECUTE");
            queue.add(ref);
            notify();//bring a thread out of suspension
        }
        catch(Exception ex)
        {}
    }
    
    public void destroyPool()
    {
        threadLifeController = false;
        int i;
        for(i =0 ; i < POOL_SIZE; i++)
            pool[i].interrupt();
        
        pool = null;
    }
    
}
