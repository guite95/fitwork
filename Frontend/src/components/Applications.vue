<template>
    <div>
      <h1 class="text-2xl font-title text-darkBlue mb-6">신청 목록</h1>
      <div class="space-y-6">
        <!-- Club Section -->
        <div>
          <h2 class="text-xl font-title text-darkBlue mb-4">클럽</h2>
          <table class="w-full border-t border-gray-300 table-fixed">
            <thead>
              <tr class="border-b border-gray-300">
                <th class="py-3 text-greyBlue font-title text-left w-1/12">번호</th>
                <th class="py-3 text-greyBlue font-title text-left w-7/12">내용</th>
                <th class="py-3 text-greyBlue font-title text-center w-2/12">날짜</th>
                <th class="py-3 text-greyBlue font-title text-center w-2/12">취소</th>
              </tr>
            </thead>
            <tbody>
              <tr
                v-for="(application, index) in clubApplications"
                :key="index"
                class="border-b border-gray-200"
              >
                <td class="py-3 text-darkBlue font-title text-left">{{ index + 1 }}</td>
                <td class="py-3 text-darkBlue font-title text-left">{{ application.content }}</td>
                <td class="py-3 text-greyBlue font-title text-center text-xs">{{ application.date }}</td>
                <td class="py-3 text-center">
                  <button
                    @click="cancelApplication('club', index)"
                    class="px-3 py-2 bg-lightBlue text-white rounded-full text-xs hover:bg-greyBlue transition duration-300 font-title"
                  >
                    신청 취소
                  </button>
                </td>
              </tr>
            </tbody>
          </table>
        </div>
  
        <!-- Class Section -->
        <div>
          <h2 class="text-xl font-title text-darkBlue mb-4">클래스</h2>
          <table class="w-full border-t border-gray-300 table-fixed">
            <thead>
              <tr class="border-b border-gray-300">
                <th class="py-3 text-greyBlue font-title text-left w-1/12">번호</th>
                <th class="py-3 text-greyBlue font-title text-left w-7/12">내용</th>
                <th class="py-3 text-greyBlue font-title text-center w-2/12">날짜</th>
                <th class="py-3 text-greyBlue font-title text-center w-2/12">취소</th>
              </tr>
            </thead>
            <tbody>
              <tr
                v-for="(application, index) in classApplications"
                :key="index"
                class="border-b border-gray-200"
              >
                <td class="py-3 text-darkBlue font-title text-left">{{ index + 1 }}</td>
                <td class="py-3 text-darkBlue font-title text-left">{{ application.content }}</td>
                <td class="py-3 text-greyBlue font-title text-center text-xs">{{ application.date }}</td>
                <td class="py-3 text-center">
                  <button
                    @click="cancelApplication('class', index)"
                    class="px-3 py-2 bg-lightBlue text-white rounded-full text-xs hover:bg-greyBlue transition duration-300 font-title"
                  >
                    신청 취소
                  </button>
                </td>
              </tr>
            </tbody>
          </table>
        </div>
      </div>
    </div>
  </template>
  
  <script setup>
  import { ref, onMounted } from "vue";
  import { useClassStore } from "../stores/class";
  import { useClubStore } from "../stores/club";
  
  // 상태 관리
  const classStore = useClassStore();
  const clubStore = useClubStore();

  const clubApplications = ref([]);
  
  const classApplications = ref([]);

  const fetchRegistedClub = async () => {
    try {
      await clubStore.getClubsById(sessionStorage.getItem('memberId'), {

      })
    } catch {
      
    }
  }
  
  // 신청 취소 함수
  function cancelApplication(type, index) {
    if (type === "club") {
      clubApplications.value.splice(index, 1); // 클럽 신청 삭제
    } else if (type === "class") {
      classApplications.value.splice(index, 1); // 클래스 신청 삭제
    }
  }
  </script>
  
  <style scoped>
  /* 추가 스타일 필요 시 작성 */
  </style>
  