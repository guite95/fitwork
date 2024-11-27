<template>
    <div class="bg-gray-50 min-h-screen">
      <!-- Header -->
      <Header />
  
      <!-- Main Content -->
      <div class="flex">
        <!-- Sidebar -->
        <aside class="w-1/6 bg-gray-100 min-h-screen p-6 shadow-md">
          <h3 class="underline text-lg font-title text-darkBlue mb-4">마이페이지</h3>
          <ul class="space-y-4 text-greyBlue">
            <li>
              <router-link
                to="/mypage/profile-edit"
                class="hover:text-lightBlue font-title"
                active-class="font-bold text-lightBlue"
              >
                회원정보 조회/수정
              </router-link>
            </li>
            <li>
              <router-link
                to="/mypage/applications"
                class="hover:text-lightBlue font-title"
                active-class="font-bold text-lightBlue"
              >
                신청/개설 목록
              </router-link>
            </li>
            <li>
              <router-link
                :to="{ name: 'admin' }"
                class="hover:text-lightBlue font-title"
                active-class="font-bold text-lightBlue"
                v-if="memberRole === 'ROLE_ADMIN'"
              >
                방문자 통계
              </router-link>
            </li>
          </ul>
        </aside>
  
        <!-- Main Section -->
        <section class="flex-1 p-8">
          <router-view></router-view>
        </section>
      </div>
      <Footer />
    </div>
  </template>
  
  <script setup>
  import Header from './Header.vue';
  import Footer from './Footer.vue';
  import { useMemberStore } from '../stores/member';

  const memberStore = useMemberStore();
  
  const memberRole = memberStore.memberRole;
  </script>
  
  <style scoped>
  /* 추가 스타일 */
  </style>
  