package org.swfparser.operation;

import com.jswiff.swfrecords.RegisterParam;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.swfparser.BooleanOperation;
import org.swfparser.Operation;
import org.swfparser.Priority;

import java.util.Collections;
import java.util.List;

public class FunctionParameterOperation implements Operation, BooleanOperation {

    private RegisterParam registerParam;

    public FunctionParameterOperation(RegisterParam registerParam) {
        super();
        this.registerParam = registerParam;
    }

    public int getArgsNumber() {
        return 1;
    }

    public String getStringValue(int level) {
        return registerParam.getParamName();
    }

    public int getPriority() {
        return Priority.HIGHEST;
    }

    public List<Operation> getOperations() {
        return Collections.EMPTY_LIST;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof FunctionParameterOperation)) {
            return false;
        }
        if (obj == this) {
            return true;
        }

        FunctionParameterOperation otherOp = (FunctionParameterOperation) obj;
        return new EqualsBuilder()
                .append(this.registerParam.getRegister(), otherOp.registerParam.getRegister())
                .append(this.registerParam.getParamName(), otherOp.registerParam.getParamName())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(registerParam.getRegister())
                .append(registerParam.getParamName())
                .toHashCode();
    }

    public Operation getInvertedOperation() {
        return new SimpleInvertedOperation(this);
    }
}
