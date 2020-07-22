// Name: MILONI MITTAL | ID: 2017A3PS0243P | Course: Object Oriented Programming

package oop;
import java.util.ArrayList;

//SharedInfo is used to store all the information related to the game which is shared by the Moderator and Players
class SharedInfo implements Subject
{
	//Initializing various parameters related to the game
	int N;
	int moderatorMaxNumbers=10;
	int playerMaxNumbers=10;	
	int winnerCriteria=3;
	int Min=0,Max=50;
	ArrayList<Integer> numberDisplayed=new ArrayList<Integer>();
	int moderatorTurn=0;
	boolean flagGameEnd=false;	
	boolean flagNumberDisplayed=false;
	boolean[] flagPlayerWinner;
	boolean[] flagPlayerTurn;
	Object accessDecision=new Object();
	ArrayList<Observer> observerList; 
	
	SharedInfo(int N)
	{	
		this.N=N;
		this.flagPlayerWinner=new boolean[N];
	    this.flagPlayerTurn=new boolean[N];
	    observerList = new ArrayList<Observer>();
	}
	public void registerObserver(Observer o) 
	{ 
        observerList.add(o); 
    } 
	public void unregisterObserver(Observer o) 
	{ 
        observerList.remove(observerList.indexOf(o)); 
    } 
	public void notifyObservers() 
	{ 
		//Notifying all observers
		this.accessDecision.notifyAll();
	}
}