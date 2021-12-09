package aoc2021;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Executor {

    public void execute(AoCTask task, String fname, int n) {
        try {
            File f = new File(fname);
            task.readInput(new Scanner(f), n);
        } catch (FileNotFoundException e) {
            System.out.println("file could not be found");
            return;
        }
        System.out.println("Task 1: " + task.task1());
        System.out.println("Task 2: " + task.task2());
        System.out.println();
    }

    public void execute_timed(AoCTask task, String fname, int n) {
        long startTime = System.currentTimeMillis();
        try {
            File f = new File(fname);
            task.readInput(new Scanner(f), n);
        } catch (FileNotFoundException e) {
            System.out.println("file could not be found");
            return;
        }
        long readTime = System.currentTimeMillis();
        System.out.println("Task 1: " + task.task1());
        long task1Time = System.currentTimeMillis();
        System.out.println("Task 2: " + task.task2());
        long endTime = System.currentTimeMillis();
        System.out.printf("%s - %s - %s -- %s \n", readTime - startTime, task1Time - readTime, endTime - task1Time,
                endTime - startTime);
    }
}
