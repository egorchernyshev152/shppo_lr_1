package service;

import java.util.Scanner;

public class LampBindingHandler implements RequestHandler {

    @Override
    public void handleRequest(int choice, ControlPanelProxy proxy, Scanner scanner) {
        if (choice == 2) {
            proxy.requestLampBinding();
            System.out.println("Новое состояние панели после привязки:");
            proxy.visualize();
        } else {
            System.out.println("Передача запроса следующему обработчику...");
        }
    }
}