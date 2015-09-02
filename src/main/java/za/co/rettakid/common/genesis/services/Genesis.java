package za.co.rettakid.common.genesis.services;

/**
 * Created by lwazi8 on 26/08/2015.
 */
public class Genesis {

    private static String schemeFilePath = "src\\main\\resources\\test\\scheme.sql";
    private static String structureFilePath = "src\\main\\resources\\test\\structure.txt";
    private static String javaPackageDto = "za.co.rettakid";

    public static void main(String[] args) throws Exception {
        StructureGenerator structureGenerator = new StructureGenerator();
        structureGenerator.generatorStructure(structureFilePath);

        FileGenerator fileGenerator = new FileGenerator(SchemaDecoder.decodeScheme(schemeFilePath),structureGenerator.getGenDirList());
        fileGenerator.generateAndroidBaseClient();
        fileGenerator.generateAndroidClients();
        fileGenerator.generateAndroidDtos();
        fileGenerator.generatePhpBindings();
        fileGenerator.generatePhpCommon();
        fileGenerator.generatePhpBaseDto();
        fileGenerator.generatePhpDto();
        fileGenerator.generatePhpControlers();
        fileGenerator.generatePhpEntities();
        fileGenerator.generatePhpXML();

    }

}
