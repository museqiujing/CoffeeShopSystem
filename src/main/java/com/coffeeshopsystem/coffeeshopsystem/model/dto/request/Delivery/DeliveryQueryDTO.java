package com.coffeeshopsystem.coffeeshopsystem.model.dto.request.Delivery;

import jakarta.validation.constraints.Min;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class DeliveryQueryDTO {
    @Min(value = 1, message = "页码必须大于0")
    private Integer pageNum = 1;

    @Min(value = 1, message = "每页大小必须大于0")
    private Integer pageSize = 10;

    private String status;
    private Integer delivererId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}