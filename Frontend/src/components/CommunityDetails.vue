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
      <div class="flex justify-between items-center text-gray-500 text-sm font-title mb-6">
        <div>
          <span>조회수 {{ board.viewCnt }}</span>
          <span class="ml-3">{{ board.regDate }}</span>
        </div>
        <!-- 수정 및 삭제 버튼 (본인 글일 경우에만 표시) -->
        <div class="flex space-x-2">
          <button @click="navigateBack"
            class="px-4 py-2 bg-lightBlue text-white rounded-full hover:bg-greyBlue transition duration-300 text-sm font-title">돌아가기</button>
          <div v-if="isAuthor" class="flex space-x-2">
            <button @click="handleEdit"
              class="px-4 py-2 bg-greyBlue text-white rounded-full hover:bg-darkBlue transition duration-300 text-sm font-title">수정</button>
            <button @click="handleDelete"
              class="px-4 py-2 bg-darkBlue text-white rounded-full hover:bg-lightBlue transition duration-300 text-sm font-title">삭제</button>
          </div>
        </div>
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
    imgSrc.value = `http://localhost:8080/file${board.value.boardFile.path}/${board.value.boardFile.systemName}`;
    console.log(imgSrc.value)
  }
  );
};

// 수정 버튼 클릭 핸들러
function handleEdit() {
  router.push({
    name: 'newpost',
    params: {
      boardNo: boardNo, // boardNo를 수정 페이지로 전달
    },
  });
}


// 삭제 버튼 클릭 핸들러
const handleDelete = () => {
  Swal.fire({
    title: '정말 삭제하시겠습니까?',
    text: '이 작업은 되돌릴 수 없습니다.',
    icon: 'warning',
    showCancelButton: true,
    confirmButtonColor: '#486284',
    cancelButtonColor: '#7B95B7',
    confirmButtonText: '네, 삭제합니다',
    cancelButtonText: '취소'
  }).then((result) => {
    if (result.isConfirmed) {
      store.deleteBoard(boardNo)
        .then(() => {
          Swal.fire('삭제 완료!', '게시글이 삭제되었습니다.', 'success').then(() => {
            router.push('/community');
          });
        })
        .catch((error) => {
          console.error('게시글 삭제 중 오류가 발생했습니다:', error);
          Swal.fire('오류', '게시글 삭제 중 문제가 발생했습니다. 다시 시도해주세요.', 'error');
        });
    }
  });
};

// 돌아가기 버튼 클릭 핸들러
const navigateBack = () => {
  router.push('/community');
};

// 컴포넌트가 마운트될 때 게시글 상세 정보 가져오기
onMounted(() => {
  detail();
});
</script>

<style scoped></style>
