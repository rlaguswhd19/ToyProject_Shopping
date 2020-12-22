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
				<div class="content_row" v-if="check_login()">
					<li>
						<v-btn text small @click="go('/account/signIn')">
							로그인
						</v-btn>
					</li>
					<li>
						<v-btn text small @click="go('/account/signUp')">
							회원가입
						</v-btn>
					</li>
				</div>
				<div class="content_row" v-else>
					<li>
						<v-btn text small @click="go('/account/update')">
							마이페이지
						</v-btn>
					</li>
					<li>
						<v-btn text small @click="logout()">
							로그아웃
						</v-btn>
					</li>
					<li v-if="get_roles()">
						<v-btn text small @click="go('/account/admin')">
							ADMIN
						</v-btn>
					</li>
				</div>
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
		check_login() {
			if (sessionStorage.getItem('access_token') == null) {
				return true
			}

			return false
		},
		get_roles() {
			if (sessionStorage.getItem('roles') == null) {
				return false
			}

			let temp = sessionStorage.getItem('roles').split(',')
			for (let i in temp) {
				if (temp[i] == 'ADMIN') {
					return true
				}
			}
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
