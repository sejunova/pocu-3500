package academy.pocu.comp3500.assignment2;

import academy.pocu.comp3500.assignment2.datastructure.ArrayList;

public final class Indent {
    final String padding;
    ArrayList<String> logs = new ArrayList<>();

    public Indent(int indentCount) {
        this.padding = new String(new char[indentCount * 2]).replace('\0', ' ');
    }

    public void discard() {
        logs = null;
    }
}
