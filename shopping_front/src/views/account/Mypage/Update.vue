<template>
	<div class="content_row">
		<div class="update_left">
			<mypageNav />
		</div>
		<div class="update_right">
			<update />
		</div>
	</div>
</template>

<script>
import mypageNav from '../../../components/account/mypage/mypageNav.vue'
import update from '../../../components/account/mypage/update.vue'
require('../../../css/mypage/mypage.css')

export default {
	name: 'Update',
	components: {
		mypageNav,
		update,
	},
}
</script>
