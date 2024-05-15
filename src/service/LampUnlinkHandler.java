package service;

import java.util.Scanner;

public class LampUnlinkHandler implements RequestHandler {

    @Override
    public void handleRequest(int choice, ControlPanelProxy proxy, Scanner scanner) {
        if (choice == 3) {
            proxy.requestLampUnlink();
            System.out.println("Новое состояние панели после отвязки:");
            proxy.visualize();
        } else {
            System.out.println("Передача запроса следующему обработчику...");
        }
    }
}