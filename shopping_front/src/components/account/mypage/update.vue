<template>
	<div>
		<h1>회원정보 수정</h1>
		<hr style="margin: 10px;" />
		<ul style="font-size: 13px;">
			<li>
				회원님의 정보를 변경하실 수 있습니다. 변경 후 반드시 확인 버튼을
				클릭하여 저장해주세요.
			</li>
			<li>
				* 표시는 필수 입력사항입니다. 반드시 입력해주세요.
			</li>
		</ul>
		<div class="update_input">
			<h3>*Email</h3>
			<input
				type="email"
				class="hj_input"
				placeholder="이메일"
				v-model="accountDto.email"
				readonly
			/>
			<div class="error_alert">
				<p v-if="!this.response_error.email">
					이메일 형식이 올바르지 않습니다.
				</p>
			</div>

			<h3 style="margin-top: 30px;">*Password</h3>
			<v-btn
				class="hj_button"
				color="primary"
				style="margin-bottom: 30px; width: 40%;"
				@click="password_change_view = true"
				>비밀번호 변경</v-btn
			>
			<v-dialog v-model="password_change_view" max-width="500px">
				<v-card style="height: 100%; padding: 20px;">
					<v-card-title
						>비밀번호 변경
						<span style="color: blue; font-size: 13px;">
							비밀번호는 영문, 숫자, 특수문자가 포함되어야 합니다.
							(8~16자리)
						</span>
					</v-card-title>
					<br />

					<v-card-text>
						<h3 style="color: black;">현재 비밀번호</h3>
						<input
							type="password"
							class="hj_input"
							style="margin-right: 10px; margin-bottom: 30px;"
							placeholder="현재 비밀번호"
							v-model="input_password.now_password"
						/>

						<h3 style="color: black;">새 비밀번호</h3>
						<input
							type="password"
							class="hj_input"
							style="margin-right: 10px;"
							placeholder="새 비밀번호"
							v-model="input_password.new_password"
						/>
						<input
							type="password"
							class="hj_input"
							style="margin-right: 10px;"
							placeholder="새 비밀번호 확인"
							v-model="input_password.new_password_check"
						/>
						<div class="error_alert">
							<div v-if="!this.pass_match">
								<p>
									비밀번호가 일치하지 않습니다.
								</p>
							</div>
							<div v-else>
								<p v-if="!this.response_error.pass">
									영문, 숫자, 특수문자가 포함되어야 합니다.
								</p>
							</div>
						</div>
					</v-card-text>
					<br />
					<v-card-actions>
						<v-btn
							text
							color="primary"
							@click="password_change()"
							style="margin-left: auto; width: 20%;"
							>저장</v-btn
						>
						<v-btn
							text
							color="primary"
							@click="password_change_view = false"
							style="width: 20%;"
							>취소</v-btn
						>
					</v-card-actions>
				</v-card>
			</v-dialog>
			<h3>*Phone</h3>
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

			<h3 style="margin-top: 30px;">Birth</h3>
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
				v-model="accountDto.sex"
				dense
				solo
				style="width: 30%; margin-left: 70%;"
			></v-select>
			<h3 style="margin-top: 30px;">*Address</h3>
			<div class="content_row">
				<input
					type="text"
					class="hj_input"
					placeholder="우편번호"
					v-model="accountDto.address.post"
					style="margin-right: 10px;"
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
			<div class="content_row" style="margin: 20px 0 0 0; width: 100%;">
				<v-btn
					color="primary"
					style="
						height: 50px !important;
						margin-left: auto;
						margin-right: 10px;
						width: 30%;
					"
					@click="update_accountDto()"
					>정보 수정</v-btn
				>
				<v-btn
					color="primary"
					style="height: 50px !important; width: 30%;"
					@click="$router.push('mypage')"
					>취소</v-btn
				>
			</div>
		</div>
	</div>
</template>

<script>
import getAddress from '../../address/getAddress'
import { EventBus } from '../../../utils/eventBus'

export default {
	components: {
		getAddress,
	},

	mounted() {
		this.set_year_month()
		this.get_accountDto()

		EventBus.$on('get_address', address => {
			this.accountDto.address = address
			console.log('#update')
		})
	},
	watch: {
		input_birth: {
			deep: true,
			handler() {
				this.set_date()
			},
		},

		input_password: {
			deep: true,
			handler() {
				this.input_password_check()
			},
		},
	},
	data() {
		return {
			accountDto: {
				email: '',
				password: '',
				phone_number: '',
				birth: '',
				sex: '',
				address: {
					id: '',
					post: '',
					road: '',
					jibun: '',
					detail: '',
					building: '',
				},
			},
			// address_id 임시저장
			address_id: '',
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
			sexs: ['Men', 'Women'],
			password_change_view: false,
			input_password: {
				now_password: '',
				new_password: '',
				new_password_check: '',
			},
			pass_match: true,
			response_error: {
				email: true,
				pass: true,
				phone: true,
			},
		}
	},
	methods: {
		get_accountDto() {
			this.$axios({
				method: 'get',
				url:
					'http://localhost:8080/api/accounts/' +
					sessionStorage.getItem('email'),
				headers: {
					'Content-Type': 'application/json;charset=UTF-8',
					Accept: 'application/hal+json;charset=UTF-8',
				},
			}).then(r => {
				// email, address
				this.accountDto.email = r.data.email
				this.accountDto.address = r.data.address
				this.address_id = r.data.address.id
				// phone_number는 -를 뺴고 넣어야한다.
				this.accountDto.phone_number = r.data.phone_number.replaceAll(
					'-',
					'',
				)

				// birth
				let arr = r.data.birth.split('-')
				this.input_birth.year = arr[0] * 1
				this.input_birth.month = arr[1] * 1
				this.input_birth.date = arr[2] * 1
				this.set_date()

				// sex
				this.accountDto.sex = r.data.sex

				//password
				this.accountDto.password = r.data.password
			})
		},
		set_year_month() {
			let today = new Date()
			let year = today.getFullYear()

			for (let i = 0; i <= 100; i++) {
				this.years.push(year - i)
			}

			for (let i = 1; i <= 12; i++) {
				this.months.push(i)
			}
		},
		set_date() {
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
		update_accountDto() {
			this.accountDto.birth =
				this.input_birth.year +
				'/' +
				this.input_birth.month +
				'/' +
				this.input_birth.date

			this.accountDto.address.id = this.address_id
			console.log(this.accountDto)

			this.$axios({
				method: 'put',
				url: 'http://localhost:8080/api/accounts',
				data: this.accountDto,
				headers: {
					Authorization:
						'Bearer' + sessionStorage.getItem('access_token'),
					'Content-Type': 'application/json;charset=UTF-8',
					Accept: 'application/hal+json;charset=UTF-8',
				},
			})
				.then(r => {
					alert('정보 수정 완료!')
				})
				.catch(e => {
					// console.log(e.response.data)
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
		input_password_check() {
			if (
				this.input_password.new_password_check ==
				this.input_password.new_password
			) {
				this.pass_match = true
			} else {
				this.pass_match = false
			}
		},
		password_change() {
			this.$axios({
				method: 'put',
				url: 'http://localhost:8080/api/accounts/password',
				data: {
					password: this.input_password.now_password,
					newPassword: this.input_password.new_password,
					email: sessionStorage.getItem('email'),
				},
				headers: {
					Authorization:
						'Bearer' + sessionStorage.getItem('access_token'),
					'Content-Type': 'application/json;charset=UTF-8',
					Accept: 'application/hal+json;charset=UTF-8',
				},
			})
				.then(r => {
					console.log(r)
					alert('비밀번호 변경 완료!')
					this.password_change_view = false
				})
				.catch(e => {
					console.log(e)
				})
		},
	},
}
</script>
