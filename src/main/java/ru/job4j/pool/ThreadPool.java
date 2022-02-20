package ru.job4j.pool;

import ru.job4j.wait.SimpleBlockingQueue;

import java.util.LinkedList;
import java.util.List;

public class ThreadPool {
    private final List<Thread> threads = new LinkedList<>();
    private final SimpleBlockingQueue<Runnable> tasks = new SimpleBlockingQueue<>(
            Runtime.getRuntime().availableProcessors());

    public ThreadPool() {
        int size = tasks.getLimit();
        for (int i = 0; i < size; i++) {
            threads.add(new Thread(() -> {
                        while (!Thread.currentThread().isInterrupted()) {
                            try {
                                System.out.println(Thread.currentThread().getName() + " работает");
                                tasks.poll().run();
                            } catch (InterruptedException e) {
                                System.out.println(Thread.currentThread().getName() + " прерван");
                                Thread.currentThread().interrupt();
                            }
                        }
                    })
            );
        }
        for (Thread thread : threads) {
            thread.start();
        }
    }

    public void work(Runnable job) {
        try {
            Thread.sleep(2000);
            tasks.offer(job);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void shutdown() {
        for (Thread thread : threads) {
            thread.interrupt();
            try {
                Thread.sleep(2000);
                thread.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

        }
    }

    public static void main(String[] args) {
        ThreadPool pool = new ThreadPool();
        pool.work(() -> System.out.println("job1"));
        pool.work(() -> System.out.println("job2"));
        pool.work(() -> System.out.println("job3"));
        pool.work(() -> System.out.println("job4"));
        pool.work(() -> System.out.println("job5"));
        pool.shutdown();
    }
}
