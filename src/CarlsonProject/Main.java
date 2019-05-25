package CarlsonProject;

import CarlsonProject.commands.Command;
import CarlsonProject.commands.ExitCommand;
import CarlsonProject.plot.*;

import java.util.concurrent.CopyOnWriteArrayList;

public class Main {
    public static void main(String[] args) {
<<<<<<< HEAD
<<<<<<< HEAD
//        String fileName = System.getenv("FILENAME");
        Command command;
        boolean exit = false;
        String fileName = "C:\\Users\\zheny\\Documents\\Progs\\Carlson-project\\src\\default.json";
        fileName = "src/default.json";
        CopyOnWriteArrayList<Window> list = new CopyOnWriteArrayList<>();
=======
=======
>>>>>>> e9052eb61c13f1c19208975e52ea4f53602f6324
        String fileName;
        //fileName = "src/default.json";
        ArrayList<Window> list = new ArrayList<Window>();
>>>>>>> e9052eb61c13f1c19208975e52ea4f53602f6324
        WindowsArrayList windows = new WindowsArrayList(list);
        try{
            fileName = System.getenv("FILENAME");
            windows.importFromFile(fileName);
        } catch (NullPointerException e){
            System.out.println("Env get error");
        }

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
<<<<<<< HEAD
<<<<<<< HEAD
                "help:                   |вывод списка доступных команд.\n";
        System.out.print(HELP);
        while (!exit) {
            command = UserHandler.getInput("Type command:", HELP, fileName);
            if (command != null) {
                try {
                    exit = ((ExitCommand) command).isExit();
                } catch (ClassCastException e) {
                }
                    if(windows == null){
                        System.out.println("Null Window Error");
                    }
                    command.execute(windows);
                }

        }
=======
                "help {command}:         |вывод списка доступных команд / инструкции по использованию команды.\n";

        UserHandler.getInput("Type command:", HELP, windows, fileName = "");
>>>>>>> e9052eb61c13f1c19208975e52ea4f53602f6324
=======
                "help {command}:         |вывод списка доступных команд / инструкции по использованию команды.\n";

        UserHandler.getInput("Type command:", HELP, windows, fileName = "");
>>>>>>> e9052eb61c13f1c19208975e52ea4f53602f6324
    }
}
