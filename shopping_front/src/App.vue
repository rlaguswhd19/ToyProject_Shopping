<template>
	<v-app>
		<Nav />
		<div class="router-content">
			<router-view />
		</div>
	</v-app>
</template>

<script>
import Nav from './views/Nav'

export default {
	name: 'App',

	components: {
		Nav,
	},
}
</script>

<style>
.router-content {
	padding: 20px 300px;
}
</style>
