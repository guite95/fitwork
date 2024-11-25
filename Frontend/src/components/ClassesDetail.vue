<template>
  <div class="bg-gray-50 min-h-screen">
    <!-- Header -->
    <Header />

    <!-- Main Content -->
    <div class="max-w-4xl mx-auto py-8">
      <!-- Class Title -->
      <div class="border-b pb-4 mb-6">
        <h1 class="text-2xl font-title text-darkBlue">
          {{ classData.className }}
        </h1>
        <p class="text-darkBlue text-sm font-title"><span class="text-greyBlue">리더: </span> {{ classData.leader }}</p>
      </div>

      <!-- Class Details -->
      <div class="flex justify-between items-center text-darkBlue text-sm font-title mb-6">
        <div>
          <span class="ml-3"><span class="text-greyBlue">태그: </span> {{ classData.tag }}</span>
          <span class="ml-3"><span class="text-greyBlue">지역: </span> {{ classData.location }}</span>
          <span class="ml-3"><span class="text-greyBlue">신청 인원: </span> {{ classData.headCount }} 명</span>
          <span class="ml-3"><span class="text-greyBlue">가격: </span> {{ classData.price }} 만원</span>
        </div>
      </div>

      <!-- Class Image -->
      <div class="mb-6 flex justify-center">
        <img v-if="classData.classesFile" :src="imgSrc" alt="Class Image" class="rounded-lg object-contain" />
      </div>

      <!-- Class Description -->
      <div class="mb-6 text-gray-700 font-title text-base">
        {{ classData.description }}
      </div>

      <!-- Navigation Button -->
      <div class="flex justify-end items-center text-sm font-title mb-6">
        <!-- Edit and Delete Buttons (Visible only to the author) -->
        <div class="flex space-x-2">
          <button @click="navigateBack"
            class="px-4 py-2 bg-lightBlue text-white rounded-full hover:bg-greyBlue transition duration-300 text-sm font-title">
            돌아가기
          </button>
          <div v-if="isAuthor" class="flex space-x-2">
            <button @click="handleEdit"
              class="px-4 py-2 bg-greyBlue text-white rounded-full hover:bg-darkBlue transition duration-300 text-sm font-title">
              수정
            </button>
            <button @click="handleDelete"
              class="px-4 py-2 bg-darkBlue text-white rounded-full hover:bg-lightBlue transition duration-300 text-sm font-title">
              삭제
            </button>
          </div>
          <!-- 클래스 신청하기 버튼 -->
          <button v-else @click="handleJoin"
            class="px-4 py-2 bg-lightBlue text-white rounded-full hover:bg-darkBlue transition duration-300 text-sm font-title">
            클래스 신청하기
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
import { ref, onMounted, computed } from "vue";
import Header from "./Header.vue";
import { useRoute, useRouter } from "vue-router";
import { useClassStore } from "@/stores/class";
import { useMemberStore } from "@/stores/member";
import Swal from "sweetalert2";
import '@/assets/swal_custom.css';

const route = useRoute();
const router = useRouter();
const store = useClassStore();
const memberStore = useMemberStore();

const classData = ref({});
const comments = ref([]);
const newComment = ref("");
const imgSrc = ref("");

// Check if the current user is the author
const isAuthor = computed(() => {
  return (
    classData.value.leader &&
    memberStore.memberNickname &&
    classData.value.leader === memberStore.memberNickname
  );
});

const classNo = route.params.classNo;

const loadClassDetails = async () => {
  try {
    await store.getClassDetail(classNo);
    classData.value = store.classDetail;

    if (classData.value.classesFile) {
      imgSrc.value = `http://:192.168.210.83:8080/file/class${classData.value.classesFile.path}/${classData.value.classesFile.systemName}`;
    }

    comments.value = classData.value.comments || [];
  } catch (error) {
    console.error("클래스 상세 정보 로드 실패:", error);
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
    .addComment(classNo, { content: newComment.value })
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

const handleEdit = () => {
  router.push({
    name: "registerclasses",
    params: {
      classNo: classNo, // Pass classNo to the edit page
    },
  });
};

const handleDelete = () => {
  Swal.fire({
    title: "정말로 삭제하시겠습니까?",
    icon: "warning",
    showCancelButton: true,
    confirmButtonColor: "#3085d6",
    cancelButtonColor: "#d33",
    confirmButtonText: "네, 삭제합니다.",
  }).then((result) => {
    if (result.isConfirmed) {
      store
        .deleteClass(classNo)
        .then(() => {
          Swal.fire("삭제 완료!", "클래스가 삭제되었습니다.", "success");
          navigateBack();
        })
        .catch((error) => {
          console.error("클래스 삭제 실패:", error);
        });
    }
  });
};

const handleJoin = () => {
  if (!memberStore.memberId) {
    Swal.fire({
      icon: "warning",
      title: "로그인이 필요합니다.",
      text: "클래스 신청을 위해 로그인해주세요.",
      confirmButtonText: "로그인하기",
    }).then(() => {
      router.push({ path: "/sign-in", query: { redirect: router.currentRoute.value.fullPath } });
    });
    return;
  }

  store
    .registerClass(memberStore.memberId, classNo)
    .then(() => {
      Swal.fire({
        icon: "success",
        title: "신청 완료",
        text: "클래스 신청이 성공적으로 완료되었습니다.",
      });
    })
    .catch((error) => {
      console.error("클래스 신청 실패:", error);
    });
};

const navigateBack = () => {
  if (window.history.length > 1) {
    // 이전 페이지가 있는 경우
    router.back();
  } else {
    // 이전 페이지가 없는 경우 기본 경로로 이동
    router.push("/classes");
  }
};

onMounted(() => {
  loadClassDetails();
});
</script>

<style scoped></style>
