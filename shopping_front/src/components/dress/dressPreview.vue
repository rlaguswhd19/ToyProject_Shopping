<template>
	<div>
		<div class="preview_wrap">
			<div class="preview_list_wrap">
				<div
					class="preview_list"
					v-for="preview in previews"
					:key="preview.idx"
				>
					<v-img
						v-if="preview.idx == currentPreview"
						:src="preview.url"
						:width="smallSize.width"
						:height="smallSize.height"
						:alt="preview.name"
						contain
						style="outline: 1px black solid;"
						@mouseover="currentPreview = preview.idx"
					/>
					<v-img
						v-else
						:src="preview.url"
						:width="smallSize.width"
						:height="smallSize.height"
						:alt="preview.name"
						contain
						@mouseover="currentPreview = preview.idx"
					/>
				</div>
			</div>
			<div class="preview_current_wrap">
				<v-img
					v-if="previews.length == 0"
					class="preview_current"
					src="../../assets/noimage.jpg"
					:width="bigSize.width"
					:height="bigSize.height"
					contain
					alt="noimage"
				/>
				<v-img
					v-else
					class="preview_current"
					:src="previews[currentPreview].url"
					:width="bigSize.width"
					:height="bigSize.height"
					contain
					:alt="previews[currentPreview].name"
				/>
			</div>
		</div>
		<div class="preview_input_wrap">
			<input
				type="file"
				multiple
				id="file_input"
				accept="image/*"
				@change="change_image($event)"
			/>

			<v-btn icon color="blue" @click="swap_image(true)"
				><v-icon>mdi-arrow-up</v-icon></v-btn
			>
			<v-btn icon color="blue" @click="swap_image(false)"
				><v-icon>mdi-arrow-down</v-icon></v-btn
			>
		</div>
	</div>
</template>

<script>
import { EventBus } from '../../utils/eventBus'
require('../../css/dress/dressPreview.css')

export default {
	data() {
		return {
			files: [],
			previews: [],
			// 크게 보이는 이미지
			currentPreview: 0,
			bigSize: { width: 630, height: 700 },
			smallSize: { width: 40, height: 50 },
		}
	},
	methods: {
		change_image(event) {
			this.files = event.target.files

			if (this.files.length > 8) {
				alert('8개 이상의 파일은 업로드가 불가합니다.')
				this.files = []
				this.previews = []
				event.target.files = []
				return
			}

			this.previews = []

			for (let i = 0; i < this.files.length; i++) {
				this.previews.push({
					idx: i,
					url: URL.createObjectURL(this.files[i]),
					name: this.files[i].name,
				})
			}

			console.log(this.previews)
			// 여기서 이벤트 버스 보내기
		},
		swap_image(up) {
			let preview = this.previews[this.currentPreview]

			if (up) {
				if (this.currentPreview == 0) {
					return
				}

				this.previews[this.currentPreview] = this.previews[
					this.currentPreview - 1
				]
				this.previews[this.currentPreview].idx += 1

				this.previews[this.currentPreview - 1] = preview
				this.previews[this.currentPreview - 1].idx -= 1
				this.currentPreview -= 1

				console.log('up')
			} else {
				if (this.currentPreview == this.previews.length - 1) {
					return
				}

				this.previews[this.currentPreview] = this.previews[
					this.currentPreview + 1
				]
				this.previews[this.currentPreview].idx -= 1

				this.previews[this.currentPreview + 1] = preview
				this.previews[this.currentPreview + 1].idx += 1
				this.currentPreview += 1

				console.log('down')
			}
		},
	},
}
</script>

<style>
.preview_wrap {
	display: flex;
	outline: 1px black solid;
}

.preview_list_wrap {
	width: 60px;
	margin-right: 20px;
	outline: 1px red solid;
}

.preview_current_wrap {
	outline: 1px blue solid;
}

.preview_input_wrap {
	display: flex;
}
</style>
