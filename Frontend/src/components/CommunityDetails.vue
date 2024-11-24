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
      <div class="mb-6 flex justify-center">
        <!-- 이미지가 있을 경우 표시 -->
        <img v-if="board.boardFile" :src="imgSrc" alt="Post Image" class="rounded-lg object-contain" />
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
        </div>
      </div>

      <!-- Comments Section -->
      <div class="border-t pt-6">
        <h2 class="text-lg font-title text-greyBlue mb-4">댓글</h2>
        <ul class="space-y-4">
          <li v-for="(comment, index) in comments" :key="index">
            <div class="flex justify-between items-center">
              <p class="text-darkBlue text-sm font-title">{{ index + 1 }}. {{ comment.content }}</p>
              <div class="text-gray-500 text-xs font-title flex items-center">
                <span class="font-title text-greyBlue text-sm">{{ comment.writer }}</span>
                <span class="ml-8">{{ comment.createdAt }}</span>
                <span class="ml-8 flex items-center">
                  {{ comment.regDate }}
                  <!-- 댓글 작성자인 경우에만 삭제 버튼 표시 -->
                  <button v-if="comment.writer === memberStore.memberNickname"
                    @click="deleteComment(comment.commentNo, index)"
                    class="ml-2 w-6 h-6 bg-darkBlue text-white text-xs font-title rounded-full flex items-center justify-center hover:bg-lightBlue transition duration-300"
                    aria-label="댓글 삭제">
                    &times;
                  </button>
                </span>
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
import { ref, computed, onMounted } from 'vue';
import Header from './Header.vue';
import { useRoute, useRouter } from 'vue-router';
import { useBoardStore } from '@/stores/board';
import { useMemberStore } from '../stores/member';
import Swal from 'sweetalert2';

// Router
const route = useRoute();
const router = useRouter();

// Store
const store = useBoardStore();
const memberStore = useMemberStore();
const board = ref({});
const imgSrc = ref('');
const newComment = ref('');
const comments = ref([]);

// 게시글 번호 가져오기
const boardNo = route.params.boardNo;

// 작성자와 현재 로그인한 사용자가 일치하는지 확인
const isAuthor = computed(() => {
  return board.value.writer && memberStore.memberNickname && board.value.writer === memberStore.memberNickname; // board.writer와 현재 사용자 이름 비교
});

const detail = async () => {
  try {
    await store.getBoardDetail(boardNo);
    board.value = store.board;

    // 첨부파일이 있는 경우에만 imgSrc 설정
    if (board.value.boardFile) {
      imgSrc.value = `http://192.168.210.83:8080/file/board${board.value.boardFile.path}/${board.value.boardFile.systemName}`;
    }

    // 댓글 불러오기
    await store.getComments(boardNo);
    comments.value = board.value.comments;
  } catch (error) {
    console.error('게시글 상세 정보 불러오기 오류:', error);
  }
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
    cancelButtonText: '취소',
  }).then((result) => {
    if (result.isConfirmed) {
      store
        .deleteBoard(boardNo)
        .then(() => {
          Swal.fire('삭제 완료!', '게시글이 삭제되었습니다.', 'success').then(() => {
            router.push('/community');
          });
        })
        .catch((error) => {
          Swal.fire({
            icon: 'error',
            title: '삭제 실패',
            text: '게시글 삭제 중 문제가 발생했습니다. 다시 시도해주세요.',
          });
          console.error('게시글 삭제 중 오류 발생:', error);
        });
    }
  });
};

// 댓글 추가 핸들러
const addComment = () => {
  if (newComment.value.trim() === '') {
    Swal.fire({
      icon: 'warning',
      title: '댓글 내용이 비어있습니다.',
      text: '댓글을 입력해주세요.',
    });
    return;
  }


  // 서버로 댓글 추가 요청
  store
    .addComment(boardNo, { content: newComment.value, writer: memberStore.memberNickname, boardNo: boardNo })
    .then(() => {
      // 댓글 추가 성공 시 로컬 상태 업데이트
      comments.value.push({
        content: newComment.value,
        writer: memberStore.memberNickname,
        createdAt: new Date().toLocaleString(),
      });
      newComment.value = '';
    })
    .catch((error) => {
      console.error('댓글 추가 중 오류 발생:', error);
    });
};

const deleteComment = (commentNo, index) => {
  Swal.fire({
    title: "정말 삭제하시겠습니까?",
    text: "댓글을 삭제하시겠습니까?",
    icon: "warning",
    showCancelButton: true,
    confirmButtonColor: "#d33",
    cancelButtonColor: "#3085d6",
    confirmButtonText: "삭제",
    cancelButtonText: "취소",
  }).then((result) => {
    if (result.isConfirmed) {
      store
        .deleteComment(commentNo) // board.js의 deleteComment 호출
        .then(() => {
          comments.value.splice(index, 1); // 로컬 상태에서 댓글 제거
        })
        .catch((error) => {
          console.error("댓글 삭제 중 오류 발생:", error);
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
