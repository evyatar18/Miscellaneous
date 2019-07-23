public interface ThreadPool {

    void queueTask(Runnable task);

    void stop();

    int numThreads();
}
