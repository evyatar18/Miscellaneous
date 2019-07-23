public class Executor {

    private final ThreadPool tp;

    public Executor(ThreadPool tp) {
        this.tp = tp;
    }

    public Future<T> submit(Callable<T> callable) {
        Future<T> f = new Future<>();

        this.tp.queueTask(() -> f.set(callable.call()));

        return f;
    }

    public Future<Void> execute(Runnable task) {
        return submit(() -> task.run());
    }
}
