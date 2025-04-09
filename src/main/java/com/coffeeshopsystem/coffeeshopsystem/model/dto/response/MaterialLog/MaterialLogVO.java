package com.coffeeshopsystem.coffeeshopsystem.model.dto.response.MaterialLog;

import lombok.Data;
import lombok.Builder;
import java.time.LocalDateTime;

@Data
@Builder
public class MaterialLogVO {
    private Integer id;
    private Integer materialId;
    private String type;
    private Integer quantity;
    private Integer operatorId;
    private LocalDateTime createdAt;
    private String remark;

    // 扩展字段
    private String materialName;       // 原材料名称
    private String materialUnit;       // 原材料单位
    private String operatorName;       // 操作员姓名
    private String typeText;          // 操作类型中文描述
    private Integer stockAfter;       // 操作后库存

    // 获取操作类型的中文描述
    public String getTypeText() {
        return switch (type) {
            case "IN" -> "入库";
            case "OUT" -> "出库";
            default -> type;
        };
    }
}