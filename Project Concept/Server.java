class Resource
{ 
 private static Resource res = null;

 public static Resource get_instance()
 {
   if(res == null)
     res = new Resource();
   return res;   
 }

 private Resource()
 {}
  
 synchronized void task()
 {//operation that only one thread can perform at a time
   try
   {
     wait(); //suspend the thread

    //threads operation
   }
   catch(InterruptedException ex)
   {
     //to neutralize the Thread interrupt 
   }
 }
}

class Pool implements Runnable
{
  Thread arr[];
  boolean thread_controller = true;
  Pool()
  {
     this(100);//delegate initialization to Pool(int)
  }

  Pool(int n)
  {
    //array of Thread references
    arr = new Thread[n];
     
    //create the Threads
    int i;
    for(i=0 ; i < n; i++)
    {
       arr[i] = new Thread(this); //objects
       //activate the thread
       arr[i].start();
    }
  }

  //runnable method
  //Threads life cycle method
  //Code in run() executes concurrently 
  public void run() 
  {
    Resource shared_resource = Resource.get_instance();
    while(thread_controller)
    {
      try
      {
       shared_resource.task();
      }
      catch(Exception ex)
      {
        //to avoid Thread death due to any exception
      }
    } 	
  }//run

  void dispose_pool()
  {
    thread_controller = false;
    int i;
    for(i =0 ; i< arr.length; i++)
      arr[i].interrupt(); //suspended thread comes out of wait by an exception

    arr = null; //drop the Thread array reference
  }

}//Pool