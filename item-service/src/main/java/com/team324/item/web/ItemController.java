package com.team324.item.web;

import com.team324.common.MqConstants;
import com.team324.common.dto.PageDTO;
import com.team324.item.pojo.Item;
import com.team324.item.service.IItemService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("item")
public class ItemController {

    @Autowired
    private IItemService itemService;
    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 分页查询所有商品
     *
     * @param page
     * @param size
     * @return
     */
    @GetMapping("/list")
    public PageDTO<Item> itemPage(int page, int size) {
        return itemService.selectItemPage(page, size);
    }

    /**
     * 根据id查询商品
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Item selectById(@PathVariable Long id) {
        return itemService.selectItemById(id);
    }

    /**
     * 添加商品
     */
    @PostMapping
    public void addItem(@RequestBody Item item) {
        itemService.addItem(item);
        //发送MQ消息到队列
        rabbitTemplate.convertAndSend(MqConstants.item_EXCHANGE, MqConstants.item_INSERT_KEY, item.getId());

    }

    /**
     * 修改商品上架、下架状态
     *
     * @param id
     * @param item
     */
    @PutMapping("/status/{id}/{status}")
    public void updateStatus(@PathVariable Long id, Item item) {
        itemService.updateStatus(id, item);
        //发送MQ消息到队列
        if (item.getStatus() == 2) {
            rabbitTemplate.convertAndSend(MqConstants.item_EXCHANGE, MqConstants.item_DELETE_KEY, id);
        } else {
            rabbitTemplate.convertAndSend(MqConstants.item_EXCHANGE, MqConstants.item_INSERT_KEY, item.getId());
        }

    }

    /**
     * 修改商品
     *
     * @param item
     */
    @PutMapping
    public void updateItem(@RequestBody Item item) {
        itemService.updateItem(item);
        //发送MQ消息到队列
        rabbitTemplate.convertAndSend(MqConstants.item_EXCHANGE, MqConstants.item_INSERT_KEY, item.getId());
    }

    /**
     * 删除商品
     *
     * @param id
     */
    @DeleteMapping("/{id}")
    public void deleteItem(@PathVariable Long id) {
        itemService.deleteItem(id);
        //发送MQ消息到队列
        rabbitTemplate.convertAndSend(MqConstants.item_EXCHANGE, MqConstants.item_DELETE_KEY, id);
    }

    /**
     * 修改库存
     * @param itemId
     * @param num
     */
    @PutMapping("/stock/{itemId}/{num}")
    public void updateStock(@PathVariable Long itemId,@PathVariable Integer num){
        itemService.updateStock(itemId,num);

    }

    @PostMapping ("/stock/{itemId}/{num}")
    public void updateStock2(@PathVariable Long itemId,@PathVariable Integer num){
        itemService.updateStock2(itemId,num);

    }

}
