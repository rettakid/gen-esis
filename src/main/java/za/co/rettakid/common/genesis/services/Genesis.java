package za.co.rettakid.common.genesis.services;

/**
 * Created by lwazi8 on 26/08/2015.
 */
public class Genesis {

    private static String schemeFilePath = "src\\main\\resources\\test\\scheme.sql";
    private static String structureFilePath = "src\\main\\resources\\test\\structure.txt";
    private static String javaPackageDto = "za.co.rettakid";
    private static String dbName;

    public static void main(String[] args) throws Exception {
        StructureGenerator structureGenerator = new StructureGenerator();
        structureGenerator.generatorStructure(structureFilePath);

        SchemaDecoder schemaDecoder = new SchemaDecoder();
        schemaDecoder.decodeScheme(schemeFilePath);

        FileGenerator fileGenerator = new FileGenerator(schemaDecoder.getClassObjects(),structureGenerator.getGenDirList(),schemaDecoder.getDatabaseName());
        fileGenerator.generateAndroidBaseClient();
        fileGenerator.generateAndroidClients();
        fileGenerator.generateAndroidDtos();
        fileGenerator.generateAndroidVos();
        fileGenerator.generateAndroidBinding();
        fileGenerator.generatePhpBindings();
        fileGenerator.generatePhpCommon();
        fileGenerator.generatePhpBaseDto();
        fileGenerator.generatePhpDto();
        fileGenerator.generatePhpControllers();
        fileGenerator.generatePhpEntities();
        fileGenerator.generatePhpXML();

    }

}
