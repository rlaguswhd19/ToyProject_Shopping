<template>
	<div>
		<!-- <v-btn @click="getAddress">Test</v-btn> -->
		<v-btn @click="getAddress()" color="primary">우편번호</v-btn>
	</div>
</template>

<script src="https://t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>

<script>
require('../../css/common/common.css')
import { EventBus } from '../../utils/eventBus'

export default {
	props: [],

	data() {
		return {}
	},
	methods: {
		getAddress() {
			var address = {
				post: '',
				road: '',
				jibun: '',
				detail: '',
				building: '',
			}

			new daum.Postcode({
				oncomplete(data) {
					address.post = data.zonecode
					address.road = data.roadAddress
					address.jibun = data.jibunAddress
					address.building = data.buildingName

					EventBus.$emit('get_address', address)
					// this.address.post_num = data.zonecode
					// this.address.road_addr = data.roadAddress
					// this.address.jibun_addr = data.jibunAddress
					// this.extra_addr = data.buildingName
				},
				onclose(state) {
					if (state === 'FORCE_CLOSE') {
						console.log('window 종료')
					} else if (state === 'COMPLETE_CLOSE') {
						//사용자가 검색결과를 선택하여 팝업창이 닫혔을 경우, 실행될 코드를 작성하는 부분입니다.
						//oncomplete 콜백 함수가 실행 완료된 후에 실행됩니다.
						console.log('address 종료')
					}
				},
			}).open()
		},
	},
}
</script>
