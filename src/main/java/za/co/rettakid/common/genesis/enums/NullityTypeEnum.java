package za.co.rettakid.common.genesis.enums;

/**
 * Created by LWAZI8 on 26/08/2015.
 */
public enum NullityTypeEnum {
    NOT_NULL("NOT NULL"),
    NULL("NULL");

    private String sqlName;

    NullityTypeEnum(String sqlName) {
        this.sqlName = sqlName;
    }

    public String getSqlName() {
        return sqlName;
    }

    public static NullityTypeEnum setEnum(String value) {
        for (NullityTypeEnum nullityTypeEnum : NullityTypeEnum.values()) {
            if (nullityTypeEnum.getSqlName().equals(value)) {
                return nullityTypeEnum;
            }
        }
        return null;
    }

}
