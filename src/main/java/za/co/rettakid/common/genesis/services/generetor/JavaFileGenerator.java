package za.co.rettakid.common.genesis.services.generetor;

import org.apache.velocity.VelocityContext;
import za.co.rettakid.common.genesis.common.GenesisConstants;
import za.co.rettakid.common.genesis.common.Utilz;
import za.co.rettakid.common.genesis.enums.TemplateEnum;
import za.co.rettakid.common.genesis.pojo.ClassObject;
import za.co.rettakid.common.genesis.pojo.GeneratedName;
import za.co.rettakid.common.genesis.pojo.VariableObject;

import java.util.*;

/**
 * Created by Lwazi Prusent on 2015/09/24.
 */
public class JavaFileGenerator extends BaseFileGenerator {

    public JavaFileGenerator(List<ClassObject> classObjects, Map<String, String> genDirList, GeneratedName databaseName) {
        super(classObjects, genDirList, databaseName);
    }

    public void generateJavaEntityBinding() throws Exception {
        String classPackage = getPackage(GenesisConstants.CON_DIR_JAVA_ENTITY_BINDING, GenesisConstants.CON_DIR_JAVA_ROOT);
        String entityPackage = getPackage(GenesisConstants.CON_DIR_JAVA_ENTITY, GenesisConstants.CON_DIR_JAVA_ROOT);
        String dtoPackage = getPackage(GenesisConstants.CON_DIR_JAVA_DTO, GenesisConstants.CON_DIR_JAVA_ROOT);
        removePostFixFromDirMap(GenesisConstants.CON_DIR_JAVA_ENTITY_BINDING);
        for (ClassObject classObject : getClassObjects()) {
            VelocityContext context = new VelocityContext();
            Set<String> imports = new HashSet<>();
            imports = Utilz.createImports(imports, entityPackage, dtoPackage);
            context.put("imports", imports);
            context.put("classPackage", classPackage);
            context.put("classObject", classObject);
            generateFile(getGenDirList().get(GenesisConstants.CON_DIR_JAVA_ENTITY_BINDING) + "Bind" + classObject.getName().getParcelCaseName() + ".java", TemplateEnum.JAVA_ENTITY_BINDING.getLocation(), context);
        }
    }

    public void generateJavaVoBinding() throws Exception {
        String classPackage = getPackage(GenesisConstants.CON_DIR_JAVA_VO_BINDING, GenesisConstants.CON_DIR_JAVA_ROOT);
        String voPackage = getPackage(GenesisConstants.CON_DIR_JAVA_VO, GenesisConstants.CON_DIR_JAVA_ROOT);
        String dtoPackage = getPackage(GenesisConstants.CON_DIR_JAVA_DTO, GenesisConstants.CON_DIR_JAVA_ROOT);
        removePostFixFromDirMap(GenesisConstants.CON_DIR_JAVA_VO_BINDING);
        for (ClassObject classObject : getClassObjects()) {
            VelocityContext context = new VelocityContext();
            Set<String> imports = new HashSet<>();
            imports = Utilz.createImports(imports, voPackage, dtoPackage);
            context.put("imports", imports);
            context.put("classPackage", classPackage);
            context.put("classObject", classObject);
            generateFile(getGenDirList().get(GenesisConstants.CON_DIR_JAVA_VO_BINDING) + "Bind" + classObject.getName().getParcelCaseName() + ".java", TemplateEnum.JAVA_VO_BINDING.getLocation(), context);
        }
    }

    public void generateJavaDtos() throws Exception {
        String classPackage = getPackage(GenesisConstants.CON_DIR_JAVA_DTO, GenesisConstants.CON_DIR_JAVA_ROOT);
        removePostFixFromDirMap(GenesisConstants.CON_DIR_JAVA_DTO);
        for (ClassObject classObject : getClassObjects()) {
            VelocityContext context = new VelocityContext();
            Set<String> imports = new HashSet<>();
            for (VariableObject variableObject : classObject.getVariables()) {
                if (variableObject.getType().getImportPackage() != null) {
                    imports.add(variableObject.getType().getImportPackage());
                }
            }
            context.put("imports", imports);
            context.put("classObject", classObject);
            context.put("classPackage", classPackage);
            generateFile(getGenDirList().get(GenesisConstants.CON_DIR_JAVA_DTO) + classObject.getName().getParcelCaseName() + "Dto.java", TemplateEnum.JAVA_DTO.getLocation(), context);
            generateFile(getGenDirList().get(GenesisConstants.CON_DIR_JAVA_DTO) + classObject.getName().getParcelCaseName() + "ListDto.java", TemplateEnum.JAVA_DTO_LIST.getLocation(), context);
        }
    }

    public void generateJavaVos() throws Exception {
        String classPackage = getPackage(GenesisConstants.CON_DIR_JAVA_VO, GenesisConstants.CON_DIR_JAVA_ROOT);
        removePostFixFromDirMap(GenesisConstants.CON_DIR_JAVA_VO);
        for (ClassObject classObject : getClassObjects()) {
            VelocityContext context = new VelocityContext();
            Set<String> imports = new HashSet<>();
            for (VariableObject variableObject : classObject.getVariables()) {
                if (variableObject.getType().getImportPackage() != null) {
                    imports.add(variableObject.getType().getImportPackage());
                }
            }
            context.put("imports", imports);
            context.put("classObject", classObject);
            context.put("classPackage", classPackage);
            generateFile(getGenDirList().get(GenesisConstants.CON_DIR_JAVA_VO) + classObject.getName().getParcelCaseName() + "Vo.java", TemplateEnum.JAVA_VO.getLocation(), context);
        }
    }

    public void generateJavaEntities() throws Exception {
        String classPackage = getPackage(GenesisConstants.CON_DIR_JAVA_ENTITY, GenesisConstants.CON_DIR_JAVA_ROOT);
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
            context.put("imports", imports);
            context.put("classObject", classObject);
            context.put("classPackage", classPackage);
            generateFile(getGenDirList().get(GenesisConstants.CON_DIR_JAVA_ENTITY) + classObject.getName().getParcelCaseName() + "Entity.java", TemplateEnum.JAVA_ENTITY.getLocation(), context);
        }
    }

    public void generateJavaDaos() throws Exception {
        String entityPackage = getPackage(GenesisConstants.CON_DIR_JAVA_ENTITY, GenesisConstants.CON_DIR_JAVA_ROOT);
        String classPackage = getPackage(GenesisConstants.CON_DIR_JAVA_DAO, GenesisConstants.CON_DIR_JAVA_ROOT);
        removePostFixFromDirMap(GenesisConstants.CON_DIR_JAVA_DAO);
        for (ClassObject classObject : getClassObjects()) {
            VelocityContext context = new VelocityContext();
            Set<String> imports = new HashSet<>();
            for (VariableObject variableObject : classObject.getVariables()) {
                if (variableObject.getType().getImportPackage() != null) {
                    imports.add(variableObject.getType().getImportPackage());
                }
            }
            imports = Utilz.createImports(imports, entityPackage);
            context.put("imports", imports);
            context.put("classObject", classObject);
            context.put("classPackage", classPackage);
            generateFile(getGenDirList().get(GenesisConstants.CON_DIR_JAVA_DAO) + classObject.getName().getParcelCaseName() + "Dao.java", TemplateEnum.JAVA_DAO.getLocation(), context);
        }
    }

    public void generateJavaDaoImpls() throws Exception {
        String entityPackage = getPackage(GenesisConstants.CON_DIR_JAVA_ENTITY, GenesisConstants.CON_DIR_JAVA_ROOT);
        String daoPackage = getPackage(GenesisConstants.CON_DIR_JAVA_DAO, GenesisConstants.CON_DIR_JAVA_ROOT);
        String classPackage = getPackage(GenesisConstants.CON_DIR_JAVA_DAO_IMPL, GenesisConstants.CON_DIR_JAVA_ROOT);
        removePostFixFromDirMap(GenesisConstants.CON_DIR_JAVA_DAO_IMPL);
        for (ClassObject classObject : getClassObjects()) {
            VelocityContext context = new VelocityContext();
            Set<String> imports = new HashSet<>();
            for (VariableObject variableObject : classObject.getVariables()) {
                if (variableObject.getType().getImportPackage() != null) {
                    imports.add(variableObject.getType().getImportPackage());
                }
            }
            imports = Utilz.createImports(imports, entityPackage, daoPackage);
            context.put("imports", imports);
            context.put("classObject", classObject);
            context.put("classPackage", classPackage);
            generateFile(getGenDirList().get(GenesisConstants.CON_DIR_JAVA_DAO_IMPL) + classObject.getName().getParcelCaseName() + "DaoImpl.java", TemplateEnum.JAVA_DAO_IMPL.getLocation(), context);
        }
    }

    public void generateJavaServices() throws Exception {
        String dtoPackage = getPackage(GenesisConstants.CON_DIR_JAVA_DTO, GenesisConstants.CON_DIR_JAVA_ROOT);
        String classPackage = getPackage(GenesisConstants.CON_DIR_JAVA_SERVICES, GenesisConstants.CON_DIR_JAVA_ROOT);
        removePostFixFromDirMap(GenesisConstants.CON_DIR_JAVA_SERVICES);
        for (ClassObject classObject : getClassObjects()) {
            VelocityContext context = new VelocityContext();
            Set<String> imports = new HashSet<>();
            for (VariableObject variableObject : classObject.getVariables()) {
                if (variableObject.getType().getImportPackage() != null) {
                    imports.add(variableObject.getType().getImportPackage());
                }
            }
            imports = Utilz.createImports(imports, dtoPackage);
            context.put("imports", imports);
            context.put("classPackage", classPackage);
            context.put("classObject", classObject);
            generateFile(getGenDirList().get(GenesisConstants.CON_DIR_JAVA_SERVICES) + classObject.getName().getParcelCaseName() + "Service.java", TemplateEnum.JAVA_SERVICE.getLocation(), context);
        }
    }

    public void generateJavaServiceImpls() throws Exception {
        String dtoPackage = getPackage(GenesisConstants.CON_DIR_JAVA_DTO, GenesisConstants.CON_DIR_JAVA_ROOT);
        String daoPackage = getPackage(GenesisConstants.CON_DIR_JAVA_DAO, GenesisConstants.CON_DIR_JAVA_ROOT);
        String servicePackage = getPackage(GenesisConstants.CON_DIR_JAVA_SERVICES, GenesisConstants.CON_DIR_JAVA_ROOT);
        String bindingPackage = getPackage(GenesisConstants.CON_DIR_JAVA_ENTITY_BINDING, GenesisConstants.CON_DIR_JAVA_ROOT);
        String classPackage = getPackage(GenesisConstants.CON_DIR_JAVA_SERVICES_IMPL, GenesisConstants.CON_DIR_JAVA_ROOT);
        removePostFixFromDirMap(GenesisConstants.CON_DIR_JAVA_SERVICES_IMPL);
        for (ClassObject classObject : getClassObjects()) {
            VelocityContext context = new VelocityContext();
            Set<String> imports = new HashSet<>();
            for (VariableObject variableObject : classObject.getVariables()) {
                if (variableObject.getType().getImportPackage() != null) {
                    imports.add(variableObject.getType().getImportPackage());
                }
            }
            imports = Utilz.createImports(imports, dtoPackage, servicePackage, daoPackage, bindingPackage);
            context.put("classPackage", classPackage);
            context.put("imports", imports);
            context.put("classObject", classObject);
            generateFile(getGenDirList().get(GenesisConstants.CON_DIR_JAVA_SERVICES_IMPL) + classObject.getName().getParcelCaseName() + "ServiceImpl.java", TemplateEnum.JAVA_SERVICE_IMPL.getLocation(), context);
        }
    }


}
