<template>
    <div class="bg-gray-50 min-h-screen">
      <!-- Header -->
      <Header />
  
      <!-- Main Content -->
      <div class="max-w-4xl mx-auto py-8">
        <!-- Club Title -->
        <div class="border-b pb-4 mb-6">
          <h1 class="text-2xl font-title text-darkBlue">
            {{ club.title }}
          </h1>
          <p class="text-greyBlue text-sm font-title">{{ club.host }}</p>
        </div>
  
        <!-- Club Image -->
        <div class="mb-6 flex justify-center">
          <img v-if="club.image" :src="imgSrc" alt="Club Image" class="rounded-lg object-contain" />
        </div>
  
        <!-- Club Description -->
        <div class="mb-6 text-gray-700 font-title text-base">
          {{ club.description }}
        </div>
  
        <!-- Club Details -->
        <div class="flex justify-between items-center text-gray-500 text-sm font-title mb-6">
          <div>
            <span>지역: {{ club.location }}</span>
            <span class="ml-3">태그: {{ club.tags }}</span>
          </div>
          <div class="flex space-x-2">
            <button @click="navigateBack"
              class="px-4 py-2 bg-lightBlue text-white rounded-full hover:bg-greyBlue transition duration-300 text-sm font-title">
              돌아가기
            </button>
          </div>
        </div>
  
        <!-- Comments Section -->
        <div class="border-t pt-6">
          <h2 class="text-lg font-title text-greyBlue mb-4">댓글</h2>
          <ul class="space-y-4">
            <li v-for="(comment, index) in comments" :key="index">
              <div class="flex justify-between">
                <p class="text-darkBlue text-sm font-title">{{ index + 1 }}. {{ comment.content }}</p>
                <div class="text-gray-500 text-xs font-title">
                  <span class="font-title text-greyBlue text-sm">{{ comment.writer }}</span>
                  <span class="ml-8">{{ comment.createdAt }}</span>
                </div>
              </div>
            </li>
          </ul>
        </div>
  
        <!-- Add Comment Section -->
        <div class="border-t pt-6 mt-6">
          <div class="flex space-x-4 items-center">
            <input type="text" v-model="newComment" placeholder="댓글을 작성하세요"
              class="w-full px-4 py-2 border border-gray-300 rounded-2xl focus:outline-none focus:ring-2 focus:ring-lightBlue text-darkBlue font-title text-sm" />
            <button @click="addComment"
              class="w-1/6 px-4 py-2 bg-lightBlue text-white font-title rounded-full hover:bg-darkBlue transition duration-300 text-sm">
              댓글 추가
            </button>
          </div>
        </div>
      </div>
    </div>
  </template>
  
  <script setup>
  import { ref, onMounted } from "vue";
  import Header from "./Header.vue";
  import { useRoute, useRouter } from "vue-router";
  import { useClubStore } from "@/stores/club";
  import Swal from "sweetalert2";
  
  const route = useRoute();
  const router = useRouter();
  const store = useClubStore();
  
  const club = ref({});
  const imgSrc = ref("");
  const comments = ref([]);
  const newComment = ref("");
  
  const clubId = route.params.clubId;
  
  const loadClubDetails = async () => {
    try {
      await store.getClubDetail(clubId);
      club.value = store.club;
  
      if (club.value.image) {
        imgSrc.value = `/images/${club.value.image}`;
      }
  
      comments.value = club.value.comments || [];
    } catch (error) {
      console.error("클럽 상세 정보 로드 실패:", error);
    }
  };
  
  const addComment = () => {
    if (newComment.value.trim() === "") {
      Swal.fire({
        icon: "warning",
        title: "댓글 내용이 비어있습니다.",
        text: "댓글을 입력해주세요.",
      });
      return;
    }
  
    store
      .addComment(clubId, { content: newComment.value })
      .then(() => {
        comments.value.push({
          content: newComment.value,
          writer: "현재 사용자",
          createdAt: new Date().toLocaleString(),
        });
        newComment.value = "";
      })
      .catch((error) => {
        console.error("댓글 추가 실패:", error);
      });
  };
  
  const navigateBack = () => {
    router.push("/clubs");
  };
  
  onMounted(() => {
    loadClubDetails();
  });
  </script>
  
  <style scoped></style>
  