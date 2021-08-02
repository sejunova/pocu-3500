package academy.pocu.comp3500.assignment4;
​
import academy.pocu.comp3500.assignment4.project.Task;
​
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
​
public final class Project2 {
    private final HashMap<String, Task> tasks = new HashMap<>();
​
    public Project(final Task[] tasks) {
        for (Task task : tasks) {
            this.tasks.put(task.getTitle(), task);
        }
    }
​
    public int findTotalManMonths(final String task) {
        int result = 0;
​
        Queue<Task> queue = new LinkedList<>();
        HashMap<Task, String> discovered = new HashMap<>();
​
        queue.add(this.tasks.get(task));
        discovered.put(this.tasks.get(task), "");
​
        while (!queue.isEmpty()) {
            Task next = queue.remove();
            result += next.getEstimate();
​
            for (Task neighbor : next.getPredecessors()) {
                if (!discovered.containsKey(neighbor)) {
                    queue.add(neighbor);
                    discovered.put(neighbor, "");
                }
            }
        }
​
        return result;
    }
​
    public int findMinDuration(final String task) {
        return findMinDurationRecursive(this.tasks.get(task));
    }
​
    private int findMinDurationRecursive(Task task) {
        if (task.getPredecessors().size() == 0) {
            return task.getEstimate();
        }
​
        int maxValue = Integer.MIN_VALUE;
        for (Task neighbor : task.getPredecessors()) {
            int value = findMinDurationRecursive(neighbor);
            if (value > maxValue) {
                maxValue = value;
            }
        }
​
        return task.getEstimate() + maxValue;
    }
​
    public int findMaxBonusCount(final String task) {
        HashMap<Task, HashMap<Task, Flow>> edgeList = new HashMap<>();
        HashMap<Task, Task> clone = new HashMap<>();
        ArrayList<Task> startTasks = new ArrayList<>();
​
        createEdgeListRecursive(this.tasks.get(task), edgeList, startTasks, clone);
​
        boolean isEnd = true;
        int result = 0;
        while (true) {
            for (Task start : startTasks) {
                if (this.tasks.get(task).equals(start)) {
                    return start.getEstimate();
                }
                Queue<Task> queue = new LinkedList<>();
                HashMap<Task, String> discovered = new HashMap<>();
                HashMap<Task, Task> prev = new HashMap<>();
                boolean isFind = false;
​
                queue.add(clone.get(start));
                discovered.put(clone.get(start), "");
                prev.put(clone.get(start), null);
​
                while (!queue.isEmpty()) {
                    Task next = queue.remove();
​
                    if (isFind) {
                        isEnd = false;
                        break;
                    }
​
                    for (Task neighbor : edgeList.get(next).keySet()) {
                        if (!discovered.containsKey(neighbor) && edgeList.get(next).get(neighbor).getMaxFlow() > edgeList.get(next).get(neighbor).getCurrentFlow()) {
                            queue.add(neighbor);
                            discovered.put(neighbor, "");
                            prev.put(neighbor, next);
                            if (neighbor.equals(this.tasks.get(task))) {
                                isFind = true;
                                break;
                            }
                        }
                    }
                }
​
                if (isFind) {
                    int minValue = Integer.MAX_VALUE;
                    Task path = this.tasks.get(task);
​
                    while (prev.get(path) != null) {
                        int temp = edgeList.get(prev.get(path)).get(path).getMaxFlow() - edgeList.get(prev.get(path)).get(path).getCurrentFlow();
                        if (temp < minValue) {
                            minValue = temp;
                        }
                        path = prev.get(path);
                    }
​
                    path = this.tasks.get(task);
​
                    while (prev.get(path) != null) {
                        edgeList.get(prev.get(path)).get(path).addCurrentFlow(minValue);
                        edgeList.get(path).get(prev.get(path)).addCurrentFlow(minValue * -1);
                        path = prev.get(path);
                    }
​
                    result += minValue;
                }
            }
​
            if (isEnd) {
                break;
            }
            isEnd = true;
        }
​
        if (this.tasks.get(task).getEstimate() < result) {
            result = this.tasks.get(task).getEstimate();
        }
        return result;
    }
​
    private void createEdgeListRecursive(Task task, HashMap<Task, HashMap<Task, Flow>> edgeList, ArrayList<Task> startTasks, HashMap<Task, Task> clone) {
        Task back = new Task(task.getTitle(), 0);
​
        edgeList.put(task, new HashMap<>());
        edgeList.put(back, new HashMap<>());
​
        edgeList.get(back).put(task, new Flow(task.getEstimate()));
        edgeList.get(task).put(back, new Flow(0));
​
        clone.put(task, back);
        clone.put(back, task);
​
        if (task.getPredecessors().size() == 0) {
            startTasks.add(task);
        }
​
        for (Task neighbor : task.getPredecessors()) {
            int minValue = task.getEstimate();
            if (minValue > neighbor.getEstimate()) {
                minValue = neighbor.getEstimate();
            }
​
            if (!edgeList.containsKey(neighbor)) {
                createEdgeListRecursive(neighbor, edgeList, startTasks, clone);
            }
​
            edgeList.get(neighbor).put(back, new Flow(minValue));
            edgeList.get(back).put(neighbor, new Flow(0));
        }
    }
}
