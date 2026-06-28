import javax.swing.SwingUtilities;

/**
 * Entry point for the single-server bank queue simulation.
 *
 * Inter-arrival times are uniformly distributed on [1, 8] minutes and service
 * times are uniformly distributed on [1, 6] minutes (integer minutes). The
 * program generates these values for N customers (default 100), builds the
 * discrete-event queue table and reports the queue characteristics.
 *
 * Modules:
 *   Customer        - data model for one row of the table
 *   Stats           - data model for the aggregate results
 *   QueueSimulator  - simulation engine (no UI)
 *   InputWindow     - input window
 *   OutputWindow    - separate results window
 *
 * Build & run:
 *   javac *.java
 *   java  BankQueueSimulation
 */
public class BankQueueSimulation {
    public static void main(String[] args) {
        // All Swing work happens on the Event Dispatch Thread.
        SwingUtilities.invokeLater(() -> new InputWindow().setVisible(true));
    }
}
