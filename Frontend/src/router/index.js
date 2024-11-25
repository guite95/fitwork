import { createRouter, createWebHistory } from 'vue-router';
import { useMemberStore } from '../stores/member';
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
import Swal from 'sweetalert2';
import '@/assets/swal_custom.css'
import ClubsDetail from '../components/ClubsDetail.vue';
import ClassesDetail from '../components/ClassesDetail.vue';
import Search from '../components/Search.vue';
import AdminView from '../components/AdminView.vue';
import VisitorStats from '../components/VisitorStats.vue';

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
    path: '/register-clubs/:clubNo?',
    name: 'registerclubs',
    component: RegisterClubs,
    props: true,
  },
  {
    path: '/register-classes/:classNo?',
    name: 'registerclasses',
    component: RegisterClasses,
    props: true,
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
    meta: { requiresAuth: true },
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
  {
    path: "/clubs/:clubNo",
    name: "clubsdetail",
    component: ClubsDetail,
    props: true,
  },
  {
    path: "/classes/:classNo",
    name: "classesdetail",
    component: ClassesDetail,
    props: true,
  },
  {
    path: "/search",
    name: "search",
    component: Search,
    props: (route) => ({
      type: route.query.type, // '클럽' 또는 '클래스'
      query: route.query.query, // 검색어
    }),
  },
  {
    path: "/admin",
    name: "admin",
    component: AdminView,
    children: [
      {
        path: "",
        name: visitorstat,
        component: VisitorStats,
      },
    ]
  },


  // 다른 경로 추가 가능
];

// 라우터 생성
const router = createRouter({
  history: createWebHistory(),
  routes,
});

router.beforeEach((to, from, next) => {
  const memberStore = useMemberStore();

  if (!memberStore.isLoggedIn && to.meta.requiresAuth) {
    Swal.fire({
      icon: 'info',
      title: '로그인이 필요합니다',
      text: '로그인 페이지로 이동합니다.',
      customClass: {
        title: 'custom-swal-title',
        text: 'custom-swal-text',
        confirmButton: 'custom-swal-button',
      },
      buttonsStyling: false,
      confirmButtonColor: '#486284',
      confirmButtonText: '확인',
    }).then(() => {
      next({ path: "/sign-in", query: { redirect: to.fullPath } }); // 리다이렉트 경로 전달
    });
  } else {
    next();
  }
});


export default router;
