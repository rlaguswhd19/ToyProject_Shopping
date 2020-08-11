<template>
	<div>
		<div class="content_row" style="outline: 1px black solid;">
			<div>Men</div>
			<div style="width: 200px; margin-left: auto;">
				<v-select
					:items="filter"
					label="filter"
					v-model="select"
					dense
					solo
				></v-select>
			</div>
		</div>
		<div class="content_row">
			<div class="bar">
				abs
			</div>
			<div class="Lists_wrap" style="outline: 1px red solid;">
				<div
					v-for="(dress, idx) in dressLists"
					:key="idx"
					class="Lists_dress"
				>
					{{ dress.id }}
				</div>
			</div>
		</div>
	</div>
</template>

<script>
require('../../css/dress/dressLists.css')
require('../../css/common/common.css')
export default {
	data() {
		return {
			filter: ['신상품순', '인기순', '높은가격순', '낮은가격순'],
			page: '0',
			select: '신상품순',
			dressLists: [],
		}
	},
	watch: {
		select: function () {
			this.getDressLists()
		},
		page: function () {
			this.getDressLists()
		},
	},
	mounted() {
		this.getDressLists()
	},
	methods: {
		getDressLists() {
			let sort
			if (this.select == '신상품순') {
				sort = 'id,desc'
			} else if (this.select == '인기순') {
				sort = 'id,desc'
			} else if (this.select == '높은가격순') {
				sort = 'price,desc'
			} else if (this.select == '낮은가격순') {
				sort = 'price,asc'
			}

			this.$axios({
				methods: 'get',
				url: 'http://localhost:8080/api/dress',
				params: {
					page: this.page,
					size: '20',
					sort: sort,
				},
				headers: {
					'Content-Type': 'application/json;charset=UTF-8',
					Accept: 'application/hal+json;charset=UTF-8',
				},
			}).then(response => {
				this.dressLists = response.data._embedded.dressList
				console.log(this.dressLists)
			})
		},
	},
}
</script>
