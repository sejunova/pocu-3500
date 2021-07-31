package academy.pocu.comp3500.assignment4;

import academy.pocu.comp3500.assignment4.project.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class Project {
    private Task[] tasks;
    private List<Task> sorted;
    private List<Task> transposed;

    public Project(final Task[] tasks) {
        this.tasks = tasks;
        this.sorted = sortTopologically(tasks);
        this.transposed = getTranspose(tasks);

    }

    public int findTotalManMonths(final String task) {
        int manMonths = 0;
        List<String> sccs = getSccSet(transposed, sorted);
        for (int i = sorted.size() - 1; i >= 0; i--) {
            Task t = sorted.get(i);
            if (sccs.contains(t.getTitle())) {
                continue;
            }
            manMonths += t.getEstimate();
            if (t.getTitle().equals(task)) {
                break;
            }
        }
        return manMonths;
    }

    public int findMinDuration(final String task) {
        return -1;
    }

    public int findMaxBonusCount(final String task) {
        return -1;
    }

    private static void dfs(Task task, Set<String> visited, List<Task> stack) {
        if (!visited.contains(task.getTitle())) {
            visited.add(task.getTitle());
            dfsRecursive(task, visited, stack);
        }
    }

    private static void dfsRecursive(Task task, Set<String> visited, List<Task> stack) {
        for (Task child : task.getPredecessors()) {
            if (!visited.contains(child.getTitle())) {
                visited.add(child.getTitle());
                dfsRecursive(child, visited, stack);
            }
        }
        stack.add(task);
    }

    private static List<Task> getTranspose(Task[] tasks) {
        Map<String, Task> transposedTasks = new HashMap<>(tasks.length);
        for (Task task : tasks) {
            transposedTasks.put(task.getTitle(), new Task(task.getTitle(), task.getEstimate()));
        }

        for (Task task : tasks) {
            for (Task predecessor : task.getPredecessors()) {
                transposedTasks.get(predecessor.getTitle())
                        .addPredecessor(transposedTasks.get(task.getTitle()));
            }
        }

        return new ArrayList<>(transposedTasks.values());
    }

    private static LinkedList<Task> sortTopologically(Task[] tasks) {
        HashSet<Task> discovered = new HashSet<>();
        LinkedList<Task> sortedList = new LinkedList<>();

        for (Task task : tasks) {
            if (discovered.contains(task)) {
                continue;
            }

            topologicalSortRecursive(task,
                    discovered,
                    sortedList);
        }

        return sortedList;
    }

    private static void topologicalSortRecursive(Task task, HashSet<Task> discovered, LinkedList<Task> linkedList) {
        discovered.add(task);

        for (Task nextCourse : task.getPredecessors()) {
            if (discovered.contains(nextCourse)) {
                continue;
            }

            topologicalSortRecursive(nextCourse,
                    discovered,
                    linkedList);
        }

        linkedList.addFirst(task);
    }

    private static List<String> getSccSet(List<Task> transposed, List<Task> sortedTasks) {
        Map<String, Task> transposedMap = new HashMap<>();
        for (Task task : transposed) {
            transposedMap.put(task.getTitle(), task);
        }

        List<String> sccs = new ArrayList<>();
        Set<String> visited = new HashSet<>(transposed.size());


        for (Task sortedTask : sortedTasks) {
            List<Task> stack = new ArrayList<>();
            Task cur = transposedMap.get(sortedTask.getTitle());

            dfs(cur, visited, stack);
            if (stack.size() >= 2) {
                for (Task task : stack) {
                    sccs.add(task.getTitle());
                }
            }
        }
        return sccs;
    }
}