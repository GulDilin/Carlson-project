package CarlsonProject;

import java.io.Serializable;
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

public class UserHandler implements Serializable {

    /**
     * convert string to JSON string
     *
     * @param s String
     * @return String line, contains JSON object
     */
    public static String fromUserStringToJSONString( String s) {
        String result = s.toLowerCase().trim()
                .replaceAll("[\\s]{2,}", " ")
                .replaceAll(" :", ":")
                .replaceAll(": ", ":");
        s = "";

        for (String term_s : result.split(" ")) {
            try {
                s += "\"" + term_s.split(":")[0] + "\":\"" + term_s.split(":")[1] + "\", ";
            } catch (ArrayIndexOutOfBoundsException e){
                    //
            }
        }
        result = s.trim()
                .replace("\"c\":", "\"color\":")
                .replace("\"sc\":", "\"speakChance\":")
                .replace("\"oc\":", "\"openChance\":")
                .replace("\"hc\":", "\"holeChance\":")
                .replace("\"rc\":", "\"robberChance\":");
        if (result.endsWith(",")){
            result = result.substring(0, result.length()-1);
        }
        return "{" + result.replaceAll(" ","") + "}";
    }

    /**
     *
     * @param message string with user command
     * @param HELP String contains text with command names
     */
    public static Command getInput(String message, String HELP, String defaultFileName) {
        Command command = null;
        boolean flag = true;
        final String[] SET_VALUES = new String[]{"save", "info", "sort", "add", "insert",
                "import", "help", "show", "remove", "remove_last", "start", "exit", "check"};
        final Set<String> COMMAND_NAMES = new HashSet<>(Arrays.asList(SET_VALUES));


        while (flag) {
            System.out.print(message);
            Scanner in = new Scanner(System.in);
            String[] s = in.nextLine().split(" ", 2);
            while (!COMMAND_NAMES.contains(s[0])) {
                System.out.println("No such command");
                System.out.print(message);
                s = in.nextLine().trim().split(" ", 2);
            }

            String line = "";
            try{
                line = s[1];
            } catch (ArrayIndexOutOfBoundsException e){
                //ignore
            }

            switch (s[0]) {
                case "remove":
                    try {
<<<<<<< HEAD
<<<<<<< HEAD
                        command = new RemoveCommand( fromUserStringToJSONString(line));
                    } catch (Exception e) {
                        System.out.println("No element");
=======
                        command = new RemoveCommand(windows, fromUserStringToJSONString(line));
                    } catch (NoElementException e) {
                        System.out.println(e.getMessage());
>>>>>>> e9052eb61c13f1c19208975e52ea4f53602f6324
=======
                        command = new RemoveCommand(windows, fromUserStringToJSONString(line));
                    } catch (NoElementException e) {
                        System.out.println(e.getMessage());
>>>>>>> e9052eb61c13f1c19208975e52ea4f53602f6324
                    }
                    break;

                case "start":
                    Nurse nurse = new Nurse("Лучшая в мире");
                    NewBaby baby = new NewBaby("Мистер", 2);
                    NewCarlson carlson = new NewCarlson("В САМОМ РАСЦВЕТЕ СИЛ");
                    command = new StartCommand( nurse, carlson, baby);
                    break;

                case "save":
                    if (line.equals("")) {
                        command = new SaveCommand(defaultFileName);
                    } else {
                        command = new SaveCommand( line);
                    }
                    break;

                case "import":
                    try {
                        command = new ImportCommand( line);
                    } catch (ArrayIndexOutOfBoundsException e1) {
                        command = new ImportCommand(defaultFileName);

                    }
                    break;

                case "help":
                    command = new HelpCommand(HELP, line);
                    break;

                case "remove_last":
                    command = new RemoveLastCommand();
                    break;

                case "sort":
                    command = new SortCommand();
                    break;

                case "info":
                    command = new InfoCommand();
                    break;

                case "show":
                    command = new ShowCommand();
                    break;

                case "insert":
                    try {
<<<<<<< HEAD
<<<<<<< HEAD
                        command = new InsertCommand( fromUserStringToJSONString(line));
                    } catch (ArrayIndexOutOfBoundsException e) {
                        System.out.println("No element error");
=======
                        command = new InsertCommand(windows, line);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
>>>>>>> e9052eb61c13f1c19208975e52ea4f53602f6324
=======
                        command = new InsertCommand(windows, line);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
>>>>>>> e9052eb61c13f1c19208975e52ea4f53602f6324
                    }
                    break;

                case "check":
                    command = new ConnectCommand();
                    break;

                case "add":
                    try {
<<<<<<< HEAD
<<<<<<< HEAD
                        if ((line.replaceAll(" ","") == "")){
                            System.out.println("No element error");
                        }
                        command = new AddCommand( fromUserStringToJSONString(line));
                    } catch (ArrayIndexOutOfBoundsException e) {
                        System.out.println("No element error");
=======
                        command = new AddCommand(windows, fromUserStringToJSONString(line));
                    } catch (NoElementException e) {
                        System.out.println(e.getMessage());
>>>>>>> e9052eb61c13f1c19208975e52ea4f53602f6324
=======
                        command = new AddCommand(windows, fromUserStringToJSONString(line));
                    } catch (NoElementException e) {
                        System.out.println(e.getMessage());
>>>>>>> e9052eb61c13f1c19208975e52ea4f53602f6324
                    }
                    break;

                case "exit":
                    flag = false;
                    command = new ExitCommand();
            }

            if (command != null) {
                return command;
            }
        }
        return null;
    }
}
