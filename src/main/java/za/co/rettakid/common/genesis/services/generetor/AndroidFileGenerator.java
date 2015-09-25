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
public class AndroidFileGenerator extends BaseFileGenerator {

    public AndroidFileGenerator(List<ClassObject> classObjects, Map<String, String> genDirList, GeneratedName databaseName) {
        super(classObjects, genDirList, databaseName);
    }

    public void generateAndroidDtos() throws Exception {
        String classPackage = getPackage(GenesisConstants.CON_DIR_ANDROID_DTO,GenesisConstants.CON_DIR_ANDROID_ROOT);
        removePostFixFromDirMap(GenesisConstants.CON_DIR_ANDROID_DTO);
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
            generateFile(getGenDirList().get(GenesisConstants.CON_DIR_ANDROID_DTO) + classObject.getName().getParcelCaseName() + "Dto.java", TemplateEnum.ANDROID_DTO.getLocation(), context);
            generateFile(getGenDirList().get(GenesisConstants.CON_DIR_ANDROID_DTO) + classObject.getName().getParcelCaseName() + "ListDto.java", TemplateEnum.ANDROID_LIST_DTO.getLocation(), context);
        }
    }

    public void generateAndroidVos() throws Exception {
        String classPackage = getPackage(GenesisConstants.CON_DIR_ANDROID_VO,GenesisConstants.CON_DIR_ANDROID_ROOT);
        removePostFixFromDirMap(GenesisConstants.CON_DIR_ANDROID_VO);
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
            generateFile(getGenDirList().get(GenesisConstants.CON_DIR_ANDROID_VO) + classObject.getName().getParcelCaseName() + "Vo.java", TemplateEnum.ANDROID_VO.getLocation(), context);
        }
    }

    public void generateAndroidBinding() throws Exception {
        String classPackage = getPackage(GenesisConstants.CON_DIR_ANDROID_BINDING,GenesisConstants.CON_DIR_ANDROID_ROOT);
        String voPackage = getPackage(GenesisConstants.CON_DIR_ANDROID_VO,GenesisConstants.CON_DIR_ANDROID_ROOT);
        String dtoPackage = getPackage(GenesisConstants.CON_DIR_ANDROID_DTO,GenesisConstants.CON_DIR_ANDROID_ROOT);
        removePostFixFromDirMap(GenesisConstants.CON_DIR_ANDROID_BINDING);
        for (ClassObject classObject : getClassObjects()) {
            VelocityContext context = new VelocityContext();
            context.put("voPackage", voPackage);
            context.put("dtoPackage", dtoPackage);
            context.put("classPackage", classPackage);
            context.put("classObject", classObject);
            generateFile(getGenDirList().get(GenesisConstants.CON_DIR_ANDROID_BINDING) + "Bind" + classObject.getName().getParcelCaseName() + ".java", TemplateEnum.ANDROID_BINDING.getLocation(), context);
        }
    }

    public void generateAndroidClients() throws Exception {
        String classPackage = getPackage(GenesisConstants.CON_DIR_ANDROID_CLIENT,GenesisConstants.CON_DIR_ANDROID_ROOT);
        String dtoPackage = getPackage(GenesisConstants.CON_DIR_ANDROID_DTO,GenesisConstants.CON_DIR_ANDROID_ROOT);
        removePostFixFromDirMap(GenesisConstants.CON_DIR_ANDROID_CLIENT);
        for (ClassObject classObject : getClassObjects()) {
            VelocityContext context = new VelocityContext();
            context.put("dtoPackage", dtoPackage);
            context.put("classPackage", classPackage);
            context.put("classObject", classObject);
            generateFile(getGenDirList().get(GenesisConstants.CON_DIR_ANDROID_CLIENT) + classObject.getName().getParcelCaseName() + "Client.java", TemplateEnum.ANDROID_CLIENT.getLocation(), context);
        }
    }

    public void generateAndroidBaseClient() throws Exception {
        String classPackage = getPackage(GenesisConstants.CON_DIR_ANDROID_DTO,GenesisConstants.CON_DIR_ANDROID_ROOT);
        removePostFixFromDirMap(GenesisConstants.CON_DIR_ANDROID_CLIENT);
        VelocityContext context = new VelocityContext();
        context.put("classPackage", classPackage);
        context.put("databaseName",getClassObjects());
        generateFile(getGenDirList().get(GenesisConstants.CON_DIR_ANDROID_CLIENT) + "BaseClient.java", TemplateEnum.ANDROID_BASE_CLIENT.getLocation(), context);
    }

}
