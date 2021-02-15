package L02_Threadpools.Assignment02_PostalOffice;

import java.util.LinkedList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

public class PostalOffice {
	
	private int nDesks; //Number of desks where users are served.
	private int serviceRoomQueueCapacity; //Max number of users waiting to be served inside service room.
	private LinkedList<User> waitingQueueOffice; //Queue of users waiting to access the service room.
	private ArrayBlockingQueue<Runnable> waitingQueueService; //Queue of users inside service room waiting to be served.
	private ExecutorService desksPool; //Pool of threads to serve users.
	private static final long TIMEOUT_CLOSURE = 10000; //Timeout for deskPool shutdown
	private final Object lock = new Object ();
	
	PostalOffice(int p_nDesks, int p_serviceRoomQueueCapacity) {
		nDesks = p_nDesks;
		serviceRoomQueueCapacity = p_serviceRoomQueueCapacity;
		waitingQueueOffice = new LinkedList<>();
		waitingQueueService = new ArrayBlockingQueue<>(p_serviceRoomQueueCapacity);
		desksPool = new PostalOfficeExecutor(
				nDesks,
				nDesks,
				0L,
				waitingQueueService,
				lock
		);
		
	}
	
	private void addUser(User u) {
		System.out.println(u.toString() + ": entered the waiting room.");
		waitingQueueOffice.add(u);
	}
	
	public void startSimulation(int nUsers) {
		for (int i = 0; i < nUsers; i++)
			addUser(new User(i));
		
	}
	
	public void manageOffice() {
		while (!waitingQueueOffice.isEmpty()) {
			User u = waitingQueueOffice.poll();
			//Try to serve u. If waitingQueueService is full a RejectedExecutionUserHandler is raised and
			//it will be used to try later, when desksPool can accept a new task.
			desksPool.execute(u);
			System.out.println(u + ": is now in the service room.");
			
		}
	}
	
	public void endSimulation() {
		//Gracefully terminate all thread in the pool
		desksPool.shutdown();
		System.out.println("Post office is closing...");
		while (!desksPool.isTerminated()) {
			try {
				desksPool.awaitTermination(TIMEOUT_CLOSURE, TimeUnit.MILLISECONDS);
			} catch (InterruptedException e) {
				System.out.println("An error occurred during thread pool shutdown.");
				e.printStackTrace();
				System.exit(-1);
			}
		}
		System.out.println("Postal office is closed.");
	}
	
	
}
