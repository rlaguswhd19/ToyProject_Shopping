<template>
	<div class="content_row">
		<div class="update_left" style="outline: 1px black solid;">
			<adminNav />
		</div>
		<div class="update_right" style="outline: 1px red solid;">
			<admin />
		</div>
	</div>
</template>

<script>
import admin from '../../../components/account/admin/admin.vue'
import adminNav from '../../../components/account/admin/adminNav.vue'
require('../../../css/mypage/mypage.css')

export default {
	name: 'Admin',
	components: {
		admin,
		adminNav,
	},
}
</script>

<style scoped></style>
