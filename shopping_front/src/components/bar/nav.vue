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
import mainBar from './main'
import loginBar from './login'

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
