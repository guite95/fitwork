<template>
    <div>
        <h1 class="text-2xl font-title text-darkBlue mb-6">회원정보 조회/수정</h1>
        <form class="space-y-6">
            <!-- 현재 비밀번호 -->
            <div>
                <label for="current-password" class="block text-darkBlue font-title mb-2">현재 비밀번호</label>
                <input id="current-password" type="password" placeholder="현재 비밀번호"
                    class="w-full px-4 py-2 rounded-full border border-gray-300 focus:outline-none focus:ring-2 focus:ring-lightBlue bg-white font-title text-darkBlue" />
            </div>

            <!-- 새 비밀번호 -->
            <div>
                <label for="new-password" class="block text-darkBlue font-title mb-2">새 비밀번호</label>
                <input id="new-password" type="password" v-model="password"
                    :class="[passwordError ? 'border-red-500 focus:ring-red-500' : 'border-gray-300 focus:ring-lightBlue']"
                    placeholder="새 비밀번호"
                    class="w-full px-4 py-2 rounded-full border focus:outline-none bg-white font-title text-darkBlue" />
            </div>

            <!-- 새 비밀번호 확인 -->
            <div>
                <label for="re-password" class="block text-darkBlue font-title mb-2">새 비밀번호 확인</label>
                <input id="re-password" type="password" v-model="rePassword"
                    :class="[passwordError ? 'border-red-500 focus:ring-red-500' : 'border-gray-300 focus:ring-lightBlue']"
                    placeholder="새 비밀번호 확인"
                    class="w-full px-4 py-2 rounded-full border focus:outline-none bg-white font-title text-darkBlue" />
                <p v-if="passwordError" class="text-red-500 text-xs mt-1">비밀번호가 일치하지 않습니다.</p>
            </div>

            <!-- 닉네임 -->
            <div>
                <label for="nickname" class="block text-darkBlue font-title mb-2">닉네임</label>
                <input id="nickname" type="text" placeholder="닉네임"
                    class="w-full px-4 py-2 rounded-full border border-gray-300 focus:outline-none focus:ring-2 focus:ring-lightBlue bg-white font-title text-darkBlue" />
            </div>

            <!-- 전화번호 -->
            <div>
                <label for="phone" class="block text-darkBlue font-title mb-2">전화번호</label>
                <input id="phone" type="text" v-model="phone" @input="formatPhoneNumber" placeholder="010-0000-0000"
                    class="w-full px-4 py-2 rounded-full border border-gray-300 focus:outline-none focus:ring-2 focus:ring-lightBlue bg-white font-title text-darkBlue" />
            </div>

            <!-- 주소 -->
            <div>
                <label for="address" class="block text-darkBlue font-title mb-2">주소</label>
                <div class="flex items-center space-x-2">
                    <input id="address" type="text" v-model="address" placeholder="주소" readonly
                        class="flex-1 px-4 py-2 rounded-full border border-gray-300 focus:outline-none focus:ring-2 focus:ring-lightBlue bg-white font-title text-darkBlue" />
                    <button type="button" @click="openAddressSearch"
                        class="shrink-0 px-5 py-2 bg-lightBlue text-white font-title rounded-full hover:bg-darkBlue transition duration-300 text-sm">
                        주소 찾기
                    </button>
                </div>
            </div>

            <div>
                <div class="flex items-center space-x-2">
                    <input id="address" type="text" placeholder="상세 주소"
                        class="flex-1 px-4 py-2 rounded-full border border-gray-300 focus:outline-none focus:ring-2 focus:ring-lightBlue bg-white font-title text-darkBlue" />
                </div>
            </div>


            <!-- 수정 완료 버튼 -->
            <div class="flex justify-end">
                <button type="submit"
                    class="px-6 py-3 bg-lightBlue text-white font-title rounded-full hover:bg-darkBlue transition duration-300">
                    수정 완료
                </button>
            </div>
        </form>
    </div>
</template>

<script setup>
import { ref, watch } from "vue";

const phone = ref(""); // Phone number
const password = ref(""); // Password input
const rePassword = ref(""); // Re-enter password input
const passwordError = ref(false); // Error state for mismatched passwords
const address = ref(""); // Address input

// Watch for changes in password and rePassword
watch([password, rePassword], ([newPassword, newRePassword]) => {
    passwordError.value = newPassword !== newRePassword && newRePassword.length > 0;
});

// Format phone number
function formatPhoneNumber(event) {
    let value = event.target.value.replace(/[^0-9]/g, ""); // 숫자만 남기기
    if (value.length <= 3) {
        phone.value = value;
    } else if (value.length <= 7) {
        phone.value = `${value.slice(0, 3)}-${value.slice(3)}`;
    } else {
        phone.value = `${value.slice(0, 3)}-${value.slice(3, 7)}-${value.slice(7, 11)}`;
    }
}

// 주소 검색 함수
function openAddressSearch() {
  new daum.Postcode({
    oncomplete: function (data) {
      // 도로명 주소 선택
      address.value = data.roadAddress || data.jibunAddress;
    },
  }).open();
}
</script>

<style scoped>
/* 필요한 추가 스타일 */
</style>
