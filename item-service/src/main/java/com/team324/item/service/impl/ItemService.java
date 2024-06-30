package com.team324.item.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.team324.common.dto.PageDTO;
import com.team324.item.mapper.ItemMapper;
import com.team324.item.pojo.Item;
import com.team324.item.service.IItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ItemService extends ServiceImpl<ItemMapper, Item> implements IItemService {
    @Autowired
    private ItemMapper itemMapper;

    /**
     * 分页查询所有商品
     *
     * @param page
     * @param size
     * @return
     */
    @Override
    public PageDTO<Item> selectItemPage(int page, int size) {

        //创建分页构造器
        Page pageInfo = new Page(page, size);
        //创建条件构造器
        LambdaQueryWrapper<Item> lqw = new LambdaQueryWrapper<>();
        //添加条件
        lqw.orderByDesc(Item::getUpdateTime);
        Page itemPage = itemMapper.selectPage(pageInfo, lqw);
        //获取总条数
        long total = itemPage.getTotal();
        //获取当前页数据
        List itemPageRecords = itemPage.getRecords();

        //返回结果
        return new PageDTO<Item>(total, itemPageRecords);
    }

    /**
     * 根据id查询商品
     *
     * @param id
     * @return
     */
    @Override
    public Item selectItemById(Long id) {

        return itemMapper.selectById(id);
    }

    /**
     * 添加商品
     *
     * @param item
     */
    @Override
    @Transactional
    public void addItem(Item item) {

        itemMapper.insert(item);
    }

    /**
     * 修改商品上架、下架状态
     *
     * @param id
     * @param item
     */
    @Override
    @Transactional
    public void updateStatus(Long id, Item item) {
        //创建条件构造器
        LambdaQueryWrapper<Item> lqw = new LambdaQueryWrapper<>();
        //id相等的条件
        lqw.eq(Item::getId, id);
        //修改状态
        itemMapper.update(item, lqw);
    }

    /**
     * 修改商品
     *
     * @param item
     */
    @Override
    @Transactional
    public void updateItem(Item item) {
        LambdaQueryWrapper<Item> lqw = new LambdaQueryWrapper<>();
        lqw.eq(Item::getId, item.getId());

        itemMapper.update(item, lqw);
    }

    /**
     * 删除商品
     *
     * @param id
     */
    @Override
    @Transactional
    public void deleteItem(Long id) {
        LambdaQueryWrapper<Item> lqw = new LambdaQueryWrapper<>();
        lqw.eq(Item::getId, id);

        itemMapper.delete(lqw);
    }

    /**
     * 减库存
     * @param itemId,num
     */
    @Override
    @Transactional
    public void updateStock(Long itemId,Integer num) {
        QueryWrapper<Item> qw = new QueryWrapper<>();
        qw.select("stock").eq("id",itemId);

        Item item = itemMapper.selectOne(qw);
        //查询出商品库存总数
        Integer stock = item.getStock();

        //计算出减库存后的数量
        int updateStock = stock - num;

        //修改条件构造
        UpdateWrapper<Item> uw = new UpdateWrapper<>();
        uw.eq("id",itemId);
        //指定字段
        uw.set("stock",updateStock);
        //修改
        itemMapper.update(null,uw);
    }

    /**
     * 恢复库存
     * @param itemId
     * @param num
     */
    @Override
    public void updateStock2(Long itemId, Integer num) {
        QueryWrapper<Item> qw = new QueryWrapper<>();
        qw.select("stock").eq("id",itemId);

        Item item = itemMapper.selectOne(qw);
        //查询出商品库存总数
        Integer stock = item.getStock();

        //计算出减库存后的数量
        int updateStock = stock + num;

        //修改条件构造
        UpdateWrapper<Item> uw = new UpdateWrapper<>();
        uw.eq("id",itemId);
        //指定字段
        uw.set("stock",updateStock);
        //修改
        itemMapper.update(null,uw);
    }
}
