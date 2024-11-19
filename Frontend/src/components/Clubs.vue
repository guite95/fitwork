<template>
     <div class="bg-gray-50 min-h-screen">
      <!-- Header -->
        <Header />
        
      <!-- 콘텐츠 -->
      <section class="px-8 py-12 bg-gray-50">
        <h1 class="text-3xl font-title text-darkBlue mb-6">운동 클럽 🏃‍♀️</h1>
  
        <!-- 검색 섹션 -->
        <!-- Search Section -->
        <div class="flex justify-end mb-8">
          <input
            type="text"
            placeholder="클럽 검색하기"
            class="px-4 py-2 border border-gray-300 rounded-2xl focus:outline-none focus:ring-2 focus:ring-lightBlue font-title"
          />
        </div>
  
        <!-- 추천 클럽 -->
        <div class="mb-12">
          <h2 class="text-xl font-title text-lightBlue mb-4">20대 여성 <span class="text-darkBlue font-title">이 관심있는</span></h2>
          <div class="grid grid-cols-3 gap-6">
            <div class="bg-gray-100 p-4 rounded-md shadow h-24"></div>
            <div class="bg-gray-100 p-4 rounded-md shadow h-24"></div>
            <div class="bg-gray-100 p-4 rounded-md shadow h-24"></div>
          </div>
        </div>
  
        <!-- 인기 클럽 -->
        <div class="mb-12">
          <h2 class="text-xl font-title text-darkBlue mb-4">최근 인기 많은</h2>
          <div class="grid grid-cols-3 gap-6">
            <div class="bg-gray-100 p-4 rounded-md shadow h-24"></div>
            <div class="bg-gray-100 p-4 rounded-md shadow h-24"></div>
            <div class="bg-gray-100 p-4 rounded-md shadow h-24"></div>
          </div>
        </div>
   
        <!-- 새로운 클럽 만들기 -->
        <div class="text-center">
          <p class="text-xl mb-4 text-darkBlue font-title">마음에 드는 클럽이 없다면 ...</p>
          <router-link to="/register-clubs">
            <button class="px-6 py-3 bg-lightBlue text-white font-title rounded-md hover:bg-darkBlue">
              👉 새로운 클럽 만들기
            </button>
          </router-link>
        </div>

      </section>
    </div>
  </template>
  
  <script setup>
  import { ref } from "vue";
  import Header from "./Header.vue";
  
  const name = ref("김싸피");
  </script>
  
  <style scoped> 
  /* 추가 스타일 */
  </style>
  