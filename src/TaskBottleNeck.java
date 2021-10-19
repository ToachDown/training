import java.util.logging.Level;
import java.util.logging.Logger;

import static java.util.logging.Level.INFO;

public class TaskBottleNeck {

    private static Object bottleNeck = new Object();
    private static Logger log = Logger.getLogger("BottleNeck");

    public static void main(String[] args) {
        MultiThread first = new MultiThread();
        MultiThread second = new MultiThread();
        MultiThread third = new MultiThread();
        MultiThread fours = new MultiThread();
        MultiThread fives = new MultiThread();
        first.start();
        second.start();
        third.start();
        fours.start();
        fives.start();
    }

    public static class MultiThread extends Thread {
        @Override
        public void run() {
            try {
                log.log(INFO, "{0} start working", Thread.currentThread().getName());
                synchronized (bottleNeck) {
                    log.log(INFO, "{0} captured resource and sleep", Thread.currentThread().getName());
                    Thread.sleep(2000);
                }
                log.log(INFO, "{0} finish work", Thread.currentThread().getName());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
