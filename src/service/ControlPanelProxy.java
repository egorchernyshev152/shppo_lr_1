package service;

public interface ControlPanelProxy {
    void visualize();
    void pressButton(int x, int y);
    void requestLampBinding();
    void requestLampUnlink();
}
