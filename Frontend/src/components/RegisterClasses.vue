<template>
  <div class="bg-gray-50 min-h-screen">
    <!-- Header -->
    <Header />

    <!-- Content -->
    <section class="flex justify-center items-center py-12 px-4 bg-gray-50">
      <div class="w-full max-w-3xl bg-lightBlue/10 p-8 rounded-2xl shadow-md">
        <h1 class="text-2xl font-title text-darkBlue mb-8">클래스 등록</h1>

        <!-- Form -->
        <form class="space-y-6" @submit.prevent="registerClass"> <!-- 등록 메서드 바인딩 -->
          <!-- Title Input -->
          <div>
            <label for="title" class="block text-darkBlue font-title mb-2">제목</label>
            <input
              id="title"
              v-model="title"
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
            <label for="tags" class="block text-darkBlue font-title mb-2">소개 태그</label>
            <input
              id="tags"
              v-model="tags"
              type="text"
              placeholder="ex) #20대 #클라이밍"
              class="w-full px-4 py-2 rounded-full border border-gray-300 focus:outline-none focus:ring-2 focus:ring-lightBlue bg-white placeholder-gray-400 font-title text-darkBlue"
            />
          </div>

          <!-- Description Textarea -->
          <div>
            <label for="description" class="block text-darkBlue font-title mb-2">클래스 소개</label>
            <textarea
              id="description"
              v-model="description"
              rows="10"
              placeholder="클래스 소개글을 적어주세요 😊"
              class="w-full px-4 py-3 rounded-2xl border border-gray-300 focus:outline-none focus:ring-2 focus:ring-lightBlue bg-white placeholder-gray-400 resize-none font-title text-darkBlue"
            ></textarea>
          </div>

          <!-- File Input -->
          <div>
            <input type="file" ref="fileInput" class="hidden" @change="handleFileChange" />
            <button
              type="button"
              @click="triggerFileInput"
              class="px-6 py-3 bg-white border border-lightBlue text-lightBlue font-title rounded-full hover:bg-lightBlue/10"
            >
              파일 추가
            </button>
            <span v-if="fileName" class="text-sm text-darkBlue ml-2">{{ fileName }}</span>
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

            <!-- Submit Button -->
            <button
              type="submit"
              class="px-6 py-3 bg-lightBlue text-white font-title rounded-full hover:bg-darkBlue"
            >
              등록
            </button>
          </div>
        </form>
      </div>
    </section>
  </div>
</template>

<script setup>
import { ref } from "vue";
import { useRouter } from "vue-router";
import { useClassStore } from "../stores/class"; // Pinia의 class.js 가져오기
import Header from "./Header.vue";

// Data
const cities = ref([
  { name: "서울", districts: ["강남구", "강동구", "강북구", "강서구"] },
  { name: "대전", districts: ["동구", "서구", "유성구"] },
  { name: "부산", districts: ["중구", "서구", "동구"] },
]);

// State
const selectedCity = ref("");
const selectedDistrict = ref("");
const districts = ref([]);
const title = ref("");
const tags = ref("");
const description = ref("");
const fileInput = ref(null);
const file = ref(null);
const fileName = ref("");

// Class Store
const classStore = useClassStore();

// Router
const router = useRouter();

// Methods
function updateDistricts() {
  const city = cities.value.find((c) => c.name === selectedCity.value);
  districts.value = city ? city.districts : [];
  selectedDistrict.value = "";
}

function triggerFileInput() {
  fileInput.value.click();
}

function handleFileChange(event) {
  file.value = event.target.files[0];
  fileName.value = file.value ? file.value.name : "";
}

async function registerClass() {
  if (!title.value || !selectedCity.value || !selectedDistrict.value || !description.value) {
    alert("모든 필드를 입력해주세요.");
    return;
  }

  const classData = {
    title: title.value,
    city: selectedCity.value,
    district: selectedDistrict.value,
    tags: tags.value,
    description: description.value,
  };

  try {
    await classStore.createClass(classData, file.value);
    alert("클래스가 성공적으로 등록되었습니다.");
    router.push("/classes");
  } catch (error) {
    alert("클래스 등록에 실패했습니다.");
  }
}

function navigateToClasses() {
  router.push("/classes");
}
</script>

<style scoped>
/* 필요 시 추가 스타일 */
</style>
