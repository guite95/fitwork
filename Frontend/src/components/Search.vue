<template>
    <div class="bg-gray-50 min-h-screen">
        <!-- Header -->
        <Header />

        <!-- 콘텐츠 -->
        <section class="px-[20%] py-12 bg-gray-50">
            <h1 class="text-3xl font-title text-darkBlue mb-6">운동 {{ searchType }} 🏃‍♀️</h1>

            <!-- 검색 섹션 -->
            <div class="flex justify-end items-center mb-8 space-x-4 w-1/4 ml-auto">
                <input v-model="inputQuery" type="text" placeholder="클럽 검색하기"
                    class="flex-grow px-4 py-2 border border-gray-300 rounded-2xl focus:outline-none focus:ring-2 focus:ring-lightBlue font-title text-sm" />
                <button @click="handleSearch"
                    class="px-5 py-2 bg-lightBlue text-white rounded-2xl font-title hover:bg-darkBlue transition duration-300 text-sm whitespace-nowrap">
                    검색
                </button>
            </div>

            <!-- Main Content -->
            <div class="max-w-6xl mx-auto py-12">
                <h1 class="text-3xl font-title text-greyBlue mb-6">
                    <span class="text-lightBlue font-title">{{ searchQuery }}</span>에 대한
                    <span class="text-lightBlue font-title">{{ searchType }}</span> 검색 결과
                </h1>

                <!-- 검색 결과 -->
                <div class="grid grid-cols-auto-fit gap-6">
                    <router-link v-for="(item, index) in filteredPaginatedResults" :key="index"
                        :to="getItemDetailLink(item)"
                        class="bg-gray-100 rounded-md shadow p-4 flex flex-col items-center cursor-pointer hover:bg-gray-200 transition">
                        <img :src="getItemImageUrl(item)" alt="Item Image"
                            class="w-full h-28 object-cover rounded-md mb-4" />
                        <span class="text-center text-darkBlue font-title">
                            {{ searchType === "클럽" ? item.clubName : item.className }}
                        </span>
                    </router-link>
                </div>

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
        <Footer />
    </div>
</template>

<script setup>
import { ref, computed, onMounted, watch } from "vue";
import { useRoute, useRouter } from "vue-router";
import Header from "./Header.vue";
import Footer from "./Footer.vue";
import { useClubStore } from "@/stores/club";
import { useClassStore } from "@/stores/class";

// Route and stores
const route = useRoute();
const router = useRouter();
const clubStore = useClubStore();
const classStore = useClassStore();

// 검색 타입 (클럽 또는 클래스) 및 쿼리
const searchType = ref(route.query.type || "클럽"); // type: 클럽 또는 클래스
const searchQuery = ref(route.query.query || ""); // 실제 검색에 사용되는 쿼리
const inputQuery = ref(searchQuery.value); // 사용자가 입력 중인 쿼리

// 검색 결과 및 페이징 관련 상태
const searchResults = ref([]);
const itemsPerPage = ref(8); // 페이지당 항목 수
const currentPage = ref(1); // 현재 페이지

// 이미지 URL 생성 메서드
const getItemImageUrl = (item) => {
    if (searchType.value === "클럽" && item.clubFile) {
        return `http://192.168.210.83:8080/file/club${item.clubFile.path}/${item.clubFile.systemName}`;
    } else if (searchType.value === "클래스" && item.classesFile) {
        return `http://192.168.210.83:8080/file/class${item.classesFile.path}/${item.classesFile.systemName}`;
    }
    return "/images/dumbbell.jpg"; // 기본 이미지
};

// 게시물 상세 링크 생성 메서드
const getItemDetailLink = (item) => {
    if (searchType.value === "클럽") {
        return { name: "clubsdetail", params: { clubNo: item.clubNo } };
    } else if (searchType.value === "클래스") {
        return { name: "classesdetail", params: { classNo: item.classNo } };
    }
    return "/";
};

// 현재 페이지에 해당하는 결과
const filteredPaginatedResults = computed(() => {
    const start = (currentPage.value - 1) * itemsPerPage.value;
    return searchResults.value.slice(start, start + itemsPerPage.value);
});

// 전체 페이지 수 계산
const totalPages = computed(() =>
    Math.ceil(searchResults.value.length / itemsPerPage.value)
);

// 페이지 이동 함수
const goToPage = (page) => {
    if (page >= 1 && page <= totalPages.value) {
        currentPage.value = page;
    }
};

// 검색 버튼 클릭 시 실행
const handleSearch = () => {
  if (!inputQuery.value.trim()) {
    alert("검색어를 입력해주세요.");
    return;
  }
  router.push({
    name: "search",
    query: { type: searchType.value, query: inputQuery.value },
  });
};

// 검색 데이터 업데이트 함수
const updateSearchResults = async () => {
  currentPage.value = 1; // 페이지를 초기화
  if (searchType.value === "클럽") {
    await clubStore.getClubList();
    searchResults.value = clubStore.clubList.filter((club) =>
      club.clubName.includes(searchQuery.value) ||
      club.tag.includes(searchQuery.value) ||
      club.location.includes(searchQuery.value) ||
      club.description.includes(searchQuery.value) ||
      club.leader.includes(searchQuery.value)
    );
  } else if (searchType.value === "클래스") {
    await classStore.getClassList();
    searchResults.value = classStore.classList.filter((classItem) =>
      classItem.className.includes(searchQuery.value) ||
      classItem.tag.includes(searchQuery.value) ||
      classItem.location.includes(searchQuery.value) ||
      classItem.description.includes(searchQuery.value) |
      classItem.leader.includes(searchQuery.value)
    );
  }
};

// 라우터 쿼리 변경 감지
watch(
  () => route.query,
  (newQuery) => {
    searchType.value = newQuery.type || "클럽";
    searchQuery.value = newQuery.query || "";
    updateSearchResults(); // 검색 결과 업데이트
  },
  { immediate: true } // 초기 실행
);

// 컴포넌트 로드 시 초기 검색 데이터 로드
onMounted(() => {
  updateSearchResults();
});
</script>

<style scoped>
.grid {
    display: grid;
    grid-template-columns: repeat(auto-fit, minmax(150px, 1fr));
    /* 최소 150px, 최대 1fr로 카드 크기 설정 */
    gap: 1.5rem;
    /* 항목 간의 간격 */
    justify-items: center;
    /* 항목을 가운데 정렬 */
    align-items: start;
    /* 항목의 상단 정렬 */
}

.grid-cols-auto-fit {
    grid-template-columns: repeat(auto-fit, minmax(150px, 1fr));
    /* 반응형으로 크기를 유지하며 열 개수를 조정 */
}

.bg-gray-100 {
    max-width: 200px;
    /* 카드의 최대 너비 고정 */
    min-width: 150px;
    /* 카드의 최소 너비 고정 */
    transition: transform 0.3s ease; /* 크기 변화 애니메이션 */
}

.bg-gray-100:hover {
    transform: scale(1.05); /* 카드 hover 시 크기 확대 */
}
</style>
