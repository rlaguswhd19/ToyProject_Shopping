<template>
	<div>
		<h1>회원 탈퇴</h1>
		<hr />
		<ul style="font-size: 13px;">
			<li>
				H STORE 온라인스토어 회원 탈퇴시 유의사항
			</li>
			<li>
				회원탈퇴를 위해서는 구매중인 상품이 없어야 합니다.
			</li>
			<li>
				회원 탈퇴시 보유하셨던 포인트와 쿠폰 및 회원정보가 모두
				삭제됩니다.
			</li>
		</ul>
		<div class="content_row" style="margin-top: 35%; width: 100%;">
			<v-btn
				style="
					margin-left: auto;
					margin-right: 10px;
					width: 25%;
					height: 50px !important;
				"
				@click="deleteAccount()"
				color="primary"
				>회원 탈퇴</v-btn
			>
			<v-btn
				color="primary"
				style="width: 25%; height: 50px !important; margin-right: 5%;"
				@click="$router.push('mypage')"
				>취소</v-btn
			>
		</div>
	</div>
</template>

<script>
export default {
	data() {
		return {}
	},
	methods: {
		deleteAccount() {
			this.$axios({
				method: 'delete',
				url:
					'http://localhost:8080/api/accounts/' +
					sessionStorage.getItem('email'),
				headers: {
					Authorization:
						'Bearer' + sessionStorage.getItem('access_token'),
					'Content-Type': 'application/json;charset=UTF-8',
					Accept: 'application/hal+json;charset=UTF-8',
				},
			})
				.then(r => {
					console.log(r)
					sessionStorage.clear()
					alert('삭제 완료')
					window.location.href = 'http://localhost:3000'
				})
				.catch(e => {
					console.log(e)
				})
		},
	},
}
</script>
