<template>
	<div class="content_row">
		<div class="update_left">
			<mypageNav />
		</div>
		<div class="update_right">
			<mypage />
		</div>
	</div>
</template>

<script>
import mypageNav from '../../../components/account/mypage/mypageNav.vue'
import mypage from '../../../components/account/mypage/mypage.vue'
require('../../../css/mypage/mypage.css')

export default {
	name: 'Mypage',
	components: {
		mypageNav,
		mypage,
	},
}
</script>
