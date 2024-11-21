import { createRouter, createWebHistory } from 'vue-router';
import Home from '../components/Home.vue'; // Home 컴포넌트 임포트
import Clubs from '../components/Clubs.vue'
import Classes from '../components/Classes.vue'
import RegisterClubs from '../components/RegisterClubs.vue';
import RegisterClasses from '../components/RegisterClasses.vue';
import SignIn from '../components/SignIn.vue';
import Register from '../components/Register.vue';
import ForgotPassword from '../components/ForgotPassword.vue';
import ForgotUsername from '../components/ForgotUsername.vue';
import NewPost from '../components/NewPost.vue';
import Community from '../components/Community.vue';
import CommunityDetails from '../components/CommunityDetails.vue';
import MyPage from '../components/MyPage.vue';
import ProfileEdit from '../components/ProfileEdit.vue';
import Applications from '../components/Applications.vue';

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
  {
    path: '/classes',
    name: 'classes',
    component: Classes,
  },
  {
    path: '/register-clubs',
    name: 'registerclubs',
    component: RegisterClubs,
  },
  {
    path: '/register-classes',
    name: 'registerclasses',
    component: RegisterClasses,
  },
  {
    path: '/sign-in',
    name: 'signin',
    component: SignIn,
  },
  {
    path: '/register',
    name: 'register',
    component: Register,
  },
  {
    path: '/forgot-password',
    name: 'forgotpassword',
    component: ForgotPassword,
  },
  {
    path: '/forgot-username',
    name: 'forgotusername',
    component: ForgotUsername,
  },
  {
    path: '/community',
    name: 'community',
    component: Community,
  },
  {
    path: '/new-post/:boardNo?', // boardNo는 선택적 파라미터입니다.
    name: 'newpost',
    component: NewPost,
    props: true, // props를 통해 boardNo를 컴포넌트에 전달합니다.
  },  
  {
    path: '/community-details/:boardNo',
    name: 'communitydetails',
    component: CommunityDetails,
    props: true,
  },
  {
    path: '/mypage',
    name: 'mypage',
    component: MyPage,
    children: [
      {
        path: '', // 기본 자식 경로
        name: 'default',
        redirect: '/mypage/profile-edit', // "회원정보 조회/수정"으로 리다이렉트
      },
      {
        path: 'profile-edit',
        name: 'profileedit',
        component: ProfileEdit,
      },
      {
        path: 'applications',
        name: 'applications',
        component: Applications,
      },
    ],
  },


  // 다른 경로 추가 가능
];

// 라우터 생성
const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;
