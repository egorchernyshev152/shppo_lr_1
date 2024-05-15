package service;

import model.Lamp;

public interface LampFactory {
    Lamp createLamp(int x, int y, String color);
}

