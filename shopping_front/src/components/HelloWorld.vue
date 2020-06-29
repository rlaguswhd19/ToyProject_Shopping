<template>
	<div></div>
</template>

<script>
import axios from 'axios'

export default {
	name: 'HelloWorld',
	data() {
		return {}
	},
	methods: {
		test_multipartfile: function () {
			axios({
				method: 'post',
			})
		},
	},
}
</script>
