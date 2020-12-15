import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../views/Home.vue'
import About from '../views/About.vue'
import SignUp from '../views/account/SignUp.vue'
import SignIn from '../views/account/SignIn.vue'
import Update from '../views/account/Update.vue'
// import { component } from 'vue/types/umd'

Vue.use(VueRouter)

const routes = [
	{
		path: '/',
		name: 'Home',
		component: Home,
	},
	{
		path: '/about',
		name: 'About',
		component: About,
	},
	{
		path: '/account/signup',
		name: 'SignUp',
		component: SignUp,
	},
	{
		path: '/account/signin',
		name: 'SignIn',
		component: SignIn,
	},
	{
		path: '/account/update',
		name: 'Update',
		component: Update,
	},
]

const router = new VueRouter({
	mode: 'history',
	base: process.env.BASE_URL,
	routes,
})

export default router
