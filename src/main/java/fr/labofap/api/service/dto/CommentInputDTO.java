package fr.labofap.api.service.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * A DTO for the Comment entity.
 */
@ApiModel(value = "CommentInputDTO")
public class CommentInputDTO implements Serializable {

    private String content;

    @ApiModelProperty(name = "orderId")
    private Long orderId;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

}
