package model;

public class Lamp extends PanelElement implements ButtonObserver {
    private boolean active;
    private final String color;

    public Lamp(int x, int y, String color) {
        super(x, y);
        this.color = color;
        this.active = false;
    }


    public String getColor() {
        return color;
    }

    public boolean isActive() {
        return active;
    }


    @Override
    public void buttonClicked() {
        this.active = !active;
        System.out.println(String.format("Получено событие через наблюдателя. Лампа %s %s перешла в состояние active = %s", getX(),getY(),active));
    }
}
