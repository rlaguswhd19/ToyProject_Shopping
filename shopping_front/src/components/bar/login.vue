<template>
	<div class="login_nav_wrap">
		<ul class="login_nav_left">
			<li>
				<v-btn text small> HJ </v-btn>
			</li>
			<li>
				<v-btn text small> join us </v-btn>
			</li>
		</ul>

		<ul class="login_nav_right">
			<li>
				<v-btn text small> 주문조회 </v-btn>
			</li>
			<li>
				<v-btn text small> 회원가입 </v-btn>
			</li>
			<li>
				<v-btn text small> 로그인 </v-btn>
			</li>
			<li>
				<v-btn text small> 고객센터 </v-btn>
			</li>
		</ul>
	</div>
</template>

<script>
export default {}
</script>

<style>
.login_nav_wrap {
	display: flex;
	height: 30px;
}
.login_nav,
ul {
	list-style-type: none;
	display: flex;
	margin: auto 0;
}

.login_nav_right {
	margin-left: auto;
}

.v-btn {
	margin: 0 10px;
}
</style>
