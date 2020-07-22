// Name: MILONI MITTAL | ID: 2017A3PS0243P | Course: Object Oriented Programming

package oop;
import java.util.Arrays;

//Player class emulates the player
class Player extends Observer implements Runnable
{
	//Characteristics pertaining to each player
	int playerNumber;								
	int totalNumbersStriked;    		
	int[] flagNumberStriked;
	int[] card;
			
	public Player(SharedInfo sharedInfo, int playerNumber) 
	{ 
		this.playerNumber=playerNumber; 		
		this.sharedInfo=sharedInfo;	
		this.totalNumbersStriked=0;
		flagNumberStriked=new int[this.sharedInfo.playerMaxNumbers];
		card=new int[this.sharedInfo.playerMaxNumbers];
		Arrays.fill(this.flagNumberStriked, 0); //Initializing to 0 since none of the numbers have been striked off yet
		
		//Generating the card for each player
		System.out.println("Card of Player "+(playerNumber+1));
		for(int i=0; i<sharedInfo.playerMaxNumbers; i++) 
		{
			int a = sharedInfo.Min+(int)(Math.random()*((sharedInfo.Max-sharedInfo.Min)+1));
			System.out.print("| "+ a +" |");
			card[i]=a;
		}
		System.out.println("");
	}
	void update()
	{
		for(int i=0; i<sharedInfo.playerMaxNumbers; i++)
		{
			if(card[i]==sharedInfo.numberDisplayed.get(sharedInfo.moderatorTurn-1) && flagNumberStriked[i]!=1)
			{
				flagNumberStriked[i]=1;
				totalNumbersStriked++;
				
				System.out.println("Player "+(playerNumber+1)+" striked "+sharedInfo.numberDisplayed.get(sharedInfo.moderatorTurn-1)+" (Match number: "+totalNumbersStriked+")");
				break;
			}
		}
	}
	
	public void run() 
	{
		synchronized(sharedInfo.accessDecision)
		{			
			while(!sharedInfo.flagGameEnd)
			{
				//Waiting till a number is displayed by the moderator OR if it's turn has already occurred 
				while(!sharedInfo.flagNumberDisplayed || sharedInfo.flagPlayerTurn[playerNumber])
				{
					try
					{
						sharedInfo.accessDecision.wait();
					} 
					catch (InterruptedException e) 
					{
						System.out.println("Thread Exception Occurred!");
					}
				}
			
				if(!sharedInfo.flagGameEnd) 
				{
					this.update();		
					//If winner found, declaring all players' turn over
					if(totalNumbersStriked>=sharedInfo.winnerCriteria)
					{
						sharedInfo.flagPlayerWinner[playerNumber]=true;
						for(int i=0; i<sharedInfo.N; i++)
						{
								sharedInfo.flagPlayerTurn[i]=true;
						}
						sharedInfo.notifyObservers();
					}
					
					//If winner not found, declaring current player's turn over	
					else
					{
						sharedInfo.flagPlayerTurn[playerNumber]=true;			
						sharedInfo.notifyObservers();
					}
				}		
			}
		}
	}
}