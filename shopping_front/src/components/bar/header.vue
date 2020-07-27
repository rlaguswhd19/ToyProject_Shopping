<template>
	<div>
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
