/**
 * Data model for a single customer: one row of the simulation table.
 *
 * All times are in whole minutes. Fields are populated by
 * {@link QueueSimulator#simulate}.
 */
public class Customer {
    public int id;
    public int interArrival;   // minutes since the previous arrival
    public int arrivalTime;    // clock time the customer arrives
    public int serviceTime;    // minutes of service required
    public int serviceBegin;   // clock time service starts
    public int waitInQueue;    // serviceBegin - arrivalTime
    public int serviceEnd;     // clock time service finishes
    public int timeInSystem;   // serviceEnd - arrivalTime
    public int idleTime;       // server idle time just before this customer
}
