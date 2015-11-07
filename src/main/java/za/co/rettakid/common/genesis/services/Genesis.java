package za.co.rettakid.common.genesis.services;

import za.co.rettakid.common.genesis.services.generetor.AndroidFileGenerator;
import za.co.rettakid.common.genesis.services.generetor.JavaFileGenerator;
import za.co.rettakid.common.genesis.services.generetor.PhpFileGenerator;

/**
 * Created by lwazi8 on 26/08/2015.
 */
public class Genesis {

    public static void main(String[] args) throws Exception {
        StructureGenerator structureGenerator = new StructureGenerator();
        structureGenerator.generatorStructure(args[1]);

        SchemaDecoder schemaDecoder = new SchemaDecoder();
        schemaDecoder.decodeScheme(args[0]);

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
        javaFileGenerator.generateJavaBinding();
        javaFileGenerator.generateJavaServiceImpls();
        javaFileGenerator.generateJavaServices();
        javaFileGenerator.generateJavaDtos();
        javaFileGenerator.generateJavaDaoImpls();
        javaFileGenerator.generateJavaDaos();
        javaFileGenerator.generateJavaEntities();

    }

}
