<template>
	<div class="main_nav">
		<a href="http://localhost:3000"
			><img src="../../assets/maison_de.png" height="100"
		/></a>
		<div class="category_nav">
			<ul>
				<li><v-btn x-large text>Men</v-btn></li>
				<li><v-btn x-large text>Women</v-btn></li>
				<li><v-btn x-large text>kid</v-btn></li>
				<li><v-btn x-large text>sport</v-btn></li>
				<li><v-btn x-large text>brands</v-btn></li>
			</ul>
		</div>

		<div class="search_bar">
			<input type="search" size="30" id="_search_bar" />
			<v-btn text>
				<v-icon>mdi-magnify</v-icon>
			</v-btn>
		</div>
	</div>
</template>

<script>
export default {
	data() {},
}
</script>

<style>
.main_nav {
	border: 2px solid black;
	display: flex;
	/* vertical-align: middle; */
}

.category_nav {
	display: flex;
	/* margin-left: auto; */
	margin: auto 0;
}

.search_bar {
	display: flex;
	margin: auto 0;
	margin-left: auto;
}

#_search_bar {
	border: 1px #e5e5e5 solid;
	border-radius: 2px;
	height: 40px;
	padding: 10px 10px 10px 10px;
}
#_search_bar:focus {
	outline: 2px solid #8d8d8d;
}
</style>
