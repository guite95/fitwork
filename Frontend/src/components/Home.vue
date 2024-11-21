<template>
  <div class="bg-gray-50 min-h-screen">
    <!-- Header -->
    <Header />

    <!-- 메인 배너 -->
    <div class="relative">
      <Swiper
        class="banner-swiper"
        :modules="[Autoplay, Pagination]"
        :slides-per-view="1"
        :space-between="0"
        :autoplay="{
          delay: 3000,
          disableOnInteraction: false
        }"
        pagination
        loop
      >
        <SwiperSlide v-for="(banner, index) in banners" :key="index">
          <div
            class="absolute inset-0 bg-cover bg-center blur-md"
            :style="{ backgroundImage: `url(${banner})` }"
          ></div>
          <img :src="banner" alt="Sports Club Banner" class="relative w-full h-80 object-contain object-center z-10" />
        </SwiperSlide>
      </Swiper>
    </div>

    <!-- 클럽 및 클래스 섹션 -->
    <section class="px-80 py-12 bg-gray-50">
      <h2 class="text-2xl font-title text-lightBlue mb-8">
        {{ memberName }} <span class="text-darkBlue">님을 위한 </span>
        <span class="text-lightBlue"> {{ memberDistrict }} </span> <span class="text-darkBlue">근처의</span>
      </h2>

      <!-- 운동 클럽 캐러셀 -->
      <div class="mb-16">
        <h3 class="text-xl font-title text-darkBlue mb-6">운동 클럽 🏃</h3>
        <Swiper
          class="my-swiper"
          :modules="[Navigation]"
          :slides-per-view="6"
          :space-between="20"
          navigation
        >
          <SwiperSlide v-for="(club, index) in clubs" :key="index">
            <div class="bg-white shadow-md rounded-lg p-4">
              <div class="w-full h-40 bg-gray-200 rounded-md mb-4"></div>
              <p class="text-gray-800 font-medium text-sm">{{ club }}</p>
            </div>
          </SwiperSlide>
        </Swiper>
        <button
          @click="navigateToClubs"
          class="mt-6 px-6 py-2 bg-darkBlue text-white rounded-md hover:bg-lightBlue font-title"
        >
          더보기
        </button>
      </div>

      <!-- 운동 클래스 캐러셀 -->
      <div class="mb-16">
        <h3 class="text-xl font-title text-darkBlue mb-6">운동 클래스 🏋️‍♀️</h3>
        <Swiper
          class="my-swiper"
          :modules="[Navigation]"
          :slides-per-view="6"
          :space-between="20"
          navigation
        >
          <SwiperSlide v-for="(exerciseClass, index) in classes" :key="index">
            <div class="bg-white shadow-md rounded-lg p-4">
              <div class="w-full h-40 bg-gray-200 rounded-md mb-4"></div>
              <p class="text-gray-800 font-medium text-sm">{{ exerciseClass }}</p>
            </div>
          </SwiperSlide>
        </Swiper>
        <button
          @click="navigateToClasses"
          class="mt-6 px-6 py-2 bg-darkBlue text-white rounded-md hover:bg-lightBlue font-title"
        >
          더보기
        </button>
      </div>
    </section>

    <!-- 실시간 인기글 -->
    <section class="px-80 py-12 bg-gray-50">
      <h3 class="text-xl font-title text-darkBlue mb-6">실시간 인기글 💬</h3>
      <div class="grid grid-cols-2 gap-6">
        <div class="p-4 bg-gray-100 rounded-lg shadow-sm">
          <p class="text-gray-700 font-medium text-sm">[클럽 후기] 어제 유성헬스에서 러닝 후기</p>
        </div>
        <div class="p-4 bg-gray-100 rounded-lg shadow-sm">
          <p class="text-gray-700 font-medium text-sm">[잡담] 요즘 운동화 뭐가 좋아?</p>
        </div>
        <div class="p-4 bg-gray-100 rounded-lg shadow-sm">
          <p class="text-gray-700 font-medium text-sm">[클래스 후기] 박씨피 트레이너 강추</p>
        </div>
        <div class="p-4 bg-gray-100 rounded-lg shadow-sm">
          <p class="text-gray-700 font-medium text-sm">[잡담] 발목 아플 때</p>
        </div>
      </div>
      <button
        @click="navigateToCommunity"
        class="mt-6 px-6 py-2 bg-darkBlue text-white rounded-md hover:bg-lightBlue font-title"
      >
        더보기
      </button>
    </section>

    <!-- Footer 추가 -->
    <Footer />
  </div>
</template>

<script setup>
import { computed, ref } from "vue";
import Header from "./Header.vue";
import Footer from "./Footer.vue"; // Footer 컴포넌트 추가
import { useRouter } from "vue-router";
import { Swiper, SwiperSlide } from "swiper/vue";
import { Navigation, Pagination, Autoplay } from "swiper";
import { useMemberStore } from "../stores/member";
import "swiper/css";
import "swiper/css/navigation";
import "swiper/css/pagination";

const memberStore = useMemberStore();
const memberName = computed(() => memberStore.memberName); 
const memberAddress = computed(() => memberStore.memberAddress);

const memberDistrict = computed(() => {
  if (memberAddress.value) {
    const parts = memberAddress.value.split(' ');
    return parts[1];
    // return parts.length > 1 ? parts[1] : ''; 
  }
  return '';
});

const router = useRouter();

const banners = ref([
  "/images/home1.png",
  "/images/home2.png",
  "/images/home3.png",
  "/images/home4.png",
  "/images/home5.png",
]);

const clubs = ref([
  "[러닝] 11/10 유성 런닝 모집해요!",
  "[크로스핏] 수업 같이 들을 20대",
  "[클라이밍] 여성 클럽원 모집해요",
  "[러닝] 11/10 유성 런닝 모집해요!",
  "[크로스핏] 수업 같이 들을 20대",
  "[클라이밍] 여성 클럽원 모집해요",
  "[러닝] 11/10 유성 런닝 모집해요!",
  "[크로스핏] 수업 같이 들을 20대",
  "[클라이밍] 여성 클럽원 모집해요",
]);

const classes = ref([
  "[헬스] 박씨피 트레이너",
  "[댄스] 넥스트 댄스 스쿨 리정 안무가",
  "[크로스핏] 하이크로스 일일수강",
  "[헬스] 박씨피 트레이너",
  "[댄스] 넥스트 댄스 스쿨 리정 안무가",
  "[크로스핏] 하이크로스 일일수강",
  "[헬스] 박씨피 트레이너",
  "[댄스] 넥스트 댄스 스쿨 리정 안무가",
  "[크로스핏] 하이크로스 일일수강",
]);

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
  background: #ffffff !important; /* greyBlue */
  opacity: 0.7;
  transition: opacity 0.3s, transform 0.3s;
}

:deep(.swiper-pagination-bullet-active) {
  background: #1e40af !important; /* darkBlue */
  transform: scale(1.2);
  opacity: 1;
}

/* Swiper 네비게이션 버튼 커스터마이징 */
:deep(.swiper-button-next),
:deep(.swiper-button-prev) {
  color: #64748b !important; /* greyBlue */
  font-size: 1.2rem !important; /* 작게 */
  font-weight: bold !important; /* 두껍게 */
  opacity: 0.8;
  transition: opacity 0.3s, transform 0.3s;
}

:deep(.swiper-button-next:hover),
:deep(.swiper-button-prev:hover) {
  opacity: 1;
  transform: scale(1.1); /* 호버 시 크기 확대 */
}
</style>
