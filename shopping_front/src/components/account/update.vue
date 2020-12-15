<template>
	<div class="update_wrap">
		<div class="update_left">
			<ul>
				<h1>MY PAGE</h1>
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
			<h1>회원정보 수정</h1>
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
				<span>Email</span>
				<input
					type="email"
					class="hj_input"
					placeholder="이메일"
					v-model="accountDto.email"
					readonly
				/>
				<v-btn class="hj_button" color="primary">비밀번호 변경</v-btn>
				<span>연락처</span>
				<span>생일 성별</span>
				<span>Address</span>
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
		this.getAccountDto()

		EventBus.$on('get_address', address => {
			this.accountDto.address = address
			console.log(this.accountDto)
			// this.address = address
		})
	},
	data() {
		return {
			accountDto: {
				email: '',
				address: {
					road: '',
					jibun: '',
					detail: '',
					building: '',
				},
			},
		}
	},
	methods: {
		getAccountDto() {
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
				this.accountDto.email = r.data.email
				this.accountDto.address = r.data.address
				delete this.accountDto.address.id // id 삭제

				console.log(r)
				console.log(this.accountDto)
			})
		},
	},
}
</script>

<style>
.update_wrap {
	outline: 1px black solid;
	display: flex;
}
.update_left {
	outline: 1px blue solid;
	width: 20%;
	padding: 10px;
}
.update_right {
	outline: 1px red solid;
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
