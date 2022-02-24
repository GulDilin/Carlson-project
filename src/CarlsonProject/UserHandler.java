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

        StringBuilder sBuilder = new StringBuilder();
        for (String term_s : result.split(" ")) {
            try {
                sBuilder.append("\"").append(term_s.split(":")[0]).append("\":\"").append(term_s.split(":")[1]).append("\", ");
            } catch (ArrayIndexOutOfBoundsException e){
                    //
            }
        }
        s = sBuilder.toString();
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
        final String[] SET_VALUES = new String[]{"save", "info", "sort", "add", "insert",
                "import", "help", "show", "remove", "remove_last", "start", "exit", "check"};
        final Set<String> COMMAND_NAMES = new HashSet<>(Arrays.asList(SET_VALUES));


        while (true) {
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
                        command = new RemoveCommand( fromUserStringToJSONString(line));
                    } catch (Exception e) {
                        System.out.println("No element");
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

                        command = new InsertCommand( fromUserStringToJSONString(line));
                    } catch (ArrayIndexOutOfBoundsException e) {
                        System.out.println("No element error");
                    }
                    break;

                case "check":
                    command = new ConnectCommand();
                    break;

                case "add":
                    try {
                        if ((line.replaceAll(" ", "").equals(""))){
                            System.out.println("No element error");
                        }
                        command = new AddCommand( fromUserStringToJSONString(line));
                    } catch (ArrayIndexOutOfBoundsException e) {
                        System.out.println("No element error");
                    }
                    break;

                case "exit":
                    command = new ExitCommand();
                    return command;
            }

            if (command != null) {
                return command;
            }
        }
    }
}
