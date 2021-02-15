package L02_Threadpools.Assignment02_PostalOffice;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

public class RejectedExecutionUserHandler implements RejectedExecutionHandler {
	final private Object lock;
	
	public RejectedExecutionUserHandler(Object lock) {
		this.lock = lock;
	}
	
	@Override
	public void rejectedExecution(Runnable r, ThreadPoolExecutor executor ) {
		// System.out.printf("Cliente numero %d: posti seconda sala esauriti \n", ((Cliente) r).getNumero());
		synchronized (lock) {
			try {
				//mi metto in attesa sulla lock
				//un thread della pool mi notifichera ' prima che inizi a servire un cliente
				lock.wait();
				// dopo che sono stato notificato posso aggiungere il client
				// alla sala piccola
				executor.execute(r);
			}
			catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
