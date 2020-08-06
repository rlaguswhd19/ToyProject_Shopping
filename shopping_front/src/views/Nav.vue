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
	name: 'Nav',
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

hr {
	border-top: 1px #cccccc solid;
}
</style>
