/**
 * Data model for the aggregate queue characteristics computed from the
 * simulation table by {@link QueueSimulator#computeStats}.
 */
public class Stats {
    public int n;                       // number of customers simulated
    public int totalRunTime;            // last service end time (minutes)
    public int numberWhoWaited;         // count of customers with wait > 0

    public double avgWait;              // average wait over all customers
    public double probWait;             // fraction of customers who had to wait
    public double avgWaitOfThoseWhoWait;
    public double serverIdleProportion; // idle time / total run time
    public double avgServiceTime;
    public double avgInterArrival;
    public double avgTimeInSystem;
    public double avgNumberInQueue;     // Lq, time-average
    public double avgNumberInSystem;    // L,  time-average
}
