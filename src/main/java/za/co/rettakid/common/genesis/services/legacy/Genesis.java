package za.co.rettakid.common.genesis.services.legacy;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by lwazi8 on 26/08/2015.
 */
public class Genesis {

    private static String schemeFilepath = "G:\\opencv\\pacs\\protected\\persistence\\scheme.sql";

    public static void main(String[] args) throws FileNotFoundException {
        PhpGenerator phpGenerator = new PhpGenerator();
        phpGenerator.generateFiles();
    }

    private static class PhpGenerator {

        enum NamingStd {PARCEL, CAMEL};

        enum ClassType {DTO, ENTITY};

        public void generateFiles() throws FileNotFoundException {
            for (SchemeObject schemeObject : getSchemeObjectss()) {
                generateFile(schemeObject, ClassType.ENTITY);
                generateFile(schemeObject, ClassType.DTO);
                generateBindingFile(schemeObject);
            }
        }

        private void generateBindingFile(SchemeObject schemeObject) throws FileNotFoundException {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("<?php\n");
            stringBuilder.append(String.format("namespace persiatance\\entity\\%sEntity;\n",convertTo(schemeObject.getClassName(), NamingStd.PARCEL)));
            stringBuilder.append(String.format("namespace presentation\\dto\\%sDto;\n",convertTo(schemeObject.getClassName(), NamingStd.PARCEL)));
            stringBuilder.append("\n");
            stringBuilder.append(String.format("require_once ($PROJ_PRESENTATION_DTO_ROOT.'%sDto.php');\n",convertTo(schemeObject.getClassName(), NamingStd.PARCEL)));
            stringBuilder.append("\n");
            stringBuilder.append(generateBindingFileSide(schemeObject, ClassType.ENTITY));
            stringBuilder.append(generateBindingFileSide(schemeObject, ClassType.DTO));
            stringBuilder.append("?>");
            PrintWriter out = new PrintWriter(String.format("../../protected/common/binding/%sBinding.php",convertTo(schemeObject.getClassName(), NamingStd.PARCEL)));
            out.write(stringBuilder.toString());
            out.close();
        }

        private String generateBindingFileSide(SchemeObject schemeObject,ClassType classType) {
            StringBuilder stringBuilder = new StringBuilder();
            String prefix = "";
            String bindPrefix = "";
            switch (classType)  {
                case DTO:
                    prefix = "Dto";
                    bindPrefix = "Entity";
                    break;
                case ENTITY:
                    prefix = "Entity";
                    bindPrefix = "Dto";
                    break;
            }
            stringBuilder.append(String.format("function bind%s%s($%s%s)\t{\n",convertTo(schemeObject.getClassName(), NamingStd.PARCEL),prefix,convertTo(schemeObject.getClassName(), NamingStd.CAMEL),prefix));
            stringBuilder.append(String.format("\tif ($%s%s != null)\t{\n",convertTo(schemeObject.getClassName(), NamingStd.CAMEL),prefix));
            stringBuilder.append(String.format("\t\t$%s%s = new %s%s();\n",convertTo(schemeObject.getClassName(), NamingStd.CAMEL),bindPrefix,convertTo(schemeObject.getClassName(), NamingStd.PARCEL),bindPrefix));
            for (String variable : schemeObject.getVariables()) {
                stringBuilder.append(String.format("\t\t$%s%s->set%s($%s%s->get%s%s());\n", convertTo(schemeObject.getClassName(), NamingStd.CAMEL), bindPrefix, convertTo(variable, NamingStd.PARCEL), convertTo(schemeObject.getClassName(), NamingStd.CAMEL), prefix,convertTo(variable, NamingStd.PARCEL),convertTo(variable, NamingStd.PARCEL)));
            }
            stringBuilder.append(String.format("\t\treturn $%s%s;\n",convertTo(schemeObject.getClassName(), NamingStd.CAMEL),prefix));
            stringBuilder.append("\t}\telse {\n");
            stringBuilder.append("\t\treturn null;\n");
            stringBuilder.append("\t}\n");
            stringBuilder.append("}\n");
            stringBuilder.append("\n");
            return stringBuilder.toString();
        }

        private void generateFile(SchemeObject schemeObject, ClassType classType) throws FileNotFoundException {
            StringBuilder stringBuilder = new StringBuilder();
            String saveToPath = "";
            stringBuilder.append("<?php\n");
            switch (classType) {
                case ENTITY:
                    stringBuilder.append("namespace persiatance\\entity;\n");
                    stringBuilder.append("\n");
                    stringBuilder.append(String.format("/** @Entity @Table(name=\"pacs_%s\") **/\n", schemeObject.getClassName()));
                    stringBuilder.append(String.format("class %sEntity \t{\n", convertTo(schemeObject.getClassName(), NamingStd.PARCEL)));
                    saveToPath = String.format("../../protected/persistence/entity/%sEntity.php", convertTo(schemeObject.getClassName(), NamingStd.PARCEL));
                    break;
                case DTO:
                    stringBuilder.append("namespace presentation\\dto;\n");
                    stringBuilder.append("require_once ($PROJ_PRESENTATION_DTO_ROOT.'dto.php');\n");
                    stringBuilder.append("\n");
                    stringBuilder.append(String.format("class %sDto extends Dto \t{\n", convertTo(schemeObject.getClassName(), NamingStd.PARCEL)));
                    saveToPath = String.format("../../protected/presentation/dto/%sDto.php", convertTo(schemeObject.getClassName(), NamingStd.PARCEL));
                    break;
            }

            Boolean isIndex = true;
            for (String variable : schemeObject.getVariables()) {
                switch (classType) {
                    case ENTITY:
                        if (isIndex) {
                            stringBuilder.append(String.format("\t/** @Id @Column(name=\"%s\", type=\"string\", nullable=false) @GeneratedValue **/\n", variable));
                            isIndex = false;
                        } else {
                            stringBuilder.append(String.format("\t/** @Column(name=\"%s\", type=\"string\", nullable=false) **/\n", variable));
                        }
                        break;
                }
                stringBuilder.append(String.format("\tprotected $%s;\n", convertTo(variable, NamingStd.CAMEL)));
            }
            stringBuilder.append("\n");
            for (String variable : schemeObject.getVariables()) {
                stringBuilder.append(String.format("\tpublic function get%s()\t{\n", convertTo(variable, NamingStd.PARCEL)));
                stringBuilder.append(String.format("\t\treturn $this->%s;\n", convertTo(variable, NamingStd.CAMEL)));
                stringBuilder.append("\t}\n");
                stringBuilder.append("\n");
                stringBuilder.append(String.format("\tpublic function set%s($%s)\t{\n", convertTo(variable, NamingStd.PARCEL), convertTo(variable, NamingStd.CAMEL)));
                stringBuilder.append(String.format("\t\t$this->%s = $%s;\n", convertTo(variable, NamingStd.CAMEL), convertTo(variable, NamingStd.CAMEL)));
                stringBuilder.append("\t}\n");
                stringBuilder.append("\n");
            }

            stringBuilder.append("\n");
            stringBuilder.append("}\n");
            stringBuilder.append("?>\n");
            PrintWriter out = new PrintWriter(saveToPath);
            out.write(stringBuilder.toString());
            out.close();
        }

        public String convertTo(String string, NamingStd namingStd) {
            switch (namingStd) {
                case PARCEL:
                    string = String.format("%s%s", string.toUpperCase().charAt(0), string.substring(1, string.length()));
                    break;
            }
            while (string.contains("_")) {
                String toUpperPart = string.substring(string.indexOf('_'), string.indexOf('_') + 2);
                string = string.replaceFirst(toUpperPart, String.valueOf(toUpperPart.toUpperCase().charAt(1)));
            }
            return string;
        }

        private List<SchemeObject> getSchemeObjectss() {
            List<SchemeObject> schemeObjects = new ArrayList<>();

            String tablePattern = "CREATE(\\s)TABLE(\\s)PACS_([A-Z_]+)(\\s)(\\()(.*?)(\\n\\))";
            Matcher matchTables = Pattern.compile(tablePattern, Pattern.DOTALL | Pattern.MULTILINE).matcher(getFileText());
            while (matchTables.find()) {
                SchemeObject schemeObject = new SchemeObject();
                String varPattern = "(\\n)(\\s+)([A-Z_]+)(\\s+)(?!KEY)";
                Matcher matchVar = Pattern.compile(varPattern).matcher(matchTables.group(6));
                schemeObject.setClassName(matchTables.group(3).toLowerCase());
                while (matchVar.find()) {
                    schemeObject.getVariables().add(matchVar.group(3).toLowerCase());
                }
                schemeObjects.add(schemeObject);
            }
            return schemeObjects;
        }

        private String getFileText() {
            String fullText = "";
            try {
                BufferedReader br = new BufferedReader(new FileReader(schemeFilepath));
                String currentLine;
                while ((currentLine = br.readLine()) != null) {
                    fullText += currentLine + "\n";
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return fullText;
        }

    }

    private static class SchemeObject {
        private String className;
        private List<String> variables = new ArrayList<String>();

        public String getClassName() {
            return className;
        }

        public void setClassName(String className) {
            this.className = className;
        }

        public List<String> getVariables() {
            return variables;
        }

        public void setVariables(List<String> variables) {
            this.variables = variables;
        }
    }

}
