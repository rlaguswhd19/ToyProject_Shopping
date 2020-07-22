<template>
	<v-app>
		<loginBar />
		<v-content>
			<createDress />
		</v-content>
	</v-app>
</template>

<script>
import createDress from './components/dress/createDress'
import loginBar from './components/bar/login'

export default {
	name: 'App',

	components: {
		createDress,
		loginBar,
	},

	data() {},
}
</script>
