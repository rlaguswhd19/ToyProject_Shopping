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
			<div class="Lists_wrap">
				<div
					v-for="(dress, idx) in dressLists"
					:key="idx"
					class="Lists_dress"
					@click="Test(dress)"
				>
					<v-img
						:src="get_imagePath(dress)"
						contain
						height="230"
						width="230"
						style="margin: 1px 0 30px 0;"
					/>
					<div class="content_col">
						<h3>{{ dress.name }}</h3>
						<p>
							{{ dress.category }}
						</p>
						<p v-if="dress.discount != 0">
							<span
								style="
									color: red;
									opacity: 70;
									margin-right: 5px;
								"
							>
								{{ dress.discount_price }}원
							</span>
							<span
								style="
									text-decoration: line-through;
									color: #9b9b9b;
								"
							>
								{{ dress.price }}원
							</span>
						</p>
						<p v-else>
							<span
								style="
									color: red;
									opacity: 70;
									margin-right: 5px;
								"
							>
								{{ dress.price }}원
							</span>
						</p>
					</div>
				</div>
			</div>
		</div>
	</div>
</template>

<script>
require('../../css/dress/dressLists.css')
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
			this.get_dressLists()
		},
		page: function () {
			this.get_dressLists()
		},
	},
	mounted() {
		this.get_dressLists()
	},
	methods: {
		get_imagePath(dress) {
			let image_path = dress.dimage.image_files.split('/')
			let idx = dress.dimage.image_repIdx
			return (
				'http://localhost:8080/assets/images/' +
				dress.dimage.id +
				'/' +
				image_path[idx]
			)
		},
		get_dressLists() {
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
				if (response.data._embedded != null) {
					this.dressLists = response.data._embedded.dressList
				}
				// console.log(response.data)
			})
		},
	},
}
</script>
