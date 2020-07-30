<template>
	<div class="header">
		<hr />
		<loginBar />
		<hr />
		<mainBar />
		<hr />
	</div>
</template>

<script>
import mainBar from '../components/bar/main'
import loginBar from '../components/bar/login'

export default {
	name: 'header',
	components: {
		mainBar,
		loginBar,
	},
}
</script>

<style>
.header {
	display: flex;
	flex-direction: column;
	position: relative;
}
</style>
