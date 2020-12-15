<template>
	<div class="login_nav_wrap">
		<div class="login_left">
			<ul>
				<li>
					<v-btn text small> HJ </v-btn>
				</li>
				<li>
					<v-btn text small> join us </v-btn>
				</li>
			</ul>
		</div>

		<div class="login_right">
			<ul>
				<li>
					<v-btn text small> 주문조회 </v-btn>
				</li>
				<li>
					<v-btn text small @click="go('/account/signUp')">
						회원가입
					</v-btn>
				</li>
				<li v-if="checkLogin()">
					<v-btn text small @click="go('/account/signIn')">
						로그인
					</v-btn>
				</li>
				<li v-else>
					<v-btn text small @click="go('/account/update')">
						마이페이지
					</v-btn>
					<v-btn text small @click="logout()">
						로그아웃
					</v-btn>
				</li>
			</ul>
		</div>
	</div>
</template>

<script>
export default {
	data() {
		return {}
	},
	mounted() {},
	methods: {
		go(to) {
			this.$router.push(to)
		},
		logout() {
			sessionStorage.clear()
			alert('logout')
			window.location.href = 'http://localhost:3000'
		},
		checkLogin() {
			if (sessionStorage.getItem('access_token') == null) {
				return true
			}

			return false
		},
	},
}
</script>

<style>
.login_nav_wrap {
	display: flex;
	height: 30px;
}
.login_nav_wrap ul {
	list-style-type: none;
	display: flex;
}

.login_right {
	margin-left: auto;
}

.v-btn {
	margin: 0;
}
</style>
