<template>
  <div class="bg-gray-50 min-h-screen">
    <!-- Header -->
    <Header />

    <!-- Content -->
    <section class="flex justify-center items-center py-12 px-4 bg-gray-50">
      <div class="w-full max-w-3xl bg-lightBlue/10 p-8 rounded-2xl shadow-md">
        <h1 class="text-2xl font-title text-darkBlue mb-8">
          {{ isEditMode ? "클래스 수정" : "클래스 등록" }}
        </h1>

        <!-- Form -->
        <form class="space-y-6" @submit.prevent="handleSubmit">
          <!-- Title Input -->
          <div>
            <label for="title" class="block text-darkBlue font-title mb-2">제목</label>
            <input
              id="title"
              v-model="title"
              type="text"
              placeholder="제목"
              class="w-full px-4 py-2 rounded-full border border-gray-300 focus:outline-none focus:ring-2 focus:ring-lightBlue bg-white placeholder-gray-400 font-title text-darkBlue"
              required
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
              required
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
              required
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
              placeholder="ex) #20대 #요가"
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
              required
            ></textarea>
          </div>

          <!-- Price Input -->
          <div>
            <label for="price" class="block text-darkBlue font-title mb-2">가격 <span class="text-greyBlue">(만원/회차)</span></label>
            <input
              id="price"
              v-model="price"
              type="number"
              placeholder="가격을 입력하세요"
              class="w-full px-4 py-2 rounded-full border border-gray-300 focus:outline-none focus:ring-2 focus:ring-lightBlue bg-white placeholder-gray-400 font-title text-darkBlue"
              required
            />
          </div>

          <!-- File Input -->
          <div>
            <label for="fileUpload" class="block text-darkBlue font-title mb-2">파일 추가</label>
            <div class="flex items-center">
              <!-- Hidden File Input -->
              <input
                id="fileUpload"
                type="file"
                ref="fileInput"
                class="hidden pointer-events-none"
                tabindex="-1"
                @change="handleFileChange"
              />

              <!-- Custom Button -->
              <button
                type="button"
                @click="triggerFileUpload"
                class="px-4 py-3 bg-lightBlue text-white font-title rounded-full hover:bg-darkBlue transition duration-300 text-sm"
              >
                파일 선택
              </button>

              <!-- Selected File Name Display and Remove Button -->
              <div v-if="fileName" class="flex items-center ml-4">
                <span class="text-gray-600 font-title">{{ fileName }}</span>
                <button
                  type="button"
                  @click="removeSelectedFile"
                  class="ml-2 text-gray-500 hover:text-red-500 transition duration-300"
                  aria-label="파일 삭제"
                >
                  ✖
                </button>
              </div>
            </div>
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
              {{ isEditMode ? "수정 완료" : "등록" }}
            </button>
          </div>
        </form>
      </div>
    </section>
  </div>
</template>

<script setup>
import { ref, onMounted } from "vue";
import { useRouter, useRoute } from "vue-router";
import { useClassStore } from "../stores/class"; // Pinia의 class.js 가져오기
import Header from "./Header.vue";

// Store
const classStore = useClassStore();

// Router
const router = useRouter();
const route = useRoute();

// State
const isEditMode = ref(false);
const title = ref("");
const tags = ref("");
const description = ref("");
const price = ref(null);
const selectedCity = ref("");
const selectedDistrict = ref("");
const districts = ref([]);
const fileInput = ref(null);
const file = ref(null);
const fileName = ref("");
const classNo = ref(null);

// Cities Data
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

// 컴포넌트가 마운트될 때 route의 state 데이터를 확인하고 폼에 반영
onMounted(() => {
  classNo.value = route.params.classNo;

  if (classNo.value) {
    // 수정 모드일 경우 기존 클래스 정보 가져오기
    classStore.getClassDetail(classNo.value).then(() => {
      const classData = classStore.classDetail;
      if (classData) {
        title.value = classData.className;
        tags.value = classData.tag;
        description.value = classData.description;
        price.value = classData.price;
        [selectedCity.value, selectedDistrict.value] = classData.location.split(" ");
        // 클래스 번호 유지
        classNo.value = route.params.classNo;
        isEditMode.value = true;
      }
    });
  } else {
    // 새 클래스 등록 모드일 경우
    resetForm();
  }
});

// Methods
function updateDistricts() {
  const city = cities.value.find((c) => c.name === selectedCity.value);
  districts.value = city ? city.districts : [];
  selectedDistrict.value = ""; // 도시 변경 시 자치구 초기화
}

function triggerFileUpload() {
  fileInput.value.click();
}

function handleFileChange(event) {
  file.value = event.target.files[0];
  fileName.value = file.value ? file.value.name : "";
}

function removeSelectedFile() {
  file.value = null;
  fileName.value = "";
  fileInput.value.value = ""; // 파일 input 필드를 초기화
}

function navigateToClasses() {
  if (isEditMode.value && classNo.value) {
    router.push(`/classes/${classNo.value}`);
  } else {
    router.push("/classes");
  }
}

function resetForm() {
  title.value = "";
  tags.value = "";
  description.value = "";
  price.value = null;
  selectedCity.value = "";
  selectedDistrict.value = "";
  file.value = null;
  fileName.value = "";
  classNo.value = null;
  isEditMode.value = false;
}

async function handleSubmit() {
  const classData = {
    classNo: classNo.value,
    leader: sessionStorage.getItem("memberNickname"),
    className: title.value,
    tag: tags.value,
    location: `${selectedCity.value} ${selectedDistrict.value}`,
    description: description.value,
    price: price.value,
    classesFile: null,
  };

  try {
    if (isEditMode.value && classNo.value) {
      // 수정 로직
      await classStore.modifyClass(classNo.value, classData, file.value);
    } else {
      // 새 클래스 등록 로직
      await classStore.createClass(classData, file.value);
    }
    router.push("/classes"); // 클래스 페이지로 이동
  } catch (error) {
    console.error("클래스 등록/수정 실패:", error);
  }
}
</script>

<style scoped>
/* 필요 시 추가 스타일 */
</style>
