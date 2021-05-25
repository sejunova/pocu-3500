package academy.pocu.comp3500.assignment2;

import academy.pocu.comp3500.assignment2.datastructure.ArrayList;

//new String(new char[indentCount * 2]).replace('\0', ' ')
public final class Indent {
    boolean discarded = false;
    Indent parent;
    String text;
    ArrayList<ArrayList<Indent>> children = new ArrayList<>();

    public Indent(Indent parent, String text) {
        this.parent = parent;
        this.text = text;
        children.add(new ArrayList<>());
    }

    public void discard() {
        discarded = true;
    }

    @Override
    public String toString() {
        return "Indent{" +
                "text='" + text + '\'' +
                '}';
    }
}
