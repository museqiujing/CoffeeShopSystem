package com.coffeeshopsystem.coffeeshopsystem.model.dto.request.Category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class CategoryUpdateDTO {
    @NotNull(message = "分类ID不能为空")
    private Integer id;

    @NotBlank(message = "分类名称不能为空")
    private String name;

    private String description;

    @NotBlank(message = "分类类型不能为空")
    @Pattern(regexp = "^(DESSERT|COFFEE|OTHER)$", message = "无效的分类类型")
    private String categoryType;
}