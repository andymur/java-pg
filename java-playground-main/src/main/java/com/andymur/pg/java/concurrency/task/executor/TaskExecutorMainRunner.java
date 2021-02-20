package com.andymur.pg.java.concurrency.task.executor;

import java.util.Optional;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class TaskExecutorMainRunner {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		boolean exit = false;
		TaskKeeper taskKeeper = new TaskKeeper();

		while (!exit) {
			final String command = scanner.nextLine();
			if ("q".equalsIgnoreCase(command)) {
				exit = true;
			} else if (command.startsWith("task")) {
				final String[] taskParts = command.split(" ");
				final Task newTaskToStart = new Task(taskParts[1], Integer.parseInt(taskParts[2]), Integer.parseInt(taskParts[3]));
				if (taskKeeper.addTask(newTaskToStart)) {
					System.out.println(String.format("Task %s has been added!", taskParts[1]));
				} else {
					System.out.println(String.format("Task %s has been already existed!", taskParts[1]));
				}
			} else if (command.startsWith("status")) {
				final String[] taskParts = command.split(" ");
				final String taskName = taskParts[1];
				System.out.println(
						"Task status: " +
						taskKeeper.getTask(taskName).flatMap(task -> Optional.of(task.toString())).orElse("Task is unknown!")
				);
			} else if (command.startsWith("cancel")) {
				final String[] taskParts = command.split(" ");
				final String taskName = taskParts[1];
				taskKeeper.cancelTask(taskName);
			}
		}
		taskKeeper.terminate();
		System.out.println("Bye!");
	}

	private static class TaskKeeper {
		ConcurrentHashMap<String, Task> tasks = new ConcurrentHashMap<>();
		ScheduledThreadPoolExecutor executorService = new ScheduledThreadPoolExecutor(10);

		public boolean addTask(final Task task) {
			if (tasks.containsKey(task.name)) {
				return false;
			}
			tasks.put(task.name, task);
			executorService.scheduleWithFixedDelay(task::tick, 0, 1, TimeUnit.SECONDS);
			return true;
		}

		public boolean cancelTask(final String taskName) {
			final Task task;
			if ((task = tasks.get(taskName)) != null) {
				task.cancelled = true;
			}
			return false;
		}

		public Optional<Task> getTask(final String taskName) {
			return Optional.ofNullable(tasks.get(taskName));
		}

		public void terminate() {
			executorService.shutdown();
		}
	}

	static class Task {
		public final String name;
		public final AtomicInteger x;
		public final int y;
		public volatile boolean cancelled = false;

		public Task(final String name, final int x, final int y) {
			this.name = name;
			this.x = new AtomicInteger(x);
			this.y = y;
		}

		public void tick() {
			if (cancelled) {
				return;
			}

			if (x.get() < y) {
				x.incrementAndGet();
			}
		}

		@Override
		public String toString() {
			return "Task{" +
					"name='" + name + '\'' +
					", x=" + x +
					", y=" + y +
					'}';
		}
	}
}
