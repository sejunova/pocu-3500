package academy.pocu.comp3500.assignment4;

import academy.pocu.comp3500.assignment4.project.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class Project {
    private Task[] tasks;

    public Project(final Task[] tasks) {
        this.tasks = tasks;
    }

    public int findTotalManMonths(final String task) {
        int manMonths = 0;
        List<Task> tasksForMs = new ArrayList<>();
        Map<String, Boolean> visited = new HashMap<>();
        Task mileStone = null;
        for (Task t : tasks) {
            if (t.getTitle().equals(task)) {
                mileStone = t;
                break;
            }
        }
        assert mileStone != null;
        dfsRecursive(mileStone, visited, tasksForMs);
        for (Task t : tasksForMs) {
            manMonths += t.getEstimate();
        }
        return manMonths;
    }

    public int findMinDuration(final String task) {
        return -1;
    }

    public int findMaxBonusCount(final String task) {
        return -1;
    }

    private static void dfsRecursive(Task task, Map<String, Boolean> visited, List<Task> stack) {
        for (Task child : task.getPredecessors()) {
            if (!visited.containsKey(child.getTitle())) {
                visited.put(child.getTitle(), true);
                dfsRecursive(child, visited, stack);
            }
        }
        stack.add(task);
    }
}