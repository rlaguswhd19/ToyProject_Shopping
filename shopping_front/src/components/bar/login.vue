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
				<li v-if="this.email == ''">
					<v-btn text small @click="go('/account/signIn')">
						로그인
					</v-btn>
				</li>
				<li v-else>
					<v-btn text small @click="go('/account/signIn')">
						{{ this.getUser() }}
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
		return {
			email: '',
		}
	},
	mounted() {
		if (sessionStorage.getItem('email') != null) {
			this.email = sessionStorage.getItem('email')
		}

		// console.log(sessionStorage.getItem('access_token'))
		// console.log(sessionStorage.getItem('refresh_token'))
		// console.log(sessionStorage.getItem('expires_in'))
	},
	methods: {
		go(to) {
			this.$router.push(to)
		},
		logout() {
			sessionStorage.clear()
			alert('logout')
			window.location.href = 'http://localhost:3000'
		},
		getUser() {
			let email = sessionStorage.getItem('email')
			let temp = email.substr(0, email.indexOf('@'))

			return temp
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
	margin: auto 0;
	display: flex;
}

.login_right {
	margin-left: auto;
}

.v-btn {
	margin: 0 10px;
}
</style>
