package ru.job4j.concurrent;

public class ConsoleProgress implements Runnable {

    @Override
    public void run() {
        int index = 0;
        char[] process = new char[]{'|', '/', '-', '\\'};
        try {
            while (!Thread.currentThread().isInterrupted()) {
                System.out.print("\r load: " + process[index++]);
                if (index == process.length) {
                    index = 0;
                }
                Thread.sleep(400);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public static void main(String[] args) {
        Thread progress = new Thread(new ConsoleProgress());
        try {
            progress.start();
            Thread.sleep(4000);
            progress.interrupt();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
