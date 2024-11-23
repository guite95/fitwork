<template>
  <div class="bg-gray-50 min-h-screen">
    <!-- Header -->
    <Header />

    <!-- 콘텐츠 -->
    <section class="px-[20%] py-12 bg-gray-50">
      <h1 class="text-3xl font-title text-darkBlue mb-6">운동 클럽 🏃‍♀️</h1>

      <!-- 검색 섹션 -->
      <div class="flex justify-end items-center mb-8 space-x-4 w-1/4 ml-auto">
        <input v-model="searchQuery" type="text" placeholder="클럽 검색하기"
          class="flex-grow px-4 py-2 border border-gray-300 rounded-2xl focus:outline-none focus:ring-2 focus:ring-lightBlue font-title text-sm" />
        <button @click="searchClubs"
          class="px-5 py-2 bg-lightBlue text-white rounded-2xl font-title hover:bg-darkBlue transition duration-300 text-sm">
          검색
        </button>
      </div>

      <!-- 추천 클럽 -->
      <div class="mb-12">
        <h2 class="text-xl font-title text-lightBlue mb-4">
          20대 여성 <span class="text-darkBlue font-title">이 관심있는</span>
        </h2>
        <Swiper class="my-swiper" :modules="[Navigation]" :slides-per-view="3" :space-between="20" navigation>
          <SwiperSlide v-for="club in RecommendedClubs" :key="club.clubNo">
            <div class="bg-gray-100 p-4 rounded-md shadow h-24 flex items-center justify-center">
              {{ club.clubName }}
            </div>
          </SwiperSlide>
        </Swiper>
      </div>

      <!-- 인기 클럽 -->
      <div class="mb-12">
        <h2 class="text-xl font-title text-darkBlue mb-4">최근 인기 많은</h2>
        <Swiper class="my-swiper" :modules="[Navigation]" :slides-per-view="3" :space-between="20" navigation>
          <SwiperSlide v-for="club in popularClubs" :key="club.clubNo">
            <div class="bg-gray-100 p-4 rounded-md shadow h-24 flex items-center justify-center">
              {{ club.clubName }}
            </div>
          </SwiperSlide>
        </Swiper>
      </div>

      <!-- 새로운 클럽 만들기 -->
      <div class="text-center">
        <p class="text-xl mb-4 text-darkBlue font-title"> 마음에 드는 클럽이 없다면... </p>
        <router-link to="/register-clubs">
          <button class="px-6 py-3 bg-lightBlue text-white font-title rounded-md hover:bg-darkBlue">
            👉 새로운 클럽 만들기
          </button>
        </router-link>
      </div>
    </section>
    <Footer />
  </div>
</template>

<script setup>
import { computed, ref, onMounted } from "vue";
import Header from "./Header.vue";
import Footer from "./Footer.vue";
import { Swiper, SwiperSlide } from "swiper/vue";
import { Navigation } from "swiper";
import { useMemberStore } from "../stores/member";
import { useClubStore } from "../stores/club"; // Pinia의 club.js 가져오기
import "swiper/css";
import "swiper/css/navigation";

// Member Store 및 Club Store
const memberStore = useMemberStore();
const clubStore = useClubStore(); // club.js 사용

const memberRole = computed(() => memberStore.memberRole);

// 검색 쿼리 상태
const searchQuery = ref("");

// 추천 클럽 데이터
const recommendedClubs = ref([]);

// 컴포넌트 로드 시 클럽 데이터 가져오기
onMounted(async () => {
  await loaded();
  console.log("최초 로드시점 후 클럽 게시물", clubStore.clubList)
  // await clubStore.fetchClubList(); // 전체 클럽 데이터 가져오기
  // recommendedClubs.value = clubStore.clubList; // 전체 클럽을 추천 클럽 리스트에 반영
});

const loaded = () => {
  new Promise(async () => {
    await clubStore.getClubList(); // 전체 클럽 데이터 가져오기
    recommendedClubs.value = clubStore.clubList; // 전체 클럽을 추천 클럽 리스트에 반영
    console.log("추천 게시물 :", recommendedClubs.value)
  })
}

const filteredRecommendedClubs = computed(() => {
  console.log("필터 전 추천게시물", recommendedClubs.value)
  // console.log(Array.isArray(recommendedClubs.value))
  if (!recommendedClubs.value) return [];
  // console.log("추천 게시물이 배열일때")
  return recommendedClubs.value.filter((c) => c && c.name && c.name.includes(searchQuery.value));
  // return recommendedClubs.value.filter((c) => c.name.includes(searchQuery.value));
});

const popularClubs = computed(() => {
  if (!Array.isArray(clubStore.clubList)) return [];
  return clubStore.clubList.filter((c) => c.isPopular);
});

// 검색 버튼 클릭 이벤트
async function searchClubs() {
  if (!searchQuery.value.trim()) {
    Swal.fire("알림", "검색어를 입력해주세요.", "warning");
    return;
  }
  // 추천 클럽 중 검색어 포함된 것만 필터링
  filteredRecommendedClubs.value = clubStore.clubList.filter((c) =>
    c.name.includes(searchQuery.value)
  );
}
</script>

<style scoped>
.my-swiper {
  width: 100%;
  height: auto;
}

/* Swiper 네비게이션 버튼 커스터마이징 */
:deep(.swiper-button-next),
:deep(.swiper-button-prev) {
  color: #64748b !important;
  /* greyBlue */
  font-size: 1.2rem !important;
  font-weight: bold !important;
  opacity: 0.8;
  transition: opacity 0.3s, transform 0.3s;
}

:deep(.swiper-button-next:hover),
:deep(.swiper-button-prev:hover) {
  opacity: 1;
  transform: scale(1.1);
}
</style>
