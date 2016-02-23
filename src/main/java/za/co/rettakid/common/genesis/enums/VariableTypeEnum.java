package za.co.rettakid.common.genesis.enums;

/**
 * Created by LWAZI8 on 26/08/2015.
 */
public enum VariableTypeEnum {

    INT("integer","Integer",null,"256"),
    BIGINT("bigint","Long",null,"512L"),
    VARCHAR("string","String",null,"\"One Thousand\""),
    BOOLEAN("boolean","Boolean",null,"false"),
    FLOAT("float","Float",null,"2048f"),
    DATE("date","Date","import java.util.Date;","new Date()"),
    TIME("time","Date","import java.util.Date;","new Date()"),
    DATETIME("datetime","Date","import java.util.Date;","new Date()"),
    BLOB("string","String",null,"\"this should be a long string\""),
    REF("string","EMPTY",null,"null");

    private String phpName;
    private String javaName;
    private String javaImport;
    private String javaTestValue;

    private VariableTypeEnum(String phpName,String javaName,String javaImport,String javaTestValue) {
        this.phpName = phpName;
        this.javaName = javaName;
        this.javaImport = javaImport;
        this.javaTestValue = javaTestValue;
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

    public String getJavaTestValue() {
        return javaTestValue;
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
