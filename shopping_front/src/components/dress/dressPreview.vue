<template>
	<div>
		<div class="preview_wrap">
			<div class="preview_list_wrap">
				<div
					class="preview_list"
					v-for="(preview, index) in previews"
					:key="preview.id"
				>
					<v-img
						v-if="index == currentPreview_index"
						:src="preview.url"
						:width="smallSize.width"
						:height="smallSize.height"
						:alt="preview.name"
						contain
						style="outline: 1px black solid;"
						@mouseover="change_current(preview, index)"
					/>
					<v-img
						v-else
						:src="preview.url"
						:width="smallSize.width"
						:height="smallSize.height"
						:alt="preview.name"
						contain
						@mouseover="change_current(preview, index)"
					/>
				</div>
			</div>
			<div class="preview_current_wrap">
				<div
					v-if="previews.length == 0"
					class="preview_current"
					style="
						outline: 1px black solid;
						width: 630px;
						height: 700px;
					"
				>
					<h2>
						등록된 이미지가 없습니다.
					</h2>
				</div>
				<v-img
					v-else
					class="preview_current"
					:src="currentPreview.url"
					:width="bigSize.width"
					:height="bigSize.height"
					contain
					:alt="currentPreview.name"
				/>
				<div class="preview_input_wrap">
					<input
						type="file"
						multiple
						id="file_input"
						accept="image/*"
						@change="change_image($event)"
						hidden
					/>
					<div style="margin-right: auto;">
						<v-btn
							color="primary"
							style="margin-right: 10px;"
							@click="select()"
							>이미지 등록</v-btn
						>

						<v-btn @click="get_dimage()" color="primary">
							이미지 저장</v-btn
						>
						<v-btn icon color="blue" @click="swap_image(true)"
							><v-icon>mdi-arrow-up</v-icon></v-btn
						>
						<v-btn icon color="blue" @click="swap_image(false)"
							><v-icon>mdi-arrow-down</v-icon></v-btn
						>
					</div>
					<div v-if="dimage_check" style="color: red;">
						<p style="margin-left: auto;">
							!이미지를 저장해주세요.
						</p>
					</div>
				</div>
			</div>
		</div>
	</div>
</template>

<script>
import { EventBus } from '../../utils/eventBus'
// require('../../css/dress/dressPreview.css')

export default {
	data() {
		return {
			files: [],
			previews: [],
			// 크게 보이는 이미지
			currentPreview: null,
			currentPreview_index: 0,
			bigSize: { width: 630, height: 700 },
			smallSize: { width: 40, height: 50 },
			dimage_check: false,
		}
	},
	methods: {
		select() {
			$('#file_input').click()
		},
		change_image(event) {
			this.files = event.target.files

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

			this.currentPreview_index = 0
			this.currentPreview = this.previews[this.currentPreview_index]
			console.log(this.previews)
			this.dimage_check = true
		},
		swap_image(up) {
			let preview = this.previews[this.currentPreview]

			if (up) {
				if (this.currentPreview_index == 0) {
					return
				}

				this.previews[this.currentPreview_index] = this.previews[
					this.currentPreview_index - 1
				]
				this.previews[
					this.currentPreview_index - 1
				] = this.currentPreview
				this.currentPreview_index -= 1

				console.log('up')
			} else {
				if (this.currentPreview_index == this.previews.length - 1) {
					return
				}

				this.previews[this.currentPreview_index] = this.previews[
					this.currentPreview_index + 1
				]
				this.previews[
					this.currentPreview_index + 1
				] = this.currentPreview
				this.currentPreview_index += 1

				console.log('down')
			}

			this.dimage_check = true
		},

		change_current(preview, index) {
			this.currentPreview = preview
			this.currentPreview_index = index
		},

		get_dimage() {
			let idx = ''
			for (let i = 0; i < this.previews.length; i++) {
				idx += this.previews[i].id
				if (i != this.previews.length - 1) {
					idx += '/'
				}
			}

			let dimage = {
				id: idx,
				files: this.files,
			}
			this.dimage_check = false
			EventBus.$emit('get_dimage', dimage)
		},
	},
}
</script>

<style>
.preview_wrap {
	display: flex;
}

.preview_list_wrap {
	width: 60px;
	margin-right: 20px;
}

.preview_current_wrap {
}

.preview_input_wrap {
	display: flex;
	margin-top: 30px;
}
</style>
