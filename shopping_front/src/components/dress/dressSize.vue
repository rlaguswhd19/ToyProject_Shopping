<template>
	<div>
		<div class="content_row" style="margin-bottom: 5px;">
			<a @click="size_chart = true">
				<i class="mdi mdi-chart-bar" />size_chart
			</a>
			<span
				v-if="dsize.length == 0"
				style="color: red; margin-left: auto;"
				>! 사이즈 정보를 입력하세요</span
			>
		</div>
		<v-dialog v-model="size_chart" max-width="1000px" persistent scrollable>
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
						<div class="content_row" style="margin-bottom: 30px;">
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
										<th style="width: 52; !important">
											사이즈
										</th>
										<td>일반 표시</td>
										<td>가슴 둘레(cm)</td>
										<td>신장(cm)</td>
										<td>수량</td>
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
												/>
											</td>
											<td v-if="checks[idx]">
												<input
													type="text"
													class="td_input"
													v-model="s.width"
												/>
											</td>
											<td v-if="checks[idx]">
												<input
													type="text"
													class="td_input"
													v-model="s.height"
												/>
											</td>
											<td v-if="checks[idx]">
												<input
													type="text"
													class="td_input"
													v-model="s.count"
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

						<v-btn color="primary" text @click="reset_size()">
							reset
						</v-btn>

						<v-btn color="primary" text @click="update_dsize()">
							submit
						</v-btn>
					</v-card-actions>
				</div>
			</v-card>
		</v-dialog>
		<div class="content_row" style="margin-bottom: 30px;">
			<div v-for="(s, idx) in sizes" :key="s.size">
				<button
					v-if="!checks[idx]"
					class="button_size"
					disabled
					style="background-color: #ddd; color: #999; outline: none;"
				>
					{{ s.size }}
				</button>
				<button v-else class="button_size">
					{{ s.size }}
				</button>
			</div>
		</div>
	</div>
</template>

<script>
import { EventBus } from '../../utils/eventBus'

export default {
	data() {
		return {
			dsize: [],
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
			this.dsize = []
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
						isOk = false
						break
					}
					this.dsize.push(size)
				}
			}

			if (isOk) {
				this.size_chart = false
			}

			EventBus.$emit('get_dsize', this.dsize)
		},
	},
}
</script>

<style>
.button_size {
	outline: 1px black solid;
	margin: 0 5px 10px 5px;
	width: 40px;
	height: 40px;
	text-align: center;
	font-size: 13px;
	/* background-color: #9b9b9b; */
	opacity: 70;
}

.size_chart_wrap {
	padding: 10px 90px;
}

.table_wrap {
	width: 100%;
	margin-right: 40px;
}

.table_wrap table,
th,
td {
	border: 1px #9b9b9b solid;
	text-align: center;
	border-collapse: collapse;
	font-size: 13px;
}

.table_wrap table {
	width: 100%;
}

.table_wrap thead {
	border-bottom: 2px black solid;
}

.table_wrap th,
td {
	height: 40px;
	width: 20%;
	text-align: center;
}

.table_wrap th {
	width: 10% !important;
}
</style>
