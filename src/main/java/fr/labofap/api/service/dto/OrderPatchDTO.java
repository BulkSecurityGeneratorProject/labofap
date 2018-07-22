package fr.labofap.api.service.dto;

import fr.labofap.api.domain.enumeration.CommandStatus;

import java.math.BigDecimal;

public class OrderPatchDTO {

    private CommandStatus status;

    private BigDecimal amount;

    public CommandStatus getStatus() {
        return status;
    }

    public void setStatus(CommandStatus status) {
        this.status = status;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
