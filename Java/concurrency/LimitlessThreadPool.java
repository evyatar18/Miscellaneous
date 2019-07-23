public class LimitlessThreadPool implements ThreadPool {

    private volatile Integer alive;
    private volatile Integer available;
    private List<Thread> threads;
    private BlockingQueue<Runnable> q;

    public LimitlessThreadPool() {
        this.alive = 0;
        this.available = 0;
        this.threads = new LinkedList<>();
        this.q = new LinkedBlockingQueue<>();
    }

    private void modifyInt(Integer i, int modif) {
        synchronized (i) {
            i += modif;
        }
    }

    private class PooledThread extends Thread {

        private Runnable task;

        public PooledThread(Runnable task) {
            this.task = task;
        }

        @Override
        public void run() {
            modifyInt(this.alive, 1);

            boolean run = true;

            do {
                this.task.run();
                modifyInt(this.available, 1);
                try { this.task = q.take(); }
                catch (InterruptedException e) { run = false; }
                modifyInt(this.available, -1);
            } while (run);

            modifyInt(this.alive, -1);
        }
    }

    @Override
    public void queueTask(Runnable r) {
        synchronized (this.available) {
            if (this.available > q.size()) {
                q.add(r);
            }
            else {
                Thread t = new PooledThread(r);
                this.threads.add(t);
            }
        }
    }

    @Override
    public void stop() {
        for (Thread t : this.threads) {
            t.interrupt();
        }
    }

    @Override
    public int numThreads() {
        return this.threads.size();
    }
}
