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
			<div style="float: right;">
				<p
					v-if="!this.check_result"
					style="color: red; font-size: 13px;"
				>
					비밀번호를 확인해주세요.
				</p>
			</div>
			<input
				type="text"
				class="hj_input"
				placeholder="휴대폰번호 '-'없이 입력해주세요."
				v-model="accountDto.phone_number"
			/>
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
			<div class="content_row">
				<input
					type="text"
					class="hj_input"
					placeholder="우편번호"
					v-model="accountDto.address.post"
					readonly
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
				placeholder="상세주소"
				v-model="accountDto.address.detail"
			/>
			<input
				type="text"
				class="hj_input"
				placeholder="건물"
				v-model="accountDto.address.building"
				readonly
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
					<i class="mdi mdi-check" style="color: blue;" />매장에서
					가입하신 고객은 웹 회원가입 과정을 거쳐야, 정식
					아이디/패스워드가 등록됩니다.
				</li>
				<li>
					<i class="mdi mdi-check" style="color: blue;" />가입 후
					휴대전화로 전송해 드린 아디클럽 회원증을 다운로드 하시면,
					오프라인 매장에서도 상품 구매 후 포인트 적립이 가능합니다.
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
			accountDto: {
				email: 'test@naver.com',
				password: 'test',
				phone_number: '01047321566',
				birth: '',
				address: {
					post: '',
					road: '',
					jibun: '',
					detail: '',
					building: '',
				},
			},
			password_input: {
				check: '',
				input: '',
			},
			check_result: false,
			input_birth: {
				year: '',
				month: '',
				date: '',
			},
			years: [],
			months: [],
			dates: [],
			auth: false,
			auth_num: '',
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
					this.check_result = true
				} else {
					this.check_result = false
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

			// 패스워드 체크
			if (!this.check_result) {
				alert('패스워드를 다시 입력하세요')
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
				})
				.catch(e => {
					console.log(e.response.data)
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
</style>
