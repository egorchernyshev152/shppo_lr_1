package model;

public abstract class PanelElement {
    private final int x;
    private final int y;

    public PanelElement(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

}
