package za.co.rettakid.common.genesis.services;

import za.co.rettakid.common.genesis.common.GenesisConstants;
import za.co.rettakid.common.genesis.pojo.StructureDir;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by lwazi8 on 26/08/2015.
 */
public class StructureGenerator {

    private static final String pathNamePattern = "([a-zA-Z_]+)";
    private static final String pathNameLastPattern = "^([a-zA-Z_]+)(.*?)^└─";
    private static final String rootPathNamePattern = "^([a-zA-Z_]+)";
    private static final String folderNamePattern = String.format("^(├─|└─)\\s%s", pathNamePattern);
    private static final String folderPattern = "^%s(.*?)^└─(.*?)^([a-zA-Z_]+)(.*?)^└─";
    private static final String folderLastPattern = "^%s(.*?)^└─(.*?)\\Z";
    private static final String lineStartPattern = String.format("^((│\\s|├─|└─|\\s\\s)\\s)");

    Map<String,String> genDirList = new HashMap<>();

    public StructureGenerator() {
        genDirList.put(GenesisConstants.CON_DIR_PHP_ROOT,null);
        genDirList.put(GenesisConstants.CON_DIR_PHP_BINDINGS, null);
        genDirList.put(GenesisConstants.CON_DIR_PHP_COMMON, null);
        genDirList.put(GenesisConstants.CON_DIR_PHP_CONTROLLERS, null);
        genDirList.put(GenesisConstants.CON_DIR_PHP_DTO, null);
        genDirList.put(GenesisConstants.CON_DIR_PHP_ENTITIES, null);
        genDirList.put(GenesisConstants.CON_DIR_JAVA_ROOT, null);
        genDirList.put(GenesisConstants.CON_DIR_JAVA_CLIENT, null);
        genDirList.put(GenesisConstants.CON_DIR_JAVA_DTO, null);
        genDirList.put(GenesisConstants.CON_DIR_JAVA_VO, null);
        genDirList.put(GenesisConstants.CON_DIR_JAVA_BINDING, null);
    }

    public void generatorStructure(String schemeLocation) {
        List<StructureDir> structureDirs = decodeSchema(schemeLocation);
        for (StructureDir structureDir : structureDirs) {
            makeDir(structureDir);
            generateSubDirs(structureDir);
        }
    }

    private void generateSubDirs(StructureDir structureDir) {
        for (StructureDir dir : structureDir.getStructureDirs()) {
            makeDir(dir);
            generateSubDirs(dir);
        }
    }

    private void makeDir(StructureDir structureDir) {
        File file = new File(structureDir.getDirPath());
        if (genDirList.containsKey(structureDir.getDirName()) && (structureDir.getStructureDirs() == null || structureDir.getStructureDirs().size() == 0 ))   {
            genDirList.put(structureDir.getDirName(),structureDir.getDirPath());
            return;
        } else  if (genDirList.containsKey(structureDir.getDirName()))   {
            genDirList.put(structureDir.getDirName(),structureDir.getDirPath());
        }

        if (file.exists())  {
            System.err.println(String.format("dir '%s' already exists",file.getAbsoluteFile()));
        }else if (file.mkdir()) {
            System.out.println(String.format("created dir '%s'", file.getAbsoluteFile()));
        } else  {
            throw new RuntimeException(String.format("Cannot create dir '%s'",file.getAbsolutePath()));
        }
    }

    private List<StructureDir> decodeSchema(String schemeLocation) {
        List<StructureDir> structureDirs = new ArrayList<>();
        String structure = FileHandler.getFileText(schemeLocation);
        StructureDir structureDir = new StructureDir();
        structureDir.setDirName("root");
        structureDir.setDirPath("root");
        structureDir.setStructureText(structure);
        structureDir.setStructureDirs(getSubDir(structureDir));
        structureDirs.add(structureDir);
        return structureDirs;
    }

    private List<StructureDir> getSubDir(StructureDir parentStructureDir) {
        if (parentStructureDir != null && parentStructureDir.getDirName() != null && parentStructureDir.getStructureText() != null) {
            Matcher matchFolder = Pattern.compile(folderNamePattern, Pattern.DOTALL | Pattern.MULTILINE).matcher(parentStructureDir.getStructureText());
            List<StructureDir> structureDirs = new ArrayList<>();
            while (matchFolder.find()) {
                StructureDir structureDir = new StructureDir();
                structureDir.setDirName(matchFolder.group(2));
                structureDir.setDirPath(parentStructureDir.getDirPath() + "\\" + structureDir.getDirName());
                structureDir.setStructureText(parentStructureDir.getStructureText());
                String folderStructure = getSubStructure(structureDir);
                structureDir.setStructureText(folderStructure);
                structureDir.setStructureDirs(getSubDir(structureDir));
                structureDirs.add(structureDir);
            }
            return structureDirs;
        } else {
            return new ArrayList<>();
        }
    }

    private String getSubStructure(StructureDir structureDir) {
        Matcher matchSubStructure = Pattern.compile(String.format(folderPattern, structureDir.getDirName()), Pattern.DOTALL | Pattern.MULTILINE).matcher(reindentStructure(structureDir.getStructureText()));
        Matcher matchLastSubStructure = Pattern.compile(String.format(folderLastPattern, structureDir.getDirName()), Pattern.DOTALL | Pattern.MULTILINE).matcher(reindentStructure(structureDir.getStructureText()));
        if (matchSubStructure.find()) {
            return replaceLast(matchSubStructure.group());
        } else if (matchLastSubStructure.find()) {
            return matchLastSubStructure.group();
        }
        return null;
    }

    private String reindentStructure(String structure) {
        Matcher folderReplace = Pattern.compile(rootPathNamePattern, Pattern.DOTALL | Pattern.MULTILINE).matcher(structure);
        Matcher matchReplace = Pattern.compile(lineStartPattern, Pattern.DOTALL | Pattern.MULTILINE).matcher(folderReplace.replaceAll(""));
        return matchReplace.replaceAll("");
    }

    private String replaceLast(String string) {
        return string.replace(getLastIndexOf(pathNameLastPattern, string), "");
    }

    private String getLastIndexOf(String str, String content) {
        Matcher matcher = Pattern.compile(str, Pattern.DOTALL | Pattern.MULTILINE).matcher(content);
        String string = null;
        while (matcher.find()) {
            string = matcher.group();
        }
        return string;
    }

    public Map<String, String> getGenDirList() {
        return genDirList;
    }

    public void setGenDirList(Map<String, String> genDirList) {
        this.genDirList = genDirList;
    }
}
