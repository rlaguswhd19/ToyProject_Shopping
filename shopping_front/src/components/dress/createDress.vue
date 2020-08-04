<template>
	<div class="content_wrap">
		<div class="preview_wrap">
			<div class="preview_list_wrap">
				<!-- <div v-for="preview in previews" :key="preview.id"> -->
				<div v-if="previews.length != 0">
					<div
						class="preview_list"
						v-if="bigPreview == representationPreview"
					>
						<v-img
							:id="'preview_' + representationPreview"
							:src="previews[representationPreview].url"
							width="50"
							height="60"
							:alt="previews[representationPreview].name"
							contain
							style="outline: 2px black solid;"
							@mouseover="mouseover(representationPreview)"
						/>
					</div>
					<div class="preview_list" v-else>
						<v-img
							:id="'preview_' + representationPreview"
							:src="previews[representationPreview].url"
							width="50"
							height="60"
							:alt="previews[representationPreview].name"
							contain
							@mouseover="mouseover(representationPreview)"
						/>
					</div>

					<div
						class="preview_list"
						v-for="preview in previews"
						:key="preview.id"
					>
						<!-- v-if로 idx값에 따라 outline을 설정하기 -->
						<v-img
							v-if="
								preview.id != representationPreview &&
								preview.id != bigPreview
							"
							:id="'preview_' + preview.id"
							:src="preview.url"
							width="50"
							height="60"
							:alt="preview.name"
							contain
							@mouseover="mouseover(preview.id)"
						/>

						<v-img
							v-if="
								preview.id != representationPreview &&
								preview.id == bigPreview
							"
							:id="'preview_' + preview.id"
							:src="preview.url"
							width="50"
							height="60"
							:alt="preview.name"
							outline="2px black solid"
							contain
							style="outline: 2px black solid;"
							@mouseover="mouseover(preview.id)"
						/>
					</div>
				</div>
			</div>

			<div class="big_preview_wrap">
				<v-img
					v-if="previews.length == 0"
					class="big_preview"
					src="../../assets/noimage.jpg"
					width="650"
					height="720"
					contain
					alt="noimage"
				/>
				<v-img
					v-else
					class="big_preview"
					:src="previews[bigPreview].url"
					width="650"
					height="720"
					contain
					:alt="previews[bigPreview].name"
				/>
				<v-file-input
					type="file"
					show-size
					counter
					multiple
					label="File input"
					v-model="files"
					accept="image/*"
					@change="changeImages"
				></v-file-input>
				<v-btn @click="changeRepresentation">대표이미지</v-btn>
			</div>
		</div>
		<div class="input_wrap">
			<div>
				<input
					type="text"
					class="hj_input"
					v-model="dressDto.sex"
					placeholder="sex"
				/>
				<input
					type="text"
					class="hj_input"
					placeholder="category"
					v-model="dressDto.dress_type"
				/>
			</div>

			<input
				type="text"
				class="hj_input"
				v-model="dressDto.name"
				placeholder="name"
			/>
			<input
				type="text"
				class="hj_input"
				v-model="dressDto.article_number"
				placeholder="article_number"
			/>

			<!-- 여기도 selector -->
			<v-text-field
				label="Color"
				placeholder="색깔을 입력하세요."
				v-model="dressDto.color"
			></v-text-field>

			<v-text-field
				label="price"
				placeholder="가격을 입력하세요."
				v-model="dressDto.price"
			></v-text-field>
			<v-text-field
				label="discount"
				placeholder="할인율을 입력하세요."
				v-model="dressDto.discount"
			></v-text-field>
			<v-text-field
				label="explanation"
				placeholder="설명을 입력하세요."
				v-model="dressDto.explanation"
			></v-text-field>
			<v-btn @click="post_dress">등록</v-btn>
		</div>
	</div>
</template>

<script>
import axios from 'axios'

require('../../css/dress/dressDetail.css')

export default {
	data() {
		return {
			dressDto: {
				brand: 'THISISNEVERTHAT',
				name: ' DSN-Logo Tee Black',
				article_number: 'TN20S0137',
				dress_type: 'Top',
				sex: 'Men',
				price: '39000',
				color: 'blue',
				discount: '30',
				explanation: 'DSN-Logo Tee Black',
				dimage: '',
				dsize: [],
			},

			files: '',
			previews: [],
			// 대표 이미지 인덱스
			representationPreview: 0,
			// 크게 보이는 이미지
			bigPreview: 0,
			// 현재 선택된 미리보기
		}
	},
	methods: {
		mouseover(id) {
			//bigPreview 변경
			this.bigPreview = id
		},

		changeRepresentation() {
			this.representationPreview = this.bigPreview
		},

		changeImages() {
			this.previews = []

			for (let i = 0; i < this.files.length; i++) {
				this.previews.push({
					id: i,
					url: URL.createObjectURL(this.files[i]),
					name: this.files[i].name,
				})
			}
		},

		post_dress() {
			let formData = new FormData()

			for (let i = 0; i < this.files.length; i++) {
				formData.append('files', this.files[i])
			}

			formData.append('idx', this.representationPreview)

			axios({
				method: 'post',
				url: 'http://localhost:8080/api/dimages',
				data: formData,
				headers: {
					'Content-Type': 'multipart/form-data',
				},
			}).then(response => {
				console.log(response)
				this.dressDto.dimage = response.data
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
