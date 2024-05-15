package service;

import service.*;

import java.util.Scanner;

public class Menu {
    public static int requestIntegerInput(Scanner scanner, String message) {
        System.out.println(message);
        while (!scanner.hasNextInt()) {
            System.out.println("Неверный ввод. Пожалуйста, введите целое число:");
            scanner.next(); // Пропускаем неверный ввод
        }
        return scanner.nextInt();
    }
    public static void menu() {
        System.out.println("Добро пожаловать в программу управления!");
        System.out.println("                /\\_/\\");
        System.out.println("               ( o.o )");
        System.out.println("                > ^ <");
        System.out.println();

        Scanner scanner = new Scanner(System.in);

        int width = requestIntegerInput(scanner, "Введите ширину панели:");
        int height = requestIntegerInput(scanner, "Введите высоту панели:");

        ControlPanel controlPanel = new ControlPanel(width, height);
        ControlPanelProxyImpl proxy = new ControlPanelProxyImpl(controlPanel);

        System.out.println("Сгенерирована панель управления:");
        proxy.visualize();

        RequestHandlerChain handlerChain = new RequestHandlerChain();
        handlerChain.addHandler(new ButtonPressHandler());
        handlerChain.addHandler(new LampBindingHandler());
        handlerChain.addHandler(new LampUnlinkHandler());

        while (true) {
            System.out.println("          Меню:");
            System.out.println("1. Нажать на кнопку");
            System.out.println("2. Привязать кнопку к лампе");
            System.out.println("3. Отвязать кнопку от лампы");
            System.out.println("4. Выйти");

            int choice = requestIntegerInput(scanner, "Ваше действие:");
            handlerChain.handleRequest(choice, proxy, scanner);

            if (choice == 4) {
                System.out.println("До свидания!");
                proxy.shutdown();
                break;
            }
        }
    }

}

