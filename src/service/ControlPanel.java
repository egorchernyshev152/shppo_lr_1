package service;

import model.Button;
import model.Lamp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import static service.Menu.requestIntegerInput;

//todo обсервер не более 1 раза , меню отдельный класс и прокси и из мейна туда методы,нормальный прокси, найти структырный паттерн
public class ControlPanel implements ControlPanelProxy{ // version 1
    private final int width;
    private final int height;
    private final List<Button> buttons;
    private final List<Lamp> lamps;

    public Button createButton(int x, int y) {
        return ButtonBuilder.builder().setX(x).setY(y).build();
    }
    public ControlPanel(int width, int height) {
        this.width = width;
        this.height = height;
        this.buttons = new ArrayList<>();
        this.lamps = new ArrayList<>();
        LampFactory lampFactory = LampFactoryImpl.getInstance(); // Используем service.LampFactory

        Random random = new Random();

        // Генерация кнопок
        for (int y = 1; y <= height; y++) {
            for (int x = 1; x <= width; x++) {
                buttons.add(createButton(x, y));
            }
        }

        // Генерация ламп
        String[] colors = {"Красный", "Зеленый", "Синий", "Желтый", "Белый"};
        int numLamps = random.nextInt(width * height) + (width * height / 2); // Случайное количество ламп от 1 до width * height

        // Генерация ламп с случайными координатами и цветом
        for (int i = 0; i < numLamps; i++) {
            int x = random.nextInt(width) + 1; // Генерируем от 1 до ширины
            int y = random.nextInt(height) + 1; // Генерируем от 1 до высоты
            String color = colors[random.nextInt(colors.length)]; // Выбираем случайный цвет из массива colors
            lamps.add(lampFactory.createLamp(x, y, color)); // Создаем новую лампу с помощью фабрики и добавляем ее в список lamps
        }

        // Перемешивание списка кнопок
        Collections.shuffle(buttons);

        // Назначение ламп кнопкам
        int lampsPerButton = lamps.size() / buttons.size(); // Количество ламп, которые будут назначены каждой кнопке
        int lampsLeft = lamps.size() % buttons.size(); // Остаток ламп, которые не могут быть равномерно распределены по кнопкам
        int lampsAssigned = 0; // Счетчик количества назначенных ламп

        for (Button button : buttons) {
            int numLampsToAssign = lampsPerButton + (lampsLeft-- > 0 ? 1 : 0); // Количество ламп для назначения текущей кнопке
            for (int i = 0; i < numLampsToAssign && lampsAssigned < lamps.size(); i++) {
                Lamp lamp = lamps.get(lampsAssigned++); // Получаем следующую лампу из списка lamps
                button.registerObserver(lamp); // Добавляем лампу к текущей кнопке
            }
        }

    }

    public void visualize() {
        for (int y = 1; y <= height; y++) {
            for (int x = 1; x <= width; x++) {
                boolean buttonExists = false;
                String symbol = "";

                // Проверяем наличие кнопки в текущей ячейке
                for (Button button : buttons) {
                    if ((button.getX() == x) && (button.getY() == y)) { // Проверяем, соответствуют ли координаты кнопки текущим координатам ячейки
                        buttonExists = true; // Кнопка существует в этой ячейке
                        symbol = button.isPressed() ? "o" : "O"; // Определяем символ для отображения кнопки в зависимости от того, нажата кнопка или нет
                        break; // Прекращаем поиск кнопок в текущей ячейке, т.к. она уже найдена
                    }
                }

                // Проверяем наличие лампы в текущей ячейке
                for (Lamp lamp : lamps) {
                        if ((lamp.getX() == x) && (lamp.getY() == y)) { // Проверяем, соответствуют ли координаты лампы текущим координатам ячейки
                            symbol = lamp.isActive() ? "Л_" + lamp.getColor().charAt(0) : "Л_"; // Определяем символ для отображения лампы в зависимости от того, активна ли лампа или нет
                            break; // Прекращаем поиск ламп в текущей ячейке, т.к. она уже найдена
                        }
                    }

                System.out.print(buttonExists ? symbol + " " : "Л_ "); // Отображаем символ кнопки или лампы в текущей ячейке
            }
            System.out.println(); // Переходим на новую строку для следующего ряда
        }
    }


    public void pressButton(int x, int y) {

        if (isLamp(x, y)) {
            System.out.println("Выбранная ячейка является лампой. Пожалуйста, выберите другую ячейку.");
            System.out.println(" ");
            return;
        }
        if ((x < 1) || (x >= width) || (y < 1) || (y >= height)) { // Проверяем, находятся ли координаты кнопки в пределах допустимого диапазона
            System.out.println("Неверные координаты кнопки. Повторите ввод.");
            System.out.println(" ");
            return;
        }

        for (Button button : buttons) {
            if ((button.getX() == x ) && (button.getY() == y )) { // Увеличиваем x и y на 1 для сопоставления с координатами кнопки
                if (button.isPressed()) {
                    button.release(); // Если кнопка уже нажата, освобождаем ее
                } else {
                    button.press(); // Если кнопка не нажата, нажимаем ее
                }
                break;
            }
        }
    }

    public void requestLampBinding() {
        Scanner scanner = new Scanner(System.in);
        int buttonX = requestIntegerInput(scanner, "Введите координату кнопки (X):");
        int buttonY = requestIntegerInput(scanner, "Введите координату кнопки (Y):");

        Button selectedButton = null;
        for (Button button : buttons) {
            if (button.getX() == buttonX && button.getY() == buttonY) {
                selectedButton = button;
                break;
            }
        }

        if (selectedButton == null) {
            System.out.println("Кнопка с указанными координатами не найдена.");
            return;
        }

        System.out.println("Введите координаты лампы (x y):");
        int lampX = requestIntegerInput(scanner, "Введите координату лампы (X):");
        int lampY = requestIntegerInput(scanner, "Введите координату лампы (Y):");
        Lamp selectedLamp = null;
        for (Lamp lamp : lamps) {
            if (lamp.getX() == lampX && lamp.getY() == lampY) {
                selectedLamp = lamp;
                break;
            }
        }

        if (selectedLamp == null) {
            System.out.println("Лампа с указанными координатами не найдена.");
            return;
        }

        if (!selectedButton.isRegisteredObserver(selectedLamp)) {
            selectedButton.registerObserver(selectedLamp);
            System.out.println("Лампа успешно привязана к этой кнопке.");
        } else {
            System.out.println("Эта кнопка уже привязана к этой лампе.");
        }
    }


    @Override
    public void requestLampUnlink() {

    }

    private boolean isLamp(int x, int y) {
        for (Lamp lamp : lamps) {
            if (lamp.getX() == x && lamp.getY() == y) {
                return true;
            }
        }
        return false;
    }

    public void unlinkButtonFromLamp(int buttonX, int buttonY, int lampX, int lampY) {
        Button selectedButton = null;
        for (Button button : buttons) {
            if (button.getX() == buttonX && button.getY() == buttonY) {
                selectedButton = button;
                break;
            }
        }

        if (selectedButton == null) {
            System.out.println("Кнопка с указанными координатами не найдена.");
            return;
        }

        // Ищем лампу по координатам
        Lamp selectedLamp = null;
        for (Lamp lamp : lamps) {
            if (lamp.getX() == lampX && lamp.getY() == lampY) {
                selectedLamp = lamp;
                break;
            }
        }

        if (selectedLamp == null) {
            System.out.println("Лампа с указанными координатами не найдена.");
            return;
        }

        // Проверяем, привязана ли кнопка к лампе
        if (!selectedButton.isRegisteredObserver(selectedLamp)) {
            System.out.println("Эта кнопка не привязана к этой лампе.");
            return;
        }

        // Удаляем лампу из списка ламп кнопки
        selectedButton.getLamps().remove(selectedLamp);
        System.out.println("Лампа успешно отвязана от этой кнопки.");
    }
}