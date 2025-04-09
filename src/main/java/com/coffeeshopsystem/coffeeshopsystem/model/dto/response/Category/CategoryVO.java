package com.coffeeshopsystem.coffeeshopsystem.model.dto.response.Category;

import com.coffeeshopsystem.coffeeshopsystem.model.dto.response.Menu.MenuVO;
import lombok.Data;
import lombok.Builder;
import java.util.List;

@Data
@Builder
public class CategoryVO {
    private Integer id;
    private String name;
    private String description;
    private Integer sortOrder;
    private String categoryType;

    // 扩展字段，用于前端展示
    private Integer menuCount;        // 该分类下的菜品数量
    private List<MenuVO> menuList;    // 该分类下的菜品列表（可选，用于某些场景）
}