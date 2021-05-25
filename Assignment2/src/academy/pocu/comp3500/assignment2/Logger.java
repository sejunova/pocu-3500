package academy.pocu.comp3500.assignment2;

import academy.pocu.comp3500.assignment2.datastructure.LinkedList;

import java.io.BufferedWriter;
import java.io.IOException;

public final class Logger {
    static Indent root = new Indent(null, null);
    static Indent cur = root;

    public static void log(final String text) {
        // Children의 마지막 LinkedList에 계속 추가
        cur.children.getLast().add(new Indent(cur, text));
    }

    public static void printTo(final BufferedWriter writer) throws IOException {
        for (LinkedList<Indent> indents : root.children) {
            printRecursive(writer, indents, 0, null);
        }
        writer.flush();
    }

    public static void printRecursive(final BufferedWriter writer, LinkedList<Indent> indents, int indentCount, String filter) throws IOException {
        for (Indent indent : indents) {
            if (indent.discarded) {
                break;
            }

            if (indent.text != null) {
                if (filter == null) {
                    writer.write(new String(new char[indentCount * 2]).replace('\0', ' ') + indent.text + "\r\n");
                } else {
                    if (indent.text.contains(filter)) {
                        writer.write(new String(new char[indentCount * 2]).replace('\0', ' ') + indent.text + "\r\n");
                    }
                }
            }
            for (LinkedList<Indent> indentsList : indent.children) {
                printRecursive(writer, indentsList, indentCount + 1, filter);
            }
        }
    }

    public static void printTo(final BufferedWriter writer, final String filter) throws IOException {
        for (LinkedList<Indent> indents : root.children) {
            printRecursive(writer, indents, 0, filter);
        }
        writer.flush();
    }

    public static void clear() {
        root = new Indent(null, null);
        cur = root;
    }

    public static Indent indent() {
        if (cur.children.getLast().getSize() == 0) {
            cur.children.getLast().add(new Indent(cur, null));
        }
        cur = cur.children.getLast().getLast();
        if (cur.children.getLast().getSize() != 0) {
            cur.children.add(new LinkedList<>());
        }
        Indent indent = new Indent(cur, null);
        cur.children.getLast().add(indent);
        return indent;
    }

    public static void unindent() {
        cur = cur.parent;
    }
}
