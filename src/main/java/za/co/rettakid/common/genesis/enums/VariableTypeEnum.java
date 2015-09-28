package za.co.rettakid.common.genesis.enums;

/**
 * Created by LWAZI8 on 26/08/2015.
 */
public enum VariableTypeEnum {

    INT("integer","Integer",null),
    BIGINT("bigint","Long",null),
    VARCHAR("string","String",null),
    BOOLEAN("boolean","Boolean",null),
    FLOAT("float","Float",null),
    DATE("date","Date","import java.util.Date;"),
    TIME("date","Date","import java.util.Date;"),
    DATETIME("datetime","Date","import java.util.Date;"),
    BLOB("string","String",null),
    REF("string","EMPTY",null);

    private String phpName;
    private String javaName;
    private String javaImport;

    private VariableTypeEnum(String phpName,String javaName,String javaImport) {
        this.phpName = phpName;
        this.javaName = javaName;
        this.javaImport = javaImport;
    }

    public String getPhpName() {
        return phpName;
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
