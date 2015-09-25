package za.co.rettakid.common.genesis.services.generetor;

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
public class BaseFileGenerator {

    private List<ClassObject> classObjects = new ArrayList<>();
    private Map<String, String> genDirList = new HashMap<>();
    private final GeneratedName databaseName;

    public BaseFileGenerator(List<ClassObject> classObjects, Map<String, String> genDirList, GeneratedName databaseName) {
        this.classObjects = classObjects;
        this.genDirList = genDirList;
        this.databaseName = databaseName;
    }

    protected void generateFile(String filePath, String templateName, VelocityContext context) throws Exception {
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

    protected void saveFile(String path, String content) throws FileNotFoundException {
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

    protected void writeFile(String path, String content) throws FileNotFoundException {
        PrintWriter out = new PrintWriter(path);
        out.write(content);
        out.close();
        System.out.println(String.format("File '%s' has been created", path));
    }

    protected Boolean userAgrees() {
        Scanner scanner = new Scanner(System.in);
        String tmp = scanner.next();
        return tmp.equalsIgnoreCase("y") || tmp.equalsIgnoreCase("yes");
    }

    protected void removePostFixFromDirMap(String dir) {
        String genDir = genDirList.get(dir);
        genDir = genDir.replace(dir,"");
        genDirList.put(dir,genDir);
    }

    protected String getPackage(String dir,String root) {
        String genDir = genDirList.get(dir);
        String packageDir = genDir.substring(genDir.indexOf(root) + root.length() + 1);
        if (packageDir.contains(dir)) {
            packageDir = packageDir.substring(0, packageDir.indexOf(dir) - 1);
        }
        return packageDir.replaceAll("\\\\",".");
    }

    /*getters and setters*/

    public List<ClassObject> getClassObjects() {
        return classObjects;
    }

    public void setClassObjects(List<ClassObject> classObjects) {
        this.classObjects = classObjects;
    }

    public Map<String, String> getGenDirList() {
        return genDirList;
    }

    public void setGenDirList(Map<String, String> genDirList) {
        this.genDirList = genDirList;
    }

    public GeneratedName getDatabaseName() {
        return databaseName;
    }
}
