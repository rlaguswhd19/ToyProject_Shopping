<template>
	<div class="login_nav">
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
.login_nav {
	display: flex;
}
.login_nav,
ul {
	list-style-type: none;
	display: flex;
}

.login_nav_right {
	margin-left: auto;
}

.v-btn {
	margin: 0 10px;
}
</style>
