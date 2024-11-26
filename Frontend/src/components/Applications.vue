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
              <th class="py-3 text-greyBlue font-title text-left w-7/12">클럽 이름</th>
              <th class="py-3 text-greyBlue font-title text-center w-2/12"></th>
            </tr>
          </thead>
          <tbody>
            <tr
              v-for="(application, index) in clubApplications"
              :key="application.clubNo"
              class="border-b border-gray-200"
            >
              <td class="py-3 text-darkBlue font-title text-left">{{ index + 1 }}</td>
              <td class="py-3 text-darkBlue font-title text-left">{{ application.clubName }}</td>
              <td class="py-3 text-center">
                <button
                  @click="cancelApplication('club', application.clubNo)"
                  class="px-3 py-2 bg-lightBlue text-white rounded-full text-xs hover:bg-greyBlue transition duration-300 font-title"
                >
                  신청 취소
                </button>
              </td>
            </tr>
          </tbody>
        </table>
      </div>

      <div style="height: 10px;"></div>

      <!-- Class Section -->
      <div>
        <h2 class="text-xl font-title text-darkBlue mb-4">클래스</h2>
        <table class="w-full border-t border-gray-300 table-fixed">
          <thead>
            <tr class="border-b border-gray-300">
              <th class="py-3 text-greyBlue font-title text-left w-1/12">번호</th>
              <th class="py-3 text-greyBlue font-title text-left w-7/12">클래스 이름</th>
              <th class="py-3 text-greyBlue font-title text-center w-2/12"></th>
            </tr>
          </thead>
          <tbody>
            <tr
              v-for="(application, index) in classApplications"
              :key="application.classNo"
              class="border-b border-gray-200"
            >
              <td class="py-3 text-darkBlue font-title text-left">{{ index + 1 }}</td>
              <td class="py-3 text-darkBlue font-title text-left">{{ application.className }}</td>
              <td class="py-3 text-center">
                <button
                  @click="cancelApplication('class', application.classNo)"
                  class="px-3 py-2 bg-lightBlue text-white rounded-full text-xs hover:bg-greyBlue transition duration-300 font-title"
                >
                  신청 취소
                </button>
              </td>
            </tr>
          </tbody>
        </table>
      </div>

      <div style="height: 25px;"></div>

      <!-- Opened Section -->
      <div>
        <h1 class="text-2xl font-title text-darkBlue mb-6">개설 목록</h1>
        <div class="space-y-6">
          <!-- Opened Club Section -->
          <div>
            <h3 class="text-lg font-title text-darkBlue mb-4">클럽</h3>
            <table class="w-full border-t border-gray-300 table-fixed">
              <thead>
                <tr class="border-b border-gray-300">
                  <th class="py-3 text-greyBlue font-title text-left w-1/12">번호</th>
                  <th class="py-3 text-greyBlue font-title text-left w-7/12">클럽 이름</th>
                  <th class="py-3 text-greyBlue font-title text-center w-2/12"></th>
                </tr>
              </thead>
              <tbody>
                <tr
                  v-for="(opened, index) in clubRegistered"
                  :key="opened.clubNo"
                  class="border-b border-gray-200"
                >
                  <td class="py-3 text-darkBlue font-title text-left">{{ index + 1 }}</td>
                  <td class="py-3 text-darkBlue font-title text-left">{{ opened.clubName }}</td>
                  <td class="py-3 text-center">
                    <button
                      @click="cancelOpenedApplication('club', opened.clubNo)"
                      class="px-3 py-2 bg-lightBlue text-white rounded-full text-xs hover:bg-greyBlue transition duration-300 font-title"
                    >
                      개설 취소
                    </button>
                  </td>
                </tr>
              </tbody>
            </table>
          </div>
          
          <div style="height: 10px;"></div>

          <!-- Opened Class Section -->
          <div v-if="memberRole === 'ROLE_INSTRUCTOR'">
            <h3 class="text-lg font-title text-darkBlue mb-4">클래스</h3>
            <table class="w-full border-t border-gray-300 table-fixed">
              <thead>
                <tr class="border-b border-gray-300">
                  <th class="py-3 text-greyBlue font-title text-left w-1/12">번호</th>
                  <th class="py-3 text-greyBlue font-title text-left w-7/12">클래스 이름</th>
                  <th class="py-3 text-greyBlue font-title text-center w-2/12"></th>
                </tr>
              </thead>
              <tbody>
                <tr
                  v-for="(opened, index) in classRegistered"
                  :key="opened.classNo"
                  class="border-b border-gray-200"
                >
                  <td class="py-3 text-darkBlue font-title text-left">{{ index + 1 }}</td>
                  <td class="py-3 text-darkBlue font-title text-left">{{ opened.className }}</td>
                  <td class="py-3 text-center">
                    <button
                      @click="cancelOpenedApplication('class', opened.classNo)"
                      class="px-3 py-2 bg-lightBlue text-white rounded-full text-xs hover:bg-greyBlue transition duration-300 font-title"
                    >
                      개설 취소
                    </button>
                  </td>
                </tr>
              </tbody>
            </table>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

  
  <script setup>
  import { ref, onMounted } from "vue";
  import { useClassStore } from "../stores/class";
  import { useClubStore } from "../stores/club";
  import { useRouter } from "vue-router";
  
  // 상태 관리
  const classStore = useClassStore();
  const clubStore = useClubStore();

  const router = useRouter();
  
  const memberRole = ref('');
  const userId = ref('');

  // 신청한 클럽목록
  const clubApplications = ref([]);
  // 신청한 클래스목록
  const classApplications = ref([]);
  // 개설한 클럽 목록
  const clubRegistered = ref([]);
  // 개설한 클래스 목록
  const classRegistered = ref([]);

  // 신청한 클럽 목록 불러오기
  const fetchRegistedClub = async () => {
    try {
      await clubStore.getClubsById(sessionStorage.getItem('memberId'))
      if (Array.isArray(clubStore.userRegisteredClubs) && clubStore.userRegisteredClubs.length > 0) {
        clubApplications.value = clubStore.userRegisteredClubs;
        console.log("회원이 신청한 클럽 로딩 성공")
      } else {
        console.log("클럽 목록이 비어있습니다")
        clubApplications.value = [];
      }
    } catch (error) {
      console.error("신청한 클럽 불러오기 오류 : ", error)
    }
  }

  // 신청한 클래스 목록 불러오기
  const fetchRegistedClass = async () => {
    try {
      await classStore.getClassesById(sessionStorage.getItem('memberId'))
      if (Array.isArray(classStore.userRegisteredClasses) && classStore.userRegisteredClasses.length > 0) {
        classApplications.value = classStore.userRegisteredClasses;
        console.log("회원이 신청한 클래스 로딩 성공")
      } else {
        console.log("클래스 목록이 비어있습니다")
        classApplications.value = [];
      }
    } catch (error) {
      console.error("신청한 클래스 불러오기 오류 : ", error)
    }
  }

  // 개설한 클럽 목록 불러오기
  const fetchLeaderedClub = async () => {
    try {
      await clubStore.getClubsByLeader(sessionStorage.getItem('memberNickname'))
      if (Array.isArray(clubStore.leaderedClubs) && clubStore.leaderedClubs.length > 0) {
        clubRegistered.value = clubStore.leaderedClubs;
        console.log("회원이 개설한 클럽 로딩 성공")
      } else {
        console.log("클럽 목록이 비어있습니다")
        clubRegistered.value = [];
      }
    } catch (error) {
      console.error("개설한 클럽 불러오기 오류 : ", error)
    }
  }

  // 개설한 클래스 목록 불러오기
  const fetchLeaderedClass = async () => {
    try {
      await classStore.getClassesByLeader(sessionStorage.getItem('memberNickname'))
      if (Array.isArray(classStore.leaderedClasses) && classStore.leaderedClasses.length > 0) {
        classRegistered.value = classStore.leaderedClasses;
        console.log("회원이 개설한 클래스 로딩 성공")
      } else {
        console.log("클래스 목록이 비어있습니다")
        classRegistered.value = [];
      }
    } catch (error) {
      console.error("개설한 클래스 불러오기 오류 : ", error)
    }
  }
  
  onMounted(async () => {
    memberRole.value = sessionStorage.getItem('memberRole')
    userId.value = sessionStorage.getItem('memberId')
    await fetchRegistedClub();
    await fetchRegistedClass();
    await fetchLeaderedClub();
    if (memberRole.value === 'ROLE_INSTRUCTOR') {
      await fetchLeaderedClass();
    }
  })

  // 신청 취소 함수
  function cancelApplication(type, typeNo) {
    if (type === "club") {
      clubStore.cancelRegistClub(userId.value, typeNo); // 클럽 신청 삭제
      router.go(0)
    } else if (type === "class") {
      classStore.cancelRegistClass(userId.value, typeNo); // 클래스 신청 삭제
      router.go(0)
    }
    
  }
  
  function cancelOpenedApplication(type, typeNo) {
    if (type === 'club') {
      clubStore.deleteClub(typeNo);
      router.go(0)
    } else if (type === 'class') {
      classStore.deleteClass(typeNo);
      router.go(0)
    }
  }
  </script>
  
  <style scoped>
  /* 추가 스타일 필요 시 작성 */
  </style>
  