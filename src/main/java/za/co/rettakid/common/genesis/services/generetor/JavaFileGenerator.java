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

    public void generateJavaBinding() throws Exception {
        String classPackage = getPackage(GenesisConstants.CON_DIR_JAVA_BINDING,GenesisConstants.CON_DIR_JAVA_ROOT);
        String entityPackage = getPackage(GenesisConstants.CON_DIR_JAVA_ENTITY,GenesisConstants.CON_DIR_JAVA_ROOT);
        String dtoPackage = getPackage(GenesisConstants.CON_DIR_JAVA_DTO,GenesisConstants.CON_DIR_JAVA_ROOT);
        removePostFixFromDirMap(GenesisConstants.CON_DIR_JAVA_BINDING);
        for (ClassObject classObject : getClassObjects()) {
            VelocityContext context = new VelocityContext();
            context.put("entityPackage", entityPackage);
            context.put("dtoPackage", dtoPackage);
            context.put("classPackage", classPackage);
            context.put("classObject", classObject);
            generateFile(getGenDirList().get(GenesisConstants.CON_DIR_JAVA_BINDING) + "Bind" + classObject.getName().getParcelCaseName() + ".java", TemplateEnum.JAVA_BINDING.getLocation(), context);
        }
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

    public void generateJavaDaos() throws Exception {
        String entityPackage = getPackage(GenesisConstants.CON_DIR_JAVA_ENTITY,GenesisConstants.CON_DIR_JAVA_ROOT);
        String classPackage = getPackage(GenesisConstants.CON_DIR_JAVA_DAO,GenesisConstants.CON_DIR_JAVA_ROOT);
        removePostFixFromDirMap(GenesisConstants.CON_DIR_JAVA_DAO);
        for (ClassObject classObject : getClassObjects()) {
            VelocityContext context = new VelocityContext();
            Set<String> imports = new HashSet<>();
            for (VariableObject variableObject : classObject.getVariables()) {
                if (variableObject.getType().getImportPackage() != null) {
                    imports.add(variableObject.getType().getImportPackage());
                }
            }
            context.put("entityPackage", entityPackage);
            context.put("classPackage", classPackage);
            context.put("imports", imports);
            context.put("classObject", classObject);
            generateFile(getGenDirList().get(GenesisConstants.CON_DIR_JAVA_DAO) + classObject.getName().getParcelCaseName() + "Dao.java", TemplateEnum.JAVA_DAO.getLocation(), context);
        }
    }

    public void generateJavaDaoImpls() throws Exception {
        String entityPackage = getPackage(GenesisConstants.CON_DIR_JAVA_ENTITY,GenesisConstants.CON_DIR_JAVA_ROOT);
        String daoPackage = getPackage(GenesisConstants.CON_DIR_JAVA_DAO,GenesisConstants.CON_DIR_JAVA_ROOT);
        String classPackage = getPackage(GenesisConstants.CON_DIR_JAVA_DAO_IMPL,GenesisConstants.CON_DIR_JAVA_ROOT);
        removePostFixFromDirMap(GenesisConstants.CON_DIR_JAVA_DAO_IMPL);
        for (ClassObject classObject : getClassObjects()) {
            VelocityContext context = new VelocityContext();
            Set<String> imports = new HashSet<>();
            for (VariableObject variableObject : classObject.getVariables()) {
                if (variableObject.getType().getImportPackage() != null) {
                    imports.add(variableObject.getType().getImportPackage());
                }
            }
            context.put("entityPackage", entityPackage);
            context.put("daoPackage", daoPackage);
            context.put("classPackage", classPackage);
            context.put("imports", imports);
            context.put("classObject", classObject);
            generateFile(getGenDirList().get(GenesisConstants.CON_DIR_JAVA_DAO_IMPL) + classObject.getName().getParcelCaseName() + "DaoImpl.java", TemplateEnum.JAVA_DAO_IMPL.getLocation(), context);
        }
    }

}
