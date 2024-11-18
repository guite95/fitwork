import { createRouter, createWebHistory } from 'vue-router';
import Home from '../components/Home.vue'; // Home 컴포넌트 임포트
import Clubs from '../components/Clubs.vue'

// 라우트 설정
const routes = [
  {
    path: '/',
    name: 'home',
    component: Home,
  },
  {
    path: '/clubs',
    name: 'clubs',
    component: Clubs,
  },
  // 다른 경로 추가 가능
];

// 라우터 생성
const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;
