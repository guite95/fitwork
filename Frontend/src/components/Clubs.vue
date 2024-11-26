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
        <button @click="navigateToSearch"
          class="px-5 py-2 bg-lightBlue text-white rounded-2xl font-title hover:bg-darkBlue transition duration-300 text-sm whitespace-nowrap">
          검색
        </button>
      </div>

      <!-- 전체 클럽(로그인 안돼있을 때) -->
      <div class="mb-12" v-if="!isLoggedIn">
        <h2 class="text-xl font-title text-lightBlue mb-8">
          <span class="text-lightBlue"></span> <span class="text-darkBlue">클럽 둘러보기</span>
        </h2>
        <Swiper class="my-swiper" :modules="[Navigation]" :slides-per-view="6" :space-between="20" navigation>
          <SwiperSlide v-for="club in allClubs" :key="club.clubNo">
            <router-link :to="{ name: 'clubsdetail', params: { clubNo: club.clubNo } }" >
              <div
                class="bg-white shadow-md rounded-lg p-4 cursor-pointer hover:bg-gray-200 transition">
                <img :src="getClubImageUrl(club)" alt="Club Image" class="w-full h-28 object-cover rounded-md mb-2" />
                <span class="text-center text-darkBlue font-title">{{ club.clubName }}</span>
              </div>
            </router-link>
          </SwiperSlide>
        </Swiper>
      </div>

      <!-- 추천 클럽(로그인 되어있을 때) -->
      <div class="mb-12" v-if="isLoggedIn">
        <h2 class="text-xl font-title text-lightBlue mb-8">
          <span class="text-lightBlue"> {{ memberDistrict }} </span> <span class="text-darkBlue">근처의</span>
        </h2>
        <Swiper class="my-swiper" :modules="[Navigation]" :slides-per-view="6" :space-between="20" navigation>
          <SwiperSlide v-for="club in filteredRecommendedClubs" :key="club.clubNo">
            <router-link :to="{ name: 'clubsdetail', params: { clubNo: club.clubNo } }">
              <div
                class="bg-white shadow-md rounded-lg p-4 cursor-pointer hover:bg-gray-200 transition">
                <img :src="getClubImageUrl(club)" alt="Club Image" class="w-full h-28 object-cover rounded-md mb-2" />
                <span class="text-center text-darkBlue font-title">{{ club.clubName }}</span>
              </div>
            </router-link>
          </SwiperSlide>
        </Swiper>
      </div>

      <!-- 인기 클럽 -->
      <div class="mb-12">
        <h2 class="text-xl font-title text-darkBlue mb-4">최근 인기 많은</h2>
        <Swiper class="my-swiper" :modules="[Navigation]" :slides-per-view="6" :space-between="20" navigation>
          <SwiperSlide v-for="club in popularClubs" :key="club.clubNo">
            <router-link :to="{ name: 'clubsdetail', params: { clubNo: club.clubNo } }">
              <div
                class="bg-white shadow-md rounded-lg p-4 cursor-pointer hover:bg-gray-200 transition">
                <img :src="getClubImageUrl(club)" alt="Club Image" class="w-full h-28 object-cover rounded-md mb-2" />
                <span class="text-center text-darkBlue font-title">{{ club.clubName }}</span>
              </div>
            </router-link>
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
  </div>
  <Footer />
</template>

<script setup>
import { computed, ref, onMounted } from "vue";
import Header from "./Header.vue";
import Footer from "./Footer.vue";
import { Swiper, SwiperSlide } from "swiper/vue";
import { Navigation } from "swiper";
import { useClubStore } from "../stores/club";
import { useMemberStore } from "../stores/member";
import { useRouter } from "vue-router";
import "swiper/css";
import "swiper/css/navigation";

// Club Store 및 Member Store
const clubStore = useClubStore();
const memberStore = useMemberStore();
const router = useRouter();

// 사용자 정보
const memberName = ref(memberStore.memberName || "사용자");
const memberAddress = computed(() => memberStore.memberAddress);
const memberDistrict = computed(() => {
  if (memberAddress.value) {
    const parts = memberAddress.value.split(" ");
    return parts[1];
  }
  return "지역";
});
const isLoggedIn = computed(() => memberStore.isLoggedIn);

// 검색 쿼리 상태
const searchQuery = ref("");

// 추천 클럽 데이터
const recommendedClubs = ref([]);
const allClubs = ref([]);

// 컴포넌트 로드 시 클럽 데이터 가져오기
onMounted(async () => {
  await clubStore.getClubList(); // 전체 클럽 데이터 가져오기
  recommendedClubs.value = clubStore.clubList; // 전체 클럽을 추천 클럽 리스트에 반영
  allClubs.value = clubStore.clubList;
});

// 추천 클럽 필터링 (사용자 지역 기준)
const filteredRecommendedClubs = computed(() => {
  console.log(recommendedClubs)
  if (!recommendedClubs.value) return [];
  return recommendedClubs.value.filter((c) => {
    // 주소가 제대로 존재하고, 분리 가능한지 확인
    const addressParts = c.location?.split(" ");
    return addressParts && addressParts[1] === memberDistrict.value;
  });
});

// 인기 클럽 필터링
const popularClubs = computed(() => {
  if (!Array.isArray(clubStore.clubList)) return [];
  return [...clubStore.clubList]
    .filter((c) => c.headCount >= 0)
    .sort((a, b) => b.headCount - a.headCount); // headCount 기준으로 내림차순 정렬
});

// 이미지 URL 생성 메서드
const getClubImageUrl = (club) => {
  if (club.clubFile) {
    return `http://192.168.210.83:8080/file/club${club.clubFile.path}/${club.clubFile.systemName}`;
  }
  return "/images/dumbbell.jpg"; // 기본 이미지
};

// 검색 버튼 클릭 시 검색 페이지로 이동
const navigateToSearch = () => {
  router.push({ name: "search", query: { type: "클럽", query: searchQuery.value } });
};
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
