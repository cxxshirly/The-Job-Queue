import java.util.*;
import java.io.*;
import java.awt.*;
//a program that simulate the operation of this system.
public class TimeShare {

	public static void main(String[] args)throws CloneNotSupportedException{
		//creat a Queue that takes in the data
		Queue queueIn = new Queue();
		//read input file
		readInputFile(args,queueIn);
		if(!queueIn.isEmpty()){ 
			//print the title
			printTitle();
			//calculation of the operation
			jobs(queueIn);
		}
	}

	// Read input file and check if any arguments pass into it
	// Read jobs and place into the job queue
	public static void readInputFile(String[] inputFile, Queue queueIn){
		String jobName = "";
		int arrivalTime = 0;
		int runTime = 0;	

		if(inputFile.length < 1){
			System.out.println("Missing Input or invaild files. Please try again");	
		}
		else{
			try{
				//***Extra feature: the program can take more than one input file***
				for(int i = 0; i < inputFile.length; i++){
					String inputFileName = inputFile[i];
					Scanner readIn = new Scanner(new File(inputFileName)); 	
					while(readIn.hasNext()){
						Scanner lsc = new Scanner(readIn.nextLine()).useDelimiter("\t");
					        jobName     = lsc.next();
						arrivalTime = lsc.nextInt();
						runTime     = lsc.nextInt();
						//	debug line below
						//	System.out.println(jobName + " " + arrivalTime + " " + runTime);
						queueIn.enqueue(new Job(jobName, arrivalTime, runTime));
					}					 	
				}
			//print message if catch error
			}catch(IOException e){
				System.out.println(e.getMessage());
			}
		}
	}
	
        // Print out summary report title  	
	public static void printTitle(){
		System.out.println();
		System.out.println("------------------------------------------------");
		System.out.println();
		System.out.println("     Job Control Analysis: Summary Report\n");
		System.out.println("job id  arrival start   run     wait    turnaround\n" +
				   "        time    time    time    time    time      \n" +
				   "------  ------  -----   ----    ----    ----------\n" );
	}
	
	// Job control process
	public static void jobs(Queue queueIn){

		int clockTime = -1;
		int waitTime  = 0;
		int turnTime = 0;
		int idleTime = 0;
		int count = 0;
		int jobCount = 0;
		int cpuUsageTime = 0;

		while(!queueIn.isEmpty()){
			Job job = ((Job)queueIn.front());
			//processing the first job that comes in
			if(job.startTime == -1 && clockTime == -1){
				job.startTime = 1;
				clockTime = 1;
				waitTime = 0;
				turnTime = job.runTime + waitTime;
				job.turnTime = turnTime;
				//increment the clocktime by runtime
				while( count < job.runTime){
				        // uncommnet below for debug 
					//System.out.println("Clock " + clockTime + " CPU " + job.jobName);
					clockTime += 1;
					cpuUsageTime +=1;
					count ++;
				}
				count = 0;
				System.out.println(queueIn.dequeue());
				jobCount += 1;
			}	
			else{
				// idle happen  when arrival time is less than clockTime
				if(clockTime < job.arrivalTime){
					while(clockTime < job.arrivalTime){
						clockTime += 1;
						idleTime += 1;
					}
				}
				// if idle not happen
				else{
					job.startTime = clockTime;
					job.waitTime = job.startTime - job.arrivalTime;
					waitTime += job.waitTime;
					turnTime = job.runTime + job.waitTime;
					job.turnTime = turnTime;
					//increment the clocktime by runtime
					while(count < job.runTime){
				        // uncommnet below for debug 
					//	System.out.println("Clock " + clockTime + " CPU " + job.jobName);
						clockTime += 1;
						cpuUsageTime +=1;
						count++;
					}	
					//reset count
					count = 0;
					System.out.println(queueIn.dequeue());
					jobCount += 1;
				}
			}
	    }

	    // Job Control Analysis (use double and convert to 2 decimal places)
	    System.out.printf("\n     Average Wait Time => %.2f\n", (double)waitTime/jobCount);
	    System.out.printf("             CPU Usage => %.2f\n",  (double)cpuUsageTime);
	    System.out.printf("              CPU Idle => %.2f\n",  (double)idleTime);
	    System.out.printf("         CPU Usage (%s) => %.2f%s\n", "%", (double)((double)cpuUsageTime/(cpuUsageTime + idleTime) * 100),"%");
	    System.out.println();
	    System.out.println("------------------------------------------------");
	    System.out.println();
	}


}
