package za.co.rettakid.common.genesis.services;

import za.co.rettakid.common.genesis.services.generetor.AndroidFileGenerator;
import za.co.rettakid.common.genesis.services.generetor.JavaFileGenerator;
import za.co.rettakid.common.genesis.services.generetor.PhpFileGenerator;

/**
 * Created by lwazi8 on 26/08/2015.
 */
public class Genesis {

    private static String schemeFilePath = "src\\main\\resources\\test\\scheme.sql";
    private static String structureFilePath = "src\\main\\resources\\test\\structure.txt";

    public static void main(String[] args) throws Exception {
        StructureGenerator structureGenerator = new StructureGenerator();
        structureGenerator.generatorStructure(structureFilePath);

        SchemaDecoder schemaDecoder = new SchemaDecoder();
        schemaDecoder.decodeScheme(schemeFilePath);

        AndroidFileGenerator androidFileGenerator = new AndroidFileGenerator(schemaDecoder.getClassObjects(),structureGenerator.getGenDirList(),schemaDecoder.getDatabaseName());
        androidFileGenerator.generateAndroidBaseClient();
        androidFileGenerator.generateAndroidClients();
        androidFileGenerator.generateAndroidBinding();
        androidFileGenerator.generateAndroidDtos();
        androidFileGenerator.generateAndroidVos();

        PhpFileGenerator phpFileGenerator = new PhpFileGenerator(schemaDecoder.getClassObjects(),structureGenerator.getGenDirList(),schemaDecoder.getDatabaseName());
        phpFileGenerator.generatePhpBindings();
        phpFileGenerator.generatePhpCommon();
        phpFileGenerator.generatePhpBaseDto();
        phpFileGenerator.generatePhpDto();
        phpFileGenerator.generatePhpControllers();
        phpFileGenerator.generatePhpEntities();
        phpFileGenerator.generatePhpXML();

        JavaFileGenerator javaFileGenerator = new JavaFileGenerator(schemaDecoder.getClassObjects(),structureGenerator.getGenDirList(),schemaDecoder.getDatabaseName());
        javaFileGenerator.generateJavaEntityBinding();
        javaFileGenerator.generateJavaVoBinding();
        javaFileGenerator.generateJavaServiceImpls();
        javaFileGenerator.generateJavaServices();
        javaFileGenerator.generateJavaDtos();
        javaFileGenerator.generateJavaVos();
        javaFileGenerator.generateJavaDaoImpls();
        javaFileGenerator.generateJavaDaos();
        javaFileGenerator.generateJavaEntities();

    }

}
