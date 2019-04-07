package CarlsonProject;

import CarlsonProject.UserHandler;
import CarlsonProject.WindowsArrayList;
import CarlsonProject.plot.*;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
//        String fileName = System.getenv("FILENAME");
        String fileName = "C:\\Users\\zheny\\Documents\\Progs\\Carlson-project\\src\\default.json";
        fileName = "src/default.json";
        ArrayList<Window> list = new ArrayList<Window>();
        WindowsArrayList windows = new WindowsArrayList(list);
        windows.importFromFile(fileName);
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
                "help {command}:         |вывод списка доступных команд / инструкции по использованию команды.\n";

        UserHandler.getInput("Type command:", HELP, windows, fileName);
    }
}
