<template>
	<view class="content">
		<view class="search-box" style="width: 100%;">
		  <my-search @click="gotoSearch" style="width: 100%;"></my-search>
		</view>
		<!-- 轮播图区域 -->
		<swiper :indicator-dots="true" :autoplay="true" :interval="3000" :duration="1000" :circular="true" >
			<swiper-item v-for="(item,index) in swiperList" :key="index">
			   <navigator class="swiperItem" :url="'/subpkg/goods_detail/goods_detail?goods_id=' + item.goods_id">
			    <image :src="item.image_src"></image>	
			   </navigator>
		    </swiper-item>
		</swiper>
		<!-- 分类导航区域 -->
		<view class="navList">
			<view class="navItem" v-for="(item,index) in navList" :key="index" @click="navClickHandler(item)">
				<image :src="item.image_src" class="navImg"></image>
			</view>
		</view>
		<!-- 楼层区域 -->
		<view class="floor-list">
			<view class="floor-item" v-for="(item,index) in floorList">
				
				<image :src="item.floor_title.image_src" class="floor-title" ></image>
				<!-- 楼层图片区域 -->
				<view class="floor-img-box">
				  <!-- 左侧大图片的盒子 -->
				  <navigator class="left-img-box" :url="item.product_list[0].url">
				    <image :src="item.product_list[0].image_src" :style="{width: item.product_list[0].image_width + 'rpx'}" mode="widthFix"></image>
				  </navigator>
				  <!-- 右侧 4 个小图片的盒子 -->
				  <view class="right-img-box">
				    <navigator class="right-img-item" v-for="(item2, i2) in item.product_list" :key="i2" v-if="i2 !== 0" :url="item2.url">
				      <image :src="item2.image_src" mode="widthFix" :style="{width: item2.image_width + 'rpx'}"></image>
				    </navigator>
				  </view>
				</view>
			</view>
		</view>
	</view>
</template> 

<script>
	import badgeMix from '@/mixins/tabbar-badge.js'
	export default {
		mixins: [badgeMix],
		data() {
			return {
                switch: true,
				swiperList:[],
				navList:[],
				floorList:[]
			}
		},
		computed:{
	
		},
		async created (){
     		 await this.getSwiperList()
			 await this.getNavList()
			 await this.getFloorList()
		},
		methods: {
           //获取轮播图
		   async getSwiperList(){
			    const {data: res} = await uni.$http.get('/api/public/v1/home/swiperdata')
			    if (res.meta.status !== 200) return uni.$showMsg()
			    this.swiperList = res.message
		   },
		   //获取分类列表
		   async getNavList(){
			    const {data: res} = await uni.$http.get('/api/public/v1/home/catitems')
		        if (res.meta.status !== 200) return uni.$showMsg()
			    this.navList = res.message
		   },
		   //点击分类跳转
		   async navClickHandler(e){
		   		if(e.name === '分类'){
		   		    uni.switchTab({
		   			   url:'/pages/cate/cate'
		   			})
		   		}
		   },
		   //获取楼层
		   async getFloorList(){
			   const {data: res} = await uni.$http.get('/api/public/v1/home/floordata')
		       if (res.meta.status !== 200) return uni.$showMsg()
			   res.message.map(floor => {
				   floor.product_list.map(prod => {
					   prod.url = '/subpkg/goods_list/goods_list?' + prod.navigator_url.split('?')[1]
				   })
			   })
			   this.floorList = res.message
		       console.log(this.floorList);
		   },
		    gotoSearch() {
		        uni.navigateTo({
		          url: '/subpkg/search/search'
		        })
		      }
		}
	}
</script>

<style lang="scss">
	.navList {
	  width: 750rpx;
	  display: flex;
	  justify-content:space-evenly;
	  margin: 30rpx 0;
	  .navImg {
	    width: 128rpx;
	    height: 140rpx;
	  }
	}
     swiper{
		 height: 330rpx;
		 width: 750rpx;
		 .swiperItem, image{
		 	width: 100%;
		 	height: 100%;
			background-color: red;
		 }
	 }
	
	.content {
		display: flex;
		flex-direction: column;
		align-items: center;
		justify-content: center;
	}

	.logo {
		height: 200rpx;
		width: 200rpx;
		margin-top: 200rpx;
		margin-left: auto;
		margin-right: auto;
		margin-bottom: 50rpx;
	}

	.text-area {
		display: flex;
		justify-content: center;
	}

	.title {
		font-size: 36rpx;
		color: #8f8f94;
	}
	.floor-title {
		height: 60rpx;
		width: 100%;
		display: flex;
	}
	.right-img-box {
	  display: flex;
	  flex-wrap: wrap;
	  justify-content: space-around;
	}
	
	.floor-img-box {
	  display: flex;
	  padding-left: 10rpx;
	}
	.search-box {
	  // 设置定位效果为“吸顶”
	  position: sticky;
	  // 吸顶的“位置”
	  top: 0;
	  // 提高层级，防止被轮播图覆盖
	  z-index: 999;
	}
</style>
