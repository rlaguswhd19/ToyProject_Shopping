<template>
	<v-app>
		<loginBar />
		<v-app-bar>
			<mainBar />
		</v-app-bar>
		<v-content>
			<createDress />
		</v-content>
	</v-app>
</template>

<script>
import createDress from './components/dress/createDress'
import mainBar from './components/bar/main'
import loginBar from './components/bar/login'

export default {
	name: 'App',

	components: {
		createDress,
		mainBar,
		loginBar,
	},

	data() {},
}
</script>
