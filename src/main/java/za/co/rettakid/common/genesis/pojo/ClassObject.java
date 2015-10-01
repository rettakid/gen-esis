package za.co.rettakid.common.genesis.pojo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lwazi8 on 26/08/2015.
 */
public class ClassObject implements Cloneable {

    private GeneratedName name;
    private VariableObject primaryKeyVar;
    private List<VariableObject> variables = new ArrayList<>(10);
    private List<ClassObject> references = new ArrayList<>(10);
    private GeneratedName refName;

    public GeneratedName getName() {
        return name;
    }

    public void setName(GeneratedName name) {
        this.name = name;
    }

    public VariableObject getPrimaryKeyVar() {
        return primaryKeyVar;
    }

    public void setPrimaryKeyVar(VariableObject primaryKeyVar) {
        this.primaryKeyVar = primaryKeyVar;
    }

    public List<VariableObject> getVariables() {
        return variables;
    }

    public void addVariables(VariableObject variable) {
        this.variables.add(variable);
    }

    public void addAllVariables(List<VariableObject> variables) {
        this.variables.addAll(variables);
    }

    public List<ClassObject> getReferences() {
        return references;
    }

    public void addReference(ClassObject reference) {
        this.references.add(reference);
    }

    public void addAllReferences(List<ClassObject> references) {
        this.references.addAll(references);
    }

    public GeneratedName getRefName() {
        return refName;
    }

    public void setRefName(GeneratedName refName) {
        this.refName = refName;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
