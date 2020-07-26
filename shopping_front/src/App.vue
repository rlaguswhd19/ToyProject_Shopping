<template>
	<v-app>
		<loginBar />
		<v-app-bar>
			<mainBar />
		</v-app-bar>
		<v-content>
			<router-view />
		</v-content>
	</v-app>
</template>

<script>
import mainBar from './components/bar/main'
import loginBar from './components/bar/login'

export default {
	name: 'App',

	components: {
		mainBar,
		loginBar,
	},

	data() {},
}
</script>
