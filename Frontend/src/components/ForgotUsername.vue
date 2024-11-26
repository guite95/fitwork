<template>
    <div class="flex items-center justify-center min-h-screen bg-lightBlue/10">
        <!-- 사용자 이름 찾기 카드 -->
        <div class="bg-white w-full max-w-sm p-14 rounded-2xl shadow-lg">
            <!-- 로고 -->
            <div class="flex mb-8">
                <router-link to="/">
                    <img src="/images/logo.png" alt="Logo" class="h-6 mr-2" />
                </router-link>
            </div>

            <!-- 제목 -->
            <h2 class="text-center text-darkBlue font-title text-2xl mb-6">FORGOT USERNAME?</h2>

            <!-- 사용자 이름 찾기 폼 -->
            <form class="space-y-8">
                <!-- Name -->
                <div>
                    <label for="name" class="block text-darkBlue text-sm font-title mb-2">NAME</label>
                    <input id="name" type="text"
                        class="w-full px-4 py-2 border border-gray-100 rounded-full shadow-md focus:outline-none focus:ring-2 focus:ring-lightBlue font-title text-darkBlue text-sm" />
                </div>

                <!-- Email -->
                <div>
                    <label for="email" class="block text-darkBlue text-sm font-title mb-2">EMAIL</label>
                    <input id="email" type="email"
                        class="w-full px-4 py-2 border border-gray-100 rounded-full shadow-md focus:outline-none focus:ring-2 focus:ring-lightBlue font-title text-darkBlue text-sm" />
                </div>

                <!-- Find Username 버튼 -->
                <button type="submit"
                    class="w-full px-4 py-2 text-white bg-lightBlue rounded-full shadow-lg hover:bg-darkBlue transition duration-300 font-title">
                    FIND USERNAME
                </button>
            </form>

            <!-- 하단 링크 -->
            <div class="flex justify-center items-center mt-6 text-lightBlue text-sm font-title">
                <router-link to="/forgot-password" class="hover:underline">FORGOT PASSWORD?</router-link>
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
/* 필요 시 추가 로직 */
</script>

<style scoped>
/* 추가 스타일 */
</style>
