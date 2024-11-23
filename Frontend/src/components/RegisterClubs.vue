<template>
  <div class="bg-gray-50 min-h-screen">
    <!-- Header -->
    <Header />

    <!-- Content -->
    <section class="flex justify-center items-center py-12 px-4 bg-gray-50">
      <div class="w-full max-w-3xl bg-lightBlue/10 p-8 rounded-2xl shadow-md">
        <h1 class="text-2xl font-title text-darkBlue mb-8">클럽 등록</h1>

        <!-- Form -->
        <form class="space-y-6" @submit.prevent="registerClub">
          <!-- Title Input -->
          <div>
            <label for="title" class="block text-darkBlue font-title mb-2">제목</label>
            <input id="title" v-model="title" type="text" placeholder="제목"
              class="w-full px-4 py-2 rounded-full border border-gray-300 focus:outline-none focus:ring-2 focus:ring-lightBlue bg-white placeholder-gray-400 font-title text-darkBlue" />
          </div>

          <!-- City Dropdown -->
          <div>
            <label for="city" class="block text-darkBlue font-title mb-2">지역</label>
            <select id="city" v-model="selectedCity" @change="updateDistricts"
              class="w-full px-4 py-3 rounded-full border border-gray-300 focus:outline-none focus:ring-2 focus:ring-lightBlue bg-white text-gray-500 font-title">
              <option value="" disabled>시를 선택하세요</option>
              <option v-for="city in cities" :key="city.name" :value="city.name">
                {{ city.name }}
              </option>
            </select>
          </div>

          <!-- District Dropdown -->
          <div>
            <select id="district" v-model="selectedDistrict" :disabled="!districts.length"
              class="w-full px-4 py-3 rounded-full border border-gray-300 focus:outline-none focus:ring-2 focus:ring-lightBlue bg-white text-gray-500 font-title">
              <option value="" disabled>구를 선택하세요</option>
              <option v-for="district in districts" :key="district" :value="district">
                {{ district }}
              </option>
            </select>
          </div>

          <!-- Tag Input -->
          <div>
            <label for="tags" class="block text-darkBlue font-title mb-2">소개 태그</label>
            <input id="tags" v-model="tags" type="text" placeholder="ex) #20대 #클라이밍"
              class="w-full px-4 py-2 rounded-full border border-gray-300 focus:outline-none focus:ring-2 focus:ring-lightBlue bg-white placeholder-gray-400 font-title text-darkBlue" />
          </div>

          <!-- Description Textarea -->
          <div>
            <label for="description" class="block text-darkBlue font-title mb-2">클럽 소개</label>
            <textarea id="description" v-model="description" rows="10" placeholder="클럽 소개글을 적어주세요 😊"
              class="w-full px-4 py-3 rounded-2xl border border-gray-300 focus:outline-none focus:ring-2 focus:ring-lightBlue bg-white placeholder-gray-400 resize-none font-title text-darkBlue"></textarea>
          </div>

          <!-- File Input -->
          <div>
            <input type="file" ref="fileInput" class="hidden" @change="handleFileChange" />
            <button type="button" @click="triggerFileInput"
              class="px-6 py-3 bg-white border border-lightBlue text-lightBlue font-title rounded-full hover:bg-lightBlue/10">
              파일 추가
            </button>
            <span v-if="fileName" class="text-sm text-darkBlue ml-2">{{ fileName }}</span>
          </div>

          <!-- Buttons -->
          <div class="flex justify-between items-center">
            <!-- Cancel Button -->
            <button type="button" @click="navigateToClubs"
              class="px-6 py-3 bg-white border border-lightBlue text-lightBlue font-title rounded-full hover:bg-lightBlue/10">
              취소
            </button>

            <!-- Submit Button -->
            <button type="submit" class="px-6 py-3 bg-lightBlue text-white font-title rounded-full hover:bg-darkBlue">
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
import { useClubStore } from "../stores/club";
import Header from "./Header.vue";

const clubStore = useClubStore();

// Data
const cities = ref([
  {
    name: "서울",
    districts: [
      "강남구",
      "강동구",
      "강북구",
      "강서구",
      "관악구",
      "광진구",
      "구로구",
      "금천구",
      "노원구",
      "도봉구",
      "동대문구",
      "동작구",
      "마포구",
      "서대문구",
      "서초구",
      "성동구",
      "성북구",
      "송파구",
      "양천구",
      "영등포구",
      "용산구",
      "은평구",
      "종로구",
      "중구",
      "중랑구",
    ],
  },
  {
    name: "대전",
    districts: ["동구", "서구", "유성구", "대덕구", "중구"],
  },
  {
    name: "부산",
    districts: [
      "중구",
      "서구",
      "동구",
      "영도구",
      "부산진구",
      "동래구",
      "남구",
      "북구",
      "해운대구",
      "사하구",
      "금정구",
      "강서구",
      "연제구",
      "수영구",
      "사상구",
    ],
  },
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

// Router
const router = useRouter();

// Methods
function updateDistricts() {
  const city = cities.value.find((c) => c.name === selectedCity.value);
  districts.value = city ? city.districts : [];
  selectedDistrict.value = ""; // 도시 변경 시 자치구 초기화
}

function triggerFileInput() {
  fileInput.value.click();
}

function handleFileChange(event) {
  file.value = event.target.files[0];
  fileName.value = file.value ? file.value.name : "";
}

function navigateToClubs() {
  router.push("/clubs");
}

async function registerClub() {
  if (!title.value || !selectedCity.value || !selectedDistrict.value || !description.value) {
    alert("모든 필드를 입력해주세요.");
    return;
  }

  // Club 객체 데이터 구성
  const clubData = {
    clubNo: null, // 초기값 null
    leader: sessionStorage.getItem("memberNickname"), // 로그인된 사용자의 닉네임
    clubName: title.value, // 클럽 이름 = 입력된 제목
    category: null, // 초기값 null
    tag: tags.value, // 입력된 태그
    location: `${selectedCity.value} ${selectedDistrict.value}`, // 시 + 구
    description: description.value, // 입력된 소개글
    headCount: null, // 초기값 null
    clubFile: null, // 파일은 별도로 추가
  };

  try {
    // Pinia store의 createClub 메서드 호출
    await clubStore.createClub(clubData, file.value);
    router.push("/clubs"); // 운동 클럽 페이지로 이동
  } catch (error) {
    console.error("클럽 등록 실패:", error);
  }
}


</script>


<style scoped>
/* 필요 시 추가 스타일 */
</style>
