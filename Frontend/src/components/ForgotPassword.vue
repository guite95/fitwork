<template>
    <div class="flex items-center justify-center min-h-screen bg-lightBlue/10">
        <!-- 비밀번호 찾기 카드 -->
        <div class="bg-white w-full max-w-sm p-14 rounded-2xl shadow-lg">
            <!-- 로고 -->
            <div class="flex mb-8">
                <router-link to="/">
                    <img src="/images/logo.png" alt="Logo" class="h-6 mr-2" />
                </router-link>
            </div>

            <!-- 제목 -->
            <h2 class="text-center text-darkBlue font-title text-2xl mb-6">FORGOT PASSWORD?</h2>

            <!-- 비밀번호 찾기 폼 -->
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
                    <label for="new-password" class="block text-darkBlue text-sm font-title mb-2">PASSWORD</label>
                    <input id="new-password" type="password" v-model="password"
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

                <!-- Reset Password 버튼 -->
                <button type="submit"
                    class="w-full px-4 py-2 text-white bg-lightBlue rounded-full shadow-lg hover:bg-darkBlue transition duration-300 font-title">
                    RESET PASSWORD
                </button>
            </form>

            <!-- 하단 링크 -->
            <div class="flex justify-center items-center mt-6 text-lightBlue text-sm font-title">
                <router-link to="/forgot-username" class="hover:underline">FORGOT USERNAME?</router-link>
            </div>
            <!-- 하단 링크 -->
            <div class="flex justify-center items-center mt-6 space-x-4 text-gray-400 text-sm font-title">
                <router-link to="/register" class="hover:underline">회원가입</router-link>
                <span class="text-gray-300">|</span>
                <router-link to="/sign-in" class="hover:underline">로그인</router-link>
            </div>
        </div>
    </div>
</template>

<script setup>
import { ref, watch } from "vue";

const password = ref(""); // Password input
const rePassword = ref(""); // Re-enter password input
const passwordError = ref(false); // Error state for mismatched passwords

// Watch for changes in password and rePassword
watch([password, rePassword], ([newPassword, newRePassword]) => {
    passwordError.value = newPassword !== newRePassword && newRePassword.length > 0;
});

</script>

<style scoped>
/* 추가 스타일 */
</style>
