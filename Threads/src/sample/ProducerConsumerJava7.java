package sample;

class MessageQueueJava7 {
    private String message;
    private boolean empty = true;

    public synchronized String read() {
        while (empty) {
            try {
                wait(); // Pause until producer notifies
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        empty = true;
        notifyAll(); // Wake up waiting producer threads
        return message;
    }

    public synchronized void write(String message) {
        while (!empty) {
            try {
                wait(); // Pause until consumer reads
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        empty = false;
        this.message = message;
        notifyAll(); // Wake up waiting consumer threads
    }
}

public class ProducerConsumerJava7 {
    public static void main(String[] args) {
        final MessageQueueJava7 queue = new MessageQueueJava7();

        // Producer Thread
        new Thread(new Runnable() {
            @Override
            public void run() {
                String[] items = {"Msg 1", "Msg 2", "DONE"};
                for (String item : items) {
                    queue.write(item);
                    System.out.println("Produced: " + item);
                }
            }
        }).start();

        // Consumer Thread
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (String val = queue.read(); !val.equals("DONE"); val = queue.read()) {
                    System.out.println("Consumed: " + val);
                }
            }
        }).start();
    }
}