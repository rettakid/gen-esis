package za.co.rettakid.common.genesis.services.generetor;

import org.apache.velocity.VelocityContext;
import za.co.rettakid.common.genesis.common.GenesisConstants;
import za.co.rettakid.common.genesis.enums.TemplateEnum;
import za.co.rettakid.common.genesis.pojo.ClassObject;
import za.co.rettakid.common.genesis.pojo.GeneratedName;

import java.util.List;
import java.util.Map;

/**
 * Created by Lwazi Prusent on 2015/09/24.
 */
public class PhpFileGenerator extends BaseFileGenerator {

    public PhpFileGenerator(List<ClassObject> classObjects, Map<String, String> genDirList, GeneratedName databaseName) {
        super(classObjects, genDirList, databaseName);
    }

    public void generatePhpBindings() throws Exception {
        removePostFixFromDirMap(GenesisConstants.CON_DIR_PHP_BINDINGS);
        for (ClassObject classObject : getClassObjects()) {
            VelocityContext context = new VelocityContext();
            context.put("classObject", classObject);
            generateFile(getGenDirList().get(GenesisConstants.CON_DIR_PHP_BINDINGS) + classObject.getName().getParcelCaseName() + "Binding.php", TemplateEnum.PHP_BINDING.getLocation(), context);
        }
    }

    public void generatePhpCommon() throws Exception {
        removePostFixFromDirMap(GenesisConstants.CON_DIR_PHP_COMMON);
        VelocityContext context = new VelocityContext();
        context.put("databaseName",getDatabaseName());
        generateFile(getGenDirList().get(GenesisConstants.CON_DIR_PHP_COMMON) + "bootstrap.php", TemplateEnum.PHP_BOOTSTRAP.getLocation(), context);
        generateFile(getGenDirList().get(GenesisConstants.CON_DIR_PHP_COMMON) + "config.php", TemplateEnum.PHP_CONFIG.getLocation(), context);
    }

    public void generatePhpBaseDto() throws Exception {
        removePostFixFromDirMap(GenesisConstants.CON_DIR_PHP_DTO);
        VelocityContext context = new VelocityContext();
        generateFile(getGenDirList().get(GenesisConstants.CON_DIR_PHP_DTO) + "Dto.php", TemplateEnum.PHP_BASE_DTO.getLocation(), context);
    }

    public void generatePhpEntities() throws Exception {
        removePostFixFromDirMap(GenesisConstants.CON_DIR_PHP_ENTITIES);
        for (ClassObject classObject : getClassObjects()) {
            VelocityContext context = new VelocityContext();
            context.put("classObject", classObject);
            context.put("databaseName",getDatabaseName());
            generateFile(getGenDirList().get(GenesisConstants.CON_DIR_PHP_ENTITIES) + classObject.getName().getParcelCaseName() + "Entity.php", TemplateEnum.PHP_ENTITY.getLocation(), context);
        }
    }

    public void generatePhpDto() throws Exception {
        removePostFixFromDirMap(GenesisConstants.CON_DIR_PHP_DTO);
        for (ClassObject classObject : getClassObjects()) {
            VelocityContext context = new VelocityContext();
            context.put("classObject", classObject);
            generateFile(getGenDirList().get(GenesisConstants.CON_DIR_PHP_DTO) + classObject.getName().getParcelCaseName() + "Dto.php", TemplateEnum.PHP_DTO.getLocation(), context);
        }
    }

    public void generatePhpControllers() throws Exception {
        removePostFixFromDirMap(GenesisConstants.CON_DIR_PHP_CONTROLLERS);
        for (ClassObject classObject : getClassObjects()) {
            VelocityContext context = new VelocityContext();
            context.put("classObject", classObject);
            generateFile(getGenDirList().get(GenesisConstants.CON_DIR_PHP_CONTROLLERS) + classObject.getName().getParcelCaseName() + "Controller.php", TemplateEnum.PHP_CONTROLLER.getLocation(), context);
        }
    }

    public void generatePhpXML() throws Exception {
        VelocityContext context = new VelocityContext();
        context.put("classObjects", getClassObjects());
        generateFile(getGenDirList().get(GenesisConstants.CON_DIR_PHP_ROOT) + "\\" + "xml.php", TemplateEnum.PHP_XML.getLocation(), context);
    }

}
