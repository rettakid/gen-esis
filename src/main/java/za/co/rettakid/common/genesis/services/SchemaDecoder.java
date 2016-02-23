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

    private static final String databaseNamePattern = "CREATE\\sDATABASE\\s([A-Z_]+)";
    private static final String tablePattern = "CREATE\\sTABLE\\s%s_([A-Z_]+)\\s+\\((.*?)(\\n\\))";
    private static final String varLenPattern = "\\n\\s+([A-Z_]+)\\s+([A-Z]+)\\((\\d+)\\)\\s+(NULL|NOT\\sNULL)";
    private static final String varPattern = "\\n\\s+([A-Z_]+)\\s+([A-Z]+)\\s+(NULL|NOT\\sNULL)";
    private static final String primaryKeyPattern = "\\n\\s+PRIMARY\\sKEY\\s+\\(([A-Z_]+)\\)";
    private static final String referencePattern = "\\n\\s+FOREIGN\\sKEY\\s+\\(([A-Z_]+)\\)\\sREFERENCES\\s%s_([A-Z_]+)\\s+\\(([A-Z_]+)\\)";

    private GeneratedName databaseName;
    private List<ClassObject> classObjects;

    private void decodeDatabaseName(String schemeLocation) {
        Matcher matchDbName = Pattern.compile(databaseNamePattern).matcher(FileHandler.getFileText(schemeLocation));
        databaseName = (matchDbName.find()) ? new GeneratedName(matchDbName.group(1)) : null;
    }

    public void decodeScheme(String schemeLocation)    {
        List<ClassObject> classObjects = new ArrayList<>();
        decodeDatabaseName(schemeLocation);
        String schemeAsText = FileHandler.getFileText(schemeLocation);
        Matcher matchTables = Pattern.compile(String.format(tablePattern,databaseName.getOriginalName()), Pattern.DOTALL | Pattern.MULTILINE).matcher(schemeAsText);

        while (matchTables.find()) {
            ClassObject classObject = new ClassObject();
            classObject.setName(new GeneratedName(matchTables.group(1)));

            classObject.addAllVariables(getVariablesWithLength(matchTables.group(2), classObject));
            classObject.addAllVariables(getVariablesWithOutLength(matchTables.group(2), classObject));
            classObject.setPrimaryKeyVar(getPrimaryKey(matchTables.group(2), classObject.getVariables()));
            setReferencedObjects(matchTables.group(2), classObject, classObjects);

            classObjects.add(classObject);
        }
        this.classObjects = classObjects;
    }

    private List<VariableObject> getVariablesWithLength(String text, ClassObject classObject)  {
        Matcher matcher = Pattern.compile(varLenPattern).matcher(text);
        List<VariableObject> variableObjects = new ArrayList<>();
        while (matcher.find()) {
            VariableObject variable = new VariableObject();
            variable.setOrgName(new GeneratedName(matcher.group(1)));
            variable.setName(new GeneratedName(matcher.group(1)));
            variable.setType(new VariableType(VariableTypeEnum.setEnum(matcher.group(2))));
            variable.setLength(Integer.parseInt(matcher.group(3)));
            variable.setNullity(NullityTypeEnum.setEnum(matcher.group(4)));
            variable.setClassObject(classObject);
            variableObjects.add(variable);
        }
        return variableObjects;
    }

    private List<VariableObject> getVariablesWithOutLength(String text, ClassObject classObject)  {
        Matcher matcher = Pattern.compile(varPattern).matcher(text);
        List<VariableObject> variableObjects = new ArrayList<VariableObject>();
        while (matcher.find()) {
            VariableObject variable = new VariableObject();
            variable.setOrgName(new GeneratedName(matcher.group(1)));
            variable.setName(new GeneratedName(matcher.group(1)));
            variable.setType(new VariableType(VariableTypeEnum.setEnum(matcher.group(2))));
            variable.setLength(0);
            variable.setNullity(NullityTypeEnum.setEnum(matcher.group(3)));
            variable.setClassObject(classObject);
            variableObjects.add(variable);
        }
        return variableObjects;
    }

    private void setReferencedObjects(String text,ClassObject curClassObject,List<ClassObject> classObjects)  {
        Matcher matcher = Pattern.compile(String.format(referencePattern,databaseName.getOriginalName())).matcher(text);
        while (matcher.find())  {
            System.err.println(matcher.group(1) + " - " + matcher.group(2) + " - " + matcher.group(3));
            for (ClassObject classObject : classObjects) {
                if ((classObject.getName().getOriginalName()).equals(matcher.group(2)))   {
                    for (VariableObject variableObject : curClassObject.getVariables()) {
                        if (matcher.group(1).equals(variableObject.getName().getOriginalName()))    {
                            VariableType variableType = new VariableType(VariableTypeEnum.REF);
                            variableType.setJavaName(classObject.getName().getParcelCaseName());
                            variableType.setPhpName(classObject.getName().getParcelCaseName());
                            variableObject.setType(variableType);
                            variableObject.setLength(null);
                            if (variableObject.getName().getOriginalName().contains("_ID") && variableObject.getName().getOriginalName().indexOf("_ID") == variableObject.getName().getOriginalName().length() - 3) {
                                variableObject.setName(new GeneratedName(variableObject.getName().getOriginalName().substring(0,variableObject.getName().getOriginalName().indexOf("_ID"))));
                            }   else    {
                                throw new RuntimeException("Please add the _ID prefix to column " + variableObject.getName().getOriginalName() + " on table " + curClassObject.getName().getOriginalName());
                            }
                            variableObject.setReference(classObject.getPrimaryKeyVar());

                            for (ClassObject refClassObject : classObjects) {
                                if (classObject.getName().getOriginalName().equals(refClassObject.getName().getOriginalName())) {
                                    try {
                                        ClassObject refClass = (ClassObject)curClassObject.clone();
                                        refClass.setRefName(new GeneratedName(variableObject.getName().getOriginalName()));
                                        refClassObject.addReference(refClass);
                                    } catch (CloneNotSupportedException e) {
                                        e.printStackTrace();
                                    }

                                }
                            }
                            break;
                        }
                    }
                    break;
                }
            }
        }
    }

    private VariableObject getPrimaryKey(String text,List<VariableObject> variableObjects)   {
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

    public GeneratedName getDatabaseName() {
        return databaseName;
    }

    public void setDatabaseName(GeneratedName databaseName) {
        this.databaseName = databaseName;
    }

    public List<ClassObject> getClassObjects() {
        return classObjects;
    }

    public void setClassObjects(List<ClassObject> classObjects) {
        this.classObjects = classObjects;
    }
}
