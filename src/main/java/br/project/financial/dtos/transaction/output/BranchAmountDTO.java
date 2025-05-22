package br.project.financial.dtos.transaction.output;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class BranchAmountDTO {
    private String branch;
    private BigDecimal amount;

    public BranchAmountDTO(String branch, BigDecimal amount) {
        this.branch = branch;
        this.amount = amount;
    }
}
