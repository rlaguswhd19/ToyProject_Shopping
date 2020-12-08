<template>
	<v-app>
		<Nav />
		<div class="all_content">
			<div class="content_left"></div>
			<div class="router-content">
				<router-view />
			</div>
			<div class="content_right">
				<Token />
			</div>
		</div>
	</v-app>
</template>

<script>
import Nav from './views/Nav'
import Token from './components/bar/token'

export default {
	name: 'App',

	components: {
		Nav,
		Token,
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
	width: 20%;
	outline: black 1px solid;
}

.content_left {
	width: 20%;
	/* outline: black 1px solid; */
}

.router-content {
	width: 80%;
}
</style>
