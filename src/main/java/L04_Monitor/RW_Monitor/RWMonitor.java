package L04_Monitor.RW_Monitor;

class RWMonitor {
	
	int readers = 0;
	boolean writing = false;
	
	synchronized void StartRead() {
		while (writing) try {//Attendo che non  ci non ci siano scrittori prima di proseguire
			wait();
		} catch (InterruptedException e) {
		}
		readers = readers + 1;
	}
	
	synchronized void EndRead() {
		readers = readers - 1;
		if (readers == 0) notifyAll();
	}
	
	synchronized void StartWrite() {
		while (writing || (readers != 0)) try { //Attendo che non ci siano scritture in corso e che non ci siano lettori in attesa
			wait();
		} catch (InterruptedException e) {
		}
		writing = true;
	}
	
	synchronized void EndWrite() {
		writing = false;
		notifyAll();
	}
	
}
