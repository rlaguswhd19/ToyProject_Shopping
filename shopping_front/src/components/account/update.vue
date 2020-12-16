<template>
	<div class="update_wrap">
		<div class="update_left">
			<ul>
				<h1>MY PAGE</h1>
				<li>
					<a>회원정보 수정</a>
				</li>
				<li>
					<a>쇼핑내역</a>
				</li>
				<li>
					<a>1:1 문의 내역</a>
				</li>
				<li>
					<a>장바구니</a>
				</li>
				<li>
					<a>주문조회</a>
				</li>
			</ul>
		</div>
		<div class="update_right">
			<h1>회원정보</h1>
			<hr style="margin: 10px;" />
			<ul style="font-size: 13px;">
				<li>
					회원님의 정보를 변경하실 수 있습니다. 변경 후 반드시 확인
					버튼을 클릭하여 저장해주세요.
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
					style="margin-bottom: 30px;"
					v-model="accountDto.email"
					readonly
				/>

				<h3>*Password</h3>
				<v-btn
					class="hj_button"
					color="primary"
					style="margin-bottom: 30px; width: 40%;"
					>비밀번호 변경</v-btn
				>
				<h3>*Phone</h3>
				<input
					type="text"
					class="hj_input"
					style="margin-bottom: 30px;"
					placeholder="휴대폰번호 '-'없이 입력해주세요."
					v-model="accountDto.phone_number"
				/>
				<h3>Birth</h3>
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
				<h3>*Address</h3>
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
				<v-btn
					color="primary"
					class="hj_button"
					style="
						margin: 20px 0 0 60%;
						width: 40%
						height: 50px !important;
					"
					@click="update_accountDto()"
					>정보 수정</v-btn
				>
			</div>
		</div>
	</div>
</template>

<script>
import getAddress from '../address/getAddress'
import { EventBus } from '../../utils/eventBus'

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
					post: '',
					road: '',
					jibun: '',
					detail: '',
					building: '',
				},
			},
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
					'update-Type': 'application/json;charset=UTF-8',
					Accept: 'application/hal+json;charset=UTF-8',
				},
			}).then(r => {
				// email, address
				this.accountDto.email = r.data.email
				this.accountDto.address = r.data.address
				delete this.accountDto.address.id // id 삭제

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
			console.log(this.accountDto)
		},
	},
}
</script>

<style>
.update_wrap {
	/* outline: 1px black solid; */
	display: flex;
}
.update_left {
	/* outline: 1px blue solid; */
	width: 20%;
	padding: 10px;
}
.update_right {
	/* outline: 1px red solid; */
	width: 80%;
	padding: 10px;
}

.update_left li {
	list-style-type: none;
	margin: 20px 0;
	font-size: 13px;
	font-weight: bolder;
}

.update_input {
	width: 60%;
	margin-top: 30px;
}
</style>
