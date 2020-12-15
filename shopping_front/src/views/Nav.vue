<template>
	<div class="header">
		<hr />
		<div class="widthChange">
			<loginBar />
		</div>
		<hr />
		<div class="widthChange">
			<mainBar />
		</div>
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
}

hr {
	border-top: 1px #cccccc solid;
}
.widthChange {
	width: 70%;
	margin-left: 15%;
	margin-right: 15%;
}
</style>
