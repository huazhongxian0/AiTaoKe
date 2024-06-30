package com.team324.item.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.team324.common.dto.PageDTO;
import com.team324.item.pojo.Item;

public interface IItemService extends IService<Item> {
    PageDTO<Item> selectItemPage(int page, int size);

    Item selectItemById(Long id);

    void addItem(Item item);

    void updateStatus(Long id, Item item);

    void updateItem(Item item);

    void deleteItem(Long id);

    void updateStock(Long itemId,Integer num);

    void updateStock2(Long itemId, Integer num);
}
