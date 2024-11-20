<template>
    <div class="bg-gray-50 min-h-screen">
      <!-- Header -->
      <Header />
  
      <!-- Content -->
      <section class="flex justify-center items-center py-12 px-4 bg-gray-50">
        <div class="w-full max-w-3xl bg-lightBlue/10 p-8 rounded-2xl shadow-md">
          <h1 class="text-2xl font-title text-darkBlue mb-8">클럽 등록</h1>
  
          <!-- Form -->
          <form class="space-y-6">
            <!-- Title Input -->
            <div>
              <label for="city" class="block text-darkBlue font-title mb-2">제목</label>
              <input
                type="text"
                placeholder="제목"
                class="w-full px-4 py-2 rounded-full border border-gray-300 focus:outline-none focus:ring-2 focus:ring-lightBlue bg-white placeholder-gray-400 font-title text-darkBlue"
              />
            </div>
  
            <!-- City Dropdown -->
            <div>
              <label for="city" class="block text-darkBlue font-title mb-2">지역</label>
              <select
                id="city"
                v-model="selectedCity"
                @change="updateDistricts"
                class="w-full px-4 py-3 rounded-full border border-gray-300 focus:outline-none focus:ring-2 focus:ring-lightBlue bg-white text-gray-500 font-title"
              >
                <option value="" disabled>시를 선택하세요</option>
                <option v-for="city in cities" :key="city.name" :value="city.name">
                  {{ city.name }}
                </option>
              </select>
            </div>
  
            <!-- District Dropdown -->
            <div>
              <select
                id="district"
                v-model="selectedDistrict"
                :disabled="!districts.length"
                class="w-full px-4 py-3 rounded-full border border-gray-300 focus:outline-none focus:ring-2 focus:ring-lightBlue bg-white text-gray-500 font-title"
              >
                <option value="" disabled>구를 선택하세요</option>
                <option v-for="district in districts" :key="district" :value="district">
                  {{ district }}
                </option>
              </select>
            </div>
  
            <!-- Tag Input -->
            <div>
              <label for="city" class="block text-darkBlue font-title mb-2">소개 태그</label>
              <input
                type="text"
                placeholder="ex) #20대 #클라이밍"
                class="w-full px-4 py-2 rounded-full border border-gray-300 focus:outline-none focus:ring-2 focus:ring-lightBlue bg-white placeholder-gray-400 font-title text-darkBlue"
              />
            </div>
  
            <!-- Description Textarea -->
            <div>
              <label for="city" class="block text-darkBlue font-title mb-2">클럽 소개</label>
              <textarea
                rows="10"
                placeholder="클럽 소개글을 적어주세요 😊"
                class="w-full px-4 py-3 rounded-2xl border border-gray-300 focus:outline-none focus:ring-2 focus:ring-lightBlue bg-white placeholder-gray-400 resize-none font-title text-darkBlue"
              ></textarea>
            </div>
  
            <!-- Buttons -->
            <div class="flex justify-between items-center">
              <!-- Cancel Button -->
              <button
                type="button"
                @click="navigateToClasses"
                class="px-6 py-3 bg-white border border-lightBlue text-lightBlue font-title rounded-full hover:bg-lightBlue/10"
              >
                취소
              </button>
  
              <!-- Submit and File Buttons -->
              <div class="flex space-x-4">
                <button
                  type="button"
                  class="px-6 py-3 bg-white border border-lightBlue text-lightBlue font-title rounded-full hover:bg-lightBlue/10"
                >
                  파일 추가
                </button>
                <button
                  type="submit"
                  class="px-6 py-3 bg-lightBlue text-white font-title rounded-full hover:bg-darkBlue"
                >
                  등록
                </button>
              </div>
            </div>
          </form>
        </div>
      </section>
    </div>
  </template>
  
  <script setup>
  import { ref } from "vue";
  import { useRouter } from "vue-router"; // Vue Router 사용
  import Header from "./Header.vue";
  
  // Data
  const cities = ref([
    { name: "서울", districts: ["강남구", "강동구", "강북구", "강서구", "관악구", "광진구", "구로구", "금천구", "노원구", "도봉구", "동대문구", "동작구", "마포구", "서대문구", "서초구", "성동구", "성북구", "송파구", "양천구", "영등포구", "용산구", "은평구", "종로구", "중구", "중랑구"] },
    { name: "대전", districts: ["동구", "대덕구", "서구", "유성구", "중구"] },
    { name: "부산", districts: ["중구", "서구", "동구", "영도구", "부산진구", "동래구", "남구", "북구", "해운대구", "사하구", "금정구", "강서구", "연제구", "수영구", "사상구", "기장군"] },
  ]);
  
  // State
  const selectedCity = ref(""); // 선택된 시
  const selectedDistrict = ref(""); // 선택된 구
  const districts = ref([]); // 선택된 시의 구 목록
  
  // Router
  const router = useRouter();
  
  // Methods
  function updateDistricts() {
    const city = cities.value.find((c) => c.name === selectedCity.value);
    districts.value = city ? city.districts : []; // 선택된 시에 해당하는 구 목록 업데이트
    selectedDistrict.value = ""; // 이전에 선택된 구 초기화
  }
  
  function navigateToClasses() {
    router.push("/classes"); // Classes.vue로 이동
  }
  </script>
  
  <style scoped>
  /* 필요 시 추가 스타일 */
  </style>
  