package CarlsonProject.commands;

import java.util.HashMap;
import java.util.Map;

public class HelpCommand implements Command {
    final String HELP;
    private String line;
    private Map<String, String> commMap = new HashMap<>();

    public HelpCommand(String HELP, String line){
        this.HELP = HELP;
        this.line = line;
        commMap.put("add", "команда добавления элемента окна в конец списка\n" +
                "Элемент указывается в формате c:%COLORNAME и далее можно шансы флагов по выбору,\n" +
                "иначе они будут установлены конструктором по умолчанию\n" +
                "sc:%double speakChance\n" +
                "oc:%double openChance\n" +
                "hc:%double holeChance\n" +
                "rc:%double robberChance");
        commMap.put("", HELP);
        commMap.put(" ", HELP);
        commMap.put("insert", "Комманда добавления элемента по индексу\n" +
                "Сначала указывается индекс, на который будет добавлен элемент, затем сам элемент\n" +
                "Элемент указывается в формате c:%COLORNAME и далее можно шансы флагов по выбору,\\n\" +\n" +
                "                \"иначе они будут установлены конструктором по умолчанию\\n\" +\n" +
                "                \"sc:%double speakChance\\n\" +\n" +
                "                \"oc:%double openChance\\n\" +\n" +
                "                \"hc:%double holeChance\\n\" +\n" +
                "                \"rc:%double robberChance\"");
        commMap.put("exit", "Команда выхода из программы");
        commMap.put("info", "Команда, которая выводит информацию о коллекции:\n" +
                "дату создания, тип и размер");
        commMap.put("remove","команда удаления элемента по значению" +
                "Элемент указывается в формате c:%COLORNAME и далее можно шансы флагов по выбору,\\n\" +\n" +
                "                \"иначе они будут установлены конструктором по умолчанию\\n\" +\n" +
                "                \"sc:%double speakChance\\n\" +\n" +
                "                \"oc:%double openChance\\n\" +\n" +
                "                \"hc:%double holeChance\\n\" +\n" +
                "                \"rc:%double robberChance\"");
    }

    @Override
    public void execute(){
        System.out.println(commMap.get(line));
    }
}
