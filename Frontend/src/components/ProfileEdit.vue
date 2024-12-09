<template>
    <div>
        <h1 class="text-2xl font-title text-darkBlue mb-6">회원정보 조회/수정</h1>
        <form class="space-y-6" @submit.prevent="handleSubmit">
            <!-- 현재 비밀번호 -->
            <div>
                <label for="current-password" class="block text-darkBlue font-title mb-2">현재 비밀번호</label>
                <input id="current-password" type="password" v-model="currentPassword" placeholder="현재 비밀번호"
                    class="w-full px-4 py-2 rounded-full border border-gray-300 focus:outline-none focus:ring-2 focus:ring-lightBlue bg-white font-title text-darkBlue" required />
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
                <input id="nickname" type="text" v-model="nickname" placeholder="닉네임"
                    class="w-full px-4 py-2 rounded-full border border-gray-300 focus:outline-none focus:ring-2 focus:ring-lightBlue bg-white font-title text-darkBlue" />
            </div>

            <!-- 전화번호 -->
            <div>
                <label for="phone" class="block text-darkBlue font-title mb-2">전화번호</label>
                <input id="phone" type="text" v-model="phone" @input="formatPhoneNumber" placeholder="010-0000-0000"
                    class="w-full px-4 py-2 rounded-full border border-gray-300 focus:outline-none focus:ring-2 focus:ring-lightBlue bg-white font-title text-darkBlue" />
            </div>

            <!-- 이메일 -->
            <div>
                <label for="email" class="block text-darkBlue font-title mb-2">이메일</label>
                <input id="email" type="email" v-model="email" placeholder="이메일"
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

            <!-- 상세 주소 -->
            <div>
                <div class="flex items-center space-x-2">
                    <input id="addressdetail" type="text" v-model="addressDetail" placeholder="상세 주소"
                        class="flex-1 px-4 py-2 rounded-full border border-gray-300 focus:outline-none focus:ring-2 focus:ring-lightBlue bg-white font-title text-darkBlue" />
                </div>
            </div>

            <!-- 수정 완료 버튼 -->
            <div class="flex justify-end">
                <button type="submit"
                    class="px-6 py-2 bg-lightBlue text-white font-title rounded-full hover:bg-darkBlue transition duration-300">
                    수정 완료
                </button>
            </div>
        </form>
    </div>
</template>

<script setup>
import { ref, watch, onMounted } from "vue";
import { useMemberStore } from "../stores/member";
import { useRouter } from "vue-router";

const memberStore = useMemberStore();
const router = useRouter();

const currentPassword = ref(""); // Current password input
const phone = ref(""); // Phone number
const password = ref(""); // New password input
const rePassword = ref(""); // Re-enter new password input
const passwordError = ref(false); // Error state for mismatched passwords
const address = ref(""); // Address input
const addressDetail = ref(""); // Detailed address input
const nickname = ref(""); // Nickname input
const email = ref(""); // Email input

// Watch for changes in password and rePassword
watch([password, rePassword], ([newPassword, newRePassword]) => {
    passwordError.value = newPassword !== newRePassword && newRePassword.length > 0;
});

// Load member info from session storage
onMounted(() => {
    loadMemberFromSession();
});

function loadMemberFromSession() {
    nickname.value = sessionStorage.getItem('memberNickname') || '';
    phone.value = sessionStorage.getItem('memberPhoneNumber') || '';
    address.value = sessionStorage.getItem('memberAddress') || '';
    addressDetail.value = sessionStorage.getItem('memberAddressDetail') || '';
    email.value = sessionStorage.getItem('memberEmail') || '';
}

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

// Handle form submission
async function handleSubmit() {
    try {
        // Update member info
        const updatedInfo = {
            nickname: nickname.value,
            phoneNumber: phone.value,
            email: email.value,
            address: address.value,
            addressDetail: addressDetail.value,
            password: password.value || null,
            currentPassword: currentPassword.value,
        };

        await memberStore.modifyMember(updatedInfo); // 서버로 수정된 정보 전송

        // 수정 성공 시 마이페이지로 이동
        Swal.fire({
            icon: "success",
            title: "수정 완료",
            text: "회원 정보가 성공적으로 수정되었습니다.",
        }).then(() => {
            router.push("/mypage"); // 마이페이지로 이동
        });
    } catch (error) {
        console.error("회원 정보 수정 중 오류가 발생했습니다:", error);

        // 수정 실패 시 알림
        Swal.fire({
            icon: "error",
            title: "수정 실패",
            text: "회원 정보 수정에 실패했습니다. 다시 시도해주세요.",
        });
    }
}
</script>

<style scoped>
/* 필요한 추가 스타일 */
</style>
