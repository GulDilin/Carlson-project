package CarlsonProject;

import java.lang.reflect.Method;
import java.util.*;
import java.io.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import CarlsonProject.plot.Color;
import CarlsonProject.plot.NoSuchColorException;
import CarlsonProject.plot.Window;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class WindowsArrayList implements Serializable {
    /**
     * @author Evgeny Gurin
     * @version 1.0
     */
    private CopyOnWriteArrayList<Window> windows;
    private Date date;
    private PrintStream out;
    private String defaultFileName = "C:\\Users\\zheny\\Documents\\Progs\\Carlson-project\\src\\default.json";
    private static ConcurrentHashMap<String, Color> colorMap;

    static {
        colorMap = new ConcurrentHashMap<String, Color>();
        colorMap.put("green", Color.GREEN);
        colorMap.put("yellow", Color.YELLOW);
        colorMap.put("blue", Color.BLUE);
        colorMap.put("red", Color.RED);
    }

    /**
     * @param windows collection
     */
    public WindowsArrayList(CopyOnWriteArrayList<Window> windows) {
        this.windows = windows;
        this.out = System.out;
        this.date = new Date();
    }

    private static Color getColor(String colorName) throws NoSuchColorException {
        Color color;
        String colorNames[] = {"green", "yellow", "red", "blue"};
        if (Arrays.asList(colorNames).contains(colorName)) {
            color = colorMap.get(colorName);
        } else {
            throw new NoSuchColorException("Ошибка в названии цвета");
        }
        return color;
    }

    public void setOut(PrintStream out) {
        this.out = out;
    }

    /**
     * @return info about collection (Initialization date, size, Type)
     */
    public String getInfo() {
        return "Дата создания: " + this.date.toString() +
                "\nТип: " + this.getClass() +
                "\nРазмер: " + windows.size();
    }

    /**
     * method for add new elem to the end of collection
     *
     * @param window new elem
     */
    public void add(Window window) {
        if ((window != null) & (!windows.contains(window)) & (this.windows.add(window))) {
            out.println("Element was added");
        } else if (window == null) {
            out.println("Get null object");
        } else {
            out.println("Elem already exist");
        }
    }

    /**
     * method for add new elem with index in collection
     *
     * @param index  int index of elem
     * @param window new elem
     */
    public void add(int index, Window window) {
        if ((window != null) && (!windows.contains(window))) {
            try {
                this.windows.add(index, window);
                out.println("Element was added");
            } catch (IndexOutOfBoundsException e) {
                out.println("Wrong index. Add failed");
            }

        } else if (window == null) {
            out.println("Get null object");
        } else {
            out.println("Elem already exist. Add failed");
        }
    }

    /**
     * sorting method
     */
    public void sort() {
        this.windows.sort((Window window, Window otherwindow) ->
                window.compareTo(otherwindow));
    }

    public int size() {
        return windows.size();
    }

    public void removeLast() {
        windows.removeIf(window -> window == windows.stream().min(Window::compareTo).get());
    }

    /**
     * @param win window, which need to remove from collection
     */
    public void remove(Window win) {
        if (windows.removeIf(window -> window.equals(win))) {
            this.out.println("Elem was deleted");
        } else {
            this.out.println("No such elem");
        }
    }

    /**
     * @return Window array from collection
     */
    public Window[] toArray() {
        Window[] array = new Window[windows.size()];
        windows.forEach(window -> array[windows.indexOf(window)] = window);
        return array;
    }

    public void show() {
        this.windows.forEach(window -> this.out.println(window.toString()));
    }

    /**
     * @param s string, which contains JSON Obj
     * @return JSON object from string s (only one obj)
     */
    public static JSONObject fromStringToJSONObject(String s, PrintStream out) {
        JSONParser parcer;
        JSONObject object = null;
        parcer = new JSONParser();
        try {
            object = (JSONObject) parcer.parse(s);
        } catch (ParseException parsE) {
            out.println("Ошибка получения объекта окна");
        } catch (NullPointerException ex) {
            out.println("Get Null obj from string line");
        }
        return object;
    }

    public static JSONObject fromStringToJSONObject(String s) {
        return fromStringToJSONObject(s, System.out);
    }

    /**
     * @param object JSONObject (window)
     * @return window obj from JSON obj
     */
    public static Window fromJSONToWindow(JSONObject object, PrintStream out) throws NullPointerException {
        Window window = null;
        try {
            Color color = getColor((String) object.get("color"));
            Window.Builder windowBuilder = new Window.Builder(color);
            String[] flagnames = {"speak", "open", "hole", "robber"};

            for (String flagname : flagnames) {
                if (object.get(flagname + "Chance") != null) {
                    try {
                        Method flagChanceMethod = windowBuilder.getClass().
                                getDeclaredMethod(flagname + "Chance", Double.class);
                        flagChanceMethod.invoke(windowBuilder,
                                Double.parseDouble((String) object.get(flagname + "Chance")));
                    } catch (NumberFormatException e) {
                        out.println("Неверный формат числа");
                    } catch (ReflectiveOperationException ex) {
                        ex.printStackTrace();
                        //ignore
                    }
                }
            }
            return windowBuilder.build();
        } catch (NoSuchColorException | NullPointerException colorE) {
            out.println(colorE.getMessage());
        }
        return window;
    }

    public static Window fromJSONToWindow(JSONObject object) throws NullPointerException {
        return fromJSONToWindow(object, System.out);
    }

    /**
     * @param s string, which contains one or more windows
     * @return array of JSON objects
     */
    public static JSONObject[] fromStringToJSONObjects(String s, PrintStream out) {
        final int LEN = s.split("},").length;
        JSONObject objects[] = new JSONObject[LEN];
        int i = 0;
        for (String str : s.split("},")) {
            if (!str.contains("}")) {
                str += "}";
            }
            objects[i] = fromStringToJSONObject(str, out);
            i++;
        }
        return objects;
    }


    public static JSONObject[] fromStringToJSONObjects(String s) {
        return fromStringToJSONObjects(s, System.out);
    }

    /**
     * save collection into JSON file
     *
     * @param fileName name of file, where collection will be saved
     */
    public void save(String fileName) {
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
            } catch (IOException e) {
                out.println("Saved was not succeded");
            }
        } catch (NullPointerException ex) {
            this.out.println("Saved was not succeded");
            this.out.println("Incorrect fileName " + fileName);
        }
    }

    /**
     * method to save to default file;
     */
    public void save() {
        this.save(defaultFileName);
    }

    /**
     * @param fileName name of input file
     *                 get collection from JSON file
     */
    public void importFromFile(String fileName) {
        File file = new File(fileName);
        this.out.println("Try to import collection from file " + fileName);
        String content = "";
        try (FileReader reader = new FileReader(file);) {

            windows.removeAll(windows);
            char[] chars = new char[(int) file.length()];

            reader.read(chars);
            content = new String(chars);
            content = content.replaceAll("[\\[\\]]", "");
            JSONObject objects[] = fromStringToJSONObjects(content);
            for (JSONObject object : fromStringToJSONObjects(content)) {
                add(fromJSONToWindow(object, out));
            }
            this.out.println("Collection was imported from file " + fileName);
        } catch (IOException e) {
            e.printStackTrace();

        }
    }

}

interface Counter {
    Double getMinSum(Window windows);
}
