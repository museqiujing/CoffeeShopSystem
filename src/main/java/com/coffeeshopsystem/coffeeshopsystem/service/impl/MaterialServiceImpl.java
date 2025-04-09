package com.coffeeshopsystem.coffeeshopsystem.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.coffeeshopsystem.coffeeshopsystem.entity.Material;
import com.coffeeshopsystem.coffeeshopsystem.entity.MaterialLog;
import com.coffeeshopsystem.coffeeshopsystem.mapper.MaterialMapper;
import com.coffeeshopsystem.coffeeshopsystem.mapper.MaterialLogMapper;
import com.coffeeshopsystem.coffeeshopsystem.service.MaterialService;
import com.coffeeshopsystem.coffeeshopsystem.util.DateTimeUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MaterialServiceImpl extends ServiceImpl<MaterialMapper, Material> implements MaterialService {

    private static final int PRICE_SCALE = 2;

    private final MaterialLogMapper materialLogMapper;
    private final HttpServletRequest request;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addMaterial(Material material) {
        // 权限检查
        if (!isStaffOrAdmin()) {
            throw new RuntimeException("无权限添加原材料");
        }

        // 参数验证
        if (!validateMaterialParams(material)) {
            throw new RuntimeException("参数验证失败");
        }

        // 检查名称是否重复
        long count = this.lambdaQuery()
                .eq(Material::getName, material.getName())
                .count();

        if (count > 0) {
            throw new RuntimeException("原材料名称已存在");
        }

        log.info("User {} adding new material: {}", "museqiujing", material.getName());

        // 设置初始值
        material.setQuantity(0);
        material.setUpdatedAt(DateTimeUtil.getCurrentDateTime());

        this.save(material);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void increaseStock(Integer id, Integer quantity, Integer operatorId, String remark) {
        if (quantity <= 0) {
            throw new RuntimeException("入库数量必须大于0");
        }

        Material material = this.getById(id);
        if (material == null) {
            throw new RuntimeException("原材料不存在");
        }

        log.info("User {} increasing stock for material {}, quantity: {}",
                "museqiujing", material.getName(), quantity);

        // 更新库存
        material.setQuantity(material.getQuantity() + quantity);
        material.setUpdatedAt(DateTimeUtil.getCurrentDateTime());
        this.updateById(material);

        // 记录入库日志
        MaterialLog log = new MaterialLog();
        log.setMaterialId(id);
        log.setType("IN");
        log.setQuantity(quantity);
        log.setOperatorId(operatorId);
        log.setCreatedAt(DateTimeUtil.getCurrentDateTime());
        log.setRemark(remark);
        materialLogMapper.insert(log);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void decreaseStock(Integer id, Integer quantity, Integer operatorId, String remark) {
        if (quantity <= 0) {
            throw new RuntimeException("出库数量必须大于0");
        }

        Material material = this.getById(id);
        if (material == null) {
            throw new RuntimeException("原材料不存在");
        }

        // 检查库存是否充足
        if (material.getQuantity() < quantity) {
            throw new RuntimeException("库存不足");
        }

        log.info("User {} decreasing stock for material {}, quantity: {}",
                "museqiujing", material.getName(), quantity);

        // 更新库存
        material.setQuantity(material.getQuantity() - quantity);
        material.setUpdatedAt(DateTimeUtil.getCurrentDateTime());
        this.updateById(material);

        // 记录出库日志
        MaterialLog log = new MaterialLog();
        log.setMaterialId(id);
        log.setType("OUT");
        log.setQuantity(quantity);
        log.setOperatorId(operatorId);
        log.setCreatedAt(DateTimeUtil.getCurrentDateTime());
        log.setRemark(remark);
        materialLogMapper.insert(log);
    }

    @Override
    public Page<Material> getMaterialPage(Integer pageNum, Integer pageSize, String keyword) {
        return this.lambdaQuery()
                .like(StringUtils.hasText(keyword), Material::getName, keyword)
                .or()
                .like(StringUtils.hasText(keyword), Material::getSupplier, keyword)
                .orderByAsc(Material::getName)
                .page(new Page<>(pageNum, pageSize));
    }

    @Override
    public List<Material> getLowStockMaterials() {
        return this.lambdaQuery()
                .apply("quantity <= min_stock")
                .orderByAsc(Material::getQuantity)
                .list();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateSupplier(Integer id, String supplier) {
        if (!StringUtils.hasText(supplier)) {
            throw new RuntimeException("供应商信息不能为空");
        }

        Material material = this.getById(id);
        if (material == null) {
            throw new RuntimeException("原材料不存在");
        }

        log.info("User {} updating supplier for material {} to {}",
                "museqiujing", material.getName(), supplier);

        material.setSupplier(supplier);
        material.setUpdatedAt(DateTimeUtil.getCurrentDateTime());
        this.updateById(material);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateMinStock(Integer id, Integer minStock) {
        if (minStock < 0) {
            throw new RuntimeException("最小库存不能小于0");
        }

        Material material = this.getById(id);
        if (material == null) {
            throw new RuntimeException("原材料不存在");
        }

        log.info("User {} updating min stock for material {} to {}",
                "museqiujing", material.getName(), minStock);

        material.setMinStock(minStock);
        material.setUpdatedAt(DateTimeUtil.getCurrentDateTime());
        this.updateById(material);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updatePrice(Integer id, BigDecimal price) {
        if (price.compareTo(BigDecimal.ZERO) < 0) {
            throw new RuntimeException("价格不能小于0");
        }

        Material material = this.getById(id);
        if (material == null) {
            throw new RuntimeException("原材料不存在");
        }

        log.info("User {} updating price for material {} to {}",
                "museqiujing", material.getName(), price);

        material.setPrice(price.setScale(PRICE_SCALE, RoundingMode.HALF_UP));
        material.setUpdatedAt(DateTimeUtil.getCurrentDateTime());
        this.updateById(material);
    }

    // 验证原材料参数
    private boolean validateMaterialParams(Material material) {
        if (!StringUtils.hasText(material.getName())) {
            return false;
        }
        if (!StringUtils.hasText(material.getUnit())) {
            return false;
        }
        if (!StringUtils.hasText(material.getSupplier())) {
            return false;
        }
        if (material.getMinStock() == null || material.getMinStock() < 0) {
            return false;
        }
        if (material.getPrice() == null || material.getPrice().compareTo(BigDecimal.ZERO) < 0) {
            return false;
        }
        return true;
    }

    // 检查是否为员工或管理员
    private boolean isStaffOrAdmin() {
        Object role = request.getAttribute("role");
        return "STAFF".equals(role) || "ADMIN".equals(role);
    }
}