package academy.pocu.comp3500.lab10;

import academy.pocu.comp3500.lab10.project.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Project {
    public static List<String> findSchedule(final Task[] tasks, final boolean includeMaintenance) {
        List<Task> sorted = sortTopologically(tasks);
        List<Task> transposed = getTranspose(tasks);

        List<String> answer = new ArrayList<>();
        if (includeMaintenance) {
            List<Task> transposedSorted = sortTopologically(transposed.toArray(Task[]::new));
            for (Task t : transposedSorted) {
                answer.add(t.getTitle());
            }
        } else {
            Set<String> sccSet = getSccSet(transposed, sorted);
            for (int i = sorted.size() - 1; i >= 0; i--) {
                String t = sorted.get(i).getTitle();
                if (!sccSet.contains(t)) {
                    answer.add(t);
                }
            }
        }
        return answer;
    }

    private static Set<String> getSccSet(List<Task> transposed, List<Task> sortedTasks) {
        Map<String, Task> transposedMap = new HashMap<>();
        for (Task task : transposed) {
            transposedMap.put(task.getTitle(), task);
        }

        Set<String> sccs = new HashSet<>();
        int i = 0;
        Set<Task> visited = new HashSet<>(transposed.size());
        while (i < sortedTasks.size()) {
            List<Task> stack = new ArrayList<>();
            String entryKey = sortedTasks.get(i).getTitle();
            Task task = transposedMap.get(entryKey);
            dfs(task, visited, stack);
            if (stack.size() != 1) {
                for (Task t : stack) {
                    sccs.add(t.getTitle());
                }
            }
            i += stack.size();
        }
        return sccs;
    }

    private static void dfs(Task task, Set<Task> visited, List<Task> stack) {
        if (!visited.contains(task)) {
            visited.add(task);
            dfsRecursive(task, visited, stack);
        }
    }

    private static void dfsRecursive(Task task, Set<Task> visited, List<Task> stack) {
        for (Task child : task.getPredecessors()) {
            if (!visited.contains(child)) {
                visited.add(child);
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

}