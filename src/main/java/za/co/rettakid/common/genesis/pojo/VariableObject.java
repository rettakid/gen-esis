package za.co.rettakid.common.genesis.pojo;

import za.co.rettakid.common.genesis.enums.NullityTypeEnum;

/**
 * Created by lwazi8 on 26/08/2015.
 */
public class VariableObject {

    private GeneratedName name;
    private GeneratedName orgName;
    private VariableType type;
    private Integer length;
    private NullityTypeEnum nullity;
    private Boolean isPk = false;
    private VariableObject reference;
    private ClassObject classObject;

    public ClassObject getClassObject() {
        return classObject;
    }

    public void setClassObject(ClassObject classObject) {
        this.classObject = classObject;
    }

    public GeneratedName getName() {
        return name;
    }

    public void setName(GeneratedName name) {
        this.name = name;
    }

    public GeneratedName getOrgName() {
        return orgName;
    }

    public void setOrgName(GeneratedName orgName) {
        this.orgName = orgName;
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

    public Boolean getIsPk() {
        return isPk;
    }

    public void setIsPk(Boolean isPk) {
        this.isPk = isPk;
    }

    public VariableObject getReference() {
        return reference;
    }

    public void setReference(VariableObject reference) {
        this.reference = reference;
    }
}
