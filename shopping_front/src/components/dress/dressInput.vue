<template>
	<div class="content_wrap">
		<div class="content_row">
			<dressPreview />
			<div class="input_wrap">
				<v-card style="height: 730px; padding: 20px;">
					<div class="content_row">
						<h2>Dress Info</h2>
						<p style="margin-left: auto;">
							가격:
							{{
								get_discountedPrice(
									dressDto.price,
									dressDto.discount,
								)
							}}
						</p>
					</div>

					<h4 style="margin-top: 20px;">category</h4>
					<div class="content_row" style="margin: 10px 0;">
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
					<div>
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
					<div style="margin-top: 40px;">
						<dressSize />
					</div>
					<div>
						<h4>colors</h4>
						<v-select
							:items="colors"
							label="색상"
							v-model="dressDto.color"
							dense
							solo
							style="width: 100%; margin: 10px 0;"
						></v-select>
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
						@click="test()"
						color="primary"
						width="100%"
						style="margin: auto; margin-top: 40px;"
						>등록</v-btn
					>
				</v-card>
			</div>
		</div>
		<div style="margin-top: 50px;">
			<h2>Explanation</h2>
			<textarea
				rows="10"
				style="width: 100%; outline: 1px black solid; padding: 7px;"
				v-model="dressDto.explanation"
			>
			</textarea>
			<div class="table_wrap">
				<table class="table_bottom">
					<tr>
						<th style="text-align: left;">
							제품코드
						</th>
						<td>
							<input
								type="text"
								class="td_input"
								v-model="dressDto.article_number"
								style="text-align: left;"
								readonly
							/>
						</td>
						<th style="text-align: left;">색상</th>
						<td>
							<input
								type="text"
								class="td_input"
								v-model="dressDto.color"
								style="text-align: left;"
								readonly
							/>
						</td>
					</tr>
					<tr>
						<th style="text-align: left;">소재</th>
						<td colspan="3">
							<input
								type="text"
								class="td_input"
								v-model="dressDto.material"
								style="text-align: left;"
							/>
						</td>
					</tr>
					<tr>
						<th style="text-align: left;">원산지</th>
						<td>
							<input
								type="text"
								class="td_input"
								v-model="dressDto.origin"
								style="text-align: left;"
							/>
						</td>
						<th style="text-align: left;">제조년월</th>
						<td style="text-align: left;">
							<input
								id="getDate"
								type="month"
								:max="dateMax"
								v-model="dressDto.manufacture"
							/>
						</td>
					</tr>
				</table>
			</div>
		</div>
	</div>
</template>

<script>
import dressPreview from './dressPreview'
import dressSize from './dressSize'
import { EventBus } from '../../utils/eventBus'

export default {
	components: {
		dressPreview,
		dressSize,
	},

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
				material: '겉면: 100%',
				origin: '중국',
				manufacture: '',
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
			sexs: ['Men', 'Women', 'Free'],
			dress_types: ['Top', 'Bottom'],
			dateMax: '',
			get_dimage: '',
		}
	},
	mounted() {
		let date = new Date()
		let month = date.getMonth() * 1
		let year = date.getFullYear() * 1
		month += 1
		if (month > 9) {
			this.dateMax = year + '-' + month
		} else {
			this.dateMax = year + '-0' + month
		}

		EventBus.$on('get_dimage', dimage => {
			this.get_dimage = dimage
			console.log(this.get_dimage)
		})

		EventBus.$on('get_dsize', dsize => {
			this.dressDto.dsize = dsize
			console.log(this.dressDto.dsize)
		})
	},

	methods: {
		get_discountedPrice(price, discount) {
			return (price * (100 - discount)) / 100
		},
		test() {
			console.log(this.dressDto)
			console.log(this.get_dimage)
		},
		post_dimage() {
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
					Authorization:
						'Bearer' + sessionStorage.getItem('access_token'),
					'Content-Type': 'multipart/form-data',
				},
			}).then(response => {
				this.dressDto.dimage = response.data
				this.post_dressDto()
			})
		},

		delete_dimage() {
			this.$axios({
				method: 'delete',
				url:
					'http://localhost:8080/api/dimages/' +
					this.dressDto.dimage.id,
				headers: {
					Authorization:
						'Bearer' + sessionStorage.getItem('access_token'),
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
					Authorization:
						'Bearer' + sessionStorage.getItem('access_token'),
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

<style>
.content_wrap {
	margin: 10px;
	width: 100%;
}

.info_wrap {
	justify-content: center;
	display: flex;
	flex-direction: column;
	margin-left: 70px;
	width: 100%;
	height: 750px;
	padding: 10px;
}

.input_wrap {
	display: flex;
	flex-direction: column;
	margin: 0 0 0 20px;
	margin-bottom: 30px;
}

input[type='number']::-webkit-outer-spin-button,
input[type='number']::-webkit-inner-spin-button {
	-webkit-appearance: none;
}

.category_wrap {
	/* outline: 1px black solid; */
	margin-bottom: 20px;
	display: flex;
}

/* .color_box:hover {
	box-shadow: 1px 1px 10px #077deb;
} */

/* .color_box:visited {
	box-shadow: 1px 1px 10px #077deb;
} */

#getDate:focus {
	outline: none;
}

.td_input {
	text-align: center;
	width: 100%;
}

.td_input:focus {
	outline: none;
}

.hj_check {
	margin: 0 auto;
}

.table_bottom th,
td {
	padding: 10px;
}
</style>
