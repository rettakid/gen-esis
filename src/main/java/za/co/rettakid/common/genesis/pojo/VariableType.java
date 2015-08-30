package za.co.rettakid.common.genesis.pojo;

import za.co.rettakid.common.genesis.enums.VariableTypeEnum;

/**
 * Created by LWAZI8 on 26/08/2015.
 */
public class VariableType {
    private VariableTypeEnum variableTypeEnum;
    private String javaName;
    private String importPackage;

    public VariableType(VariableTypeEnum variableTypeEnum) {
        this.variableTypeEnum = variableTypeEnum;
        this.javaName = variableTypeEnum.getJavaName();
        this.importPackage = variableTypeEnum.getJavaImport();
    }

    public VariableTypeEnum getVariableTypeEnum() {
        return variableTypeEnum;
    }

    public void setVariableTypeEnum(VariableTypeEnum variableTypeEnum) {
        this.variableTypeEnum = variableTypeEnum;
    }

    public String getJavaName() {
        return javaName;
    }

    public void setJavaName(String javaName) {
        this.javaName = javaName;
    }

    public String getImportPackage() {
        return importPackage;
    }

    public void setImportPackage(String importPackage) {
        this.importPackage = importPackage;
    }
}
