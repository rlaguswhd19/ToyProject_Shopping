<template>
	<div>
		<v-file-input
			type="file"
			show-size
			counter
			multiple
			label="File input"
			v-model="files"
			accept="image/*"
		></v-file-input>
		<v-btn @click="post_dress" style="float: right;">등록</v-btn>
		<v-text-field
			label="Brand"
			placeholder="브랜드를 입력하세요."
			outlined
			v-model="dressDto.brand"
		></v-text-field>
		<v-text-field
			label="Name"
			placeholder="이름을 입력하세요."
			outlined
			v-model="dressDto.name"
		></v-text-field>
		<v-text-field
			label="article_number"
			placeholder="품번을 입력하세요."
			outlined
			v-model="dressDto.article_number"
		></v-text-field>
		<v-text-field
			label="dress_type"
			placeholder="옷타입을 입력하세요."
			outlined
			v-model="dressDto.dress_type"
		></v-text-field>
		<v-text-field
			label="sex"
			placeholder="성별을 입력하세요."
			outlined
			v-model="dressDto.sex"
		></v-text-field>
		<v-text-field
			label="price"
			placeholder="가격을 입력하세요."
			outlined
			v-model="dressDto.price"
		></v-text-field>
		<v-text-field
			label="discount"
			placeholder="할인율을 입력하세요."
			outlined
			v-model="dressDto.discount"
		></v-text-field>
		<v-text-field
			label="explanation"
			placeholder="설명을 입력하세요."
			outlined
			v-model="dressDto.explanation"
		></v-text-field>
	</div>
</template>

<script>
import axios from 'axios'

export default {
	data() {
		return {
			dressDto: {
				brand: 'THISISNEVERTHAT',
				name: ' DSN-Logo Tee Black',
				article_number: 'TN20S0137',
				dress_type: 'Top',
				sex: 'Man',
				price: '39000',
				discount: '30',
				explanation: 'DSN-Logo Tee Black',
				dimage_id: '',
			},
			files: '',
		}
	},
	methods: {
		post_dress() {
			let formData = new FormData()

			for (let i = 0; i < this.files.length; i++) {
				formData.append('files', this.files[i])
			}

			axios({
				method: 'post',
				url: 'http://localhost:8080/api/dimages',
				data: formData,
				headers: {
					'Content-Type': 'multipart/form-data',
				},
			}).then(response => {
				this.dressDto.dimage_id = response.data.id
				console.log(this.dressDto)
				this.post_dressDto()
			})
		},

		post_dressDto() {
			axios({
				method: 'post',
				url: 'http://localhost:8080/api/dress',
				data: this.dressDto,
				headers: {
					'Content-Type': 'application/json;charset=UTF-8',
					Accept: 'application/hal+json;charset=UTF-8',
				},
			}).then(response => {
				console.log(response)
			})
		},
	},
}
</script>

<style></style>
