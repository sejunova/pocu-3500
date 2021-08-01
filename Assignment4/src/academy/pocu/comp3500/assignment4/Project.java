package academy.pocu.comp3500.assignment4;

import academy.pocu.comp3500.assignment4.project.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
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
        getMinDuration(mileStone, minDurationMap);
        return minDurationMap.get(mileStone);
    }

    public int findMaxBonusCount(final String task) {
        Task mileStone = null;
        for (Task t : tasks) {
            if (t.getTitle().equals(task)) {
                mileStone = t;
                break;
            }
        }

        assert mileStone != null;

        Map<Task, Boolean> visited = new HashMap<>();
        List<Task> tasksForMilestone = new ArrayList<>();
        getTasksForMilestoneRecursive(mileStone, visited, tasksForMilestone);

        List<Task> startNodes = new ArrayList<>();
        for (Task t : tasksForMilestone) {
            if (t.getPredecessors().isEmpty()) {
                startNodes.add(t);
            }
        }
        Map<String, Map<String, Edge>> nodeEdges = new HashMap<>();
        for (Task t : tasksForMilestone) {
            String s = t.getTitle();
            String e = t.getTitle() + "'";
            // 임시노드와 연결
            nodeEdges.put(s, new HashMap<>());
            nodeEdges.put(e, new HashMap<>());
            nodeEdges.get(s).put(e, new Edge(s, e, Integer.MAX_VALUE));
        }

        for (Task t : tasksForMilestone) {
            String e = t.getTitle();
            for (Task p : t.getPredecessors()) {
                String s = p.getTitle() + "'";
                // 정방향 엣지 생성
                nodeEdges.get(s).put(e, new Edge(s, e, p.getEstimate()));
                // 역방향 엣지 생성
                nodeEdges.get(e).put(s, new Edge(e, s, 0));
            }
        }
        String endNodeName = "END_NODE";
        String milestoneEndName = mileStone.getTitle() + "'";
        nodeEdges.put(endNodeName, new HashMap<>());
        nodeEdges.get(milestoneEndName).put(endNodeName, new Edge(milestoneEndName, endNodeName, mileStone.getEstimate()));
//        nodeEdges.get(endNodeName).put(milestoneEndName, new Edge(endNodeName, milestoneEndName, 0);

        for (Task s : startNodes) {
            while (true) {
                boolean isFull = bfs(nodeEdges, s.getTitle(), endNodeName);
                if (nodeEdges.get(milestoneEndName).get(endNodeName).flow == mileStone.getEstimate()) {
                    return mileStone.getEstimate();
                }
                if (isFull) {
                    break;
                }
            }
        }
        int answer = nodeEdges.get(milestoneEndName).get(endNodeName).flow;
        return answer;
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
        minDurationMap.put(task, task.getEstimate());
        for (Task child : task.getPredecessors()) {
            if (!minDurationMap.containsKey(child)) {
                minDurationMap.put(child, 0);
                getMinDuration(child, minDurationMap);
            }
        }
        int minEstimationForTask = task.getEstimate();
        for (Task child : task.getPredecessors()) {
            if (minDurationMap.get(child) + task.getEstimate() > minEstimationForTask) {
                minEstimationForTask = minDurationMap.get(child) + task.getEstimate();
            }
        }
        minDurationMap.put(task, minEstimationForTask);
    }

    private static class ManMonth {
        private int val = 0;
    }

    private static void getTasksForMilestoneRecursive(Task task, Map<Task, Boolean> visited, List<Task> tasks) {
        for (Task child : task.getPredecessors()) {
            if (!visited.containsKey(child)) {
                visited.put(child, true);
                getTasksForMilestoneRecursive(child, visited, tasks);
            }
        }
        tasks.add(task);
    }

    private static boolean bfs(Map<String, Map<String, Edge>> nodeEdges, String start, String end) {
        LinkedList<String> queue = new LinkedList<>();
        Map<String, String> visitedBy = new HashMap<>();
        for (String node : nodeEdges.keySet()) {
            visitedBy.put(node, null);
        }

        queue.addFirst(start);
        while (!queue.isEmpty()) {
            String node = queue.removeFirst();
            if (node.equals(end)) {
                String from;
                String to = end;
                int minFlowAvailable = Integer.MAX_VALUE;
                List<Edge> paths = new ArrayList<>();
                while (true) {
                    from = visitedBy.get(to);
                    Edge e = nodeEdges.get(from).get(to);
                    paths.add(e);
                    minFlowAvailable = Math.min(e.getFlowAvailable(), minFlowAvailable);

                    if (from.equals(start)) {
                        break;
                    }

                    to = from;
                }
                for (Edge e : paths) {
                    e.flow += minFlowAvailable;
                    Edge reverse = nodeEdges.get(e.end).get(e.start);
                    if (reverse != null) {
                        reverse.flow -= minFlowAvailable;
                    }
                }
                return false;
            }

            for (Edge e : nodeEdges.get(node).values()) {
                if (e.end.equals(node)) {
                    continue;
                }
                if (e.isMax()) {
                    continue;
                }
                if (visitedBy.get(e.end) != null) {
                    continue;
                }

                visitedBy.put(e.end, node);
                queue.addLast(e.end);
            }
        }
        return true;
    }


    private static class Edge {
        String start;
        String end;
        int capacity;
        int flow = 0;

        public Edge(String start, String end, int capacity) {
            this.start = start;
            this.end = end;
            this.capacity = capacity;
        }

        public boolean isMax() {
            return capacity == flow;
        }

        public int getFlowAvailable() {
            return capacity - flow;
        }

        @Override
        public String toString() {
            return "Edge{" +
                    "start='" + start + '\'' +
                    ", end='" + end + '\'' +
                    ", capacity=" + capacity +
                    ", flow=" + flow +
                    '}';
        }
    }
}