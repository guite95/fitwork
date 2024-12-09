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
            <input id="title" v-model="title" type="text" placeholder="제목"
              class="w-full px-4 py-2 rounded-full border border-gray-300 focus:outline-none focus:ring-2 focus:ring-lightBlue bg-white placeholder-gray-400 font-title text-darkBlue"
              required />
          </div>

          <!-- 게시판 선택 -->
          <div>
            <label for="category" class="block text-darkBlue font-title mb-2">게시판 선택</label>
            <select id="category" v-model="category"
              class="w-full px-4 py-3 rounded-full border border-gray-300 focus:outline-none focus:ring-2 focus:ring-lightBlue bg-white text-gray-500 font-title"
              required>
              <option value="" disabled>게시판을 선택하세요</option>
              <option value="club">클럽 후기</option>
              <option value="class">클래스 후기</option>
              <option value="chat">잡담</option>
            </select>
          </div>

          <!-- Content Textarea -->
          <div>
            <label for="content" class="block text-darkBlue font-title mb-2">본문</label>
            <textarea id="content" v-model="content" rows="10" placeholder="본문을 적어주세요 😊"
              class="w-full px-4 py-3 rounded-2xl border border-gray-300 focus:outline-none focus:ring-2 focus:ring-lightBlue bg-white placeholder-gray-400 resize-none font-title text-darkBlue"
              required></textarea>
          </div>

          <!-- Form Section에서 파일 업로드 부분 -->
          <div>
            <label for="fileUpload" class="block text-darkBlue font-title mb-2">파일 추가</label>
            <div class="flex items-center">
              <!-- Hidden File Input -->
              <input id="fileUpload" type="file" ref="fileInput" class="hidden pointer-events-none" tabindex="-1"
                @change="handleFileChange" />

              <!-- Custom Button -->
              <button type="button" @click="triggerFileUpload"
                class="px-4 py-3 bg-lightBlue text-white font-title rounded-full hover:bg-darkBlue transition duration-300 text-sm">
                파일 선택
              </button>

              <!-- Selected File Name Display and Remove Button -->
              <div v-if="selectedFile" class="flex items-center ml-4">
                <span class="text-gray-600 font-title">{{ selectedFile.name }}</span>
                <button type="button" @click="removeSelectedFile"
                  class="ml-2 text-gray-500 hover:text-red-500 transition duration-300" aria-label="파일 삭제">
                  ✖
                </button>
              </div>
            </div>
          </div>

          <!-- Buttons -->
          <div class="flex justify-between items-center">
            <!-- Cancel Button -->
            <button type="button" @click="navigateBack"
              class="px-6 py-3 bg-white border border-lightBlue text-lightBlue font-title rounded-full hover:bg-lightBlue/10">
              취소
            </button>

            <!-- Submit Button -->
            <button type="submit" class="px-6 py-3 bg-lightBlue text-white font-title rounded-full hover:bg-darkBlue">
              {{ isEditMode ? "수정 완료" : "게시글 등록" }}
            </button>
          </div>
        </form>
      </div>
    </section>
  </div>
</template>

<script setup>
import { ref, onMounted } from "vue";
import { useRouter, useRoute } from "vue-router";
import { useBoardStore } from "@/stores/board";
import Header from "./Header.vue"; // 헤더 컴포넌트

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

// 컴포넌트가 마운트될 때 route의 state 데이터를 확인하고 폼에 반영
onMounted(() => {
  const boardNo = route.params.boardNo;

  if (boardNo) {
    // 수정 모드일 경우 기존 게시글 정보 가져오기
    boardStore.getBoardDetail(boardNo).then(() => {
      const post = boardStore.board;
      if (post) {
        title.value = post.title;
        content.value = post.content;
        category.value = post.category;
        postId.value = boardNo;
        isEditMode.value = true;
      }
    });
  } else {
    // 새 글 작성 모드일 경우
    title.value = "";
    content.value = "";
    category.value = "";
    postId.value = null;
    isEditMode.value = false;
  }
});

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

function triggerFileUpload() {
  fileInput.value.click();
}

function handleFileChange(event) {
  selectedFile.value = event.target.files[0];
}

function removeSelectedFile() {
  selectedFile.value = null;
  fileInput.value.value = ""; // 파일 input 필드를 초기화
}

async function handleSubmit() {
  const formData = new FormData();
  formData.append(
    "board",
    new Blob(
      [
        JSON.stringify({
          title: title.value,
          writer: sessionStorage.getItem("memberNickname"),
          content: content.value,
          category: category.value,
        }),
      ],
      { type: "application/json" }
    )
  );

  if (selectedFile.value) {
    formData.append("file", selectedFile.value);
  }

  try {
    if (isEditMode.value && postId.value) {
      // 수정 로직
      await boardStore.modifyBoard(postId.value, formData);
    } else {
      // 새 게시글 등록 로직
      await boardStore.writeBoard(formData);
    }

    // 글 작성/수정 성공 시 커뮤니티 게시판으로 이동
    router.push("/community");
  } catch (error) {
    console.error("글 작성/수정 중 오류가 발생했습니다:", error);
  }
}
</script>


<style scoped>
/* 추가 스타일 */
</style>
