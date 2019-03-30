package CarlsonProject.plot;

import CarlsonProject.UserHandler;
import CarlsonProject.WindowsArrayList;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        String fileName = System.getenv("FILENAME");
        ArrayList<Window> list = new ArrayList<Window>();
        WindowsArrayList windows = new WindowsArrayList(list);
        final String HELP = "           Команды : \n" +
                "add {element}:          |добавить новый элемент в коллекцию.\n" +
                "insert index {element}: |добавить новый элемент в коллекцию по индексу.\n" +
                "show:                   |вывести в стандартный поток вывода все элементы коллекции в строковом представлении.\n" +
                "import {path}:          |добавить в коллекцию все данные из файла.\n" +
                "save:                   |сохранить коллекцию в файл.\n" +
                "sort:                   |вывод итсортированного список элементов коллекции.\n" +
                "info:                   |вывести информацию о коллекции.\n" +
                "remove {element}:       |удалить элемент из коллекции по его значению.\n" +
                "remove_last:            |удалить последний элемент.\n" +
                "start:                  |запустить основную программу для настроенной коллекции.\n" +
                "exit:                   |выход из программы.\n" +
                "help:                   |вывод списка доступных команд.\n";

        UserHandler.getInput("Type command:", HELP, windows);

        Nurse nurse = new Nurse("Лучшая в мире");
        NewBaby baby = new NewBaby("Мистер", 2);
        NewCarlson carlson = new NewCarlson("В САМОМ РАСЦВЕТЕ СИЛ");
        NewMove move = new NewMove(carlson, baby, nurse);
    }
}
