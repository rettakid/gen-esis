package za.co.rettakid.common.genesis.pojo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lwazi8 on 26/08/2015.
 */
public class ClassObject {

    private GeneratedName name;
    private VariableObject primaryKeyVar;
    private List<VariableObject> variables = new ArrayList<>(10);
    private List<ReferenceObject> references = new ArrayList<>(10);

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

    public List<ReferenceObject> getReferences() {
        return references;
    }

    public void allReference(ReferenceObject reference) {
        this.references.add(reference);
    }

    public void allAllReferences(List<ReferenceObject> references) {
        this.references.addAll(references);
    }

}
