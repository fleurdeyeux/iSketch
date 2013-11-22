
import java.io.PrintStream;

public class ThreadSender extends Thread 
{
	private PrintStream wStream;
	private Message msg;

	ThreadSender(PrintStream ps, Message var)
	{
		this.wStream = ps;
		this.msg = var;
	}

	public void run()
	{
		while (true)
		{
			System.out.println("Thread pret à envoyer");
<<<<<<< HEAD
			/*while (msg.getMsg().isEmpty()) 
			{ 
				try 
				{
					sleep(1000);
				}
				catch (InterruptedException e) { }
=======
			while (msg.getMsg().isEmpty()) 
			{
			    try {sleep (1000);}
			    catch (InterruptedException e){}
>>>>>>> dc93f445c6de3c7b7f7ee979af057b82895ba1de
			}
			synchronized (msg)
			{
				wStream.println(msg.getMsg());
				System.out.println("C->S : " + msg.getMsg());
				msg.setMsg("");
			}*/
			synchronized (msg)
			{
				try 
				{
					msg.wait();
				} 
				catch (InterruptedException e) {
					e.printStackTrace();
				}
				wStream.println(msg.getMsg());
				System.out.println("C->S : " + msg.getMsg());
			}
		}
	}
}