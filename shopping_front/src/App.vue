<template>
	<v-app>
		<Nav />
		<div class="router-content">
			<router-view />
		</div>
	</v-app>
</template>

<script>
import Nav from './components/bar/nav'

export default {
	name: 'App',

	components: {
		Nav,
	},

	data() {},
}
</script>

<style>
.router-content {
	padding: 20px 300px;
}
</style>
