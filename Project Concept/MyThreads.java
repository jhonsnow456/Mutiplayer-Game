/*
Single-Threaded Application
-----------------------------
Every application is single-threaded, by default.
The one thread it has is the "main" thread.

A single threaded application uses the one 
available thread to perform all the sub tasks.
Hence the sub tasks are performed sequentially.

Multi-Threaded Application
-------------------------------
Multithreading is the ability of an application
to perform multiple sub tasks concurrently.

A multithreaded application creates custom threads 
and makes them execute sub tasks.
By this approach concurrency is achieved. 

Java supports developement of multithreaded applications by extending Thread class.

Extending Thread class
------------------------
1) Extends the Thread class.
2) Every object of the sub class is a custom thread.
3) Every custom thread is inactive by default. It must be activated once by calling start() on the object.
4) On activation the custom thread begins concurrent execution of run().
5) Being a sub class it has inherited a run() method from the super class Thread. The inherited run() has empty definition. Override it to assign sub tasks to be executed concurrently.
6) run() defines the life cycle of thread. Once run() is complete thread goes into dead state and cannot be reactivated.


*/
class MyThreads extends Thread
{
  int flag;
  
  MyThreads(int x)
  {
    flag = x;

   //Thread is inactive by default 
   //It can be activated only once by calling start()
   //on activation, the custom thread begins concurrent execution of run() 
    start();
  }


 //override to define the threads life cycle
 public void run()
 {//code here is executed concurrently by the thread

   if(flag == 1)
     fx1();   
   else if(flag == 2)
     fx2();

 }//run()
 //as run() ends the thread's life ends and it goes into a dead state
 //it cannot be restarted/reactivated.


 void fx1()
 {
   int i;
   for(i =0 ; i < 1000; i++)
   {
     System.out.print(" 1 ");
   }
 } 

 void fx2()
 {
   int i;
   for(i =0 ; i < 1000; i++)
   {
     System.out.print(" 2 ");
   }
 } 

 public static void main(String args[])
 {
   //2 Object of the sub class 
   MyThreads mt1= new MyThreads(1); //is a custom thread. 
   MyThreads mt2= new MyThreads(2); //is a custom thread too.


   
   //main thread to run the following loop
   int i;
   for(i =0 ; i < 1000; i++)
   {
     System.out.print(" M " );
   }    

 }
}

//FYI
//In Java, threads are non-daemon threads, which mean they can keep the application
//alive (executing) even after the main thread gets over.