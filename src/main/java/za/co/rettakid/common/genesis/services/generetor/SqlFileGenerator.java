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
 * Created by LWAZI8 on 14/12/2015.
 */
public class SqlFileGenerator extends BaseFileGenerator {

    public SqlFileGenerator(List<ClassObject> classObjects, Map<String, String> genDirList, GeneratedName databaseName) {
        super(classObjects, genDirList, databaseName);
    }

    public void generateSchema() throws Exception {
        removePostFixFromDirMap(GenesisConstants.CON_DIR_SQL_SCHEME_FILE);
        VelocityContext context = new VelocityContext();
        context.put("databaseName", getDatabaseName());
        context.put("tables", getClassObjects());
        generateFile(getGenDirList().get(GenesisConstants.CON_DIR_SQL_SCHEME_FILE) + "audit schema.sql", TemplateEnum.SQL_FILE.getLocation(), context);
    }

}
