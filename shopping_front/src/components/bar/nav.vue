<template>
	<div class="header">
		<loginBar />
		<mainBar />
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
	border: 2px blue solid;
	display: flex;
	flex-direction: column;
}
</style>
