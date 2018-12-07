package com.andymur.pg.java.concurrency.future;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class CompletableFutureRunner {
	public static void main(String[] args) throws ExecutionException, InterruptedException {
		CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> "Hi");

		completableFuture.thenRun(() -> {
			System.out.println("Finished");
		});

		CompletableFuture.runAsync(() -> {
			System.out.println("Hi");
		});

		CompletableFuture<String> newCompletableFuture = new CompletableFuture<>();
		newCompletableFuture.complete("This is what you get");
		System.out.println(newCompletableFuture.get());
		assert newCompletableFuture.get().equals("This is what you get");
	}
}
