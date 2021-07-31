package academy.pocu.comp3500.assignment4;

import academy.pocu.comp3500.assignment4.project.Task;

import java.util.HashMap;
import java.util.Map;

public final class Project {
    private Task[] tasks;

    public Project(final Task[] tasks) {
        this.tasks = tasks;
    }

    public int findTotalManMonths(final String task) {
        Task mileStone = null;
        for (Task t : tasks) {
            if (t.getTitle().equals(task)) {
                mileStone = t;
                break;
            }
        }

        assert mileStone != null;

        ManMonth manMonth = new ManMonth();
        Map<Task, Boolean> visited = new HashMap<>();
        getManMonthsRecursive(mileStone, visited, manMonth);
        return manMonth.val;
    }

    public int findMinDuration(final String task) {
        Task mileStone = null;
        for (Task t : tasks) {
            if (t.getTitle().equals(task)) {
                mileStone = t;
                break;
            }
        }

        assert mileStone != null;
        Map<Task, Integer> minDurationMap = new HashMap<>();
        minDurationMap.put(mileStone, 0);
    }

    public int findMaxBonusCount(final String task) {
        return -1;
    }

    private static void getManMonthsRecursive(Task task, Map<Task, Boolean> visited, ManMonth manMonth) {
        for (Task child : task.getPredecessors()) {
            if (!visited.containsKey(child)) {
                visited.put(child, true);
                getManMonthsRecursive(child, visited, manMonth);
            }
        }
        manMonth.val += task.getEstimate();
    }

    private static void getMinDuration(Task task, Map<Task, Integer> minDurationMap) {
        for (Task child : task.getPredecessors()) {
            if (!minDurationMap.containsKey(child)) {
                minDurationMap.put(child, 0);
                getManMonthsRecursive(child, visited, manMonth);
            }
        }
        manMonth.val += task.getEstimate();
    }

    private static class ManMonth {
        private int val = 0;
    }
}