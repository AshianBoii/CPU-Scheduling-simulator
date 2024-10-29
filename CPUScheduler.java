import java.io.*;
import java.util.*;

class Process {
    int pid;  // Process ID
    int arrivalTime;  // Time the process arrives
    int burstTime;  // Time required by the process to complete execution
    int priority;  // Priority level of the process (lower value = higher priority)
    int remainingTime;  // Time left to complete (used in Round Robin)
    int waitingTime = 0;  // Total time process spends waiting to execute
    int turnaroundTime = 0;  // Time between arrival and completion
    int responseTime = -1;  // Time from arrival until the process starts for the first time

    // Constructor to initialize a process
    public Process(int pid, int arrivalTime, int burstTime, int priority) {
        this.pid = pid;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.remainingTime = burstTime;  // Initially, the remaining time is the full burst time
        this.priority = priority;
    }
}

public class CPUScheduler {
    private List<Process> processes = new ArrayList<>();  // List to store processes
    private int timeQuantum = 0;  // Time quantum for Round Robin

    // Method to read input from a file and populate the list of processes
    public void readInputFile(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split("\\s+");  // Split each line by spaces
                int pid = Integer.parseInt(data[0]);
                int arrivalTime = Integer.parseInt(data[1]);
                int burstTime = Integer.parseInt(data[2]);
                int priority = Integer.parseInt(data[3]);
                processes.add(new Process(pid, arrivalTime, burstTime, priority));
            }
        } catch (IOException e) {
            System.out.println("Error reading the input file.");
            e.printStackTrace();
        }
    }

    public void fcfs() {
        processes.sort(Comparator.comparingInt(p -> p.arrivalTime));  // Sort processes by arrival time
        simulate("First Come First Serve (FCFS)");  // Call simulate method for FCFS
    }


    public void sjf() {
        processes.sort(Comparator.comparingInt(p -> p.arrivalTime));  // Sort by arrival time
        PriorityQueue<Process> pq = new PriorityQueue<>(Comparator.comparingInt(p -> p.burstTime));  // Use a priority queue for burst time
        simulateWithQueue("Shortest Job First (SJF)", pq);
    }


    public void priorityScheduling() {
        processes.sort(Comparator.comparingInt(p -> p.arrivalTime));  // Sort by arrival time
        PriorityQueue<Process> pq = new PriorityQueue<>(Comparator.comparingInt(p -> p.priority));  // Use priority queue based on process priority
        simulateWithQueue("Preemptive Priority Scheduling", pq);
    }


    public void roundRobin() {
        processes.sort(Comparator.comparingInt(p -> p.arrivalTime));  // Sort by arrival time
        simulateRoundRobin();  // Call method for RR simulation
    }


    private void simulate(String algorithm) {
        int currentTime = 0;
        for (Process process : processes) {
            if (currentTime < process.arrivalTime) {
                currentTime = process.arrivalTime;  // Ensure current time doesn't go below the arrival time
            }
            if (process.responseTime == -1) {
                process.responseTime = currentTime - process.arrivalTime;  // Set response time on first execution
            }
            process.waitingTime = currentTime - process.arrivalTime;
            currentTime += process.burstTime;
            process.turnaroundTime = currentTime - process.arrivalTime;
            System.out.println("Time " + currentTime + ": Process " + process.pid + " completed in " + algorithm);
        }
        printStatistics();  // Print statistics after all processes complete
    }

    private void simulateWithQueue(String algorithm, PriorityQueue<Process> pq) {
        int currentTime = 0;
        int index = 0;

        while (!pq.isEmpty() || index < processes.size()) {
            while (index < processes.size() && processes.get(index).arrivalTime <= currentTime) {
                pq.add(processes.get(index));  // Add processes that have arrived to the queue
                index++;
            }

            if (pq.isEmpty()) {
                currentTime = processes.get(index).arrivalTime;  // Skip time to next process if none are in queue
                continue;
            }

            Process process = pq.poll();  // Get the next process to run
            if (process.responseTime == -1) {
                process.responseTime = currentTime - process.arrivalTime;  // Set response time
            }
            currentTime += process.remainingTime;
            process.turnaroundTime = currentTime - process.arrivalTime;
            System.out.println("Time " + currentTime + ": Process " + process.pid + " completed in " + algorithm);
        }
        printStatistics();  // Print stats after completion
    }

    private void simulateRoundRobin() {
        Queue<Process> queue = new LinkedList<>();
        int currentTime = 0;
        int index = 0;

        while (!queue.isEmpty() || index < processes.size()) {
            while (index < processes.size() && processes.get(index).arrivalTime <= currentTime) {
                queue.add(processes.get(index));
                index++;
            }

            if (queue.isEmpty()) {
                currentTime = processes.get(index).arrivalTime;
                continue;
            }

            Process process = queue.poll();
            if (process.responseTime == -1) {
                process.responseTime = currentTime - process.arrivalTime;
            }

            int timeSlice = Math.min(timeQuantum, process.remainingTime);
            process.remainingTime -= timeSlice;
            currentTime += timeSlice;

            if (process.remainingTime > 0) {
                queue.add(process);
            } else {
                process.turnaroundTime = currentTime - process.arrivalTime;
                System.out.println("Time " + currentTime + ": Process " + process.pid + " completed in Round Robin.");
            }
        }
        printStatistics();
    }

    private void printStatistics() {
        double totalWaitingTime = 0;
        double totalResponseTime = 0;
        double totalTurnaroundTime = 0;
        for (Process process : processes) {
            totalWaitingTime += process.waitingTime;
            totalResponseTime += process.responseTime;
            totalTurnaroundTime += process.turnaroundTime;
        }

        System.out.println("Average Waiting Time: " + totalWaitingTime / processes.size());
        System.out.println("Average Response Time: " + totalResponseTime / processes.size());
        System.out.println("Average Turnaround Time: " + totalTurnaroundTime / processes.size());
    }


    public void start() {
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter the input file name (e.g., input.txt):");
        String filename = sc.nextLine();
        readInputFile(filename);

        System.out.println("Choose a scheduling algorithm:");
        System.out.println("1) First Come First Serve (FCFS)");
        System.out.println("2) Shortest Job First (SJF)");
        System.out.println("3) Preemptive Priority Scheduling");
        System.out.println("4) Round Robin (RR)");

        int choice = sc.nextInt();

        if (choice == 4) {
            System.out.println("Enter the time quantum for Round Robin:");
            timeQuantum = sc.nextInt();
        }

        switch (choice) {
            case 1:
                fcfs();
                break;
            case 2:
                sjf();
                break;
            case 3:
                priorityScheduling();
                break;
            case 4:
                roundRobin();
                break;
            default:
                System.out.println("Invalid choice.");
        }
    }

    public static void main(String[] args) {
        CPUScheduler scheduler = new CPUScheduler();
        scheduler.start();
    }
}
