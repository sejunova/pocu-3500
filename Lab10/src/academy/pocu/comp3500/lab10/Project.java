package academy.pocu.comp3500.lab10;

import academy.pocu.comp3500.lab10.project.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringJoiner;
import java.util.stream.Collectors;

public class Project {
    public static List<String> findSchedule(final Task[] tasks, final boolean includeMaintenance) {
        List<Task> sorted = sortTopology(tasks);
        List<Task> transposed = getTranspose(tasks);

        List<String> answer = new ArrayList<>();
        if (includeMaintenance) {
            Map<String, Task> orgTaskMap = new HashMap<>(tasks.length);
            for (Task task : tasks) {
                orgTaskMap.put(task.getTitle(), task);
            }
            Map<String, String> sccs = getSccs(transposed, sorted, orgTaskMap);
            for (int i = sorted.size() - 1; i >= 0; i--) {
                String t = sorted.get(i).getTitle();
                if (!sccs.containsKey(t)) {
                    answer.add(t);
                } else {
                    if (sccs.get(t) != null) {
                        String[] titles = sccs.get(t).split(",");
                        for (String title : titles) {
                            answer.add(title);
                        }
                    }
                }
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

    private static Map<String, String> getSccs(List<Task> transposed, List<Task> sortedTasks, Map<String, Task> orgTaskMap) {
        Map<String, Task> transposedMap = new HashMap<>(transposed.size());
        for (Task task : transposed) {
            transposedMap.put(task.getTitle(), task);
        }

        Map<String, String> sccs = new HashMap<>(transposed.size());
        int i = 0;
        Set<String> visited = new HashSet<>(transposed.size());
        while (i < sortedTasks.size()) {
            List<Task> stack = new ArrayList<>();
            String entryKey = sortedTasks.get(i).getTitle();
            Task task = transposedMap.get(entryKey);
            dfs(task, visited, stack);
            if (stack.size() != 1) {
                StringJoiner joiner = new StringJoiner(",");

                int k = getEntryIdx(stack, orgTaskMap);
                entryKey = stack.get(k).getTitle();
                for (int l = 0; l < stack.size(); l++) {
                    joiner.add(stack.get(k).getTitle());
                    if (l != 0) {
                        String t = stack.get(k).getTitle();
                        assert !t.equals(entryKey);
                        sccs.put(t, null);
                    }

                    k--;
                    if (k < 0) {
                        k = stack.size() + k;
                    }
                }
                sccs.put(entryKey, joiner.toString());

            }
            i += stack.size();
        }
        return sccs;
    }

    private static Set<String> getSccSet(List<Task> transposed, List<Task> sortedTasks) {
        Map<String, Task> transposedMap = new HashMap<>();
        for (Task task : transposed) {
            transposedMap.put(task.getTitle(), task);
        }

        Set<String> sccs = new HashSet<>();
        int i = 0;
        Set<String> visited = new HashSet<>(transposed.size());
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

    private static int getEntryIdx(List<Task> stack, Map<String, Task> orgTaskMap) {
        Set<String> titles = new HashSet<>(stack.size());
        for (Task task : stack) {
            titles.add(task.getTitle());
        }
        for (int i = 0; i < stack.size(); i++) {
            String title = stack.get(i).getTitle();
            for (Task t : orgTaskMap.get(title).getPredecessors()) {
                if (!titles.contains(t.getTitle())) {
                    return i;
                }
            }
        }
        throw new RuntimeException("should not come here");
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

    private static List<Task> sortTopology(Task[] tasks) {
        List<Task> stack = new ArrayList<>(tasks.length);
        Set<String> visited = new HashSet<>(tasks.length);

        for (Task task : tasks) {
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

//    private static LinkedList<Task> sortTopologically(ArrayList<Task> tasks) {
//        HashSet<Task> discovered = new HashSet<>();
//        LinkedList<Task> sortedList = new LinkedList<>();
//
//        for (Task task : tasks) {
//            if (discovered.contains(task)) {
//                continue;
//            }
//
//            topologicalSortRecursive(task,
//                    discovered,
//                    sortedList);
//        }
//
//        return sortedList;
//    }
//
//    private static void topologicalSortRecursive(Task task, HashSet<Task> discovered, LinkedList<Task> linkedList) {
//        discovered.add(task);
//
//        for (Task nextCourse : task.getPredecessors()) {
//            if (discovered.contains(nextCourse)) {
//                continue;
//            }
//
//            topologicalSortRecursive(nextCourse,
//                    discovered,
//                    linkedList);
//        }
//
//        linkedList.addFirst(task);
//    }

}