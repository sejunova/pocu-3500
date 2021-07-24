package academy.pocu.comp3500.lab10;

import academy.pocu.comp3500.lab10.project.Task;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Project {
    public static List<String> findSchedule(final Task[] tasks, final boolean includeMaintenance) {
        List<Task> taskList = new ArrayList<>(Arrays.asList(tasks));
        List<Task> transPosed = getTranspose(taskList);
        List<Task> sorted = sortTopology(transPosed);
        if (includeMaintenance) {
            return sorted
                    .stream()
                    .map(Task::getTitle)
                    .collect(Collectors.toCollection(ArrayList::new));
        }
        Set<Task> maintenanceJobs = getMaintenanceJobs(taskList, sorted);
        return sorted
                .stream()
                .filter(x -> !maintenanceJobs.contains(x))
                .map(Task::getTitle)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    private static Set<Task> getMaintenanceJobs(List<Task> tasks, List<Task> sortedTasks) {
        Set<Task> maintenanceJobs = new HashSet<>();
        int i = 0;
        Set<Task> visited = new HashSet<>(tasks.size());
        while (i < sortedTasks.size()) {
            List<Task> stack = new ArrayList<>();
            Task task = sortedTasks.get(i);
            Checker checker = new Checker(task, false);
            dfs(task, visited, stack, checker);
            if (checker.isCycle) {
                maintenanceJobs.addAll(stack);
            }
            i += stack.size();
        }
        return maintenanceJobs;
    }

    private static List<Task> sortTopology(List<Task> tasks) {
        List<Task> stack = new ArrayList<>(tasks.size());
        Set<Task> visited = new HashSet<>(tasks.size());

        for (Task task: tasks) {
            dfs(task, visited, stack, new Checker(task, false));
        }
        List<Task> ret = new ArrayList<>(stack.size());
        for (int i = stack.size() - 1; i >= 0; i--) {
            ret.add(stack.get(i));
        }
        return ret;
    }

    private static void dfs(Task task, Set<Task> visited, List<Task> stack, Checker checker) {
        if (!visited.contains(task)) {
            dfsRecursive(task, visited, stack, checker);
        }
    }

    private static void dfsRecursive(Task task, Set<Task> visited, List<Task> stack, Checker checker) {
        visited.add(task);
        for (Task child : task.getPredecessors()) {
            if (!visited.contains(child)) {
                visited.add(child);
                if (checker.task == child) {
                    checker.isCycle = true;
                }
                dfsRecursive(child, visited, stack, checker);
            }
        }
        stack.add(task);
    }

    private static List<Task> getTranspose(List<Task> tasks) {
        Map<String, Task> transposedTasks = new HashMap<>(tasks.size());
        for (Task task: tasks) {
            transposedTasks.put(task.getTitle(), new Task(task.getTitle(), task.getEstimate()));
        }

        for (Task task: tasks) {
            for (Task predecessor: task.getPredecessors()) {
                transposedTasks.get(predecessor.getTitle())
                        .addPredecessor(transposedTasks.get(task.getTitle()));
            }
        }

        return new ArrayList<>(transposedTasks.values());
    }

    private static class Checker {
        private Task task;
        private boolean isCycle;

        public Checker(Task task, boolean isCycle) {
            this.task = task;
            this.isCycle = isCycle;
        }
    }

//    public static void main(String[] args) {
//        Task a = new Task("A", 12);
//        Task b = new Task("B", 7);
//        Task c = new Task("C", 10);
//        Task d = new Task("D", 9);
//        Task e = new Task("E", 8);
//        Task f = new Task("F", 11);
//        Task g = new Task("G", 14);
//
//        b.addPredecessor(a);
//        c.addPredecessor(b, e);
//        d.addPredecessor(c);
//        e.addPredecessor(d);
//        f.addPredecessor(a);
//        g.addPredecessor(b, f);
//
//        List<Task> tasks = new ArrayList<>(Arrays.asList(a, b, c, d, e, f, g));
//
//        List<Task> sorted = sortTopology(getTranspose(tasks));
//        int x = 0;
//
//
//
//
//    }
}