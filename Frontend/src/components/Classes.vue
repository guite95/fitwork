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
        <button @click="navigateToSearch"
          class="px-5 py-2 bg-lightBlue text-white rounded-2xl font-title hover:bg-darkBlue transition duration-300 text-sm whitespace-nowrap">
          검색
        </button>
      </div>

      <!-- 전체 클래스(로그인 안돼있을 때) -->
      <div class="mb-12" v-if="!isLoggedIn">
        <h2 class="text-xl font-title text-lightBlue mb-8">
          <span class="text-lightBlue"></span> <span class="text-darkBlue">클래스 둘러보기</span>
        </h2>
        <Swiper class="my-swiper" :modules="[Navigation]" :slides-per-view="6" :space-between="20" navigation>
          <SwiperSlide v-for="classItem in allClasses" :key="classItem.classNo">
            <router-link :to="{ name: 'classesdetail', params: { classNo: classItem.classNo } }">
              <div
                class="bg-white shadow-md rounded-lg p-4 cursor-pointer hover:bg-gray-200 transition">
                <img :src="getClassImageUrl(classItem)" alt="Class Image"
                  class="w-full h-28 object-cover rounded-md mb-2" />
                <span class="text-center text-darkBlue font-title">{{ classItem.className }}</span>
              </div>
            </router-link>
          </SwiperSlide>
        </Swiper>
      </div>

      <!-- 추천 클래스(로그인 돼있을 때) -->
      <div class="mb-12" v-if="isLoggedIn">
        <h2 class="text-xl font-title text-lightBlue mb-8">
          <span class="text-lightBlue"> {{ memberDistrict }} </span> <span class="text-darkBlue">근처의</span>
        </h2>
        <Swiper class="my-swiper" :modules="[Navigation]" :slides-per-view="6" :space-between="20" navigation>
          <SwiperSlide v-for="classItem in filteredRecommendedClasses" :key="classItem.classNo">
            <router-link :to="{ name: 'classesdetail', params: { classNo: classItem.classNo } }">
              <div
                class="bg-white shadow-md rounded-lg p-4 cursor-pointer hover:bg-gray-200 transition">
                <img :src="getClassImageUrl(classItem)" alt="Class Image"
                  class="w-full h-28 object-cover rounded-md mb-2" />
                <span class="text-center text-darkBlue font-title">{{ classItem.className }}</span>
              </div>
            </router-link>
          </SwiperSlide>
        </Swiper>
      </div>


      <!-- 인기 클래스 -->
      <div class="mb-12">
        <h2 class="text-xl font-title text-darkBlue mb-4">최근 인기 많은</h2>
        <Swiper class="my-swiper" :modules="[Navigation]" :slides-per-view="6" :space-between="20" navigation>
          <SwiperSlide v-for="(classItem, index) in popularClasses" :key="index">
            <!-- 클래스 항목을 router-link로 감싸서 클릭 시 상세 페이지로 이동 -->
            <router-link :to="{ name: 'classesdetail', params: { classNo: classItem.classNo } }">
              <div
                class="bg-white shadow-md rounded-lg p-4 cursor-pointer hover:bg-gray-200 transition">
                <img :src="getClassImageUrl(classItem)" alt="Class Image"
                  class="w-full h-28 object-cover rounded-md mb-2" />
                <span class="text-center text-darkBlue font-title">{{ classItem.className }}</span>
              </div>
            </router-link>
          </SwiperSlide>
        </Swiper>
      </div>

      <!-- 새로운 클래스 만들기 -->
      <div v-if="memberRole === 'ROLE_INSTRUCTOR'" class="text-center">
        <p class="text-xl mb-4 text-darkBlue font-title">
          <span class="text-lightBlue font-title">강사</span>이신가요?
        </p>
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
import { useClassStore } from "../stores/class";
import { useRouter } from "vue-router"; // Router 사용
import Swal from "sweetalert2";
import "swiper/css";
import "swiper/css/navigation";

// Member Store 및 Class Store
const memberStore = useMemberStore();
const classStore = useClassStore();
const router = useRouter(); // Router instance

const memberRole = computed(() => memberStore.memberRole);
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


// 추천 클래스 데이터
const recommendedClasses = ref([]);
const allClasses = ref([]);

// 컴포넌트 로드 시 클래스 데이터 가져오기
onMounted(async () => {
  await classStore.getClassList(); // 전체 클래스 데이터 가져오기
  recommendedClasses.value = classStore.classList; // 전체 클럽을 추천 클럽 리스트에 반영
  allClasses.value = classStore.classList;
});

// 추천 클럽 필터링 (사용자 지역 기준)
const filteredRecommendedClasses = computed(() => {
  if (!recommendedClasses.value) return [];
  return recommendedClasses.value.filter((c) => {
    // 주소가 제대로 존재하고, 분리 가능한지 확인
    const addressParts = c.location?.split(" ");
    return addressParts && addressParts[1] === memberDistrict.value;
  });
});

// 인기 클래스
const popularClasses = computed(() => {
  if (!Array.isArray(classStore.classList)) return [];
  return [...classStore.classList]
    .filter((c) => c.headCount >= 0) // headCount가 0 이상인 클래스만 필터링
    .sort((a, b) => b.headCount - a.headCount); // headCount 기준으로 내림차순 정렬
});

// 이미지 URL 생성 메서드
const getClassImageUrl = (classItem) => {
  if (classItem.classesFile) {
    return `http://192.168.210.83:8080/file/class${classItem.classesFile.path}/${classItem.classesFile.systemName}`;
  }
  return "/images/dumbbell.jpg"; // 기본 이미지
};

// 검색 버튼 클릭 시 search 페이지로 이동
function navigateToSearch() {
  if (!searchQuery.value.trim()) {
    Swal.fire("알림", "검색어를 입력해주세요.", "warning");
    return;
  }
  router.push({
    name: "search",
    query: { type: "클래스", query: searchQuery.value },
  });
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
