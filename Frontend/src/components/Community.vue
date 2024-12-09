<template>
  <div class="bg-gray-50 min-h-screen">
    <!-- Header -->
    <Header />

    <!-- Main Content -->
    <div class="flex">
      <!-- Sidebar -->
      <aside class="w-1/6 bg-gray-100 min-h-screen p-6 shadow-md">
        <h3 class="underline text-lg font-title text-darkBlue mb-4">커뮤니티</h3>
        <ul class="space-y-4 text-greyBlue">
          <li>
            <button @click="changeTab('club')"
              :class="['font-title', selectedTab.value === 'club' ? 'text-lightBlue font-bold' : 'text-greyBlue hover:text-lightBlue']">
              클럽 후기
            </button>
          </li>
          <li>
            <button @click="changeTab('class')"
              :class="['font-title', selectedTab.value === 'class' ? 'text-lightBlue font-bold' : 'text-greyBlue hover:text-lightBlue']">
              클래스 후기
            </button>
          </li>
          <li>
            <button @click="changeTab('chat')"
              :class="['font-title', selectedTab.value === 'chat' ? 'text-lightBlue font-bold' : 'text-greyBlue hover:text-lightBlue']">
              잡담
            </button>
          </li>
        </ul>
      </aside>

      <!-- Main Section -->
      <section class="flex-1 px-20 py-10">
        <!-- Title -->
        <div class="flex items-center justify-between mb-6">
          <h1 class="text-2xl font-title text-darkBlue">
            {{ currentTabTitle }}
          </h1>
        </div>

        <!-- 로그인 확인 -->
        <div v-if="!isLoggedIn" class="text-center text-darkBlue font-title mt-20">
          <p>게시글을 보려면 로그인이 필요합니다.</p>
          <router-link to="{ path: '/sign-in', query: { redirect: $route.fullPath } }">
            <button
              class="mt-4 px-6 py-2 bg-lightBlue text-white rounded-full hover:bg-darkBlue transition duration-300">
              로그인
            </button>
          </router-link>
        </div>

        <!-- 게시글 목록 -->
        <div v-else>
          <!-- Search Bar and Write Button -->
          <div class="flex justify-between items-center space-x-4 mb-4">
            <!-- 글쓰기 버튼 -->
            <router-link to="/new-post">
              <button
                class="px-4 py-2 bg-lightBlue text-white font-title rounded-full hover:bg-darkBlue transition duration-300 text-sm">
                글쓰기
              </button>
            </router-link>

            <!-- 검색 바 및 검색 버튼 -->
            <div class="flex space-x-2 items-center w-1/2 justify-end">
              <input type="text" placeholder="검색" v-model="searchQuery"
                class="w-50 px-4 py-2 border border-gray-300 rounded-2xl focus:outline-none focus:ring-2 focus:ring-lightBlue text-gray-700 font-title text-sm" />
              <button @click="handleSearch"
                class="w-3- px-3 py-2 bg-lightBlue text-white rounded-full hover:bg-darkBlue transition duration-300 font-title text-sm">
                검색
              </button>
            </div>
          </div>

          <!-- Table -->
          <table class="w-full border-t border-gray-300 table-fixed">
            <thead>
              <tr class="border-b border-gray-300">
                <th class="py-3 text-greyBlue font-title text-left table-title">제목</th>
                <th class="py-3 text-greyBlue font-title text-center table-views">조회수</th>
                <th class="py-3 text-greyBlue font-title text-center table-date">작성시간</th>
              </tr>
            </thead>
            <tbody>
              <tr v-for="review in filteredPaginatedReviews" :key="review.boardNo"
                class="border-b border-gray-200 hover:bg-gray-50">
                <td class="py-3 text-darkBlue font-title text-left">
                  <router-link :to="`/community-details/${review.boardNo}`" class="hover:underline">
                    {{ review.title }}
                  </router-link>
                  <p class="text-greyBlue text-sm">{{ review.writer }}</p>
                </td>
                <td class="py-3 text-greyBlue font-title text-center text-sm">
                  {{ review.viewCnt }}
                </td>
                <td class="py-3 text-darkBlue font-title text-center text-sm">
                  {{ review.regDate }}
                </td>
              </tr>
            </tbody>
          </table>

          <!-- Pagination -->
          <div class="flex justify-center mt-6">
            <nav class="flex space-x-2">
              <button @click="goToPage(currentPage.value - 1)"
                class="px-3 py-1 bg-white rounded-md text-gray-500 hover:bg-lightBlue hover:text-white font-title text-sm"
                :disabled="currentPage.value === 1">
                &laquo;
              </button>
              <button v-for="page in totalPages" :key="page" @click="goToPage(page)"
                class="px-3 py-1 rounded-md font-title text-sm"
                :class="currentPage.value === page ? 'bg-greyBlue text-white' : 'bg-lightBlue/30 text-darkBlue hover:bg-lightBlue hover:text-white'">
                {{ page }}
              </button>
              <button @click="goToPage(currentPage.value + 1)"
                class="px-3 py-1 bg-white rounded-md text-gray-500 hover:bg-lightBlue hover:text-white font-title text-sm"
                :disabled="currentPage.value === totalPages">
                &raquo;
              </button>
            </nav>
          </div>
        </div>
      </section>
    </div>
    <Footer />
  </div>
</template>

<script setup>
import { ref, computed, watch, onMounted } from "vue";
import { useRouter, useRoute } from "vue-router";
import Header from "./Header.vue";
import Footer from "./Footer.vue";
import { useBoardStore } from "../stores/board";
import { useMemberStore } from "../stores/member";

const router = useRouter();
const route = useRoute();
const store = useBoardStore();
const memberStore = useMemberStore();

const isLoggedIn = computed(() => memberStore.isLoggedIn);

// 게시글 목록 로드
onMounted(async () => {
  if (isLoggedIn.value) {
    await store.getBoardList();
  }
});

const allReviews = computed(() => store.boardList);
const selectedTab = ref(route.query.tab || "club");
const searchQuery = ref("");
const currentPage = ref(1);
const itemsPerPage = ref(5);

watch(
  () => route.query.tab,
  (newTab) => {
    selectedTab.value = newTab || "club";
    currentPage.value = 1; // 탭 변경 시 페이지 초기화
  },
  { immediate: true }
);

// 검색된 리뷰 목록
const filteredReviews = computed(() => {
  const filteredByTab = allReviews.value.filter(
    (review) => review.category === selectedTab.value
  );

  if (searchQuery.value.trim() === "") {
    return filteredByTab;
  }

  // 검색 조건에 따라 필터링
  return filteredByTab.filter((review) =>
    review.title.includes(searchQuery.value) ||
    review.writer.includes(searchQuery.value) ||
    review.content.includes(searchQuery.value)
  );
});

// 페이지네이션된 리뷰 목록
const filteredPaginatedReviews = computed(() => {
  const start = (currentPage.value - 1) * itemsPerPage.value;
  return filteredReviews.value.slice(start, start + itemsPerPage.value);
});

const totalPages = computed(() =>
  Math.ceil(filteredReviews.value.length / itemsPerPage.value)
);

const tabTitles = { club: "클럽 후기 🏃", class: "클래스 후기 🏋️‍♀️", chat: "잡담 💬" };
const currentTabTitle = computed(() => tabTitles[selectedTab.value]);

function changeTab(tab) {
  selectedTab.value = tab;
  router.push({ query: { tab } });
  currentPage.value = 1;
}

function goToPage(page) {
  if (page >= 1 && page <= totalPages.value) {
    currentPage.value = page;
  }
}

// 검색 버튼 핸들러
function handleSearch() {
  currentPage.value = 1; // 검색 시 페이지를 1로 초기화
}

</script>


<style scoped>
table {
  table-layout: fixed;
  /* 고정된 열 너비 */
  width: 100%;
}

.table-title {
  width: 66.66%;
  /* 제목 */
}

.table-views,
.table-date {
  width: 16.66%;
  /* 조회수, 작성시간 */
}

table th {
  text-align: left;
  /* 헤더 중앙 정렬 */
  font-weight: bold;
}

table td {
  text-align: left;
  /* 게시글 내용 왼쪽 정렬 */
  vertical-align: middle;
}
</style>
