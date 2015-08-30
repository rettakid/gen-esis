package za.co.rettakid.common.genesis.pojo;

import za.co.rettakid.common.genesis.enums.NullityTypeEnum;

/**
 * Created by lwazi8 on 26/08/2015.
 */
public class VariableObject {

    private GeneratedName name;
    private VariableType type;
    private Integer length;
    private NullityTypeEnum nullity;

    public GeneratedName getName() {
        return name;
    }

    public void setName(GeneratedName name) {
        this.name = name;
    }

    public VariableType getType() {
        return type;
    }

    public void setType(VariableType type) {
        this.type = type;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public NullityTypeEnum getNullity() {
        return nullity;
    }

    public void setNullity(NullityTypeEnum nullity) {
        this.nullity = nullity;
    }

}
