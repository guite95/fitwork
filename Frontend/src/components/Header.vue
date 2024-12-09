<template>
    <!-- Header -->
    <header class="flex items-center justify-between px-12 py-4 bg-white shadow-md">
        <div class="flex items-center space-x-4">
            <!-- Logo -->
            <router-link to="/">
                <img src="/images/logo.png" alt="Logo" class="h-8 object-contain cursor-pointer" />
            </router-link>

            <!-- Navigation -->
            <nav class="hidden md:flex space-x-6 text-gray-700 text-lg">
                <router-link to="/"
                    :class="['font-title', isActive('/') ? 'text-lightBlue font-bold' : 'hover:text-darkBlue']">
                    Home
                </router-link>
                <router-link to="/clubs"
                    :class="['font-title', isActive('/clubs') ? 'text-lightBlue font-bold' : 'hover:text-darkBlue']">
                    Clubs
                </router-link>
                <router-link to="/classes"
                    :class="['font-title', isActive('/classes') ? 'text-lightBlue font-bold' : 'hover:text-darkBlue']">
                    Classes
                </router-link>
                <router-link to="/community"
                    :class="['font-title', isActive('/community') ? 'text-lightBlue font-bold' : 'hover:text-darkBlue']">
                    Community
                </router-link>
                <router-link to="/mypage" v-if="isLoggedIn"
                    :class="['font-title', isActive('/mypage') ? 'text-lightBlue font-bold' : 'hover:text-darkBlue']">
                    My Page
                </router-link>
            </nav>
        </div>
        <div class="flex items-center space-x-3">
            <!-- Welcome Message -->
            <span v-if="isLoggedIn" class="hidden md:inline text-darkBlue font-title">
                안녕하세요, <span class="text-lightBlue"><strong>{{ memberName }}</strong></span> 님
            </span>
            <!-- Login/Logout Button -->
            <button @click="handleAuthAction"
                class="px-4 py-2 font-title bg-lightBlue text-white rounded-full hover:bg-white hover:text-lightBlue hover:border hover:border-lightBlue transition duration-300 text-sm">
                {{ isLoggedIn ? 'Sign-Out' : 'Sign-In' }}
            </button>

            <!-- Sign-Up Button -->
            <button v-if="!isLoggedIn" @click="goToRegisterPage"
                class="px-4 py-2 font-title bg-white text-lightBlue border border-lightBlue rounded-full hover:bg-lightBlue hover:text-white text-sm transition duration-300">
                Sign-Up
            </button>
        </div>
    </header>

    <!-- Mobile Navigation -->
    <div class="md:hidden px-4 py-4 bg-white shadow-md">
        <nav class="flex flex-col space-y-2 text-gray-700 text-lg">
            <router-link to="/"
                :class="['font-title', isActive('/') ? 'text-lightBlue font-bold' : 'hover:text-darkBlue']">
                Home
            </router-link>
            <router-link to="/clubs"
                :class="['font-title', isActive('/clubs') ? 'text-lightBlue font-bold' : 'hover:text-darkBlue']">
                Clubs
            </router-link>
            <router-link to="/classes"
                :class="['font-title', isActive('/classes') ? 'text-lightBlue font-bold' : 'hover:text-darkBlue']">
                Classes
            </router-link>
            <router-link to="/community"
                :class="['font-title', isActive('/community') ? 'text-lightBlue font-bold' : 'hover:text-darkBlue']">
                Community
            </router-link>
            <router-link to="/mypage" v-if="isLoggedIn"
                :class="['font-title', isActive('/mypage') ? 'text-lightBlue font-bold' : 'hover:text-darkBlue']">
                My Page
            </router-link>
        </nav>
    </div>
</template>

<script setup>
import { computed } from "vue";
import { useRouter, useRoute } from "vue-router";
import { useMemberStore } from "../stores/member";

// State
const router = useRouter(); // Vue Router 사용
const route = useRoute(); // 현재 경로 가져오기
const memberStore = useMemberStore();
const memberName = computed(() => memberStore.memberName);
const isLoggedIn = computed(() => !!memberStore.memberToken); // 로그인 여부 상태

// Methods
function handleAuthAction() {
    if (isLoggedIn.value) {
        // 로그아웃 처리
        memberStore.clearMemberToken();
        router.push("/"); // 로그아웃 후 홈으로 이동
    } else {
        // 로그인 페이지로 이동하며 현재 경로를 쿼리로 전달
        router.push({ path: "/sign-in", query: { redirect: route.fullPath } });
    }
}

function goToRegisterPage() {
    // 회원가입 페이지로 이동
    router.push({ path: "/register", query: { redirect: route.fullPath } });
}

function isActive(path) {
    return route.path === path || (path !== "/" && route.path.startsWith(path));
}
</script>

<style scoped>
/* 반응형 디자인 추가 */
@media (max-width: 768px) {
    .hidden.md\:inline {
        display: none;
    }

    .md\:hidden {
        display: block;
    }

    .md\:flex {
        display: none;
    }
}
</style>
