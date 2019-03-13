package CarlsonProject;
import java.io.*;

public class FileHandler{
    private String fileName;
    private FileReader fr;

    public FileHandler(String name) throws FileNotFoundException {
        this.fileName = name;
        try{
            fr = new FileReader(fileName);
        } catch (FileNotFoundException ex){
            System.out.print(ex.getMessage());
        }
    }

    public String read(String fileName) throws FileNotFoundException{
        return "xs";
    }
}
