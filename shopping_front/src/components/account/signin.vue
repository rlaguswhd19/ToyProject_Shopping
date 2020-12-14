<template>
	<div>
		<div class="login_wrap">
			<h3 style="font-size: 30px; margin-bottom: 15px;">로그인</h3>
			<div class="login_box">
				<input
					type="email"
					class="hj_input"
					placeholder="이메일"
					v-model="account.username"
				/>
				<input
					type="password"
					class="hj_input"
					placeholder="비밀번호"
					v-model="account.password"
				/>
				<div
					style="
						text-align: right;
						font-size: 14px;
						margin-bottom: 30px;
					"
				>
					<a>아이디</a> / <a>비밀번호 찾기</a>
				</div>

				<v-btn color="primary" class="hj_button" @click="login()"
					>로그인</v-btn
				>
			</div>
		</div>
	</div>
</template>

<script>
require('../../css/common/common.css')

export default {
	data() {
		return {
			account: {
				username: 'user@naver.com',
				password: 'user',
				grant_type: 'password',
			},
		}
	},

	methods: {
		login() {
			let form = new FormData()
			form.append('username', this.account.username)
			form.append('password', this.account.password)
			form.append('grant_type', this.account.grant_type)

			this.$axios({
				method: 'post',
				url: 'http://localhost:8080/oauth/token',
				data: form,
				headers: {
					'Content-Type': 'application/json;charset=UTF-8',
					Accept: 'application/hal+json;charset=UTF-8',
				},
				auth: {
					username: 'hjapp',
					password: 'hjpass',
				},
			})
				.then(r => {
					console.log(r.data)
					sessionStorage.setItem('email', this.account.username)
					sessionStorage.setItem('access_token', r.data.access_token)
					sessionStorage.setItem('expires_in', r.data.expires_in)
					sessionStorage.setItem(
						'refresh_token',
						r.data.refresh_token,
					)

					for (var key in window.sessionStorage) {
						// getItem( ) 메서드를 이용하여 key 값의 value 값을 찾는다.
						console.log(key, sessionStorage.getItem(key))
					}
					alert('login')
					window.location.href = 'http://localhost:3000'

					// this.$axios({
					// 	method: 'get',
					// 	url:
					// 		'http://localhost:8080/api/accounts/' +
					// 		this.account.username,
					// 	headers: {
					// 		'Content-Type': 'application/json;charset=UTF-8',
					// 		Accept: 'application/hal+json;charset=UTF-8',
					// 	},
					// }).then(r => {
					// 	console.log(r)
					// 	sessionStorage.setItem('id', r.data.id)
					// 	for (var key in window.sessionStorage) {
					// 		// getItem( ) 메서드를 이용하여 key 값의 value 값을 찾는다.
					// 		console.log(key, sessionStorage.getItem(key))
					// 	}
					// 	alert('login')
					// 	window.location.href = 'http://localhost:3000'
					// })
				})
				.catch(e => {
					console.log(e)
					alert('가입하지 않은 아이디이거나, 잘못된 비밀번호입니다.')
				})
		},
	},
}
</script>

<style>
.login_wrap {
	/* text-align: left; */
	width: 350px;
	margin: auto;
	margin-top: 100px;
}

.login_box {
	margin: auto;
	width: 350px;
}
</style>
