package za.co.rettakid.common.genesis.services;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import za.co.rettakid.common.genesis.common.GenesisConstants;
import za.co.rettakid.common.genesis.pojo.ClassObject;
import za.co.rettakid.common.genesis.pojo.VariableObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.*;

/**
 * Created by LWAZI8 on 26/08/2015.
 */
public class FileGenerator {

    private List<ClassObject> classObjects = new ArrayList<>();
    private Map<String, String> genDirList = new HashMap<>();

    public FileGenerator(List<ClassObject> classObjects, Map<String, String> genDirList) {
        this.classObjects = classObjects;
        this.genDirList = genDirList;
    }

    public void generateAndroidDtos() throws Exception {
        removePostFixFromDirMap(GenesisConstants.CON_DIR_JAVA_DTO);
        for (ClassObject classObject : classObjects) {
            VelocityContext context = new VelocityContext();
            Set<String> imports = new HashSet<>();
            for (VariableObject variableObject : classObject.getVariables()) {
                if (variableObject.getType().getImportPackage() != null) {
                    imports.add(variableObject.getType().getImportPackage());
                }
            }
            context.put("imports", imports);
            context.put("classObject", classObject);
            generateFile(genDirList.get(GenesisConstants.CON_DIR_JAVA_DTO) + classObject.getName().getParcelCaseName() + "Dto.java", "java\\TemplateDto_java.vm", context);
        }
    }

    public void generateAndroidClients() throws Exception {
        removePostFixFromDirMap(GenesisConstants.CON_DIR_JAVA_CLIENT);
        for (ClassObject classObject : classObjects) {
            VelocityContext context = new VelocityContext();
            context.put("classObject", classObject);
            generateFile(genDirList.get(GenesisConstants.CON_DIR_JAVA_CLIENT) + classObject.getName().getParcelCaseName() + "Client.java", "java\\TemplateClient_java.vm", context);
        }
    }

    public void generatePhpBindings() throws Exception {
        removePostFixFromDirMap(GenesisConstants.CON_DIR_PHP_BINDINGS);
        for (ClassObject classObject : classObjects) {
            VelocityContext context = new VelocityContext();
            context.put("classObject", classObject);
            generateFile(genDirList.get(GenesisConstants.CON_DIR_PHP_BINDINGS) + classObject.getName().getParcelCaseName() + "Binding.php", "php\\TemplateBinding_php.vm", context);
        }
    }

    private void generateFile(String filePath, String templateName, VelocityContext context) throws Exception {
        Properties properties = new Properties();
        properties.setProperty(RuntimeConstants.RUNTIME_LOG_LOGSYSTEM_CLASS, "org.apache.velocity.runtime.log.Log4JLogChute" );
        properties.setProperty("runtime.log.logsystem.log4j.logger","velocity");
        context.put("H","$");
        VelocityEngine velocityEngine = new VelocityEngine();
        velocityEngine.init(properties);
        Template template = velocityEngine.getTemplate(String.format("src\\main\\resources\\%s", templateName));
        StringWriter writer = new StringWriter();
        template.merge(context, writer);
        saveFile(filePath, writer.toString());
    }

    private void saveFile(String path, String content) throws FileNotFoundException {
        File file = new File(path);
        if (file.exists()) {
            System.out.println(String.format("'%s' exists, would you like to override it?", path));
            if (userAgrees()) {
                writeFile(path, content);
            } else {
                System.err.println(String.format("'%s' was not created", path));
            }
        } else {
            writeFile(path, content);
        }

    }

    private void writeFile(String path, String content) throws FileNotFoundException {
        PrintWriter out = new PrintWriter(path);
        out.write(content);
        out.close();
        System.out.println(String.format("File '%s' has been created", path));
    }

    private Boolean userAgrees() {
        Scanner scanner = new Scanner(System.in);
        String tmp = scanner.next();
        return tmp.equalsIgnoreCase("y") || tmp.equalsIgnoreCase("yes");
    }

    private void removePostFixFromDirMap(String dir) {
        String genDir = genDirList.get(dir);
        genDir = genDir.replace(dir,"");
        genDirList.put(dir,genDir);
    }

}
