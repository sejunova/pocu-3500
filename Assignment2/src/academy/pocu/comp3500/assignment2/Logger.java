package academy.pocu.comp3500.assignment2;

import academy.pocu.comp3500.assignment2.datastructure.ArrayList;

import java.io.BufferedWriter;
import java.io.IOException;

public final class Logger {
    private static ArrayList<Indent> indents = new ArrayList<>();
    private static int curIndent = 0;

    public static void log(final String text) {
        if (indents.getSize() == 0) {
            clear();
        }
        indents.get(indents.getSize() - 1).logs.add(text);
    }

    public static void printTo(final BufferedWriter writer) throws IOException {
        for (Indent indent : indents) {
            if (indent.logs != null) {
                for (String log : indent.logs) {
                    writer.write(String.format("%s%s%s", indent.padding, log, "\r\n"));
                }
            }
        }
        writer.flush();
    }

    public static void printTo(final BufferedWriter writer, final String filter) throws IOException {
        for (Indent indent : indents) {
            if (indent.logs != null) {
                for (String log : indent.logs) {
                    if (log.contains(filter)) {
                        writer.write(String.format("%s%s%s", indent.padding, log, "\r\n"));
                    }
                }
            }
        }
        writer.flush();
    }

    public static void clear() {
        curIndent = 0;
        indents.clear();
        indents.add(new Indent(curIndent));
    }

    public static Indent indent() {
        Indent indent = new Indent(++curIndent);
        indents.add(indent);
        return indent;
    }

    public static void unindent() {
        Indent indent = new Indent(--curIndent);
        indents.add(indent);
    }
}
