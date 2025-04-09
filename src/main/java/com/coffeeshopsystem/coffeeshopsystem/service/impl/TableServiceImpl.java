package com.coffeeshopsystem.coffeeshopsystem.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.coffeeshopsystem.coffeeshopsystem.entity.Table;
import com.coffeeshopsystem.coffeeshopsystem.mapper.TableMapper;
import com.coffeeshopsystem.coffeeshopsystem.service.TableService;
import com.coffeeshopsystem.coffeeshopsystem.util.DateTimeUtil;
import com.coffeeshopsystem.coffeeshopsystem.util.Result;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TableServiceImpl extends ServiceImpl<TableMapper, Table> implements TableService {

    private static final List<String> VALID_STATUS = Arrays.asList("IDLE", "OCCUPIED");
    private final HttpServletRequest request;

    @Override
    public Result<List<Table>> getIdleTables() {
        try {
            List<Table> tables = this.lambdaQuery()
                    .eq(Table::getStatus, "IDLE")
                    .orderByAsc(Table::getTableNumber)
                    .list();
            return Result.ok(tables);
        } catch (Exception e) {
            return Result.error("获取空闲餐桌失败：" + e.getMessage());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<Void> updateTableStatus(Integer id, String status) {
        try {
            // 权限检查
            if (!isStaffOrAdmin()) {
                return Result.error(403, "无权限执行此操作");
            }

            // 参数验证
            if (!VALID_STATUS.contains(status)) {
                return Result.error("无效的餐桌状态");
            }

            Table table = this.getById(id);
            if (table == null) {
                return Result.error("餐桌不存在");
            }

            table.setStatus(status);
            this.updateById(table);
            return Result.ok();
        } catch (Exception e) {
            return Result.error("更新餐桌状态失败：" + e.getMessage());
        }
    }

    @Override
    public Result<Page<Table>> getTablePage(Integer pageNum, Integer pageSize, String status) {
        try {
            // 验证状态参数（如果提供）
            if (StringUtils.hasText(status) && !VALID_STATUS.contains(status)) {
                return Result.error("无效的餐桌状态");
            }

            Page<Table> page = this.lambdaQuery()
                    .eq(StringUtils.hasText(status), Table::getStatus, status)
                    .orderByAsc(Table::getTableNumber)
                    .page(new Page<>(pageNum, pageSize));

            return Result.ok(page);
        } catch (Exception e) {
            return Result.error("查询餐桌列表失败：" + e.getMessage());
        }
    }

    @Override
    public Result<Table> getByTableNumber(String tableNumber) {
        try {
            if (!StringUtils.hasText(tableNumber)) {
                return Result.error("餐桌号不能为空");
            }

            Table table = this.lambdaQuery()
                    .eq(Table::getTableNumber, tableNumber)
                    .one();

            if (table == null) {
                return Result.error("餐桌不存在");
            }

            return Result.ok(table);
        } catch (Exception e) {
            return Result.error("查询餐桌失败：" + e.getMessage());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<Void> addTable(Table table) {
        try {
            // 权限检查
            if (!isStaffOrAdmin()) {
                return Result.error(403, "无权限执行此操作");
            }

            // 参数验证
            if (!validateTableParams(table)) {
                return Result.error("参数验证失败");
            }

            // 检查桌号是否已存在
            Table existingTable = this.lambdaQuery()
                    .eq(Table::getTableNumber, table.getTableNumber())
                    .one();

            if (existingTable != null) {
                return Result.error("该桌号已存在");
            }

            // 设置默认值
            table.setStatus("IDLE");
            table.setCreatedAt(DateTimeUtil.getCurrentDateTime());

            this.save(table);
            return Result.ok();
        } catch (Exception e) {
            return Result.error("添加餐桌失败：" + e.getMessage());
        }
    }

    // 验证餐桌参数
    private boolean validateTableParams(Table table) {
        if (!StringUtils.hasText(table.getTableNumber())) {
            return false;
        }
        if (table.getSeats() == null || table.getSeats() <= 0) {
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