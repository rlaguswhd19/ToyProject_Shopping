<template>
	<v-container> </v-container>
</template>

<script>
import axios from 'axios'

export default {
	name: 'HelloWorld',

	data() {
		return {
			dress: {},
		}
	},
	methods: {
		test_multipart: function () {
			axios({
				method: 'post',
				url: 'http://localhost/api/dress',
				data: this.dress,
			})
		},
	},
}
</script>
