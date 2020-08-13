<template>
	<div class="content_row" style="padding: 0 150px; margin-top: 100px;">
		<div class="signup_left">
			<h1 style="font-size: 35px;">회원가입</h1>
			<div>
				<div class="email_check">
					<h1>이메일 인증할곳 만들기</h1>
				</div>
				<input
					type="email"
					class="hj_input"
					placeholder="이메일"
					v-model="account.email"
				/>
				<input
					type="password"
					class="hj_input"
					placeholder="비밀번호"
					v-model="account.password"
				/>
				<input
					type="password"
					class="hj_input"
					placeholder="비밀번호확인"
					v-model="password_check"
				/>
				<input
					type="text"
					class="hj_input"
					placeholder="휴대폰번호 '-'없이 입력해주세요."
					v-model="account.phone_number"
				/>
				<!-- 생년월일 만들기 -->
			</div>
		</div>
		<div class="signup_right">
			<h1>여기두</h1>
			<ul>
				<li>회원가입은 14세 이상 고객만 가능합니다.</li>
				<li>
					고객님의 개인정보를 신중히 취급하며, 동의없이 정보가
					공개되지 않습니다.
				</li>
				<li>
					매장에서 가입하신 고객은 웹 회원가입 과정을 거쳐야, 정식
					아이디/패스워드가 등록됩니다.
				</li>
				<li>
					가입 후 휴대전화로 전송해 드린 아디클럽 회원증을 다운로드
					하시면, 오프라인 매장에서도 상품 구매 후 포인트 적립이
					가능합니다.
				</li>
				<li>
					마케팅 수신동의 하시면, 할인쿠폰 및 이벤트 등의 소식을
					받으실 수 있습니다.
				</li>
			</ul>
		</div>
	</div>
</template>

<script>
export default {
	data() {
		return {
			account: {
				email: '',
				password: '',
				phone_number: '',
			},
			password_check: '',
		}
	},
}
</script>

<style>
.signup_wrap {
}

.signup_left {
	outline: 1px blue solid;
	width: 50%;
	padding: 40px;
}

.signup_right {
	width: 50%;
	outline: 1px red solid;
	padding: 130px 20px;
}
</style>
