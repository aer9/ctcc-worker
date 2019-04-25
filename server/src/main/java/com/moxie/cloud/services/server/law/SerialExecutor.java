package com.moxie.cloud.services.server.law;


import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * Created with IntelliJ IDEA.
 *
 * @author: yangjiawei
 * @date: 2019/4/18
 */


public class SerialExecutor implements Executor {


    final Queue<Runnable> tasks = new ArrayDeque<>();

    final Executor executor;

    Runnable active;

    SerialExecutor(Executor executor) {
        this.executor = executor;
    }


    @Override
    public synchronized void execute(Runnable command) {

        tasks.offer(new Runnable() {
            @Override
            public void run() {
                try {
                    command.run();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    sechdulNext();
                }
            }
        });

        if (active == null) {
            sechdulNext();
        }

    }


    public synchronized void sechdulNext() {
        if ((active = tasks.poll()) != null) {
            executor.execute(active);
        }
    }

    private static int ctlOf(int rs, int wc) {
        return rs | wc;
    }


    public static void main(String[] args) {

        final int COUNT_BITS = Integer.SIZE - 3;
        final int RUNNING = -1 << COUNT_BITS;
        final int CAPACITY   = (1 << COUNT_BITS) - 1;

        AtomicInteger ctl = new AtomicInteger(ctlOf(RUNNING, 0));

        String binary = Integer.toBinaryString(COUNT_BITS);
        String capacity = Integer.toBinaryString(CAPACITY);
        System.out.println(binary);
        System.out.println(capacity);
        System.out.println(0 & CAPACITY);


//        ExecutorService executor = Executors.newCachedThreadPool();
//        Task task = new Task();
////        Future<Integer> result = executor.submit(task);
//
//        FutureTask<Integer> futureTask = new FutureTask<Integer>(task);
//        executor.submit(futureTask);
//        executor.shutdown();
//
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//        System.out.println("主线程在执行task");
//
//        try {
//            System.out.println("task执行结果：" + futureTask.get());
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        }
//        System.out.println("所有任务执行完毕");

    }

    static class Task implements Callable<Integer> {
        @Override
        public Integer call() throws Exception {
            System.out.println("子线程在进行计算");
            Thread.sleep(3000);
            int sum = 0;
            for (int i = 0; i < 100; i++) {
                sum += i;
            }
            return sum;
        }
    }

}
