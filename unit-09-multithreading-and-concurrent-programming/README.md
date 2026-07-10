# Unit 9: Multithreading and Concurrent Programming

This unit delves into the theoretical and practical foundations of concurrent programming and multithreading architectures. The curriculum is designed to impart an understanding of thread lifecycles, execution scheduling paradigms, synchronization mechanisms, and high-level concurrency constructs within both the Java and C# ecosystems.

---

## 📖 Core Concepts

### 1. Threads and Execution Tasks

**Threads** constitute independent, concurrent paths of execution within a program's lifecycle. Every executable program instantiated in Java or C# possesses a primary execution thread, which systematically executes the `main` method.
Tasks characterized by substantial temporal or resource constraints are frequently delegated to background threads. This architectural decision ensures the primary thread remains unobstructed, thereby maintaining optimal application responsiveness. Such execution tasks are commonly formalized as **Runnables**.

### 2. Thread Dynamics in the Java Virtual Machine (JVM)

- The JVM allocates a distinct, independent **Stack** to each thread upon initialization. This memory architecture isolates parameters, local variables, and return values, consequently mitigating data conflicts across concurrent execution streams.
- The ontological representations of threads and their respective tasks are defined through the `java.lang.Thread` class and the `java.lang.Runnable` interface.
- In the context of Java, threads are abstractions managed by the JVM but mapped to and executed as native operating system threads. Consequently, while the JVM orchestrates the logical execution flow, the physical scheduling and resource allocation remain within the purview of the host operating system.

### 3. Thread Execution States

A thread transitions through various states over the course of its lifecycle. These discrete states include:

| State | Theoretical Description |
|---|---|
| **NEW** | The thread has been instantiated but not yet initiated by the execution environment. |
| **RUNNABLE** | The thread is actively executing within the JVM, though it may be subject to time-slicing by the OS scheduler. |
| **BLOCKED** | The thread's progression is halted as it awaits the acquisition of a monitor lock to enter or re-enter a synchronized block/method. |
| **WAITING** | The thread is suspended indefinitely, awaiting a specific notification or action from another thread. |
| **TIMED_WAITING** | The thread is suspended for a predetermined maximum temporal duration, awaiting an action from another thread. |
| **TERMINATED** | The thread has concluded its execution lifecycle and released its associated resources. |

### 4. Thread Scheduling Algorithms

In environments where the quantity of active threads surpasses the available processing cores, the execution of threads must be methodically coordinated. Operating systems employ a structural component known as a **Scheduler** to systematically manage these execution queues.

- **Preemptive Scheduling**: A scheduling paradigm wherein the operating system reserves the authority to interrupt (preempt) a currently executing process to allocate CPU resources to an alternative process, typically one possessing a higher priority.
- **Round Robin Scheduling**: A preemptive algorithmic approach where each process is allocated a fixed temporal unit (quantum) for execution. Upon exhaustion of this quantum, the process is preempted and relegated to the terminus of the scheduling queue, thereby facilitating an equitable distribution of processing time.

### 5. Delineating Concurrency and Parallelism

| Paradigm | Academic Definition |
|---|---|
| **Parallelism** | A strict operational condition characterized by the simultaneous execution of two or more threads on distinct physical processors or cores at the exact same chronological moment. |
| **Concurrency** | A broader execution framework where at least two threads make progressive advancement over a given timeframe. Concurrency encompasses time-slicing (interleaving execution on a single core), thereby creating a form of virtual parallelism. |

---

## 🔒 Synchronization and Coordination Paradigms

### 1. Critical Sections

A **Critical Section** refers to a localized segment of code that necessitates access to shared resources or variables.
- Unregulated, simultaneous execution by multiple threads precipitates race conditions and deterministic data corruption.
- Consequently, critical sections mandate **mutual exclusion**, ensuring rigorous serialization such that only a singular thread may traverse the section at any given instance.
- This condition is strictly enforced via sophisticated synchronization mechanisms.

### 2. Synchronization via Monitors

Synchronization systematically prevents the concurrent traversal of a critical section by multiple threads. In object-oriented concurrency, this is predominantly implemented through the architectural construct of **monitors**.

**Monitors** serve as high-level synchronization constructs that enforce mutual exclusion intrinsically.
- In both Java and C#, every instantiated object possesses an implicit monitor that can be engaged using `synchronized` or `lock` primitives.
- Monitors facilitate not only mutual exclusion but also complex inter-thread communication topographies through signaling methods (e.g., `wait()`, `notify()`, `Monitor.Wait()`, `Monitor.Pulse()`).

### 3. Synchronization Semantics: Java vs. C#

| Construct | Implementation in Java | Implementation in C# |
|---|---|---|
| **Methods** | Incorporates the `synchronized` keyword directly within the method signature (applicable to both static and instance methods). | Lacks direct support for synchronized method declarations. |
| **Blocks** | Utilizes `synchronized(object)` statement blocks to encapsulate the critical section. | Utilizes `lock(object)` blocks, or explicitly invokes the `Monitor` class (`Monitor.Enter`, `Monitor.Exit`). |

### 4. The Wait / Notify API

The Wait/Notify API orchestrates cooperative coordination among threads competing for the same monitor lock. It mitigates the inefficiencies of busy-waiting (where a thread continuously polls a condition, thereby squandering CPU cycles) by allowing threads to voluntarily suspend execution.

| Method (Java / C#) | Operational Semantics |
|---|---|
| `wait()` / `Monitor.Wait()` | The executing thread relinquishes its hold on the monitor and enters a dormant state until explicitly signaled. |
| `notify()` / `Monitor.Pulse()` | Arbitrarily awakens a single thread currently suspended and waiting on the specific monitor. |
| `notifyAll()` / `Monitor.PulseAll()` | Awakens all threads suspended on the monitor, obligating them to subsequently compete for the lock. |

*Note: A suspended thread consumes negligible CPU resources. The archetype for this coordination pattern is the Producer-Consumer problem, wherein producer threads notify consumer threads upon resource availability.*

---

## 🛠️ High-Level Concurrency Utilities

Direct manipulation of low-level concurrency primitives (`wait()`, `notify()`, `synchronized`) introduces significant cognitive overhead and architectural complexity. Suboptimal implementations frequently precipitate severe architectural defects, including race conditions, thread starvation, and deadlocks. Furthermore, excessive reliance on `synchronized` blocks severely attenuates systemic scalability.

To mitigate these architectural risks, modern frameworks provision higher-level constructs:

### Executors

The Executor framework decouples task submission from the mechanics of execution, facilitating the execution of discrete tasks (such as `java.lang.Runnable`) within managed thread pools.
- Central to this framework is the declaration of the uniform invocation method: `void execute(Runnable runnable)`.
- The `java.util.concurrent.ExecutorService` interface augments this paradigm by providing capabilities to monitor task progression, aggregate asynchronous results, and execute graceful or immediate termination of the thread pool.

---

## 💻 Java Code Examples

The repository includes a suite of practical Java examples to demonstrate the theoretical concepts discussed. You can find them under the [java](file:///c:/Users/jlcorcuera/repository/pucp-progra3-2026-01/unit-09-multithreading-and-concurrent-programming/java) directory:

### `ejercicio1`: Thread Instantiation and Execution
Demonstrates the fundamental mechanics of instantiating multiple threads and initiating their execution sequences. By extending `Thread` and invoking `.start()`, the example illustrates how the JVM allocates distinct execution paths, allowing the threads to operate independently of the primary `main` thread.

### `ejercicio2`: Thread Lifecycle and State Monitoring
Provides an empirical examination of thread states (`NEW`, `RUNNABLE`, `TERMINATED`). It showcases the programmatic observation of a thread's state transitions, highlighting the utility of the `.join()` method to enforce the primary thread to await the termination of background execution tasks.

### `ejercicio3`: Producer-Consumer Coordination
A classic implementation of the Producer-Consumer synchronization problem. Multiple producer and consumer threads interact with a shared `Identificador` resource, showcasing complex coordination patterns. The example leverages `.join()` to synchronize the termination of all involved threads before deriving the final state of the shared resource.

### `ejercicio4`: Bank Account Simulation (Wait/Notify API)
A robust simulation modeling concurrent deposits and withdrawals on a shared `CuentaBancaria` (Bank Account). This exercise vividly illustrates the `wait()` and `notifyAll()` primitives. Withdrawal threads evaluating a deficit condition will invoke `wait()` to suspend execution and release the monitor. Conversely, deposit threads, upon injecting funds, invoke `notifyAll()` to awaken the dormant withdrawal threads, allowing them to re-evaluate the account balance and complete their transactions safely without race conditions.

---

## 💻 C# (.NET) Code Examples

The repository also includes a C# implementation of the same exercises to demonstrate concurrency in the .NET ecosystem. You can find them under the [net/sesion-de-clase-24](file:///c:/Users/jlcorcuera/repository/pucp-progra3-2026-01/unit-09-multithreading-and-concurrent-programming/net/sesion-de-clase-24) directory:

### `Ejercicio1`: Thread Instantiation and Execution
Demonstrates the mechanics of instantiating multiple threads using `System.Threading.Thread` and initiating their execution. It illustrates how the .NET runtime allocates distinct execution paths, allowing background threads to operate independently.

### `Ejercicio2`: Thread Lifecycle and State Monitoring
Provides an examination of thread states (`ThreadState`). It showcases the programmatic observation of a thread's state transitions, highlighting the utility of the `.Join()` method to enforce the primary thread to await the termination of background execution tasks.

### `Ejercicio3`: Producer-Consumer Coordination
A C# implementation of the Producer-Consumer synchronization problem. Multiple producer and consumer threads interact with a shared `Identificador` resource, showcasing complex coordination patterns using `Monitor.Wait()` and `Monitor.PulseAll()`. The example leverages `.Join()` to synchronize the termination of all involved threads.

### `Ejercicio4`: Bank Account Simulation (Monitor Pattern)
A robust simulation modeling concurrent deposits and withdrawals on a shared `CuentaBancaria` (Bank Account). This exercise illustrates the use of the `Monitor` class primitives. Withdrawal threads evaluating a deficit condition will invoke `Monitor.Wait()` to suspend execution and release the lock. Conversely, deposit threads, upon injecting funds, invoke `Monitor.PulseAll()` to awaken the dormant withdrawal threads, allowing them to safely complete their transactions.
