public class BoundedThreadPool implements ThreadPool {

    private Thread[] threads;
    private boolean run;
    private BlockingQueue<Runnable> tasks;

    public BoundedThreadPool(int numthreads) {
        this.tasks = new LinkedBlockingQueue<Runnable>();
        this.run = true;
        initThreads(numthreads);
    }

    private class PooledThread extends Thread {

        @Override
        public void run() {
            while (run || tasks.size() > 0) {
                try { tasks.take().run() }
                catch (InterruptedException e) { }
            }
        }
    }

    private void initThreads(int numthreads) {
        this.threads = new Thread[numthreads];

        for (int i = 0; i < numthreads; i++) {
            Thread t = new PooledThread();
            t.start();
            this.threads[i] = t;
        }
    }

    @Override
    public void queueTask(Runnable r) {
        tasks.add(r);
    }

    @Override
    public void stop() {
        for (Thread t : threads) {
            t.interrupt();
        }
    }

    @Override
    public int numThreads() {
        return this.threads.length;
    }
}
