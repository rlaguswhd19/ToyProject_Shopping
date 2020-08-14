<template>
	<div>
		<div class="login_wrap">
			<h3 style="font-size: 30px; margin-bottom: 15px;">로그인</h3>
			<div class="login_box">
				<input
					type="email"
					class="hj_input"
					placeholder="이메일"
					v-model="account.email"
				/>
				<input
					type="text"
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

				<v-btn
					color="primary"
					style="
						width: 100%;
						margin: auto;
						border-radius: 0;
						height: 50px;
					"
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
				email: '',
				password: '',
			},
			test: '',
		}
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
