import java.util.Random;

/**
 * Simulation engine for a single-server bank queue.
 *
 * Inter-arrival times are uniformly distributed on [iaMin, iaMax] minutes and
 * service times are uniformly distributed on [stMin, stMax] minutes (integer
 * minutes, each value equally likely). This class is pure logic with no UI, so
 * it can be reused or unit-tested on its own.
 */
public class QueueSimulator {

    /**
     * Runs the simulation.
     *
     * Convention (standard textbook form): customer 1 arrives at clock time 0,
     * so it has no inter-arrival time. Inter-arrival times are generated for
     * customers 2..N. Service times are generated for every customer.
     */
    public static Customer[] simulate(int n, int iaMin, int iaMax,
                                      int stMin, int stMax, Random rng) {
        Customer[] c = new Customer[n];
        int prevServiceEnd = 0;   // service end of previous customer
        int prevArrival = 0;      // arrival time of previous customer

        for (int i = 0; i < n; i++) {
            Customer cur = new Customer();
            cur.id = i + 1;

            if (i == 0) {
                cur.interArrival = 0;       // first customer arrives at time 0
                cur.arrivalTime = 0;
            } else {
                cur.interArrival = uniformInt(iaMin, iaMax, rng);
                cur.arrivalTime = prevArrival + cur.interArrival;
            }

            cur.serviceTime = uniformInt(stMin, stMax, rng);

            // Service can only begin once the customer has arrived AND the
            // server is free (previous customer's service has ended).
            cur.serviceBegin = Math.max(cur.arrivalTime, prevServiceEnd);
            cur.waitInQueue  = cur.serviceBegin - cur.arrivalTime;
            cur.serviceEnd   = cur.serviceBegin + cur.serviceTime;
            cur.timeInSystem = cur.serviceEnd - cur.arrivalTime;

            // Server idle time between finishing the previous customer and
            // starting this one.
            cur.idleTime = Math.max(0, cur.arrivalTime - prevServiceEnd);

            c[i] = cur;
            prevServiceEnd = cur.serviceEnd;
            prevArrival = cur.arrivalTime;
        }
        return c;
    }

    /** Uniform integer on [lo, hi] inclusive, each value equally likely. */
    public static int uniformInt(int lo, int hi, Random rng) {
        return lo + rng.nextInt(hi - lo + 1);
    }

    /** Reduces the per-customer table to the aggregate queue characteristics. */
    public static Stats computeStats(Customer[] c) {
        Stats s = new Stats();
        s.n = c.length;

        long sumWait = 0, sumService = 0, sumInter = 0;
        long sumSystem = 0, sumIdle = 0;
        int waiters = 0;

        for (Customer cur : c) {
            sumWait    += cur.waitInQueue;
            sumService += cur.serviceTime;
            sumInter   += cur.interArrival;   // customer 1 contributes 0
            sumSystem  += cur.timeInSystem;
            sumIdle    += cur.idleTime;
            if (cur.waitInQueue > 0) waiters++;
        }

        s.totalRunTime    = c[c.length - 1].serviceEnd;   // last service end
        s.numberWhoWaited = waiters;

        s.avgWait        = (double) sumWait / s.n;
        s.probWait       = (double) waiters / s.n;
        s.avgWaitOfThoseWhoWait = waiters == 0 ? 0.0 : (double) sumWait / waiters;
        s.serverIdleProportion  = s.totalRunTime == 0 ? 0.0
                                  : (double) sumIdle / s.totalRunTime;
        s.avgServiceTime  = (double) sumService / s.n;
        // Inter-arrival average is over customers 2..N (customer 1 has none).
        s.avgInterArrival = s.n > 1 ? (double) sumInter / (s.n - 1) : 0.0;
        s.avgTimeInSystem = (double) sumSystem / s.n;
        // Time-average number in queue / system over the whole run.
        s.avgNumberInQueue  = s.totalRunTime == 0 ? 0.0
                              : (double) sumWait / s.totalRunTime;
        s.avgNumberInSystem = s.totalRunTime == 0 ? 0.0
                              : (double) sumSystem / s.totalRunTime;
        return s;
    }
}
