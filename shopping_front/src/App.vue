<template>
	<v-app>
		<Nav />
		<v-content>
			<router-view />
		</v-content>
	</v-app>
</template>

<script>
import Nav from './components/bar/nav'

export default {
	name: 'App',

	components: {
		Nav,
	},

	data() {},
}
</script>
