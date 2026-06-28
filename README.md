# Bank Queue Simulation

A Java (Swing) discrete-event simulation of a **single-server bank queue**.

Inter-arrival times are uniformly distributed on **[1, 8] minutes** and service
times are uniformly distributed on **[1, 6] minutes** (whole minutes, each value
equally likely). The program generates these values for **100 customers**
(configurable), builds the classic queue table, and reports the queue
characteristics.

There is an **input window** for the parameters and a **separate output window**
showing the per-customer table and the computed statistics.

---

## Requirements

- A Java Development Kit (JDK), version 8 or newer (`javac` and `java` on your PATH).
- A graphical desktop environment (the program opens Swing windows).

Check whether Java is installed:

```sh
java -version
javac -version
```

If those commands are not found, install a JDK:

- **macOS (Homebrew):** `brew install openjdk`
  Then add it to your PATH for the current terminal session:
  ```sh
  export PATH="/opt/homebrew/opt/openjdk/bin:$PATH"
  ```
- **Windows:** install [Temurin / Adoptium](https://adoptium.net/) and reopen your terminal.
- **Linux (Debian/Ubuntu):** `sudo apt install default-jdk`

---

## Build and Run

From inside the project folder (the directory containing the `.java` files):

```sh
# 1. Compile all modules
javac *.java

# 2. Run the program
java BankQueueSimulation
```

This opens the **input window**.

---

## Using the Program

1. In the input window, accept the defaults or change them:
   - **Number of customers** — default `100`
   - **Inter-arrival min / max (min)** — default `1` / `8`
   - **Service time min / max (min)** — default `1` / `6`
   - **Random seed (optional)** — leave blank for a different random run each
     time, or enter a number to reproduce the same run.
2. Click **Run Simulation**.
3. A separate **results window** opens, showing:
   - The full per-customer table (inter-arrival, arrival time, service time,
     service begins, wait in queue, service ends, time in system, server idle).
   - The **queue characteristics**: average waiting time, probability a customer
     waits, average wait of those who wait, server idle proportion, average
     service time, average time between arrivals, average time in system, and the
     time-average number in queue (Lq) and in system (L).

You can run the simulation repeatedly; each run opens a new results window.

---

## Project Structure

The code is separated into modules:

| File | Responsibility |
|------|----------------|
| `Customer.java` | Data model for one row of the simulation table |
| `Stats.java` | Data model for the aggregate results |
| `QueueSimulator.java` | Simulation engine (pure logic, no UI) |
| `InputWindow.java` | Input window that collects parameters |
| `OutputWindow.java` | Separate results window (table + statistics) |
| `BankQueueSimulation.java` | Entry point — `main()` launches the input window |

---

## Notes

- **Convention:** customer 1 arrives at clock time 0 (no inter-arrival time);
  inter-arrival times are generated for customers 2..N. This matches the standard
  textbook form of the single-channel queue problem.
- The simulation engine (`QueueSimulator`) has no Swing dependency, so it can be
  tested or reused independently of the GUI.
