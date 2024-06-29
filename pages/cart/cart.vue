<template>
	<view>
	<view class="cart-container" v-if="cart.length !== 0">
		<!-- 购物车商品列表的标题区域 -->
		<view class="cart-title">
		  <!-- 左侧的图标 -->
		  <uni-icons type="shop" size="18"></uni-icons>
		  <!-- 描述文本 -->
		  <text class="cart-title-text">购物车</text>
		  <!-- 商品列表区域 -->
		  <uni-swipe-action>
		  <block v-for="(goods, i) in cart" :key="i">
		     <uni-swipe-action-item :options="options" @click="swipeActionClickHandler(goods)">
		        <my-goods :goods="goods" :show-radio="true" :show-num="true" @radio-change="radioChangeHandler" @num-change="numberChangeHandler"></my-goods>
		     </uni-swipe-action-item>
		  </block>
		  </uni-swipe-action>
		</view>
	  <!-- 结算区域 -->
	    <my-settle></my-settle>
	</view>
	  <!-- 空白购物车区域 -->
	  <view class="empty-cart" v-else>
	    <image src="/static/cart_empty@2x.png" class="empty-img"></image>
	    <text class="tip-text">空空如也~</text>
	  </view>
	  </view>
</template>

<script>
	import { mapState, mapMutations,mapGetters } from 'vuex'
	import badgeMix from '@/mixins/tabbar-badge.js'
	export default {
		mixins: [badgeMix],
		data() {
			return {
			  options: [{
			      text: '删除', // 显示的文本内容
			      style: {
			        backgroundColor: '#C00000' // 按钮的背景颜色
			      }
			    }]
			}
		},
		computed: {
			// 将 m_cart 模块中的 total 映射为当前页面的计算属性
			...mapGetters('m_cart', ['total']),
			 ...mapState('m_cart', ['cart']),
		},
		onShow() {
		   // 在页面刚展示的时候，设置数字徽标
		   this.setBadge()
		},
		methods: {
			// 点击了滑动操作按钮
			swipeActionClickHandler(goods) {
			  console.log(goods)
			},
			 setBadge() {
			      // 调用 uni.setTabBarBadge() 方法，为购物车设置右上角的徽标
			      uni.setTabBarBadge({
			         index: 2, // 索引
			         text: this.total + '' // 注意：text 的值必须是字符串，不能是数字
			      })
			   },
			 radioChangeHandler(e) {
			   this.updateGoodsState(e) // 输出得到的数据 -> {goods_id: 395, goods_state: false}
			  },
			// 商品的数量发生了变化
			  numberChangeHandler(e) {
			    this.updateGoodsCount(e)
			  },
			  // 点击了滑动操作按钮
			  swipeActionClickHandler(goods) {
			    this.removeGoodsById(goods.goods_id)
			  },
            ...mapMutations('m_cart', ['updateGoodsState', 'updateGoodsCount', 'removeGoodsById']),
		}
	}
</script>

<style lang="scss">
.cart-container {
	padding-bottom: 50px;
}
.cart-title {
  height: 40px;
  display: flex;
  align-items: center;
  flex-wrap: wrap;
  font-size: 14px;
  padding-left: 5px;
  border-bottom: 1px solid #efefef;
  .cart-title-text {
    margin-left: 10px;
  }
}
.empty-cart {
  display: flex;
  flex-direction: column;
  align-items: center;
  padding-top: 150px;

  .empty-img {
    width: 90px;
    height: 90px;
  }

  .tip-text {
    font-size: 12px;
    color: gray;
    margin-top: 15px;
  }
}
</style>