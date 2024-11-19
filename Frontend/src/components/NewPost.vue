<template>
    <div class="bg-gray-50 min-h-screen">
      <!-- Header -->
      <Header />
  
      <!-- Content -->
      <section class="flex justify-center items-center py-12 px-4 bg-gray-50">
        <div class="w-full max-w-3xl bg-lightBlue/10 p-8 rounded-2xl shadow-md">
          <h1 class="text-2xl font-title text-darkBlue mb-8">게시글 작성</h1>
  
          <!-- Form -->
          <form class="space-y-6">
            <!-- Title Input -->
            <div>
              <label for="title" class="block text-darkBlue font-title mb-2">제목</label>
              <input
                id="title"
                type="text"
                placeholder="제목"
                class="w-full px-4 py-2 rounded-full border border-gray-300 focus:outline-none focus:ring-2 focus:ring-lightBlue bg-white placeholder-gray-400 font-title text-darkBlue"
              />
            </div>
  
            <!-- 게시판 선택 -->
            <div>
              <label for="category" class="block text-darkBlue font-title mb-2">게시판 선택</label>
              <select
                id="category"
                class="w-full px-4 py-3 rounded-full border border-gray-300 focus:outline-none focus:ring-2 focus:ring-lightBlue bg-white text-gray-500 font-title"
              >
                <option value="" disabled>게시판을 선택하세요</option>
                <option value="club-review">클럽 후기</option>
                <option value="class-review">클래스 후기</option>
                <option value="general">잡담</option>
              </select>
            </div>
  
            <!-- Content Textarea -->
            <div>
              <label for="content" class="block text-darkBlue font-title mb-2">본문</label>
              <textarea
                id="content"
                rows="10"
                placeholder="본문을 적어주세요 😊"
                class="w-full px-4 py-3 rounded-2xl border border-gray-300 focus:outline-none focus:ring-2 focus:ring-lightBlue bg-white placeholder-gray-400 resize-none font-title text-darkBlue"
              ></textarea>
            </div>
  
            <!-- Buttons -->
            <div class="flex justify-between items-center">
              <!-- Cancel Button -->
              <button
                type="button"
                @click="navigateToCommunity"
                class="px-6 py-3 bg-white border border-lightBlue text-lightBlue font-title rounded-full hover:bg-lightBlue/10"
              >
                취소
              </button>
  
              <!-- Submit and File Buttons -->
              <div class="flex space-x-4">
                <button
                  type="button"
                  class="px-6 py-3 bg-white border border-lightBlue text-lightBlue font-title rounded-full hover:bg-lightBlue/10"
                >
                  파일 추가
                </button>
                <button
                  type="submit"
                  class="px-6 py-3 bg-lightBlue text-white font-title rounded-full hover:bg-darkBlue"
                >
                  게시글 등록
                </button>
              </div>
            </div>
          </form>
        </div>
      </section>
    </div>
  </template>
  
  <script setup>
  import { ref } from "vue";
  import { useRouter } from "vue-router";
  import Header from "./Header.vue"; // 헤더 컴포넌트
  
  // Router
  const router = useRouter();
  
  // Methods
  function navigateToCommunity() {
    router.push("/community"); // Community 페이지로 이동
  }
  </script>
  
  <style scoped>
  /* 추가 스타일 */
  </style>
  