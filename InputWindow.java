import javax.swing.*;
import java.awt.*;
import java.util.Random;

/**
 * The input window: collects simulation parameters and launches a separate
 * {@link OutputWindow} when the user runs the simulation.
 */
public class InputWindow extends JFrame {
    private final JTextField nField     = new JTextField("100", 8);
    private final JTextField iaMinField = new JTextField("1", 8);
    private final JTextField iaMaxField = new JTextField("8", 8);
    private final JTextField stMinField = new JTextField("1", 8);
    private final JTextField stMaxField = new JTextField("6", 8);
    private final JTextField seedField  = new JTextField("", 8);   // optional

    public InputWindow() {
        super("Bank Queue Simulation - Input");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel form = new JPanel(new GridBagLayout());
        form.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(5, 5, 5, 5);
        g.anchor = GridBagConstraints.WEST;

        int row = 0;
        addRow(form, g, row++, "Number of customers:", nField);
        addRow(form, g, row++, "Inter-arrival min (min):", iaMinField);
        addRow(form, g, row++, "Inter-arrival max (min):", iaMaxField);
        addRow(form, g, row++, "Service time min (min):", stMinField);
        addRow(form, g, row++, "Service time max (min):", stMaxField);
        addRow(form, g, row++, "Random seed (optional):", seedField);

        JButton run = new JButton("Run Simulation");
        run.addActionListener(e -> runSimulation());

        g.gridx = 0; g.gridy = row; g.gridwidth = 2;
        g.anchor = GridBagConstraints.CENTER;
        form.add(run, g);

        add(form);
        pack();
        setLocationRelativeTo(null);
    }

    private void addRow(JPanel p, GridBagConstraints g, int row,
                        String label, JComponent field) {
        g.gridx = 0; g.gridy = row; g.gridwidth = 1;
        g.anchor = GridBagConstraints.WEST;
        p.add(new JLabel(label), g);
        g.gridx = 1; g.gridy = row;
        p.add(field, g);
    }

    private void runSimulation() {
        try {
            int n     = Integer.parseInt(nField.getText().trim());
            int iaMin = Integer.parseInt(iaMinField.getText().trim());
            int iaMax = Integer.parseInt(iaMaxField.getText().trim());
            int stMin = Integer.parseInt(stMinField.getText().trim());
            int stMax = Integer.parseInt(stMaxField.getText().trim());

            if (n < 1) throw new IllegalArgumentException("Number of customers must be >= 1.");
            if (iaMin < 0 || iaMax < iaMin) throw new IllegalArgumentException("Invalid inter-arrival range.");
            if (stMin < 1 || stMax < stMin) throw new IllegalArgumentException("Invalid service-time range.");

            String seedText = seedField.getText().trim();
            Random rng = seedText.isEmpty()
                    ? new Random()
                    : new Random(Long.parseLong(seedText));

            Customer[] customers = QueueSimulator.simulate(n, iaMin, iaMax, stMin, stMax, rng);
            Stats stats = QueueSimulator.computeStats(customers);

            // Separate output window, as the hint requests.
            new OutputWindow(customers, stats).setVisible(true);

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "Please enter valid whole numbers in all fields.",
                    "Input error", JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(),
                    "Input error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
