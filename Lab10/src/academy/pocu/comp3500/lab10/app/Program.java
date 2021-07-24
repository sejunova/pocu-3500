package academy.pocu.comp3500.lab10.app;

import academy.pocu.comp3500.lab10.Project;
import academy.pocu.comp3500.lab10.project.Task;

import java.util.List;

public class Program {

    public static void main(String[] args) {
//        {
//            Task a = new Task("A", 6);
//            Task b = new Task("B", 6);
//            Task c = new Task("C", 6);
//            Task d = new Task("D", 6);
//            Task e = new Task("E", 6);
//            Task f = new Task("F", 6);
//            Task g = new Task("G", 6);
//            Task h = new Task("H", 6);
//            Task i = new Task("I", 6);
//            Task j = new Task("J", 6);
//            Task k = new Task("K", 6);
//            Task l = new Task("L", 6);
//            Task m = new Task("M", 6);
//            Task n = new Task("N", 6);
//            Task o = new Task("O", 6);
//            Task p = new Task("P", 6);
//            Task q = new Task("Q", 6);
//            Task r = new Task("R", 6);
//            Task s = new Task("S", 6);
//            Task t = new Task("T", 6);
//            Task u = new Task("U", 6);
//            Task v = new Task("V", 6);
//            Task w = new Task("W", 6);
//            Task x = new Task("X", 6);
//            Task y = new Task("Y", 6);
//            Task z = new Task("Z", 6);
//            b.addPredecessor(a);
//            e.addPredecessor(d);
//            h.addPredecessor(g);
//            c.addPredecessor(b, h);
//            f.addPredecessor(e, b);
//            i.addPredecessor(e, h);
//            j.addPredecessor(f, i);
//            k.addPredecessor(j, c);
//            l.addPredecessor(o, k);
//            m.addPredecessor(l);
//            n.addPredecessor(m, l);
//            o.addPredecessor(n);
//            p.addPredecessor(k);
//            s.addPredecessor(p, v, w);
//            t.addPredecessor(s);
//            u.addPredecessor(s);
//            v.addPredecessor(t);
//            w.addPredecessor(u);
//            q.addPredecessor(p);
//            r.addPredecessor(y, q);
//            y.addPredecessor(x);
//            x.addPredecessor(r);
//            z.addPredecessor(q);
//            Task[] tasks = {
//                    z, d, e, f, g, h, i, n, o, p, q, r, s, t, u, v, a, b, c, w, x, y, j, k, l, m
//            };
//
//            List<String> schedule = Project.findSchedule(tasks, false);
//            assert (schedule.size() == 14);
//            assert (schedule.indexOf("A") < schedule.indexOf("B"));
//            assert (schedule.indexOf("B") < schedule.indexOf("C"));
//            assert (schedule.indexOf("B") < schedule.indexOf("F"));
//            assert (schedule.indexOf("D") < schedule.indexOf("E"));
//            assert (schedule.indexOf("E") < schedule.indexOf("F"));
//            assert (schedule.indexOf("E") < schedule.indexOf("I"));
//            assert (schedule.indexOf("G") < schedule.indexOf("H"));
//            assert (schedule.indexOf("H") < schedule.indexOf("C"));
//            assert (schedule.indexOf("H") < schedule.indexOf("I"));
//            assert (schedule.indexOf("F") < schedule.indexOf("J"));
//            assert (schedule.indexOf("I") < schedule.indexOf("J"));
//            assert (schedule.indexOf("J") < schedule.indexOf("K"));
//            assert (schedule.indexOf("C") < schedule.indexOf("K"));
//            assert (schedule.indexOf("K") < schedule.indexOf("P"));
//            assert (schedule.indexOf("Q") < schedule.indexOf("Z"));
//            schedule = Project.findSchedule(tasks, true);
//            assert (schedule.size() == 26);
//            assert (schedule.indexOf("A") < schedule.indexOf("B"));
//            assert (schedule.indexOf("B") < schedule.indexOf("C"));
//            assert (schedule.indexOf("B") < schedule.indexOf("F"));
//            assert (schedule.indexOf("D") < schedule.indexOf("E"));
//            assert (schedule.indexOf("E") < schedule.indexOf("F"));
//            assert (schedule.indexOf("E") < schedule.indexOf("I"));
//            assert (schedule.indexOf("G") < schedule.indexOf("H"));
//            assert (schedule.indexOf("H") < schedule.indexOf("C"));
//            assert (schedule.indexOf("H") < schedule.indexOf("I"));
//            assert (schedule.indexOf("F") < schedule.indexOf("J"));
//            assert (schedule.indexOf("I") < schedule.indexOf("J"));
//            assert (schedule.indexOf("J") < schedule.indexOf("K"));
//            assert (schedule.indexOf("C") < schedule.indexOf("K"));
//            assert (schedule.indexOf("K") < schedule.indexOf("P"));
//            assert (schedule.indexOf("Q") < schedule.indexOf("Z"));
//            assert (schedule.indexOf("L") < schedule.indexOf("M"));
//            assert (schedule.indexOf("M") < schedule.indexOf("N"));
//            assert (schedule.indexOf("N") < schedule.indexOf("O"));
//            assert (schedule.indexOf("S") < schedule.indexOf("T"));
//            assert (schedule.indexOf("S") < schedule.indexOf("T"));
//            assert (schedule.indexOf("T") < schedule.indexOf("V"));
//            assert (schedule.indexOf("U") < schedule.indexOf("W"));
//        }
//
//        {
//            Task a = new Task("A", 12);
//            Task b = new Task("B", 7);
//            Task c = new Task("C", 10);
//            Task d = new Task("D", 9);
//            Task e = new Task("E", 8);
//            Task f = new Task("F", 11);
//            b.addPredecessor(a, e);
//            c.addPredecessor(b);
//            d.addPredecessor(c);
//            e.addPredecessor(d, f);
//            f.addPredecessor(c);
//            Task[] tasks = new Task[]{ a, b, c, d, e, f };
//            List<String> schedule = Project.findSchedule(tasks, true);
//            System.out.println("Test Case by fergcd");
//            for (String title : schedule) {
//                System.out.println(title);
//            }
//            assert (schedule.size() == 6);
//            assert (schedule.get(0).equals("A"));
//            assert (schedule.get(1).equals("B"));
//            assert (schedule.get(2).equals("C"));
//            assert (schedule.indexOf("C") < schedule.indexOf("D"));
//            assert (schedule.indexOf("C") < schedule.indexOf("F"));
//            assert (schedule.indexOf("D") < schedule.indexOf("E"));
//            assert (schedule.indexOf("F") < schedule.indexOf("E"));
//        }
//
//        {
//            Task a = new Task("A", 15);
//            Task b = new Task("B", 12);
//            Task c = new Task("C", 11);
//
//            c.addPredecessor(b);
//            b.addPredecessor(a);
//
//            Task[] tasks = new Task[]{b, c, a};
//
//            List<String> schedule = Project.findSchedule(tasks, false);
//
//            assert (schedule.size() == 3);
//            assert (schedule.get(0).equals("A"));
//            assert (schedule.get(1).equals("B"));
//            assert (schedule.get(2).equals("C"));
//        }

        {
            Task[] tasks = createTasks();

            List<String> schedule = Project.findSchedule(tasks, false);

            assert (schedule.size() == 6);
            assert (schedule.get(0).equals("A"));
            assert (schedule.get(1).equals("B"));
            assert (schedule.get(2).equals("C"));
            assert (schedule.get(3).equals("E"));
            assert (schedule.get(4).equals("F"));
            assert (schedule.get(5).equals("I"));
        }

        {
            Task[] tasks = createTasks();

            List<String> schedule = Project.findSchedule(tasks, true);

            assert (schedule.size() == 9);
            assert (schedule.get(0).equals("A"));
            assert (schedule.get(1).equals("B"));
            assert (schedule.get(2).equals("C"));
            assert (schedule.indexOf("C") < schedule.indexOf("E"));
            assert (schedule.indexOf("E") < schedule.indexOf("F"));
            assert (schedule.indexOf("F") < schedule.indexOf("I"));

            assert (schedule.indexOf("C") < schedule.indexOf("D"));
            assert (schedule.indexOf("D") < schedule.indexOf("G"));
            assert (schedule.indexOf("G") < schedule.indexOf("H"));
        }
    }

    private static Task[] createTasks() {
        Task a = new Task("A", 12);
        Task b = new Task("B", 7);
        Task c = new Task("C", 10);
        Task d = new Task("D", 9);
        Task e = new Task("E", 8);
        Task f = new Task("F", 11);
        Task g = new Task("G", 14);
        Task h = new Task("H", 13);
        Task i = new Task("I", 6);

        i.addPredecessor(f);
        f.addPredecessor(e);
        e.addPredecessor(b, c);
        d.addPredecessor(c, h);
        c.addPredecessor(b);
        b.addPredecessor(a);
        h.addPredecessor(g);
        g.addPredecessor(d);

        return new Task[]{
                a, b, c, d, e, f, g, h, i
        };
    }
}