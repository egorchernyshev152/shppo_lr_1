package service;

import java.util.Scanner;

public interface RequestHandler {
    void handleRequest(int choice, ControlPanelProxy proxy, Scanner scanner);
}