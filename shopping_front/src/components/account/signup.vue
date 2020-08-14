<template>
	<div class="content_row" style="padding: 0 130px; margin-top: 50px;">
		<div class="signup_left">
			<span style="font-size: 30px; font-weight: 700;">회원가입</span>
			<div class="content_row">
				<input
					type="email"
					class="hj_input"
					style="margin-right: 10px;"
					placeholder="이메일"
					v-model="account.email"
				/>
				<v-btn
					style="margin: auto;"
					color="primary"
					@click="auth = true"
					>인증</v-btn
				>
				<v-dialog v-model="auth" max-width="500px">
					<v-card style="height: 500px;">
						<v-card-title>인증하기</v-card-title>
						<v-card-text>
							<input
								type="number"
								class="hj_input"
								style="margin-right: 10px;"
								placeholder="인증번호"
								v-model="auth_number"
							/>
						</v-card-text>
						<v-card-actions>
							<v-btn text color="primary" @click="auth = false"
								>ok</v-btn
							>
						</v-card-actions>
					</v-card>
				</v-dialog>
			</div>
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
			<div class="content_row" style="margin-top: 50px;">
				<v-select
					:items="years"
					label="년도"
					v-model="input_birth.year"
					dense
					solo
					style="width: 30%; margin-right: 5%;"
				></v-select>
				<v-select
					:items="months"
					label="월"
					v-model="input_birth.month"
					dense
					solo
					style="width: 30%; margin-right: 5%;"
				></v-select>
				<v-select
					:items="dates"
					label="일"
					v-model="input_birth.date"
					dense
					solo
					style="width: 30%;"
				></v-select>
			</div>

			<v-btn
				color="primary"
				style="
					width: 60%;
					height: 50px;
					border-radius: 1;
					margin: 15px 0 0 0;
				"
				disabled="false"
				>회원가입</v-btn
			>
		</div>
		<div class="signup_right">
			<ul>
				<li>
					<i class="mdi mdi-check" />회원가입은 14세 이상 고객만
					가능합니다.
				</li>
				<li>
					<i class="mdi mdi-check" />고객님의 개인정보를 신중히
					취급하며, 동의없이 정보가 공개되지 않습니다.
				</li>
				<li>
					<i class="mdi mdi-check" />매장에서 가입하신 고객은 웹
					회원가입 과정을 거쳐야, 정식 아이디/패스워드가 등록됩니다.
				</li>
				<li>
					<i class="mdi mdi-check" />가입 후 휴대전화로 전송해 드린
					아디클럽 회원증을 다운로드 하시면, 오프라인 매장에서도 상품
					구매 후 포인트 적립이 가능합니다.
				</li>
				<li>
					<i class="mdi mdi-check" />마케팅 수신동의 하시면, 할인쿠폰
					및 이벤트 등의 소식을 받으실 수 있습니다.
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
				birth: '',
			},
			password_check: '',
			input_birth: {
				year: '',
				month: '',
				date: '',
			},
			years: [],
			months: [],
			dates: [],
			auth: false,
		}
	},
	mounted() {
		let today = new Date()
		let year = today.getFullYear()
		this.input_birth.year = year
		this.input_birth.month = today.getMonth()
		this.input_birth.date = today.getDate()
		for (let i = 0; i <= 100; i++) {
			this.years.push(year - i)
		}

		for (let i = 1; i <= 12; i++) {
			this.months.push(i)
		}
	},
	watch: {
		input_birth: {
			deep: true,
			handler() {
				this.setDate()
			},
		},
	},
	methods: {
		setDate() {
			let year = this.input_birth.year
			let currentDay = new Date(
				this.input_birth.year,
				this.input_birth.month,
				0,
			)
			let lastDay = parseInt(currentDay.toString().split(' ')[2])
			this.dates = []
			for (let i = 1; i <= lastDay; i++) {
				this.dates.push(i)
			}
		},
	},
}
</script>

<style>
.signup_wrap {
}

.email_check {
	height: 50px;
}

.signup_left {
	width: 60%;
	padding: 25px 80px;
}

.signup_right {
	width: 40%;
	padding: 50px 20px;
}

.signup_right li {
	list-style-type: none;
	font-size: 13px;
	margin: 20px 0;
	display: flex;
}

.signup_right ul {
	outline: 10px #ebebeb solid;
	width: 350px;
	margin: 10px auto;
	padding: 10px;
}

.signup_right .mdi-check {
	margin-right: 5px;
}
</style>
