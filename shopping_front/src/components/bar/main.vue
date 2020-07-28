<template>
	<div class="main_nav">
		<div class="logo">
			<a href="http://localhost:3000">H STORE </a>
		</div>
		<div class="category_nav">
			<ul>
				<li><a>Men</a></li>
				<li><a>Women</a></li>
				<li><a>Kid</a></li>
				<li><a>Sport</a></li>
				<li><a>Brand</a></li>
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
	display: flex;
	height: 60px;
	margin: auto 0;
}

.logo {
	border: 2px red solid;
	width: 20%;
}

.category_nav {
	display: flex;
	margin: auto;
	border: black 1px solid;
}

.category_nav ul li a {
	padding: 0 40px;
	font-size: 16px;
	font-weight: 600;
	color: black;
}

.category_nav ul li a:hover {
	color: red;
}

.search_bar {
	width: 20%;
	border: red 1px solid;
	display: flex;
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
