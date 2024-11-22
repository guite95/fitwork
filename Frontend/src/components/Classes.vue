<template>
  <div class="bg-gray-50 min-h-screen">
    <!-- Header -->
    <Header />

    <!-- 콘텐츠 -->
    <section class="px-[20%] py-12 bg-gray-50">
      <h1 class="text-3xl font-title text-darkBlue mb-6">운동 클래스 🏋️‍♀️</h1>

      <!-- 검색 섹션 -->
      <div class="flex justify-end items-center mb-8 space-x-4 w-1/4 ml-auto">
        <input v-model="searchQuery" type="text" placeholder="클래스 검색하기"
          class="flex-grow px-4 py-2 border border-gray-300 rounded-2xl focus:outline-none focus:ring-2 focus:ring-lightBlue font-title text-sm" />
        <button @click="searchClasses"
          class="px-5 py-2 bg-lightBlue text-white rounded-2xl font-title hover:bg-darkBlue transition duration-300 text-sm">
          검색
        </button>
      </div>

      <!-- 추천 클래스 -->
      <div class="mb-12">
        <h2 class="text-xl font-title text-lightBlue mb-4">
          20대 여성 <span class="text-darkBlue font-title">이 관심있는</span>
        </h2>
        <Swiper class="my-swiper" :modules="[Navigation]" :slides-per-view="3" :space-between="20" navigation>
          <SwiperSlide v-for="(classItem, index) in filteredNearbyClasses" :key="index">
            <div class="bg-gray-100 p-4 rounded-md shadow h-24 flex items-center justify-center">
              {{ classItem.name }}
            </div>
          </SwiperSlide>
        </Swiper>
      </div>

      <!-- 인기 클래스 -->
      <div class="mb-12">
        <h2 class="text-xl font-title text-darkBlue mb-4">최근 인기 많은</h2>
        <Swiper class="my-swiper" :modules="[Navigation]" :slides-per-view="3" :space-between="20" navigation>
          <SwiperSlide v-for="(classItem, index) in popularClasses" :key="index">
            <div class="bg-gray-100 p-4 rounded-md shadow h-24 flex items-center justify-center">
              {{ classItem.name }}
            </div>
          </SwiperSlide>
        </Swiper>
      </div>

      <!-- 새로운 클래스 만들기 -->
      <div v-if="memberRole === 'ROLE_INSTRUCTOR'" class="text-center">
        <p class="text-xl mb-4 text-darkBlue font-title"> <span class="text-lightBlue font-title">강사</span>이신가요?</p>
        <router-link to="/register-classes">
          <button class="px-6 py-3 bg-lightBlue text-white font-title rounded-md hover:bg-darkBlue">
            👉 새로운 클래스 만들기
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
import { useClassStore } from "../stores/class"; // Pinia의 class.js 가져오기
import "swiper/css";
import "swiper/css/navigation";

// Member Store 및 Class Store
const memberStore = useMemberStore();
const classStore = useClassStore(); // class.js 사용

const memberRole = computed(() => memberStore.memberRole);

// 검색 쿼리 상태
const searchQuery = ref("");

// 추천 클래스와 인기 클래스 데이터
const nearbyClasses = ref([]);
const popularClasses = computed(() => classStore.classList.filter((c) => c.isPopular)); // 인기 클래스 필터

// 검색 결과 필터링
const filteredNearbyClasses = computed(() =>
  nearbyClasses.value.filter((c) => c.name.includes(searchQuery.value))
);

// 컴포넌트 로드 시 클래스 데이터 가져오기
onMounted(async () => {
  await classStore.fetchClassList(); // 전체 클래스 데이터 가져오기
  nearbyClasses.value = classStore.classList; // 전체 클래스를 추천 클래스 리스트에 반영
});

// 검색 버튼 클릭 이벤트
async function searchClasses() {
  if (!searchQuery.value.trim()) {
    Swal.fire("알림", "검색어를 입력해주세요.", "warning");
    return;
  }
  // 추천 클래스 중 검색어 포함된 것만 필터링
  filteredNearbyClasses.value = classStore.classList.filter((c) =>
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
