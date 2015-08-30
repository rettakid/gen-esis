package za.co.rettakid.common.genesis.services;

import za.co.rettakid.common.genesis.enums.NullityTypeEnum;
import za.co.rettakid.common.genesis.enums.VariableTypeEnum;
import za.co.rettakid.common.genesis.pojo.ClassObject;
import za.co.rettakid.common.genesis.pojo.GeneratedName;
import za.co.rettakid.common.genesis.pojo.VariableObject;
import za.co.rettakid.common.genesis.pojo.VariableType;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by LWAZI8 on 26/08/2015.
 */
public class SchemaDecoder {

    private static final String tablePattern = "CREATE\\sTABLE\\sPACS_([A-Z_]+)\\s\\((.*?)(\\n\\))";
    private static final String varLenPattern = "\\n\\s+([A-Z_]+)\\s+([A-Z]+)\\((\\d+)\\)\\s+(NULL|NOT\\sNULL)";
    private static final String varPattern = "\\n\\s+([A-Z_]+)\\s+([A-Z]+)\\s+(NULL|NOT\\sNULL)";
    private static final String primaryKeyPattern = "\\n\\s+PRIMARY\\sKEY\\s+\\(([A-Z_]+)\\)";
    private static final String referencePattern = "\\n\\s+FOREIGN\\sKEY\\s+\\(([A-Z_]+)\\)\\sREFERENCES\\sPACS_([A-Z_]+)\\(([A-Z_]+)\\)";

    public static List<ClassObject> decodeScheme(String schemeLocation)    {
        List<ClassObject> classObjects = new ArrayList<>();

        Matcher matchTables = Pattern.compile(tablePattern, Pattern.DOTALL | Pattern.MULTILINE).matcher(FileHandler.getFileText(schemeLocation));

        while (matchTables.find()) {
            ClassObject classObject = new ClassObject();
            classObject.setName(new GeneratedName(matchTables.group(1)));

            classObject.addAllVariables(getVariablesWithOutLength(matchTables.group(2)));
            classObject.addAllVariables(getVariablesWithLength(matchTables.group(2)));
            setReferencedObjects(matchTables.group(2),classObject,classObjects);
            classObject.setPrimaryKeyVar(getPrimaryKey(matchTables.group(2),classObject.getVariables()));

            classObjects.add(classObject);
        }
        return classObjects;
    }

    private static List<VariableObject> getVariablesWithOutLength(String text)  {
        Matcher matcher = Pattern.compile(varLenPattern).matcher(text);
        List<VariableObject> variableObjects = new ArrayList<>();
        while (matcher.find()) {
            VariableObject variable = new VariableObject();
            variable.setName(new GeneratedName(matcher.group(1)));
            variable.setType(new VariableType(VariableTypeEnum.setEnum(matcher.group(2))));
            variable.setLength(Integer.parseInt(matcher.group(3)));
            variable.setNullity(NullityTypeEnum.setEnum(matcher.group(4)));
            variableObjects.add(variable);
        }
        return variableObjects;
    }

    private static List<VariableObject> getVariablesWithLength(String text)  {
        Matcher matcher = Pattern.compile(varPattern).matcher(text);
        List<VariableObject> variableObjects = new ArrayList<VariableObject>();
        while (matcher.find()) {
            VariableObject variable = new VariableObject();
            variable.setName(new GeneratedName(matcher.group(1)));
            variable.setType(new VariableType(VariableTypeEnum.setEnum(matcher.group(2))));
            variable.setNullity(NullityTypeEnum.setEnum(matcher.group(3)));
            variableObjects.add(variable);
        }
        return variableObjects;
    }

    private static void setReferencedObjects(String text,ClassObject curClassObject,List<ClassObject> classObjects)  {
        Matcher matcher = Pattern.compile(referencePattern).matcher(text);
        while (matcher.find())  {
            for (ClassObject classObject : classObjects) {
                if (classObject.getName().getOriginalName().equals(matcher.group(2)))   {
                    for (VariableObject variableObject : curClassObject.getVariables()) {
                        if (matcher.group(1).equals(variableObject.getName().getOriginalName()))    {
                            VariableType variableType = new VariableType(VariableTypeEnum.REF);
                            variableType.setJavaName(classObject.getName().getParcelCaseName());
                            variableObject.setType(variableType);
                            variableObject.setLength(null);
                            variableObject.setName(new GeneratedName(classObject.getName().getCamelCaseName()));
                            break;
                        }
                    }
                    break;
                }
            }
        }
    }

    private static VariableObject getPrimaryKey(String text,List<VariableObject> variableObjects)   {
        Matcher matcher = Pattern.compile(primaryKeyPattern).matcher(text);
        VariableObject primaryKey = null;
        while (matcher.find()) {
            for (VariableObject variableObject : variableObjects) {
                if (variableObject.getName().getOriginalName().equals(matcher.group(1))) {
                    primaryKey = variableObject;
                    primaryKey.setIsPk(true);
                    variableObjects.remove(variableObject);
                    variableObjects.add(0, primaryKey);
                    break;
                }
            }
        }
        return primaryKey;
    }

}
