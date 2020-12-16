<template>
	<div class="main_nav_wrap">
		<div class="logo">
			<ul>
				<li><a href="http://localhost:3000">H STORE </a></li>
			</ul>
		</div>
		<div class="category_nav">
			<ul>
				<li><a @click="go('/')">Men</a></li>
				<li><a @click="go('/about')">Women</a></li>
				<li><a>Kid</a></li>
				<li><a>Sport</a></li>
				<li><a>Brand</a></li>
			</ul>
		</div>

		<div class="search_bar">
			<input type="search" size="30" id="_search_bar" />
			<v-btn text style="margin: auto 0;">
				<v-icon>mdi-magnify</v-icon>
			</v-btn>
		</div>
	</div>
</template>

<script>
export default {
	methods: {
		go(to) {
			this.$router.push(to)
		},
	},
}
</script>

<style>
.main_nav_wrap {
	display: flex;
	height: 60px;
	margin: auto 0;
}

.logo {
	width: 20%;
	padding: 0 16px;
}

.logo a {
	font-size: 40px;
	font-weight: 1000;
	font-style: oblique;
	text-decoration: none;
}

.logo li {
	list-style-type: none;
}

.category_nav {
	display: flex;
	margin: auto;
}

.category_nav ul {
	display: flex;
}

.category_nav ul a {
	color: black;
}

.category_nav li {
	padding: 0 40px;
	font-size: 18px;
	font-weight: 600;
	color: black;
	list-style-type: none;
	margin: auto 0;
}

.category_nav a:hover {
	color: red;
}

.search_bar {
	width: 20%;
	display: flex;
}

#_search_bar {
	border: 1px #e5e5e5 solid;
	border-radius: 2px;
	height: 40px;
	padding: 10px 10px 10px 10px;
	margin: auto 0;
}
#_search_bar:focus {
	outline: 2px solid #8d8d8d;
}
</style>
