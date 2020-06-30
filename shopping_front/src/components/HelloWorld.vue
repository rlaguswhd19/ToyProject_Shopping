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
		<v-btn @click="test_multipart" style="float: right;">등록</v-btn>
	</v-container>
</template>

<script>
import axios from 'axios'

export default {
	name: 'HelloWorld',

	data() {
		return {
			dressDto: {
				brand: '',
				article_number: '',
				dress_type: '',
				sex: '',
				sale: '',
				discount: '',
				explanation: '',
			},
		}
	},
	methods: {
		test_multipart: function () {
			axios({
				method: 'post',
				url: 'http://localhost:8080/api/dress',
				data: this.dressDto,
			}).then(response => {
				console.log(response)
			})
		},
	},
}
</script>
