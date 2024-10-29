# CPU-Scheduling-simulator
program to simulate following CPU scheduling algorithms:

1) First Come First Serve (FCFS)

2) Shortest Job First (SJF)

3) Preemptive Priority Scheduling

4) Round Robin (RR)

The task information will be read from an input file (input.txt) with the following format:

Pid Arrival_Time Burst_Time Priority

All of fields are integer type where

Pid is a unique numeric process ID

Arrival_Time is the time when the task arrives in the unit of milliseconds

Burst_Time is the CPU time requested by a task, in the unit of milliseconds

Priority is the priority associated with each task with low numbers representing high priority.

Note: for RR algorithm you need to define a Time-quantum.

What to do?

Create a simple user interface to ask user for input.txt file, a scheduling algorithm and Time-quantum if RR is chosen.

Print progress of task every unit time (millisecond).
Once all tasks are completed, the program should compute and print :
Average waiting time
Average response time
Average turnaround time
CPU utilization rate

Command to run- java CPUScheduler.java

<img width="422" alt="Screenshot 2024-10-23 at 1 51 02 PM" src="https://github.com/user-attachments/assets/73b5b417-6513-4ffe-991a-97698d114319">
<img width="463" alt="Screenshot 2024-10-23 at 1 50 06 PM" src="https://github.com/user-attachments/assets/331408c3-a861-4a6e-ad2a-a48a35c71070">
<img width="450" alt="Screenshot 2024-10-23 at 1 49 48 PM" src="https://github.com/user-attachments/assets/16df81cf-8a2d-4f20-801b-b73f8fcd77ac">
<img width="452" alt="Screenshot 2024-10-23 at 1 49 18 PM" src="https://github.com/user-attachments/assets/fa01982a-7008-4506-995b-463ae87997ab">

