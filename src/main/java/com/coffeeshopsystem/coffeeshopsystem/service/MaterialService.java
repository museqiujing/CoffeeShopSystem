package com.coffeeshopsystem.coffeeshopsystem.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.coffeeshopsystem.coffeeshopsystem.entity.Material;

import java.math.BigDecimal;
import java.util.List;

public interface MaterialService extends IService<Material> {
    // 添加原材料
    void addMaterial(Material material);

    // 入库操作
    void increaseStock(Integer id, Integer quantity, Integer operatorId, String remark);

    // 出库操作
    void decreaseStock(Integer id, Integer quantity, Integer operatorId, String remark);

    // 分页查询
    Page<Material> getMaterialPage(Integer pageNum, Integer pageSize, String keyword);

    // 获取库存预警列表（库存低于最小库存的材料）
    List<Material> getLowStockMaterials();

    // 更新供应商信息
    void updateSupplier(Integer id, String supplier);

    // 更新最小库存
    void updateMinStock(Integer id, Integer minStock);

    // 更新价格
    void updatePrice(Integer id, BigDecimal price);
}