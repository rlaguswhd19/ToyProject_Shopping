<template>
	<div class="content_wrap">
		<div style="display: flex; margin-bottom: 100px;">
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
							label="성별"
							v-model="dressDto.sex"
							dense
							solo
							style="width: 30%; margin-right: 5%;"
						></v-select>
						<v-select
							:items="dress_types"
							label="옷 타입"
							v-model="dressDto.category"
							style="width: 60%;"
							dense
							solo
						></v-select>
					</div>
					<div class="input_wrap">
						<input
							type="text"
							class="hj_input"
							min="100"
							v-model="dressDto.name"
							placeholder="이름"
						/>
						<input
							type="text"
							class="hj_input"
							min="0"
							max="100"
							v-model="dressDto.article_number"
							placeholder="제품코드"
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
													<td>수량</td>
												</thead>
												<tbody>
													<tr
														v-for="(s,
														idx) in sizes"
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
																style="
																	width: 100%;
																"
															/>
														</td>
														<td v-if="checks[idx]">
															<input
																type="text"
																class="td_input"
																v-model="
																	s.width
																"
																style="
																	width: 100%;
																"
															/>
														</td>
														<td v-if="checks[idx]">
															<input
																type="text"
																class="td_input"
																v-model="
																	s.height
																"
																style="
																	width: 100%;
																"
															/>
														</td>
														<td v-if="checks[idx]">
															<input
																type="text"
																class="td_input"
																v-model="
																	s.count
																"
																style="
																	width: 100%;
																"
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
					<div class="content_row" style="margin-bottom: 50px;">
						<div v-for="s in sizes" :key="s.size">
							<button
								v-if="s.count == 0"
								class="size_button"
								disabled
								style="
									background-color: #ddd;
									color: #999;
									outline: none;
								"
							>
								{{ s.size }}
							</button>
							<button v-else class="size_button">
								{{ s.size }}
							</button>
						</div>
					</div>
					<div>
						<span>colors</span>
						<v-select
							:items="colors"
							label="색상"
							v-model="dressDto.color"
							dense
							solo
							style="width: 100%;"
						></v-select>
						<div></div>
					</div>
					<div class="content_row">
						<input
							type="number"
							class="hj_input"
							placeholder="가격"
							v-model="dressDto.price"
							style="width: 60%; margin-right: 10%;"
						/>
						<input
							type="number"
							class="hj_input"
							placeholder="할인율"
							v-model="dressDto.discount"
							style="width: 30%;"
						/>
					</div>
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
		<h2>Explanation</h2>
		<textarea
			rows="10"
			style="width: 100%; outline: 1px black solid;"
			v-model="dressDto.explanation"
		>
		</textarea>
	</div>
</template>

<script>
require('../../css/dress/dressDetail.css')

export default {
	data() {
		return {
			dressDto: {
				name: ' DSN-Logo Tee Black',
				article_number: 'TN20S0137',
				category: 'Top',
				sex: 'Men',
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
				{ size: 'XXXS', info: '', width: '', height: '', count: '' },
				{ size: 'XXS', info: '', width: '', height: '', count: '' },
				{ size: 'XS', info: '', width: '', height: '', count: '' },
				{
					size: 'S',
					info: '85',
					width: '43',
					height: '50',
					count: '10',
				},
				{
					size: 'M',
					info: '90',
					width: '45',
					height: '60',
					count: '10',
				},
				{
					size: 'L',
					info: '95',
					width: '47',
					height: '70',
					count: '10',
				},
				{ size: 'XL', info: '', width: '', height: '', count: '' },
				{ size: 'XXL', info: '', width: '', height: '', count: '' },
				{ size: 'XXXL', info: '', width: '', height: '', count: '' },
			],
			checks: [true, true, true, true, true, true, true, true, true],
		}
	},
	methods: {
		reset_size() {
			this.sizes = [
				{ size: 'XXXS', info: '', width: '', height: '', count: '' },
				{ size: 'XXS', info: '', width: '', height: '', count: '' },
				{ size: 'XS', info: '', width: '', height: '', count: '' },
				{ size: 'S', info: '', width: '', height: '', count: '' },
				{ size: 'M', info: '', width: '', height: '', count: '' },
				{ size: 'L', info: '', width: '', height: '', count: '' },
				{ size: 'XL', info: '', width: '', height: '', count: '' },
				{ size: 'XXL', info: '', width: '', height: '', count: '' },
				{ size: 'XXXL', info: '', width: '', height: '', count: '' },
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
						size.height == '' ||
						size.count == ''
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
			if (this.files.length > 8) {
				alert('8개 이상의 파일은 업로드가 불가합니다.')
				this.files = []
				return
			}

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
		delete_dimage() {
			console.log(this.dressDto)
			this.$axios({
				method: 'delete',
				url:
					'http://localhost:8080/api/dimages/' +
					this.dressDto.dimage.id,
				headers: {
					'Content-Type': 'application/json;charset=UTF-8',
					Accept: 'application/hal+json;charset=UTF-8',
				},
			}).then(response => {
				console.log(response)
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
			})
				.then(response => {
					alert('등록 완료!!')
					console.log(response)
				})
				.catch(error => {
					// 만드는데 실패하면 dimage 지우기
					this.delete_dimage()
					console.log(error)
				})
		},
	},
}
</script>
