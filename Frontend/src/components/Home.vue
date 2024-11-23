<template>
  <div class="bg-gray-50 min-h-screen">
    <!-- Header -->
    <Header />

    <!-- 메인 배너 -->
    <div class="relative">
      <Swiper class="banner-swiper" :modules="[Autoplay, Pagination]" :slides-per-view="1" :space-between="0" :autoplay="{
        delay: 3000,
        disableOnInteraction: false
      }" pagination loop>
        <SwiperSlide v-for="(banner, index) in banners" :key="index">
          <div class="absolute inset-0 bg-cover bg-center blur-md" :style="{ backgroundImage: `url(${banner})` }"></div>
          <img :src="banner" alt="Sports Club Banner" class="relative w-full h-80 object-contain object-center z-10" />
        </SwiperSlide>
      </Swiper>
    </div>

    <!-- 클럽 및 클래스 섹션 -->
    <section class="px-[20%] py-12 bg-gray-50">
      <!-- 로그인 여부에 따라 조건부 렌더링 -->
      <template v-if="isLoggedIn">
        <h2 class="text-2xl font-title text-lightBlue mb-8">
          {{ memberName }} <span class="text-darkBlue">님을 위한 </span>
          <span class="text-lightBlue"> {{ memberDistrict }} </span> <span class="text-darkBlue">근처의</span>
        </h2>
      </template>

      <!-- 운동 클럽 캐러셀 -->
      <div class="mb-16">
        <h3 class="text-xl font-title text-darkBlue mb-6">운동 클럽 🏃</h3>
        <Swiper class="my-swiper" :modules="[Navigation]" :slides-per-view="6" :space-between="20" navigation>
          <SwiperSlide v-for="(club, index) in clubs" :key="index">
            <div class="bg-white shadow-md rounded-lg p-4">
              <!-- 이미지 추가 -->
              <img src="/images/dumbbell.jpg" alt="Club Image" class="w-full h-40 object-cover rounded-md mb-4" />
              <p class="text-gray-800 font-medium text-sm">{{ club.clubName }}</p>
            </div>
          </SwiperSlide>
        </Swiper>
        <button @click="navigateToClubs"
          class="mt-6 px-6 py-2 bg-darkBlue text-white rounded-md hover:bg-lightBlue font-title">
          더보기
        </button>
      </div>

      <!-- 운동 클래스 캐러셀 -->
      <div class="mb-16">
        <h3 class="text-xl font-title text-darkBlue mb-6">운동 클래스 🏋️‍♀️</h3>
        <Swiper class="my-swiper" :modules="[Navigation]" :slides-per-view="6" :space-between="20" navigation>
          <SwiperSlide v-for="(exerciseClass, index) in classes" :key="index">
            <div class="bg-white shadow-md rounded-lg p-4">
              <!-- 이미지 추가 -->
              <img src="/images/dumbbell.jpg" alt="Class Image" class="w-full h-40 object-cover rounded-md mb-4" />
              <p class="text-gray-800 font-medium text-sm">{{ exerciseClass }}</p>
            </div>
          </SwiperSlide>
        </Swiper>
        <button @click="navigateToClasses"
          class="mt-6 px-6 py-2 bg-darkBlue text-white rounded-md hover:bg-lightBlue font-title">
          더보기
        </button>
      </div>

    </section>

    <!-- 실시간 인기글 -->
    <section class="px-[20%] py-12 bg-gray-50">
      <h3 class="text-xl font-title text-darkBlue mb-6">실시간 인기글 💬</h3>
      <div class="grid grid-cols-2 gap-6">
        <div v-for="(post, index) in popularPostsTransformed" :key="index" class="p-4 bg-gray-100 rounded-lg shadow-sm">
          <p class="text-darkBlue font-title text-sm">[{{ post.categoryText }}] {{ post.title }}</p>
        </div>
      </div>
      <button @click="navigateToCommunity"
        class="mt-6 px-6 py-2 bg-darkBlue text-white rounded-md hover:bg-lightBlue font-title">
        더보기
      </button>
    </section>

    <!-- Footer 추가 -->
    <Footer />
  </div>
</template>



<script setup>
import { computed, ref, onMounted, watch } from "vue";
import Header from "./Header.vue";
import Footer from "./Footer.vue"; // Footer 컴포넌트 추가
import { useRouter, useRoute } from "vue-router";
import { Swiper, SwiperSlide } from "swiper/vue";
import { Navigation, Pagination, Autoplay } from "swiper";
import { useMemberStore } from "../stores/member";
import { useBoardStore } from "../stores/board";
import { useClubStore } from "../stores/club";
import { useClassStore } from "../stores/class"

const memberStore = useMemberStore();
const boardStore = useBoardStore();
const clubStore = useClubStore();
const classStore = useClassStore();
const memberName = computed(() => memberStore.memberName);
const memberAddress = computed(() => memberStore.memberAddress);
const isLoggedIn = computed(() => memberStore.isLoggedIn);

const memberDistrict = computed(() => {
  if (memberAddress.value) {
    const parts = memberAddress.value.split(" ");
    return parts[1];
  }
  return "";
});

const router = useRouter();
const route = useRoute();

const banners = ref([
  "/images/home1.png",
  "/images/home2.png",
  "/images/home3.png",
  "/images/home4.png",
  "/images/home5.png",
]);

const clubs = ref([]);

const classes = ref([]);

const popularPosts = ref([]);

// Methods for navigation
function navigateToClubs() {
  router.push("/clubs");
}

function navigateToClasses() {
  router.push("/classes");
}

function navigateToCommunity() {
  router.push("/community");
}

// Fetch board list and popular posts based on views
const fetchPopularPosts = async () => {
  try {
    await boardStore.getBoardList(); // 게시글 리스트 가져오기 (await 사용으로 데이터 로드 완료 보장)
    // boardList가 유효한 상태인지 확인 후 처리
    if (Array.isArray(boardStore.boardList) && boardStore.boardList.length > 0) {
      // 조회수 기준으로 내림차순 정렬 후 상위 4개만 가져오기
      popularPosts.value = boardStore.boardList
        .slice()
        .sort((a, b) => b.viewCnt - a.viewCnt)
        .slice(0, 4);
      console.log("Popular posts fetched successfully:", popularPosts.value);
    } else {
      console.warn("게시글 목록이 비어있습니다.");
      popularPosts.value = [];
    }
  } catch (error) {
    console.error("실시간 인기글 불러오기 오류:", error);
  }
};

const fetchPopolarClub = async () => {
  try {
    await clubStore.getClubsByLocation(memberDistrict.value)
    console.log(Array.isArray(clubStore.filteredClubs))
    if (Array.isArray(clubStore.filteredClubs) && clubStore.filteredClubs.length > 0) {
      clubs.value = clubStore.filteredClubs
      .slice()
      .sort((a, b) => b.headCount - a.headCount)
      .slice(0, 10);
      console.log("인근 인기 클럽 로딩 성공", clubs.value);
    } else {
      console.log("클럽 목록이 비어있습니다");
    }
  } catch (error) {
    console.error("인근 인기 클럽 불러오기 오류 : ", error);
  }
}

const fetchPopolarClass = async () => {
  try {
    await classStore.getClassesByLocation(memberDistrict.value)
    console.log(Array.isArray(classStore.filteredClasses))
    if (Array.isArray(classStore.filteredClasses) && classStore.filteredClasses.length > 0) {
      classes.value = classStore.filteredClasses
      .slice()
      .sort((a, b) => b.headCount - a.headCount)
      .slice(0, 10);
      console.log("인근 인기 클래스 로딩 성공", classes.value);
    } else {
      console.log("클래스 목록이 비어있습니다");
    }
  } catch (error) {
    console.error("인근 인기 클래스 불러오기 오류 : ", error);
  }
}

// 컴포넌트 마운트 시 게시글과 인기글 가져오기
onMounted(async () => {
  await fetchPopularPosts(); // 컴포넌트가 마운트될 때 게시글과 인기글 가져오기
  await fetchPopolarClub();
  await fetchPopolarClass();
});

// Watch for route changes to reload data
watch(
  () => route.path,
  async (newPath) => {
    if (newPath === "/") {
      await fetchPopularPosts();
    }
  }
);

const popularPostsTransformed = computed(() =>
  popularPosts.value.map((post) => ({
    ...post,
    categoryText:
      post.category === "club"
        ? "클럽 후기"
        : post.category === "class"
          ? "클래스 후기"
          : post.category === "chat"
            ? "잡담"
            : "기타",
  }))
);

</script>




<style scoped>
/* Swiper 컨테이너 */
.my-swiper {
  width: 100%;
  height: auto;
}

/* Swiper 배너 슬라이드 */
.banner-swiper {
  width: 100%;
  height: auto;
}

/* Swiper 페이지네이션 버튼 커스터마이징 */
:deep(.swiper-pagination-bullet) {
  background: #ffffff !important;
  /* greyBlue */
  opacity: 0.7;
  transition: opacity 0.3s, transform 0.3s;
}

:deep(.swiper-pagination-bullet-active) {
  background: #1e40af !important;
  /* darkBlue */
  transform: scale(1.2);
  opacity: 1;
}

/* Swiper 네비게이션 버튼 커스터마이징 */
:deep(.swiper-button-next),
:deep(.swiper-button-prev) {
  color: #64748b !important;
  /* greyBlue */
  font-size: 1.2rem !important;
  /* 작게 */
  font-weight: bold !important;
  /* 두껍게 */
  opacity: 0.8;
  transition: opacity 0.3s, transform 0.3s;
}

:deep(.swiper-button-next:hover),
:deep(.swiper-button-prev:hover) {
  opacity: 1;
  transform: scale(1.1);
  /* 호버 시 크기 확대 */
}
</style>
