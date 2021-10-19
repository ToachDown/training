import java.util.logging.Level;
import java.util.logging.Logger;

import static java.util.logging.Level.INFO;
import static java.util.logging.Level.WARNING;

public class TaskDeadLock {

    private static Logger log = Logger.getLogger("Thread");
    private static Object first = new Object();
    private static Object second = new Object();

    public static void main(String[] args) {
        SecondLockThread threadSecond = new SecondLockThread();
        FirstLockThread threadFirst = new FirstLockThread();
        threadFirst.start();
        threadSecond.start();
    }

    public static class FirstLockThread extends Thread {
        @Override
        public void run() {
            synchronized (second) {
                log.log(INFO, "{0} hold object second", Thread.currentThread().getName());
                try {
                    Thread.sleep(1000);
                }  catch (InterruptedException e) {
                    e.printStackTrace();
                }
                log.log(INFO, "{0} waiting 1000 ms", Thread.currentThread().getName());
                synchronized (first) {
                    log.log(WARNING, "dead lock");
                }
            }
        }
    }

    public static class SecondLockThread extends Thread {
        @Override
        public void run() {
            synchronized (first) {
                log.log(INFO, "{0} hold object second", Thread.currentThread().getName());
                try {
                  Thread.sleep(1000);
                }  catch (InterruptedException e) {
                    e.printStackTrace();
                }
                log.log(INFO, "{0} waiting 1000 ms", Thread.currentThread().getName());
                synchronized (second) {
                    log.log(WARNING, "dead lock");
                }
            }
        }
    }
}
