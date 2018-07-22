package fr.labofap.api.service.dto;


import fr.labofap.api.domain.enumeration.CommandStatus;
import io.swagger.annotations.ApiModel;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the Command entity.
 */
@ApiModel(value = "OrderInputDTO")
public class OrderInputDTO implements Serializable {

    private String title;

    private CommandStatus status;

    private BigDecimal amount;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

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
