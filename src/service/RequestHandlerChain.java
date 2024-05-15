package service;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class RequestHandlerChain {
    private final List<RequestHandler> handlers = new ArrayList<>();

    public void addHandler(RequestHandler handler) {
        handlers.add(handler);
    }

    public void handleRequest(int choice, ControlPanelProxy proxy, Scanner scanner) {
        for (RequestHandler handler : handlers) {
            handler.handleRequest(choice, proxy, scanner);
        }
    }
}
