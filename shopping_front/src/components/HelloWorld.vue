<template>
	<v-container>
		<v-text-field
			label="Brand"
			placeholder="브랜드를 입력하세요."
			outlined
			v-model="dressDto.brand"
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
			label="sale"
			placeholder="가격을 입력하세요."
			outlined
			v-model="dressDto.sale"
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
		<v-file-input
			type="file"
			show-size
			counter
			multiple
			label="File input"
			v-model="files"
		></v-file-input>
		<v-btn @click="test_multipart" style="float: right;">등록</v-btn>
		<v-btn @click="test_image" style="float: right;">이미지</v-btn>
	</v-container>
</template>

<script>
import axios from 'axios'

export default {
	name: 'HelloWorld',

	data() {
		return {
			dressDto: {
				brand: '현지',
				article_number: '현지',
				dress_type: 'top',
				sex: 'Man',
				sale: '123000',
				discount: '10',
				explanation: '현지',
			},
			files: '',
		}
	},
	methods: {
		test_dress() {
			axios({
				method: 'post',
				url: 'http://localhost:8080/api/dress',
				headers: {
					'Content-Type': 'application/json;charset=UTF-8',
				},
				data: this.dressDto,
			}).then(response => {
				console.log(response)
			})
		},
		test_multipart() {
			console.log(this.files)
			console.log('dressDto', this.dressDto)
			let formData = new FormData()
			formData.append('brand', this.dressDto.brand)
			formData.append('article_number', this.dressDto.article_number)
			formData.append('dress_type', this.dressDto.dress_type)
			formData.append('sex', this.dressDto.sex)
			formData.append('sale', this.dressDto.sale)
			formData.append('discount', this.dressDto.discount)
			formData.append('explanation', this.dressDto.explanation)

			for (let i = 0; i < this.files.length; i++) {
				formData.append('files', this.files[i])
			}

			axios({
				method: 'post',
				url: 'http://localhost:8080/api/dress/multipart',
				data: formData,
				headers: {
					'Content-Type': 'multipart/data-form',
				},
			}).then(response => {
				console.log(response)
			})
		},
		test_image() {
			console.log(this.files)

			let formData = new FormData()
			for (let i = 0; i < this.files.length; i++) {
				formData.append('files', this.files[i])
			}

			axios({
				method: 'post',
				url: 'http://localhost:8080/api/dress/test',
				data: formData,
				headers: {
					'Content-Type': 'multipart/form-data',
				},
			}).then(response => {
				console.log(response)
			})
		},
	},
}
</script>
