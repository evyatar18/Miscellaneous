public class Future<T> {

    private volatile T value;

    public Future() {
        this.value = null;
    }

    public void set(T v) {
        synchronized (this) {
            this.value = v;
            this.notifyAll();
        }
    }

    public T get() {
        if (this.value != null)
            return this.value;

        synchronized (this) {
            if (this.value == null) {
                try { this.wait(); }
                catch (InterruptedException e) { }
            }

            return this.value;
        }
    }
}
