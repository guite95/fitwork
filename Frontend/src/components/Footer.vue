<template>
  <footer class="bg-darkBlue text-white py-3">
    <div class="container mx-auto px-8">
      <div class="flex flex-col md:flex-row justify-between items-center">
        <!-- Logo and Description -->
        <div class="mb-6 md:mt-6 md:mb-0">
          <router-link to="/">
            <img src="/images/logo.png" alt="Logo" class="h-8 mb-4" />
          </router-link>
          <p class="text-sm text-white max-w-xs font-title">
            당신 근처의 운동 커뮤니티
          </p>
        </div>

        <!-- Navigation Links -->
        <div class="flex flex-wrap space-x-10 mb-6 md:mb-0 font-title">
          <router-link to="/clubs" class="text-sm hover:underline">운동 클럽</router-link>
          <router-link to="/classes" class="text-sm hover:underline">운동 클래스</router-link>
          <router-link to="/community" class="text-sm hover:underline">커뮤니티</router-link>
        </div>
      </div>

      <!-- Copyright -->
      <div class="text-center text-xs text-gray-100 mt-8 font-title">
        &copy; 2024 Sports Club Platform. All rights reserved.
      </div>
    </div>
  </footer>
</template>

<script setup>

</script>

<style scoped>
footer {
  background-color: #aabcd3;
}

footer a {
  transition: color 0.3s;
}
</style>