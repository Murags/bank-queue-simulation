import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 * The output window (a separate frame, as the question's hint requests): shows
 * the full per-customer simulation table on top and the computed queue
 * characteristics below.
 */
public class OutputWindow extends JFrame {

    public OutputWindow(Customer[] customers, Stats s) {
        super("Bank Queue Simulation - Results");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // ---- Simulation table ----
        String[] cols = {
            "Customer", "Inter-arrival", "Arrival time", "Service time",
            "Service begins", "Wait in queue", "Service ends",
            "Time in system", "Server idle"
        };
        DefaultTableModel model = new DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        for (Customer c : customers) {
            model.addRow(new Object[]{
                    c.id, c.interArrival, c.arrivalTime, c.serviceTime,
                    c.serviceBegin, c.waitInQueue, c.serviceEnd,
                    c.timeInSystem, c.idleTime
            });
        }
        JTable table = new JTable(model);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        JScrollPane tableScroll = new JScrollPane(table);
        tableScroll.setPreferredSize(new Dimension(900, 400));

        // ---- Statistics panel ----
        JTextArea stats = new JTextArea(buildStatsText(s));
        stats.setEditable(false);
        stats.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 13));
        stats.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JScrollPane statsScroll = new JScrollPane(stats);
        statsScroll.setPreferredSize(new Dimension(900, 250));

        JSplitPane split = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
                tableScroll, statsScroll);
        split.setResizeWeight(0.6);
        add(split, BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
    }

    private String buildStatsText(Stats s) {
        StringBuilder b = new StringBuilder();
        b.append("================  QUEUE CHARACTERISTICS  ================\n\n");
        b.append(String.format("Number of customers simulated .............. %d%n", s.n));
        b.append(String.format("Total simulation run time (min) ............ %d%n", s.totalRunTime));
        b.append(String.format("Number of customers who had to wait ........ %d%n%n", s.numberWhoWaited));

        b.append(String.format("Average waiting time in queue (min) ........ %.4f%n", s.avgWait));
        b.append(String.format("Probability a customer has to wait ......... %.4f%n", s.probWait));
        b.append(String.format("Average wait of those who waited (min) ..... %.4f%n", s.avgWaitOfThoseWhoWait));
        b.append(String.format("Proportion of server idle time ............. %.4f%n", s.serverIdleProportion));
        b.append(String.format("Average service time (min) ................. %.4f%n", s.avgServiceTime));
        b.append(String.format("Average time between arrivals (min) ........ %.4f%n", s.avgInterArrival));
        b.append(String.format("Average time a customer spends in system ... %.4f%n", s.avgTimeInSystem));
        b.append(String.format("Average number of customers in queue (Lq) .. %.4f%n", s.avgNumberInQueue));
        b.append(String.format("Average number of customers in system (L) .. %.4f%n", s.avgNumberInSystem));
        return b.toString();
    }
}
