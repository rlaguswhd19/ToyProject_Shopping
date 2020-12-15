<template>
	<div class="content_row" style="padding: 0 130px; margin-top: 50px;">
		<div class="signup_left">
			<span style="font-size: 30px; font-weight: 700;">회원가입</span>
			<div class="content_row" style="margin-top: 20px;">
				<input
					type="email"
					class="hj_input"
					style="margin-right: 10px;"
					placeholder="이메일"
					v-model="accountDto.email"
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
								v-model="auth_num"
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
			<div class="error_alert">
				<p v-if="!this.response_error.email">
					이메일 형식이 올바르지 않습니다.
				</p>
			</div>
			<input
				type="password"
				class="hj_input"
				placeholder="비밀번호"
				v-model="password_input.input"
			/>
			<input
				type="password"
				class="hj_input"
				placeholder="비밀번호확인"
				v-model="password_input.check"
			/>
			<div class="error_alert">
				<p v-if="!this.pass_match">
					비밀번호가 일치하지 않습니다.
				</p>
				<div v-else>
					<p v-if="!this.response_error.pass">
						영문, 숫자, 특수문자가 포함되어야 합니다.
					</p>
				</div>
			</div>
			<input
				type="text"
				class="hj_input"
				placeholder="휴대폰번호 '-'없이 입력해주세요."
				v-model="accountDto.phone_number"
			/>
			<div class="error_alert">
				<p v-if="!this.response_error.phone">
					휴대폰번호의 형식이 올바르지 않습니다.
				</p>
			</div>
			<!-- 생년월일 만들기 -->
			<div style="margin: 30px 0 5px 0;">
				<span>생년월일</span>
			</div>
			<div class="content_row">
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
			<v-select
				:items="sexs"
				label="성별"
				v-model="sex"
				dense
				solo
				style="width: 30%; margin-left: 70%;"
			></v-select>
			<div class="content_row">
				<input
					type="text"
					class="hj_input"
					placeholder="우편번호"
					v-model="accountDto.address.post"
					readonly
					style="margin-right: 10px;"
				/>
				<getAddress />
			</div>
			<input
				type="text"
				class="hj_input"
				placeholder="도로명주소"
				v-model="accountDto.address.road"
				readonly
			/>
			<input
				type="text"
				class="hj_input"
				placeholder="지번주소"
				v-model="accountDto.address.jibun"
				readonly
			/>
			<input
				type="text"
				class="hj_input"
				placeholder="건물"
				v-model="accountDto.address.building"
				readonly
			/>
			<input
				type="text"
				class="hj_input"
				placeholder="상세주소"
				v-model="accountDto.address.detail"
			/>
			<v-btn
				color="primary"
				style="
					width: 100%;
					height: 50px;
					border-radius: 1;
					margin: 15px 0 0 0;
				"
				@click="post_account"
				>회원가입</v-btn
			>
		</div>
		<div class="signup_right">
			<ul>
				<li style="margin: 0;">
					<i class="mdi mdi-check" style="color: blue;" />
					<span style="color: blue;">H STROE</span>
				</li>
				<li>
					<i class="mdi mdi-check" style="color: blue;" />회원가입은
					14세 이상 고객만 가능합니다.
				</li>
				<li>
					<i class="mdi mdi-check" style="color: blue;" />고객님의
					개인정보를 신중히 취급하며, 동의없이 정보가 공개되지
					않습니다.
				</li>
				<li>
					<i class="mdi mdi-check" style="color: blue;" />비밀번호는
					영문, 숫자, 특수문자가 포함되어야 합니다. (8~16자리)
				</li>
				<li>
					<i class="mdi mdi-check" style="color: blue;" />상세주소를
					기입하지 않을 경우 제품배송이 여려울 수 있습니다.
				</li>
				<li>
					<i class="mdi mdi-check" style="color: blue;" />마케팅
					수신동의 하시면, 할인쿠폰 및 이벤트 등의 소식을 받으실 수
					있습니다.
				</li>
			</ul>
		</div>
	</div>
</template>

<script>
import getAddress from '../address/getAddress'
import { EventBus } from '../../utils/eventBus'

export default {
	components: { getAddress },

	data() {
		return {
			// 계정 정보
			accountDto: {
				email: 'test@naver.com',
				password: 'test',
				phone_number: '01047321566',
				birth: '',
				sex: '',
				address: {
					post: '',
					road: '',
					jibun: '',
					detail: '',
					building: '',
				},
			},
			// 비밀번호
			password_input: {
				check: '',
				input: '',
			},
			// 비밀번호 확인
			pass_match: true,
			// 입력된 생년월일
			input_birth: {
				year: '',
				month: '',
				date: '',
			},
			// 생년월일 selector 값
			years: [],
			months: [],
			dates: [],
			// 이메일 인증
			auth: false,
			auth_num: '',
			// error response check
			response_error: {
				email: true,
				pass: true,
				phone: true,
			},
			sexs: ['남자', '여자'],
			sex: '',
		}
	},
	mounted() {
		let today = new Date()
		let year = today.getFullYear()
		this.input_birth.year = year
		this.input_birth.month = today.getMonth() + 1
		this.input_birth.date = today.getDate()

		for (let i = 0; i <= 100; i++) {
			this.years.push(year - i)
		}

		for (let i = 1; i <= 12; i++) {
			this.months.push(i)
		}

		EventBus.$on('get_address', address => {
			this.accountDto.address = address
			console.log(this.accountDto)
			// this.address = address
		})
	},
	watch: {
		input_birth: {
			deep: true,
			handler() {
				this.setDate()
			},
		},
		password_input: {
			deep: true,
			handler() {
				if (this.password_input.input == this.password_input.check) {
					this.pass_match = true
				} else {
					this.pass_match = false
				}
			},
		},
	},
	methods: {
		setDate() {
			let year = this.input_birth.year
			let temp = this.input_birth.date

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

			if (temp > lastDay) {
				temp = lastDay
			}
			this.input_birth.date = temp
		},
		check_password() {
			if (this.accountDto.password == this.password_check) {
				return true
			} else {
				return false
			}
		},

		post_account() {
			// birth 만들기
			this.accountDto.birth =
				this.input_birth.year +
				'/' +
				this.input_birth.month +
				'/' +
				this.input_birth.date

			if (this.sex == '남자') {
				this.accountDto.sex = 'Men'
			} else {
				this.accountDto.sex = 'Women'
			}
			// 패스워드 체크
			if (!this.pass_match) {
				alert('비밀번호가 일치하지 않습니다.')
				return
			}
			this.accountDto.password = this.password_input.input

			console.log(this.accountDto)

			this.$axios({
				method: 'post',
				url: 'http://localhost:8080/api/accounts',
				data: this.accountDto,
				headers: {
					'Content-Type': 'application/json;charset=UTF-8',
					Accept: 'application/hal+json;charset=UTF-8',
				},
			})
				.then(r => {
					console.log(r)
					alert('회원가입 완료')
				})
				.catch(e => {
					console.log(e.response.data)
					let errors = e.response.data.content

					for (let i = 0; i < errors.length; i++) {
						let error = errors[i]
						if (error.field != null) {
							if (error.field == 'email') {
								this.response_error.email = false
							} else if (error.field == 'password') {
								this.response_error.pass = false
							} else {
								this.response_error.phone = false
							}
						}
					}
				})
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
	margin: 30px 0;
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
.error_alert {
	color: red;
	float: right;
	font-size: 13px;
}
</style>
