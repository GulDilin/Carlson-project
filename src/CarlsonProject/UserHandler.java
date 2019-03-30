package CarlsonProject;

import java.util.Set;
import java.util.HashSet;
import java.util.Arrays;
import java.util.Scanner;
import CarlsonProject.commands.*;
import CarlsonProject.plot.NewBaby;
import CarlsonProject.plot.NewCarlson;
import CarlsonProject.plot.Nurse;

/**
 * Class for interact with user input
 *
 * @author Evgeny Gurin
 * @version 1.0
 *
 */

public class UserHandler {

    /**
     *
     * @param message string with user command
     * @param HELP String contains text with command names
     * @param windows collection
     */
    public static void getInput(String message, String HELP, WindowsArrayList windows, String defaultFileName) {
        Command command = null;
        boolean flag = true;
        final String[] SET_VALUES = new String[]{"save", "info", "sort", "add",
                "import", "help", "show", "remove", "remove_last", "start", "exit"};
        final Set<String> COMMAND_NAMES = new HashSet<>(Arrays.asList(SET_VALUES));

        System.out.print(HELP);
        while (flag) {
            System.out.print(message);
            Scanner in = new Scanner(System.in);
            String[] s = in.nextLine().split(" ", 2);
            while (!COMMAND_NAMES.contains(s[0])) {
                System.out.println("No such command");
                System.out.print(message);
                s = in.nextLine().split(" ", 2);
            }

            switch (s[0]) {
                case "remove":
                    try {
                        command = new RemoveCommand(windows, s[1]);

                    } catch (Exception e) {
                        System.out.println("No element");
                    }
                    break;

                case "start":

                    Nurse nurse = new Nurse("Лучшая в мире");
                    NewBaby baby = new NewBaby("Мистер", 2);
                    NewCarlson carlson = new NewCarlson("В САМОМ РАСЦВЕТЕ СИЛ");
                    command = new StartCommand(windows, nurse, carlson, baby);
                    break;

                case "save":
                    if (s[1] == null) {
                        command = new SaveCommand(windows, defaultFileName);
                    } else {
                        command = new SaveCommand(windows, s[1]);
                    }
                    break;

                case "import":
                    try {
                        command = new ImportCommand(windows, s[1]);
                    } catch (ArrayIndexOutOfBoundsException e1) {
                        //System.out.println("File not found");
                        command = new ImportCommand(windows, defaultFileName);

                    }
                    break;

                case "help":
                    System.out.println(HELP);
                    break;

                case "remove_last":
                    command = new RemoveLastCommand(windows);
                    break;

                case "sort":
                    command = new SortCommand(windows);
                    break;

                case "info":
                    command = new InfoCommand(windows);
                    ;
                    break;

                case "show":
                    command = new ShowCommand(windows);
                    break;
                case "insert":
                    try {
                        command = new InsertCommand(windows, s[1]);
                    } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("No element error");
            }
                case "add":
                    try {
                        command = new AddCommand(windows, s[1]);
                        System.out.println("Element added");
                    } catch (ArrayIndexOutOfBoundsException e) {
                        System.out.println("No element error");
                    }
                    break;

                case "exit":
                    flag = false;
                    windows.save();
            }

            if (command != null) {
                command.execute();
            }
        }
    }
}
