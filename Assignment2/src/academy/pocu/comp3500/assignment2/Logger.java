package academy.pocu.comp3500.assignment2;

import academy.pocu.comp3500.assignment2.datastructure.ArrayList;

import java.io.BufferedWriter;
import java.io.IOException;

public final class Logger {
    static Indent root = new Indent(null, null);
    static Indent cur = root;

    public static void log(final String text) {
        // Children의 마지막 ArrayList에 계속 추가
        cur.children.get(cur.children.getSize() - 1).add(new Indent(cur, text));
    }

    public static void printTo(final BufferedWriter writer) throws IOException {
        for (ArrayList<Indent> indents : root.children) {
            printRecursive(writer, indents, 0, null);
        }
        writer.flush();
    }

    public static void printRecursive(final BufferedWriter writer, ArrayList<Indent> indents, int indentCount, String filter) throws IOException {
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
            for (ArrayList<Indent> indentsList : indent.children) {
                printRecursive(writer, indentsList, indentCount + 1, filter);
            }
        }
    }

    public static void printTo(final BufferedWriter writer, final String filter) throws IOException {
        for (ArrayList<Indent> indents : root.children) {
            printRecursive(writer, indents, 0, filter);
        }
        writer.flush();
    }

    public static void clear() {
        root = new Indent(null, null);
        cur = root;
    }

    public static Indent indent() {
        if (cur.children.get(cur.children.getSize() - 1).getSize() == 0) {
            cur.children.get(cur.children.getSize() - 1).add(new Indent(cur, null));
        }
        cur = cur.children.get(cur.children.getSize() - 1).get(cur.children.get(cur.children.getSize() - 1).getSize() - 1);
        if (cur.children.get(cur.children.getSize() - 1).getSize() != 0) {
            cur.children.add(new ArrayList<>());
        }
        Indent indent = new Indent(cur, null);
        cur.children.get(cur.children.getSize() - 1).add(indent);
        return indent;
    }

    public static void unindent() {
        cur = cur.parent;
    }
}
