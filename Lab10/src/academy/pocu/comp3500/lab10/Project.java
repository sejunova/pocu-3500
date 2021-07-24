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
        Set<String> maintenanceJobs = getMaintenanceJobs(taskList, sorted);
        return sorted
                .stream()
                .map(Task::getTitle)
                .filter(x -> !maintenanceJobs.contains(x))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    private static Set<String> getMaintenanceJobs(List<Task> tasks, List<Task> sortedTasks) {
        Set<String> maintenanceJobs = new HashSet<>();
        int i = 0;
        Set<String> visited = new HashSet<>(tasks.size());
        while (i < sortedTasks.size()) {
            List<Task> stack = new ArrayList<>();
            final String key = sortedTasks.get(i).getTitle();
            Task task = tasks.stream().filter(x -> x.getTitle().equals(key)).findFirst().get();
            dfs(task, visited, stack);
            if (stack.size() != 1) {
                maintenanceJobs.addAll(stack.stream().map(Task::getTitle).collect(Collectors.toCollection(ArrayList::new)));
            }
            i += stack.size();
        }
        return maintenanceJobs;
    }

    private static List<Task> sortTopology(List<Task> tasks) {
        List<Task> stack = new ArrayList<>(tasks.size());
        Set<String> visited = new HashSet<>(tasks.size());

        for (Task task: tasks) {
            dfs(task, visited, stack);
        }
        List<Task> ret = new ArrayList<>(stack.size());
        for (int i = stack.size() - 1; i >= 0; i--) {
            ret.add(stack.get(i));
        }
        return ret;
    }

    private static void dfs(Task task, Set<String> visited, List<Task> stack) {
        if (!visited.contains(task.getTitle())) {
            dfsRecursive(task, visited, stack);
        }
    }

    private static void dfsRecursive(Task task, Set<String> visited, List<Task> stack) {
        visited.add(task.getTitle());
        for (Task child : task.getPredecessors()) {
            if (!visited.contains(child.getTitle())) {
                visited.add(child.getTitle());
                dfsRecursive(child, visited, stack);
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