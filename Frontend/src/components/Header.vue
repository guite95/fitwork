<template>
    <!-- Header -->
    <header class="flex items-center justify-between px-8 py-4 bg-white shadow-md">
        <div class="flex items-center space-x-6">
            <!-- Logo -->
            <router-link to="/">
                <img src="/images/logo.png" alt="Logo" class="h-8 object-contain cursor-pointer" />
            </router-link>

            <!-- Navigation -->
            <nav class="hidden md:flex space-x-8 text-gray-700 text-xl">
                <router-link to="/" class="hover:text-darkBlue font-title">Home</router-link>
                <router-link to="/clubs" class="hover:text-darkBlue font-title">Clubs</router-link>
                <router-link to="/classes" class="hover:text-darkBlue font-title">Classes</router-link>
                <router-link to="/community" class="hover:text-darkBlue font-title">Community</router-link>
                <router-link to="/mypage" class="hover:text-darkBlue font-title">My Page</router-link>
            </nav>
        </div>
        <div class="flex items-center space-x-6">
            <span class="text-darkBlue font-title">
                안녕하세요, <span class="text-lightBlue"><strong>{{ name }}</strong></span> 님
            </span>
            <button class="px-5 py-2 font-title bg-darkBlue text-white rounded-full hover:bg-lightBlue">
                Logout
            </button>
        </div>
    </header>

</template>

<script setup>
import { ref } from "vue";

const name = ref("김싸피");
</script>

<style scoped></style>