package Entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by killeryuan on 2017/2/19.
 */
public class DIR {
    private String DIRName;
    private String fileName;
    private List<String> fileNames = new ArrayList<>();

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setFileNames(List<String> fileNames) {
        this.fileNames = fileNames;
    }

    public String getDIRName() {
        return DIRName;
    }

    public void setDIRName(String DIRName) {
        this.DIRName = DIRName;
    }

    public List<String> getFileNames() {
        return fileNames;
    }

    public void setFileNames(String[] fileNames) {
        for (String name:fileNames) {
            System.out.println(name);
            this.fileNames.add(name);
        }
    }
}
