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
            <form @submit.prevent="submitForm" class="space-y-8">
                <!-- Username -->
                <div>
                    <label for="username" class="block text-darkBlue text-sm font-title mb-2">USERNAME</label>
                    <div class="flex space-x-2">
                        <input id="username" type="text" v-model="id" required
                            class="w-full px-4 py-2 border border-gray-100 rounded-full shadow-md focus:outline-none focus:ring-2 focus:ring-lightBlue font-title text-darkBlue text-sm" />
                        <button type="button" @click="checkIdDuplication(id)"
                            class="shrink-0 px-3 py-2 bg-lightBlue text-white shadow-md font-title rounded-full hover:bg-darkBlue transition duration-300 text-xs">
                            중복확인
                        </button>
                    </div>
                    <!-- Error Message -->
                    <p v-if="idChecked && idError" class="text-red-400 text-xs mt-1 font-title">이미 사용 중인 아이디입니다</p>
                    <p v-if="idChecked && !idError" class="text-blue-400 text-xs mt-1 font-title">사용 가능한 아이디입니다</p>
                </div>

                <!-- Email -->
                <div>
                    <label for="email" class="block text-darkBlue text-sm font-title mb-2">EMAIL</label>
                    <input id="email" type="email" v-model="email" required @input="validateEmail"
                        :class="[emailError ? 'border-red-500 focus:ring-red-500' : 'border-gray-100 focus:ring-lightBlue']"
                        class="w-full px-4 py-2 border rounded-full shadow-md focus:outline-none font-title text-darkBlue text-sm" />
                    <!-- Error Message -->
                    <p v-if="emailError" class="text-red-400 text-xs mt-1 font-title">유효한 이메일 형식이 아닙니다</p>
                </div>

                <!-- Password -->
                <div>
                    <label for="password" class="block text-darkBlue text-sm font-title mb-2">PASSWORD</label>
                    <input id="password" type="password" v-model="password" required
                        :class="[passwordError ? 'border-red-500 focus:ring-red-500' : 'border-gray-100 focus:ring-lightBlue']"
                        class="w-full px-4 py-2 border rounded-full shadow-md focus:outline-none font-title text-darkBlue text-sm" />
                </div>

                <!-- Re-enter Password -->
                <div>
                    <label for="re-password" class="block text-darkBlue text-sm font-title mb-2">RE-ENTER
                        PASSWORD</label>
                    <input id="re-password" type="password" v-model="rePassword" required
                        :class="[passwordError ? 'border-red-500 focus:ring-red-500' : 'border-gray-100 focus:ring-lightBlue']"
                        class="w-full px-4 py-2 border rounded-full shadow-md focus:outline-none font-title text-darkBlue text-sm" />
                    <!-- Error Message -->
                    <p v-if="passwordError" class="text-red-400 text-xs mt-1 font-title">비밀번호가 일치하지 않습니다</p>
                </div>

                <!-- Name -->
                <div>
                    <label for="name" class="block text-darkBlue text-sm font-title mb-2">NAME</label>
                    <input id="name" type="text" v-model="name" required
                        class="w-full px-4 py-2 border border-gray-100 rounded-full shadow-md focus:outline-none focus:ring-2 focus:ring-lightBlue font-title text-darkBlue text-sm" />
                </div>

                <!-- Nickname -->
                <div>
                    <label for="nickname" class="block text-darkBlue text-sm font-title mb-2">NICKNAME</label>
                    <input id="nickname" type="text" v-model="nickname" required
                        class="w-full px-4 py-2 border border-gray-100 rounded-full shadow-md focus:outline-none focus:ring-2 focus:ring-lightBlue font-title text-darkBlue text-sm" />
                </div>

                <!-- Gender -->
                <div>
                    <label class="block text-darkBlue text-sm font-title mb-2">GENDER</label>
                    <div class="flex space-x-4">
                        <button type="button" @click="gender = 'M'"
                            :class="gender === 'M' ? 'bg-lightBlue text-white' : 'bg-gray-100 text-darkBlue'"
                            class="w-full py-2 rounded-full shadow-md focus:ring-2 focus:ring-lightBlue font-title text-sm">
                            MALE
                        </button>
                        <button type="button" @click="gender = 'F'"
                            :class="gender === 'F' ? 'bg-lightBlue text-white' : 'bg-gray-100 text-darkBlue'"
                            class="w-full py-2 rounded-full shadow-md focus:ring-2 focus:ring-lightBlue font-title text-sm">
                            FEMALE
                        </button>
                    </div>
                </div>

                <!-- Birthdate -->
                <div>
                    <label for="birthdate" class="block text-darkBlue text-sm font-title mb-2">BIRTHDATE</label>
                    <input id="birthdate" type="date" v-model="birthDate" required
                        class="w-full px-4 py-2 border border-gray-100 rounded-full shadow-md focus:outline-none focus:ring-2 focus:ring-lightBlue font-title text-darkBlue text-sm" />
                </div>

                <!-- Phone Number -->
                <div>
                    <label for="phone" class="block text-darkBlue text-sm font-title mb-2">PHONE NUMBER</label>
                    <input id="phone" type="text" v-model="phoneNumber" @input="formatPhoneNumber"
                        placeholder="010-0000-0000" required
                        class="w-full px-4 py-2 border border-gray-100 rounded-full shadow-md focus:outline-none focus:ring-2 focus:ring-lightBlue font-title text-darkBlue text-sm" />
                </div>

                <!-- Address -->
                <div>
                    <label for="address" class="block text-darkBlue text-sm font-title mb-2">ADDRESS</label>
                    <div class="flex flex-col space-y-2">
                        <div class="flex space-x-2">
                            <input id="address" type="text" v-model="address" placeholder="주소" readonly required
                                class="w-full px-4 py-2 border border-gray-100 rounded-full shadow-md focus:outline-none focus:ring-2 focus:ring-lightBlue font-title text-darkBlue text-sm" />
                            <button type="button" @click="openAddressSearch"
                                class="shrink-0 px-3 py-2 bg-lightBlue text-white shadow-md font-title rounded-full hover:bg-darkBlue transition duration-300 text-xs">
                                주소 찾기
                            </button>
                        </div>
                        <input id="detail-address" type="text" v-model="detailAddress" placeholder="상세 주소" required
                            class="w-full px-4 py-2 border border-gray-100 rounded-full shadow-md focus:outline-none focus:ring-2 focus:ring-lightBlue font-title text-darkBlue text-sm" />
                    </div>
                </div>

                <!-- User Type -->
                <div>
                    <label class="block text-darkBlue text-sm font-title mb-2">USER TYPE</label>
                    <div class="flex space-x-4">
                        <button type="button" @click="memberRole = 'ROLE_INSTRUCTOR'"
                            :class="memberRole === 'ROLE_INSTRUCTOR' ? 'bg-lightBlue text-white' : 'bg-gray-100 text-darkBlue'"
                            class="w-full py-2 rounded-full shadow-md focus:ring-2 focus:ring-lightBlue font-title text-sm">
                            INSTRUCTOR
                        </button>
                        <button type="button" @click="memberRole = 'ROLE_USER'"
                            :class="memberRole === 'ROLE_USER' ? 'bg-lightBlue text-white' : 'bg-gray-100 text-darkBlue'"
                            class="w-full py-2 rounded-full shadow-md focus:ring-2 focus:ring-lightBlue font-title text-sm">
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
import axios from "axios";
import { useRouter } from 'vue-router'; // Router 사용
import Swal from "sweetalert2";
import '@/assets/swal_custom.css';

// const REST_API_URL = `http://192.168.210.83:8080/api-member`; // API URL
const REST_API_URL = `http://192.168.210.83:8080/api-member`; // API URL
const router = useRouter(); // Router 인스턴스

// Form data
const id = ref("");
const email = ref("");
const password = ref("");
const rePassword = ref("");
const name = ref("");
const nickname = ref("");
const gender = ref("");
const birthDate = ref("");
const phoneNumber = ref("");
const address = ref("");
const detailAddress = ref("");
const memberRole = ref("");

// Error states
const passwordError = ref(false);
const emailError = ref(false);
const idError = ref(false);
const idChecked = ref(false); // ID 체크 여부

// Watch for changes in password and rePassword
watch([password, rePassword], ([newPassword, newRePassword]) => {
    passwordError.value = newPassword !== newRePassword && newRePassword.length > 0;
});

// Watch for email changes to validate the format
function validateEmail() {
    const emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    emailError.value = !emailPattern.test(email.value) && email.value.length > 0;
}

// Check ID duplication
async function checkIdDuplication(id) {
    try {
        const response = await axios.get(`${REST_API_URL}/check/${id}`, {
            params: { id: id.value },
        });
        idError.value = response.data; // 서버에서 사용 가능 여부 반환
        idChecked.value = true; // ID 체크 완료 표시
    } catch (error) {
        console.error("ID 중복 확인 중 오류가 발생했습니다:", error);
        Swal.fire({
            icon: 'error',
            title: '오류',
            text: 'ID 중복 확인 중 오류가 발생했습니다.',
        });
    }
}

// Format phone number
function formatPhoneNumber(event) {
    let value = event.target.value.replace(/[^0-9]/g, ""); // 숫자만 남기기
    if (value.length <= 3) {
        phoneNumber.value = value;
    } else if (value.length <= 7) {
        phoneNumber.value = `${value.slice(0, 3)}-${value.slice(3)}`;
    } else {
        phoneNumber.value = `${value.slice(0, 3)}-${value.slice(3, 7)}-${value.slice(7, 11)}`;
    }
}

// Open address search popup (Kakao/Daum API)
function openAddressSearch() {
    new window.daum.Postcode({
        oncomplete: function (data) {
            address.value = data.roadAddress || data.jibunAddress || "";
        },
    }).open();
}

// Submit form to create new account
async function submitForm() {
    if (passwordError.value || emailError.value || idError.value) {
        Swal.fire({
            icon: 'warning',
            title: '입력한 정보를 확인하세요',
            text: '입력한 정보에 오류가 있습니다. 다시 확인해주세요.',
            customClass: {
                title: 'custom-swal-title',
                text: 'custon-swal-text',
                confirmButton: 'custom-swal-button' 
            },
            buttonsStyling: false, 
        });
        return;
    }

    try {
        await axios.post(`${REST_API_URL}/regist`, {
            id: id.value,
            email: email.value,
            password: password.value,
            name: name.value,
            nickname: nickname.value,
            gender: gender.value,
            birthDate: birthDate.value,
            phoneNumber: phoneNumber.value,
            address: `${address.value} ${detailAddress.value}`,
            memberRole: memberRole.value,
        });
        Swal.fire({
            icon: 'success',
            title: '회원가입 완료',
            text: '회원가입이 완료되었습니다!',
            showConfirmButton: true,
            customClass: {
                title: 'custom-swal-title', 
                text: 'custon-swal-text',
                confirmButton: 'custom-swal-button' 
            },
            buttonsStyling: false, 
        }).then(() => {
            router.push('/sign-in'); // 회원가입 후 로그인 페이지로 이동
        });
    } catch (error) {
        console.error("회원가입 중 오류가 발생했습니다:", error);
        Swal.fire({
            icon: 'error',
            title: '회원가입 오류',
            text: '회원가입 중 오류가 발생했습니다. 다시 시도해주세요.',
            customClass: {
                title: 'custom-swal-title', 
                text: 'custon-swal-text',
                confirmButton: 'custom-swal-button' 
            },
            buttonsStyling: false, 
        });
    }
}
</script>

<style scoped>
/* 추가 스타일 */
</style>
