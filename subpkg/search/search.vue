<template>
	<view>
	<view class="search-box">
		<uni-search-bar @confirm="search" @input="input" :radius="20" bgColor='white' placeholder = '搜索' cancelButton='none'></uni-search-bar>
	</view>
	<!-- 搜索历史 -->
	<view class="history-box"  v-if="searchResults.length === 0">
	  <!-- 标题区域 -->
	  <view class="history-title">
	    <text>搜索历史</text>
	    <uni-icons type="trash" size="17" @click="cleanHistory"></uni-icons>
	  </view>
	  <!-- 列表区域 -->
	  <view class="history-list" >
	    <uni-tag @click="gotoGoodsList(item)" :text="item" v-for="(item, i) in historyList" :key="i"></uni-tag>
	  </view>
	</view>
	<!-- 搜索建议列表 -->
	<view class="sugg-list"  v-if="searchResults.length !== 0">
	  <view class="sugg-item" v-for="(item, i) in searchResults" :key="i" @click="gotoDetail(item.goods_id)">
	    <view class="goods-name">{{item.goods_name}}</view>
	    <uni-icons type="arrowright" size="16"></uni-icons>
	  </view>
	</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				timer: null,
				kw: '',
				searchResults:[],
				historyList: ['a', 'app', 'apple']
			};
		},
		computed: {
			  historys() {
			    // 注意：由于数组是引用类型，所以不要直接基于原数组调用 reverse 方法，以免修改原数组中元素的顺序
			    // 而是应该新建一个内存无关的数组，再进行 reverse 反转
			    return [...this.historyList].reverse()
			  }
		},
		created(){
			 this.historyList = JSON.parse(uni.getStorageSync('kw') || '[]')
		},
		methods: {
			input(e) {
				clearTimeout(this.timer)
				this.timer = setTimeout(() => {
					this.kw = e
                    this.getSearchList()
				}, 500)
			},
			async getSearchList() {
			  // 判断关键词是否为空
			  if (this.kw === '') {
			    this.searchResults = []
			    return
			  }
			  // 发起请求，获取搜索建议列表
			  const { data: res } = await uni.$http.get('/api/public/v1/goods/qsearch', { query: this.kw })
			  if (res.meta.status !== 200) return uni.$showMsg()
			  this.searchResults = res.message
			  this.saveSearchHistory()
			},
			gotoDetail(goods_id) {
			  uni.navigateTo({
			    // 指定详情页面的 URL 地址，并传递 goods_id 参数
			    url: '/subpkg/goods_detail/goods_detail?goods_id=' + goods_id
			  })
			},
			saveSearchHistory() {
				// 1. 将 Array 数组转化为 Set 对象
				const set = new Set(this.historyList)
				// 2. 调用 Set 对象的 delete 方法，移除对应的元素
				set.delete(this.kw)
				// 3. 调用 Set 对象的 add 方法，向 Set 中添加元素
				set.add(this.kw)
				// 4. 将 Set 对象转化为 Array 数组
				this.historyList = Array.from(set)
				 uni.setStorageSync('kw', JSON.stringify(this.historyList))
			},
			cleanHistory() {
			  // 清空 data 中保存的搜索历史
			  this.historyList = []
			  // 清空本地存储中记录的搜索历史
			  uni.setStorageSync('kw', '[]')
			},
			gotoGoodsList(kw) {
			  uni.navigateTo({
			    url: '/subpkg/goods_list/goods_list?query=' + kw
			  })
			}
		}
	}
</script>

<style lang="scss">
.search-box{
	background-color: #c00000;
	// display: flex;
    position: sticky;
	top: 0;
	z-index: 0;
}
.sugg-list {
  padding: 0 5px;

  .sugg-item {
    font-size: 12px;
    padding: 13px 0;
    border-bottom: 1px solid #efefef;
    display: flex;
    align-items: center;
    justify-content: space-between;

    .goods-name {
      // 文字不允许换行（单行文本）
      white-space: nowrap;
      // 溢出部分隐藏
      overflow: hidden;
      // 文本溢出后，使用 ... 代替
      text-overflow: ellipsis;
      margin-right: 3px;
    }
  }
}
.history-box {
  padding: 0 5px;

  .history-title {
    display: flex;
    justify-content: space-between;
    align-items: center;
    height: 40px;
    font-size: 13px;
    border-bottom: 1px solid #efefef;
  }

  .history-list {
    display: flex;
    flex-wrap: wrap;

    .uni-tag {
      margin-top: 5px;
      margin-right: 5px;
    }
  }
}
</style>
