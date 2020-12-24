<template>
	<div>
		<h1>회원 탈퇴</h1>
		<hr style="margin: 10px;" />
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
		<div
			class="content_row"
			style="margin-top: 30%; width: 100%; outline: 1px black solid;"
		>
			<v-btn
				style="
					margin-left: auto;
					margin-right: 10px;
					width: 25%;
					height: 45px !important;
				"
				color="primary"
				>회원 탈퇴</v-btn
			>
			<v-btn color="primary" style="width: 25%; height: 45px !important;"
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
	methods: {},
}
</script>
