import Vue from 'vue'
import Vuex from 'vuex'
import createPersistedState from 'vuex-persistedstate'

Vue.use(Vuex)

export default new Vuex.Store({
	state: {
		auth: '',
		login: false,
		email: '',
	},
	mutations: {},
	actions: {},
	modules: {},
	plugins: [createPersistedState()],
})
