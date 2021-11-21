package Lab05;

import org.apache.commons.math3.stat.descriptive.moment.Mean;
import org.apache.commons.math3.stat.descriptive.moment.StandardDeviation;

import java.util.*;
import java.util.stream.Collectors;

public class StatCollector {
    private final HashMap<Integer, HashMap<Integer, ArrayList<Double>>> results = new HashMap<>();

    synchronized void add(int threadcount, int taskcount, long time) {
        if (!results.containsKey(threadcount)) {
            results.put(threadcount, new HashMap<>());
        }
        if (!results.get(threadcount).containsKey(taskcount)) {
            results.get(threadcount).put(taskcount, new ArrayList<>());
        }
        results.get(threadcount).get(taskcount).add((double) time);
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (int threadcount : results.keySet().stream().sorted().collect(Collectors.toList())) {
            for (int taskcount : results.get(threadcount).keySet().stream().sorted().collect(Collectors.toList())) {
                double[] times = Arrays.stream(results.get(threadcount).get(taskcount).toArray(new Double[0])).mapToDouble(Double::doubleValue).toArray();
                Mean mean = new Mean();
                StandardDeviation deviation = new StandardDeviation();
                s.append("Threadcount: ")
                        .append(threadcount)
                        .append(", \tJobs: ")
                        .append(String.format("%6d", taskcount))
                        .append(", \tTime: ")
                        .append(mean.evaluate(times))
                        .append(" +- ")
                        .append(deviation.evaluate(times)).append("[ns] \n");
            }
        }
        return s.toString();
    }
}
