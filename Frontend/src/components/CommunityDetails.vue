<template>
  <div class="bg-gray-50 min-h-screen">
    <!-- Header -->
    <Header />

    <!-- Main Content -->
    <div class="max-w-4xl mx-auto py-8">
      <!-- Post Title -->
      <div class="border-b pb-4 mb-6">
        <h1 class="text-2xl font-title text-darkBlue">
          {{ board.title }}
        </h1>
        <p class="text-greyBlue text-sm font-title">{{ board.writer }}</p>
      </div>

      <!-- Post Image -->
      <div class="mb-6">
        <div class="w-full bg-gray-200 h-64 rounded-lg flex items-center justify-center">
          <!-- 이미지가 있을 경우 표시 -->
          <img v-if="board.boardFile" :src="imgSrc" alt="Post Image" class="w-full h-full object-cover rounded-lg" />
        </div>
      </div>

      <!-- Post Content -->
      <div class="mb-6 text-gray-700 font-title text-base">
        {{ board.content }}
      </div>

      <!-- Post Details -->
      <div class="flex justify-start items-center text-gray-500 text-sm font-title mb-6">
        <span>조회수 {{ board.viewCnt }}</span>
        <span class="ml-3">{{ board.regDate }}</span>
      </div>

      <!-- Comments Section -->
      <div class="border-t pt-6">
        <h2 class="text-lg font-title text-darkBlue mb-4">댓글</h2>
        <ul class="space-y-4">
          <li v-for="(comment, index) in board.comments" :key="index">
            <div class="flex justify-between">
              <p class="text-gray-700 text-sm font-title">{{ comment.content }}</p>
              <div class="text-gray-500 text-xs font-title">
                <span class="font-title text-darkBlue">{{ comment.author }}</span>
                <span class="ml-2">{{ comment.createdAt }}</span>
              </div>
            </div>
          </li>
        </ul>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import Header from './Header.vue';
import { useRoute } from 'vue-router';
import { useBoardStore } from '@/stores/board';

// Router
const route = useRoute();

// Store
const store = useBoardStore();
const board = ref({});
const imgSrc = ref('');

// 게시글 번호 가져오기
const boardNo = route.params.boardNo;

const detail = async () => {
  return new Promise( async (resolve) => {
    await store.getBoardDetail(boardNo);
    resolve();
  }).then(() => {
    board.value = store.board;
    imgSrc.value = `C:/SSAFY/final-prj/board${board.value.boardFile.path}/${board.value.boardFile.systemName}`
    console.log(imgSrc.value)
  });
}

// 컴포넌트가 마운트될 때 게시글 상세 정보 가져오기
onMounted(() => {
  detail();
});
</script>

<style scoped></style>
