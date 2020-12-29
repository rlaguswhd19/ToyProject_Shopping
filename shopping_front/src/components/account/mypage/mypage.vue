<template>
	<div>
		<h1>{{ user }}고객님 안녕하세요!</h1>
		<div class="content_row" style="margin: 100px 0 20px 0;">
			<h1>
				{{ role }}
			</h1>
		</div>

		<h2>다음 등급까지...</h2>
		<v-progress-linear value="15"></v-progress-linear>

		<div style="margin-top: 50px;">
			<h1>등급표</h1>
		</div>
	</div>
</template>

<script>
export default {
	data() {
		return {
			user: '',
			role: '',
		}
	},
	mounted() {
		this.getUser()
	},
	methods: {
		getUser() {
			let email = sessionStorage.getItem('email')
			let idx = email.indexOf('@')
			this.user = email.substr(0, idx)

			this.role = sessionStorage.getItem('roles')
		},
	},
}
</script>
