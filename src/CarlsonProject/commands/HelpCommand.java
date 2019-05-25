package CarlsonProject.commands;

import CarlsonProject.WindowsArrayList;

import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;

public class HelpCommand implements Command {
    final String HELP;
    private String line;
    private Map<String, String> commMap = new HashMap<>();
    private transient PrintStream out;
    private int UserID;

    public HelpCommand(String HELP, String line){
        this.HELP = HELP;
        this.line = line;
        this.commMap.put("add", "\tкоманда добавления элемента окна в конец списка\n" +
                "\tЭлемент указывается в формате c:%COLORNAME и далее можно шансы флагов по выбору,\n" +
                "\tиначе они будут установлены конструктором по умолчанию\n" +
                "\t\tsc:%double speakChance\n" +
                "\t\toc:%double openChance\n" +
                "\t\thc:%double holeChance\n" +
                "\t\trc:%double robberChance");
        this.commMap.put("", HELP);
        this.commMap.put(" ", HELP);
        this.commMap.put("insert", "Комманда добавления элемента по индексу\n" +
                "Сначала указывается индекс, на который будет добавлен элемент, затем сам элемент\n" +
                "Элемент указывается в формате c:%COLORNAME и далее можно шансы флагов по выбору,\\n\" +\n" +
                "                \"иначе они будут установлены конструктором по умолчанию\\n\" +\n" +
                "                \"\t\tsc:%double speakChance\\n\" +\n" +
                "                \"\t\toc:%double openChance\\n\" +\n" +
                "                \"\t\thc:%double holeChance\\n\" +\n" +
                "                \"\t\trc:%double robberChance\"");
        this.commMap.put("exit", "Команда выхода из программы");
        this.commMap.put("info", "Команда, которая выводит информацию о коллекции:\n" +
                "дату создания, тип и размер");
        this.commMap.put("remove","команда удаления элемента по значению" +
                "Элемент указывается в формате c:%COLORNAME и далее можно шансы флагов по выбору,\\n\" +\n" +
                "                \"иначе они будут установлены конструктором по умолчанию\\n\" +\n" +
                "                \"sc:%double speakChance\\n\" +\n" +
                "                \"oc:%double openChance\\n\" +\n" +
                "                \"hc:%double holeChance\\n\" +\n" +
                "                \"rc:%double robberChance\"");
    }

    @Override
    public void execute(WindowsArrayList windows){
        this.out.println(commMap.get(line));
    }

    @Override
    public void setUserID(int userID) {
        UserID = userID;
    }

    @Override
    public void setOut(PrintStream out) {
        this.out = out;
    }
}
