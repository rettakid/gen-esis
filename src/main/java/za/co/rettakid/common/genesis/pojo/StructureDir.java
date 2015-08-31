package za.co.rettakid.common.genesis.pojo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LWAZI8 on 28/08/2015.
 */
public class StructureDir {

    private String dirName;
    private String dirPath = "";
    private List<StructureDir> structureDirs = new ArrayList<StructureDir>();
    private String structureText;

    public String getDirName() {
        return dirName;
    }

    public void setDirName(String dirName) {
        this.dirName = dirName;
    }

    public List<StructureDir> getStructureDirs() {
        return structureDirs;
    }

    public void setStructureDirs(List<StructureDir> structureDirs) {
        this.structureDirs = structureDirs;
    }

    public String getStructureText() {
        return structureText;
    }

    public void setStructureText(String structureText) {
        this.structureText = structureText;
    }

    public String getDirPath() {
        return dirPath;
    }

    public void setDirPath(String dirPath) {
        this.dirPath = dirPath;
    }
}
