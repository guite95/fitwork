<template>
  <div class="bg-gray-50 min-h-screen">
    <!-- Header -->
    <Header />

    <!-- 콘텐츠 -->
    <section class="px-80 py-12 bg-gray-50">
      <h1 class="text-3xl font-title text-darkBlue mb-6">운동 클래스 🏋️‍♀️</h1>

      <!-- 검색 섹션 -->
      <div class="flex justify-end items-center mb-8 space-x-4 w-1/4 ml-auto">
        <input
          type="text"
          placeholder="클래스 검색하기"
          class="flex-grow px-4 py-2 border border-gray-300 rounded-2xl focus:outline-none focus:ring-2 focus:ring-lightBlue font-title text-sm"
        />
        <button
          @click="searchClasses"
          class="px-5 py-2 bg-lightBlue text-white rounded-2xl font-title hover:bg-darkBlue transition duration-300 text-sm"
        >
          검색
        </button>
      </div>

      <!-- 추천 클래스 -->
      <div class="mb-12">
        <h2 class="text-xl font-title text-lightBlue mb-4">
          20대 여성 <span class="text-darkBlue font-title">이 관심있는</span>
        </h2>
        <Swiper
          class="my-swiper"
          :modules="[Navigation]"
          :slides-per-view="3"
          :space-between="20"
          navigation
        >
          <SwiperSlide v-for="(classItem, index) in nearbyClasses" :key="index">
            <div class="bg-gray-100 p-4 rounded-md shadow h-24 flex items-center justify-center">
              {{ classItem }}
            </div>
          </SwiperSlide>
        </Swiper>
      </div>

      <!-- 인기 클래스 -->
      <div class="mb-12">
        <h2 class="text-xl font-title text-darkBlue mb-4">최근 인기 많은</h2>
        <Swiper
          class="my-swiper"
          :modules="[Navigation]"
          :slides-per-view="3"
          :space-between="20"
          navigation
        >
          <SwiperSlide v-for="(classItem, index) in popularClasses" :key="index">
            <div class="bg-gray-100 p-4 rounded-md shadow h-24 flex items-center justify-center">
              {{ classItem }}
            </div>
          </SwiperSlide>
        </Swiper>
      </div>

      <!-- 새로운 클래스 만들기 -->
      <div class="text-center">
        <p class="text-xl mb-4 text-darkBlue font-title">마음에 드는 클래스가 없다면 ...</p>
        <router-link to="/register-classes">
          <button class="px-6 py-3 bg-lightBlue text-white font-title rounded-md hover:bg-darkBlue">
            👉 새로운 클래스 만들기
          </button>
        </router-link>
      </div>
    </section>
  </div>
</template>

<script setup>
import { ref } from "vue";
import Header from "./Header.vue";
import { Swiper, SwiperSlide } from "swiper/vue";
import { Navigation } from "swiper";
import "swiper/css";
import "swiper/css/navigation";

// 추천 클래스와 인기 클래스 데이터
const nearbyClasses = ref([
  "유성 런닝 클래스",
  "헬스 기초 트레이닝",
  "크로스핏 심화반",
  "댄스 기초 클래스",
]);

const popularClasses = ref([
  "리정 안무가의 댄스 클래스",
  "하이크로스 크로스핏",
  "헬스 고급 트레이닝",
  "요가 전문가 과정",
]);

// 검색 버튼 클릭 이벤트
function searchClasses() {
  alert("검색 기능은 구현되지 않았습니다.");
}
</script>

<style scoped>
.my-swiper {
  width: 100%;
  height: auto;
}

/* Swiper 네비게이션 버튼 커스터마이징 */
:deep(.swiper-button-next),s
:deep(.swiper-button-prev) {
  color: #64748b !important; /* greyBlue */
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
