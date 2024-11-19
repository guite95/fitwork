<template>
    <div class="bg-gray-50 min-h-screen">
      <!-- Header -->
        <Header />
        
      <!-- Content -->
      <section class="px-8 py-12">
        <h1 class="text-3xl font-title text-darkBlue mb-6">운동 클래스 🏋️‍♀️</h1>
  
        <!-- Search Section -->
        <div class="flex justify-end mb-8">
          <input
            type="text"
            placeholder="클래스 검색하기"
            class="px-4 py-2 border border-gray-300 rounded-2xl focus:outline-none focus:ring-2 focus:ring-lightBlue font-title"
          />
        </div>
  
        <!-- Nearby Classes -->
        <div class="mb-12">
          <h2 class="text-xl font-title text-darkBlue mb-4"><span class="text-lightBlue">유성구</span> 근처의</h2>
          <div class="grid grid-cols-4 gap-6">
            <div class="bg-gray-100 p-4 rounded-md shadow h-32"></div>
            <div class="bg-gray-100 p-4 rounded-md shadow h-32"></div>
            <div class="bg-gray-100 p-4 rounded-md shadow h-32"></div>
            <div class="bg-gray-100 p-4 rounded-md shadow h-32"></div>
          </div>
        </div>
  
        <!-- Popular Classes -->
        <div class="mb-12">
          <h2 class="text-xl font-title text-darkBlue mb-4">평점 높은</h2>
          <div class="grid grid-cols-4 gap-6">
            <div class="bg-gray-100 p-4 rounded-md shadow h-32"></div>
            <div class="bg-gray-100 p-4 rounded-md shadow h-32"></div>
            <div class="bg-gray-100 p-4 rounded-md shadow h-32"></div>
            <div class="bg-gray-100 p-4 rounded-md shadow h-32"></div>
          </div>
        </div>
  
        <!-- Open a New Class -->
        <div class="text-center">
          <p class="text-xl mb-4 text-darkBlue font-title">강사이신가요?</p>
          <router-link to="/register-classes">
            <button class="px-6 py-3 bg-lightBlue text-white font-title rounded-md hover:bg-darkBlue">
              👉 새로운 클래스 만들기
            </button>
          </router-link>
        </div>
        
      </section>
    </div>
  </template>
  
  <script setup>
  import { ref } from "vue";
  import Header from "./Header.vue";
  
  // User name for the greeting
  const name = ref("김싸피");
  </script>
  
  <style scoped>
  /* Additional styling if needed */
  </style>
  