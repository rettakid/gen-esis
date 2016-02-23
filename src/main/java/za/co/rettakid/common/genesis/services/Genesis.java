package za.co.rettakid.common.genesis.services;

import za.co.rettakid.common.genesis.services.generetor.AndroidFileGenerator;
import za.co.rettakid.common.genesis.services.generetor.JavaFileGenerator;
import za.co.rettakid.common.genesis.services.generetor.PhpFileGenerator;
import za.co.rettakid.common.genesis.services.generetor.SqlFileGenerator;

/**
 * Created by lwazi8 on 26/08/2015.
 */
public class Genesis {

    public static void main(String[] args) throws Exception {
        StructureGenerator structureGenerator = new StructureGenerator();
        structureGenerator.generatorStructure(args[1]);

        SchemaDecoder schemaDecoder = new SchemaDecoder();
        schemaDecoder.decodeScheme(args[0]);

        SqlFileGenerator sqlFileGenerator = new SqlFileGenerator(schemaDecoder.getClassObjects(),structureGenerator.getGenDirList(),schemaDecoder.getDatabaseName());
        sqlFileGenerator.generateSchema();

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
        javaFileGenerator.generateJavaRest();
        javaFileGenerator.generateJavaServiceImpls();
        javaFileGenerator.generateJavaServices();
        javaFileGenerator.generateJavaDtos();
        javaFileGenerator.generateJavaVos();
        javaFileGenerator.generateJavaDaoImpls();
        javaFileGenerator.generateJavaDaos();
        javaFileGenerator.generateJavaDaoTests();
        javaFileGenerator.generateJavaEntities();
        javaFileGenerator.generateJavaEntitiesTests();

    }

}
