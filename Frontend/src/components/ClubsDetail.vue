<template>
  <div class="bg-gray-50 min-h-screen">
    <!-- Header -->
    <Header />

    <!-- Main Content -->
    <div class="max-w-4xl mx-auto py-8">
      <!-- Club Title -->
      <div class="border-b pb-4 mb-6">
        <h1 class="text-2xl font-title text-darkBlue">
          {{ club.clubName }}
        </h1>
        <p class="text-darkBlue text-sm font-title"><span class="text-greyBlue">리더: </span> {{ club.leader }}</p>
      </div>

      <!-- Club Details -->
      <div class="flex justify-between items-center text-darkBlue text-sm font-title mb-6">
        <div>
          <span class="ml-3"><span class="text-greyBlue">태그: </span> {{ club.tag }}</span>
          <span class="ml-3"><span class="text-greyBlue">지역: </span> {{ club.location }}</span>
          <span class="ml-3"><span class="text-greyBlue">신청 인원: </span> {{ club.headCount }} 명</span>
        </div>
      </div>

      <!-- Club Image -->
      <div class="mb-6 flex justify-center">
        <img v-if="club.clubFile" :src="imgSrc" alt="Club Image" class="rounded-lg object-contain" />
      </div>

      <!-- Club Description -->
      <div class="mb-6 text-gray-700 font-title text-base">
        {{ club.description }}
      </div>

      <!-- Navigation Button -->
      <div class="flex justify-end items-center text-sm font-title mb-6">
        <!-- 수정 및 삭제 버튼 (본인 글일 경우에만 표시) -->
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
          <!-- 클럽 신청하기 버튼 -->
          <button v-else @click="handleJoin"
            class="px-4 py-2 bg-lightBlue text-white rounded-full hover:bg-darkBlue transition duration-300 text-sm font-title">
            클럽 신청하기
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
import { useClubStore } from "@/stores/club";
import { useMemberStore } from "@/stores/member";
import Swal from "sweetalert2";
import '@/assets/swal_custom.css';

const customSwal = Swal.mixin({
  customClass: {
    title: 'custom-swal-title',
    text: 'custom-swal-text',
    confirmButton: 'custom-swal-button',
  },
  buttonsStyling: false,
});

const route = useRoute();
const router = useRouter();
const store = useClubStore();
const memberStore = useMemberStore();

const club = ref({});
const comments = ref([]);
const newComment = ref("");
const imgSrc = ref('');

// 작성자와 현재 로그인한 사용자가 일치하는지 확인
const isAuthor = computed(() => {
  return club.value.leader && memberStore.memberNickname && club.value.leader === memberStore.memberNickname;
});

const clubNo = route.params.clubNo;

const loadClubDetails = async () => {
  try {
    await store.getClubDetail(clubNo);
    club.value = store.clubDetail;

    if (club.value.clubFile) {
      imgSrc.value = `http://localhost:8080/file/club${club.value.clubFile.path}/${club.value.clubFile.systemName}`;
    }

    comments.value = club.value.comments || [];
  } catch (error) {
    console.error("클럽 상세 정보 로드 실패:", error);
  }
};

const addComment = () => {
  if (!memberStore.memberId) {
    Swal.fire({
      icon: "warning",
      title: "로그인이 필요합니다.",
      text: "댓글 작성을 위해 로그인해주세요.",
      confirmButtonText: "로그인하기",
    }).then(() => {
      router.push({path: "/sign-in", query: { redirect: router.currentRoute.value.fullPath } });
    });
    return;
  }
  if (newComment.value.trim() === "") {
    customSwal.fire("댓글 내용이 비어있습니다.", "댓글을 입력해주세요.","warning",
    );
    return;
  }

  store
    .addComment(clubNo, { content: newComment.value })
    .then(() => {
      comments.value.push({
        content: newComment.value,
        writer: memberStore.memberNickname || "익명",
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
    name: 'registerclubs',
    params: {
      clubNo: clubNo, // clubNo를 수정 페이지로 전달
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
        .deleteClub(clubNo)
        .then(() => {
          customSwal.fire("삭제 완료!", "클럽이 삭제되었습니다.", "success");
          navigateBack();
        })
        .catch((error) => {
          console.error("클럽 삭제 실패:", error);
        });
    }
  });
};

// 수정된 handleJoin 함수
const handleJoin = () => {
  if (!memberStore.memberId) {
    customSwal.fire({
      icon: "warning",
      title: "로그인이 필요합니다.",
      text: "클럽 신청을 위해 로그인해주세요.",
      confirmButtonText: "로그인하기",
    }).then(() => {
      router.push({path: "/sign-in", query: { redirect: router.currentRoute.value.fullPath } });
    });
    return;
  }

  store
    .registerClub(memberStore.memberId, clubNo)
    .then(() => {
      customSwal.fire({
        icon: "success",
        title: "신청 완료",
        text: "클럽 신청이 성공적으로 완료되었습니다.",
      });
    })
    .catch((error) => {
      console.error("클럽 신청 실패:", error);
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
