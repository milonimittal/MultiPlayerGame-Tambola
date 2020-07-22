// Name: MILONI MITTAL | ID: 2017A3PS0243P | Course: Object Oriented Programming

package oop;

//Moderator class takes care of displaying the numbers
class Moderator extends Observer implements Runnable 
{
	//Using Singleton Design Pattern to make sure that there is only one Moderator
	private static Moderator obj=new Moderator();
	
	private Moderator() 
	{}
	
	public static Moderator getObject(SharedInfo sharedInfo)
	{
		obj.sharedInfo=sharedInfo;
		return obj;
	}
	
	void update()
	{
		sharedInfo.numberDisplayed.add(sharedInfo.Min+(int)(Math.random()*((sharedInfo.Max-sharedInfo.Min)+1)));
		System.out.println("Moderator drew: "+sharedInfo.numberDisplayed.get(sharedInfo.moderatorTurn));
		sharedInfo.moderatorTurn++;
	}
	
	public void run() 
	{
		synchronized(sharedInfo.accessDecision)
		{
			 while(true)
			 {
				//Checking if a winner has been found and if found stop running the game
				boolean breakOutOfLoop1=false;
				for(int i=0; i<sharedInfo.N; i++)
					breakOutOfLoop1=breakOutOfLoop1 || sharedInfo.flagPlayerWinner[i];
				if(breakOutOfLoop1)
					break;
			
				//Announcing that the number has not been displayed yet
				sharedInfo.flagNumberDisplayed=false;
				
				//Setting turn flag of all players to false because their turn hasn't been completed before displaying
				//the number
				for(int i=0; i<sharedInfo.N; i++)
					sharedInfo.flagPlayerTurn[i]=false;

				//Announcing the number
				this.update();
				
				sharedInfo.flagNumberDisplayed=true; 
				sharedInfo.notifyObservers();
				
				//Waiting till all players have searched for the displayed number in their cards
				while(true)
				{
					boolean breakOutOfLoop2=true;
					for(int i=0; i<sharedInfo.N; i++)
						breakOutOfLoop2=breakOutOfLoop2 && sharedInfo.flagPlayerTurn[i];
					if(breakOutOfLoop2)
						break;
					try
					{
						sharedInfo.accessDecision.wait();
					}
					catch(InterruptedException e)
					{
						System.out.println("Thread Exception Occurred!");
					}
				}
				
				//Introducing a time gap of 3 seconds between each display of numbers by Moderator
				try 
				{
					Thread.sleep(3000);
		        } 
				catch (InterruptedException e) 
				{
					System.out.println("Thread Exception Occurred!");
		        }
			
				//Limiting number of numbers announced by Moderator to moderatorMaxNumbers
				if(sharedInfo.moderatorTurn>=sharedInfo.moderatorMaxNumbers)
				{
					for(int i=0; i<sharedInfo.N; i++)
						sharedInfo.flagPlayerTurn[i]=true;
					break;
				}	
			}
			
			//Declaring the winner
			int flagFoundWinner=0;
			for(int i=0; i<sharedInfo.N; i++)
			{
				if(sharedInfo.flagPlayerWinner[i])
				{
					System.out.println("Player "+(i+1)+" won.");
					flagFoundWinner=1;
					System.exit(0);
				}
			}
			if(flagFoundWinner==0)
			{
				System.out.println("No winner.");
				System.exit(0);
			}	
		}		
	}
}