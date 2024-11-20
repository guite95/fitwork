<template>
    <div class="flex items-center justify-center min-h-screen bg-lightBlue/10 py-20">
        <!-- 회원가입 카드 -->
        <div class="bg-white w-full max-w-sm p-10 rounded-2xl shadow-lg">
            <!-- 로고 -->
            <div class="flex items-center mb-8">
                <router-link to="/">
                    <img src="/images/logo.png" alt="Logo" class="h-6 mr-2" />
                </router-link>
            </div>

            <!-- 제목 -->
            <h2 class="text-center text-darkBlue font-title text-2xl mb-6">CREATE ACCOUNT</h2>

            <!-- 회원가입 폼 -->
            <form class="space-y-8">
                <!-- Username -->
                <div>
                    <label for="username" class="block text-darkBlue text-sm font-title mb-2">USERNAME</label>
                    <input id="username" type="text"
                        class="w-full px-4 py-2 border border-gray-100 rounded-full shadow-md focus:outline-none focus:ring-2 focus:ring-lightBlue font-title text-darkBlue text-sm" />
                </div>

                <!-- Email -->
                <div>
                    <label for="email" class="block text-darkBlue text-sm font-title mb-2">EMAIL</label>
                    <input id="email" type="email"
                        class="w-full px-4 py-2 border border-gray-100 rounded-full shadow-md focus:outline-none focus:ring-2 focus:ring-lightBlue font-title text-darkBlue text-sm" />
                </div>

                <!-- Password -->
                <div>
                    <label for="password" class="block text-darkBlue text-sm font-title mb-2">PASSWORD</label>
                    <input id="password" type="password" v-model="password"
                        :class="[passwordError ? 'border-red-500 focus:ring-red-500' : 'border-gray-100 focus:ring-lightBlue']"
                        class="w-full px-4 py-2 border rounded-full shadow-md focus:outline-none font-title text-darkBlue text-sm" />
                </div>

                <!-- Re-enter Password -->
                <div>
                    <label for="re-password" class="block text-darkBlue text-sm font-title mb-2">RE-ENTER
                        PASSWORD</label>
                    <input id="re-password" type="password" v-model="rePassword"
                        :class="[passwordError ? 'border-red-500 focus:ring-red-500' : 'border-gray-100 focus:ring-lightBlue']"
                        class="w-full px-4 py-2 border rounded-full shadow-md focus:outline-none font-title text-darkBlue text-sm" />
                    <!-- Error Message -->
                    <p v-if="passwordError" class="text-red-500 text-xs mt-1">비밀번호가 일치하지 않습니다</p>
                </div>

                <!-- Name -->
                <div>
                    <label for="name" class="block text-darkBlue text-sm font-title mb-2">NAME</label>
                    <input id="name" type="text"
                        class="w-full px-4 py-2 border border-gray-100 rounded-full shadow-md focus:outline-none focus:ring-2 focus:ring-lightBlue font-title text-darkBlue text-sm" />
                </div>

                <!-- Nickname -->
                <div>
                    <label for="nickname" class="block text-darkBlue text-sm font-title mb-2">NICKNAME</label>
                    <input id="nickname" type="text"
                        class="w-full px-4 py-2 border border-gray-100 rounded-full shadow-md focus:outline-none focus:ring-2 focus:ring-lightBlue font-title text-darkBlue text-sm" />
                </div>

                <!-- Gender -->
                <div>
                    <label class="block text-darkBlue text-sm font-title mb-2">GENDER</label>
                    <div class="flex space-x-4">
                        <button type="button"
                            class="w-full py-2 bg-gray-100 text-darkBlue rounded-full shadow-md focus:ring-2 focus:ring-lightBlue font-title text-sm">
                            MALE
                        </button>
                        <button type="button"
                            class="w-full py-2 bg-lightBlue text-white rounded-full shadow-md focus:ring-2 focus:ring-darkBlue/90 font-title text-sm">
                            FEMALE
                        </button>
                    </div>
                </div>

                <!-- Birthdate -->
                <div>
                    <label for="birthdate" class="block text-darkBlue text-sm font-title mb-2">BIRTHDATE</label>
                    <input id="birthdate" type="date"
                        class="w-full px-4 py-2 border border-gray-100 rounded-full shadow-md focus:outline-none focus:ring-2 focus:ring-lightBlue font-title text-darkBlue text-sm" />
                </div>

                <!-- Phone Number -->
                <div>
                    <label for="phone" class="block text-darkBlue text-sm font-title mb-2">PHONE NUMBER</label>
                    <input id="phone" type="text" v-model="phone" @input="formatPhoneNumber" placeholder="010-0000-0000"
                        class="w-full px-4 py-2 border border-gray-100 rounded-full shadow-md focus:outline-none focus:ring-2 focus:ring-lightBlue font-title text-darkBlue text-sm" />
                </div>

                <!-- Address -->
                <div>
                    <label for="address" class="block text-darkBlue text-sm font-title mb-2">ADDRESS</label>
                    <div class="flex flex-col space-y-2">
                        <div class="flex space-x-2">
                            <input id="address" type="text" v-model="address" placeholder="주소" readonly
                                class="w-full px-4 py-2 border border-gray-100 rounded-full shadow-md focus:outline-none focus:ring-2 focus:ring-lightBlue font-title text-darkBlue text-sm" />
                            <button type="button" @click="openAddressSearch"
                                class="shrink-0 px-3 py-2 bg-lightBlue text-white shadow-md font-title rounded-full hover:bg-darkBlue transition duration-300 text-xs">
                                주소 찾기
                            </button>
                        </div>
                        <input id="detail-address" type="text" v-model="detailAddress" placeholder="상세 주소"
                            class="w-full px-4 py-2 border border-gray-100 rounded-full shadow-md focus:outline-none focus:ring-2 focus:ring-lightBlue font-title text-darkBlue text-sm" />
                    </div>
                </div>


                <!-- User Type -->
                <div>
                    <label class="block text-darkBlue text-sm font-title mb-2">USER TYPE</label>
                    <div class="flex space-x-4">
                        <button type="button"
                            class="w-full py-2 bg-gray-100 text-darkBlue rounded-full shadow-md focus:ring-2 focus:ring-lightBlue font-title text-sm">
                            INSTRUCTOR
                        </button>
                        <button type="button"
                            class="w-full py-2 bg-lightBlue text-white rounded-full shadow-md focus:ring-2 focus:ring-darkBlue/90 font-title text-sm">
                            USER
                        </button>
                    </div>
                </div>

                <!-- 회원가입 버튼 -->
                <button type="submit"
                    class="w-full px-4 py-2 text-white bg-lightBlue rounded-full shadow-lg hover:bg-darkBlue transition duration-300 font-title">
                    CREATE
                </button>
            </form>
        </div>
    </div>
</template>

<script setup>
import { ref, watch } from "vue";

const phone = ref(""); // Phone number
const password = ref(""); // Password input
const rePassword = ref(""); // Re-enter password input
const passwordError = ref(false); // Error state for mismatched passwords

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

const address = ref(""); // Address input
const detailAddress = ref(""); // Detailed address input

// Open address search popup (Kakao/Daum API)
function openAddressSearch() {
    new window.daum.Postcode({
        oncomplete: function (data) {
            // 도로명 주소 우선 사용, 없으면 지번 주소
            address.value = data.roadAddress || data.jibunAddress || "";
        },
    }).open();
}

</script>

<style scoped>
/* 추가 스타일 */
</style>
