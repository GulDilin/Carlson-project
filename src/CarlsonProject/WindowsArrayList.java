package CarlsonProject;

import java.util.*;
import java.io.*;

import CarlsonProject.plot.Color;
import CarlsonProject.plot.NoSuchColorException;
import CarlsonProject.plot.Window;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class WindowsArrayList {
    /**
     * @author Evgeny Gurin
     * @version 1.0
     */
    private ArrayList<Window> windows;
    private Date date;
    private String defaultFileName = "C:\\Users\\zheny\\Documents\\Progs\\Carlson-project\\src\\default.json";
    private static HashMap<String, Color> colorMap;
    static {
        colorMap = new HashMap<String, Color>();
        colorMap.put("green", Color.GREEN);
        colorMap.put("yellow", Color.YELLOW);
        colorMap.put("blue", Color.BLUE);
        colorMap.put("red", Color.RED);
    }

    /**
     * @param windows collection
     */
    public WindowsArrayList(ArrayList<Window> windows){
        this.windows = windows;
        this.date = new Date();
        this.defaultFileName = System.getenv("FILENAME");
    }


    private static Color getColor(String colorName) throws NoSuchColorException {
        Color color;
        if (colorName.equalsIgnoreCase("green")) {
            color = colorMap.get("green");
        } else if (colorName.equalsIgnoreCase("yellow")) {
            color = colorMap.get("yellow");
        } else if (colorName.equalsIgnoreCase("red")) {
            color = colorMap.get("red");
        } else if (colorName.equalsIgnoreCase("blue")) {
            color = colorMap.get("blue");
        } else {
            throw new NoSuchColorException("Ошибка в названии цвета");
        }
        return color;
    }

    /**
     *
     * @return info about collection (Initialization date, size, Type)
     */
    public String getInfo(){
        return "Дата создания: " + this.date.toString() +
                "\nТип: " + this.getClass() +
                "\nРазмер: " + windows.size();
    }

    /**
     * method for add new elem in collection
     * @param window new elem
     */
    public void add(Window window){
        if (this.windows.add(window)){
            System.out.println("Element was added");
        } else {
            System.out.println("Add error");
        }
//         save(defaultFileName);
    }

    /**
     * sorting method
     */
    public void sort(){
        this.windows.sort((Window window, Window otherwindow) ->
                Double.compare(window.getChanceSum(),
                        otherwindow.getChanceSum() ));
    }

    public void add(int index, Window window){
        this.windows.add(index, window);
    }

    public int size(){
        return windows.size();
    }

    public void removeLast()
    {
        double minSum = -1.0D;
        for(Window w: windows){
            if (minSum == -1.0D) {
                minSum = w.getChanceSum();
            }
            if(w.getChanceSum() < minSum){
                minSum = w.getChanceSum();
            }
        }
        final double MINSUM = minSum;
        windows.removeIf(window -> window.getChanceSum() == MINSUM);
    }

    /**
     *
     * @param win window, which need to remove from collection
     */
    public void remove(Window win){
        try {
            if (windows.removeIf(window -> window.equals(win))){
                System.out.println("Elem was deleted");
            }
        } catch (NullPointerException e){
            System.out.println("No such elem");
        }
    }

//    public Window[] toArray(){
//        return (Window[]) windows.toArray();
//    }
    public Window[] toArray(){
        Window[] array = new Window[windows.size()];
        windows.forEach(window -> array[windows.indexOf(window)] = window);
        return array;
    }

    public void show(){
        this.windows.forEach(window -> System.out.println(window.toString()));
    }

    /**
     *
     * @param s string, which contains JSON Obj
     * @return JSON object from string s (only one obj)
     */
    public static JSONObject fromStringToJSONObject(String s){
        JSONParser parcer;
        JSONObject object = null;
        parcer = new JSONParser();
        try {
            object = (JSONObject) parcer.parse(s);
        } catch (ParseException parsE){
            System.out.println("Ошибка получения объекта окна");
        }
        return object;
    }

    /**
     *
     * @param object JSONObject (window)
     * @return window obj from JSON obj
     */
    public static Window fromJSONToWindow(JSONObject object) throws NullPointerException{
        Window window = null;
        try {
            Color color = getColor((String)object.get("color"));
            Window.Builder windowBuilder = new Window.Builder(color);
            if (object.get("speakChance") != null){
                try {
                    windowBuilder.speakChance(Double.parseDouble((String) object.get("speakChance")));
                } catch (NumberFormatException e){
                    System.out.println("Неверный формат числа");
                }
            }
            if (object.get("holeChance") != null){
                try {
                    windowBuilder.holeChance(Double.parseDouble((String)object.get("holeChance")));
                } catch (NumberFormatException e){
                    System.out.println("Неверный формат числа");
                }
            }
            if (object.get("robberChance") != null){
                try{
                    windowBuilder.robberChance(Double.parseDouble((String)object.get("robberChance")));
                } catch (NumberFormatException e){
                    System.out.println("Неверный формат числа");
                }
            }
            if (object.get("openChance") != null){
                try{
                    windowBuilder.openChance(Double.parseDouble((String)object.get("openChance")));
                }  catch (NumberFormatException e) {
                    System.out.println("Неверный формат числа");
                }
            }
            return windowBuilder.build();
        } catch (NoSuchColorException colorE){
            System.out.println(colorE.getMessage());
        } catch (NullPointerException ex){
            System.out.println( ex.getMessage());
        }
        return window;
    }

    /**
     *
     * @param s string, which contains one or more windows
     * @return array of JSON objects
     */
    public static JSONObject[] fromStringToJSONObjects(String s){
        final int LEN = s.split("},").length;
        JSONObject objects[] = new JSONObject[LEN];
        int i = 0;
        for (String str: s.split("},")){
            if (!str.contains("}")){
                str += "}";
            }
            objects[i] = fromStringToJSONObject(str);
            i++;
        }
        return objects;
    }

    /**
     * save collection into JSON file
     * @param fileName name of file, where collection will be saved
     */
    public void save(String fileName){
        try {
            File file = new File(fileName);
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                String str = "[ \n";
                for (Window window : windows) {
                    str += window.toString();
                    if (windows.indexOf(window) != (windows.size() - 1)) {
                        str += ", \n";
                    } else {
                        str += " \n";
                    }
                }
                str += "]";
                writer.write(str);
                writer.close();
            } catch (IOException e) {
                System.out.println("Saved was not succeded");
            }
        }catch (NullPointerException ex){
            System.out.println("Incorrect fileName "+ fileName);
        }

    }

    /**
     * method to save to default file;
     */
    public void save(){
        this.save(defaultFileName);
    }

    /**
     *
     * @param fileName name of input file
     * get collection from JSON file
     */
    public void importFromFile(String fileName) {
        File file = new File(fileName);
        System.out.println("Try to import collection from file " + fileName);
        String content = "";
        FileReader reader = null;
        try {
            reader = new FileReader(file);
            windows.removeAll(windows);
            char[] chars = new char[(int) file.length()];

            try {
                reader.read(chars);
                content = new String(chars);
                content = content.replaceAll("[\\[\\]]", "");
                JSONObject objects[] = fromStringToJSONObjects(content);
                for(JSONObject object: fromStringToJSONObjects(content)){
                    add(fromJSONToWindow(object));
                }
                System.out.println("Collection was imported from file " + fileName);
            }

            catch (IOException ex){
                System.out.println(ex.getMessage());
            }
        }catch (FileNotFoundException e){
//            if (fileName != "../default.json") {
//                System.out.println("Нет такого файла, коллекция будет загружена из default.json");
//                importFromFile("../default.json");
//            } else {
                System.out.println("No such file!");
//            }
        } finally {
            if (reader != null){
                try {
                    reader.close();
                } catch (IOException ex){
                    //ignore
                }
            }
        }
    }
}
