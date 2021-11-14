package Lab04.fat_and_slim;

import java.util.ArrayList;
import java.util.List;

public class StatCollector {
    private final List<Integer> sizes = new ArrayList<>();
    private final List<Long> times = new ArrayList<>();

    synchronized void add(int size, long time) {
        sizes.add(size);
        times.add(time);
    }

    @Override
    public String toString() {
        return sizes + "\n" + times + "\n";
    }
}
