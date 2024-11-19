<template>
    <div class="bg-gray-50 min-h-screen">
      <!-- Header -->
      <Header />
  
      <!-- Main Content -->
      <div class="max-w-4xl mx-auto py-8">
        <!-- Post Title -->
        <div class="border-b pb-4 mb-6">
          <h1 class="text-2xl font-title text-darkBlue">
            어제 유성천에서 모여서 뛴 후기!
          </h1>
          <p class="text-greyBlue text-sm font-title">달려라하늬</p>
        </div>
  
        <!-- Post Image -->
        <div class="mb-6">
          <div class="w-full bg-gray-200 h-64 rounded-lg flex items-center justify-center">
           
          </div>
        </div>
  
        <!-- Post Content -->
        <div class="mb-6 text-gray-700 font-title text-base">
          어제 유성천에서 같이 뛴 분들 넘 즐거웠어요! <br />
          다음주에 또 뛰어요 ~~
        </div>
  
        <!-- Post Details -->
        <div class="flex justify-between items-center text-gray-500 text-sm font-title mb-6">
          <span>조회수 26</span>
          <span>2024.11.18 10:21</span>
        </div>
  
        <!-- Edit/Delete Buttons -->
        <div class="flex justify-end space-x-4 mb-8">
          <button
            class="px-4 py-2 bg-lightBlue text-white rounded-full hover:bg-darkBlue transition duration-300 text-sm font-title">
            수정
          </button>
          <button
            class="px-4 py-2 bg-white border border-gray-300 text-gray-700 rounded-full hover:bg-red-100 transition duration-300 text-sm font-title">
            삭제
          </button>
        </div>
  
        <!-- Comments Section -->
        <div class="border-t pt-6">
          <h2 class="text-lg font-title text-darkBlue mb-4">댓글</h2>
          <ul class="space-y-4">
            <li v-for="(comment, index) in comments" :key="index">
              <div class="flex justify-between">
                <p class="text-gray-700 text-sm font-title">{{ comment.content }}</p>
                <div class="text-gray-500 text-xs font-title">
                  <span class="font-title text-darkBlue">{{ comment.author }}</span>
                  <span>{{ comment.createdAt }}</span>
                </div>
              </div>
            </li>
          </ul>
        </div>
      </div>
    </div>
  </template>

<script setup>
import { ref } from "vue";
import Header from "./Header.vue";

// Sample comments data
const comments = ref([
  { content: "저도 너무 즐거웠습니다. 다음주에 또 봐요!", author: "로투스", createdAt: "2024.11.18 10:30" },
  { content: "로투스님 혹시 어느쪽 사세요?", author: "user2", createdAt: "2024.11.18 10:30" },
]);
</script>

<style scoped>

</style>




