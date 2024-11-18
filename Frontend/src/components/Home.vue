<template>
  <div class="bg-gray-50">
    <!-- 헤더 -->
    <header class="flex items-center justify-between px-8 py-4 bg-white shadow-md">
      <div class="flex items-center space-x-6">
<!-- 통합된 로고 및 햄버거 메뉴 이미지 -->
        <router-link to="/">
          <img src="/images/logo.png" alt="Logo and Menu" class="h-10 object-contain cursor-pointer" />
        </router-link>

        <!-- 네비게이션 -->
        <nav class="hidden md:flex space-x-8 text-gray-700">
          <router-link to="/" class="hover:text-darkBlue font-title">Home</router-link>
          <router-link to="/clubs" class="hover:text-darkBlue font-title">Clubs</router-link>
          <router-link to="/classes" class="hover:text-darkBlue font-title">Classes</router-link>
          <router-link to="/community" class="hover:text-darkBlue font-title">Community</router-link>
          <router-link to="/mypage" class="hover:text-darkBlue font-title">My Page</router-link>
        </nav>
      </div>
      <div class="flex items-center space-x-6">
        <span class="text-darkBlue font-title">안녕하세요, <span class="text-lightBlue"><strong>{{ name }}</strong> </span> 님</span>
        <button class="px-6 py-2 font-title bg-darkBlue text-white rounded-full hover:bg-lightBlue">
          Logout
        </button>
      </div>
    </header>

    <!-- 메인 배너 -->
    <div class="relative">
      <img alt="Sports Club Banner" class="w-full h-80 object-cover" />
    </div>

    <!-- 클럽 및 클래스 섹션 -->
    <section class="px-8 py-12 bg-gray-50">
      <h2 class="text-2xl font-title text-lightBlue mb-8">
        {{ name }}<span class="text-darkBlue">님을 위한 </span>
        <span class="text-lightBlue">유성구</span> <span class="text-darkBlue">근처의</span>
      </h2>

      <!-- 운동 클럽 -->
      <div class="mb-16">
        <h3 class="text-xl font-title text-gray-800 mb-6">운동 클럽 🏃</h3>
        <div class="grid grid-cols-3 gap-6">
          <div class="bg-white shadow-md rounded-lg p-4">
            <div class="w-full h-40 bg-gray-200 rounded-md mb-4"></div>
            <p class="text-gray-800 font-medium text-sm">[러닝] 11/10 유성 런닝 모집해요!</p>
          </div>
          <div class="bg-white shadow-md rounded-lg p-4">
            <div class="w-full h-40 bg-gray-200 rounded-md mb-4"></div>
            <p class="text-gray-800 font-medium text-sm">[크로스핏] 수업 같이 들을 20대</p>
          </div>
          <div class="bg-white shadow-md rounded-lg p-4">
            <div class="w-full h-40 bg-gray-200 rounded-md mb-4"></div>
            <p class="text-gray-800 font-medium text-sm">[클라이밍] 여성 클럽원 모집해요</p>
          </div>
        </div>
        <button class="mt-6 px-6 py-2 bg-darkBlue text-white rounded-md hover:bg-lightBlue font-title">
          더보기
        </button>
      </div>

      <!-- 운동 클래스 -->
      <div class="mb-16">
        <h3 class="text-xl font-title text-gray-800 mb-6">운동 클래스 🏋️‍♀️</h3>
        <div class="grid grid-cols-3 gap-6">
          <div class="bg-white shadow-md rounded-lg p-4">
            <div class="w-full h-40 bg-gray-200 rounded-md mb-4"></div>
            <p class="text-gray-800 font-medium text-sm">[헬스] 박씨피 트레이너</p>
          </div>
          <div class="bg-white shadow-md rounded-lg p-4">
            <div class="w-full h-40 bg-gray-200 rounded-md mb-4"></div>
            <p class="text-gray-800 font-medium text-sm">[댄스] 넥스트 댄스 스쿨 리정 안무가</p>
          </div>
          <div class="bg-white shadow-md rounded-lg p-4">
            <div class="w-full h-40 bg-gray-200 rounded-md mb-4"></div>
            <p class="text-gray-800 font-medium text-sm">[크로스핏] 하이크로스 일일수강</p>
          </div>
        </div>
        <button class="mt-6 px-6 py-2 bg-darkBlue text-white rounded-md hover:bg-lightBlue font-title">
          더보기
        </button>
      </div>
    </section>

    <!-- 실시간 인기글 -->
    <section class="px-8 py-12 bg-white">
      <h3 class="text-xl font-title text-gray-800 mb-6">실시간 인기글 💬</h3>
      <div class="grid grid-cols-2 gap-6">
        <div class="p-4 bg-gray-100 rounded-lg shadow-sm">
          <p class="text-gray-700 font-medium text-sm">[클럽 후기] 어제 유성헬스에서 러닝 후기</p>
        </div>
        <div class="p-4 bg-gray-100 rounded-lg shadow-sm">
          <p class="text-gray-700 font-medium text-sm">[잡담] 요즘 운동화 뭐가 좋아?</p>
        </div>
        <div class="p-4 bg-gray-100 rounded-lg shadow-sm">
          <p class="text-gray-700 font-medium text-sm">[클래스 후기] 박씨피 트레이너 강추</p>
        </div>
        <div class="p-4 bg-gray-100 rounded-lg shadow-sm">
          <p class="text-gray-700 font-medium text-sm">[잡담] 발목 아플 때</p>
        </div>
      </div>
    </section>
  </div>
</template>

<script>
export default {
  data() {
    return {
      name: "김싸피", // 로그인된 사용자 이름
    };
  },
};
</script>

<style scoped>
/* 추가적인 맞춤 스타일 적용 가능 */
</style>
