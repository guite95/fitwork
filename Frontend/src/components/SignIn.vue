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
            <form class="space-y-8">
                <!-- Username -->
                <div>
                    <label for="username" class="block text-darkBlue text-sm font-title mb-2">USERNAME</label>
                    <input id="username" type="text"
                        class="w-full px-4 py-2 border border-gray-100 rounded-full shadow-md focus:outline-none focus:ring-2 focus:ring-lightBlue font-title text-darkBlue text-sm" />
                </div>

                <!-- Password -->
                <div>
                    <label for="password" class="block text-darkBlue text-sm font-title mb-2">PASSWORD</label>
                    <input id="password" type="password"
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

</script>

<style scoped>

</style>