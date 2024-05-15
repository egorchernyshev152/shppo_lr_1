package model;

import java.util.ArrayList;
import java.util.List;

public class Button extends PanelElement {
    private boolean pressed;

    private final List<ButtonObserver> observers;

    public Button(int x, int y) {
        super(x, y);
        this.pressed = false;
        this.observers = new ArrayList<>();
    }

    // Добавляем метод для регистрации наблюдателя
    public void registerObserver(ButtonObserver observer) {
        if (!observers.contains(observer)) {
            observers.add(observer);
            System.out.println(String.format("К кнопке добавлен новый наблюдатель! Количество наблюдателей кнопки %s %s = %s", getX(), getY(), observers.size()));
        } else {
            System.out.println("Эта кнопка уже привязана к лампе.");
        }
    }

    // Добавляем метод для оповещения наблюдателей
    private void notifyObservers() {
        for (ButtonObserver observer : observers) {
            observer.buttonClicked();

        }
    }

    public void press() {
        this.pressed = true;
        notifyObservers(); // Оповещаем наблюдателей о нажатии кнопки

    }

    public void release() {
        this.pressed = false;
        notifyObservers();

    }
    public boolean isRegisteredObserver(Lamp lamp) {
        return observers.contains(lamp);
    }

    public boolean isPressed() {
        return pressed;
    }

    public List<ButtonObserver> getLamps() {
        return observers;
    }
}