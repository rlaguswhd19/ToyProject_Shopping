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
							:width="smallSize.width"
							:height="smallSize.height"
							:alt="previews[representationPreview].name"
							contain
							style="outline: 1px black solid;"
							@mouseover="bigPreview = representationPreview"
						/>
					</div>
					<div class="preview_list" v-else>
						<v-img
							:id="'preview_' + representationPreview"
							:src="previews[representationPreview].url"
							:width="smallSize.width"
							:height="smallSize.height"
							:alt="previews[representationPreview].name"
							contain
							@mouseover="bigPreview = representationPreview"
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
							:width="smallSize.width"
							:height="smallSize.height"
							:alt="preview.name"
							contain
							@mouseover="bigPreview = preview.id"
						/>

						<v-img
							v-if="
								preview.id != representationPreview &&
								preview.id == bigPreview
							"
							:id="'preview_' + preview.id"
							:src="preview.url"
							:width="smallSize.width"
							:height="smallSize.height"
							:alt="preview.name"
							outline="2px black solid"
							contain
							style="outline: 1px black solid;"
							@mouseover="bigPreview = preview.id"
						/>
					</div>
				</div>
			</div>

			<div class="big_preview_wrap">
				<v-img
					v-if="previews.length == 0"
					class="big_preview"
					src="../../assets/noimage.jpg"
					:width="bigSize.width"
					:height="bigSize.height"
					contain
					alt="noimage"
				/>
				<v-img
					v-else
					class="big_preview"
					:src="previews[bigPreview].url"
					:width="bigSize.width"
					:height="bigSize.height"
					contain
					:alt="previews[bigPreview].name"
				/>
				<div class="content_row">
					<v-file-input
						type="file"
						show-size
						counter
						multiple
						label="File input"
						v-model="files"
						accept="image/*"
						@change="change_image"
						dense
						outlined
					></v-file-input>
					<v-btn
						@click="representationPreview = bigPreview"
						color="primary"
						>대표이미지</v-btn
					>
				</div>
			</div>
		</div>
		<!-- 입력 -->
		<div class="info_wrap">
			<v-card style="height: 800px; padding: 20px;">
				<h2>Dress Info</h2>
				<span>category</span>
				<div class="category_wrap">
					<v-select
						:items="sexs"
						label="Sex"
						v-model="dressDto.sex"
						dense
						solo
						style="width: 30%; margin-right: 5%;"
					></v-select>
					<v-select
						:items="dress_types"
						label="DressType"
						v-model="dressDto.dress_type"
						style="width: 60%;"
						dense
						solo
					></v-select>
				</div>
				<div class="input_wrap">
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
				</div>
				<!-- 여기도 selector 색깔 에시를 주고 선택하기 색깔 보여주고... -->
				<div class="content_row" style="margin-bottom: 5px;">
					<a @click="size_chart = true">
						<i class="mdi mdi-chart-bar" />size_chart
					</a>
					<span
						v-if="dressDto.dsize.length == 0"
						style="color: red; margin-left: auto;"
						>! 사이즈 정보를 입력하세요</span
					>
				</div>
				<v-dialog
					v-model="size_chart"
					max-width="1000px"
					persistent
					scrollable
				>
					<v-card>
						<div class="size_chart_wrap">
							<v-card-title
								>Size Info
								<v-btn
									text
									style="margin-left: auto;"
									fab
									@click="size_chart = false"
								>
									<i class="mdi mdi-close"
								/></v-btn>
							</v-card-title>
							<v-card-text>
								<div
									class="content_row"
									style="margin-bottom: 30px;"
								>
									<v-checkbox
										v-for="(s, idx) in sizes"
										:key="idx"
										:label="s.size"
										v-model="checks[idx]"
										class="hj_check"
									/>
								</div>
								<div class="content_row">
									<div class="table_wrap">
										<table class="size_chart">
											<thead>
												<th
													style="width: 52; !important"
												>
													사이즈
												</th>
												<td>일반 표시</td>
												<td>가슴 둘레(cm)</td>
												<td>신장(cm)</td>
											</thead>
											<tbody>
												<tr
													v-for="(s, idx) in sizes"
													:key="idx"
												>
													<th v-if="checks[idx]">
														{{ s.size }}
													</th>
													<td v-if="checks[idx]">
														<input
															type="text"
															class="td_input"
															v-model="s.info"
															style="width: 100%;"
														/>
													</td>
													<td v-if="checks[idx]">
														<input
															type="text"
															class="td_input"
															v-model="s.width"
															style="width: 100%;"
														/>
													</td>
													<td v-if="checks[idx]">
														<input
															type="text"
															class="td_input"
															v-model="s.height"
															style="width: 100%;"
														/>
													</td>
												</tr>
											</tbody>
										</table>
									</div>
									<v-img
										src="../../assets/sizeinfo.jpg"
										contain
										height="300"
										width="250"
										style="outline: black 1px solid;"
									/>
								</div>
							</v-card-text>
							<v-card-actions>
								<v-spacer></v-spacer>

								<v-btn
									color="green darken-1"
									text
									@click="reset_size"
								>
									reset
								</v-btn>

								<v-btn
									color="primary"
									text
									@click="update_dsize"
								>
									submit
								</v-btn>
							</v-card-actions>
						</div>
					</v-card>
				</v-dialog>
				<v-select
					:items="colors"
					label="Color"
					v-model="dressDto.color"
					dense
					solo
					style="width: 100%;"
				></v-select>
				<div class="content_row">
					<v-text-field
						placeholder="Price"
						v-model="dressDto.price"
						style="width: 50%; margin-right: 30px;"
					></v-text-field>
					<v-text-field
						placeholder="Discount"
						v-model="dressDto.discount"
						style="width: 20%;"
					></v-text-field>
				</div>
				<v-textarea
					label="Explanation"
					solo
					v-model="dressDto.explanation"
				></v-textarea>
				<v-btn
					@click="post_dress"
					color="primary"
					width="100%"
					style="margin: auto;"
					>등록</v-btn
				>
			</v-card>
		</div>
	</div>
</template>

<script>
require('../../css/dress/dressDetail.css')

export default {
	data() {
		return {
			dressDto: {
				brand: 'THISISNEVERTHAT',
				name: ' DSN-Logo Tee Black',
				article_number: 'TN20S0137',
				dress_type: '',
				sex: '',
				price: '39000',
				color: '색상을 선택해 주세요',
				discount: '30',
				explanation: 'DSN-Logo Tee Black',
				dimage: '',
				dsize: [],
			},
			colors: [
				'RED',
				'ORANGE',
				'YELLOW',
				'GREEN',
				'BLUE',
				'PURPLE',
				'BLACK',
				'WHITE',
			],
			files: '',
			previews: [],
			// 대표 이미지 인덱스
			representationPreview: 0,
			// 크게 보이는 이미지
			bigPreview: 0,
			bigSize: { width: 630, height: 700 },
			smallSize: { width: 40, height: 50 },

			sexs: ['Men', 'Women', 'Free'],
			dress_types: ['Top', 'Bottom'],
			size_chart: false,
			sizes: [
				{ size: 'XXXS', info: '', width: '', height: '' },
				{ size: 'XXS', info: '', width: '', height: '' },
				{ size: 'XS', info: '', width: '', height: '' },
				{ size: 'S', info: '', width: '', height: '' },
				{ size: 'M', info: '', width: '', height: '' },
				{ size: 'L', info: '', width: '', height: '' },
				{ size: 'XL', info: '', width: '', height: '' },
				{ size: 'XXL', info: '', width: '', height: '' },
				{ size: 'XXXL', info: '', width: '', height: '' },
			],
			checks: [true, true, true, true, true, true, true, true, true],
		}
	},
	methods: {
		reset_size() {
			this.sizes = [
				{ size: 'XXXS', info: '', width: '', height: '' },
				{ size: 'XXS', info: '', width: '', height: '' },
				{ size: 'XS', info: '', width: '', height: '' },
				{ size: 'S', info: '', width: '', height: '' },
				{ size: 'M', info: '', width: '', height: '' },
				{ size: 'L', info: '', width: '', height: '' },
				{ size: 'XL', info: '', width: '', height: '' },
				{ size: 'XXL', info: '', width: '', height: '' },
				{ size: 'XXXL', info: '', width: '', height: '' },
			]

			this.checks = [true, true, true, true, true, true, true, true, true]

			this.dressDto.dsize = []
		},

		update_dsize() {
			this.dressDto.dsize = []
			let isOk = true

			for (let i = 0; i < this.sizes.length; i++) {
				let size = this.sizes[i]
				if (this.checks[i]) {
					if (
						size.size == '' ||
						size.info == '' ||
						size.width == '' ||
						size.height == ''
					) {
						alert('error')
						this.dressDto.dsize = []
						isOk = false
						break
					}
					this.dressDto.dsize.push(size)
				}
			}

			if (isOk) {
				this.size_chart = false
			}
		},

		change_image() {
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

			this.$axios({
				method: 'post',
				url: 'http://localhost:8080/api/dimages',
				data: formData,
				headers: {
					'Content-Type': 'multipart/form-data',
				},
			}).then(response => {
				this.dressDto.dimage = response.data
				this.post_dressDto()
			})
		},

		post_dressDto() {
			this.$axios({
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
