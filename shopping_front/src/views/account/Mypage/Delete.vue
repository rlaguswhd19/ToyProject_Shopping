<template>
	<div class="content_row">
		<div class="update_left">
			<mypageNav />
		</div>
		<div class="update_right">
			<deleteAccount />
		</div>
	</div>
</template>

<script>
import mypageNav from '../../../components/account/mypage/mypageNav.vue'
import deleteAccount from '../../../components/account/mypage/delete.vue'
require('../../../css/mypage/mypage.css')

export default {
	name: 'Mypage',
	components: {
		mypageNav,
		deleteAccount,
	},
}
</script>
