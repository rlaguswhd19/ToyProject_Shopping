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
		<v-btn @click="post_dressDto_files" style="float: right;">등록</v-btn>
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
		post_dressDto_files() {
			let formData = new FormData()

			for (let i = 0; i < this.files.length; i++) {
				formData.append('files', this.files[i])
			}

			formData.append('brand', this.dressDto.brand)
			formData.append('article_number', this.dressDto.article_number)
			formData.append('dress_type', this.dressDto.dress_type)
			formData.append('sex', this.dressDto.sex)
			formData.append('sale', this.dressDto.sale)
			formData.append('discount', this.dressDto.discount)
			formData.append('explanation', this.dressDto.explanation)

			axios({
				method: 'post',
				url: 'http://localhost:8080/api/dress/dressDto',
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
