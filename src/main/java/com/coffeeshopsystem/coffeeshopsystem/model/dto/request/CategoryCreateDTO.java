package com.coffeeshopsystem.coffeeshopsystem.model.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class CategoryCreateDTO {
    @NotBlank(message = "分类名称不能为空")
    private String name;

    private String description;

    @NotBlank(message = "分类类型不能为空")
    @Pattern(regexp = "^(DESSERT|COFFEE|OTHER)$", message = "无效的分类类型")
    private String categoryType;

    @Min(value = 0, message = "排序值不能小于0")
    private Integer sortOrder = 0;  // 默认值为0
}