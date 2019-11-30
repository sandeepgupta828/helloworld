package com.programs.async;

import java.util.concurrent.*;
import java.util.function.Consumer;
import java.util.function.Function;

public class AsyncTest {

    public static class ExecuteAsync {
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        public Future<Integer> computeSquareAsyncC(int n) {
            return executorService.submit(() -> { return n * n;});
        }

        public Future<?> computeSquareAsyncR(int n) {
            return executorService.submit(() -> { System.out.println(n * n);});
        }

        public Future<Integer> compute(int n) {
            return CompletableFuture.completedFuture(n*n);
        }

        public FutureTask<Integer> computeSquareAsyncF(int n) {
            // FutureTask is simply implementation of Future interface
            FutureTask<Integer>  task = new FutureTask<>(() -> {return n*n;});
            executorService.submit(task);
            return task;
        }

    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecuteAsync executeAsync = new ExecuteAsync();
        Future<Integer> future1 = executeAsync.computeSquareAsyncC(100);
        System.out.println(future1.get());
        Future<?> future2 = executeAsync.computeSquareAsyncR(9);
        System.out.println(future2.get());
        System.out.println(executeAsync.compute(8).get());
        System.out.println(executeAsync.computeSquareAsyncF(7).get());
        Function<Integer, Integer> square = value -> value*value;
        CompletableFuture.completedFuture(6).thenApplyAsync(square).thenAccept(val -> System.out.println(val));
        CompletableFuture.completedFuture(5).thenApplyAsync(square, Executors.newSingleThreadExecutor()).thenAcceptAsync(val -> System.out.println(val), Executors.newSingleThreadExecutor());

        Function<Integer, Integer> divide = value -> value/0;
        Function<Throwable, Integer> exceptionHandler = (throwable) -> {
            System.out.println(throwable.getMessage());
            return 1;
        };

        Consumer printer = value -> System.out.println(value);
        CompletableFuture cf = CompletableFuture.supplyAsync(() -> 4).thenApply(divide).exceptionally(exceptionHandler).thenApply(square).thenAccept(printer);
        cf.join();
        System.out.println(cf.isCompletedExceptionally());
        System.exit(0);

    }
}
