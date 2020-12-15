<template>
	<div class="token_bar" v-if="time != -1">
		<v-card class="token_box">
			<v-card-title>Token Time</v-card-title>
			<v-card-text>
				<h1 style="text-align: center;">
					{{ Math.floor(time / 60) }} : {{ time % 60 }}
				</h1>
			</v-card-text>
			<!-- <v-btn @click="Stop()">stop</v-btn> -->
			<v-btn color="primary" class="hj_button" @click="extension()"
				>시간연장</v-btn
			>
		</v-card>
	</div>
</template>

<script>
export default {
	data() {
		return {
			time: -1,
			intervalid: '',
		}
	},
	methods: {
		extension() {
			let form = new FormData()
			form.append('grant_type', 'refresh_token')
			form.append(
				'refresh_token',
				sessionStorage.getItem('refresh_token'),
			)

			this.$axios({
				method: 'post',
				url: 'http://localhost:8080/oauth/token',
				data: form,
				headers: {
					'Content-Type': 'application/json;charset=UTF-8',
					Accept: 'application/hal+json;charset=UTF-8',
				},
				auth: {
					username: 'hjapp',
					password: 'hjpass',
				},
			}).then(r => {
				sessionStorage.setItem('access_token', r.data.access_token)
				sessionStorage.setItem('expires_in', r.data.expires_in)
				sessionStorage.setItem('refresh_token', r.data.refresh_token)
				this.time = r.data.expires_in
				alert('연장되었습니다.')
			})
		},

		IntervalTime() {
			this.time -= 1
			sessionStorage.setItem('expires_in', this.time)
			if (this.time <= 0) {
				clearInterval(this.intervalid)
				alert('token 시간 만료')
				sessionStorage.clear()
				window.location.href = 'http://localhost:3000'
			}
		},
	},

	mounted() {
		// console.log(sessionStorage.getItem('expires_in'))
		if (sessionStorage.getItem('access_token') != null) {
			this.time = sessionStorage.getItem('expires_in')
			this.time *= 1
			this.intervalid = setInterval(this.IntervalTime, 1000)
		} else {
			clearInterval(this.intervalid)
		}
		// this.IntervalTime()
	},
}
</script>

<style>
.token_bar {
	position: fixed;
	/* margin: 0 auto; */
	/* right: 0; */
	bottom: 80px;
	right: 30px;
	/* outline: 3px blue solid; */
	padding: 20px;
}

.token_box {
	margin: 0 auto;
	width: 200px;
	height: 200px;
	padding: 10px;
}
</style>
