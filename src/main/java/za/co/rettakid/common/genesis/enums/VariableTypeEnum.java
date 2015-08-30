package za.co.rettakid.common.genesis.enums;

/**
 * Created by LWAZI8 on 26/08/2015.
 */
public enum VariableTypeEnum {

    INT("Integer",null),
    VARCHAR("String",null),
    BOOLEAN("Boolean",null),
    FLOAT("Float",null),
    DATE("Date","import java.util.Date;"),
    REF("EMPTY",null);

    private String javaName;
    private String javaImport;

    private VariableTypeEnum(String javaName,String javaImport) {
        this.javaName = javaName;
        this.javaImport = javaImport;
    }

    public String getJavaName() {
        return javaName;
    }

    public String getJavaImport()   {
        return javaImport;
    }

    public static VariableTypeEnum setEnum(String value) {
        for (VariableTypeEnum variableTypeEnum : VariableTypeEnum.values()) {
            if (variableTypeEnum.name().equals(value)) {
                return variableTypeEnum;
            }
        }
        return null;
    }

}
