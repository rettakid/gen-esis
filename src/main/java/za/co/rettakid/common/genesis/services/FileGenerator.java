package za.co.rettakid.common.genesis.services;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import za.co.rettakid.common.genesis.common.GenesisConstants;
import za.co.rettakid.common.genesis.enums.TemplateEnum;
import za.co.rettakid.common.genesis.pojo.ClassObject;
import za.co.rettakid.common.genesis.pojo.GeneratedName;
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
    private GeneratedName databaseName;

    public FileGenerator(List<ClassObject> classObjects, Map<String, String> genDirList,GeneratedName databaseName) {
        this.classObjects = classObjects;
        this.genDirList = genDirList;
        this.databaseName = databaseName;
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
            generateFile(genDirList.get(GenesisConstants.CON_DIR_JAVA_DTO) + classObject.getName().getParcelCaseName() + "Dto.java", TemplateEnum.JAVA_DTO.getLocation(), context);
            generateFile(genDirList.get(GenesisConstants.CON_DIR_JAVA_DTO) + classObject.getName().getParcelCaseName() + "ListDto.java", TemplateEnum.JAVA_LIST_DTO.getLocation(), context);
        }
    }

    public void generateAndroidVos() throws Exception {
        removePostFixFromDirMap(GenesisConstants.CON_DIR_JAVA_VO);
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
            generateFile(genDirList.get(GenesisConstants.CON_DIR_JAVA_VO) + classObject.getName().getParcelCaseName() + "Vo.java", TemplateEnum.JAVA_VO.getLocation(), context);
        }
    }

    public void generateAndroidBinding() throws Exception {
        removePostFixFromDirMap(GenesisConstants.CON_DIR_JAVA_BINDING);
        for (ClassObject classObject : classObjects) {
            VelocityContext context = new VelocityContext();
            context.put("classObject", classObject);
            generateFile(genDirList.get(GenesisConstants.CON_DIR_JAVA_BINDING) + "Bind" + classObject.getName().getParcelCaseName() + ".java", TemplateEnum.JAVA_BINDING.getLocation(), context);
        }
    }

    public void generateAndroidClients() throws Exception {
        removePostFixFromDirMap(GenesisConstants.CON_DIR_JAVA_CLIENT);
        for (ClassObject classObject : classObjects) {
            VelocityContext context = new VelocityContext();
            context.put("classObject", classObject);
            generateFile(genDirList.get(GenesisConstants.CON_DIR_JAVA_CLIENT) + classObject.getName().getParcelCaseName() + "Client.java", TemplateEnum.JAVA_CLIENT.getLocation(), context);
        }
    }

    public void generateAndroidBaseClient() throws Exception {
        removePostFixFromDirMap(GenesisConstants.CON_DIR_JAVA_CLIENT);
        VelocityContext context = new VelocityContext();
        context.put("databaseName",databaseName);
        generateFile(genDirList.get(GenesisConstants.CON_DIR_JAVA_CLIENT) + "BaseClient.java", TemplateEnum.JAVA_BASE_CLIENT.getLocation(), context);
    }

    public void generatePhpBindings() throws Exception {
        removePostFixFromDirMap(GenesisConstants.CON_DIR_PHP_BINDINGS);
        for (ClassObject classObject : classObjects) {
            VelocityContext context = new VelocityContext();
            context.put("classObject", classObject);
            generateFile(genDirList.get(GenesisConstants.CON_DIR_PHP_BINDINGS) + classObject.getName().getParcelCaseName() + "Binding.php", TemplateEnum.PHP_BINDING.getLocation(), context);
        }
    }

    public void generatePhpCommon() throws Exception {
        removePostFixFromDirMap(GenesisConstants.CON_DIR_PHP_COMMON);
        VelocityContext context = new VelocityContext();
        context.put("databaseName",databaseName);
        generateFile(genDirList.get(GenesisConstants.CON_DIR_PHP_COMMON) + "bootstrap.php", TemplateEnum.PHP_BOOTSTRAP.getLocation(), context);
        generateFile(genDirList.get(GenesisConstants.CON_DIR_PHP_COMMON) + "config.php", TemplateEnum.PHP_CONFIG.getLocation(), context);
    }

    public void generatePhpBaseDto() throws Exception {
        removePostFixFromDirMap(GenesisConstants.CON_DIR_PHP_DTO);
        VelocityContext context = new VelocityContext();
        generateFile(genDirList.get(GenesisConstants.CON_DIR_PHP_DTO) + "Dto.php", TemplateEnum.PHP_BASE_DTO.getLocation(), context);
    }

    public void generatePhpEntities() throws Exception {
        removePostFixFromDirMap(GenesisConstants.CON_DIR_PHP_ENTITIES);
        for (ClassObject classObject : classObjects) {
            VelocityContext context = new VelocityContext();
            context.put("classObject", classObject);
            context.put("databaseName",databaseName);
            generateFile(genDirList.get(GenesisConstants.CON_DIR_PHP_ENTITIES) + classObject.getName().getParcelCaseName() + "Entity.php", TemplateEnum.PHP_ENTITY.getLocation(), context);
        }
    }

    public void generatePhpDto() throws Exception {
        removePostFixFromDirMap(GenesisConstants.CON_DIR_PHP_DTO);
        for (ClassObject classObject : classObjects) {
            VelocityContext context = new VelocityContext();
            context.put("classObject", classObject);
            generateFile(genDirList.get(GenesisConstants.CON_DIR_PHP_DTO) + classObject.getName().getParcelCaseName() + "Dto.php", TemplateEnum.PHP_DTO.getLocation(), context);
        }
    }

    public void generatePhpControllers() throws Exception {
        removePostFixFromDirMap(GenesisConstants.CON_DIR_PHP_CONTROLLERS);
        for (ClassObject classObject : classObjects) {
            VelocityContext context = new VelocityContext();
            context.put("classObject", classObject);
            generateFile(genDirList.get(GenesisConstants.CON_DIR_PHP_CONTROLLERS) + classObject.getName().getParcelCaseName() + "Controller.php", TemplateEnum.PHP_CONTROLLER.getLocation(), context);
        }
    }

    public void generatePhpXML() throws Exception {
        VelocityContext context = new VelocityContext();
        context.put("classObjects", classObjects);
        generateFile(genDirList.get(GenesisConstants.CON_DIR_PHP_ROOT) + "\\" + "xml.php", TemplateEnum.PHP_XML.getLocation(), context);
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
