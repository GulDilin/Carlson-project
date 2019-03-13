package CarlsonProject;

import java.util.*;
import java.io.*;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class WindowsArrayList {
    private ArrayList<Window> windows;
    private static HashMap<String, Color> colorMap;
    static {
        colorMap = new HashMap<String, Color>();
        colorMap.put("green", Color.GREEN);
        colorMap.put("yellow", Color.YELLOW);
        colorMap.put("blue", Color.BLUE);
        colorMap.put("red", Color.RED);
    }

    private JSONObject jsonObject = new JSONObject();

    public WindowsArrayList(ArrayList<Window> windows){
        this.windows = windows;
    }

    private static Color getColor(String colorName) throws NoSuchColorException{
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
//    public void add(String colorName, double SC, double HC, double RC, double OC) throws NoSuchColorException{
//        try{
//            Color color = getColor(colorName);
//            windows.add(new Window.Builder(color)
//                    .speakChance(SC)
//                    .openChance(OC)
//                    .holeChance(HC)
//                    .robberChance(RC)
//                    .build());
//        } catch (NoSuchColorException e){
//            e.getMessage();
//        }
//    }

    public void add(Window window){
        this.windows.add(window);
    }

    public void show(){
        Iterator<Window> windowIterator = windows.iterator();
        while (windowIterator.hasNext()){
            System.out.println(windowIterator.next().toString());
        }
    }

    public static JSONObject fromStringToJSONString(String s){
        JSONParser parcer;
        JSONObject object = null;
        parcer = new JSONParser();
        String[] strObjs = s.split("},");
        for(String str: strObjs){
            try {
                object = (JSONObject) parcer.parse(str);
            } catch (ParseException parsE){
                System.out.println("Ошибка получения объекта окна");
            }
        }
        return object;
    }

    public static Window fromJSONToWindow(JSONObject object){
        Window window = null;
        try {
            Color color = getColor((String)object.get("color"));
            Window.Builder windowBuilder = new Window.Builder(color);
            if (object.get("speakChance") != null){
                windowBuilder.speakChance((double)object.get("speakChance"));
            }
            return windowBuilder.build();
        } catch (NoSuchColorException colorE){
            System.out.println(colorE.getMessage());
        }
        return window;
    }

    public void importFile(String fileName) {
        File file = new File(fileName);
        String content = "";
        FileReader reader = null;
        try {
            reader = new FileReader(file);
            windows.removeAll(windows);
            char[] chars = new char[(int) file.length()];
            try {
                reader.read(chars);
                content = new String(chars);
                content = content.replaceAll("[\\{\\[\\]]", "");
                JSONObject object = fromStringToJSONString(content);
            } catch (IOException ex){
                System.out.println(ex.getMessage());
            }
        }catch (FileNotFoundException e){
            if (fileName != "../default.json") {
                System.out.println("Нет такого файла, коллекция будет загружена из default.json");
                importFile("../default.json");
            } else {
                System.out.println("Нет такого файла!");
            }
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
