<template>
    <div class="bg-gray-50 px-8 py-6">
      <!-- 헤더 -->
      <header class="flex items-center justify-between px-8 py-4 bg-white shadow-md">
        <div class="flex items-center space-x-6">
          <img src="/images/logo.png" alt="Logo" class="h-10 object-contain" />
          <nav class="hidden md:flex space-x-8 text-gray-700">
            <router-link to="/" class="hover:text-darkBlue font-title">Home</router-link>
            <router-link to="/clubs" class="hover:text-darkBlue font-title">Clubs</router-link>
            <router-link to="/classes" class="hover:text-darkBlue font-title">Classes</router-link>
            <router-link to="/community" class="hover:text-darkBlue font-title">Community</router-link>
            <router-link to="/mypage" class="hover:text-darkBlue font-title">My Page</router-link>
          </nav>
        </div>
        <div class="flex items-center space-x-6">
          <span class="text-darkBlue font-title">안녕하세요, <strong>김싸피</strong> 님</span>
          <button class="px-6 py-2 font-title bg-darkBlue text-white rounded-full hover:bg-lightBlue">
            Logout
          </button>
        </div>
      </header>
  
      <!-- 콘텐츠 -->
      <section class="py-8">
        <h1 class="text-2xl font-title text-darkBlue mb-4">운동 클럽 🏃‍♀️</h1>
  
        <!-- 검색 섹션 -->
        <div class="flex justify-end mb-6">
          <input
            type="text"
            placeholder="클럽 검색하기"
            class="px-4 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-lightBlue"
          />
        </div>
  
        <!-- 추천 클럽 -->
        <div class="mb-12">
          <h2 class="text-xl font-title text-lightBlue mb-4">20대 여성이 관심있는</h2>
          <div class="grid grid-cols-3 gap-6">
            <div class="bg-gray-100 p-4 rounded-md shadow h-24"></div>
            <div class="bg-gray-100 p-4 rounded-md shadow h-24"></div>
            <div class="bg-gray-100 p-4 rounded-md shadow h-24"></div>
          </div>
        </div>
  
        <!-- 인기 클럽 -->
        <div class="mb-12">
          <h2 class="text-xl font-title text-lightBlue mb-4">최근 인기 많은</h2>
          <div class="grid grid-cols-3 gap-6">
            <div class="bg-gray-100 p-4 rounded-md shadow h-24"></div>
            <div class="bg-gray-100 p-4 rounded-md shadow h-24"></div>
            <div class="bg-gray-100 p-4 rounded-md shadow h-24"></div>
          </div>
        </div>
  
        <!-- 새로운 클럽 만들기 -->
        <div class="text-center">
          <p class="mb-4 text-gray-700 font-title">마음에 드는 클럽이 없다면 ...</p>
          <button class="px-6 py-3 bg-lightBlue text-white font-title rounded-md hover:bg-darkBlue">
            👉 새로운 클럽 만들기
          </button>
        </div>
      </section>
    </div>
  </template>
  
  <script>
  export default {
    name: "Club",
  };
  </script>
  
  <style scoped>
  /* 추가 스타일 */
  </style>
  