<template>
    <div class="flex items-center justify-center min-h-screen bg-lightBlue/10">
        <!-- 로그인 카드 -->
        <div class="bg-white w-full max-w-sm p-14 rounded-2xl shadow-lg">
            <!-- 로고 -->
            <div class="flex mb-8">
                <router-link to="/">
                    <img src="/images/logo.png" alt="Logo" class="h-6 mr-2" />
                </router-link>
            </div>

            <!-- 제목 -->
            <h2 class="text-center text-darkBlue font-title text-2xl mb-6">SIGN IN</h2>

            <!-- 로그인 폼 -->
            <form @submit.prevent="handleLogin" class="space-y-8">
                <!-- Username -->
                <div>
                    <label for="username" class="block text-darkBlue text-sm font-title mb-2">USERNAME</label>
                    <input id="username" type="text" v-model="id"
                        class="w-full px-4 py-2 border border-gray-100 rounded-full shadow-md focus:outline-none focus:ring-2 focus:ring-lightBlue font-title text-darkBlue text-sm" />
                </div>

                <!-- Password -->
                <div>
                    <label for="password" class="block text-darkBlue text-sm font-title mb-2">PASSWORD</label>
                    <input id="password" type="password" v-model="password"
                        class="w-full px-4 py-2 border border-gray-100 rounded-full shadow-md focus:outline-none focus:ring-2 focus:ring-lightBlue font-title text-darkBlue" />
                </div>

                <!-- 로그인 버튼 -->
                <button type="submit"
                    class="w-full px-4 py-2 text-white bg-lightBlue rounded-full shadow-lg hover:bg-darkBlue transition duration-300 font-title">
                    SIGN IN
                </button>
            </form>

            <!-- 하단 링크 -->
            <div class="flex justify-center items-center mt-6 space-x-4 text-gray-400 text-sm font-title">
                <router-link to="/register" class="hover:underline">회원가입</router-link>
                <span class="text-gray-300">|</span>
                <router-link to="/forgot-password" class="hover:underline">아이디/비밀번호 찾기</router-link>
            </div>
        </div>
    </div>
</template>

<script setup>
import { ref } from 'vue';
import { useRouter } from 'vue-router';
import { useMemberStore } from '@/stores/member';
import Swal from 'sweetalert2';
import '@/assets/swal_custom.css'


const id = ref('');
const password = ref('');

const memberStore = useMemberStore();
const router = useRouter();

const handleLogin = async () => {
    try {
        // 로그인 요청 처리
        await memberStore.login(id.value, password.value);
        
        // 이전 페이지로 이동 (query를 이용하여 이전 경로를 전달받은 경우 처리)
        const redirectPath = router.currentRoute.value.query.redirect || '/';
        router.push(redirectPath);
    } catch (error) {
        // 로그인 실패 시 SweetAlert 호출
        console.error('로그인 실패:', error);
        Swal.fire({
            icon: 'error',
            title: '로그인 실패',
            text: '로그인에 실패했습니다. 다시 시도해 주세요.',
            customClass: {
                title: 'custom-swal-title',
                text: 'custom-swal-text',
                confirmButton: 'custom-swal-button' // 버튼에 적용할 클래스
            },
            buttonsStyling: false, // 버튼의 기본 스타일을 없애고 커스텀 스타일을 적용할 수 있게 합니다.
        });
    }
};

</script>

<style scoped>
/* 스타일 그대로 유지 */
</style>
