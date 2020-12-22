<template>
	<div style="margin: 0 auto; width: 100%; outline: 1px black solid;">
		<h1>HOME</h1>
	</div>
</template>

<script>
export default {
	name: 'Home',
	components: {},
	mounted() {
		for (var key in window.sessionStorage) {
			// getItem( ) 메서드를 이용하여 key 값의 value 값을 찾는다.
			console.log(key, sessionStorage.getItem(key))
		}
	},
}
</script>
