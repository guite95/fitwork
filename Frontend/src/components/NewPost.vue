<template>
  <div class="bg-gray-50 min-h-screen">
    <!-- Header -->
    <Header />

    <!-- Content -->
    <section class="flex justify-center items-center py-12 px-4 bg-gray-50">
      <div class="w-full max-w-3xl bg-lightBlue/10 p-8 rounded-2xl shadow-md">
        <h1 class="text-2xl font-title text-darkBlue mb-8">
          {{ isEditMode ? "게시글 수정" : "게시글 작성" }}
        </h1>

        <!-- Form -->
        <form class="space-y-6" @submit.prevent="handleSubmit">
          <!-- Title Input -->
          <div>
            <label for="title" class="block text-darkBlue font-title mb-2">제목</label>
            <input
              id="title"
              v-model="title"
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
              v-model="category"
              class="w-full px-4 py-3 rounded-full border border-gray-300 focus:outline-none focus:ring-2 focus:ring-lightBlue bg-white text-gray-500 font-title"
            >
              <option value="" disabled>게시판을 선택하세요</option>
              <option value="club">클럽 후기</option>
              <option value="class">클래스 후기</option>
              <option value="chat">잡담</option>
            </select>
          </div>

          <!-- Content Textarea -->
          <div>
            <label for="content" class="block text-darkBlue font-title mb-2">본문</label>
            <textarea
              id="content"
              v-model="content"
              rows="10"
              placeholder="본문을 적어주세요 😊"
              class="w-full px-4 py-3 rounded-2xl border border-gray-300 focus:outline-none focus:ring-2 focus:ring-lightBlue bg-white placeholder-gray-400 resize-none font-title text-darkBlue"
            ></textarea>
          </div>

          <!-- File Upload -->
          <div>
            <label for="fileUpload" class="block text-darkBlue font-title mb-2">파일 추가</label>
            <input
              id="fileUpload"
              type="file"
              ref="fileInput"
              class="block w-full text-gray-500 px-4 py-3 border border-gray-300 rounded-full cursor-pointer focus:outline-none focus:ring-2 focus:ring-lightBlue bg-white"
              @change="handleFileChange"
            />
          </div>

          <!-- Buttons -->
          <div class="flex justify-between items-center">
            <!-- Cancel Button -->
            <button
              type="button"
              @click="navigateBack"
              class="px-6 py-3 bg-white border border-lightBlue text-lightBlue font-title rounded-full hover:bg-lightBlue/10"
            >
              취소
            </button>

            <!-- Submit Button -->
            <button
              type="submit"
              class="px-6 py-3 bg-lightBlue text-white font-title rounded-full hover:bg-darkBlue"
            >
              {{ isEditMode ? "수정 완료" : "게시글 등록" }}
            </button>
          </div>
        </form>
      </div>
    </section>
  </div>
</template>

<script setup>
import { ref, watch } from "vue";
import { useRouter, useRoute } from "vue-router";
import { useBoardStore } from "@/stores/board";
import Header from "./Header.vue"; // 허더 컴포넌트

// Store
const boardStore = useBoardStore();

// Router
const router = useRouter();
const route = useRoute();

// State
const isEditMode = ref(false);
const title = ref("");
const content = ref("");
const category = ref("");
const postId = ref(null); // 게시글 ID
const fileInput = ref(null);
const selectedFile = ref(null);

// Watch route query for edit mode
watch(
  () => route.state,
  (newState) => {
    if (newState && newState.title && newState.content && newState.category) {
      title.value = newState.title;
      content.value = newState.content;
      category.value = newState.category;
      postId.value = newState.id || null;
      isEditMode.value = true;
    } else {
      title.value = "";
      content.value = "";
      category.value = "";
      postId.value = null;
      isEditMode.value = false;
    }
  },
  { immediate: true }
);

// Methods
function navigateBack() {
  if (isEditMode.value && postId.value) {
    // 수정 모드에서 취소 시 원래 게시글로 이동
    router.push(`/community-details/${postId.value}`);
  } else {
    // 새 글 작성 모드에서 취소 시 커뮤니티로 이동
    router.push("/community");
  }
}

function handleFileChange(event) {
  selectedFile.value = event.target.files[0];
}

function handleSubmit() {
  const formData = new FormData();
  formData.append("board", new Blob([JSON.stringify({
    title: title.value,
    writer: sessionStorage.getItem('memberNickname'),
    content: content.value,
    category: category.value,
  })], {type: "application/json"}));

  if (selectedFile.value) {
    formData.append("file", selectedFile.value);
  }

  if (isEditMode.value && postId.value) {
    // 수정 로직
    boardStore.modifyBoard(postId.value, formData);
  } else {
    // 새 게시글 등록 로직
    boardStore.writeBoard(formData);
  }
}
</script>

<style scoped>
/* 추가 스타일 */
</style>
