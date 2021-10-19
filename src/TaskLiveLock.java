import java.awt.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.util.logging.Level.INFO;

public class TaskLiveLock {

    private static Lock lockFirst = new ReentrantLock(true);
    private static Lock lockSecond = new ReentrantLock(true);
    private static Logger log = Logger.getLogger("LiveLock");

    public static void main(String[] args) {
        LiveLockFirstThread threadFirst = new LiveLockFirstThread();
        LiveLockSecondThread threadSecond = new LiveLockSecondThread();
        threadFirst.start();
        threadSecond.start();
    }

    public static class LiveLockFirstThread extends Thread {
        @Override
        public void run() {
            try {
                while (true) {
                    lockFirst.tryLock(150, TimeUnit.MILLISECONDS);
                    log.log(INFO, "{0} capture first lock, waiting to acquired second lock", Thread.currentThread().getName());
                    Thread.sleep(500);

                    if(lockSecond.tryLock()) {
                        log.log(INFO, "lock second acquired");
                    } else {
                        log.log(INFO, "cannot acquire second lock, unlock first lock");
                        lockFirst.unlock();
                        continue;
                    }
                    log.log(INFO, "execute second operation. ");
                    break;
                }
                lockSecond.unlock();
                lockFirst.unlock();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static class LiveLockSecondThread extends Thread {
        @Override
        public void run() {
            try {
                while (true) {
                    lockSecond.tryLock(150, TimeUnit.MILLISECONDS);
                    log.log(INFO, "{0} capture second lock, waiting to acquired first lock", Thread.currentThread().getName());
                    Thread.sleep(500);

                    if(lockFirst.tryLock()) {
                        log.log(INFO, "lock first acquired");
                    } else {
                        log.log(INFO, "cannot acquire first lock, unlock second lock");
                        lockSecond.unlock();
                        continue;
                    }
                    log.log(INFO, "execute first operation. ");
                    break;
                }
                lockFirst.unlock();
                lockSecond.unlock();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
