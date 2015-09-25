package za.co.rettakid.common.genesis.services.generetor;

import org.apache.velocity.VelocityContext;
import za.co.rettakid.common.genesis.common.GenesisConstants;
import za.co.rettakid.common.genesis.enums.TemplateEnum;
import za.co.rettakid.common.genesis.pojo.ClassObject;
import za.co.rettakid.common.genesis.pojo.GeneratedName;
import za.co.rettakid.common.genesis.pojo.VariableObject;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Lwazi Prusent on 2015/09/24.
 */
public class JavaFileGenerator extends BaseFileGenerator {

    public JavaFileGenerator(List<ClassObject> classObjects, Map<String, String> genDirList, GeneratedName databaseName) {
        super(classObjects, genDirList, databaseName);
    }

    public void generateJavaDtos() throws Exception {
        String classPackage = getPackage(GenesisConstants.CON_DIR_JAVA_DTO,GenesisConstants.CON_DIR_JAVA_ROOT);
        removePostFixFromDirMap(GenesisConstants.CON_DIR_JAVA_DTO);
        for (ClassObject classObject : getClassObjects()) {
            VelocityContext context = new VelocityContext();
            Set<String> imports = new HashSet<>();
            for (VariableObject variableObject : classObject.getVariables()) {
                if (variableObject.getType().getImportPackage() != null) {
                    imports.add(variableObject.getType().getImportPackage());
                }
            }
            context.put("classPackage", classPackage);
            context.put("imports", imports);
            context.put("classObject", classObject);
            generateFile(getGenDirList().get(GenesisConstants.CON_DIR_JAVA_DTO) + classObject.getName().getParcelCaseName() + "Dto.java", TemplateEnum.JAVA_DTO.getLocation(), context);
            //generateFile(getGenDirList().get(GenesisConstants.CON_DIR_JAVA_DTO) + classObject.getName().getParcelCaseName() + "ListDto.java", TemplateEnum.JAVA_DTO.getLocation(), context);
        }
    }

    public void generateJavaEntities() throws Exception {
        String classPackage = getPackage(GenesisConstants.CON_DIR_JAVA_ENTITY,GenesisConstants.CON_DIR_JAVA_ROOT);
        removePostFixFromDirMap(GenesisConstants.CON_DIR_JAVA_ENTITY);
        for (ClassObject classObject : getClassObjects()) {
            VelocityContext context = new VelocityContext();
            Set<String> imports = new HashSet<>();
            for (VariableObject variableObject : classObject.getVariables()) {
                if (variableObject.getType().getImportPackage() != null) {
                    imports.add(variableObject.getType().getImportPackage());
                }
            }
            context.put("databaseName", getDatabaseName());
            context.put("classPackage", classPackage);
            context.put("imports", imports);
            context.put("classObject", classObject);
            generateFile(getGenDirList().get(GenesisConstants.CON_DIR_JAVA_ENTITY) + classObject.getName().getParcelCaseName() + "Entity.java", TemplateEnum.JAVA_ENTITY.getLocation(), context);
        }
    }

}
