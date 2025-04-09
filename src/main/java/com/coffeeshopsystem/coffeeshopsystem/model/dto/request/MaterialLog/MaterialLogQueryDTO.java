package com.coffeeshopsystem.coffeeshopsystem.model.dto.request.MaterialLog;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class MaterialLogQueryDTO {
    @Min(value = 1, message = "页码必须大于0")
    private Integer pageNum = 1;

    @Min(value = 1, message = "每页大小必须大于0")
    private Integer pageSize = 10;

    @Pattern(regexp = "^(IN|OUT)$", message = "无效的操作类型")
    private String type;

    private Integer materialId;
    private Integer operatorId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}