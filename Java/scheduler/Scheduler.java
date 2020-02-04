import java.util.function.Consumer;

public interface Scheduler {

    void <T> scheduleTask(Consumer<T> code, T params, Time t);


}
