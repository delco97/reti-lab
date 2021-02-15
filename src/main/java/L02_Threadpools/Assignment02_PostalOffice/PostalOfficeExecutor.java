package L02_Threadpools.Assignment02_PostalOffice;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class PostalOfficeExecutor extends ThreadPoolExecutor {
	/**
	 * oggetto utilizzato per la sincronizzazione
	 */
	private Object lock;
	
	/**
	 *
	 * @param corePoolSize ThreadPoolExecutor#corePoolSize
	 * @param maximumPoolSize ThreadPoolExecutor#maximumPoolSize
	 * @param keepAliveTime ThreadPoolExecutor#keepAliveTime
	 * @param q ThreadPoolExecutor#workQueue
	 * @param lock oggetto utilizzato per la sincronizzazione
	 */
	public PostalOfficeExecutor(
			int corePoolSize,
			int maximumPoolSize,
			long keepAliveTime,
			BlockingQueue<Runnable> q,
			Object lock)
	{
		// @see https://bit.ly/3nxX0e3 (costruttore ThreadPoolExecutor)
		super(corePoolSize , maximumPoolSize , keepAliveTime, TimeUnit.SECONDS , q, new RejectedExecutionUserHandler(lock));
		this.lock = lock;
	}
	
	@Override
	protected void beforeExecute (Thread t , Runnable r) {
		synchronized (lock) {
			lock.notify();
		}
	}
}
