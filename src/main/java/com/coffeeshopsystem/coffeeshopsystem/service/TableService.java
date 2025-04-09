package com.coffeeshopsystem.coffeeshopsystem.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.coffeeshopsystem.coffeeshopsystem.entity.Table;
import com.coffeeshopsystem.coffeeshopsystem.util.Result;
import java.util.List;

public interface TableService extends IService<Table> {
    // 获取空闲餐桌
    Result<List<Table>> getIdleTables();

    // 更新餐桌状态
    Result<Void> updateTableStatus(Integer id, String status);

    // 分页查询餐桌
    Result<Page<Table>> getTablePage(Integer pageNum, Integer pageSize, String status);

    // 根据桌号查询餐桌
    Result<Table> getByTableNumber(String tableNumber);

    // 添加餐桌
    Result<Void> addTable(Table table);
}