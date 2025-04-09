package com.coffeeshopsystem.coffeeshopsystem.model.dto.response;

import lombok.Data;
import java.util.List;

@Data
public class MenuPageVO {
    private Long total;
    private Long current;
    private Long size;
    private List<MenuVO> records;
}