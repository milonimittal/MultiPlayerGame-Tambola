// Name: MILONI MITTAL | ID: 2017A3PS0243P | Course: Object Oriented Programming

package oop;

import java.io.*;
//import java.util.ArrayList;
//import java.util.Arrays;

class MultiPlayerGame 
{
	public static void main(String[] args) throws IOException 
	{
		int N=2; //Uncomment line 15-32 if it is required that user enters number of players
		
//		BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
//		boolean rightInputType = false;
//		 while (!rightInputType) 
//		 {
//			 try 
//			 {
//				 System.out.print("Enter number of players: ");
//				 N=Integer.parseInt(br.readLine());
//				 if(N>=2)
//					 rightInputType=true;
//				 else
//					 System.out.println("Number of players should be greater than or equal to 2!");
//	         } 
//			 catch (NumberFormatException e) 
//			 {
//				 System.out.println("Please enter an integer!");
//	         }
//		 }
		
		//Initializing all essential instances and respective threads
		SharedInfo sharedInfo=new SharedInfo(N);
		Moderator moderator=Moderator.getObject(sharedInfo);
		Thread playerThread[]=new Thread[N];
		Thread moderatorThread=new Thread(moderator);
		sharedInfo.registerObserver(moderator);
		Player tempObj;
		for(int i=0; i<N; i++)
		{
			tempObj=new Player(sharedInfo,i);
			sharedInfo.registerObserver(tempObj);
			playerThread[i]=new Thread(tempObj);
		}

		//Starting the thread execution
		moderatorThread.start();
		for(int i=0; i<N; i++)
		{
			playerThread[i].start();
		}	
	}
}

//Using Observer Design Pattern
interface Subject 
{ 
    public void registerObserver(Observer o); 
    public void unregisterObserver(Observer o); 
    public void notifyObservers(); 
} 

//Using Observer Design Pattern
abstract class Observer 
{
    SharedInfo sharedInfo;
    abstract void update();
}


