package academy.pocu.comp3500.lab9;

import academy.pocu.comp3500.lab9.data.Task;

import java.util.Arrays;
import java.util.Comparator;

public class ProfitCalculator {
    public static int findMaxProfit(final Task[] tasks, final int[] skillLevels) {
        if (tasks.length == 0 || skillLevels.length == 0) {
            return 0;
        }
        int profit = 0;
        Arrays.sort(skillLevels);
        Arrays.sort(tasks, Comparator.comparingInt(Task::getProfit));

        int right = skillLevels.length - 1;
        for (int i = tasks.length - 1; i >= 0; i--) {
            Task task = tasks[i];
            int equalOrGraterIdx = binarySearchGreater(skillLevels, right, task.getDifficulty());
            if (equalOrGraterIdx > right) {
                continue;
            }
            profit += (right - equalOrGraterIdx + 1) * task.getProfit();
            right = equalOrGraterIdx - 1;

            if (right == -1) {
                break;
            }
        }
        return profit;
    }

    private static int binarySearchGreater(int[] arr, int r, int target) {
        int l = 0;
        while (l <= r) {
            int m = l + (r - l) / 2;
            if (arr[m] == target)
                return m;
            if (arr[m] < target)
                l = m + 1;
            else
                r = m - 1;
        }
        return r + 1;
    }

    public static void main(String[] args) {
        Task[] tasks = new Task[]{
                new Task(20, 30),
                new Task(30, 40),
                new Task(10, 35)
        };

        int profit = ProfitCalculator.findMaxProfit(tasks, new int[]{10}); // 35
        System.out.println(profit);
        profit = ProfitCalculator.findMaxProfit(tasks, new int[]{20, 25}); // 70
        System.out.println(profit);
        profit = ProfitCalculator.findMaxProfit(tasks, new int[]{40, 15, 5}); // 75
        System.out.println(profit);

    }
}