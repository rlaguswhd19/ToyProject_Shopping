<template>
	<div>
		<ul>
			<a @click="go('mypage')" style="color: black;"><h1>MY PAGE</h1></a>
			<li>
				<a @click="go('update')">회원정보 수정</a>
			</li>
			<li>
				<a @click="go('delete')">회원 탈퇴</a>
			</li>
			<li>
				<a>쇼핑 내역</a>
			</li>
			<li>
				<a>1:1 문의 내역</a>
			</li>
			<li>
				<a>장바구니</a>
			</li>
			<li>
				<a>주문 조회</a>
			</li>
		</ul>
	</div>
</template>

<script>
export default {
	data() {
		return {}
	},
	methods: {
		go(to) {
			this.$router.push(to)
		},
	},
}
</script>

<style>
a {
	text-decoration: none;
}
</style>
