/*
Single-Threaded Application
-----------------------------
Every application is single-threaded, by default.
The one thread it has is the "main" thread.

A single threaded application uses the one available 
thread to perform all the sub tasks.
Hence the sub tasks are performed sequentially.

Multi-Threaded Application
-------------------------------
Multithreading is the ability of an application to 
perform multiple sub tasks concurrently.

A multithreaded application creates custom threads 
and makes them execute sub tasks.
By this approach concurrency is achieved. 

Java supports developement of multithreaded applications by 
using Thread class object and implementing Runnable interface.

Thread class object and Runnable interface
------------------------------------------------
1) Extends the Thread class.
2) Every object of the sub class is a custom thread.
3) Every custom thread is inactive by default. It must be activated once by calling start() on the object.
4) On activation the custom thread begins concurrent execution of run().
5) Being a sub class it has inherited a run() method from the super class Thread. The inherited run() has empty definition. Override it to assign sub tasks to be executed concurrently.
6) run() defines the life cycle of thread. Once run() is complete thread goes into dead state and cannot be reactivated.


*/

class MyThreadsAgain implements Runnable
{
  //data member 
  Thread t;
  int flag;

  MyThreadsAgain(int x)
  {
    flag = x;
    t = new Thread(this); //a thread
    t.start();//activation	
  }


  public void run()//this to be executed concurrently by thread
  {
    if(flag == 1)
      fx1();
    else if(flag == 2)
      fx2();
  }//life cycle method for the thread 

  void fx1()
  {
    int i;
    for(i =0 ; i < 1000; i++)
    {
       System.out.print("T1 ");  
    }
  }

  void fx2()
  {
    int i;
    for(i =0 ; i < 1000; i++)
    {
       System.out.print("T2 ");  
    }
  }
  

  public static void main(String args[])
  {
    MyThreadsAgain mta1 = new MyThreadsAgain(1);
    MyThreadsAgain mta2 = new MyThreadsAgain(2);

    int i;
    for(i =0 ; i < 1000; i++)
    {
       System.out.print("M ");  
    }

  }

}//MyThreadsAgain

/*
package java.lang;

interface Runnable
{
  public void run();
}

public class Thread implements Runnable
{
  Runnable ref;

  Thread()
  {
     ref = this;   
  }

  Thread(Runnable r)
  {
    ref = r;
  }
  
  ....more methods

  public void start()
  {
    thread activation process
    ref.run();
  }

  public void run()
  {} //empty defn
}


*/