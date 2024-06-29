<template>
   <view class="my-search-container" :style="{'background-color': bgcolor}">
	  <view class="my-search-container" :style="{'background-color': bgcolor}">
        <view class="my-search-box" :style="{'border-radius': radius + 'px'}"  @click="searchBoxHandler">
          <uni-icons type="search" size="17"></uni-icons>
          <text class="placeholder">搜索</text>
        </view>
      </view>
	</view>
</template>
<script>
	export default {
		name:"mySearch",
		props:{
			bgcolor: {
				type: String,
				default: '#c00000'
			},
			radius: {
				type: Number,
				default: 18
			}
		},
		data() {
			return {
				wh:0,
			}
		},
		methods:{
		  searchBoxHandler() {    
			this.$emit('click')
		  }
		},
		created(){
		  const sysInfo = uni.getSystemInfoSync()
		  // 可用高度 = 屏幕高度 - navigationBar高度 - tabBar高度 - 自定义的search组件高度
		  this.wh = sysInfo.windowHeight - 50
		}
	}
</script>

<style lang="scss">
.my-search-container {
  // background-color: #c00000; 
  width: 100%;
  margin: 0rpx;
  height: 50px;
  box-sizing: border-box;
  padding: 0 10px ;
  
  display: flex;
  align-items: center;
}

.my-search-box {
  height: 36px;
  background-color: #ffffff;
  /* border-radius: 15px; */
  width: 100%;
  display: flex;
  align-items: center;
  justify-content: center;

  .placeholder {
    font-size: 15px;
    margin-left: 5px;
  }
}
</style>