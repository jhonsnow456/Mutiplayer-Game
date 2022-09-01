//Basic Example on Interface
//See interfaces.txt and interfaces.png

class Merchant
{
 public static void main(String args[])
 {
  if(args.length == 0) 
  {
    System.out.println("Communication Down");
    return;
  }

  Communicate cr;
  if(args[0].equalsIgnoreCase("W"))
    cr = new WhatsApp(); //intrerface reference can refer to implementing class object
  else if(args[0].equalsIgnoreCase("M"))
    cr = new Messenger();
  else if(args[0].equalsIgnoreCase("E"))
    cr = new Email();
  else
  {
    System.out.println("Argument must be W/M/E");
    return;
  }

  System.out.println("Welcome to ABC Merchants");
  System.out.println("Processing the cart");
  System.out.println("OTP");
	
  //interface method calls are runtime bound
  cr.sendMessage("9881604914", "OTP for money transfer is: 998877");

  System.out.println("Validation and Money Transfer");
 }
}

interface Communicate
{
  //interface is a collection of abstract methods, ...
  void sendMessage(String id, String message); 
}


class WhatsApp implements Communicate
{
  //n other methods
  //...

  //Implementing class must override the interface methods
  public void sendMessage(String id, String message)
  {
    System.out.println("\tUsing WhatsApp");
    System.out.println("\tConnect to "+ id);
    System.out.println("\tSend" + message);
    System.out.println("\tAcknowledge the trasfer: By double tick");
    System.out.println("\t30 minutes to remove the message from the clients account");
    System.out.println("\tDisconnect");
  }
}

class Messenger implements Communicate
{
  //n other methods
  //...

  //must override the interface methods
  public void sendMessage(String id, String message)
  {
    System.out.println("* Using Messenger");
    System.out.println("* Connect to "+ id);
    System.out.println("* Send" + message);
    System.out.println("* Disconnect");
  }
}

class Email implements Communicate
{
  //n other methods
  //...

  //must override the interface methods
  public void sendMessage(String id, String message)
  {
    System.out.println("->Using Email");
    System.out.println("->Connect to "+ id);
    System.out.println("->Send" + message);
    System.out.println("->Acknowledge the trasfer");
    System.out.println("->1 minute to remove the message from the clients account");
    System.out.println("->Disconnect");
  }
}



class A
{}


class B
{

  def fx(A ref)
  {
     ref.degreeofAccess(high)
  }

}
