//Producer Consumer Problem
//Study of thread synchronization

import java.util.Random;

class NumberFactory
{
  int vessel; 
  Random rnd = new Random();
  boolean produced = false;

  synchronized void produce()
  {
    if(produced)
    {
      try
      {
        wait(); //suspend until notified
      }
      catch(InterruptedException ex)
      {}
    }

    System.out.println("=====Production Starts=====");
    vessel = rnd.nextInt(100); //any random int range 0-99
    System.out.println(vessel + " generated");
    System.out.println("=====Production Ends=====");
    produced = true;
    //may be that consumer thread is in wait, so lets signal notify
    notify();
    
  }

  synchronized void consume()
  {
    if(!produced)
    {
      try
      {
        wait(); //suspend until notified
      }
      catch(InterruptedException ex)
      {}
    }


    System.out.println("-----Consumtion Begins-----");
    System.out.println("Consumed " + vessel);
    System.out.println("-----Consumption ends-----");
    produced = false;
    //may be that producer thread is in wait, so lets signal notify
    notify();
  }
}


class Producer extends Thread
{
  NumberFactory ref;
  Producer( NumberFactory f)
  {
    ref = f;
    start(); //activate the thread
  }

  public void run() //executes concurrently
  {
    int i;
    for(i =0 ; i < 1000; i++)
      ref.produce();
  }

}//Producer

class Consumer extends Thread
{
  NumberFactory ref;
  Consumer(NumberFactory f)
  {
    ref = f;
    start(); //activate the thread
  }

  public void run() //executes concurrently
  {
    long x = System.currentTimeMillis();
    int i;
    for(i =0 ; i < 1000; i++)
      ref.consume();

    System.out.println("1000 p and c cycles in " + (System.currentTimeMillis() - x)/1000 + "seconds.") ;
  }

}//Consumer

class Sync
{
 public static void main(String args[])
 {
   NumberFactory factory = new NumberFactory();//shared resource
   Producer p = new Producer(factory);//a thread
   Consumer c = new Consumer(factory);//a thread
 }
}