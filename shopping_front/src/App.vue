<template>
	<v-app>
		<Token />
		<div style="width: 100%; margin: 20;">
			<Nav />
		</div>
		<div v-if="!current">
			<router-view />
		</div>
		<div v-else>
			<div class="all_content">
				<div class="content_left"></div>
				<div class="router-content">
					<router-view />
				</div>
				<div class="content_right"></div>
			</div>
		</div>
	</v-app>
</template>

<script>
import Nav from './views/Nav'
import Token from './components/bar/token'
require('./css/common/common.css')

export default {
	name: 'App',
	data() {
		return {
			current: true,
		}
	},
	watch: {
		$route(to, from) {
			// console.log(this.$router.currentRoute.name)
			this.checkHome()
		},
	},
	components: {
		Nav,
		Token,
	},
	mounted() {
		this.checkHome()
	},
	methods: {
		checkHome() {
			if (this.$router.currentRoute.path == '/') {
				// 현재 위치가 home
				this.current = false
			} else {
				this.current = true
			}
		},
	},
}
</script>

<style>
.all_content {
	display: flex;
	flex-direction: row;
	padding: 20px 0;
	height: 100%;
}

.content_right {
	width: 15%;
	/* outline: black 1px solid; */
}

.content_left {
	width: 15%;
	/* outline: black 1px solid; */
}

.router-content {
	width: 70%;
	padding: 10px;
}
</style>
